package com.td1madao.htmlGet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.td1madao.bean.JsoupBean;
import com.td1madao.filters.fetchUrlUtil;
import com.td1madao.global.GlobalVar;
import com.td1madao.stringUtil.MyStringUtil;


/**
 * �������ָ����ַ���ص�HTML����
 * ��������Ƚϼ򵥣�û��HttpClientţ�ƣ�����ִ�������ٶȾ��Բ������������ڲ���
 * @demo 
 * */
public class GetHttp {
	/**
	 * ģ�ⰳ��google�����
	 * �ܼ��������Ƿ���ִ���(��GBK����)
	 * @param url ���ʵ���ַ
	 * @return String ��ַ��HTML����
	 * */
public static String work(final String url) {
	StringBuilder builder=new StringBuilder();
	try {
		URL realUrl = new URL(url);
		
		URLConnection connection = realUrl.openConnection();//��ҳ����
		connection.setRequestProperty("Accept", "*/*");
		connection.setRequestProperty("Proxy-Connection:", "keep-alive");
		connection.setRequestProperty("User-Agent","Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.95 Safari/537.36");
		String str=new String(url);
		str=str.replace("http://", "");
		connection.setRequestProperty("Host", MyStringUtil.getHost(url));//host
		connection.setRequestProperty("Accept-Language", "zh-CN,zh;q=0.8");//host
		InputStreamReader isr = new InputStreamReader(connection.getInputStream(), "GBK");
		BufferedReader in = new BufferedReader(isr);		
		String line;
		boolean correctEncoding=true;
		while ((line = in.readLine()) != null) {
			if(!isGBKEncoding(line)){
				correctEncoding=false;
				builder.delete(0, builder.length());
				break;
			}
			builder.append(line);
		}
		in.close();
		isr.close();
		if (!correctEncoding) {//���ֱ��������������
			connection=realUrl.openConnection();
			isr = new InputStreamReader(connection.getInputStream(), "UTF-8");
			in = new BufferedReader(isr);
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
			in.close();
			isr.close();
		}
		
		
	} catch (Exception e) {
		e.printStackTrace();
	}
return builder.toString();
}

/**
 * �жϱ��뷽ʽ�Ƿ�ΪGBK
 * */
private static boolean isGBKEncoding(final String line){
	return java.nio.charset.Charset.forName("GBK").newEncoder().canEncode(line);
}


/**
 * ����URL�Ҿ���
 * 
 * ����һ��JsoupBean 
 * 
 * ��ĥ��ĥ��ͨ����ѧģ�ʹ���һ�£����ܵõ�
 * 
 * UrlScoreBean
 * 
 * */
public static JsoupBean workByClient(final String url){
	
	
	Document doc=null;
	String temp=null;
	for (int i = 0; i < GlobalVar.tryTime; i++) {
		
	try{
	Connection connection=Jsoup.connect(url);
	doc = connection.get(); //��Document��¼ҳ����Ϣ����CHROME�������ͦ���
ArrayList<String> al=new ArrayList<String>();	
	 Element body = doc.body();
	  Elements es=body.select("a");
	  for (Iterator<Element> it = es.iterator(); it.hasNext();) {
	   Element e = (Element) it.next();
	   String href=e.attr("href");
	   if (href!=null&&href.length()!=0&&href.indexOf("http")==0) {
		al.add(href);
	}
	  }
	  
	temp=connection.response().url().toString();//���һ���������ض���
	if (temp.equals(url)) {//���һ��������js���ض���
		String testRedirect=MyStringUtil.deletEnter(doc.toString());
		if (testRedirect.contains("window.location.replace")) {
			temp=fetchUrlUtil.work(testRedirect);
		}
	}
	return new JsoupBean(doc.toString(), temp, al);
	}catch(HttpStatusException e){
	}
	catch(Exception e){
	}
	}
	return new JsoupBean(null, null, null);
}

/**
 * ģ�����
 * */

public static void main(String[] args) {
//	String urlString="http://www.baidu.com/link?url=eJujH2-xVJdDkr5qD5k4JYeoCLEwmODdkxi7Wirv7r6hgnYAYhxUGuBH3xizc9t_CmAydH_tDn7fNrzes__M8K";
//	urlString=workByClient(urlString)[1];
//	System.out.println(urlString);
//	System.out.println("http://donghua.52pk.com/yinhun3/0-13.shtml".indexOf("http")==0);
	
}
}
