package com.td1madao.threads;

import com.td1madao.global.GlobalVar;
import com.td1madao.global.TaskQueue;
import com.td1madao.gui.MyFrame;

/**
 * ������Ȼ����
 * �����Ǹ�����Ҫ��Ļ����֡�����̨�߳�
 * �縺���������/����/��������������ش���
 * 
 * ��Ȼ����Ҳ�ǵ�����������һȺ���������̰߳�(��o��)
 * 
 * ������������������90%
 * ˵��Ҫ��̫���ˣ��������˷ѣ������һ��Ҫ�󣬹��˵���ضȵ͵������� ��Ҳִ����������(���������հ�)
 * 
 * ���������С��30%��˵�������еûţ��ż���Ϊ0��ֻҪ��ؾ�Ҫ(զ�о���ĳЩ��˾�б���ʵϰ���Ƶģ�˵��ȫ���ᰡ)
 * ˳����������������̣�������������
 * 
 * ����������50%��˵���������ã��������������̹߳�������
 * 
 * */
public class DaemonThread extends Thread {
	int taskNum=0;
int queueLen=0;
private static Object obj = new Object();
	private static DaemonThread uniqueInstance = new DaemonThread();
	private DaemonThread(){}
	public static DaemonThread getInstance() {
        return uniqueInstance;
}
	
	public void run() {
		MyFrame.Trace("��̨�߳�����");
//		System.out.println("��̨�߳�����");
		while (true) {
			if (MyFrame.pause) {
			    synchronized (obj) {
		            try {
		                obj.wait();
		            } catch (InterruptedException e) {
		                System.out.println(getName() + " Test Thread Interrupted");
		            }
		        }
			}
			if (taskNum!=TaskQueue.getInstance().size()) {
				taskNum=TaskQueue.getInstance().size();
//				System.out.println("��ǰ��������"+taskNum+"/"+GlobalVar.queueLength);
				MyFrame.Trace("��ǰ��������"+taskNum+"/"+GlobalVar.queueLength);
			}
			queueLen=TaskQueue.getInstance().size();
			double percentage=(double)queueLen/GlobalVar.queueLength;
			if (percentage<=0.2) {//����̫��
				//���������̣߳����ͳɱ�
				try {
					MEngine.getInstance().start();//������������
				} catch (Exception e) {
				}
				GlobalVar.filterScore=0;
			}else if (percentage<=0.5) {
				//�ر������߳�
				MEngine.getInstance().flag=false;//�����������������
			}else if(percentage>=0.9){
				//��߳ɱ���������������
				GlobalVar.filterScore=TaskQueue.getInstance().getHigherQuality();
				//��ʵҲ���Կ��Ǹ��������ݣ���Ȼ���Һ���Ĵ�����
			}
			yield();
			try {
				wait(1000);
			} catch (Exception e) {
			}
			}
		}
	public void toNotify() {
		  synchronized (obj) {
	            obj.notify();
	        }
	}
	}
