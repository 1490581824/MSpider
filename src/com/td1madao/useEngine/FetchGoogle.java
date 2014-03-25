package com.td1madao.useEngine;

import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;

import com.td1madao.bean.KeyWord;
import com.td1madao.filters.fetchUrlUtil;
import com.td1madao.global.GlobalVar;
import com.td1madao.gui.MyFrame;
import com.td1madao.stringUtil.MyStringUtil;



public class FetchGoogle {
	/**
	 *	�ȸ����������鲻Ҫ��
	 *	�쳯���� 
	 *
	 *	�Ҳ������ģ�鶼������
	 *	
	 *
	 * */

	
	
	
	public static ArrayList<String> work(String host){
		
		ArrayList<String> ret=new ArrayList<String>();
		int getURLNum=0;//�����ĸ������ʿɶ�ֹ��(Global���涨��������)
		int urlPage=0;//���ܵĵڼ�ҳ
		String elementString = null;//js��ȡ��Ԫ��
		String key=null;
		if (host!=null) {
			key="site%3A"+host+"%20"+getKeySerials();
		}else {
			key=getKeySerials();
		}
		while (true) {
		String threadUrl ="http://www.google.com.hk/search?q="+key+"&start="+(urlPage*10);
		System.out.println(threadUrl);
		try{
			Connection conn=Jsoup.connect(threadUrl);
			conn.header("Host", "www.google.com.hk");
			conn.header("Proxy-Connection", "keep-alive");
			conn.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
			conn.header("Accept-Encoding", "gzip,deflate,sdch");
			conn.header("Accept-Language", "zh-CN,zh;q=0.8");
			Document doc=null ;
			try{
		 doc = (Document)conn.get(); //��Document��¼ҳ����Ϣ����CHROME�������ͦ���
			}catch(Exception e){
				MyFrame.Trace("�ȸ���������쳣����Ҫ������֤�룬����رչȸ���������");
				return null;
			}
		for (int i = 0; i < GlobalVar.baiduNum; i++) {
			elementString=doc.getElementsByClass("g").toString();
			elementString=MyStringUtil.deletEnter(elementString);//��
		String gerStrings=fetchUrlUtil.work(elementString);//�����ҳ��URL
		if(gerStrings!=null||getURLNum>=GlobalVar.searchNum){
			ret.add(gerStrings);//�õ������Ӷ���ȥ��
			getURLNum++;
		}
		else {
			break;
		}
		}//end for
		if (getURLNum>=GlobalVar.searchNum) {
			break;
		}
		else {
			urlPage++;//��ҳ
		}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
		}//end while
		return ret;
	}
	
	/**
	 * ��������Ĺؼ���
	 * */
	private static String getKeySerials() {
		KeyWord[] keyword = GlobalVar.keyStrings.clone();
		StringBuffer keyComb=new StringBuffer();
		for (int i = 0; i < keyword.length-1; i++) {
			keyComb.append(keyword[i].getName());
			keyComb.append('+');
		}
		keyComb.append(keyword[keyword.length-1].getName());
		return keyComb.toString();
	}
	
	
	 /**
		 * �Ҳ⣡
		 */
		public static void main(String[] args) {
//			GlobalVar.searchNum=20;//��������20��
//			System.out.println("fetch�ȸ�");
//			GlobalVar.keyStrings=new String[]{"����Ͱ�","����"};//ÿ�β��Զ��Ѱ���Ͱ��أ��������岻�������ɣ�
//			ArrayList<String>aList=work(null);
//				System.out.println(aList);
//				System.out.println(aList.size());
		}
}
