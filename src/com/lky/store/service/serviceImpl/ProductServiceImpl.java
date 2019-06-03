package com.lky.store.service.serviceImpl;

import java.util.List;

import com.lky.store.dao.ProductDao;
import com.lky.store.dao.daoImpl.ProductDaoImpl;
import com.lky.store.domain.PageModel;
import com.lky.store.domain.Product;
import com.lky.store.service.ProductService;

public class ProductServiceImpl implements ProductService {

	ProductDao dao = new ProductDaoImpl();

	@Override
	public List<Product> findNews() throws Exception {
		// TODO Auto-generated method stub
		return dao.findNews();
	}

	@Override
	public List<Product> findHots() throws Exception {
		// TODO Auto-generated method stub
		return dao.findHots();
	}

	@Override
	public Product findProductByPid(String pid) throws Exception {
		// TODO Auto-generated method stub
		return dao.findProductByPid(pid);
	}

	@Override
	public PageModel findProductsByCidWithPage(String cid, int curNum) throws Exception {
		// TODO Auto-generated method stub
		// 创建对象，计算参数
		int totalRecords = dao.findTotalRecords(cid);
		PageModel pageModel = new PageModel(curNum, totalRecords, 12);
		// 分页语句
		List<Product> list = dao.findProductsByCidWithPage(cid, pageModel.getStartIndex(), pageModel.getPageSize());
		pageModel.setList(list);

		pageModel.setUrl("ProductServlet?method=findProductsByCidWithPage&cid=" + cid);
		return pageModel;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) throws Exception {
		// TODO Auto-generated method stub
		// 创建pm对象
		int totalRecords = dao.findTotalRecords();
		PageModel pageModel = new PageModel(curNum, totalRecords, 5);
		// 关联集合
		List<Product> list = dao.findAllProductsWithPage(pageModel.getStartIndex(), pageModel.getPageSize());
		pageModel.setList(list);
		// 关联url
		pageModel.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pageModel;
	}

	@Override
	public void saveProduct(Product product) throws Exception {
		// TODO Auto-generated method stub
		dao.saveProduct(product);
	}

}
