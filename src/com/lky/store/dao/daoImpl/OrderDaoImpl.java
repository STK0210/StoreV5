package com.lky.store.dao.daoImpl;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.lky.store.dao.OrderDao;
import com.lky.store.domain.Order;
import com.lky.store.domain.OrderItem;
import com.lky.store.domain.Product;
import com.lky.store.domain.User;
import com.lky.store.utils.JDBCUtils;

public class OrderDaoImpl implements OrderDao {

	@Override
	public void saveOrder(Connection conn, Order order) throws Exception {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid() };
		qr.update(conn, sql, params);
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem orderItem) throws Exception {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO orderitem VALUES(?,?,?,?,?)";
		QueryRunner qr = new QueryRunner();
		Object[] params = { orderItem.getItemid(), orderItem.getQuantity(), orderItem.getTotal(),
				orderItem.getProduct().getPid(), orderItem.getOrder().getOid() };
		qr.update(conn, sql, params);
	}

	@Override
	public int getTotalRecords(User user) throws Exception {
		// TODO 获取总记录条数
		String sql = "SELECT COUNT(*) FROM orders WHERE uid = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Long totalRecords = (Long) qr.query(sql, new ScalarHandler(), user.getUid());
		return totalRecords.intValue();
	}

	@Override
	public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM orders WHERE uid = ? LIMIT ? , ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list_order = qr.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(), startIndex,
				pageSize);

		// 遍历订单集合,一个人有多个订单
		for (Order order : list_order) {
			// 获取每一个订单的id，才能获取每一个具体的订单项
			String oid = order.getOid();
			sql = "SELECT * FROM orderitem o , product p WHERE o.pid = p.pid and oid = ?";
			List<Map<String, Object>> list_orderitem = qr.query(sql, new MapListHandler(), oid);
			// 遍历list，list是所有的订单项，要把全部订单项存储到订单中
			for (Map<String, Object> map : list_orderitem) {
				OrderItem orderItem = new OrderItem();
				Product product = new Product();
				// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
				// 1_创建时间类型的转换器
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
				// 将map中的内容自动填充到product上
				BeanUtils.populate(orderItem, map);
				BeanUtils.populate(product, map);
				// 订单项与商品发生关联关系
				orderItem.setProduct(product);
				// 订单与订单项发生关联
				order.getList().add(orderItem);
			}

		}
		return list_order;
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM orders WHERE oid = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Order order = qr.query(sql, new BeanHandler<Order>(Order.class), oid);

		sql = "SELECT * FROM orderitem o,product p WHERE o.pid = p.pid AND oid = ?";
		List<Map<String, Object>> list = qr.query(sql, new MapListHandler(), oid);

		for (Map<String, Object> map : list) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			// 将map中的内容自动填充到product上
			BeanUtils.populate(orderItem, map);
			BeanUtils.populate(product, map);
			// 订单项与商品发生关联关系
			orderItem.setProduct(product);
			// 订单与订单项发生关联
			order.getList().add(orderItem);
		}

		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		// TODO Auto-generated method stub
		String sql = "UPDATE orders SET ordertime = ? , total = ? , state = ? , address = ? , name = ? , telephone = ? WHERE oid = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = { order.getOrdertime(), order.getTotal(), order.getState(), order.getAddress(),
				order.getName(), order.getTelephone(), order.getOid() };
		qr.update(sql, params);
	}

	@Override
	public List<Order> findAllOrders() throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM orders";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Order>(Order.class));
	}

	@Override
	public List<Order> findAllOrders(String state) throws Exception {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM orders WHERE state = ?";
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		return qr.query(sql, new BeanListHandler<Order>(Order.class), state);
	}

}
