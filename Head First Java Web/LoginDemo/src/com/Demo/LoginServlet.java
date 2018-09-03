package com.Demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println("username = " + username);
		System.out.println("password = " + password);
		
		response.setContentType("text/html");
		response.getWriter().println("Login Success");
	}
}
