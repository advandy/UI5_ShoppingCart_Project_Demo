package com.yunhan.cheng.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * get shoppingcart from session, if non exists create a shoppingcart object
 * @author lenovo
 *
 */

public class BookStoreWebUtils {
	public static ShoppingCart getShoppingCart(HttpServletRequest request){
		HttpSession session = request.getSession();
		ShoppingCart sc = (ShoppingCart) session.getAttribute("shoppingCart");
		
		if(sc == null){
			sc = new ShoppingCart();
			session.setAttribute("shoppingCart", sc);
			
		}
		return sc;
	}
	
	public static Account getAccount(HttpServletRequest request){
		HttpSession session = request.getSession();
		Account acc = (Account)session.getAttribute("account");
		return acc;
	}

}
