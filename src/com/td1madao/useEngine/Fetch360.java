package com.td1madao.useEngine;

import java.util.ArrayList;

import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;

import com.td1madao.bean.KeyWord;
import com.td1madao.filters.fetchUrlUtil;
import com.td1madao.global.GlobalVar;
import com.td1madao.gui.MyFrame;



public class Fetch360 {
	/**
	 *  �����360����ĵ��ù��ߣ��������� 
	 * */

	
	
	
	public static ArrayList<String> work(String host){
		
		ArrayList<String> ret=new ArrayList<String>();
		int urlPage=0;
		String elementString = null;
		String key=null;
		if (GlobalVar.searchCont==null) {
			
		if (host!=null) {
			key="site%3A"+host+"%20"+getKeySerials();
		}else {
			key=getKeySerials();
		}
		}else {
			key=GlobalVar.searchCont;
		}
		
		
		while (true) {
			int retL=ret.size();
		String threadUrl ="http://so.360.cn/s?q="+key+"&pn="+(urlPage+1);
		MyFrame.Trace("360������"+threadUrl);
		urlPage++;//��ҳ
		try{
		Document doc = null;
			doc = (Document)Jsoup.connect(threadUrl).get(); //��Document��¼ҳ����Ϣ����CHROME�������ͦ���
			
		
		
		for (int i = 0; i < GlobalVar.baiduNum; i++) {
			elementString=doc.getElementsByClass("res-title").toString();//�������Ĵ���
//					Id(String.valueOf(getURLNum+1)).toString();
			int nowSize=ret.size();//��ǰ��С
			ArrayList<String>ansArrayList=fetchUrlUtil.workAll(elementString);//��÷��ϱ�׼�ĳ�����
			if (nowSize+ansArrayList.size()>GlobalVar.searchNum) {//����̫��
				
				for (int j = 0; j < GlobalVar.searchNum-nowSize; j++) {
					ret.add(ansArrayList.get(j));
				}
				break;	
			}
			else
			ret.addAll(ansArrayList);
		
		}//end for
		if (ret.size()==retL) {
			break;	
		}

		
		if (ret.size()>=GlobalVar.searchNum) {
			break;
		}
		
		}catch(NullPointerException e){
			MyFrame.Trace("360���Ҳ�������Ҫ�ģ�������360���ԣ�");
			return null;
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
//			GlobalVar.searchNum=20;//��360������20��
//			System.out.println("fetch360");
//			GlobalVar.keyStrings=new String[]{"����Ͱ�","����"};
//				System.out.println(work(null).size());
		}
}
