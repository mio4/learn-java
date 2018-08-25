

import java.awt.Point;
import java.util.Scanner;

public class OrderHandler implements Runnable{
	/**
	 * @overview:To deal with input string.
	 * Checkout format and validation.
	 * Deal with valid input.
	 */
	private Scanner sc;
	private Orders orders;
	private TaxiGUI tg;
	private TaxiSystem ts;
	//private File loadfile;
	//private Dijk Di;
	private Map ma;
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == ts!=null && orders!=null && tg!=null && ma!=null;
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:
		 */
		return ts!=null && orders!=null && tg!=null && ma!=null;
	}
	public OrderHandler(TaxiSystem ts,Orders orders,TaxiGUI tg,Map ma) {
		/**
		 * @REQUIRES:ts!=null && orders!=null && tg!=null && ma!=null;
		 * @MODIFIES:orders,tg,ma,ts;
		 * @EFFECTS:initial orders,tg,ma,ts;
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:
		 */
		sc = new Scanner(System.in);
		this.orders = orders;
		this.tg=tg;
		//this.Di=di;
		this.ma=ma;
		this.ts=ts;
	}
	private boolean num_check(String ss) {
		/**
		 * @REQUIRES:ss can be translated to int;
		 * @MODIFIES:
		 * @EFFECTS:\result==(s>=0 && s<=79);
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		int s = Integer.valueOf(ss);
		return (s>=0 && s<=79);
	}
	private boolean format_check(String s) {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==s is a valid order;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		String p = "\\[CR,\\(\\d+\\,\\d+\\),\\(\\d+,\\d+\\)\\]";
		if(s.matches(p)) {
			String pp = s.replaceAll("[^0-9]+", " ");
			String []pos = pp.split(" ");
			if(num_check(pos[1]) && num_check(pos[2]) && num_check(pos[3]) && num_check(pos[4])) {
				if(!pos[1].equals(pos[3]) || !pos[2].equals(pos[4])) return true;
			}
		}
		return false;
	}
	private void add(String s) {
		/**
		 * @REQUIRES:s is a valid order;
		 * @MODIFIES:orders.Ps.size;
		 * @EFFECTS:\result==!(s is same order);
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		Passenger p = new Passenger(s,tg,this.ma);
		boolean flag=true;
		for(int i=0;i<orders.size();i++) {
			//System.out.println(orders.get(i));
			//System.out.println(p);
			if(orders.get(i).equals(p)) {
				flag=false;
				System.out.println("SAME"+s);
				break;
			}
		}
		if(flag) {
			orders.add(p);
		}
	}
	private boolean roadmatch(String []st) {//ROAD (1,1) (2,2) 1
		/**
		 * @REQUIRES:st!=null;
		 * @MODIFES:
		 * @EFFECTS:\result==(is a valid ROAD);
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:
		 */
		if(st[0].equals("ROAD")) {
			String pattern1 = "\\(\\d+,\\d+\\)";
			String pattern2 = "\\d+";
			boolean pattern = st[1].matches(pattern1) && st[2].matches(pattern1) && st[3].matches(pattern2);
			if(!pattern) return false; 
			Point p1=null,p2=null;
			for(int i=1;i<3;i++) {
				String s1 = st[i].replaceAll("\\(", "");
				String s2 = s1.replaceAll("\\)", "");
				String []roadpoint = s2.split(",");
				int x=Integer.valueOf(roadpoint[0]);
				int y=Integer.valueOf(roadpoint[1]);
				if(x<0 || x>=80 || y<0 || y>=80) return false;
				if(i==1) p1 = new Point(x,y);
				else p2 = new Point(x,y);
			}
			int open = Integer.valueOf(st[3]);
			if(open!=0 && open!=1) return false;
			if(p1.equals(p2)) return false;
			if(p1.x!=p2.x && p1.y!=p2.y) return false;
			if(open==0 && ma.graph[p1.x*80+p1.y][p2.x*80+p2.y]==gv.MAXNUM) {
				//System.out.println("already block");
				return false;
			}
			if(open==1 && ma.graph[p1.x*80+p1.y][p2.x*80+p2.y]==1) {
				//System.out.println("already link");
				return false;
			}
			ts.setroadstatus(p1, p2, open);
			return true;
		}//ROAD (8,6) (8,7) 0
		return false;
	}
	public void input(String s) {
		/**
		 * @REQUIRES:s!=null;
		 * @MODIFIES:orders.Ps.size,System.out;
		 * @EFFECTS:
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		
		String []st=s.split(" ");
		if(!roadmatch(st)) {
			s.replaceAll(" ", "");
			//System.out.println(s);
			if(format_check(s)) add(s);
			else System.out.println("INVALID ORDER");
		}
		else System.out.println("ROAD request received");
	}
	public void run() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:System.in;
		 * @EFFECTS:checkout input string format;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		try {
		String s = sc.nextLine();
		while(!s.equals("END")) {
			//boolean lo = load(s);
			//if(lo) {
			//	loadread(loadfile);
			//}
			//s.replaceAll(" ", "");
			//System.out.println(s);
			//if(format_check(s)) add(s);
			//else System.out.println("INVALID ORDER");
			//s = sc.nextLine();
			input(s);
			s = sc.nextLine();
		}
		sc.close();
		}catch(Exception e) {
			System.out.println("INVALID ORDER");
		}
	}
}
