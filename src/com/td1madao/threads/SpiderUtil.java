package com.td1madao.threads;

import com.td1madao.bean.JsoupBean;
import com.td1madao.bean.UrlScoreBean;
import com.td1madao.global.GlobalVar;
import com.td1madao.gui.MyFrame;
import com.td1madao.htmlGet.GetHttp;
import com.td1madao.math.WordDensity;

/**
 * ����һ��������
 * 
 * ˵���˾�������һ����ַ������һ��UrlScoreBean ����
 * 
 * */
public class SpiderUtil {
public static UrlScoreBean work(final String url,final int father) {
	JsoupBean jbBean=GetHttp.workByClient(url);
	String newUrl=new String(url);
	if (jbBean.getChangeURL()!=null) {
		newUrl= jbBean.getChangeURL();//��ȷ�����ӵ�ַ
	}
	double asso=WordDensity.work(GlobalVar.keyStrings, jbBean.getArticle());
	UrlScoreBean usb=new UrlScoreBean(asso, newUrl, jbBean.child,father+1);
	
	String temp="";
	for (int i = 0; i < GlobalVar.keyStrings.length; i++) {
		temp+= GlobalVar.keyStrings[i]+" ";
	}
	MyFrame.Trace(">>>�ؼ���:"+temp);
	MyFrame.Trace(">>>������:"+asso);
	
	return usb;//���ְ����˵㣬������һ�����ݽṹ��Ҳ�Ǵ��뵽���ݿ������
	
}
public static void main(String[] args) {
//	GlobalVar.keyStrings=new String[]{"������Ů","��ħ��"};//�r(�s���t)�q �����Ҵ��屾�԰�
//	System.out.print(work("http://baike.baidu.com/subview/8773/7380728.htm?fr=aladdin"));
	//ûɶ���⣬���������е����ӣ�������˵��� ������Ůûɶ��ϵ�ľ�O��
}
}
