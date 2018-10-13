package com.bjsxt.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.bjsxt.entity.Student;
import com.bjsxt.service.StudentService;
import com.bjsxt.service.impl.StudentServiceImpl;

public class AddServlet2 extends HttpServlet {


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
		 	
		 	//限制上传文件大小应该放在获取数据之前
		 	upload.setFileSizeMax(16*1024);//单个文件上传不能超过16kb
		 	upload.setSizeMax(5*16*1024);//所有文件上传不能超过80Kkb
		 	
		 	//3、通过ServletFileUpload对象实现上传操作
		 	List<FileItem> list = null ;
		 	try {
		 		list = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
				request.setAttribute("mess", "文件不能超过16kb");
		    	request.getRequestDispatcher("/add.jsp").forward(request, response);
				return;
			}
		 	
		 	String name = null;
		 	int age = 0;
		 	double score = 0;
		 	String realName = null;
		 	String photoName = null;
		 	String photoType = null;
		 	//4、遍历各个FileItem，相当于对各个表单进行处理
		 	for (FileItem fileItem : list) {
		 		String fileName = fileItem.getFieldName();
				if(fileItem.isFormField()){//true证明为正常表单项
					//name
					if("name".equals(fileName)){//获取中文字符使用utf-8
						name = fileItem.getString("utf-8");
					}
					//age
					else if("age".equals(fileName)){
						age = Integer.parseInt(fileItem.getString());
					}
					//score
					else if("score".equals(fileName)){
						score = Double.parseDouble(fileItem.getString());
					}
				}else{//false为文件表单项
					//photo
					if("photo".equals(fileName)){
						realName = fileItem.getName();
						//限制上传文件的大小，限制文件上传大小放在此处不适合，因为已经接收到数据了，应该先判断文件大小，大小通过再接收数据
//						long size = fileItem.getSize();
//						if(size>16*1024){
//							request.setAttribute("mess", "文件不能超过16kb");
//					    	request.getRequestDispatcher("/add.jsp").forward(request, response);
//							return;
//						}
						
						//只获取指定的文件类型
						photoType = fileItem.getContentType();
						if(!"image/gif".equals(photoType)&&!"image/png".equals(photoType)&&!"image/jpeg".equals(photoType)){
							request.setAttribute("mess", "只能上传gif,png,gif!");
					    	request.getRequestDispatcher("/add.jsp").forward(request, response);
							return;
						}
						//指定长传的文件夹
						//File dir = new File("D:\\apache-tomcat-7.0.90\\webapps\\updownload1\\upload");
						//使用物理路径，不灵活，改为真实路径
						//realPath为服务器存放地址，当该项目移除服务器时，以前添加的图片将找不到
						String realPath = this.getServletContext().getRealPath("/upload");
						System.out.println(realPath);
						File dir = new File(realPath);
						if(!dir.exists()){//假如文件不存在，创建文件
							dir.mkdirs();
						}
						//指定上传的文件名
						String pName = fileItem.getName();
						//为了防止文件被覆盖，上传到服务器端的文件要重新命名，使用UUID
						UUID id = UUID.randomUUID();
						//获取文件的类型
						String FileType = pName.substring(pName.lastIndexOf("."));
						System.out.println("FileType:"+FileType);
						photoName = id.toString()+FileType;
						//指定长传的文件夹和文件名
						File file = new File(dir,photoName);
						
						//上传该照片到指定位置
						try {
							fileItem.write(file);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		 	
		 	System.out.println("realName:"+realName);
		 	System.out.println("photoName:"+photoName);
		 	System.out.println("photoType:"+photoType);
		 	Student stu = new Student(name, age, score, realName, photoName, photoType);
		 	
		 	//调用业务层处理
		 	StudentService ss = new StudentServiceImpl();
		 	int n = ss.save(stu);
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

