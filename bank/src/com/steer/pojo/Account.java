package com.steer.pojo;

/**
 * @author yanl
 * @date 2019-12-08 6:52 下午
 */
public class Account {
    private int id;
    private String accNo;
    private int password;
    private String name;
    private double balance;

 // 标准实体类对构造方法是没有要求的

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
