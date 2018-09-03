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
 		
	public RequestQueue() {
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: None
		 */
	}	
	public synchronized void addReq(String str,int srcx, int srcy, int dstx,int dsty, long time) { //添加请求
		/** @REQUIRES: None
		 * @MODIFIES: str,srcx,srcy,dstx,dsty,times,used,cnt
		 * @EFFECTS: None
		 */
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
		/** @REQUIRES: !empty(str)
		 * @MODIFIES: None
		 * @EFFECTS: \result == str.get(i)
		 */
		return this.str.get(i);
	}
	public synchronized int getSrcx(int i) {
		/** @REQUIRES: !empty(srcx)
		 * @MODIFIES: None
		 * @EFFECTS: \result == srcx.get(i)
		 */
		return this.srcx.get(i);
	}
	public synchronized int getSrcy(int i) {
		/** @REQUIRES: !empty(srcy)
		 * @MODIFIES: None
		 * @EFFECTS: \result == srcy.get(i)
		 */
		return this.srcy.get(i);
	}
	public synchronized int getDstx(int i) {
		/** @REQUIRES: !empty(dstx);
		 * @MODIFIES: None;
		 * @EFFECTS: \result == dstx.get(i);
		 */
		return this.dstx.get(i);
	}
	public synchronized int getDsty(int i) {
		/** @REQUIRES: !empty(dsty);
		 * @MODIFIES: None;
		 * @EFFECTS: \result == dsty.get(i);
		 */
		return this.dsty.get(i);
	}
	public synchronized long getTime(int i) {
		/** @REQUIRES: !empty(times);
		 * @MODIFIES: None;
		 * @EFFECTS: \result == times.get(i);
		 */
		return this.times.get(i);
	}
	public synchronized boolean getUsed(int i) {
		/** @REQUIRES: !empty(used);
		 * @MODIFIES: None;
		 * @EFFECTS: \result == used.get(i);
		 */
		return this.used.get(i);
	}
	public synchronized int getCnt() {
		/** @REQUIRES: !empty(cnt);
		 * @MODIFIES: None;
		 * @EFFECTS: \result == cnt.get(i);
		 */
		return this.cnt;
	}
	public synchronized ArrayList<Boolean> changeUsed(){
		/** @REQUIRES: !empty(used);
		 * @MODIFIES: None;
		 * @EFFECTS: \result == used.get(i);
		 */
		return this.used;
	}
}
