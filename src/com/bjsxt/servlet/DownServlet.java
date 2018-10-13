package com.bjsxt.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.bjsxt.entity.Student;
import com.bjsxt.service.StudentService;
import com.bjsxt.service.impl.StudentServiceImpl;

public class DownServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//获取学生信息
		String sid = request.getParameter("id");
		int id = 0;
		//创建业务层对象
		StudentService ss = new StudentServiceImpl();
		try{
			id = Integer.parseInt(sid);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return;
		}
		
		//调用业务层对象获取该学生的所有信息
		Student stu = ss.findById(id);
		//通过IO流实现照片的下载（从服务器端到客户端）
		//1、创建流
		String path = this.getServletContext().getRealPath("/upload")+"/"+stu.getPhotoName();
		File file = new File(path);
		String realName = stu.getRealName();
		
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		
		if(userAgent.indexOf("mozilla")>=0){//如果是IE
			realName = URLEncoder.encode(realName,"utf-8");
		}else{//其他
			realName = new String(realName.getBytes("utf-8"), "iso-8859-1");
		}
		
		
		
		response.setContentLength((int)file.length());
		response.setContentType(stu.getPhotoType());
		response.setHeader("Content-disposition","attachment;filename="+realName);
		
		InputStream is = new FileInputStream(file);//读取服务器端的文件

		OutputStream os = response.getOutputStream();//写入客户端的电脑
		
		//2、复制操作，从服务器端到客户端,IOUtils,apache下的commons.io中的工具类
		IOUtils.copy(is, os);
		//3、关闭流
		os.close();
		is.close();
	}
}
