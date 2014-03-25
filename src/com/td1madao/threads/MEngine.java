package com.td1madao.threads;

import java.util.ArrayList;

import com.td1madao.bean.UrlScoreBean;
import com.td1madao.db.DBOperator;
import com.td1madao.global.GlobalVar;
import com.td1madao.global.TaskQueue;
import com.td1madao.gui.MyFrame;
import com.td1madao.useEngine.FetchBaidu;

/**
 * �������˵�����棬������˵������������
 * 
 * ��Ȼ����Ҳ�ǵ���ģʽ��
 * 
 * �����ö��ܡ��ȸ衢�滢�������ĸ�����һ���������
 * ��Ȼ��������������һ������ȥ��
 * ��ȫ�����ػ��߳�DaemonThread�ĵ�ǲ
 * 
 * ���������ʾ�߳�״̬
 * 
 * */
public class MEngine extends Thread {
	public volatile boolean flag= false;
	private static Object obj = new Object();
	private static MEngine uniqueInstance = new MEngine();
	private MEngine(){}
	public static MEngine getInstance() {
        return uniqueInstance;
}
	
	@Override
	public void run() {
//		System.out.println("�����߳�����");
		MyFrame.Trace("���������߳�����");
		flag=true;
		ArrayList<String> al=new ArrayList<String>();
		if (GlobalVar.baidu) {
			ArrayList<String> temp	=FetchBaidu.work(GlobalVar.maxHost);//�ٶ�
			if (temp!=null) {
				al.addAll(temp);
			}
		}
		if (GlobalVar.google) {
			ArrayList<String> temp	=FetchBaidu.work(GlobalVar.maxHost);//�ٶ�
			if (temp!=null) {
				al.addAll(temp);
			}
		}
		if (GlobalVar.sousou) {
			ArrayList<String> temp	=FetchBaidu.work(GlobalVar.maxHost);//�ٶ�
			if (temp!=null) {
				al.addAll(temp);
			}
		}	
		if (GlobalVar.qihu) {
			ArrayList<String> temp	=FetchBaidu.work(GlobalVar.maxHost);//�ٶ�
			if (temp!=null) {
				al.addAll(temp);
			}
		}
		UrlScoreBean usb[]=new UrlScoreBean[al.size()];
			for (int i = 0; i < al.size()&&flag; i++) {//flag=false��ʾ����ֹͣ����
				
				if (MyFrame.pause) {
				    synchronized (obj) {
			            try {
			                obj.wait();
			            } catch (InterruptedException e) {
			                System.out.println(getName() + " Test Thread Interrupted");
			            }
			        }
				}
				usb[i]=SpiderUtil.work(al.get(i));
				

				if (Double.isNaN(usb[i].getScore())||usb[i].getScore()<=GlobalVar.filterScore||GlobalVar.blackList.contains(usb[i].getHost()))
				{
					continue;//û�û��ֵ���߻����ں�������
				}
				
				//�õ���������ߵ�host����Ȼ����������ĳ���
				if (usb[i].getScore()>GlobalVar.maxScore) {
					GlobalVar.maxScore=usb[i].getScore();
					GlobalVar.maxHost=usb[i].getHost();
				}				

				MyFrame.Trace(">������������{��ַ:"+usb[i].getUrl()+",������:"+usb[i].getScore()+"}");
				TaskQueue.getInstance().offer(usb[i]);//ò���м�ֵ�Ķ�����͵���ӽ�������
				  DBOperator.getInstance().inputRecord(usb[i]);//�������ݿ�����
		}
			//��������
//			System.out.println("�����̹߳ر�");
			MyFrame.Trace("���������̹߳ر�");
	}
	public void toNotify() {
		 synchronized (obj) {
	            obj.notify();
	        }
	}

}
