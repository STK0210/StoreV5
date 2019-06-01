package com.lky.store.service;

import java.sql.SQLException;
import java.util.List;

import com.lky.store.domain.Category;

public interface CategoryService {

	List<Category> getAllCats() throws SQLException;

	void addCategory(Category category) throws SQLException;

}
