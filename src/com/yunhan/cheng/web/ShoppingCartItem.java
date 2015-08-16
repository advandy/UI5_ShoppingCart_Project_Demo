package com.yunhan.cheng.web;

public class ShoppingCartItem {
	private Book book;
	public Book getBook() {
		return book;
	}

	public int getQuantity() {
		return quantity;
	}

	private int quantity;
		
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public ShoppingCartItem(Book book){
		this.book = book;
		this.quantity = 1;
	}
	
	public Float getItemPrice(){
		return  book.getPrice()*quantity;		
	}
	
	public Float getItemCost(){
		return book.getOriginPrice()*quantity;
	}
	
	public void increment(){
		this.quantity++;
	}

}
