package com.lky.store.web.servlet;

import com.lky.store.domain.PageModel;
import com.lky.store.domain.Product;
import com.lky.store.service.ProductService;
import com.lky.store.service.serviceImpl.ProductServiceImpl;
import com.lky.store.web.base.BaseServlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AdminProductServlet
 */
@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {

	public String findAllProductsWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取当前页
		int curNum = Integer.parseInt(req.getParameter("num"));
		// 调用业务层获取一个pagemodel
		ProductService productService = new ProductServiceImpl();
		PageModel pageModel = productService.findAllProductsWithPage(curNum);
		// 把pm放入request
		req.setAttribute("page", pageModel);
		// 转发到admin/product/list.jsp下
		return "/admin/product/list.jsp";
	}
}
