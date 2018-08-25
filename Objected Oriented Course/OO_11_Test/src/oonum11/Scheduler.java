package oonum11;

import java.util.Scanner;
import java.awt.Point;

public class Scheduler extends Thread{
	/**
	 * @OVERVIEW: 该类是处理请求的类，在该类调配;
	 * @INHERIT: Thread;
	 */
	private TaxiGUI gui;
    private mapInfo map;
    private Tray tray;
    private guiInfo gi;
    private Taxi[] taxiline;
    private Putout putout;
    private Point p1;
    private Point p2;
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
   		if(taxiline == null) return false;
   		if(putout == null) return false;
   		return true;
       }
        /**
    	 * @REQUIRES: none;
    	 * @MODIFIES: this;
    	 * @EFFECTS: none; 
    	 */
      public Scheduler(TaxiGUI gui, mapInfo map,Tray tray,guiInfo gi, Taxi[] taxiline,Putout putout,Point p1, Point p2) {
        	
      	  this.gui = gui;
      	  this.map = map;
      	  this.tray = tray;
      	  this.taxiline = taxiline;
      	  this.gi = gi;
      	  this.putout =putout;
      	  this.p1 = p1;
      	  this.p2 = p2;
      }
      /**
     	 * @REQUIRES: none;
     	 * @MODIFIES: none;
     	 * @EFFECTS: none;
     	 */
      public void run() {
    	int[] abled = new int[100],cr = tray.getcr();
      	int[][] nl = tray.getnl();
      	int[][] putout1 = new int[100][5];
      	int i,k = 0,choose,j = 0,m;
      	//初始化abled
      	for(i = 0;i<100;i++) {
      		abled[i] = -1;
      	}
      	boolean judge = true;
      	//三秒抢单期
      	for(j = 0;j<75;j++) {
      		for(i=0;i<100;i++) {
      			if((nl[i][0]>=p1.x-2)&&(nl[i][0]<=p1.x+2)&&(nl[i][1]>=p1.y-2)&&(nl[i][1]<=p1.y+2)) {
      				judge = true;
      				for(m = 0;m<100;m++) {
      					if(abled[m]==i) judge = false;
      					if(tray.getstatus(i)!= 2) judge = false;
      				}
      				if(judge == true) {
      					putout1[k][0] = i;
      					putout1[k][1] = nl[i][0];
      					putout1[k][2] = nl[i][1];
      					putout1[k][3] = taxiline[i].gets();
      					putout1[k][4] = tray.getcr()[i]+1;
      					abled[k++] = i;
      				}
      			}
      		}
      		try {
    			  sleep(100);
    			  nl = tray.getnl();
    		    }catch(InterruptedException e) {}
      	}
      	//三秒过后进行调配
      	if(k == 0) {
      		System.out.println("没有出租车响应");
      	}
      	else {
      		putout.pthree(k,putout1);
      		int[] abled1 = new int[100];    		
      		int[] abled2 = new int[100];
      		for(i = 0;i<100;i++) {
      			abled1[i] = -1;
      			abled2[i] = -1;
      		}
      		int bestcr = 0;
      		int bestdis = 10000;
      		for(i = 0;i<100&&abled[i]!=-1;i++) {
      			tray.addcr(abled[i], 1);
      			cr = tray.getcr();
      			if(cr[abled[i]]>bestcr) bestcr = cr[abled[i]];
      		}
      		k =0;
      		for(i = 0;i<100&&abled[i]!=-1;i++) {
      			if(cr[abled[i]]==bestcr) abled1[k++] = abled[i];
      		}
      		nl = tray.getnl();
      		int[] dis=new int[100];
      		for(i = 0;i<100;i++) {
      			dis[i] =10000;
      		}
      		for(i = 0;i<100&&abled1[i]!=-1;i++) {
      			dis[i] = gi.distance(nl[abled1[i]][0], nl[abled1[i]][1], p1.x, p1.y);
      			if(dis[i] <bestdis) bestdis = dis[i];
      		}
      		k = 0;
      		for(i = 0;i<100&&abled1[i]!=-1;i++) {
      			if(dis[i] == bestdis) {
      				abled2[k++] = abled1[i];
      			}
      		}
      		for(i = 0;i<k;i++) {
      			if(tray.getstatus(abled2[i]) == 2) {
      				tray.addcr(abled2[i], 3);
      				tray.setstatus(abled2[i], 3);
      			    break;
      			}
      		}
      		
      	int[] term1 = new int[2];
      	int[] term2 = new int[2];
      	term1[0] = p1.x;
      	term1[1] = p1.y;
      	term2[0] = p2.x;
      	term2[1] = p2.y;
      	tray.addre(abled2[0],term1,term2);
      	}
      }
}
