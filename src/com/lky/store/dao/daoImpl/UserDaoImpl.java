package com.lky.store.dao.daoImpl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.lky.store.dao.UserDao;
import com.lky.store.domain.User;
import com.lky.store.utils.JDBCUtils;

public class UserDaoImpl implements UserDao {

	@Override
	public void userRegist(User user) throws SQLException {
		// 注册实现,不成功会抛出错误
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { user.getUid(), user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode() };
		qr.update(sql, params);
	}

	@Override
	public User userActive(String code) throws SQLException {

		String sql = "select * from user where code = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User user = qr.query(sql, new BeanHandler<User>(User.class), code);
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException {

		String sql = "update user set username = ? , password = ? , name = ? , email = ? , telephone = ? ,birthday = ? , sex = ? , state = ? , code = ? where uid = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { user.getUsername(), user.getPassword(), user.getName(), user.getEmail(),
				user.getTelephone(), user.getBirthday(), user.getSex(), user.getState(), user.getCode(),
				user.getUid() };
		qr.update(sql, params);
	}

	@Override
	public User userLogin(User tempuser) throws SQLException {

		String sql = "select * from user where username = ? and password = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		User user = qr.query(sql, new BeanHandler<User>(User.class), tempuser.getUsername(), tempuser.getPassword());
		return user;
	}

}
