package com.sun.download;

import java.io.FileInputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FileServlet
 */
@WebServlet("/FileServlet")
public class FileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Servlet文件下载
		// 设置编码表
		response.setCharacterEncoding("UTF-8");
		
		// 获得响应传输过来的文件名
		String filename = request.getParameter("filename"); // 此处必须和前端保持一致
		
		// 要下载的这个文件的类型-----客户端通过文件的MIME类型去区分类型 在tomcat里面有自带如果区分的配置文件
		response.setContentType(this.getServletContext().getMimeType(filename));
		
		//告诉客户端该文件不是直接解析 而是以附件形式打开(下载)
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		
		//获取文件的绝对路径
		String path = this.getServletContext().getRealPath("download/"+filename);
		
		//获得文件输入流
		FileInputStream inputStream = new FileInputStream(path);
		
		//获得下载流
		ServletOutputStream outputStream = response.getOutputStream();
		
		// 文件复制代码
		try {
			int len = 0;
			byte [] buffer = new byte [1024];
			while((len=inputStream.read())>0) {
				outputStream.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			outputStream.close();
			inputStream.close();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
