package com.lky.store.dao;

import java.sql.Connection;
import java.util.List;

import com.lky.store.domain.Order;
import com.lky.store.domain.OrderItem;
import com.lky.store.domain.User;

public interface OrderDao {

	void saveOrder(Connection conn, Order order) throws Exception;

	void saveOrderItem(Connection conn, OrderItem orderItem) throws Exception;

	int getTotalRecords(User user) throws Exception;

	List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception;

	Order findOrderByOid(String oid) throws Exception;

	void updateOrder(Order order) throws Exception;

}
