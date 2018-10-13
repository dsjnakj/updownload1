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
		//��ȡѧ����Ϣ
		String sid = request.getParameter("id");
		int id = 0;
		//����ҵ������
		StudentService ss = new StudentServiceImpl();
		try{
			id = Integer.parseInt(sid);
		}catch(NumberFormatException e){
			e.printStackTrace();
			return;
		}
		
		//����ҵ�������ȡ��ѧ����������Ϣ
		Student stu = ss.findById(id);
		//ͨ��IO��ʵ����Ƭ�����أ��ӷ������˵��ͻ��ˣ�
		//1��������
		String path = this.getServletContext().getRealPath("/upload")+"/"+stu.getPhotoName();
		File file = new File(path);
		String realName = stu.getRealName();
		
		String userAgent = request.getHeader("User-Agent").toLowerCase();
		
		if(userAgent.indexOf("mozilla")>=0){//�����IE
			realName = URLEncoder.encode(realName,"utf-8");
		}else{//����
			realName = new String(realName.getBytes("utf-8"), "iso-8859-1");
		}
		
		
		
		response.setContentLength((int)file.length());
		response.setContentType(stu.getPhotoType());
		response.setHeader("Content-disposition","attachment;filename="+realName);
		
		InputStream is = new FileInputStream(file);//��ȡ�������˵��ļ�

		OutputStream os = response.getOutputStream();//д��ͻ��˵ĵ���
		
		//2�����Ʋ������ӷ������˵��ͻ���,IOUtils,apache�µ�commons.io�еĹ�����
		IOUtils.copy(is, os);
		//3���ر���
		os.close();
		is.close();
	}
}
