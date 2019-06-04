package com.lky.store.dao.daoImpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.lky.store.dao.CategoryDao;
import com.lky.store.domain.Category;
import com.lky.store.utils.JDBCUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> getAllCats() throws SQLException {
		String sql = "select * from category";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		String sql = "INSERT INTO category VALUES( ? , ? )";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		qr.update(sql, category.getCid(), category.getCname());
	}

}
