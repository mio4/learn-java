package Taxi;

import java.util.ArrayList;
import java.util.Arrays;

//请求队列
public class RequestQueue {
	//与请求相关的属性存储到ArrayList中
	private ArrayList<String> str = new ArrayList<String>();
	private ArrayList<Integer> srcx = new ArrayList<Integer>();
	private ArrayList<Integer> srcy = new ArrayList<Integer>();
	private ArrayList<Integer> dstx = new ArrayList<Integer>();
	private ArrayList<Integer> dsty = new ArrayList<Integer>();
	private ArrayList<Long> times = new ArrayList<Long>();
	private ArrayList<Boolean> used  = new ArrayList<Boolean>(); //请求是否被使用
	private int cnt = 0;
 		
	public RequestQueue() { //构造器在这里暂时不使用...
		
	}	
	public synchronized void addReq(String str,int srcx, int srcy, int dstx,int dsty, long time) { //添加请求
		this.str.add(str);
		this.srcx.add(srcx);
		this.srcy.add(srcy);
		this.dstx.add(dstx);
		this.dsty.add(dsty);
		this.times.add(time);
		this.used.add(false);
		this.cnt++;
	}
	//取出队列中元素
	public synchronized String getStr(int i) {
		return this.str.get(i);
	}
	public synchronized int getSrcx(int i) {
		return this.srcx.get(i);
	}
	public synchronized int getSrcy(int i) {
		return this.srcy.get(i);
	}
	public synchronized int getDstx(int i) {
		return this.dstx.get(i);
	}
	public synchronized int getDsty(int i) {
		return this.dsty.get(i);
	}
	public synchronized long getTime(int i) {
		return this.times.get(i);
	}
	public synchronized boolean getUsed(int i) {
		return this.used.get(i);
	}
	public synchronized int getCnt() {
		return this.cnt;
	}
	public synchronized ArrayList<Boolean> changeUsed(){
		return this.used;
	}
}
