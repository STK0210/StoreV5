package com.lky.store.web.servlet;

import com.lky.store.domain.Category;
import com.lky.store.service.CategoryService;
import com.lky.store.service.serviceImpl.CategoryServiceImpl;
import com.lky.store.utils.JedisUtils;
import com.lky.store.web.base.BaseServlet;
import com.mysql.cj.xdevapi.JsonArray;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CategoryServlet")
public class CategoryServlet extends BaseServlet {

	public String findAllCats(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, SQLException {

		// 在redis中获取全部分类
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		if (jsonStr == null || "".equals(jsonStr)) {
			// 通过业务层获取所有目录的集合
			CategoryService cs = new CategoryServiceImpl();
			List<Category> list = cs.getAllCats();
			// 转化为json数据
			jsonStr = JSONArray.fromObject(list).toString();
			// 存入jedis缓存
			jedis.set("allCats", jsonStr);
			System.out.println("第一次,通过数据库获取并存入redis缓存");
		}
		// 返回给客户端,告诉浏览器是json格式对象
		resp.setContentType("application/json;charset=utf-8");
		resp.getWriter().print(jsonStr);

		JedisUtils.closeJedis(jedis);

		return null;// ajax不需要转发或重定向，会直接返回数据
	}
}
