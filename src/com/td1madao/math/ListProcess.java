package com.td1madao.math;

import java.util.List;
/**
 * ArrayList�õķ��㣬
 * ����ʱ�����������
 * ����Set����
 * 
 * ����list���ܶ����final��������
 * */
public class ListProcess {
	public static void removeDuplicate(List<?> list){
		for (int i=0;i<list.size()-1;i++) {
		for(int j=list.size()-1;j>i;j--){
		if(list.get(j).equals(list.get(i))){list.remove(j);}
		}
		}
		}
}
