package com.yunhan.cheng.web;

import java.util.ArrayList;
import java.util.Map;

public class Transaction {

	private String tr_time;
	private String user_name;
	private float tr_sum;
	private float tr_cost;
	private String note;
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public float getTr_cost() {
		return tr_cost;
	}

	public void setTr_cost(float tr_cost) {
		this.tr_cost = tr_cost;
	}

	private int tr_id;
	private ArrayList<String> items = new ArrayList<String>();
	
	public ArrayList<String> getItems() {
		return items;
	}

	public void setItems(ArrayList<String> items) {
		this.items = items;
	}

	public void addItem(String name,int quantity){
		items.add(name+","+quantity);
	}

	public String getTr_time() {
		return tr_time;
	}

	public void setTr_time(String tr_time) {
		this.tr_time = tr_time;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public float getTr_sum() {
		return tr_sum;
	}

	public void setTr_sum(float tr_sum) {
		this.tr_sum = tr_sum;
	}

	public int getTr_id() {
		return tr_id;
	}

	public void setTr_id(int tr_id) {
		this.tr_id = tr_id;
	}

	
	
	
	
}
