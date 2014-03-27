package com.td1madao.threads;

import java.util.ArrayList;

import com.td1madao.bean.UrlScoreBean;
import com.td1madao.db.DBOperator;
import com.td1madao.global.GlobalVar;
import com.td1madao.global.TaskQueue;
import com.td1madao.gui.MyFrame;
import com.td1madao.useEngine.Fetch360;
import com.td1madao.useEngine.FetchBaidu;
import com.td1madao.useEngine.FetchGoogle;
import com.td1madao.useEngine.FetchSouSou;

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
	public static ArrayList<String> al=new ArrayList<String>();
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
		
		
		if (!(GlobalVar.baidu||GlobalVar.google||GlobalVar.sousou||GlobalVar.qihu)) {
			return;
		}
		if (GlobalVar.baidu) {
			MyFrame.Trace("����һ�£���ͺǺ�");
			ArrayList<String> temp	=FetchBaidu.work(GlobalVar.maxHost);//�ٶ�
			if (temp!=null) {
				al.addAll(temp);
			}
		}
		if (GlobalVar.google) {
			MyFrame.Trace("Goooooogle(��ǽ������)");
			ArrayList<String> temp	=FetchGoogle.work(GlobalVar.maxHost);//�ٶ�
			if (temp!=null) {
				al.addAll(temp);
			}
		}
		if (GlobalVar.sousou) {
			MyFrame.Trace("���Ѹ�����(������塭��)");
			ArrayList<String> temp	=FetchSouSou.work(GlobalVar.maxHost);//�ٶ�
			if (temp!=null) {
				al.addAll(temp);
			}
		}	
		if (GlobalVar.qihu) {
			MyFrame.Trace("360��ȻΥ������Ԫ��N����");
			ArrayList<String> temp	=Fetch360.work(GlobalVar.maxHost);//�ٶ�
			if (temp!=null) {
				al.addAll(temp);
			}
		}

		if (al.size()==0) {
			return;
		}
		
		UrlScoreBean usb[]=new UrlScoreBean[al.size()];
			for (int i = 0; i < al.size()&&flag; i++) {//flag=false��ʾ����ֹͣ����
				if (MyFrame.pause) {//�߳�����
				    synchronized (obj) {
			            try {
			                obj.wait();
			            } catch (InterruptedException e) {
			                System.out.println(getName() + "�߳��ж�");
			            }
			        }
				}
				
				
				String url=al.get(i);
				if (url.charAt(0)=='\"') {
					url=url.substring(1);
				}
				if (url.charAt(url.length()-1)=='\"') {
					url=url.substring(0,url.length()-1);
				}
				al.set(i, url);
				usb[i]=SpiderUtil.work(al.get(i),0);

				if ((Double.isNaN(usb[i].getScore())||usb[i].getScore()<=GlobalVar.filterScore||GlobalVar.blackList.contains(usb[i].getHost()))&&!GlobalVar.init)
				{
					continue;//û�û��ֵ���߻����ں�������
				}
				
				if (usb[i].getScore()>GlobalVar.maxScore) {//�õ���������ߵ�host����Ȼ����������ĳ���
					GlobalVar.maxScore=usb[i].getScore();
					GlobalVar.maxHost=usb[i].getHost();
				}				
				
				MyFrame.Trace(">������������{��ַ:"+usb[i].getUrl()+",������:"+usb[i].getScore()+"}");
				TaskQueue.getInstance().offer(usb[i]);//ò���м�ֵ�Ķ�����͵���ӽ�������
				if (usb[i].getScore()<GlobalVar.filterScore||usb[i].getScore()==0||Double.isNaN(usb[i].getScore())) {
					continue;
				}
				
				
				  DBOperator.getInstance().inputRecord(usb[i]);//�������ݿ�����
		}
			//��������
//			System.out.println("�����̹߳ر�");
			MyFrame.Trace("���������̹߳ر�");
			al.clear();
	}
	public void toNotify() {
		 synchronized (obj) {
	            obj.notify();
	        }
	}

}
