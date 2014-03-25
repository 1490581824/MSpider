package com.td1madao.db;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.jdbc.MysqlDataTruncation;
import com.td1madao.bean.UrlScoreBean;
import com.td1madao.global.GlobalVar;
import com.td1madao.gui.MyFrame;
/**
 * ר�Ÿ����ݿ����
 * 
 * ������Ҳ�ǵ���ģʽ
 * 
 * */
public class DBOperator {
	 private static DBOperator uniqueInstance = new DBOperator();
	private static  Connection conn=null;
	private static String sql = null;
	private static ResultSet rs = null;
	private static PreparedStatement pstmt=null; 
	private static Statement stmt=null; 
	private DBOperator(){}
	public static DBOperator getInstance() {

		String driver = "com.mysql.jdbc.Driver";
         try { 
        	 Class.forName(driver);
           conn = DriverManager.getConnection(GlobalVar.db, GlobalVar.username, GlobalVar.password);
           stmt=conn.createStatement();
         }catch(SQLException e)
         {
        	 MyFrame.Trace("��������������ݿ��Ƿ�����");
         }
         catch(Exception e) {
             e.printStackTrace();
         } 
        return uniqueInstance;
}
//	public DBOperator() {}
	
	/**
	 * ������host��Ϊ����д洢������.����������
	 * �����.ȫ����-
	 * */
	private static String hostFix(final String string) {
		return string.replace(".", "_");
	}
	/**
	 * �жϱ��Ƿ���ڣ���������ڣ����½���
	 * */
	private static void testTable(final String tableName){
		sql="CREATE TABLE IF NOT EXISTS `"+hostFix(tableName)+"` (  `Id` int(11) NOT NULL AUTO_INCREMENT,  `url` varchar(255) CHARACTER SET utf8 NOT NULL DEFAULT '',  `score` double NOT NULL DEFAULT '0',  `child` blob ,  PRIMARY KEY (`Id`))Engine =InnoDB DEFAULT CHARSET=latin1";
		try {
            stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * �ж������ַ�Ƿ���ڣ�������ڣ���true
	 * */
	private static boolean select(final UrlScoreBean bean) {
	         sql = "select * from "+hostFix(bean.getHost())+" where url= '"+bean.getUrl()+"'";
	        try {
	            rs = stmt.executeQuery(sql);
	            if(rs.next()){
	            	return true;
	            }
	        }catch (Exception e){
	           e.printStackTrace();
	        }
	        return false;
	    }
	/**
	 * д������
	 * */
	private static void insert(UrlScoreBean bean) {
		sql="INSERT INTO `"+hostFix(bean.getHost())+"` VALUES (0,'"+bean.getUrl()+"',"+bean.getScore()+",?);";
	        try {
	        	pstmt = conn.prepareStatement(sql);
	        	 if (bean.child==null) {
	        		 pstmt.setObject(1,Types.BLOB);//�Ӵ����Կ�
				}else{
					pstmt.setObject(1,bean.child);
				}
		         pstmt .executeUpdate();          
	        }
	        catch(MysqlDataTruncation e){
	        	if (GlobalVar.notice) {
	        		System.err.println("���޸�MySQL�� my.ini�ļ���ȥ��\"STRICT_TRANS_TABLES,\",Ĭ���ַ�����֧������,�����޷����������¼");
	        		MyFrame.Trace("������ݿⲻ֧�����ģ�������Ϣ���ܱ��档���޸�MySQL�� my.ini�ļ���ȥ��\"STRICT_TRANS_TABLES,\"");
	        		GlobalVar.notice=false;
				}
	        }
	        catch (Exception e){
	           e.printStackTrace();
	        }
	    }	
	
	/**
	 * ���⿪�ŵķ�����ֱ�Ӱ������ӽ����ݿ�
	 * 
	 * ���û��ͽ���
	 * ��������򲻹�
	 * ��������ھͲ�������
	 * ���ݽṹ 
	 * 
	 * host{
	 * score
	 * url
	 * child
	 * }
	 * */
	public synchronized  boolean inputRecord(UrlScoreBean bean) {
		boolean succ=false;
		testTable(bean.getHost());
		if (!select(bean)) {
			insert(bean);
			succ=true;
		}
		return succ;
	}
	
public static void main(String[] args){
	
DBOperator dbOperator=getInstance(); 
dbOperator.inputRecord(new UrlScoreBean(0.3, "www.hoho.com",new ArrayList<String>()));
} 
}