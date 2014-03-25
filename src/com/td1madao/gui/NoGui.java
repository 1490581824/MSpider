package com.td1madao.gui;


import javax.swing.JOptionPane;

import com.td1madao.global.GlobalVar;
import com.td1madao.global.TaskQueue;
import com.td1madao.threads.DaemonThread;
import com.td1madao.threads.MEngine;
import com.td1madao.threads.MSpider;

/**
 * GUI�е㷳�������÷�GUI��������
 * 
 * */
public class NoGui extends Thread{
 String args2[]=null;
 boolean pause=false;
 public static MSpider[] array=new MSpider[GlobalVar.spiderNum];//���滹��ű���̡߳���Ҳ�п���
private static NoGui uniqueInstance = new NoGui();
	
	private NoGui() {
	}
	
	public static NoGui getInstance() {
        return uniqueInstance;
}
	public  void init(String[] args) {
		args2=args;
	}
	public void run() {
		MyFrame.yesButton.setEnabled(false);
		if (args2.length==0||args2[0]=="") {
			GlobalVar.keyStrings=new String[]{"����","������ʱ"};//����Ҳ���Զ��������ؼ���
		}
		else {
			GlobalVar.keyStrings=args2.clone();
		}
		
		MEngine.getInstance().run();//�����Ϊ�˳�ʼ�������ӣ�ǧ��ҪstartŶ��
		if (TaskQueue.getInstance().size()==0) {
			JOptionPane.showConfirmDialog(null, "����ʧ�ܣ�������õ��ǹȸ裬�뻻�ñ���������ԣ�","��ʾ:", JOptionPane.OK_OPTION);
			System.exit(0);
		}
		MyFrame.yesButton.setEnabled(true);
//		System.out.println("�õ���"+TaskQueue.getInstance().size()+"���������ӣ�");
		MyFrame.Trace("�õ���"+TaskQueue.getInstance().size()+"���������ӣ�");
		MEngine.getInstance().start();
		MEngine.getInstance().flag=false;
	    
	    for (int i = 0; i < array.length; i++) {
			array[i]=new MSpider(i);
			array[i].start();
		}
	    DaemonThread.getInstance().setDaemon(true);//������������յĺ�̨�߳�
	    DaemonThread.getInstance().start();//��̨�߳�
	}

	public static void notifys() {
		// TODO Auto-generated method stub
		array[0].toNotify();
		MEngine.getInstance().toNotify();
		 DaemonThread.getInstance().toNotify();
	}

}
