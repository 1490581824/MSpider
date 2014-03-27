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
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	public static FileWriter fwFileWriter=null;
	public static BufferedWriter bWriter=null;
	private static final long serialVersionUID = 3216185994578075384L;
	public static boolean pause=false;
	JLabel te=new JLabel("����������Դ���棺");
	JLabel te2=new JLabel("���ҳ��URL��������Ÿ���(�ɿ�)��");
	JLabel te3=new JLabel("�������(�ɿգ���д�Ļ�������������������֧�������﷨)");
	JCheckBox jCheckBox0 = new JCheckBox("�ٶ�",true);
	JCheckBox jCheckBox1 = new JCheckBox("360",true);
	JCheckBox jCheckBox2 = new JCheckBox("����",true);
	JCheckBox jCheckBox3 = new JCheckBox("�ȸ�",true);
	
	
	
	static JButton yesButton=new JButton(); 
	JTextField keyWord=new JTextField();
	JTextField search=new JTextField();
	JTextField user=new JTextField();
	JTextField password=new JTextField();
	JTextField database=new JTextField();
	JTextField seed=new JTextField(20);
	JLabel keyWordJLabel=new JLabel("����ؼ���(�ո�ֿ�):");
	JLabel userJLabel=new JLabel("�����û���:");
	JLabel passwordJLabel=new JLabel("��������:");
	JLabel databaseLabel=new JLabel("�������ݿ���:");
	JPanel p1=new JPanel();
	JPanel p3=new JPanel();
	JPanel p2=new JPanel();
	JPanel p4=new JPanel();
	JPanel p5=new JPanel();
	JPanel p6=new JPanel();
	JPanel p7=new JPanel();
	static TextArea t2=new TextArea();
	boolean alreadyrun=false;
public MyFrame() {
	try {
		fwFileWriter=new FileWriter("data.log");
		bWriter=new BufferedWriter(fwFileWriter);
		bWriter.write(new Date().toString());
		bWriter.newLine();
	} catch (Exception e) {
		e.printStackTrace();
	}
	search.setText("MSpider ���һMADAO");
	setSize(480,420);
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
			try {			fwFileWriter.close();			bWriter.close();			} catch (Exception e2) {}
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
	
	keyWord.setText("MSpider");
	p1.setLayout(new BorderLayout());
	p6.setLayout(new BorderLayout());
	p7.setLayout(new BorderLayout());
	
	p6.add(keyWordJLabel,BorderLayout.NORTH);
	p6.add(keyWord);
	p7.add(te3,BorderLayout.NORTH);
	p7.add(search);
	p1.add(p6,BorderLayout.NORTH);
	p1.add(p7);
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
			else if ((!(GlobalVar.baidu||GlobalVar.google||GlobalVar.sousou||GlobalVar.qihu))&&seed.getText().length()<4) {
				Trace("����������������棬���ٸ����������Ӱɣ�");
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
				
				boolean b=NoGui.getInstance().init(keyWord.getText());
				if (!b) {
					JOptionPane.showConfirmDialog(null, "������ı��ʽ���Ϸ���","��ʾ:", JOptionPane.OK_OPTION);
					return;
				}
				GlobalVar.seed=seed.getText();//��������������
				String temp=search.getText().trim();
				if (temp.length()>0) {
					while(temp.contains("  ")){
					temp=temp.replaceAll("  ", " ");
					}
					temp=temp.replaceAll(" ", "+");
					GlobalVar.searchCont=new String(temp);
				}
				NoGui.getInstance().start();
				alreadyrun=true;
				yesButton.setText("��ͣ");
			}
		}
	});
	add(p1);
	
	p5.add(te2);
	p5.add(seed);
	add(p5);
	
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
	
	Trace("ʹ�÷�����\nȷ��������ݿ��Ǵ��ڵģ�\n������ؼ��ʡ��˻������롢���ݿ���������Ϣ��\n����:ͨ�����ſ��Զ���ؼ��ʵ�Ȩ�غ��Ƿ������� \n\n����\"JAVA ������ ��� ʵϰ(4,true) 2014\"\n\n��ʾʵϰ��Ȩ��Ϊ4���ұ����������г���,Ĭ�ϴʵ�Ȩ��Ϊ1\n��ʽ���Ҫ��ȷ��������ֻ��Ϊ(��ʵ��,[true/false])");

	setVisible(true);
	repaint();
}

public static void Trace(String s){
	t2.append("\n"+s);
	try {
		bWriter.append(s);
		bWriter.newLine();
		bWriter.flush();
	} catch (Exception e) {
		e.printStackTrace();
	}
}
public static void main(String[] args) {
	new MyFrame();
}
}