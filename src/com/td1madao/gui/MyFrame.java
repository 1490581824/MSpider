package com.td1madao.gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.td1madao.global.GlobalVar;
/**
 * ��򵥵Ľ���
 * */
public class MyFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3216185994578075384L;
	public static boolean pause=false;
	JLabel te=new JLabel("����������Դ���棺");
	JCheckBox jCheckBox0 = new JCheckBox("�ٶ�",true);
	JCheckBox jCheckBox1 = new JCheckBox("360",true);
	JCheckBox jCheckBox2 = new JCheckBox("����",true);
	JCheckBox jCheckBox3 = new JCheckBox("�ȸ�",true);
	
	
	
	
	static JButton yesButton=new JButton(); 
	JTextField keyWord=new JTextField();
	JTextField user=new JTextField();
	JTextField password=new JTextField();
	JTextField database=new JTextField();
	JLabel keyWordJLabel=new JLabel("����ؼ���(�ո�ֿ�):");
	JLabel userJLabel=new JLabel("�����û���:");
	JLabel passwordJLabel=new JLabel("��������:");
	JLabel databaseLabel=new JLabel("�������ݿ���:");
	JPanel p1=new JPanel();
	JPanel p3=new JPanel();
	JPanel p2=new JPanel();
	JPanel p4=new JPanel();
	static TextArea t2=new TextArea();
	boolean alreadyrun=false;
public MyFrame() {
	setSize(480,320);
	setResizable(false);
	t2.setEditable(false);
	setLayout(new FlowLayout());
	user.setText(GlobalVar.username);
	password.setText(GlobalVar.password);
	database.setText(GlobalVar.db);
	this.addWindowListener(new WindowListener() {
		@Override
		public void windowOpened(WindowEvent e) {
		}
		
		@Override
		public void windowIconified(WindowEvent e) {
			
		}
		
		@Override
		public void windowDeiconified(WindowEvent e) {
			
		}
		
		@Override
		public void windowDeactivated(WindowEvent e) {
			
		}
		
		@Override
		public void windowClosing(WindowEvent e) {
			System.exit(0);
		}
		
		@Override
		public void windowClosed(WindowEvent e) {
			
		}
		
		@Override
		public void windowActivated(WindowEvent e) {
			
		}
	});
	setTitle("���һMADAOС����");
	
	keyWord.setText("JAVA ������ ��� ʵϰ 2014");
	p1.setLayout(new BorderLayout());
	p1.add(keyWordJLabel,BorderLayout.NORTH);
	p1.add(keyWord);
	p1.add(t2,BorderLayout.SOUTH);
	yesButton.setText("���ɣ�");
	yesButton.addActionListener(new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			if (alreadyrun) {
				pause=true;
				alreadyrun=false;
				yesButton.setText("����");
			}
			else if (!(GlobalVar.baidu||GlobalVar.google||GlobalVar.sousou||GlobalVar.qihu)) {
				Trace("������������һ���������档");
			}
			else if(keyWord.getText().equals("")){
				Trace("������ؼ���");
			}
			else if(user.getText().equals("")){
				Trace("���������ݿ��˺�");
			}
			else if(password.getText().equals("")){
				Trace("���������ݿ�����");
			}
			else if(database.getText().equals("")){
				Trace("��������Ҫд������ݿ⣬Ҫ�Լ��½�");
			}
			else if (yesButton.getText().equals("����")) {
				NoGui.notifys();
				yesButton.setText("��ͣ");
				pause=false;
				alreadyrun=true;
			} 
			else
			{
				NoGui.getInstance().init(keyWord.getText().split(" "));
				NoGui.getInstance().start();
				alreadyrun=true;
				yesButton.setText("��ͣ");
			}
		}
	});
	add(p1);
	jCheckBox0.addItemListener(new ItemListener() {
		JCheckBox jCheckBox;
		@Override
		public void itemStateChanged(ItemEvent e) {
			 jCheckBox = (JCheckBox) e.getSource();
			 if (jCheckBox.isSelected()) {
				 GlobalVar.baidu=true;
			 }else {
				 GlobalVar.baidu=false;
			}
		}
	});
	jCheckBox1.addItemListener(new ItemListener() {
		JCheckBox jCheckBox;
		@Override
		public void itemStateChanged(ItemEvent e) {
			 jCheckBox = (JCheckBox) e.getSource();
			 if (jCheckBox.isSelected()) {
				 GlobalVar.qihu=true;
			 }else {
				 GlobalVar.qihu=false;
			}
		}
	});
	jCheckBox2.addItemListener(new ItemListener() {
		JCheckBox jCheckBox;
		@Override
		public void itemStateChanged(ItemEvent e) {
			 jCheckBox = (JCheckBox) e.getSource();
			 if (jCheckBox.isSelected()) {
				 GlobalVar.sousou=true;
			 }else {
				 GlobalVar.sousou=false;
			}
		}
	});
	jCheckBox3.addItemListener(new ItemListener() {
		JCheckBox jCheckBox;
		@Override
		public void itemStateChanged(ItemEvent e) {
			 jCheckBox = (JCheckBox) e.getSource();
			 if (jCheckBox.isSelected()) {
				 GlobalVar.google=true;
			 }else {
				 GlobalVar.google=false;
			}
		}
	});
	
	
	p4.add(jCheckBox0);
	p4.add(jCheckBox1);
	p4.add(jCheckBox2);
	p4.add(jCheckBox3);
	add(p4);
	
	
	p3.add(userJLabel);
	p3.add(user);
	p3.add(passwordJLabel);
	p3.add(password);
	add(p3);
	p2.add(databaseLabel);
	p2.add(database);
	p2.add(yesButton);
	add(p2);
	Trace("ʹ�÷�����\nȷ��������ݿ��Ǵ��ڵģ�\n������ؼ��ʡ��˻������롢���ݿ���������Ϣ��\n");
	setVisible(true);
	repaint();
}

public static void Trace(String s){
	t2.append("\n"+s);
}
public static void main(String[] args) {
	new MyFrame();
}
}