package com.lky.store.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ServletDemo01")
public class ServletDemo01 extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取客户端提交到服务端的method对应的值
		String md = request.getParameter("method");
		// 定义变量,存放功能执行完毕之后要转发的路径
		String path = null;
		// 获取到当前字节码对象(ServletDemo02.class在内存中对象)
		Class clazz = this.getClass();
		try {
			// 获取clazz上名称为md方法
			Method method = clazz.getMethod(md, HttpServletRequest.class, HttpServletResponse.class);
			if (null != method) {
				// 调用找到的方法
				path = (String) method.invoke(this, request, response);
			}
			if (null != path) {
				// 服务端的转发
				request.getRequestDispatcher(path).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String addStu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("添加学生");
		return "/test.html";
	}

	public String delStu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("删除学生");
		return "/test.html";
	}

	public String checkStu(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("检查学生");
		response.getWriter().println("DDDDDD");
		return null;
	}

}
