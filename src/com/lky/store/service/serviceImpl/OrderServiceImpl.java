package com.lky.store.service.serviceImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.lky.store.dao.OrderDao;
import com.lky.store.dao.daoImpl.OrderDaoImpl;
import com.lky.store.domain.Order;
import com.lky.store.domain.OrderItem;
import com.lky.store.domain.PageModel;
import com.lky.store.domain.User;
import com.lky.store.service.OrderService;
import com.lky.store.utils.BeanFactory;
import com.lky.store.utils.JDBCUtils;

public class OrderServiceImpl implements OrderService {

	OrderDao dao = (OrderDao) BeanFactory.createObject("OrderDao");

	@Override
	public void saveOrder(Order order) throws Exception {
		// 保存订单和订单下所有订单项

//		try {
//			JDBCUtils.startTransaction();
//			OrderDao dao = new OrderDaoImpl();
//			dao.saveOrder(order);
//			for (OrderItem orderItem : order.getList()) {
//				dao.saveOrderItem(orderItem);
//			}
//			JDBCUtils.commitAndClose();
//		} catch (Exception e) {
//			JDBCUtils.rollbackAndClose();
//			System.out.println("出现错误，事务已经回滚" + e);
//		}
		Connection conn = null;
		try {
			// 获取连接
			conn = JDBCUtils.getConnection();
			// 开启事务
			conn.setAutoCommit(false);
			// 保存订单和订单项

			dao.saveOrder(conn, order);
			for (OrderItem orderItem : order.getList()) {
				dao.saveOrderItem(conn, orderItem);
			}
			// 提交
			conn.commit();
		} catch (Exception e) {
			// 回滚
			e.printStackTrace();
			conn.rollback();
		}
//		  finally {
//			if (null != conn) {
//				conn.close();
//				conn = null;
//			}
//		}

	}

	@Override
	public PageModel findMyOrdersWithPage(User user, int curNum) throws Exception {
		// 获取总条数
		int totalRecords = dao.getTotalRecords(user);
		// 创建分页对象，携带参数和集合
		PageModel pm = new PageModel(curNum, totalRecords, 3);

		List list = dao.findMyOrdersWithPage(user, pm.getStartIndex(), pm.getPageSize());
		pm.setList(list);
		// 关联url
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");

		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		// TODO Auto-generated method stub
		return dao.findOrderByOid(oid);
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		// TODO Auto-generated method stub
		dao.updateOrder(order);
	}

	@Override
	public List<Order> findAllOrders() throws Exception {
		// TODO Auto-generated method stub
		return dao.findAllOrders();
	}

	@Override
	public List<Order> findAllOrders(String state) throws Exception {
		// TODO Auto-generated method stub
		return dao.findAllOrders(state);
	}

}
