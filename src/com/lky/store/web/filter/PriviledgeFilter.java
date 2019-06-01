package com.lky.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.lky.store.domain.User;

/**
 * Servlet Filter implementation class PriviledgeFilter
 */
@WebFilter({ "/jsp/cart.jsp", "/jsp/order_info.jsp", "/jsp/order_list.jsp" })
public class PriviledgeFilter implements Filter {

	public PriviledgeFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 因为当前request内部不存在session的API所以转换一下
		HttpServletRequest myReq = (HttpServletRequest) request;
		User user = (User) myReq.getSession().getAttribute("loginUser");
		if (user != null) {// 存在即放行
			chain.doFilter(request, response);
		} else {
			myReq.setAttribute("msg", "请登陆之后再访问！");
			myReq.getRequestDispatcher("/jsp/info.jsp").forward(request, response);;
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
