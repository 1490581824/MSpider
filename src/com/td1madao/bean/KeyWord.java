package com.td1madao.bean;
/**
 * ���ؼ�����Ϊһ�����ݽṹ
 * */
public class KeyWord {
	
	private String name=null;//�ؼ���
private double weight=0;//�ؼ��ʵ�Ȩ�أ�Ȩ��Խ������Խ��
private boolean necessary=false;//�Ƿ�������(�����ڽ��ᱻ����)
public KeyWord(String name,double weight,boolean necessary) {
	this.setName(name);
	this.setWeight(weight);
	this.setNecessary(necessary);
}
public double getWeight() {
	return weight;
}
public void setWeight(double weight) {
	this.weight = weight;
}
public boolean isNecessary() {
	return necessary;
}
public void setNecessary(boolean necessary) {
	this.necessary = necessary;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
@Override
public String toString() {
	return "("+name+","+weight+","+necessary+")";
}
}
