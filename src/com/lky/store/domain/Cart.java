package com.lky.store.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {
	private double total = 0;// 总计金额/积分
	private Map<String, CartItem> map = new HashMap<String, CartItem>();// 个数不确定的购物项

	// 添加购物项到购物车
	public void addCartItemToCart(CartItem cartItem) {
		String pid = cartItem.getProduct().getPid();

		if (map.containsKey(pid)) {
			CartItem oldItem = map.get(pid);
			oldItem.setNum(oldItem.getNum() + cartItem.getNum());
		} else {
			map.put(pid, cartItem);
		}
	}

	public double getTotal() {
		total = 0;
		// 获取map中所有的购物项
		Collection<CartItem> values = map.values();
		for (CartItem cartItem : values) {
			total += cartItem.getSubTotal();
		}
		return total;
	}

	// 返回map中所有的value
	public Collection<CartItem> getCartItems() {
		return map.values();
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Map<String, CartItem> getMap() {
		return map;
	}

	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}

	// 移除购物项，把指定pid的商品发送到服务端
	public void removeCartItem(String pid) {
		map.remove(pid);
	}

	// 清空购物车
	public void clearCart() {
		map.clear();
	}

}
