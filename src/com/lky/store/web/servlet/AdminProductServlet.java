package com.lky.store.web.servlet;

import com.lky.store.domain.Category;
import com.lky.store.domain.PageModel;
import com.lky.store.domain.Product;
import com.lky.store.service.CategoryService;
import com.lky.store.service.ProductService;
import com.lky.store.service.serviceImpl.CategoryServiceImpl;
import com.lky.store.service.serviceImpl.ProductServiceImpl;
import com.lky.store.utils.UUIDUtils;
import com.lky.store.utils.UploadUtils;
import com.lky.store.web.base.BaseServlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

/**
 * Servlet implementation class AdminProductServlet
 */
@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {

	public String findAllProductsWithPage(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取当前页
		int curNum = Integer.parseInt(req.getParameter("num"));
		// 调用业务层获取一个pagemodel
		ProductService productService = new ProductServiceImpl();
		PageModel pageModel = productService.findAllProductsWithPage(curNum);
		// 把pm放入request
		req.setAttribute("page", pageModel);
		// 转发到admin/product/list.jsp下
		return "/admin/product/list.jsp";
	}

	public String addProductUI(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取全部分类信息
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> allCats = categoryService.getAllCats();
		// 将全部分类放入request中
		req.setAttribute("allCats", allCats);
		// 转发到添加商品的页面
		return "/admin/product/add.jsp";
	}

	public String addProduct(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// map存储表单中取出的数据
		Map<String, String> map = new HashMap<String, String>();
		// product携带表单中的数据向service dao传递数据
		Product product = new Product();
		try {
			// 利用req.getInputStream();获取到请求体中的全部数据，进行拆分和封装
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List<FileItem> list = upload.parseRequest(req);

			for (FileItem item : list) {
				if (item.isFormField()) {
					// 普通项
					map.put(item.getFieldName(), item.getString("UTF-8"));
				} else {
					// 上传项
					// 获取原始文件的名称
					String oldFileName = item.getName();
					String newFileName = UploadUtils.getUUIDName(oldFileName);
					// 获取具体的输入流对象
					InputStream is = item.getInputStream();
					// 获取存储文件夹的真实路径
					String realPath = getServletContext().getRealPath("/products/3/");
					// 获取文件的存储目录，并且将目录转存到dir中
					String dir = UploadUtils.getDir(newFileName);
					// realPath =
					// "D:/Servers/Tomcat/apache-tomcat-8.5.39/webapps/StoreV5/products/3";
					String path = realPath + dir;
					// 内存中去创建目录
					File newDir = new File(path);
					if (!newDir.exists()) {
						newDir.mkdirs();
					}
					// 在服务器端创建空文件
					File finalFile = new File(newDir, newFileName);
					if (!finalFile.exists()) {
						finalFile.createNewFile();
					}
					// 建立和空文件对应的输出流
					OutputStream os = new FileOutputStream(finalFile);
					// 流对接，将数据刷到输出流中
					IOUtils.copy(is, os);
					// 释放资源
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					// 把数据存入map
					map.put("pimage", "/products/3/" + dir + newFileName);
					System.out.println("path" + path);
				}
			}
			// 填充数据
			BeanUtils.populate(product, map);
			product.setPid(UUIDUtils.getId());
			product.setPdate(new Date());
			product.setPfllag(0);
			// 用service去存储数据
			ProductService productService = new ProductServiceImpl();
			productService.saveProduct(product);
			// http://localhost:8080/StoreV5//products/3//f/4/1/d/9/a/3/1//7F3A9EE77B9D427AB396DE6CA32CB080.jpg
			resp.sendRedirect("/StoreV5/AdminProductServlet?method=findAllProductsWithPage&num=1");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}