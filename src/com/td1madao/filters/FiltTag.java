package com.td1madao.filters;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.td1madao.htmlGet.GetHttp;
import com.td1madao.stringUtil.MyStringUtil;

/**
 * ���ڹ�����ҳ��ǩ����
 * */
public class FiltTag {
private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // ����script��������ʽ
private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // ����style��������ʽ
private static final String regEx_html = "<[^>]+>"; // ����HTML��ǩ��������ʽ

public static String work(final String htmlStr) {
	if (htmlStr==null) {
		return null;
	}
	
	String temp=new String(htmlStr);
    Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
    Matcher m_script = p_script.matcher(temp);
    temp = m_script.replaceAll(""); // ����script��ǩ
    Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
    Matcher m_style = p_style.matcher(temp);
    temp = m_style.replaceAll(""); // ����style��ǩ
    Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
    Matcher m_html = p_html.matcher(temp);
    temp = m_html.replaceAll(""); // ����html��ǩ
    return temp.trim().replaceAll("&nbsp;", ""); // �����ı��ַ���
}
public static void main(String[] args) {
	String getHTML=GetHttp.work("http://www.baidu.com/");//�����ö�����ʵ��
	String deleteEnter=MyStringUtil.deletEnter(getHTML);
	String deleteTag=work(deleteEnter);
	System.out.println(deleteTag);
	//�ţ�ò��ûɶ���⣬�ðɣ��Ź���ɣ�\(^o^)/~
}
}
