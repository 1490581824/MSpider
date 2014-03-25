package com.td1madao.bean;

import java.util.ArrayList;

import com.td1madao.math.ListProcess;
import com.td1madao.stringUtil.MyStringUtil;

/**
 * ������Ȼ�����˵㣬���������������URL�͹���ϵ���Ľṹ �����������˼����Ȼ�����ϲ���bean��
 * */
public class UrlScoreBean implements Comparable<UrlScoreBean> {
	public UrlScoreBean(double score, String url,ArrayList<String> child) {
		this.score = score;
		this.url = url;
		
		if (child!=null) {//��ʵ���г����ӿ�Ҳû��ϵ
		ListProcess.removeDuplicate(child);//��ȥ�ظ���������
		}
		this.child=child;
		host = MyStringUtil.getHost(url);
	}

	private double score = 0;// ����ϵ��
	private String url = "";// URL
	private String host = "";// host
	public ArrayList<String> child = null;// �����ڲ�������

	public double getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	/**
	 * ��Ϊ��Ҫ����Ҫ���ǽ���
	 * */
	public int compareTo(UrlScoreBean o) {
		if (score - o.score < 0)
			return 1;
		return -1;
	}

	@Override
	public String toString() {
		return "(" + host + "," + score + "," + url + "," + child +")";
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
	public static void main(String[] args) {
		UrlScoreBean uBean=new UrlScoreBean(3, "www.hehe.com", null);
		System.out.println(uBean.getHost());
	}
}
