package com.td1madao.threads;

import com.td1madao.bean.JsoupBean;
import com.td1madao.bean.UrlScoreBean;
import com.td1madao.filters.FiltTag;
import com.td1madao.global.GlobalVar;
import com.td1madao.htmlGet.GetHttp;
import com.td1madao.math.WordDensity;
import com.td1madao.stringUtil.MyStringUtil;

/**
 * ����һ��������
 * 
 * ˵���˾�������һ����ַ������һ��UrlScoreBean ����
 * 
 * */
public class SpiderUtil {
public static UrlScoreBean work(final String url) {
	JsoupBean jbBean=GetHttp.workByClient(url);
	String newUrl=new String(url);
	if (jbBean.getChangeURL()!=null) {
		newUrl= jbBean.getChangeURL();//��ȷ�����ӵ�ַ
	}
	UrlScoreBean usb=new UrlScoreBean(WordDensity.work(GlobalVar.keyStrings, FiltTag.work(MyStringUtil.deletEnter(jbBean.getArticle()))), newUrl, jbBean.child);
	return usb;//���ְ����˵㣬������һ�����ݽṹ��Ҳ�Ǵ��뵽���ݿ������
	
}
public static void main(String[] args) {
	GlobalVar.keyStrings=new String[]{"������Ů","��ħ��"};//�r(�s���t)�q �����Ҵ��屾�԰�
	System.out.print(work("http://baike.baidu.com/subview/8773/7380728.htm?fr=aladdin"));
	//ûɶ���⣬���������е����ӣ�������˵��� ������Ůûɶ��ϵ�ľ�O��
}
}
