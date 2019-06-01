package com.lky.store.service;

import java.sql.SQLException;

import com.lky.store.domain.User;

public interface UserService {

	void userRegist(User user) throws SQLException;

	boolean userActive(String code) throws SQLException;

	User userLogin(User tempuser) throws SQLException;

}
