package com.yunhan.cheng.filter;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import com.yunhan.cheng.web.ConnectionContext;

/**
 * Servlet Filter implementation class TransactionFilter
 */
@WebFilter("/*")
public class TransactionFilter implements Filter {

    /**
     * Default constructor. 
     */
	
	protected static final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
	protected static final String DB_URL="jdbc:mysql://localhost/test?";
	
	protected static final String USER = "root";
	protected static final String PASS = "root";
	
    public TransactionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		Connection conn = null;
		try{		
			//acquire connection
			Class.forName(JDBC_DRIVER);
	         // Open a connection
	        conn = DriverManager.getConnection(DB_URL+"user="+USER+"&password="+PASS); 
			
			//launch transaction
	        conn.setAutoCommit(false);
			
			//thread local to bind conn and thread
	        ConnectionContext.getInstance().bind(conn);
	        	        			
			//forward request to servlet
	        chain.doFilter(request, response);
			//submit transaction
			conn.commit();
		}catch(Exception e){
			e.printStackTrace();
			//rollback
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			//release binding
			ConnectionContext.getInstance().remove();
			
			//close conn
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		// pass the request along the filter chain
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
