package com.bjsxt.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class AddServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
		
	}

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 	request.setCharacterEncoding("utf-8");
		 	//1������������FileItemFactory
		 	FileItemFactory factory = new DiskFileItemFactory();
		 	
		 	//2������ServletFileUpload����
		 	ServletFileUpload upload = new ServletFileUpload(factory);
		 	
		 	//3��ͨ��ServletFileUpload����ʵ���ϴ�����
		 	List<FileItem> list = null ;
		 	try {
		 		list = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		 	
		 	//4����������FileItem���൱�ڶԸ��������д���
		 	for (FileItem fileItem : list) {
				System.out.println(fileItem.isFormField());//�ж��Ƿ����ļ�����
				System.out.println(fileItem.getContentType());//��ȡ�ļ����͵ĸ�ʽ
				System.out.println(fileItem.getFieldName());//��ȡ����
				System.out.println(fileItem.getName());//��ȡ�ļ�����
				System.out.println(fileItem.getSize());//��ȡ�ֽڴ�С
				fileItem.getString("utf-8");//�����file������������
				System.out.println("===========");
			}
		 	
		    int n = 1;
		    //ҳ����ת
		    if(n!=0){
		    	//�ض���:/����Ҫ��������·��  /stumgr   /stumgr2
		    	response.sendRedirect(request.getContextPath()+"/servlet/ShowAllServlet");
		    }else{
		    	request.setAttribute("mess", "���ʧ��!");
		    	request.getRequestDispatcher("/add.jsp").forward(request, response);
		    }
	
	}

}

