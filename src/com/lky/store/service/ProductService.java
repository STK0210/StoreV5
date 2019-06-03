package com.lky.store.service;

import java.util.List;

import com.lky.store.domain.PageModel;
import com.lky.store.domain.Product;

public interface ProductService {

	List<Product> findNews() throws Exception;

	List<Product> findHots() throws Exception;

	Product findProductByPid(String pid) throws Exception;

	PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception;

	PageModel findAllProductsWithPage(int curNum) throws Exception;

	void saveProduct(Product product) throws Exception;

}
