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
		 	//1、创建工厂类FileItemFactory
		 	FileItemFactory factory = new DiskFileItemFactory();
		 	
		 	//2、创建ServletFileUpload对象
		 	ServletFileUpload upload = new ServletFileUpload(factory);
		 	
		 	//3、通过ServletFileUpload对象实现上传操作
		 	List<FileItem> list = null ;
		 	try {
		 		list = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		 	
		 	//4、遍历各个FileItem，相当于对各个表单进行处理
		 	for (FileItem fileItem : list) {
				System.out.println(fileItem.isFormField());//判断是否是文件类型
				System.out.println(fileItem.getContentType());//获取文件类型的格式
				System.out.println(fileItem.getFieldName());//获取名称
				System.out.println(fileItem.getName());//获取文件名称
				System.out.println(fileItem.getSize());//获取字节大小
				fileItem.getString("utf-8");//解决非file表单项乱码问题
				System.out.println("===========");
			}
		 	
		    int n = 1;
		    //页面跳转
		    if(n!=0){
		    	//重定向:/后面要跟上下文路径  /stumgr   /stumgr2
		    	response.sendRedirect(request.getContextPath()+"/servlet/ShowAllServlet");
		    }else{
		    	request.setAttribute("mess", "添加失败!");
		    	request.getRequestDispatcher("/add.jsp").forward(request, response);
		    }
	
	}

}

