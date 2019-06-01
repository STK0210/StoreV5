package com.lky.store.service.serviceImpl;

import java.sql.SQLException;
import java.util.List;

import com.lky.store.dao.CategoryDao;
import com.lky.store.dao.daoImpl.CategoryDaoImpl;
import com.lky.store.domain.Category;
import com.lky.store.service.CategoryService;
import com.lky.store.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class CategoryServiceImpl implements CategoryService {

	CategoryDao cd = new CategoryDaoImpl();

	@Override
	public List<Category> getAllCats() throws SQLException {

		return cd.getAllCats();
	}

	@Override
	public void addCategory(Category category) throws SQLException {
		// TODO Auto-generated method stub
		cd.addCategory(category);
		// mysql更新完之后，redis缓存也需要更新
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
		// 删除干净之后，查询不到cats,会重新更新
	}

}
