package com.yunhan.cheng.web;

import java.util.List;

public class Page <T>{

	//curent page
	private int pageNo;
	
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	private List<T> list;
	
	private int pageSize = 6;
	
	private long totalItemNumber;
	
	public Page(int pageNo){
		super();
		this.pageNo = pageNo;
	}
	
	public int getPageNo(){		
		if(pageNo<0){
			pageNo = 1;
		}
		
		if(pageNo>getTotalPageNumber()){
			pageNo = getTotalPageNumber();						
		}
		
		return pageNo;
	}
	
	
	public int getPageSize(){	
		
		return pageSize;
	}
	
	public void setList(List<T> list){
		this.list = list;
	}
	
	public List<T> getList(){
		return this.list;
	}
	
	
	//get total number of page
	
	public int getTotalPageNumber(){
		int totalPageNumber = (int)totalItemNumber/ pageSize;
		if(totalItemNumber%pageSize!=0){
			totalPageNumber ++;
		}
		return totalPageNumber;		
	}
	
	
	public void setTotalItemNumber(long totalItemNumber){
		this.totalItemNumber = totalItemNumber;
	}
	
	public boolean isHasNext(){
		if(getPageNo()<getTotalPageNumber()){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean isHasPrev(){
		if(getPageNo()> 1){
			return true;
		}else{
			return false;
		}
	}
	
	public int getPrevPage(){
		if(isHasPrev()){
			return getPageNo() -1;
		}else{
			return getPageNo();
		}
	}
	
	public int getNextPage(){
		if(isHasNext()){
			return getPageNo() + 1;
		}else{
			return getPageNo();
		}
	}
	
	
	
	
	
	
}
