package com.yunhan.cheng.db;

import static org.junit.Assert.*;

import org.junit.Test;

public class BaseDAOTest {

	@Test
	public void testQuery() throws ClassNotFoundException {
		BaseDAO baseDAO = new BaseDAO();
		baseDAO.query("Select * from book");
	}

}
