import java.awt.Point;
import java.io.File;
import java.io.FileWriter;

public class Passenger {
	/**
	 * @overview:Passenger contains generated time, start point and destination point of the order.
	 * Passenger can be informed of the information of taxi that can be despatched.
	 */
	private long time;
	private boolean end,recv;
	private Point point,des_point;
	private Taxi driver;
	private Drivers drivers;
	private FileWriter out;
	//private Dijk Di;
	private Map ma;
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == 0<=\this.point.x && \this.point.x<80 && 0<=\this.point.y&&\this.point.y<80 && 0<=\this.des_point.x && \this.des_point.x<80 && 0<=\this.des_point.y && \this.des_point.y<80 && drivers!=null && \this.out!=null && \this.time>=0 && \this.ma!=null;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		boolean p = 0<=this.point.x && this.point.x<80 && 0<=this.point.y&&this.point.y<80 && 0<=this.des_point.x && this.des_point.x<80 && 0<=this.des_point.y && this.des_point.y<80;
		return p && drivers!=null && this.out!=null && this.time>=0 && this.ma!=null;
	}
	public Passenger(Point p,Point des,Taxi tx) {
		/**
		 * @REQUIRES:0<=p.x,p.y,des.x,des.y<80 && tx!=null;
		 * @MODIFIES:time,end,recv,point,des_point,driver,out;
		 * @EFFECTS:initialize time,end,recv,point,des_point,driver,drivers;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		this.time = gv.getTime();
		this.end = false;
		this.recv = true;
		this.point=p;
		this.des_point = des;
		this.driver = tx;
		this.out=null;
	}
	public Passenger(String s,TaxiGUI tg,Map ma) {
		/**
		 * @REQUIRES:s is a valid order;
		 * @MODIFIES:time,end,recv,point,des_point,driver,drivers,ma,out;
		 * @EFFECTS:initialize time,end,recv,point,des_point,driver,drivers,ma;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		this.time = gv.getTime();
		this.end = false;
		this.recv = false;
		String pp = s.replaceAll("[^0-9]+", " ");
		String []pos = pp.split(" ");
		this.point=new Point(Integer.valueOf(pos[1]),Integer.valueOf(pos[2]));
		this.des_point = new Point(Integer.valueOf(pos[3]),Integer.valueOf(pos[4]));
		this.driver = null;
		this.drivers = new Drivers();
		//tg.RequestTaxi(this.point, this.des_point);
		//this.Di=di;
		this.ma=ma;
		try {
			synchronized(this) {
			File ff = new File ("output//"+String.format("%d%d%d%d%d",this.time,this.point.x,this.point.y,this.des_point.x,this.des_point.y)+".txt");
			ff.createNewFile();
			//System.out.println(this+"create");
			this.out=new FileWriter(ff);
			this.out.write(this+System.getProperty("line.separator"));
			this.out.flush();
			//System.out.println(this+"write");
			}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	public FileWriter getFw() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==out;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return this.out;
	}
	public synchronized boolean getEnd() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==end;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.end;
	}
	public boolean getRecv() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==recv;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.recv;
	}
	public int getX() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==point.x;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return this.point.x;
	}
	public int getY() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==point.y;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return this.point.y;
	}
	public long getTime() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==time;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return this.time;
	}
	public Point getDes_point() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==des_point;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return this.des_point;
	}
	public String toString() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==\this;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return "Order: ["+String.format("%d", this.time)+"ms,("+this.point.x+","+this.point.y+")"+",("+this.des_point.x+","+this.des_point.y+")]";
	}
	public boolean equals(Passenger p) {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==(\this==p);
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return (String.format("%.1f",  Math.floor(this.time/100)/10).equals(String.format("%.1f", Math.floor(p.time/100)/10)) && this.des_point.equals(p.des_point) && this.point.equals(p.point));
	}
	public void received(Taxi t) {
		/**
		 * @REQUIRES:
		 * @MODIFIES:driver.size,t.credit;
		 * @EFFECTS:!(t already in drivers)==>driver.contains(t);
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		synchronized(this.drivers) {
			//System.out.println("receive");
		boolean exist=false;
		for(int i=0;i<drivers.size();i++) {
			if(drivers.get(i).getNo()==t.getNo()) {
				exist=true;
				break;
			}
		}
		if(!exist) {
			this.drivers.add(t);
			t.addCredit(1);
			//System.out.println("add");
		}
		}
	}
	private Taxi[] credit_rank() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:drivers.size;
		 * @EFFECTS:\result==ranked_by_credit(drivers)cretaxi[];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked((\all Taxi t,drivers));
		 */
		Taxi [] cretaxi = new Taxi[drivers.size()];
		int max = 0;
		for(int i=0;i<drivers.size();i++) {
			//synchronized(drivers.get(i)) {
			if(drivers.get(i).signal==true) {
				drivers.remove(i);
				i--;
				continue;
			}
			if(drivers.get(i).getCredit()>max) max =  drivers.get(i).getCredit();
			//}
		}
		int j=0;
		for(int i=0;i<drivers.size();i++) {
			//synchronized(drivers.get(i)) {
			if(drivers.get(i).getCredit()==max) cretaxi[j++]=drivers.get(i);
			//}
		}
		//System.out.println("creditend");
		if(j>0) {
			return cretaxi;
		}
		else return null;
	}
	private Taxi dis_rank(Taxi []t) {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==shortest_distance_ranked(Taxi[]);
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked(ma);
		 *				\locked((\all Taxi t,t[]));
		 */
		//System.out.println("disrank1");
		if(t==null) return null;
		int min = 10000000;
		Taxi []temp = new Taxi[t.length];
		//System.out.println("disrank2");
		//synchronized(this.Di) {
		//synchronized(this.ma) {
		//this.Di.getpath(this.point.x*80+ this.point.y,gv.MAXNUM);
			//this.ma.spfa(this.point.x*80+ this.point.y,gv.MAXNUM);
		//}
		for(int i=0;i<t.length;i++) {
			
			if(t[i]==null) continue;
			synchronized(t[i]) {
			if(t[i].signal==true) {
				t[i]=null;
				continue;
			}
			//synchronized(this.Di) {
			synchronized(this.ma) {
			//int dis = this.Di.distance[this.point.x*80+ this.point.y][t[i].getX()*80+ t[i].getY()];
				int dis = this.ma.spfa(this.point.x*80+ this.point.y,t[i].getX()*80+ t[i].getY())[t[i].getX()*80+ t[i].getY()];
			//System.out.println("distance= "+dis+"to "+t[i].getPoint());
			if(dis<min) {
				min =  dis;
				temp[0]=t[i];
			}
			}
			}
		}
		//System.out.println("disrank3");
		//System.out.println("min ids"+min);
		System.out.println(this.toString()+"select"+temp[0].toString());
		if(temp!=null) return temp[0];
		else return null;
	}
	public boolean select() {
		/**
		 * @REQUIRES:!end;
		 * @MODIFIES:out.write,System.out;
		 * @EFFECTS:out.write,driver==dis_rank();
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked(driver);
		 */
		if(this.drivers.size()!=0) {
			for(int i=0;i<this.drivers.size();i++) {
				try {
					this.out.write("picked up by:"+System.getProperty("line.separator"));
					this.out.write(drivers.get(i).toString()+System.getProperty("line.separator"));
					this.out.flush();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
				}
			}
			//System.out.println("rank........");
			this.driver = this.dis_rank(this.credit_rank());
			if(this.driver!=null) {
				//synchronized(this.driver) {
				//System.out.println("dispatch........");
				this.driver.dispatch(this);
				//}
				this.recv=true;
				return true;
			}
		}
		System.out.println(this.toString()+"Sorry, no taxi want you");
			try {
				this.out.write("Sorry, no taxi want you"+System.getProperty("line.separator"));
				this.out.flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			this.setEnd();
			return false;
	}
	public void setEnd() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:end;
		 * @EFFECTS:end==true;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		this.end=true;
	}
}
