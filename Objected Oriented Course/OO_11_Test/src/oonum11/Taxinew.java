package oonum11;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.io.PrintStream;

public class Taxinew extends Taxi{
	/**
	 * @OVERVIEW: 该类是特殊出租车类，控制出租车的移动;
	 * @INHERIT: Taxi;
	 */
	 protected ArrayList<Long> detailtime = new ArrayList<Long>();
	 protected ArrayList<int[]> detailcr = new ArrayList<int[]>();
	 protected ArrayList<int[][]> detailpath1 = new ArrayList<int[][]>();
	 protected ArrayList<int[][]> detailpath2 = new ArrayList<int[][]>();
	 protected ArrayList<Point> detaillo = new ArrayList<Point>();
	 private PrintStream ps;
     public boolean repOK() {
		 return super.repOK();
	 }
	 /**
		 * @REQUIRES: none;
		 * @MODIFIES: this;
		 * @EFFECTS: none;
		 */
     public Taxinew(int num, TaxiGUI gui, mapInfo map,Tray tray,guiInfo gi, Putout putout, guigv gv, Light li) {
 	    super(num,gui,map,tray,gi,putout,gv,li);
 	    String detail = "detail.txt";
		try{
 		ps = new PrintStream(detail); 
 			}catch(Exception e) {   e.printStackTrace();  }
     
     }
     /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
     public void move(int x1,int y1,int x2,int y2,int dis) {
          super.move1(x1, y1, x2, y2, dis);
     }
     /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
     public synchronized void next() {
    	 if(hasnext()!=true) {
    		 System.out.println("没有可输出的完整数据");
    		 return;
    	 }
    	 ps.println("指令为从("+detailcr.get(0)[1]+","+detailcr.get(0)[2]+")to("+detailcr.get(0)[3]+","+detailcr.get(0)[4]+")");
         detailcr.remove(0);
    	 ps.println("时间为"+detailtime.get(0));
         detailtime.remove(0);
         ps.println("被派单地点为"+detaillo.get(0));
         detaillo.remove(0);
         for(int i = 0; detailpath1.get(0)[i][0]!=-1;i++) {
        	 ps.println("("+detailpath1.get(0)[i][0]+","+detailpath1.get(0)[i][1]+")");
         }
         detailpath1.remove(0);
         for(int i = 0; detailpath2.get(0)[i][0]!=-1;i++) {
        	 ps.println("("+detailpath2.get(0)[i][0]+","+detailpath2.get(0)[i][1]+")");
         }
         detailpath2.remove(0);
     }
     /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
     public synchronized void pre() {
    	 int i = detailcr.size()-1;
    	 if(haspre()!=true) {
    		 System.out.println("没有可输出的完整数据");
    		 return;
    	 }
    	 ps.println("指令为从("+detailcr.get(i)[1]+","+detailcr.get(i)[2]+")to("+detailcr.get(i)[3]+","+detailcr.get(i)[4]+")");
         detailcr.remove(i);
    	 ps.println("时间为"+detailtime.get(i));
         detailtime.remove(i);
         ps.println("被派单地点为"+detaillo.get(i));
         detaillo.remove(i);
         for(int j = 0; detailpath1.get(i)[j][0]!=-1;j++) {
        	 ps.println("("+detailpath1.get(i)[j][0]+","+detailpath1.get(i)[j][1]+")");
         }
         detailpath1.remove(i);
         for(int j = 0; detailpath2.get(i)[j][0]!=-1;j++) {
        	 ps.println("("+detailpath2.get(i)[j][0]+","+detailpath2.get(i)[j][1]+")");
         }
         detailpath2.remove(i);
     }
     /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: this.detailcr.size()!=0&& this.detailtime.size()!= 0&& this.detaillo.size()!= 0&& this.detailpath1.size()!= 0&& this.detailpath2.size()!= 0==>\result== true;
		 */
     public synchronized boolean hasnext() {
    	 if(detailcr.size()!= 0 &&detailtime.size()!= 0&&detaillo.size()!= 0&&detailpath1.size()!= 0&&detailpath2.size()!= 0) {
    		 return true;
    	 }
    	 else {
    		 return false;
    	 }
     }
     /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: this.detailcr.size()!=0&& this.detailtime.size()!= 0&& this.detaillo.size()!= 0&& this.detailpath1.size()!= 0&& this.detailpath2.size()!= 0==>\result== true;
		 */
     public synchronized boolean haspre() {
    	 if(detailcr.size()== detailtime.size()&& detailtime.size()==detaillo.size()&&detaillo.size()==detailpath1.size()&&detailpath1.size()==detailpath2.size()&&detailpath2.size()!= 0) {
    		 return true;
    	 }
    	 else {
    		 return false;
    	 }
     }
     /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
     public void run() {
          //super.run1();

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
    		   		  detailcr.add(re[k]);
    				  break;
    			  
    			  }
    		  }
    		  
    	  }
    	  detaillo.add(new Point(location[0],location[1]));
    	  detailtime.add(System.currentTimeMillis());
    	  putout.p1(num, location[0], location[1], System.currentTimeMillis());
    	  //出租车前往乘客处；
    	  int dis = gi.distance1(location[0], location[1], re[k][1], re[k][2]);
    	  move(location[0], location[1], re[k][1], re[k][2],dis);
    	  plocation[movek][0] = -1;
    	  detailpath1.add(plocation);
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
    	  dis = gi.distance1(re[k][1], re[k][2], re[k][3], re[k][4]);
    	  move(re[k][1], re[k][2], re[k][3], re[k][4],dis);
    	  plocation[movek][0] = -1;
    	  detailpath2.add(plocation);
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
