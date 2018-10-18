package com.mio4.web.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 跳转到首页
 */
@WebServlet(name = "IndexServlet")
public class IndexServlet extends BaseServlet {
	public String index(HttpServletRequest request, HttpServletResponse response) {
		//TODO
		//从数据库查询商品，放入request域中

		return "/jsp/index.jsp";
	}
}
