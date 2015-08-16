package com.yunhan.cheng.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ArrowServlet
 */
@WebServlet("/arrowServlet")
public class ArrowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ArrowServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	
	protected void gameSet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.getRequestDispatcher("WEB-INF/pages/arrowGameSet.jsp").forward(request,response);
	}
	
	protected void initiateGame(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String gameTypeStr = request.getParameter("gameType");
		String inOptionStr = request.getParameter("inOption");
		String outOptionStr = request.getParameter("outOption");
		String p1Str = request.getParameter("p1");
		String p2Str = request.getParameter("p2");
		
		if(!gameTypeStr.equals("301")&&!gameTypeStr.equals("501")&&!gameTypeStr.equals("701")&&!gameTypeStr.equals("1001")){
			request.getRequestDispatcher("WEB-INF/pages/arrowGameSet.jsp").forward(request,response);
			return;
		}
		
		if(!inOptionStr.equals("openIn")&&!gameTypeStr.equals("doubleIn")){
			request.getRequestDispatcher("WEB-INF/pages/arrowGameSet.jsp").forward(request,response);
			return;
		}
		
		if(!outOptionStr.equals("openOut")&&!outOptionStr.equals("doubleOut")){
			request.getRequestDispatcher("WEB-INF/pages/arrowGameSet.jsp").forward(request,response);
			return;
		}
		
		if(p1Str.equals("")){
			p1Str = "Player 1";
		}
		
		if(p2Str.equals("")){
			p2Str = "Player 2";
		}
		
		request.setAttribute("gameType", gameTypeStr);
		request.setAttribute("inOption",inOptionStr);
		request.setAttribute("outOption", outOptionStr);
		request.setAttribute("p1", p1Str);
		request.setAttribute("p2", p2Str);
		
		
		request.getRequestDispatcher("WEB-INF/pages/arrowGame.jsp").forward(request,response);
	}
	

}
