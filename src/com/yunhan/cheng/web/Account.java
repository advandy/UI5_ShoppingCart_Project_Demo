package com.yunhan.cheng.web;

public class Account {
	
	public Account(long accountNo, String accountHolder, int accountId,
			float accountBalance) {
		super();
		this.accountNo = accountNo;
		this.accountHolder = accountHolder;
		this.accountId = accountId;
		this.accountBalance = accountBalance;
	}
	
	private long accountNo;
	private String accountHolder;
	private int accountId;
	private float accountBalance;
	public long getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccountHolder() {
		return accountHolder;
	}
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public float getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(float accountBalance) {
		this.accountBalance = accountBalance;
	}

}
