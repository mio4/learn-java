package com.mio4.service;

import com.mio4.domain.User;

import javax.mail.MessagingException;
import java.sql.SQLException;

public interface IUserService {
	public void regist(User user) throws SQLException, MessagingException;
}
