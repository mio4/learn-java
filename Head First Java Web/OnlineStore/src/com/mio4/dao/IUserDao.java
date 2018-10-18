package com.mio4.dao;

import com.mio4.domain.User;

import java.sql.SQLException;

public interface IUserDao{
	public void add(User user) throws SQLException;
}
