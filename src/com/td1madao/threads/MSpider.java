package com.td1madao.threads;

import java.util.Iterator;

import com.td1madao.bean.UrlScoreBean;
import com.td1madao.db.DBOperator;
import com.td1madao.global.GlobalVar;
import com.td1madao.global.TaskQueue;
import com.td1madao.gui.MyFrame;
/**
 * �����ʹ������
 * ������
 * ����������
 * ����
 * �ӽ�����
 * 
 * M��ʵ���ʾMadao,����SM�Ǹ�M����^_^
 * 
 * */
public class MSpider extends Thread {
	 public volatile boolean flag = true;
	 int num=0;
	   private static Object obj = new Object();
	 public MSpider(int num) {
		 this.num=num;
	}
	 
	/**
	 * ��ʵ�о�û��Ҫ�ù����������ͣ
	 * ��ѭ������
	 * 
	 * ���ˣ�����д�ɣ�����Եò�����
	 * 
	 * �Ͼ����������ݿ�Ͷ�����
	 * 
	 * */
	@Override
	public void run() {
//		System.out.println("�����߳�"+num+"����");
		MyFrame.Trace("�����߳�"+num+"����");
		while (flag) {//Ŀǰ��û��������ͣ�����Ĺ��ܣ�
			if (MyFrame.pause) {
			    synchronized (obj) {
		            try {
		                obj.wait();
		            } catch (InterruptedException e) {
		                System.out.println(getName() + " Test Thread Interrupted");
		            }
		        }
			}
			UrlScoreBean usb=TaskQueue.getInstance().poll();//�õ���ض���ߵ�����
			 Iterator<String> itera	 = usb.child.iterator();//���е�����
			  while (itera.hasNext()) {
				  if (MyFrame.pause) {
					    synchronized (obj) {
				            try {
				                obj.wait();
				            } catch (InterruptedException e) {
				                System.out.println(getName() + " Test Thread Interrupted");
				            }
				        }
					}
				UrlScoreBean tempbean=  SpiderUtil.work(itera.next());
				
				
				if (Double.isNaN(tempbean.getScore())||tempbean.getScore()<=GlobalVar.filterScore||GlobalVar.blackList.contains(tempbean.getHost()))
				{
					continue;//û�û��ֵ���߻����ں�������
				}
				
				//�õ���������ߵ�host
				if (tempbean.getScore()>GlobalVar.maxScore) {
					GlobalVar.maxScore=tempbean.getScore();
					GlobalVar.maxHost=tempbean.getHost();
				}
				
				  TaskQueue.getInstance().offer(tempbean);//ò���м�ֵ�Ķ�����͵���ӽ�������
				  if(DBOperator.getInstance().inputRecord(tempbean))//�������ݿ�����
//				  System.out.println(">����"+num+"��������:"+tempbean.getScore()+"/"+tempbean.getHost());
					  MyFrame.Trace(">��������"+num+"{��ַ:"+tempbean.getUrl()+",������:"+tempbean.getScore()+"}");
//				  System.out.println("OK");
			  }
		}//�ɲ���Ļ�����Ƕ��������������ˣ����򡭡�
//		System.out.println("�����̹߳ر�");
	}

	public void toNotify() {
		  synchronized (obj) {
			  obj.notifyAll();
	        }
	}
}
