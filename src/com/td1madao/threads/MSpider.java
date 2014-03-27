package com.td1madao.threads;

import java.util.ArrayList;
import java.util.List;

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
			UrlScoreBean usb=TaskQueue.getInstance().poll();//�õ���ض���ߵ�����
			
			MyFrame.Trace("�����߳�"+num+"��ʼ����");
			GlobalVar.spiderState[num]=false;//�߳��ڹ���
			if (MyFrame.pause||usb==null) {//����ͣ����������û�յ��κ�����
			    synchronized (obj) {
		            try {
		            	GlobalVar.spiderState[num]=true;//�߳�����(û������)
		            	MyFrame.Trace("�����߳�"+num+"����");
		                obj.wait();
		            } catch (InterruptedException e) {
		                System.out.println(getName() + "�߳��ж�");
		            }
		        }
			}
			MyFrame.Trace("�����߳�"+num+"�õ�������"+usb.getUrl());
			
			
				 if (usb.child==null) {//������
					 MyFrame.Trace("�����߳�"+num+"����"+usb.getUrl()+"û��������");
					 continue;
				}	
			
//			 Iterator<String> itera	 = usb.child.iterator();//���е�����
				 //�������õ������������漰���޸�Ԫ�صĲ���
				 
			 int father=usb.getChildLevel();
//			  while (itera.hasNext()) {
			 
			 List<String> list =  new  ArrayList<String>(usb.child);
			 
				  for (int j = 0; j < list.size(); j++) {
					
				  if (MyFrame.pause) {
					    synchronized (obj) {
				            try {
				                obj.wait();
				            } catch (InterruptedException e) {
				                System.out.println(getName() + "�߳��ж�");
				            }
				        }
					}
				  
				  
				  String url=list.get(j);
					if (url.charAt(0)=='\"') {
						url=url.substring(1);
					}
					if (url.charAt(url.length()-1)=='\"') {
						url=url.substring(0,url.length()-1);
					}
					list.set(j, url);
				  
				  
				  
				UrlScoreBean tempbean=  SpiderUtil.work(list.get(j),father);
				MyFrame.Trace(">�����߳�"+num+"�õ�������"+tempbean.getUrl());
				
				if (father>3&&(Double.isNaN(tempbean.getScore())||tempbean.getScore()<=GlobalVar.filterScore||GlobalVar.blackList.contains(tempbean.getHost())))
				{
					MyFrame.Trace(">�����߳�"+num+"����������"+tempbean.getUrl());
					continue;//û�û��ֵ���߻����ں�������
				}
				
				//�õ���������ߵ�host
				if (tempbean.getScore()>GlobalVar.maxScore) {
					MyFrame.Trace("���host�ǣ�"+tempbean.getHost()+"�÷��ǣ�"+tempbean.getScore());
					GlobalVar.maxScore=tempbean.getScore();
					GlobalVar.maxHost=tempbean.getHost();
				}
				  TaskQueue.getInstance().offer(tempbean);//ò���м�ֵ�Ķ�����͵���ӽ�������
				  if (tempbean.getScore()<GlobalVar.filterScore||tempbean.getScore()==0||Double.isNaN(tempbean.getScore())) {
					continue;
				}
				  
				  				  
				  MyFrame.Trace(">�����߳�"+num+"����д������"+tempbean.getUrl()+"(���֣�"+tempbean.getScore()+")");
				  if(DBOperator.getInstance().inputRecord(tempbean))//�������ݿ�����
					  MyFrame.Trace(">>�����߳�"+num+"д��"+tempbean.getUrl()+",���֣�"+tempbean.getScore()+")");
			  }
		}//�ɲ���Ļ�����Ƕ��������������ˣ����򡭡�
	}

	public static void toNotify() {
		  synchronized (obj) {
			  obj.notifyAll();
	        }
	}
}
