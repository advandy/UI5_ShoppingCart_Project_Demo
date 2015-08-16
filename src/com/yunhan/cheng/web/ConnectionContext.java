package com.yunhan.cheng.web;

import java.sql.Connection;

public class ConnectionContext {
	private ConnectionContext(){}
	
	private static ConnectionContext instance;
	
	public static ConnectionContext getInstance(){
		if(instance==null){
			instance  = new ConnectionContext();
		}
		return instance;
	}
	
	private ThreadLocal<Connection> connThreadLocal;
	
	public void bind(Connection conn){
		if(connThreadLocal==null){
			connThreadLocal = new ThreadLocal<>();
		}
		connThreadLocal.set(conn);
	}
	
	public Connection get(){
		if(connThreadLocal==null){
			connThreadLocal = new ThreadLocal<>();
		}
		return connThreadLocal.get();
		
	}
	
	public void remove(){
		if(connThreadLocal==null){
			connThreadLocal = new ThreadLocal<>();
		}
		connThreadLocal.remove();
	}

}
