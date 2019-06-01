package com.lky.store.test;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UpLoad
 */
@WebServlet("/UpLoad")
@MultipartConfig
public class UpLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UpLoad() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 说明输入的请求信息采用UTF-8编码方式
		request.setCharacterEncoding("utf-8");
		// Servlet3.0中新引入的方法，用来处理multipart/form-data类型编码的表单
		Part part = request.getPart("file");
		// 获取HTTP头信息
		String headerInfo = part.getHeader("content-disposition");
		// 从HTTP头信息中获取文件名
		String fileName = headerInfo.substring(headerInfo.lastIndexOf("filename") + 10, headerInfo.length() - 1);
		// 获得存储上传文件的文件夹路径
		String fileSavingFolder = this.getServletContext().getRealPath("/UpLoad_3.0");
		// 获得存储上传文件的完整路径（文件夹路径+文件名），文件夹位置固定，文件名采用与上传文件的原始名字相同）
		String fileSavingPath = fileSavingFolder + File.separator + fileName;
		// 如果存储上传文件的文件夹不存在，则创建文件夹
		File f = new File(fileSavingFolder + File.separator);
		if (!f.exists()) {
			f.mkdirs();
			System.out.println("文件夹第一次创建");
		}
		// 将上传的文件内容写入服务器文件中
		System.out.println(fileSavingPath);
		part.write(fileSavingPath);
		// 输出上传成功信息
		OutPut.outputToClient("文件已上传到：" + fileSavingPath, response);
	}

}
