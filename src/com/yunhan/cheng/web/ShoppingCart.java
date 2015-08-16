package com.yunhan.cheng.web;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
	private Map<Integer,ShoppingCartItem> books = new HashMap<>();
	
	public void addBook (Book book){
		ShoppingCartItem sci = books.get(book.id);
		if(sci==null){
			//book does NOT exist in the shopping cart 
			sci = new ShoppingCartItem(book);
			books.put(book.id, sci);
			
		}else{
			//book exists in the shopping cart 
			sci.increment();			
		}
	}
	
	public ShoppingCartItem getItem(int id){
		ShoppingCartItem sci = books.get(id);
		if(sci!=null){
			return sci;
		}else{
			return null;
		}
		
	}
	public boolean hasBook(Integer id){
		return books.containsKey(id);
	}

	public Map<Integer, ShoppingCartItem> getBooks() {
		return books;
	}
	
	public Collection<ShoppingCartItem> getItems(){
		return books.values();
	}
	
	public float getCostSum(){
		float total = 0;
		for(ShoppingCartItem sci:getItems()){
			total += sci.getItemCost();
		}
		return total;
	}
	
	public float getSum(){
		float total = 0;
		for(ShoppingCartItem sci:getItems()){
			total+= sci.getItemPrice();
		}		
		return total;
	}
	
	public boolean isEmpty(){
		return books.isEmpty();
	}
	
	public void clear(){
		books.clear();
	}
	
	public void removeItem(Integer id){
		books.remove(id);
	}
	
	public void updateItemQuantity(Integer id, int quantity){
		ShoppingCartItem sci = books.get(id);
		if (sci!=null){
			sci.setQuantity(quantity);
		}
	}
	
	
	public int getBookNumber(){
		int total = 0;
		for(ShoppingCartItem sci:books.values()){
			total += sci.getQuantity();
		}
		
		return total;
	}

}
