package com.lky.store.dao;

import java.util.List;

import com.lky.store.domain.Product;

public interface ProductDao {

	List<Product> findNews() throws Exception;

	List<Product> findHots() throws Exception;

	Product findProductByPid(String pid) throws Exception;

	int findTotalRecords(String cid) throws Exception;

	List findProductsByCidWithPage(String cid, int startIndex, int pageSize) throws Exception;

	int findTotalRecords() throws Exception;

	List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception;

	void saveProduct(Product product) throws Exception;

}
