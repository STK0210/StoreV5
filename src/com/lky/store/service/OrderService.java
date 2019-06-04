package com.lky.store.service;

import java.util.List;

import com.lky.store.domain.Order;
import com.lky.store.domain.PageModel;
import com.lky.store.domain.User;

public interface OrderService {

	void saveOrder(Order order) throws Exception;

	PageModel findMyOrdersWithPage(User user, int curNum) throws Exception;

	Order findOrderByOid(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

	List<Order> findAllOrders() throws Exception;

	List<Order> findAllOrders(String state) throws Exception;
}
