package com.td1madao.global;

import java.util.ArrayList;
import java.util.List;

import com.td1madao.bean.UrlScoreBean;

/**
 * ������Ȼ��Ƿ�ᣬ�����Ǵ�ȫ�����õ�
 * 
 * ����������ݳ��˳�ʼ��ʱ��ģ������������䣬�Ҿ��Ȳ���ͬ���� ����������������
 * */
public class GlobalVar {
	public static int searchNum = 3;// ÿ���������湹��ģ��ʱ��ȡ����,���鲻Ҫ̫�࣬�����ʼ�������
	public static int baiduNum = 10;// �ٶ�ÿҳ��10���������
	public static int tryTime = 3;// http����ʧ�ܴ�������ʾʧ��3�ξ�������
	public static int queueLength = 100;// ������еĳ��ȣ�����������Ե���
	public static int spiderNum = 8;// ����ĸ���
	public static double filterPos = 0.8;// �������̫�������˵����λ�á�100%����Ķ�����һ��СС���Ż�,����˷���Դ
	public static double filterScore = 0;// �������ѧģ�͵õ������������С����������۾Ͱ�������������ҳ��ֱ�ӹ��˵����������ݿ���
	public static volatile double maxScore = 0;// ��������ǿ�ķ���
	public static volatile String maxHost = null;// ����ص�host�����������ĺľ���Դ���Ǿ������host����������Դ�ɣ�������
	public static List<String> urlStore = new ArrayList<String>();
	// �ⶫ���Ǵ洢��һ�������õ�URL�õģ����ڴ���������һ�����
	public static List<UrlScoreBean> beanList = new ArrayList<UrlScoreBean>();
	// �洢URL�����֣�������host����
	public static String[] keyStrings = null;
	public static String username = "root";// ���ݿ�
	public static String password = "123456";
	public static String db = "jdbc:mysql://127.0.0.1:3306/mspider";
	public static ArrayList<String> blackList = new ArrayList<String>();
	// host�����������˵���Щhost�����ڶ���վ�����أ��ҾͲ������������ O(��_��)O
	public static boolean notice = true;//���ݿ⾯��,�е�mysqlĬ�����ò�֧������
	//����4�����������Ƿ�����
	public static boolean baidu = true;
	public static boolean google = true;
	public static boolean qihu = true;
	public static boolean sousou = true;
	
}
