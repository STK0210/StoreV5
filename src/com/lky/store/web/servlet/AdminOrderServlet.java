package com.lky.store.web.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lky.store.domain.Order;
import com.lky.store.service.OrderService;
import com.lky.store.service.serviceImpl.OrderServiceImpl;
import com.lky.store.web.base.BaseServlet;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class AdminCategoryServlet
 */
@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {

	public String updateOrderByOid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取订单id查询订单
		String oid = req.getParameter("oid");
		OrderService os = new OrderServiceImpl();
		Order order = os.findOrderByOid(oid);
		// 更改状态
		order.setState(3);
		os.updateOrder(order);
		// 重定向
		resp.sendRedirect("/StoreV5/AdminOrderServlet?method=findAllOrders&state=3");
		return null;
	}

	public String findOrderByOidWithAjax(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		String oid = req.getParameter("id");
		OrderService os = new OrderServiceImpl();
		Order order = os.findOrderByOid(oid);
		// 转化为json格式字符串，响应到客户端
		String jsonStr = JSONArray.fromObject(order.getList()).toString();
		resp.setContentType("application/json;charset=UTF-8");
		resp.getWriter().println(jsonStr);
		return null;
	}

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
