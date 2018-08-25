package oonum9;

public class Tray {
	private int[][]nowlocation = new int[100][2];//出租车位置；
	private int[] credit = new int[100];
	private int[][] reaction = new int[100][6];
	private int requestnum = 0;
	private int[] init = new int[100];
	private int[] status = new int[100];
	public synchronized void init(int num) {
		/**
		 * @REQUIRES: num;
		 * @MODIFIES: init;
		 * @EFFECTS:  mark the car that needs to initialize;
		 */
		init[num] = 1;
	}
	public synchronized int getinit(int num) {
		/**
		 * @REQUIRES: num, init;
		 * @MODIFIES: none;
		 * @EFFECTS: get which car needs to initialize;
		 */
		return init[num];
	}
	public synchronized void setstatus(int num, int value) {
		/**
		 * @REQUIRES: num, value;
		 * @MODIFIES: status;
		 * @EFFECTS: initialize the status of the car;
		 */
		status[num] = value;
	}
	public synchronized int getstatus(int num) {
		/**
		 * @REQUIRES: num ,status[];
		 * @MODIFIES: none;
		 * @EFFECTS: get status of the car;
		 */
		return status[num];
	}
	public synchronized int[][] getnl(){
		/**
		 * @REQUIRES: nowlocation[][];
		 * @MODIFIES: none;
		 * @EFFECTS: get nowlocation[][];
		 */
		return nowlocation;
	}
	public synchronized int[] getnls(int num) {
		/**
		 * @REQUIRES: num, nowlocation[][];
		 * @MODIFIES: none;
		 * @EFFECTS: get the location of the car;
		 */
		return nowlocation[num];
	}
	public synchronized int[] getcr(){
		/**
		 * @REQUIRES: credit[];
		 * @MODIFIES: none;
		 * @EFFECTS: get credit[];
		 */
		return credit;
	}
	public synchronized int[][] getre(){
		/**
		 * @REQUIRES: reaction[];
		 * @MODIFIES: none;
		 * @EFFECTS: get reaction
		 */
		return reaction;
	}
	public synchronized void addnl(int i,int[] term) {
		/**
		 * @REQUIRES: i, term[];
		 * @MODIFIES: nowlocation[][];
		 * @EFFECTS: change the location of the car;
		 */
		nowlocation[i] = term;
	}
	public synchronized void addcr(int i, int cre) {
		/**
		 * @REQUIRES: i, cre;
		 * @MODIFIES: credit[];
		 * @EFFECTS: add credit of the car;
		 */
		credit[i] = credit[i]+cre;
	}
	public synchronized void change(int num) {
		/**
		 * @REQUIRES: num;
		 * @MODIFIES: reaction[][];
		 * @EFFECTS: mark the cr have been answered;
		 */
		reaction[num][5] =2;
	}
	public synchronized void addre(int i , int[] term1,int[] term2) {
		/**
		 * @REQUIRES: num, Point p1, p2;
		 * @MODIFIES: reaction[][];
		 * @EFFECTS:  save cr information to reaction[]; 
		 */
		reaction[requestnum][0] = i;
		reaction[requestnum][1] = term1[0];
		reaction[requestnum][2] = term1[1];
		reaction[requestnum][3] = term2[0];
		reaction[requestnum][4] = term2[1];
		reaction[requestnum][5] = 1;
		requestnum++;
	}

}
