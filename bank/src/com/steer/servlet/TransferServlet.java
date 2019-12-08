package com.steer.servlet;

import com.steer.pojo.Account;
import com.steer.service.AccountService;
import com.steer.service.impl.AccountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author yanl
 * @date 2019-12-08 8:34 下午
 */

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
    private AccountService accService = new AccountServiceImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 信息中可能会有中文，先设置文本编码
        req.setCharacterEncoding("utf-8");
        // 实例化一个转出对象
        Account accOut = new Account();
        accOut.setAccNo(req.getParameter("accOutAccNo"));
        accOut.setPassword(Integer.parseInt(req.getParameter("accOutPassword")));
        accOut.setBalance(Double.parseDouble(req.getParameter("accOutBalance")));
        // 实例化一个收款账户
        Account accIn =new Account();
        accIn.setAccNo(req.getParameter("accInAccNo"));
        accIn.setName(req.getParameter("accInName"));

        // 调用转账方法,返回的是转账业务的各个状态码
        int index = accService.transfer(accIn, accOut);
        if(index==AccountService.SUCCESS){
            // 转账成功
//            resp.sendRedirect("/bank/show");
            resp.sendRedirect("success.jsp");
        }else{
            // 转账失败
            HttpSession session = req.getSession();
            session.setAttribute("code", index);
            resp.sendRedirect("/bank/error/error.jsp");
        }
    }
}
