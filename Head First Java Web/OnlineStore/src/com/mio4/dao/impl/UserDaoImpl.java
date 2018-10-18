package com.mio4.dao.impl;

import com.mio4.dao.IUserDao;
import com.mio4.domain.User;
import com.mio4.utils.DataSourceUtils;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.SQLException;

/**
 * CREATE TABLE `user` (
 *   `uid` varchar(32) NOT NULL,
 *   `username` varchar(20) DEFAULT NULL,
 *   `password` varchar(100) DEFAULT NULL,
 *   `name` varchar(20) DEFAULT NULL,
 *   `email` varchar(30) DEFAULT NULL,
 *   `telephone` varchar(20) DEFAULT NULL,
 *   `birthday` varchar(20) DEFAULT NULL,
 *   `sex` varchar(10) DEFAULT NULL,
 *   `state` int(11) DEFAULT NULL,
 *   `code` varchar(64) DEFAULT NULL,
 *   PRIMARY KEY (`uid`)
 * ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
 */
public class UserDaoImpl implements IUserDao {

	/**
	 * 注册新用户
	 * @param user
	 */
	@Override
	public void add(User user) throws SQLException {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		qr.update(sql,user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),user.getCode()
		);

	}
}
