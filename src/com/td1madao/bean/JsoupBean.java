package com.td1madao.bean;

import java.util.ArrayList;
/**
 * ������ݽṹ�Ǹ���ʱ�������뾡����������
 * 
 * ֮����Ҫ����ȷ��URL������Ϊ�������ѧģ����Ҫ�õ�host������������
 * 
 * */
public class JsoupBean {
private String article=null;
private String changeURL=null;
public ArrayList<String> child=null;
public JsoupBean(String article,String changeURL,ArrayList<String>child) {
	this.setArticle(article);
	this.setChangeURL(changeURL);
	this.child=child;
}
public String getChangeURL() {
	return changeURL;
}
public void setChangeURL(String changeURL) {
	this.changeURL = changeURL;
}
public String getArticle() {
	return article;
}
public void setArticle(String article) {
	this.article = article;
}
}
