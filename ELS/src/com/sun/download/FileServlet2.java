package com.sun.download;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.BASE64Encoder;

/**
 * Servlet implementation class FileServlet2
 */
@WebServlet("/FileServlet2")
public class FileServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileServlet2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//*******�ļ����������ĵ�����*******


				//���Ҫ���ص��ļ�������
				String filename = request.getParameter("filename");//????.jpg
				//���������Ĳ���������----�½ڿν�
				filename = new String(filename.getBytes("UTF-8"),"UTF-8");//��Ů.jpg

				
				//�������ͷ�е�User-Agent
				String agent = request.getHeader("User-Agent");
				//���ݲ�ͬ��������в�ͬ�ı���
				String filenameEncoder = "";
				if (agent.contains("MSIE")) {
					// IE�����
					filenameEncoder = URLEncoder.encode(filename, "utf-8");
					filenameEncoder = filenameEncoder.replace("+", " ");
				} else if (agent.contains("Firefox")) {
					// ��������
					BASE64Encoder base64Encoder = new BASE64Encoder();
					filenameEncoder = "=?utf-8?B?"
							+ base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
				} else {
					// ���������
					filenameEncoder = URLEncoder.encode(filename, "utf-8");				
				}



				//Ҫ���ص�����ļ�������-----�ͻ���ͨ���ļ���MIME����ȥ��������
				response.setContentType(this.getServletContext().getMimeType(filename));
				//���߿ͻ��˸��ļ�����ֱ�ӽ��� �����Ը�����ʽ��(����)----filename="+filename �ͻ���Ĭ�϶����ֽ��н���
				response.setHeader("Content-Disposition", "attachment;filename="+filenameEncoder);
				
				System.out.println(filename);
				//��ȡ�ļ��ľ���·��
				String path = this.getServletContext().getRealPath("download/"+filename);
				//��ø��ļ���������
				InputStream in = new FileInputStream(path);
				//��������---ͨ��response��õ������ ������ͻ���д����
				ServletOutputStream out = response.getOutputStream();
				//�ļ�������ģ�����
				int len = 0;
				byte[] buffer = new byte[1024];
				while((len=in.read(buffer))>0){
					out.write(buffer, 0, len);
				}

				in.close();
				out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
