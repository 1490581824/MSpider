package com.td1madao.filters;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ������Ȼ�е����������ǻ��һ��HTML�����е�һ��������URL
 * */
public class fetchUrlUtil {
	/**
	 * @param htmlCode �����
	 * @return �����ַ������飬�������е�URL
	 * ֻҪ��һ��
	 * */
	public static String work(final String htmlCode) {
		Matcher matcher = null;
		Pattern pattern1 = Pattern.compile("\"http?\'?(.*?)\"?\'?\"",Pattern.DOTALL);
		matcher = pattern1.matcher(htmlCode);
		if(matcher != null && matcher.find())
		{	String temp=matcher.group(0);
				return 	temp.substring(temp.indexOf("\"")+1,temp.lastIndexOf("\""));
		}
		else return null;
	}
	/**
	 * �õ����еĳ�����
	 * */
	public static ArrayList<String> workAll(final String htmlCode) {
		ArrayList<String>aList=new ArrayList<String>();
		Matcher matcher = null;
		Pattern pattern1 = Pattern.compile("\"http?\'?(.*?)\"?\'?\"",Pattern.DOTALL);
		matcher = pattern1.matcher(htmlCode);
		while(matcher.find()) {
			String s=matcher.group();
			if (!(s==null||s.equals(""))) {
				aList.add(s);
			}
		}
		return aList;
	}
	
	public static void main(String[] args) {
//		String ss = "<html> <head>  <script>window.location.replace(\"http://www.bilibili.tv/video/av366276/\")</script>   <noscript>   <meta http-equiv=\"refresh\" content=\"0;URL='http://www.bilibili.tv/video/av366276/'\" />  </noscript>  </head> <body></body></html>";
		String ss = "<a href=\"http://baike.so.com/lottery/?act_id=5&amp;src=ss\" target=\"_blank\" class=\"edit\">�н��༭</a><a href=\"http://baike.so.com/doc/416057.html\" data-tp=\"kvdb\" data-stp=\"baike\" data-extargs=\"[]\" data-st=\"0\" data-e=\"1\" data-pos=\"1\" data-m=\"612eee9776742d44e020ba83b983e4ec\" target=\"_blank\"><em>����</em>_360�ٿ�</a>\"";
		System.out.println(workAll(ss));
	}
}
