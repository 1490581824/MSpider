package com.td1madao.useEngine;

import java.util.ArrayList;

import org.jsoup.Jsoup;  
import org.jsoup.nodes.Document;

import com.td1madao.filters.fetchUrlUtil;
import com.td1madao.global.GlobalVar;
import com.td1madao.stringUtil.MyStringUtil;



public class CopyOfFetchSouSou {
	/**
	 * �ڶ����������ؼ���,���浽Global����
	 * ���ö��ܵ�����
	 * 
	 * ����������ΪGlobal����涨������
	 * @param String string �����Ƶ���վ��ûҪ�����null
	 * @return �����Ľ��Ϊһ���ַ�������
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
		String threadUrl ="http://www.soso.com/q?query="+key+"&pg="+(urlPage+1);
		try{
		Document doc = (Document)Jsoup.connect(threadUrl).get(); //��Document��¼ҳ����Ϣ����CHROME�������ͦ���
		for (int i = 0; i < GlobalVar.baiduNum; i++) {
			elementString=doc.getElementById(String.valueOf(getURLNum+1)).toString();
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
			GlobalVar.keyStrings=new String[]{"����Ͱ�","����"};
				work(null);
				System.out.println(GlobalVar.urlStore);
				System.out.println(GlobalVar.urlStore.size());
		}
}
