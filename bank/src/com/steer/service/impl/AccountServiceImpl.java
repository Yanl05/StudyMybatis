package com.steer.service.impl;

import com.steer.pojo.Account;
import com.steer.pojo.Log;
import com.steer.service.AccountService;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author yanl
 * @date 2019-12-08 7:40 下午
 */
public class AccountServiceImpl implements AccountService {
    @Override
    public int transfer(Account accIn, Account accOut) throws IOException {
        // 从对应文件中加载资源，加载为Stream
        InputStream is = Resources.getResourceAsStream("mybatis.xml");
        // 前面是工厂模式 后面是构建者模式
        // 构建一个工厂，通过工厂生成session
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(is);
        SqlSession session = factory.openSession();

        // 查询数据库中是否有转出帐号存在，以及判断帐号密码是否匹配
        Account accOutSelect = session.selectOne("com.steer.mapper.AccountMapper.selByAccnoPwd", accOut);
        if(accOutSelect!=null){
            // 转出帐号真实存在，则判断 余额是否充足
            if(accOutSelect.getBalance()>=accOut.getBalance()){
                // 查询接收账户是否存在
                Account accInSelect = session.selectOne("com.steer.mapper.AccountMapper.selByAccnoName", accIn);
                if(accInSelect!=null){
                    accIn.setBalance(accOut.getBalance());
                    accOut.setBalance(-accOut.getBalance());
                    int index = session.update("com.steer.mapper.AccountMapper.upBalanceByAccno",accIn);
                    index += session.update("com.steer.mapper.AccountMapper.upBalanceByAccno",accOut);
                    if(index==2){
                        // 日志表记录
                        Log log = new Log();
                        log.setAccIn(accIn.getAccNo());
                        log.setAccOut(accOut.getAccNo());
                        log.setMoney(accIn.getBalance());
                        session.insert("com.steer.mapper.LogMapper.insLog", log);

                        // 日志文件记录
                        Logger logger = Logger.getLogger(AccountServiceImpl.class);
                        logger.info(new Date().toLocaleString()+" :"+log.getAccOut()+"给"+log.getAccIn()+"转了"+log.getMoney()+"元。");

                        session.commit();
                        session.close();
                        return SUCCESS;

                    }else{
                        // 转账某个环节出错，对事务进行回滚
                        session.rollback();
                        session.close();
                        return ERROR;
                    }
                }else{
                    // 接收账户错误或者不存在
                    return ACCOUNT_NAME_NOT_MATCH;
                }
            }else{
                // 余额不足
                return ACCOUNT_BALANCE_NOT_ENOUGH;
            }
        }else{
            // 帐号密码不匹配
            return ACCOUNT_PASSWORD_NOT_MATCH;
        }


    }
}
