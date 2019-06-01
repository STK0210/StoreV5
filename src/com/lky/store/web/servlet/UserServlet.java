package com.lky.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lky.store.domain.Category;
import com.lky.store.domain.User;
import com.lky.store.service.CategoryService;
import com.lky.store.service.UserService;
import com.lky.store.service.serviceImpl.CategoryServiceImpl;
import com.lky.store.service.serviceImpl.UserServiceImpl;
import com.lky.store.utils.CookUtils;
import com.lky.store.utils.MailUtils;
import com.lky.store.utils.MyBeanUtils;
import com.lky.store.utils.UUIDUtils;
import com.lky.store.web.base.BaseServlet;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {

	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		return "/jsp/register.jsp";
	}

	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 通过业务层获取所有目录的集合
		CategoryService cs = new CategoryServiceImpl();
		List<Category> list = cs.getAllCats();
		// 在request中存储list
		request.setAttribute("allCats", list);

		Cookie[] cookies = request.getCookies();
		Cookie cookie = CookUtils.getCookieByName("storeuser", cookies);

		if (cookie != null) {// 存在cookie
			String str = cookie.getValue();
			String[] strs = str.split("#");
			UserService userService = new UserServiceImpl();
			User tempuser = new User(strs[0], strs[1]);

			User user = userService.userLogin(tempuser);

			request.getSession().setAttribute("loginUser", user);
			response.sendRedirect("/StoreV5/index.jsp");
			return null;
		}
		return "/jsp/login.jsp";
	}

	public String logOut(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 清除session
		request.getSession().invalidate();
		// 重定向到首页
		response.sendRedirect("/StoreV5/index.jsp");
		return null;
	}

	public String userLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		User tempuser = new User();
		MyBeanUtils.populate(tempuser, request.getParameterMap());

		UserService userService = new UserServiceImpl();
		User user = null;
		try {
			user = userService.userLogin(tempuser);

			if (user != null) {// 查询回来的不为空，user真实存在，存入cookie
				Cookie newCookie = new Cookie("storeuser", user.getUsername() + "#" + user.getPassword());
				newCookie.setMaxAge(60 * 60);
				response.addCookie(newCookie);
			}

			request.getSession().setAttribute("loginUser", user);
			response.sendRedirect("/StoreV5/index.jsp");
			return null;
		} catch (Exception e) {
			// 登陆失败
			String msg = e.getMessage();
			System.out.println(msg);
			// 向request放入失败的信息
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
	}

	public String userRegist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, IllegalAccessException, InvocationTargetException {
		// 获取表单参数
		Map<String, String[]> map = request.getParameterMap();

		User user = new User();
		MyBeanUtils.populate(user, map);

		// 为用户的其他属性赋值
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());

//		for (Entry<String, String[]> entry : map.entrySet()) {
//			System.out.print("key= " + entry.getKey() + " and value= ");
//			String[] values = entry.getValue();
//			for (String value : values) {
//				System.out.print(value+"  ");
//			}
//			System.out.println();
//		}
		System.out.println(user);

		UserService userService = new UserServiceImpl();
		try {
			userService.userRegist(user);
			// 注册成功，发送邮件
			MailUtils.sendMail(user.getEmail(), user.getCode());

			request.setAttribute("msg", "用户注册成功，请激活！");
		} catch (Exception e) {
			// 注册失败
			request.setAttribute("msg", "用户注册失败，请重新注册！");

		}
		return "/jsp/info.jsp";
	}

	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		String code = request.getParameter("code");

		UserService userService = new UserServiceImpl();
		boolean flag = userService.userActive(code);

		if (flag == true) {
			request.setAttribute("msg", "用户激活成功，请登录！");
			return "/jsp/login.jsp";
		} else {
			request.setAttribute("msg", "用户激活失败，请重新激活！");
			return "/jsp/info.jsp";
		}
	}

}
