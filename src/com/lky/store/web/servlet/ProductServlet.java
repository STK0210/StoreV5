package com.lky.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lky.store.domain.PageModel;
import com.lky.store.domain.Product;
import com.lky.store.service.ProductService;
import com.lky.store.service.serviceImpl.ProductServiceImpl;
import com.lky.store.web.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {

	public String findProductByPid(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String pid = req.getParameter("pid");

		ProductService ps = new ProductServiceImpl();
		Product product = ps.findProductByPid(pid);

		req.setAttribute("product", product);

		return "/jsp/product_info.jsp";
	}

	public String findProductsByCidWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String cid = req.getParameter("cid");
		int curNum = Integer.parseInt(req.getParameter("num"));

		ProductService ps = new ProductServiceImpl();
		PageModel pageModel = ps.findProductsByCidWithPage(cid, curNum);

		req.setAttribute("page", pageModel);

		return "/jsp/product_list.jsp";
	}

}
