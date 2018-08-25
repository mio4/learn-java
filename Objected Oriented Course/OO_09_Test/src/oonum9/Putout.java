package oonum9;

import java.io.PrintStream;

public class Putout {
      private PrintStream ps;
      public Putout(PrintStream ps) {
    	  /**
  		 * @REQUIRES: (\exist PrintStream ps);
  		 * @MODIFIES: none;
  		 * @EFFECTS: get a ps to print ;
  		 */
    	  this.ps = ps;
      }
      public synchronized void pre(long i1,int x1,int y1,int x2,int y2) {//输出请求信息
    	  /**
  		 * @REQUIRES: (\exist point p1, point p2, time);
  		 * @MODIFIES: none;
  		 * @EFFECTS: print cr ;
  		 */
    	  ps.println(i1+" request:("+x1+","+y1+")to("+x2+","+y2+")");
      }
      public synchronized void pthree(int k,int[][] term) {//输出时间窗内的车辆请求
    	  /**
  		 * @REQUIRES:(\exist int k; int[][] term);
  		 * @MODIFIES: none;
  		 * @EFFECTS: print all of the cars that answered;
  		 */
    	  int i = 0;
    	  for(;i<k;i++) {
    		  ps.println("num."+term[i][0]+"("+term[i][1]+","+term[i][2]+")state:"+term[i][3]+"credit:"+term[i][4]);
    	  }
      }
      public synchronized void p1(int num, int x1, int y1,long time) {
    	  /**
  		 * @REQUIRES: (\exist int num, long time, Point x);
  		 * @MODIFIES: none;
  		 * @EFFECTS: print the time of arriving at Point x;
  		 */
    	  ps.println(time+":num."+num+"("+x1+","+y1+")");
      }
      public synchronized void p2(int x1,int y1,long time) {
    	  /**
  		 * @REQUIRES: (\exist point , time);
  		 * @MODIFIES: none;
  		 * @EFFECTS: print the time of arriving at Piont x;
  		 */
    	  ps.println(time+":arrive at("+x1+","+y1+")");
      }
      public synchronized void p3(int num,int k, int[][] term, long time[]) {
    	  /**
  		 * @REQUIRES:(\exist load , num, time);
  		 * @MODIFIES: none;
  		 * @EFFECTS: print the load of the car;
  		 */
    	  for(int i = 0; i<k;i++) {
    		  ps.println(time[i]+":num."+num+"("+term[i][0]+","+term[i][1]+")");
    	  }
      }
}
