package com.td1madao.math;

import com.td1madao.htmlGet.GetHttp;
import com.td1madao.stringUtil.MyStringUtil;

/**
 * �����Ƶ�ܶȵ���ѧģ��
 * 
 * �����ʱ��������������ƶ��Ż����ģ��
 * ������ؼ��ʷֲ����������λ�ü�Ϊ1��û���ֵļ�Ϊ0
 * ֮������ؼ������鿴�������������ƶ�
 * ��ôȷ���ؼ��ʵĹ����̶�
 * 
 * ��ģ����ʱ�и���Ŀ��ƴ��ֽ������ʱ���õľ������ԭ��
 * 
 * �����ж���������֮������ƶȣ����������� [��Ȩϵ����] ������
 * 
 * */
public class WordDensity {
	/**
	 * @param keyWord �ؼ�������
	 * @param article ����(��ҳ)
	 * */
public static double work(final String []keyWord,final String article) {
	if (article==null) {
		return 0;
	}
int keyWordLength=keyWord.length;
int articleLength=article.length();//���Ϊx������ɣ�
int countKeyWord[]=new int[keyWordLength];
double density=0;
for (int i = 0; i < countKeyWord.length; i++) {
	countKeyWord[i]=calTime(article, keyWord[i]);//��������Ǽ����Ƶ�ܶȺ͹ؼ��ʷֲ��õĶ���
	density+=((double)countKeyWord[i]/articleLength);
}
return density;
}
/**
 * �ؼ��ʳ��ִ���
 * */
public static int calTime(final String str,final String str1) {
	int count = 0;
	int start = 0;
	while (str.indexOf(str1, start) >= 0 && start < str.length()) {
		count++;
		start = str.indexOf(str1, start) + str1.length();
	}
	return count;
}
public static void main(String[] args) {
	//����á����꡷�����԰ɣ��r(�s���t)�q
	String getHTML=GetHttp.work("http://tieba.baidu.com/f?kw=%B3%A4%B9%C8%B4%A8%CC%A9%C8%FD&fr=ala0");//�����ö�����ʵ��
	String deleteEnter=MyStringUtil.deletEnter(getHTML);
	double ans=work(new String[]{"MADAO","���ȴ�","������ʱ"},deleteEnter);
	System.out.println(ans);
}
}
