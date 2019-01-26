package com.bjpowernode.httpclient;

import java.io.FileWriter;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ActivityPageServiceTest {
	
	public static void main(String[] args) {
		
		//httpclient + htmlparser（网络爬虫）
		
		//java 调用微信接口
		//String urlNameString = "http://192.168.146.2:8080/crm/workbench/activity/page.do?pageNo=1&pageSize=2";
		//String urlNameString = "http://www.pontikis.net/labs/bs_pagination/docs/";
		String urlNameString = "https://sou.zhaopin.com/jobs/searchresult.ashx?jl=%E5%8C%97%E4%BA%AC&kw=java&sm=0&p=2";
		try {
			HttpGet request = new HttpGet(urlNameString);
			HttpClient httpClient = new DefaultHttpClient();
			HttpResponse response = httpClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity(), "utf-8");
				//System.out.println(result);
				FileWriter writer = new FileWriter("index.html");
				writer.write(result);
				writer.flush();
				writer.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
