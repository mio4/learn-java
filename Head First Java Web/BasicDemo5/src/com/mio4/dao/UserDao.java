package com.mio4.dao;

import com.mio4.domain.User;
import com.mio4.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

public class UserDao {

	/**
	 *
	 * @param username
	 * @param password
	 * @return
	 */
	public User getUserByUsernameAndPwd(String username,String password) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		User user = null;
		try {
			user = qr.query(sql, new BeanHandler<>(User.class), username, password);
		} catch(Exception e){
			System.out.println("Exception at UserDao");
		}
		return user;
	}
}
