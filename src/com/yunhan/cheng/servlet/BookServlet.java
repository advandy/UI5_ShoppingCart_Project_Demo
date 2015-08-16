package com.yunhan.cheng.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.yunhan.cheng.db.AccountDAO;
import com.yunhan.cheng.db.BookDAO;
import com.yunhan.cheng.db.TransactionDAO;
import com.yunhan.cheng.web.Account;
import com.yunhan.cheng.web.Book;
import com.yunhan.cheng.web.BookStoreWebUtils;
import com.yunhan.cheng.web.CriteriaBook;
import com.yunhan.cheng.web.Page;
import com.yunhan.cheng.web.ShoppingCart;
import com.yunhan.cheng.web.ShoppingCartItem;
import com.yunhan.cheng.web.Transaction;


@WebServlet("/bookServlet")
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BookDAO bookDAO = new BookDAO();
	private AccountDAO accDAO = new AccountDAO();
	private TransactionDAO trDAO = new TransactionDAO();

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}  
   
	/**
	 * @param <T>
	 * @throws IOException 
	 * @throws ServletException 
	 * @throws SQLException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String methodName = request.getParameter("method");
		try {
			Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(this, request,response);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	protected void logOff(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, ServletException, IOException, SQLException {
		HttpSession session = request.getSession();
		session.removeAttribute("account");
		this.toCashPage(request, response);
	}
	
	protected void checkAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		String accStr = request.getParameter("account");
		String passStr = request.getParameter("pass");
		
		long accNumber = -1;
		try{
			accNumber = Long.parseLong(accStr);
		}catch(Exception e){}
		
		if(accNumber<0){
			request.setAttribute("error", "Invalid Account");
			request.getRequestDispatcher("WEB-INF/pages/logIn.jsp").forward(request, response);;
		}else{
			//use AccountDAO to get account info			
			Account acc =accDAO.checkAccount(accNumber, passStr);
			if(acc!=null){
				HttpSession session = request.getSession();
				session.setAttribute("account", acc);
				checkStock(request,response);				
			}else{
				request.setAttribute("error", "Account and Password unmatch");
				request.getRequestDispatcher("WEB-INF/pages/logIn.jsp").forward(request, response);;
			}
		}
		
	} 
	
	protected void pay(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException{
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		Account acc = BookStoreWebUtils.getAccount(request);
		if(sc!=null && acc!=null){
			if(sc.getSum()>acc.getAccountBalance()){
				String warning = "Not enough balance, <a href='bookServlet?method=toCartPage'>Click to Shopping Cart</a>";
				request.setAttribute("warning",warning);
				request.getRequestDispatcher("WEB-INF/pages/cash.jsp").forward(request, response);
			}else{
				//1. update balance
				boolean balanceUpdateSuccess = accDAO.updateBalance(acc, sc.getSum());
				//2. update Stock 
				boolean updateStockSuccess = bookDAO.batchUpdateBookStock(sc.getItems());
				
				//3. add to transaction
				boolean transactionSuccess = trDAO.addTransaction(sc, acc.getAccountId());
				
				if(balanceUpdateSuccess && updateStockSuccess && transactionSuccess){
					
					sc.clear();
					request.getRequestDispatcher("WEB-INF/pages/confirm.jsp").forward(request, response);
				}
			}
		}
	}
	
	protected void transactionView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException{
		HttpSession session = request.getSession();
		Account acc = (Account)session.getAttribute("account");		
		if(acc!=null){
			
		 ArrayList<Transaction> tranArr = trDAO.getTransactions(acc.getAccountId());
		 Gson gson = new Gson();
		 String str = gson.toJson(tranArr);
		 request.setAttribute("transactionData", str);
					
		request.getRequestDispatcher("WEB-INF/pages/transaction.jsp").forward(request,response);}
	}
	
	protected void checkStock(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException
	{	
		String stockAlert="";
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		ArrayList<ShoppingCartItem> arr = new ArrayList<ShoppingCartItem>(sc.getItems());
		
		for(ShoppingCartItem item : arr){
			Book book = item.getBook();
			int stock = bookDAO.getBook(book.getId()).getStock();
			
			int quantity = item.getQuantity();
			
			if(quantity>stock){
				stockAlert = stockAlert+ "Your demand quantity ("+quantity+") of "+ book.getName()+" exceeded "+ stock+" in stock. <br><br>" ;				
			}
		}
		
		if(!stockAlert.equals("")){
			stockAlert = stockAlert+" This will cause a delay in delivery. <a href='bookServlet?method=toCartPage'>Click  to change the quantity</a>";
		}
		
		request.setAttribute("stockAlert",stockAlert);
		
		request.getRequestDispatcher("WEB-INF/pages/cash.jsp").forward(request, response);
	}
	
	protected void toCashPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		Account acc = BookStoreWebUtils.getAccount(request);
		if(acc==null){
		request.getRequestDispatcher("WEB-INF/pages/logIn.jsp").forward(request, response);
		}else{
			checkStock(request,response);
		}
	}  
	protected void updateTransactionNote(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		String idStr = request.getParameter("id");
		String noteStr = request.getParameter("note");
		
		int id = -1;
		
		try{
			id = Integer.parseInt(idStr);
			
			
		}catch(Exception e){}
		
		if(id>0){
			trDAO.updateTransactionNote(id,noteStr);
		}
	}
	
	
	protected void updateItemQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException{
	 String idStr = request.getParameter("id");
	 String quantityStr = request.getParameter("quantity");
	 
	 
	 int id = -1;
	 int quantity = -1;
	 
	 try{
		 id = Integer.parseInt(idStr);
	 }catch(Exception e){}
	 
	 try{
		 quantity = Integer.parseInt(quantityStr);
	 }catch(Exception e){}
	 
	 ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
	 
	 if(id>0 && quantity>0){
		 sc.updateItemQuantity(id, quantity);
	 }else{
		 Gson gson = new Gson();
		 String jsonStr = gson.toJson("Error Value");
		 response.getWriter().print(jsonStr);
		 return;
	 }
	 
	 Map<String, Number>result = new HashMap<String, Number>();
	 result.put("bookNumber", sc.getBookNumber());
	 result.put("sum", sc.getSum());
	 result.put("itemTotal", sc.getItem(id).getItemPrice());
	 result.put("quantity", sc.getItem(id).getQuantity());
	 
	 Gson gson = new Gson();
	 String jsonStr = gson.toJson(result);
	 response.setContentType("text/javascript");
	 response.getWriter().print(jsonStr);
	 
	 
	 
	 
	}  
	
	
	protected void getBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ClassNotFoundException, SQLException {
		String idStr = request.getParameter("id");
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		String keywordStr = request.getParameter("keyword");
		
		int pageNo = 1;
		Float minPrice = (float) 0.00;
		Float maxPrice = (float) 10000.00;
		if(pageNoStr!=null&&pageNoStr!=""){
			pageNo = Integer.parseInt(pageNoStr);
		}
		
		if(minPriceStr!=""&&minPriceStr!=null){
			minPrice = Float.parseFloat(minPriceStr);
		}
		
		if(maxPriceStr!=""&&maxPriceStr!=null){
			maxPrice = Float.parseFloat(maxPriceStr);
		}
	    
		int id = 1;
		id = Integer.parseInt(idStr);		
		Book book = bookDAO.getBook(id);
		
		if(book==null){
			response.sendRedirect(request.getContextPath()+"/error.jsp");
			return;
		}
		request.setAttribute("book", book);
		request.setAttribute("minPrice",minPrice);
		request.setAttribute("maxPrice",maxPrice);
		request.setAttribute("pageNo",pageNo);
		request.setAttribute("keyword",keywordStr);
		request.getRequestDispatcher("/WEB-INF/pages/book.jsp").forward(request, response);		
	    
	}
	
	protected void addToCart(HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException, SQLException, IOException, ServletException{
		String idStr = request.getParameter("id");
		int id = Integer.parseInt(idStr);		
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		if(id>0){
		Book book = bookDAO.getBook(id);
			if(book!=null){
				sc.addBook(book);
				getBooks(request,response);
			}else{
				response.sendRedirect(request.getContextPath()+"/error.jsp");
			}
		}
		
	}
	
	protected void deleteItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		String idStr = request.getParameter("itemId");
		int id = Integer.parseInt(idStr);
		sc.removeItem(id);
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		
		int pageNo = 1;
		Float minPrice = (float) 0.00;
		Float maxPrice = (float) 10000.00;
		if(pageNoStr!=null&&pageNoStr!=""){
			pageNo = Integer.parseInt(pageNoStr);
		}
		
		if(minPriceStr!=""&&minPriceStr!=null){
			minPrice = Float.parseFloat(minPriceStr);
		}
		
		if(maxPriceStr!=""&&maxPriceStr!=null){
			maxPrice = Float.parseFloat(maxPriceStr);
		}
		
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("minPrice", minPrice);
		request.setAttribute("maxPrice", maxPrice);
		
		request.getRequestDispatcher("WEB-INF/pages/cart.jsp").forward(request, response);
				
	}
	
	
	protected void clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		ShoppingCart sc = BookStoreWebUtils.getShoppingCart(request);
		sc.clear();
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		
		int pageNo = 1;
		Float minPrice = (float) 0.00;
		Float maxPrice = (float) 10000.00;
		if(pageNoStr!=null&&pageNoStr!=""){
			pageNo = Integer.parseInt(pageNoStr);
		}
		
		if(minPriceStr!=""&&minPriceStr!=null){
			minPrice = Float.parseFloat(minPriceStr);
		}
		
		if(maxPriceStr!=""&&maxPriceStr!=null){
			maxPrice = Float.parseFloat(maxPriceStr);
		}
		
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("minPrice", minPrice);
		request.setAttribute("maxPrice", maxPrice);
		
		request.getRequestDispatcher("WEB-INF/pages/cart.jsp").forward(request, response);
		
	}
	
	
	protected void toCartPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		String keyword = request.getParameter("keyword");
		
		int pageNo = 1;
		Float minPrice = (float) 0.00;
		Float maxPrice = (float) 10000.00;
		if(pageNoStr!=null&&pageNoStr!=""){
			pageNo = Integer.parseInt(pageNoStr);
		}
		
		if(minPriceStr!=""&&minPriceStr!=null){
			minPrice = Float.parseFloat(minPriceStr);
		}
		
		if(maxPriceStr!=""&&maxPriceStr!=null){
			maxPrice = Float.parseFloat(maxPriceStr);
		}
		
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("keyword", keyword);
		request.setAttribute("minPrice", minPrice);
		request.setAttribute("maxPrice", maxPrice);
		
		request.getRequestDispatcher("WEB-INF/pages/cart.jsp").forward(request, response);
	}
	
	
	protected void getBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String pageNoStr = request.getParameter("pageNo");
		String minPriceStr = request.getParameter("minPrice");
		String maxPriceStr = request.getParameter("maxPrice");
		String keyword = request.getParameter("keyword");
		
		
		
		int pageNo = 1;
		Float minPrice = (float) 0.00;
		Float maxPrice = (float) 10000.00;
		if(pageNoStr!=null&&pageNoStr!=""){
			pageNo = Integer.parseInt(pageNoStr);
		}
		
		if(minPriceStr!=""&&minPriceStr!=null){
			minPrice = Float.parseFloat(minPriceStr);
		}
		
		if(maxPriceStr!=""&&maxPriceStr!=null){
			maxPrice = Float.parseFloat(maxPriceStr);
		}
		
		if(pageNo<1){
			pageNo=1;
		}
		
		CriteriaBook cb = new CriteriaBook(minPrice,maxPrice,pageNo);
		if(keyword!=null){
			cb.setKeyword(keyword);		
		}
		try {
			Page page = bookDAO.getPage(cb);	
			request.setAttribute("pageNo",pageNo);
			request.setAttribute("bookpage", page);
			request.setAttribute("minPrice",minPrice);
			request.setAttribute("maxPrice",maxPrice);
			request.setAttribute("keyword",keyword);
			request.getRequestDispatcher("/WEB-INF/pages/books.jsp").forward(request,response);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
