package com.td1madao.bean;

import java.util.HashSet;
/**
 * ������ݽṹ�Ǹ���ʱ�������뾡����������
 * 
 * ֮����Ҫ����ȷ��URL������Ϊ�������ѧģ����Ҫ�õ�host������������
 * 
 * */
public class JsoupBean {
private String article=null;
private String changeURL=null;
public HashSet<String> child=null;
public JsoupBean(String article,String changeURL,HashSet<String>child) {
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
