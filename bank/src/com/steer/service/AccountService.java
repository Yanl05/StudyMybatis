package com.steer.service;

import com.steer.pojo.Account;

import java.io.IOException;

/**
 * @author yanl
 * @date 2019-12-08 7:36 下午
 */
public interface AccountService {
    //  定义转账业务的各种状态码

    /**
     * 帐号和密码不匹配状态码
     */
    int ACCOUNT_PASSWORD_NOT_MATCH=1;
    /**
     * 余额不足
     */
    int ACCOUNT_BALANCE_NOT_ENOUGH=2;
    /**
     * 账户姓名不匹配
     */
    int ACCOUNT_NAME_NOT_MATCH=3;
    /**
     * 转账失败
     */
    int ERROR=4;
    /**
     * 转账成功
     */
    int SUCCESS=5;


    /**
     *  转账业务
     * @param accIn
     * @param accOut
     * @return
     */
    int transfer(Account accIn, Account accOut) throws IOException;
}
