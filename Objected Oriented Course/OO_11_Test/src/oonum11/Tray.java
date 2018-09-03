package oonum11;

public class Tray {
	/**
	 * @OVERVIEW: 该类是托盘类，用来存放各种需要交互的数据;
	 * @INHERIT: none;
	 */
	private int[][]nowlocation = new int[100][2];//出租车位置；
	private int[] credit = new int[100];
	private int[][] reaction = new int[100][6];
	private int requestnum = 0;
	private int[] init = new int[100];
	private int[] status = new int[100];
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: none;
	 * @EFFECTS: none;
	 */
	public boolean repOK() {
		int i,j;
		for(i = 0;i<100;i++) {
			for(j = 0;j<2;j++) {
				if(nowlocation[i][j]>79||nowlocation[i][j]<0) return false;
			}
		}
		for(i = 0; i<100;i++) {
			if(credit[i]<0) return false;
			if(status[i]<0) return false;
			if(init[i]<0) return false;
		}
		return true;
	}
	/**
	 * @REQUIRES: num>=0 && num<100;
	 * @MODIFIES: this.init;
	 * @EFFECTS:  none;
	 */
	public synchronized void init(int num) {
		
		init[num] = 1;
	}
	/**
	 * @REQUIRES: num>=0 && num<100;
	 * @MODIFIES: none;
	 * @EFFECTS: none;
	 */
	public synchronized int getinit(int num) {
		
		return init[num];
	}
	/**
	 * @REQUIRES: num>=0 && num<100;
	 * @MODIFIES: this.status;
	 * @EFFECTS: none;
	 */
	public synchronized void setstatus(int num, int value) {
		
		status[num] = value;
	}
	/**
	 * @REQUIRES: num>=0 && num<100;
	 * @MODIFIES: none;
	 * @EFFECTS: none;
	 */
	public synchronized int getstatus(int num) {

		return status[num];
	}
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: none;
	 * @EFFECTS: none;
	 */
	public synchronized int[][] getnl(){

		return nowlocation;
	}
	/**
	 * @REQUIRES: num>=0 && num<100;
	 * @MODIFIES: none;
	 * @EFFECTS: none;
	 */
	public synchronized int[] getnls(int num) {

		return nowlocation[num];
	}
	
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: none;
	 * @EFFECTS: none;
	 */
	public synchronized int[] getcr(){

		return credit;
	}
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: none;
	 * @EFFECTS: none;
	 */
	public synchronized int[][] getre(){

		return reaction;
	}
	/**
	 * @REQUIRES: i>=0 && i<100;
	 * @MODIFIES: this.nowlocation;
	 * @EFFECTS: this.nowlocation!=\old(this).nowlocation;
	 */
	public synchronized void addnl(int i,int[] term) {

		nowlocation[i] = term;
	}
	/**
	 * @REQUIRES: i>=0 && i<100;
	 * @MODIFIES: this.credit;
	 * @EFFECTS: this.credit!=\old(this).credit;
	 */
	public synchronized void addcr(int i, int cre) {

		credit[i] = credit[i]+cre;
	}
	/**
	 * @REQUIRES: num>=0 && num<100;
	 * @MODIFIES: this.reaction;
	 * @EFFECTS: this.reaction!=\old(this).reaction;
	 */
	public synchronized void change(int num) {

		reaction[num][5] =2;
	}
	/**
	 * @REQUIRES: i>=0 && i<100;
	 * @MODIFIES: this.reaction;
	 * @EFFECTS:  this.reaction!=\old(this).reaction; 
	 */
	public synchronized void addre(int i , int[] term1,int[] term2) {
		
		reaction[requestnum][0] = i;
		reaction[requestnum][1] = term1[0];
		reaction[requestnum][2] = term1[1];
		reaction[requestnum][3] = term2[0];
		reaction[requestnum][4] = term2[1];
		reaction[requestnum][5] = 1;
		requestnum++;
	}

}
