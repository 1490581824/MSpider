package com.td1madao.useEngine;

import java.util.ArrayList;

import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;

import com.td1madao.filters.fetchUrlUtil;
import com.td1madao.global.GlobalVar;



public class FetchSouSou {
	/**
	 * ˵ʵ�������ѵ������360������񣬱˴˾��Ǳ������ֲ�ͬ����ҳ��ʽ��һ��һ��
	 * �ٶ�����͹ȸ����
	 * 
	 * */

	
	
	
	public static ArrayList<String> work(String host){
		
		ArrayList<String> ret=new ArrayList<String>();
		int urlPage=0;//���ܵĵڼ�ҳ
		String elementString = null;//js��ȡ��Ԫ��
		String key=null;
		if (host!=null) {
			key="site%3A"+host+"%20"+getKeySerials();
		}else {
			key=getKeySerials();
		}
		while (true) {
		String threadUrl ="http://www.soso.com/q?query="+key+"&pg="+(urlPage+1);
		try{
			Document doc = (Document)Jsoup.connect(threadUrl).get(); //��Document��¼ҳ����Ϣ����CHROME�������ͦ���
			for (int i = 0; i < GlobalVar.baiduNum; i++) {
				elementString=doc.getElementsByClass("rb").toString();//�������Ĵ���
//						Id(String.valueOf(getURLNum+1)).toString();
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
			if (ret.size()>=GlobalVar.searchNum) {
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
		String[] keyword = GlobalVar.keyStrings.clone();
		StringBuffer keyComb=new StringBuffer();
		for (int i = 0; i < keyword.length-1; i++) {
			keyComb.append(keyword[i]);
			keyComb.append('+');
		}
		keyComb.append(keyword[keyword.length-1]);
		return keyComb.toString();
	}
	
	
	 /**
		 * �Ҳ⣡
		 */
		public static void main(String[] args) {
			GlobalVar.searchNum=20;//��������20��
			System.out.println("fetch����");
			GlobalVar.keyStrings=new String[]{"����Ͱ�","����"};//ÿ�β��Զ��Ѱ���Ͱ��أ��������岻�������ɣ�
			ArrayList<String>aList=work(null);
				System.out.println(aList);
				System.out.println(aList.size());
		}
}
