package com.mio4.web.service;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserServlet",urlPatterns = "/user")
public class UserServlet extends BaseServlet {
	public String add(HttpServletRequest request, HttpServletResponse response){
		return null;
	}

	/**
	 * 跳转到登录页面
	 * @param request
	 * @param response
	 * @return
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response){
		return "/jsp/login.jsp";
	}

	/**
	 * 跳转到注册页面
	 * @param request
	 * @param response
	 * @return
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response){
		return "/jsp/register.jsp";
	}
}
