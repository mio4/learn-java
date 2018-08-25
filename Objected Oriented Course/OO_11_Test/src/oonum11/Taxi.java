package oonum11;

import java.util.Random;
import java.util.ArrayList;
import java.awt.Point;
public class Taxi extends Thread {
	/**
	 * @OVERVIEW: 该类是出租车类，控制出租车的移动;
	 * @INHERIT: Thread;
	 */
	  protected int num;
      protected int state = 2;//服务状态 -0  接单状态 -1 等待状态 -2 停止状态 -3
      protected int[] location = new int[2];
      protected TaxiGUI gui;
      protected mapInfo map;
      protected int credit = 0;
      protected Tray tray;
      protected guiInfo gi;
      protected Putout putout;
      protected guigv gv;
      protected Light li;
      protected char turn = '0';
      protected int[][] plocation = new int[1000][2];
      protected long[] time = new long[1000];
      protected int movek = 0;
      /**
  	 * @REQUIRES: none;
  	 * @MODIFIES: none;
  	 * @EFFECTS: none;
  	 */
       public boolean repOK() {
  		if(gui == null) return false;
  		if(map == null) return false;
  		if(tray == null) return false;
  		if(gi == null) return false;
  		if(putout == null) return false;
  		if(num<0||num>=100) return false;
  		if(state>3||state<0) return false;
  		if(location[0]<0||location[0]>79) return false;
  		if(location[1]<0||location[1]>79) return false;
  		if(credit<0) return false;
  		return true;
      }
      /**
		 * @REQUIRES: none;
		 * @MODIFIES: this;
		 * @EFFECTS: none;
		 */
      public Taxi(int num, TaxiGUI gui, mapInfo map,Tray tray,guiInfo gi, Putout putout, guigv gv, Light li) {
    	  
    	  this.num = num;
    	  this.gui = gui;
    	  this.map = map;
    	  this.tray = tray;
    	  this.gi = gi;
    	  this.putout = putout;
    	  this.gv = gv;
    	  this.li = li;
    	  Random r = new Random();
    	  if(tray.getinit(num) == 1) {
    		  location[0] = tray.getnls(num)[0];
    		  location[1] = tray.getnls(num)[1];
    		  credit = tray.getcr()[num];
    		  state = tray.getstatus(num);
    	  }
    	  else {
    	  location[0] = Math.abs(r.nextInt()%80);
    	  location[1] = Math.abs(r.nextInt()%80);
    	  }
    	  tray.addnl(num,location);
    	  Point p = new Point(location[0],location[1]);
    	  gui.SetTaxiStatus(num, p, state);
    	  if(state!=2) {
			  try {
    			  sleep(1000);
    		  }catch(InterruptedException e) {}
		  }
    	  state = 2;
    	  gui.SetTaxiStatus(num, p, 2);
      }
      /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
      public int gets() {
    	  
    	  return state;
      }
      /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
      public int[] getlo() {

    	  return location;
      }
     /* public void state2() {
    	  int[][] re;
    	  int[][] term = new int[4][3];
    	  boolean judge = true;
    	  int i = 0,j = 0,k;
    	  while(state == 2) {
    		  j++;
    		  if(j == 40) {
    			  state = 0;
    			  Point p = new Point(location[0],location[1]);
    	    	  gui.SetTaxiStatus(num, p, state);
    			  try {
        			  sleep(1000);
        		  }catch(InterruptedException e) {}
    			  j=0;
    			  continue;
    		  }
    		  Point p = new Point(location[0],location[1]);
    		  term = map.getpoint(p);
    		  judge = true;
    		  while( judge == true) {
    			  Random r= new Random();
    			  i = Math.abs(r.nextInt()%4);
    			  if(term[i][2] == 1) {
    				  judge = false;
    			  }
    		  }
    		  p = new Point(term[i][0],term[i][1]);
    		  try {
    			  sleep(200);
    		  }catch(InterruptedException e) {}
    		  gui.SetTaxiStatus(num, p, 2);
    		  location[0] = p.x;
    		  location[1] = p.y;
    		  tray.addnl(num,location);
    		  re = tray.getre();
    		  for(k =0;re[k][5]!= 0;k++) {
    			  if(re[k][0] == num&&re[k][5]==1) break;
    		  }
    		  tray.change(k);
    		  state = 3;
    	  }
      }*/
      /**
		 * @REQUIRES: (\exist x1,y1,x2,y2, dis);
		 * @MODIFIES: this.location, this.tray.location;
		 * @EFFECTS:  this.location != \old(this).location;
		 */
      public void move(int x1,int y1,int x2,int y2,int dis) {
    	  System.out.println(dis);
    	  if(dis == 0) {
    		  putout.p3(num, movek, plocation, time);
    		  return;
    	  }
    	  ArrayList<Point> ar = gi.way(x1, y1, x2, y2, dis);
    	  //int[][] plocation = new int[1000][2];
    	  //long[] time = new long[1000];
    	  int i  = ar.size()-1;
    	  //int k = 0;
    	  Point p1, p2;
    	  p1 = new Point(x1,y1);
    	  //for(;i>=0;i--) {
    		  p2 = ar.get(i);
    		  waitlight(p1,p2);
    		  try {
    			  sleep(500);
    		  }catch(InterruptedException e) {}
    		  gui.SetTaxiStatus(num, ar.get(i), state);
    		  changeturn(p1.x,p1.y,p2.x,p2.y);
    		  //p1 = p2;
    		  location[0] = ar.get(i).x;
    		  location[1] = ar.get(i).y;
    		  plocation[movek][0] = location[0];
    		  plocation[movek][1] = location[1];
    		  time[movek] = System.currentTimeMillis();
    		  movek++;
    		  tray.addnl(num, location);
    	  int dis1 = gi.distance(p2.x, p2.y, x2, y2);
    	  System.out.println(dis1+"dis1");
    	  move(p2.x, p2.y, x2, y2,dis1);


      }
      /**
		 * @REQUIRES: (\exist x1,y1,x2,y2, dis);
		 * @MODIFIES: this.location, this.tray.location;
		 * @EFFECTS:  this.location != \old(this).location;
		 */
      public void move1(int x1,int y1,int x2,int y2,int dis) {
       	  if(dis == 0) {
       		  putout.p3(num, movek, plocation, time);
       		  return;
       	  }
       	  ArrayList<Point> ar = gi.way1(x1, y1, x2, y2, dis);
       	  //int[][] plocation = new int[1000][2];
       	  //long[] time = new long[1000];
       	  int i  = ar.size()-1;
       	  //int k = 0;
       	  Point p1, p2;
       	  p1 = new Point(x1,y1);
       	  //for(;i>=0;i--) {
       		  p2 = ar.get(i);
       		  waitlight(p1,p2);
       		  try {
       			  sleep(500);
       		  }catch(InterruptedException e) {}
       		  gui.SetTaxiStatus(num, ar.get(i), state);
       		  changeturn(p1.x,p1.y,p2.x,p2.y);
       		  //p1 = p2;
       		  location[0] = ar.get(i).x;
       		  location[1] = ar.get(i).y;
       		  plocation[movek][0] = location[0];
       		  plocation[movek][1] = location[1];
       		  time[movek] = System.currentTimeMillis();
       		  movek++;
       		  tray.addnl(num, location);
       	  int dis1 = gi.distance1(p2.x, p2.y, x2, y2);
       	  move1(p2.x, p2.y, x2, y2,dis1);


         }
      /**
		 * @REQUIRES: (\exist p1,p2);
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
      public void waitlight(Point p1, Point p2) {
    	 while(true) {
    	  if(turn == '0') break;
    	  if(li.getlight(p1.x,p1.y) == 0) break;
    	  else if(turn == 'n') {
    		  if(li.getstatus() == 1) { 
    			  if(p1.x == p2.x) break;
    		  }
    		  else if(li.getstatus() == 2) {
    			  if(!(p1.x == p2.x&&p1.y>p2.y)) break;
    		  }
    	  }
    	  else if(turn == 's') {
    		  if(li.getstatus() == 1) { 
    			  if(p1.x == p2.x) break;
    		  }
    		  else if(li.getstatus() == 2) {
    			  if(!(p1.x == p2.x&&p1.y<p2.y)) break;
    		  }
    	  }
    	  else if(turn == 'w') {
    		  if(li.getstatus() == 2) { 
    			  if(p1.y == p2.y) break;
    		  }
    		  else if(li.getstatus() == 1) {
    			  if(!(p1.y == p2.y&&p1.x<p2.x)) break;
    		  }
    	  }
    	  else if(turn == 'e') {
    		  if(li.getstatus() == 2) { 
    			  if(p1.y == p2.y) break;
    		  }
    		  else if(li.getstatus() == 1) {
    			  if(!(p1.y == p2.y&&p1.x>p2.x)) break;
    		  }
    	  }
    	  try {
			  sleep(5);
		  }catch(InterruptedException e) {}
    	  }
    	} 
      /**
		 * @REQUIRES: (\exist x1,y1,x2,y2);
		 * @MODIFIES: this.turn;
		 * @EFFECTS: none;
		 */
      public void changeturn(int x1, int y1, int x2, int y2) {
    	  if(x1 == x2) {
    		  if(y1>y2) turn = 'w';
    		  else turn = 'e';
    	  }
    	  else if(y1 == y2) {
    		  if(x1>x2) turn = 'n';
    		  else turn = 's';
    	  }
      }
      /**
		 * @REQUIRES: (\exist x1,y1,x2,y2);
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
    public char getturn(int x1, int y1, int x2, int y2) {
  	  if(x1 == x2) {
  		  if(y1>y2) return  'w';
  		  else return  'e';
  	  }
  	  else if(y1 == y2) {
  		  if(x1>x2) return  'n';
  		  else return  's';
  	  }
  	  return '0';
    }
      /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS:  none;
		 */
      public void run() {
    	 
    	  int[][] re = new int[100][6];
    	  int[] offset = {-1, -80, 80, 1};
    	  int[] available = new int[4]; 
    	  int[][] term = new int [4][3];
    	  int minflow = 1000;
    	  int avainum = 0;
     	  boolean judge = true;
     	  long t = System.currentTimeMillis();
    	  int i = 0,j = 0,k =0 ;
      while(true) {
    	  j = 0;
    	  k = 0;
    	  while(state == 2) {
    		  j++;
    		  if(j == 40) {
    			  state = 3;
    			  Point p = new Point(location[0],location[1]);
    	    	  gui.SetTaxiStatus(num, p, state);
    			  try {
        			  sleep(1000);
        		  }catch(InterruptedException e) {}
    			  j=0;
    			  state = 2;
    			  continue;
    		  }
    		  Point p = new Point(location[0],location[1]);
    		  Point p1 = new Point(location[0],location[1]);
    		  
    		  t = System.currentTimeMillis();
    		  term = gi.getpoint1(p);
    		  
    		  for(i= 0; i<4; i++) {
    			  if(term[i][2] == 1&&minflow>gv.GetFlow(p.x, p.y, term[i][0],term[i][1] )) {
    				  minflow  = gv.GetFlow(p.x, p.y, term[i][0],term[i][1]);
    			  }
    		  }    		  
    		  for(i = 0;i <4; i++) {
    			  if(term[i][2] == 1&&minflow == gv.GetFlow(p.x, p.y, term[i][0],term[i][1] )) {
    				  available[i] = 1;
    			  }
    		  }
    		  for(i = 0; i<4;i++) {
    			  if(available[i] == 1) avainum++;
    		  }
    		  judge = true;
    		  i = 0;
    		  while( judge == true) {
    			  Random r= new Random();
    			  i = Math.abs(r.nextInt()%4);
    			  if(available[i] == 1&&(getturn(term[i][0],term[i][1],location[0],location[1])!=turn||avainum==1)) {
    				  judge = false;
    			  }
    		  }
    		  avainum = 0;
    		  available = new int[4];
    		  p = new Point(term[i][0],term[i][1]);
    		  minflow = 1000;
    		  waitlight(p1,p);
    		  try {
    			  sleep(500);
    		  }catch(InterruptedException e) {}
    		  gui.SetTaxiStatus(num, p, 2);
    		  changeturn(p1.x, p1.y, p.x, p.y);
    		  location[0] = p.x;
    		  location[1] = p.y;
    		  tray.addnl(num,location);
    		  re = tray.getre();
    		  for(k =0;re[k][5]!= 0;k++) {
    			  if(re[k][0] == num&&re[k][5]==1) {
    				  state = 1;
    				  tray.setstatus(num, 1);
    				  tray.change(k);
    				  break;
    			  
    			  }
    		  }
    		  
    	  }
    	  
    	  putout.p1(num, location[0], location[1], System.currentTimeMillis());
    	  //出租车前往乘客处；
    	  int dis = gi.distance(location[0], location[1], re[k][1], re[k][2]);
    	  move(location[0], location[1], re[k][1], re[k][2],dis);
    	  movek = 0;
    	  plocation = new int[1000][2];
    	  time = new long[1000];
    	  putout.p2(re[k][1], re[k][2],System.currentTimeMillis() );
    	  /*try {
			  sleep(200*dis);
		  }catch(InterruptedException e) {}
    	  Point p = new Point(re[k][1],re[k][2]);
    	  gui.SetTaxiStatus(num, p, 3);
    	  location[0] = re[k][1];
    	  location[1] = re[k][2];
    	  tray.addnl(num, location);*/
    	  //停一秒前往目的地
    	  try {
			  sleep(1000);
		  }catch(InterruptedException e) {}
    	  state = 0;
    	  dis = gi.distance(re[k][1], re[k][2], re[k][3], re[k][4]);
    	  move(re[k][1], re[k][2], re[k][3], re[k][4],dis);
    	  movek = 0;
    	  plocation = new int[1000][2];
    	  time = new long[1000];
    	  /*try {
			  sleep(200*dis);
		  }catch(InterruptedException e) {}
    	  Point p = new Point(re[k][3],re[k][4]);
    	  gui.SetTaxiStatus(num, p, 1);
    	  location[0] = re[k][3];
    	  location[1] = re[k][4];
    	  tray.addnl(num, location);*/
    	  state = 3;
		  Point p = new Point(location[0],location[1]);
    	  gui.SetTaxiStatus(num, p, state);
    	  try {
			  sleep(1000);
		  }catch(InterruptedException e) {}
    	  state = 2;
    	  tray.setstatus(num, 2);
      }
      }
}