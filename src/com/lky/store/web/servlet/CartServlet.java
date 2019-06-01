package com.lky.store.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lky.store.domain.Cart;
import com.lky.store.domain.CartItem;
import com.lky.store.domain.Product;
import com.lky.store.service.ProductService;
import com.lky.store.service.serviceImpl.ProductServiceImpl;
import com.lky.store.web.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {

	/**
	 * 把购物车项目添加进购物车
	 **/
	public String addCartItemToCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 创建一个购物车去接收存放在session中的购物车
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		if (cart == null) {// 获取不到就创建新的，并存放到session中
			cart = new Cart();
			req.getSession().setAttribute("cart", cart);
		}
		// 之后获取需要的属性
		String pid = req.getParameter("pid");
		int num = Integer.parseInt(req.getParameter("quantity"));
		// 通过pid来获取产品对象
		ProductService ps = new ProductServiceImpl();
		Product product = ps.findProductByPid(pid);
		// 根据产品和数量来创建购物车对象
		CartItem cartItem = new CartItem();
		cartItem.setNum(num);
		cartItem.setProduct(product);
		// 然后添加进购物车
		cart.addCartItemToCart(cartItem);

		resp.sendRedirect("/StoreV5/jsp/cart.jsp");
		return null;
	}

	public String clearCart(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		cart.clearCart();
		
		resp.sendRedirect("/StoreV5/jsp/cart.jsp");
		return null;
	}

	public String removeCartItem(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 获取id
		String pid = req.getParameter("pid");
		// 从购物车删除,所以购物车存在
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		cart.removeCartItem(pid);

		resp.sendRedirect("/StoreV5/jsp/cart.jsp");
		return null;
	}

}
