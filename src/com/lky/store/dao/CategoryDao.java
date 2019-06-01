package com.lky.store.dao;

import java.sql.SQLException;
import java.util.List;

import com.lky.store.domain.Category;

public interface CategoryDao {

	List<Category> getAllCats() throws SQLException;

	void addCategory(Category category) throws SQLException;

}
