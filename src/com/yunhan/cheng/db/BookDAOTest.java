package com.yunhan.cheng.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.yunhan.cheng.web.Book;
import com.yunhan.cheng.web.CriteriaBook;
import com.yunhan.cheng.web.Page;

public class BookDAOTest {

	@Test
	public void testGetPage() throws ClassNotFoundException, SQLException {
		BookDAO bookDAO = new BookDAO();
		CriteriaBook cb = new CriteriaBook(0,Float.MAX_VALUE,1);		
		Page page = bookDAO.getPage(cb);
		
		List<Book> bookList = page.getList();
		for (Book obj : bookList) {
			System.out.println(obj.name+"\n");
		}
		
	}

}
