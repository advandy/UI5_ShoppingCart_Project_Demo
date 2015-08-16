package com.yunhan.cheng.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.yunhan.cheng.web.Book;
import com.yunhan.cheng.web.ConnectionContext;
import com.yunhan.cheng.web.CriteriaBook;
import com.yunhan.cheng.web.Page;
import com.yunhan.cheng.web.ShoppingCartItem;

public class BookDAO extends BaseDAO {
	
	
	public Page<Book> getPage(CriteriaBook cb) throws ClassNotFoundException, SQLException{
	
		Connection conn = ConnectionContext.getInstance().get();
        
        String sql = "";
        // Execute SQL query
        Statement stmt = conn.createStatement();
        
        String keyword = cb.getKeyword();
        int pageNo = cb.getPageNo();
        if (pageNo==0){
        	pageNo=1;
        }
        if(keyword==null){
         sql = "select * from book where price between "+cb.getMinPrice()+" AND "+ cb.getMaxPrice()+ " limit "+(pageNo-1)*6+", 6";	
        }else{        
         sql = "select * from book where name LIKE '%"+keyword+"%' AND price between "+cb.getMinPrice()+" AND "+ cb.getMaxPrice()+ " limit "+(pageNo-1)*6+", 6";
         }
        ResultSet rs = stmt.executeQuery(sql);
        
        Page<Book> page = new Page<Book>(cb.getPageNo());
        
        List<Book> list = new ArrayList<Book>();
        
        while (rs.next()){
        	Book book = new Book();
        	book.id = rs.getInt("id");
        	book.author = rs.getString("author");
        	book.price = rs.getFloat("price");
        	book.date = rs.getDate("date");
        	book.name = rs.getString("name");
        	book.stock = rs.getInt("stock");
        	book.originPrice = rs.getFloat("origin_price");
        	list.add(book);        	
        }
        
        String getCountSql="";
        if(keyword==null){
         getCountSql= "select * from book where price between "+cb.getMinPrice()+" AND "+ cb.getMaxPrice();}
        else{
        	getCountSql = "select * from book where name LIKE '%"+keyword+"%' AND price between "+cb.getMinPrice()+" AND "+ cb.getMaxPrice();
        }
        ResultSet rsCount = stmt.executeQuery(getCountSql);
        int count = 0;
        while(rsCount.next()){
        	count++;
        }
        
        page.setTotalItemNumber(count);
        
        page.setList(list);         
        
        
		return page;
	}
	
	public boolean batchUpdateBookStock(Collection<ShoppingCartItem> col) throws ClassNotFoundException, SQLException{
		
		boolean flag = false;
		// Open a connection
		Connection conn = ConnectionContext.getInstance().get();
        
        // Execute SQL query
        Statement stmt = conn.createStatement();
        
		ArrayList<ShoppingCartItem> arr = new ArrayList<ShoppingCartItem>(col);
		
		for(ShoppingCartItem item: arr){
			int id = item.getBook().getId();
			int stock = item.getQuantity();
			Book book = getBook(id);
			if(book!=null){
				int updatedStock = book.getStock() - stock;
				String sql = "UPDATE book set stock="+updatedStock+" where id="+id;
				if(!stmt.execute(sql)){
					flag= true;
				}else{
					return false;
				}
				
				
			}else{
				return false;
			}
		}
		
		return flag;
	}
	
	public Book getBook(int id) throws ClassNotFoundException, SQLException{
		Book book = null;
		Class.forName(JDBC_DRIVER);
		// Open a connection
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        
        // Execute SQL query
        Statement stmt = conn.createStatement();
        
        String sql = "select * from book where id="+id;
        
        ResultSet rs = stmt.executeQuery(sql);
        
        while(rs.next()){
        	book = new Book();
        	book.id = rs.getInt("id");
        	book.author = rs.getString("author");
        	book.price = rs.getFloat("price");
        	book.date = rs.getDate("date");
        	book.name = rs.getString("name");
        	book.stock = rs.getInt("stock");     
        	book.originPrice = rs.getFloat("origin_price");
        }
        
		
		return book;
	}



}
