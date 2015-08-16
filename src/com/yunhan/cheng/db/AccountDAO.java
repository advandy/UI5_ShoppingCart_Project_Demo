package com.yunhan.cheng.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.yunhan.cheng.web.Account;

public class AccountDAO extends BaseDAO{
	public Account checkAccount(long accountNumber, String password) throws ClassNotFoundException, SQLException{
		Class.forName(JDBC_DRIVER);
		// Open a connection
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
        // Execute SQL query
        Statement stmt = conn.createStatement();
        Account acc = null;
        String sql = "select * from account where number="+accountNumber+" AND password="+password;
        ResultSet rs = stmt.executeQuery(sql);
		while(rs.next()){
			long accNo = rs.getLong("number");
			String accHolder = rs.getString("holder");
			int accId = rs.getInt("id");
			float accBalance = rs.getFloat("balance");
			if(accHolder!=null){
				acc= new Account(accNo,accHolder,accId,accBalance);
			}
		}
		
		return acc;
        
		
	}
	
	public boolean updateBalance(Account acc, float debit) throws ClassNotFoundException, SQLException{
		Account account = null;
		Class.forName(JDBC_DRIVER);
		// Open a connection
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
        // Execute SQL query
        Statement stmt = conn.createStatement();
        
        String sql = "UPDATE account SET balance="+ (acc.getAccountBalance()-debit) +" where number="+acc.getAccountNo();
		
        boolean success = stmt.execute(sql);
        
        return !success;
	}

}
