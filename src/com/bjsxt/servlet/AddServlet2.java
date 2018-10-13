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
		 	//1������������FileItemFactory
		 	FileItemFactory factory = new DiskFileItemFactory();
		 	
		 	//2������ServletFileUpload����
		 	ServletFileUpload upload = new ServletFileUpload(factory);
		 	
		 	//�����ϴ��ļ���СӦ�÷��ڻ�ȡ����֮ǰ
		 	upload.setFileSizeMax(16*1024);//�����ļ��ϴ����ܳ���16kb
		 	upload.setSizeMax(5*16*1024);//�����ļ��ϴ����ܳ���80Kkb
		 	
		 	//3��ͨ��ServletFileUpload����ʵ���ϴ�����
		 	List<FileItem> list = null ;
		 	try {
		 		list = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
				request.setAttribute("mess", "�ļ����ܳ���16kb");
		    	request.getRequestDispatcher("/add.jsp").forward(request, response);
				return;
			}
		 	
		 	String name = null;
		 	int age = 0;
		 	double score = 0;
		 	String realName = null;
		 	String photoName = null;
		 	String photoType = null;
		 	//4����������FileItem���൱�ڶԸ��������д���
		 	for (FileItem fileItem : list) {
		 		String fileName = fileItem.getFieldName();
				if(fileItem.isFormField()){//true֤��Ϊ��������
					//name
					if("name".equals(fileName)){//��ȡ�����ַ�ʹ��utf-8
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
				}else{//falseΪ�ļ�����
					//photo
					if("photo".equals(fileName)){
						realName = fileItem.getName();
						//�����ϴ��ļ��Ĵ�С�������ļ��ϴ���С���ڴ˴����ʺϣ���Ϊ�Ѿ����յ������ˣ�Ӧ�����ж��ļ���С����Сͨ���ٽ�������
//						long size = fileItem.getSize();
//						if(size>16*1024){
//							request.setAttribute("mess", "�ļ����ܳ���16kb");
//					    	request.getRequestDispatcher("/add.jsp").forward(request, response);
//							return;
//						}
						
						//ֻ��ȡָ�����ļ�����
						photoType = fileItem.getContentType();
						if(!"image/gif".equals(photoType)&&!"image/png".equals(photoType)&&!"image/jpeg".equals(photoType)){
							request.setAttribute("mess", "ֻ���ϴ�gif,png,gif!");
					    	request.getRequestDispatcher("/add.jsp").forward(request, response);
							return;
						}
						//ָ���������ļ���
						//File dir = new File("D:\\apache-tomcat-7.0.90\\webapps\\updownload1\\upload");
						//ʹ������·����������Ϊ��ʵ·��
						//realPathΪ��������ŵ�ַ��������Ŀ�Ƴ�������ʱ����ǰ��ӵ�ͼƬ���Ҳ���
						String realPath = this.getServletContext().getRealPath("/upload");
						System.out.println(realPath);
						File dir = new File(realPath);
						if(!dir.exists()){//�����ļ������ڣ������ļ�
							dir.mkdirs();
						}
						//ָ���ϴ����ļ���
						String pName = fileItem.getName();
						//Ϊ�˷�ֹ�ļ������ǣ��ϴ����������˵��ļ�Ҫ����������ʹ��UUID
						UUID id = UUID.randomUUID();
						//��ȡ�ļ�������
						String FileType = pName.substring(pName.lastIndexOf("."));
						System.out.println("FileType:"+FileType);
						photoName = id.toString()+FileType;
						//ָ���������ļ��к��ļ���
						File file = new File(dir,photoName);
						
						//�ϴ�����Ƭ��ָ��λ��
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
		 	
		 	//����ҵ��㴦��
		 	StudentService ss = new StudentServiceImpl();
		 	int n = ss.save(stu);
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

