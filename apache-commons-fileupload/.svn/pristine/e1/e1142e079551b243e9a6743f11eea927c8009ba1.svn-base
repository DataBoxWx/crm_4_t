package com.bjpowernode.crm.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet(urlPatterns={"/fileup.do"})
public class FileUpController extends HttpServlet {
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//在进行文件上传的时候，这种方式已经无法解决普通form表单元素产生的乱码。
		//request.setCharacterEncoding("UTF-8");
		
		//在进行fileupload的时候，由于不再使用普通的提交方式提交表单，所以以下代码已经无法获取到表单提交的数据。
		//String username = request.getParameter("username");
		//System.out.println(username);
		
		//request对象非常重要，request对象封装了请求发送的数据。
	    String path = getServletContext().getRealPath("/files");
	    //临时文件目录
	    String tmpPath = getServletContext().getRealPath("/tmp");
	    //声明disk
	    DiskFileItemFactory disk = new DiskFileItemFactory();
	    disk.setSizeThreshold(1024 * 1024); //超1MB之后生成临时文件
	    disk.setRepository(new File(tmpPath));
	    //声明解析requst的servlet
	    ServletFileUpload up = new ServletFileUpload(disk);
	    try{
	      //解析requst
	      List<FileItem> list = up.parseRequest(request);
	      //声明一个list<map>封装上传的文件的数据
	      for(FileItem file:list){
	    	  if(file.isFormField()){ //普通的表单元素
	    		  String fieldName = file.getFieldName();
	    		  //String fieldValue = file.getString();
	    		  String fieldValue = file.getString("UTF-8");
	    		  System.out.println(fieldName + "=" + fieldValue);
	    	  }else{
	    		//获取文件名
	  	        String fileName = file.getName();
	  	        System.out.println(fileName);
	  	        file.write(new File(path+"/"+fileName));	  
	    	  }
	      }
	    }catch(Exception e){
	      e.printStackTrace();
	    }
	  }
}

