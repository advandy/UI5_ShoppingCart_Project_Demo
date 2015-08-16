package com.yunhan.cheng.web;

import java.util.Date;

public class Book {
	public int id;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String author;
	public String name;
	public Date date;
	public float price;
	public float originPrice;
	public float getOriginPrice() {
		return originPrice;
	}
	public void setOriginPrice(float originPrice) {
		this.originPrice = originPrice;
	}
	public int stock;
	

}
