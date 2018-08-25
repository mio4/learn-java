package oonum11;

import java.awt.Point;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Requestline extends Thread{
	
	/**
	 * @OVERVIEW: 该类是处理请求的类，在该类中识别请求;
	 * @INHERIT: Thread;
	 */
	private TaxiGUI gui;
    private mapInfo map;
    private Tray tray;
    private int[][] requestl = new int[300][4];
    private long[] time = new long[300];
    private guiInfo gi;
    private Taxi[] taxiline;
    private Putout putout;
    private Scanner scan;
    static final String restr1 ="\\[CR,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]";
    static final String restr2 ="\\[OPEN,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]";
    static final String restr3 ="\\[CLOSE,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]";
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
		if(scan == null) return false;
		int i,j;
		for(i = 0; i<300;i++) {
			for(j = 0;j<4;j++) {
				if(requestl[i][j]>=80) return false;
			}
		}
		return true;
    }
    /**
	 * @REQUIRES: none;
	 * @MODIFIES: this;
	 * @EFFECTS: none; 
	 */
    public Requestline(TaxiGUI gui, mapInfo map,Tray tray,guiInfo gi, Taxi[] taxiline,Putout putout,Scanner scan) {
    	
  	  this.gui = gui;
  	  this.map = map;
  	  this.tray = tray;
  	  this.taxiline = taxiline;
  	  this.gi = gi;
  	  this.putout =putout;
  	  this.scan = scan;
    }
    /**
	 * @REQUIRES: (\exist  p1, p2);
	 * @MODIFIES: this.tray;
	 * @EFFECTS:  this.tray.reaction!= \old(this.tray).reaction;
	 */
    public void scheduler(Point p1,Point p2) {
    	
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
    /**
	 * @REQUIRES: none;
	 * @MODIFIES: none;
	 * @EFFECTS:  none;
	 */
    public void run() {
    	
    	String str;
   	    String[] strarray;
   	    int[] term = new int[4];
   	    int i = 0,j,k,num,same;
   	    long  after = 0;
   	    boolean judge= true,judge1 = true;
   	    //Scanner scan = new Scanner(System.in);
   	    str = scan.nextLine();
		str = str.replaceAll(" ","");
		long now = System.currentTimeMillis(); 
   	    Pattern pa1 = Pattern.compile(restr1);
   	    Pattern pa2 = Pattern.compile(restr2);
   	    Pattern pa3 = Pattern.compile(restr3);
   	    Matcher m1 = pa1.matcher(str);
   	    Matcher m2 = pa2.matcher(str);
   	    Matcher m3 = pa3.matcher(str);
		strarray = str.split(",");
		while(true) {
			if(!m1.matches()&&!m2.matches()&&!m3.matches()) {
				if(!str.equals("#end_request")){
				System.out.println("INVALID"+str);}
				try{str = scan.nextLine();
				}catch(Exception e) {
					
				}
				str = str.replaceAll(" ","");
				strarray = str.split(",");
				m1 = pa1.matcher(str);
				m2 = pa2.matcher(str);
				m3 = pa3.matcher(str);
				now = System.currentTimeMillis();
				continue;
			}
			for(j=1;j<5;j++) {
				num = 0;
				for(k = 0; k<strarray[j].length();k++) {
				   if(strarray[j].charAt(k)>='0'&&strarray[j].charAt(k)<='9') {
					   num = num*10 +strarray[j].charAt(k)-'0';
				   }
				   else {
					   continue;
				   }
				}
				if(num>=80) judge = false;
				term[j-1] = num;
			}
			if(term[0] == term[2]&&term[1] == term[3]) {
				System.out.println("INVALID"+str);
                str = scan.nextLine();
				
				str = str.replaceAll(" ","");
				strarray = str.split(",");
				m1 = pa1.matcher(str);
				m2 = pa2.matcher(str);
				m3 = pa3.matcher(str);
				now = System.currentTimeMillis();
				continue;
			}
			//System.out.println(term[0]+"|"+term[1]+"|"+term[2]+"|"+term[3]);
			if(judge == false) {
				System.out.println("INVALID"+str);
				judge = true;
				str = scan.nextLine();
				
				str = str.replaceAll(" ","");
				strarray = str.split(",");
				m1 = pa1.matcher(str);
				m2 = pa2.matcher(str);
				m3 = pa3.matcher(str);
				now = System.currentTimeMillis();
				continue;
			}
			for(same = 0; same<i;same++) {
				if(requestl[same][0]==term[0]&&requestl[same][1]==term[1]&&requestl[same][2]==term[2]&&requestl[same][3]==term[3]&&Math.abs(time[same]-now)<100) judge1 = false;
			}
			if(judge1 == false) {
				judge1 = true;
				System.out.println("Same");
				str = scan.nextLine();
				str = str.replaceAll(" ","");
				strarray = str.split(",");
				m1 = pa1.matcher(str);
				now = System.currentTimeMillis();
				continue;
			}
			if(m1.matches()) {
			requestl[i][0] = term[0];
			requestl[i][1] = term[1];
			requestl[i][2] = term[2];
			requestl[i][3] = term[3];
			time[i] = now;
			putout.pre(now, term[0],term[1], term[2], term[3]);
			Point p1 = new Point(term[0],term[1]);
			Point p2 = new Point(term[2],term[3]);
			gui.RequestTaxi(p1, p2);
			//scheduler(p1,p2);
			Scheduler sc = new Scheduler(gui,map,tray,gi,taxiline,putout,p1,p2);
			sc.start();
			after = System.currentTimeMillis();
			}
			
			else if(m2.matches()) {
				Point p1 = new Point(term[0],term[1]);
				Point p2 = new Point(term[2],term[3]);
				gui.SetRoadStatus(p1,p2,1);
			}
			else if(m3.matches()) {
				Point p1 = new Point(term[0],term[1]);
				Point p2 = new Point(term[2],term[3]);
				gui.SetRoadStatus(p1,p2,0);
			}

			str = scan.nextLine();
			str = str.replaceAll(" ","");
			strarray = str.split(",");
			m1 = pa1.matcher(str);
			m2 = pa2.matcher(str);
			m3 = pa3.matcher(str);
			now = System.currentTimeMillis();
			i++;
			if(str.equals("END")) break;
		 }
    }

}
