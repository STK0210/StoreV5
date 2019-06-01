package com.lky.store.web.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lky.store.domain.Category;
import com.lky.store.domain.Product;
import com.lky.store.service.CategoryService;
import com.lky.store.service.ProductService;
import com.lky.store.service.serviceImpl.CategoryServiceImpl;
import com.lky.store.service.serviceImpl.ProductServiceImpl;
import com.lky.store.web.base.BaseServlet;

@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
//		// 通过业务层获取所有目录的集合
//		CategoryService cs = new CategoryServiceImpl();
//		List<Category> list = cs.getAllCats();
//		// 在request中存储list
//		req.setAttribute("allCats", list);

		// 调用业务层，查询最新和最热商品
		ProductService ps = new ProductServiceImpl();
		List<Product> list_new = ps.findNews();
		List<Product> list_hot = ps.findHots();
		// 放到request,转到首页
		req.setAttribute("news", list_new);
		req.setAttribute("hots", list_hot);
		// 返回首页
		return "/jsp/index.jsp";
	}

}
