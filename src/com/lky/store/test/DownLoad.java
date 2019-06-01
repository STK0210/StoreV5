package com.lky.store.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownLoad
 */
@WebServlet("/DownLoad")
public class DownLoad extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownLoad() {
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
		try {
			String filepath = "/WEB-INF/web.xml";// 服务器上文件的相对路径
			// 服务器上文件的绝对路径
			String fullFilePath = getServletContext().getRealPath(filepath);
			File file = new File(fullFilePath);// 打开文件，创建File类型的文件对象
			if (file.exists()) {// 如果文件存在
				System.out.println("文件存在");
				// 获得文件名，并采用UTF-8编码方式进行编码，以解决中文问题
				String filename = URLEncoder.encode(file.getName(), "UTF-8");
				response.reset();// 重置response对象
				// 设置文件的类型，xml文件采用text/xml类型，详见MIME类型说明
				response.setContentType("text/xml");
				// 设置HTTP头信息中内容
				response.addHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
				int fileLength = (int) file.length();// 设置文件长度
				System.out.println(fileLength);
				response.setContentLength(fileLength);
				if (fileLength != 0) {// 如果文件长度大于0
					InputStream inStream = new FileInputStream(file);// 创建输入流
					byte[] buf = new byte[4096];
					// 创建输出流
					ServletOutputStream servletOS = response.getOutputStream();
					int readLength;
					// 读取文件内容并写入到response的输出流当中
					while (((readLength = inStream.read(buf)) != -1)) {
						servletOS.write(buf, 0, readLength);
					}
					inStream.close();// 关闭输入流
					servletOS.flush();// 刷新输出缓冲
					servletOS.close(); // 关闭输出流
				}
			} else {
				System.out.println("文件不存在");
				PrintWriter out = response.getWriter();
				out.println("文件 \"" + fullFilePath + "\" 不存在");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}
