package com.mio4.service.impl;

import com.mio4.dao.IUserDao;
import com.mio4.dao.impl.UserDaoImpl;
import com.mio4.domain.User;
import com.mio4.service.IUserService;
import com.mio4.utils.MailUtils;

import javax.mail.MessagingException;
import java.sql.SQLException;

public class UserServiceImpl implements IUserService {
	public void regist(User user) throws SQLException, MessagingException {
		IUserDao dao = new UserDaoImpl();
		dao.add(user);

		//发送激活邮件
		String mailMsg = "点击链接激活账号<a href='http://localhost/OnlineStore/user?method=activce&code=" + user.getCode() +  "'>点击激活</a>";
		MailUtils.sendMail(user.getEmail(),mailMsg);
	}
}
