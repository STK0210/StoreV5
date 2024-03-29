package com.lky.store.service.serviceImpl;

import java.sql.SQLException;

import com.lky.store.dao.UserDao;
import com.lky.store.dao.daoImpl.UserDaoImpl;
import com.lky.store.domain.User;
import com.lky.store.service.UserService;
import com.lky.store.utils.BeanFactory;

public class UserServiceImpl implements UserService {

	UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");

	@Override
	public void userRegist(User user) throws SQLException {
		// 实现注册
		UserDao userDao = new UserDaoImpl();
		userDao.userRegist(user);
	}

	@Override
	public boolean userActive(String code) throws SQLException {
		// 激活
		User user = userDao.userActive(code);
		if (user != null) {
			user.setState(1);
			user.setCode(null);

			userDao.updateUser(user);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User userLogin(User tempuser) throws SQLException {
		// 异常在模块间传递数据
		User user = userDao.userLogin(tempuser);
		if (user == null) {
			throw new RuntimeException("密码有误！");
		} else if (user.getState() == 0) {
			throw new RuntimeException("用户未激活！");
		} else {
			return user;
		}
	}

}
