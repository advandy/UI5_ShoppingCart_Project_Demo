package com.yunhan.cheng.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

import com.yunhan.cheng.web.ConnectionContext;


public class BaseDAO {

	protected static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
	protected static final String DB_URL="jdbc:mysql://localhost/test?";
	
	protected static final String USER = "root";
	protected static final String PASS = "root";
	
	public ResultSet query (String sql) throws ClassNotFoundException{
		
		try{
			 
            Connection conn = ConnectionContext.getInstance().get();

	         // Execute SQL query
	         Statement stmt = conn.createStatement();
	         
	         ResultSet rs = stmt.executeQuery(sql);
	       	       	         
	         rs.close();
	         stmt.close();
	         conn.close();
	         
	         System.out.print(rs);
	         
	         return rs;
			
		}catch(SQLException se){
			se.printStackTrace();
		}
		return null;
		
	    
		
		
	}
	
	
	
	
}
