package oonum9;

import java.util.Random;
import java.util.ArrayList;
import java.awt.Point;
public class Taxi extends Thread {
      private int num;
      private int state = 2;//服务状态 -1  接单状态 -3 等待状态 -2 停止状态 -0 
      private int[] location = new int[2];
      private TaxiGUI gui;
      private mapInfo map;
      private int credit = 0;
      private Tray tray;
      private guiInfo gi;
      private Putout putout;
      private guigv gv;
      public Taxi(int num, TaxiGUI gui, mapInfo map,Tray tray,guiInfo gi, Putout putout, guigv gv) {
    	  /**
  		 * @REQUIRES: (\exist num );
  		 * @MODIFIES: none;
  		 * @EFFECTS: Initialize class Taxi;
  		 */
    	  this.num = num;
    	  this.gui = gui;
    	  this.map = map;
    	  this.tray = tray;
    	  this.gi = gi;
    	  this.putout = putout;
    	  this.gv = gv;
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
      public int gets() {
    	  /**
  		 * @REQUIRES: none;
  		 * @MODIFIES: none;
  		 * @EFFECTS: get state of car;
  		 */
    	  return state;
      }
      public int[] getlo() {
    	  /**
  		 * @REQUIRES: none;
  		 * @MODIFIES: none;
  		 * @EFFECTS: get location of car;
  		 */
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
      public void move(int x1,int y1,int x2,int y2,int dis) {
    	  /**
  		 * @REQUIRES: (\exist Point p1, p2, int dis);
  		 * @MODIFIES: location, tray.location;
  		 * @EFFECTS:  move the car from p1 to p2;
  		 */
    	  ArrayList<Point> ar = gi.way(x1, y1, x2, y2, dis);
    	  int[][] plocation = new int[1000][2];
    	  long[] time = new long[1000];
    	  int i  = ar.size()-1;
    	  int k = 0;
    	  for(;i>=0;i--) {
    		  try {
    			  sleep(500);
    		  }catch(InterruptedException e) {}
    		  gui.SetTaxiStatus(num, ar.get(i), state);
    		  location[0] = ar.get(i).x;
    		  location[1] = ar.get(i).y;
    		  plocation[k][0] = location[0];
    		  plocation[k][1] = location[1];
    		  time[k] = System.currentTimeMillis();
    		  k++;
    		  tray.addnl(num, location);
    	  }
    	  putout.p3(num,k, plocation, time);
      }
      public void run() {
    	  /**
  		 * @REQUIRES: none;
  		 * @MODIFIES: none;
  		 * @EFFECTS:  if the car wait move randomly else move from p1 to p2;
  		 */
    	  int[][] re = new int[100][6];
    	  int[] offset = {-1, -80, 80, 1};
    	  int[] available = new int[4]; 
    	  int[][] term = new int [4][3];
    	  int minflow = 1000;
     	  boolean judge = true;
     	  long t = System.currentTimeMillis();
    	  int i = 0,j = 0,k =0 ;
      while(true) {
    	  j = 0;
    	  k = 0;
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
    			  state = 2;
    			  continue;
    		  }
    		  Point p = new Point(location[0],location[1]);
    		  Point p1 = new Point(location[0],location[1]);
    		  
    		  t = System.currentTimeMillis();
    		  term = gi.getpoint(p);
    		  
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
    		  
    		  judge = true;
    		  i = 0;
    		  while( judge == true) {
    			  Random r= new Random();
    			  i = Math.abs(r.nextInt()%4);
    			  if(available[i] == 1) {
    				  judge = false;
    			  }
    		  }
    		  available = new int[4];
    		  p = new Point(term[i][0],term[i][1]);
    		  minflow = 1000;
    		  try {
    			  sleep(t+500-System.currentTimeMillis());
    		  }catch(InterruptedException e) {}
    		  gui.SetTaxiStatus(num, p, 2);
    		  location[0] = p.x;
    		  location[1] = p.y;
    		  tray.addnl(num,location);
    		  re = tray.getre();
    		  for(k =0;re[k][5]!= 0;k++) {
    			  if(re[k][0] == num&&re[k][5]==1) {
    				  state = 3;
    				  tray.change(k);
    				  break;
    			  
    			  }
    		  }
    		  
    	  }
    	  putout.p1(num, location[0], location[1], System.currentTimeMillis());
    	  //出租车前往乘客处；
    	  int dis = gi.distance(location[0], location[1], re[k][1], re[k][2]);
    	  move(location[0], location[1], re[k][1], re[k][2],dis);
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
    	  state = 1;
    	  dis = gi.distance(re[k][1], re[k][2], re[k][3], re[k][4]);
    	  move(re[k][1], re[k][2], re[k][3], re[k][4],dis);
    	  /*try {
			  sleep(200*dis);
		  }catch(InterruptedException e) {}
    	  Point p = new Point(re[k][3],re[k][4]);
    	  gui.SetTaxiStatus(num, p, 1);
    	  location[0] = re[k][3];
    	  location[1] = re[k][4];
    	  tray.addnl(num, location);*/
    	  state = 0;
		  Point p = new Point(location[0],location[1]);
    	  gui.SetTaxiStatus(num, p, state);
    	  try {
			  sleep(1000);
		  }catch(InterruptedException e) {}
    	  state = 2;
      }
      }
}