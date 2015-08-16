package com.yunhan.cheng.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import com.yunhan.cheng.web.ConnectionContext;
import com.yunhan.cheng.web.ShoppingCart;
import com.yunhan.cheng.web.ShoppingCartItem;
import com.yunhan.cheng.web.Transaction;

public class TransactionDAO extends BaseDAO {
	
	public void updateTransactionNote(int id,String note) throws SQLException{
		
		Connection conn = ConnectionContext.getInstance().get();

        // Execute SQL query
        Statement stmt = conn.createStatement();
		String sql = "UPDATE transaction set note='"+note+"' where transaction_id="+id;
		stmt.execute(sql);
		
	}
	
	
	public boolean addTransaction(ShoppingCart sc, int user_id) throws ClassNotFoundException, SQLException{
		Connection conn = ConnectionContext.getInstance().get();

        // Execute SQL query
        Statement stmt = conn.createStatement();
        
		ArrayList<ShoppingCartItem> arr = new ArrayList<ShoppingCartItem>(sc.getItems());
		
		UUID cartUUID = UUID.randomUUID(); 
		String cart_id = cartUUID.toString();
		Date date = new Date();
		String transaction_time =date.toString();
		float sum = sc.getSum();
		float cost = sc.getCostSum();
		
		//add to transaction table
		String sql = "insert into transaction (transaction_time,user_id,cart_id, sum, cost) values('"+ 
		transaction_time+"',"+user_id+ ", '"+cart_id+"' ,"+sum+","+cost+")";
		
		stmt.execute(sql);
		
		for(ShoppingCartItem item:arr){
			sql = "insert into transaction_item (cart_id,book_id,book_quantity) values('"+ 
			  cart_id+"' ,"+item.getBook().getId()+ ","+item.getQuantity()+")";
			stmt.execute(sql);
		}
		
		 
        
         
		 return true;
	}
	
	public ArrayList<Transaction> getTransactions(int user_id) throws ClassNotFoundException, SQLException{
		
		Connection conn = ConnectionContext.getInstance().get();

        // Execute SQL query
        Statement stmt = conn.createStatement();
        ArrayList<Transaction> trArr = new ArrayList<Transaction>();
		
		String sql = "Select * from transaction "
				+ " inner Join transaction_item on transaction.user_id="+ user_id
				+ " AND transaction.cart_id = transaction_item.cart_id "
				+ " inner Join book on book.id = transaction_item.book_id"
				+ " inner Join account on account.id="+user_id;
				
		int trId = 0;
		Transaction tr = new Transaction();
		ResultSet rs =stmt.executeQuery(sql);
		while(rs.next()){
			if(trId!= rs.getInt("transaction_id")){
				trId= rs.getInt("transaction_id");
				tr = new Transaction();
				tr.setTr_id(trId);
				tr.setTr_cost(rs.getFloat("cost"));
				tr.setTr_sum(rs.getFloat("sum"));
				tr.setTr_time(rs.getString("transaction_time"));
				tr.setUser_name(rs.getString("holder"));
				Integer q = rs.getInt("book_quantity");
				tr.addItem(rs.getString("name"), q);	
				tr.setNote(rs.getString("note"));
				trArr.add(tr);				
			}else{
				tr = trArr.get(trArr.indexOf(tr));
				Integer q = rs.getInt("book_quantity");
				tr.addItem(rs.getString("name"), q);
			}
		}
		
		return trArr;
		
	}

}
