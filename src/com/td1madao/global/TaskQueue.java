package com.td1madao.global;

import java.util.Iterator;
import java.util.TreeSet;

import com.td1madao.bean.UrlScoreBean;

/**
 * ��������������(���꣬������TreeSet)
 * 
 * Ҳ��������������ؼ��Ķ�����������
 * 
 * �ⶫ���ǵ���ģʽ
 * 
 * ���ҷ�ֹ���߳�ͬʱ����������
 * 
 * û�취���������ظ�������������Ҳֻ����TreeSet�ˣ��ж����������Ƚ��������
 * 
 * */
public class TaskQueue{
	
	
	 private static TaskQueue uniqueInstance = new TaskQueue();
	 private static TreeSet<UrlScoreBean> queue=new TreeSet<UrlScoreBean>();
//	 private static Queue<UrlScoreBean> queue = new LinkedList<UrlScoreBean>();
	 private TaskQueue(){	 }
	 public static TaskQueue getInstance() {
	             return uniqueInstance;
	 }
	 /**
	  * ����Ƿ�ֹ�ڴ汬�ˣ���Ȼ��������濴���
	  * ����ڴ��㹻�ʵ��䳤���ڴ治�����ʵ���С�������
	  * 
	  * */
	public synchronized void offer(UrlScoreBean u) {
		if (queue.size()<=GlobalVar.queueLength) {
			queue.add(u);  
		}
	}
	public synchronized UrlScoreBean poll() {
		if (!queue.isEmpty()) {
			return queue.pollFirst();
		}
		return null;
	}
	/**
	 * ����������̫����
	 * ����߹���Ҫ��,��������
	 * �������Ҫ��Ϊѡ����ڵ�ǰ����ǰ75%��
	 * ����������˾��˷����������Ķ�����
	 * 
	 * ������������ɶ����ͷ���ˣ��ɴ�����1�����2�������n������
	 * */
	public synchronized double getHigherQuality() {
		
		int nowLength=TaskQueue.getInstance().size();//��ǰ��������
		int maxLength=GlobalVar.queueLength;//������������
		int expectPos=(int)(maxLength*GlobalVar.filterPos);//�����Ĺ���λ��
		int times=nowLength-expectPos;
		if (times<0) {//��Ȼ��������˵������������ܣ����Ͼ����̣߳��Է���һԽ����
			return 0;
		}
		
		for(Iterator<UrlScoreBean> iterator = queue.iterator() ;iterator.hasNext();){
			if (times<=0) {
				return iterator.next().getScore();//�ҵ����λ�õ����֣�����
			}
			iterator.next();
			iterator.remove();
			times--;
		}
		return 0;//��������˵�������Ҳ�ǲ����ܵ�
	}
	
	/**
	 * ���������ʵ���Բ�ͬ���ģ���̨�̲߳���Ҫ̫��ȷ�ĳ���
	 * */
	public synchronized int size() {
		return queue.size();
	}
}
