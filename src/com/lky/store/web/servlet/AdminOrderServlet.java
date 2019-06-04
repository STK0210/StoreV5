package com.lky.store.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lky.store.domain.Order;
import com.lky.store.service.OrderService;
import com.lky.store.service.serviceImpl.OrderServiceImpl;
import com.lky.store.web.base.BaseServlet;

/**
 * Servlet implementation class AdminCategoryServlet
 */
@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {

	public String findAllOrders(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取全部订单并存入request中
		OrderService os = new OrderServiceImpl();
		String state = req.getParameter("state");
		List<Order> orders = null;
		if (null == state || "".equals(state)) {
			orders = os.findAllOrders();
		} else {
			orders = os.findAllOrders(state);
		}
		// 转发到list.jsp
		req.setAttribute("allOrders", orders);
		return "/admin/order/list.jsp";
	}
}
