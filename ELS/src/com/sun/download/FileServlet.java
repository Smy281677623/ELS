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
		// TODO Servlet�ļ�����
		// ���ñ����
		response.setCharacterEncoding("UTF-8");
		
		// �����Ӧ����������ļ���
		String filename = request.getParameter("filename"); // �˴������ǰ�˱���һ��
		
		// Ҫ���ص�����ļ�������-----�ͻ���ͨ���ļ���MIME����ȥ�������� ��tomcat�������Դ�������ֵ������ļ�
		response.setContentType(this.getServletContext().getMimeType(filename));
		
		//���߿ͻ��˸��ļ�����ֱ�ӽ��� �����Ը�����ʽ��(����)
		response.setHeader("Content-Disposition", "attachment;filename="+filename);
		
		//��ȡ�ļ��ľ���·��
		String path = this.getServletContext().getRealPath("download/"+filename);
		
		//����ļ�������
		FileInputStream inputStream = new FileInputStream(path);
		
		//���������
		ServletOutputStream outputStream = response.getOutputStream();
		
		// �ļ����ƴ���
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
