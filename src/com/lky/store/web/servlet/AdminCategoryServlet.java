package com.lky.store.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lky.store.domain.Category;
import com.lky.store.service.CategoryService;
import com.lky.store.service.serviceImpl.CategoryServiceImpl;
import com.lky.store.utils.UUIDUtils;
import com.lky.store.web.base.BaseServlet;

/**
 * Servlet implementation class AdminCategoryServlet
 */
@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {

	public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取全部分类信息
		CategoryService cs = new CategoryServiceImpl();
		List<Category> list = cs.getAllCats();
		// 存放所有信息
		req.setAttribute("allCats", list);
		// 转发页面
		return "/admin/category/list.jsp";
	}

	public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		return "/admin/category/add.jsp";
	}

	public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取分类名称及id
		String cname = req.getParameter("cname");
		String cid = UUIDUtils.getId();
		Category category = new Category();
		category.setCid(cid);
		category.setCname(cname);
		// 调用业务层功能
		CategoryService cs = new CategoryServiceImpl();
		cs.addCategory(category);
		// 重定向到查询全部分类信息
		resp.sendRedirect("/StoreV5/AdminCategoryServlet?method=findAllCats");
		return null;
	}

}
