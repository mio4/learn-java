package Taxi;

import java.util.ArrayList;
import java.util.Arrays;

//请求队列
public class RequestQueue {
	/* Overview: 合法请求的请求队列，包括请求出发点、目的地以及请求时间
	 *
	 */
	//与请求相关的属性存储到ArrayList中
	private ArrayList<String> str = new ArrayList<String>();
	private ArrayList<Integer> srcx = new ArrayList<Integer>();
	private ArrayList<Integer> srcy = new ArrayList<Integer>();
	private ArrayList<Integer> dstx = new ArrayList<Integer>();
	private ArrayList<Integer> dsty = new ArrayList<Integer>();
	private ArrayList<Long> times = new ArrayList<Long>();
	private ArrayList<Boolean> used  = new ArrayList<Boolean>(); //请求是否被使用
	private int cnt = 0;

	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: None;
	 */
	public RequestQueue() {
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @Effects : \result == invariant(this);
	 */
	public boolean repOK(){
		if(srcx==null || srcy==null || dstx==null || dsty==null)
			return false;
		return true;
	}

	/** @REQUIRES: None;
	 * @MODIFIES: this.str,this.srcx,this.srcy,this.dstx,this.dsty,this.times,this.used,this.cnt;
	 * @EFFECTS: None;
	 */
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

	/** @REQUIRES: empty(str)==false;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == str.get(i);
	 */
	public synchronized String getStr(int i) {
		return this.str.get(i);
	}

	/** @REQUIRES: empty(srcx)==false；
	 * @MODIFIES: None；
	 * @EFFECTS: \result == srcx.get(i)；
	 */
	public synchronized int getSrcx(int i) {

		return this.srcx.get(i);
	}

	/** @REQUIRES: empty(srcy)==false;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == srcy.get(i);
	 */
	public synchronized int getSrcy(int i) {
		return this.srcy.get(i);
	}

	/** @REQUIRES: empty(dstx)==false;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == dstx.get(i);
	 */
	public synchronized int getDstx(int i) {
		return this.dstx.get(i);
	}

	/** @REQUIRES: empty(dsty)==false;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == dsty.get(i);
	 */
	public synchronized int getDsty(int i) {
		return this.dsty.get(i);
	}

	/** @REQUIRES: empty(times)==false;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == times.get(i);
	 */
	public synchronized long getTime(int i) {
		return this.times.get(i);
	}

	/** @REQUIRES: empty(used)==false;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == used.get(i);
	 */
	public synchronized boolean getUsed(int i) {
		return this.used.get(i);
	}

	/** @REQUIRES: empty(cnt)==false;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == cnt.get(i);
	 */
	public synchronized int getCnt() {

		return this.cnt;
	}

	/** @REQUIRES: empty(used)==false;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == used.get(i);
	 */
	public synchronized ArrayList<Boolean> changeUsed(){
		return this.used;
	}
}
