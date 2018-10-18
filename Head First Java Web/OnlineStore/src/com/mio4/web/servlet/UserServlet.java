package com.mio4.web.servlet;

import com.mio4.converter.MyConverter;
import com.mio4.domain.User;
import com.mio4.service.IUserService;
import com.mio4.service.impl.UserServiceImpl;
import com.mio4.utils.MD5Utils;
import com.mio4.utils.UUIDUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import javax.mail.MessagingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;

@WebServlet(name = "UserServlet")
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

	/**
	 * 进行用户的注册
	 * @param request
	 * @param response
	 * @return
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws SQLException, InvocationTargetException, IllegalAccessException, MessagingException {
		//1.封装数据
		User user = new User();

		//注册自定义转换器
		//暂时不使用：注册时生日填写字符串形式
		//ConvertUtils.register(new MyConverter(),Date.class);
		BeanUtils.populate(user,request.getParameterMap());

		//设置用户id
		user.setUid(UUIDUtils.getId());
		//设置激活码
		user.setCode(UUIDUtils.getCode());
		//对密码进行加密
		user.setPassword(MD5Utils.md5(user.getPassword()));

		//2.调用Service完成注册
		IUserService userService = new UserServiceImpl();
		userService.regist(user);

		//3.请求转发
		request.setAttribute("msg","用户注册成功,激活邮件已发送");

		return "/jsp/msg.jsp";
	}

	/**
	 * 激活用户
	 * @param request
	 * @param response
	 * @return
	 */
	public String active(HttpServletRequest request, HttpServletResponse response){
		//1.获取激活码
		String code = request.getParameter("code");

		//2.调用Service，完成激活

		//3.跳转到提示页面
		return null;
	}
}
