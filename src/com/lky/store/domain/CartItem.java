package com.lky.store.domain;

public class CartItem {
	private Product product;// 商品的参数
	private int num;// 商品数量
	private double subTotal;// 小计

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public double getSubTotal() {
		return product.getShop_price() * num;
	}

	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}

}
