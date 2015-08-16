package com.yunhan.cheng.web;

public class CriteriaBook {
	
	private float minPrice = 0;
	private float maxPrice = Float.MAX_VALUE;
	private String keyword;
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	private int PageNo;

	public float getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(float minPrice) {
		this.minPrice = minPrice;
	}

	public float getMaxPrice() {
		return maxPrice;
	}

	public void setMaxPrice(float maxPrice) {
		this.maxPrice = maxPrice;
	}

	public int getPageNo() {
		
		return PageNo;
	}

	public void setPageNo(int pageNo) {
		PageNo = pageNo;
	}

	public CriteriaBook(float minPrice, float maxPrice, int pageNo) {
		super();
		this.minPrice = minPrice;
		this.maxPrice = maxPrice;
		this.PageNo = pageNo;
	}
	
	
	

}
