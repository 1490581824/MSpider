package com.td1madao.gui;


import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.td1madao.bean.KeyWord;
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
	KeyWord args2[]=null;
 boolean pause=false;
 public static MSpider[] array=new MSpider[GlobalVar.spiderNum];//���滹��ű���̡߳���Ҳ�п���
private static NoGui uniqueInstance = new NoGui();
	
	private NoGui() {
	}
	
	public static NoGui getInstance() {
        return uniqueInstance;
}
	
	/**
	 * �ѹؼ����﷨����ΪKeyWord�ṹ������Ҫ�Ĳ���
	 * 
	 * */
	public  boolean init(String s) {
		ArrayList<KeyWord> als=new ArrayList<KeyWord>();
		String s2[]=s.split(" ");
		ArrayList<String> aList=new ArrayList<String>();
		for (int i = 0; i < s2.length; i++) {
			if (s2[i].length()!=0) {
				aList.add(s2[i].trim());
			}
		}
		//�õ��ؼ���
		String temp;
		for (int i = 0; i < aList.size(); i++) {
			KeyWord kw = null;
			temp=aList.get(i);
			
			String[] result = temp.split(",");
			int count = result.length - 1;
			
			if (temp.contains("(")&&temp.contains(")")&&count==1) {
				String name=temp.substring(0,temp.indexOf("("));
				String getString[]=temp.substring(temp.indexOf("(")+1,temp.lastIndexOf(")")).split(",");
				try {
					kw=new KeyWord(name, Double.parseDouble(getString[0]), Boolean.parseBoolean(getString[1]));
					if (Double.parseDouble(getString[0])<=0) {
						return false;
					}
				} catch (Exception e) {
					return false;
				}
			}
			else if (!temp.contains("(")&&!temp.contains(")")) {
				kw=new KeyWord(temp, 1,false);
			}
			else {
				return false;
			}
			//�õ�һ���ؼ���
			als.add(kw);
		}
		args2= (KeyWord[])als.toArray(new KeyWord[als.size()]);
		return true;
	}
	public void run() {
		MyFrame.yesButton.setEnabled(false);
		if (args2.length==0||args2==null) {
			GlobalVar.keyStrings=new  KeyWord[]{new KeyWord("����",1, true),new KeyWord("������ʱ",5, false)};//����Ҳ���Զ��������ؼ���
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
