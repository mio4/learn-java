package oonum11;

import java.io.PrintStream;

public class Putout {
	
	/**
	 * @OVERVIEW: 该类是控制输出的类，输出各种信息到文件;
	 * @INHERIT: none;
	 */
      private PrintStream ps;
      /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
	  public boolean repOK() {
			if(ps == null) return false;
			return true;
	}
      /**
		 * @REQUIRES: (\exist ps);
		 * @MODIFIES: none;
		 * @EFFECTS: none ;
		 */
      public Putout(PrintStream ps) {
    	  
    	  this.ps = ps;
      }
      /**
		 * @REQUIRES: (\exist i1, x1, y1, x2, y2);
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
      public synchronized void pre(long i1,int x1,int y1,int x2,int y2) {//输出请求信息
    	 
    	  ps.println(i1+" request:("+x1+","+y1+")to("+x2+","+y2+")");
      }
      /**
		 * @REQUIRES:(\exist k, term);
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
      public synchronized void pthree(int k,int[][] term) {//输出时间窗内的车辆请求
    	 
    	  int i = 0;
    	  for(;i<k;i++) {
    		  ps.println("num."+term[i][0]+"("+term[i][1]+","+term[i][2]+")state:"+term[i][3]+"credit:"+term[i][4]);
    	  }
      }
      /**
		 * @REQUIRES: (\exist num ,time, x1, y1);
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
      public synchronized void p1(int num, int x1, int y1,long time) {
    	  
    	  ps.println(time+":num."+num+"("+x1+","+y1+")");
      }
      /**
		 * @REQUIRES: (\exist x1,y1 , time);
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
      public synchronized void p2(int x1,int y1,long time) {
    	  
    	  ps.println(time+":arrive at("+x1+","+y1+")");
      }
      /**
		 * @REQUIRES:(\exist term , num, time);
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
      public synchronized void p3(int num,int k, int[][] term, long time[]) {
    	 
    	  for(int i = 0; i<k;i++) {
    		  ps.println(time[i]+":num."+num+"("+term[i][0]+","+term[i][1]+")");
    	  }
      }
}
