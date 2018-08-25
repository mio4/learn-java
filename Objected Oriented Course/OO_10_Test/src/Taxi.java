
import java.awt.Point;
import java.util.Random;

public class Taxi extends Thread{
	/**
	 * @overview:Taxi contains some methods to get or set its information.
	 * 
	 */
	private int No,wait_count,stop_count,recv_count;
	public long time;
	public long wakeup,oldlightwake;
	public Point point,des_point,next_point;
	private int credit,t;//2:waiting   0:stop   3:dispached   1:serving  
	public TaxiStatus status;
	public boolean signal,empty;
	private Passenger order;
	private Drivers dr;
	private int [][] map;
	private int[] path;
	private TaxiGUI tg;
	private LightSystem light;
	private int oldlight;
	public int dir;//1:east 2:south 3:west 4:north
	//private int cur_dir;
	//private Dijk Di;
	private Map ma;
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == point.x>=0 && point.y>=0 && point.x<80 && point.y<80 && des_point.x>=0 && des_point.y>=0 && des_point.x<80 && des_point.y<80 && credit>=0 && No>=0 && tg!=null && ma!=null && light!=null && dr!=null;
		 */
		boolean p = point.x>=0 && point.y>=0 && point.x<80 && point.y<80;
		boolean d = des_point.x>=0 && des_point.y>=0 && des_point.x<80 && des_point.y<80;
		//boolean n = next_point.x>=0 && next_point.y>=0 && next_point.x<80 && next_point.y<80;
		
		return oldlight>=0 && oldlight<=2 && dir>=1 && dir<=4 && p && d && credit>=0 && No>=0 && tg!=null && ma!=null && light!=null && dr!=null;
	}
	public Taxi (int no,Point point,TaxiGUI tg,Map ma,LightSystem light,Drivers dr) {
		/**
		 * @REQUIRES:no>=0 && point in 80*80 && tg!=null && ma!=null && light!=null && dr!=null;
		 * @MODIFIES:No,wait_count,stop_count,recv_count,point,status,credit,t,signal,empty,tg,ma,map[][],path[];
		 * @EFFECTS:initialize No,wait_count,stop_count,recv_count,point,status,credit,t,signal,empty,tg,ma,map[][],path[];
		 */
		this.dr = dr;
		this.No=no;
		this.wait_count=0;
		this.stop_count=0;
		this.recv_count=0;
		this.point=point;
		this.des_point=new Point(0,0);
		this.status=TaxiStatus.WAITING;
		this.credit=0;
		this.t=0;
		this.dir=1;//east
		this.next_point=null;
		//this.wakeup=time;
		this.oldlightwake=0;
		this.light=light;
		this.oldlight=0;
		this.signal=false;
		this.empty=true;
		this.tg = tg;
		this.ma = ma;
		//this.map=ma.map;
		this.path=new int [6400];
		//this.Di=di;
	}
	public int sta_num(TaxiStatus ts) {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==(TaxiStatus translated to int);
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		switch(ts) {
		case STOP:
			return 0;
		case SERVING:
			return 1;
		case WAITING:
			return 2;
		case DISPATCHED:
			return 3;
		default:return 4;
		}
	}
	public TaxiStatus num_sta(int num) {
		/**
		 * @REQUIRES:0<=num<4;
		 * @MODIFIES:
		 * @EFFECTS:translate num to TaxiStatus;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		switch(num) {
		case 1: return TaxiStatus.SERVING;
		case 2: return TaxiStatus.WAITING;
		case 3: return TaxiStatus.DISPATCHED;
		default: return TaxiStatus.STOP;
		}
	}
	public synchronized void wait_counter(int t) {
		/**
		 * @REQUIRES:t>=0;
		 * @MODIFIES:wait_count,time;
		 * @EFFECTS:wait_count==\old(wait_count)+t;
		 *			time==\old(time)+t;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.wait_count+=t;
		this.time+=t;
	}
	public synchronized void stop_counter(int t) {
		/**
		 * @REQUIRES:t>=0;
		 * @MODIFIES:stop_count,time;
		 * @EFFECTS:stop_count==\old(stop_count)+t;
		 *			time==\old(time)+t;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.stop_count+=t;
		this.time+=t;
	}
	public synchronized void recv_counter(int t) {
		/**
		 * @REQUIRES:t>=0;
		 * @MODIFIES:recv_count,time;
		 * @EFFECTS:recv_count==\old(recv_count)+t;
		 *			time==\old(time)+t;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.recv_count+=t;
		this.time+=t;
	}

	public synchronized void resetWait() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:wait_count;
		 * @EFFECTS:wait_count==0;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.wait_count=0;
	}
	public synchronized void resetStop() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:stop_count;
		 * @EFFECTS:stop_count==0;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.stop_count=0;
	}
	public synchronized void resetRecv() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:recv_count;
		 * @EFFECTS:recv_count==0;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.recv_count=0;
	}
	public synchronized long getTime() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==time;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.time;
	}
	public synchronized int getWait() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==wait_count;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.wait_count;
	}
	public synchronized int getStop() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==stop_count;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.stop_count;
	}
	public synchronized int getRecv() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==recv_count;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.recv_count;
	}

	public synchronized int getX() {
		/**
		 * @REQUIRES:
 		 * @MODIFIES:
		 * @EFFECTS:\result==point.x;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.point.x;
	}
	public synchronized int getY() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==point.y;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.point.y;
	}
	public synchronized void resetOrder() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:order;
		 * @EFFECTS:order==null;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.order=null;
	}
	public synchronized Passenger getOrder() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==order;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked(order);
		 */
		synchronized(this.order) {
		return this.order;
		}
	}
	public synchronized Point getPoint() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==point;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.point;
	}
	public synchronized Point getOrderPoint() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==order's point;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return new Point(this.order.getX(),this.order.getY());
	}
	public synchronized Point getOrderDesPoint() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==order's destination point;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.order.getDes_point();
	}
	
	public synchronized TaxiStatus getStatus() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==status;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.status;
	}
	public int getNo() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==No;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return this.No;
	}
	public synchronized int getCredit() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==credit;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.credit;
	}
	public synchronized void addCredit(int i) {
		/**
		 * @REQUIRES:i>=0;
		 * @MODIFIES:credit;
		 * @EFFECTS:credit==\old(credit)+i;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.credit+=i;
	}
	public synchronized boolean getSignal() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==signal;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return this.signal;
	}
	public synchronized void setStatus(TaxiStatus ts) {
		/**
		 * @REQUIRES:ts!=null;
		 * @MODIFIES:credit,status;
		 * @EFFECTS:status==ts;
		 *			credit==\old(credit)+3;
		 *			update TaxiGUI;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.status=ts;//0stop 1ser 2wait 3dispartched
		if(ts==TaxiStatus.SERVING) this.addCredit(3);
		this.tg.SetTaxiStatus(this.No,this.point,this.sta_num(ts));
	}
	public synchronized void iniStatus(TaxiStatus ts) {
		/**
		 * @REQUIRES:ts!=null;
		 * @MODIFIES:credit,status;
		 * @EFFECTS:status==ts;
		 *			credit==\old(credit)+3;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.status=ts;//0stop 1ser 2wait 3dispartched
		if(ts==TaxiStatus.SERVING) this.addCredit(3);
		//this.tg.SetTaxiStatus(this.No,this.point,this.sta_num(ts));
	}
	public synchronized void setPoint(Point newpoint) {
		/**
		 * @REQUIRES:newpoint in 80*80;
		 * @MODIFIES:point;
		 * @EFFECTS:point==newpoint;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.point=newpoint;
	}
	public void setdesPoint(Point newpoint) {
		/**
		 * @REQUIRES:newpoint in 80*80;
		 * @MODIFIES:des_point;
		 * @EFFECTS:des_point==newpoint;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.des_point=newpoint;
	}
	public void setCredit(int newcredit) {
		/**
		 * @REQUIRES:newcredit>=0;
		 * @MODIFIES:credit;
		 * @EFFECTS:credit==newcredit;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		this.credit=newcredit;
	}
	public synchronized void dispatch(Passenger p) {//once be dispatched, delete all orders
		/**
		 * @REQUIRES:signal==false;
		 * @MODIFIES:signal,order,order.out.write;
		 * @EFFECTS:order==p && signal==true && order.out.write"Dispatched...";
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked(p);
		 */
		//synchronized(p) {
		this.order = p;
		//this.time=p.getTime()+7500;
		this.signal = true;
		this.wait_count=0;
		this.stop_count=0;
		try {
			this.order.getFw().write(String.format("%d",this.time)+"ms: "+"Dispatched by:"+System.getProperty("line.separator"));
			this.order.getFw().write(this.toString());
			this.order.getFw().flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//System.out.println("lllllllllllllllllllllll");
			//e.printStackTrace();
		// }
		}
	}
	
	public synchronized boolean arrived(Point p) {
		/**
		 * @REQUIRES:p in 80*80;
		 * @MODIFIES:order.out.write;
		 * @EFFECTS:\result==(point==p);
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked(order);
		 */
		if(this.order!=null && this.point.equals(p)) {
			synchronized(this.order) {
			try {
				if(p.equals(this.getOrderPoint()))
					this.order.getFw().write(String.format("%d",this.time)+"ms: Get passenger ("+this.getOrderPoint().x+","+this.getOrderPoint().y+")"+System.getProperty("line.separator"));
				else
					this.order.getFw().write(String.format("%d",this.time)+"ms: "+"Arrived at destination ("+this.getOrderDesPoint().x+","+this.getOrderDesPoint().y+")"+System.getProperty("line.separator"));
				this.order.getFw().flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			return true;
			}
		}
		else {
			return false;
		}
	} 
	/*private int[][] getflow(){
		int [][]flow = new int[80][80];
		for(int i=0;i<80;i++)
			for(int j=0;j<80;j++) flow[i][j]=0;
		for(Taxi t:this.drivers.Tx) {
			switch(t.getcurdir()) {
			case 1:
				flow[t.getPoint().x][t.getPoint().y+1]++;
				flow[t.getPoint().y+1][t.getPoint().x]++;
				break;
			case 2:
				flow[t.getPoint().x+1][t.getPoint().y]++;
				flow[t.getPoint().y][t.getPoint().x+1]++;
				break;
			case 3:
				flow[t.getPoint().x][t.getPoint().y-1]++;
				flow[t.getPoint().y-1][t.getPoint().x]++;
				break;
			}
		}
		return flow;
	}*/
	public void random_go() {//1234
		/**
		 * @REQUIRES:
		 * @MODIFIES:point,time,TaxiGUI,wait_count;
		 * @EFFECTS:point==random_point_around_this_point;
		 * 			time==\old(time)+500;
		 *			update TaxiGUI tg;
		 * 			wait_count==\old(wait_count)+500;
		 * @THREAD_REQUIRES:\locked();
		 * @THREAD_EFFECTS:\locked();
		 */
		//System.out.println(this.No+"random");
		if(this.next_point!=null) {
			//System.out.println(this.No+"next point"+this.next_point);
			if(this.next_point.x-this.point.x > 0) this.dir = 2;
			else if(this.next_point.x-this.point.x < 0) this.dir = 4;
			else if(this.next_point.y-this.point.y > 0) this.dir = 1;
			else this.dir = 3;
			//ma.addFlow(this.point, this.next_point, 1);
			Map.AddFlow(this.point.x, this.point.y, this.next_point.x, this.next_point.y);
			guigv.AddFlow(this.point.x, this.point.y, this.next_point.x, this.next_point.y);
			this.wait_count+=500;
			this.time = this.wakeup;
			this.oldlight = 0;
			//this.point=this.next_point;
			//System.out.println(this.No+" "+gv.getTime()+" "+this.point);
			this.next_point=null;
			tg.SetTaxiStatus(this.No, this.point, 2);//waiting
			//this.wakeup+=500;
			
			this.jud_light(dir,2);
			/*
			for(int j=0;j<dr.size();j++) {
				if(dr.get(j).wakeup>this.wakeup) {
					dr.remove(this);
					dr.add(j,this);
				}
			}
			
			*/
			
			return;
		}
		int []dir = new int[4];
		int []flo = new int[4];
		int t=0;
		//map=ma.map;
		//System.out.println(this.No+"ready to lock flowmap");
		/******************************************************??*/
		Map.UpdateFlow();
		//System.out.println(this.No+"flowmap free");
		int p=this.point.x*80+this.point.y;
		//System.out.println(p+"pppppppppppppppppp");
		if(this.point.y==0) {
			if(ma.graph[p][p+1]==1) {//right
				dir[t]=1;
				//flo[t]=ma.getsflow()[p][p+1];
				flo[t]=Map.GetFlow(p/80,p%80,(p+1)/80,(p+1)%80);
				t++;
			}
			if(p!=79*80 && ma.graph[p+80][p]==1) {//down
				dir[t]=2;
				//flo[t]=ma.getsflow()[p+80][p];
				flo[t]=Map.GetFlow(p/80,p%80,(p+80)/80,(p+80)%80);
				t++;
			}
			if(p!=0 && ma.graph[p-80][p]==1) {//up
				dir[t]=4;
				//flo[t]=ma.getsflow()[p-80][p];
				flo[t]=Map.GetFlow(p/80,p%80,(p-80)/80,(p-80)%80);
				t++;
			}
		}
		else if(this.point.y==79) {
			if(ma.graph[p][p-1]==1) {//left
				dir[t]=3;
				//flo[t]=ma.getsflow()[p][p-1];
				flo[t]=Map.GetFlow(p/80,p%80,(p-1)/80,(p-1)%80);
				t++;
			}
			if(p!=6399 && ma.graph[p+80][p]==1) {//down
				dir[t]=2;
				//flo[t]=ma.getsflow()[p+80][p];
				flo[t]=Map.GetFlow(p/80,p%80,(p+80)/80,(p+80)%80);
				t++;
			}
			if(p!=79 && ma.graph[p-80][p]==1) {//up
				dir[t]=4;
				//flo[t]=ma.getsflow()[p-80][p];
			flo[t]=Map.GetFlow(p/80,p%80,(p-80)/80,(p-80)%80);
				t++;
			}
		}
		else if(this.point.y<79 && this.point.x==0) {
			if(ma.graph[p][p-1]==1) {//left
				dir[t]=3;
				//flo[t]=ma.getsflow()[p][p-1];
				flo[t]=Map.GetFlow(p/80,p%80,(p-1)/80,(p-1)%80);
				//System.out.println("left"+flo[t]);
				t++;
			}
			if(ma.graph[p][p+1]==1) {//right
				dir[t]=1;
				//flo[t]=ma.getsflow()[p][p+1];
				flo[t]=Map.GetFlow(p/80,p%80,(p+1)/80,(p+1)%80);
				t++;
			}
			if(ma.graph[p+80][p]==1) {//down
				dir[t]=2;
				//flo[t]=ma.getsflow()[p+80][p];
				flo[t]=Map.GetFlow(p/80,p%80,(p+80)/80,(p+80)%80);
				t++;
			}
		}
		else if(this.point.x==79 && this.point.y<79) {
			if(ma.graph[p][p-1]==1) {//left
				dir[t]=3;
				//flo[t]=ma.getsflow()[p][p-1];
				flo[t]=Map.GetFlow(p/80,p%80,(p-1)/80,(p-1)%80);
				t++;
			}
			if(ma.graph[p][p+1]==1) {//right
				dir[t]=1;
				//flo[t]=ma.getsflow()[p][p+1];
				flo[t]=Map.GetFlow(p/80,p%80,(p+1)/80,(p+1)%80);
				t++;
			}
			if(ma.graph[p-80][p]==1) {//up
				dir[t]=4;
				//flo[t]=ma.getsflow()[p-80][p];
				flo[t]=Map.GetFlow(p/80,p%80,(p-80)/80,(p-80)%80);
				t++;
			}
		}
		else {
			if(ma.graph[p][p-1]==1) {//left
				dir[t]=3;
				//flo[t]=ma.getsflow()[p][p-1];
				flo[t]=Map.GetFlow(p/80,p%80,(p-1)/80,(p-1)%80);
				t++;
			}
			if(ma.graph[p][p+1]==1) {//right
				dir[t]=1;
				//flo[t]=ma.getsflow()[p][p+1];
				flo[t]=Map.GetFlow(p/80,p%80,(p+1)/80,(p+1)%80);
				t++;
			}
			if(ma.graph[p-80][p]==1) {//up
				dir[t]=4;
				//flo[t]=ma.getsflow()[p-80][p];
				flo[t]=Map.GetFlow(p/80,p%80,(p-80)/80,(p-80)%80);
				t++;
			}
			if(ma.graph[p+80][p]==1) {//down
				dir[t]=2;
				//flo[t]=ma.getsflow()[p+80][p];
				flo[t]=Map.GetFlow(p/80,p%80,(p+80)/80,(p+80)%80);
				t++;
			}
		}
		//System.out.println(this.No+"fuck");
		int i=0,j=0,temp=0;
		for(i=0;i<t-1;i++) {
			for(j=0;j<t-1;j++) {
				if(flo[j+1]<flo[j]) {
					temp=flo[j+1];
					flo[j+1]=flo[j];
					flo[j]=temp;
					temp=dir[j+1];
					dir[j+1]=dir[j];
					dir[j]=temp;
				}
			}
		}
		//System.out.println("t="+t);
		int k=0;
		while(k<t && flo[k]==flo[0]) k++;
		Random r = new Random();
		int ra = r.nextInt(100);
		int direc = ra%(k);
		//while(dir[dirc]==this.dir) ;
		//this.wait_count+=500;///////////////////////////////
		this.time+=500;
		//System.out.println(this.No);
		this.jud_light(dir[direc],2);
	}
	public void setPath(int go_x,int go_y) {
		/**
		 * @REQUIRES:0<=go_x,go_y<80;
		 * @MODIFIES:path[],des_point,t;
		 * @EFFECTS:path[]==ma.spfa.translate_to_path;
		 * 					des_point==(go_x,go_y);
		 * @THREAD_REQUIRES:\locked();
		 * @THREAD_EFFECTS:\locked(ma);
		 */
		//System.out.println("start"+System.currentTimeMillis());
		this.t=0;
		this.des_point=new Point(go_x,go_y);
		//synchronized(this.Di) {
		synchronized(this.ma) {
			//this.Di.getpath(go_x*80+go_y,this.point.x*80+ this.point.y);
			this.ma.spfa(go_x*80+go_y,this.point.x*80+ this.point.y);	
			if(go_x*80+go_y==this.point.x*80+ this.point.y) {
				tg.SetTaxiStatus(this.No, this.point, 0);
				return;
			}
			int t_=this.point.x*80+ this.point.y;
			//synchronized(this.Di) {
			do{
				//t_ = this.Di.spath[t_];
				t_ = this.ma.getpath()[t_];
				path[t++]=t_;	//////////////////////////////////////one
			}while(t_!=go_x*80+go_y) ;
		}
		this.t=0;
		//System.out.println(this.point+"end"+System.currentTimeMillis());
	}
	private boolean jud_light(int direc,int curstatus) {//（当前运动方向，当前状态）不改变时间信息，更新下次醒来时刻，wait_count在
		/**
		 * @REQUIRES:direc>=1 && direc<=4 && curstatus>=0 && curstatus<=3;
		 * @MODIFIES:wakeup,next_point,point,oldlight,oldlightwake,dir,Map.flowmap,wait_count;
		 * @EFFECTS:(dir==1 && ((oldlight==1 && direc==4)||(oldlight==2 && direc==1))) ==> \this.wakeup = \this.oldlightwake+500;
		 * 			(dir==1 && !((oldlight==1 && direc==4)||(oldlight==2 && direc==1))) ==> \this.wakeup = \(old)\this.wakeup+500;
		 * 			(dir==2 && ((oldlight==1 && direc==2) || (oldlight==2 && direc==1))) ==> \this.wakeup = \this.oldlightwake+500;
		 * 			(dir==2 && !((oldlight==1 && direc==2) || (oldlight==2 && direc==1)))==> \this.wakeup = \(old)\this.wakeup+500;
		 * 			(dir==3 && ((oldlight==1 && direc==2) || (oldlight==2 && direc==3))) ==> \this.wakeup = \this.oldlightwake+500;
		 * 			(dir==3 && !((oldlight==1 && direc==2) || (oldlight==2 && direc==3))) ==> \this.wakeup = \(old)\this.wakeup+500;
		 * 			(dir==4 && ((oldlight==1 && direc==4) || (oldlight==2 && direc==3))) ==> \this.wakeup = \this.oldlightwake+500;
		 * 			(dir==4 && !((oldlight==1 && direc==4) || (oldlight==2 && direc==3))) ==> \this.wakeup = \(old)\this.wakeup+500;
		 * 			(\all,Taxi t:dr,i<j && dr[i].wakeup<dr[j].wakeup);
		 * 			set next_point || set point;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked(ma);
		 */
		boolean stop_flag=false;
		switch(this.dir) {
		case 1:
			if((oldlight==1 && direc==4)||(oldlight==2 && direc==1)) {
				stop_flag = true;
				this.wakeup = this.oldlightwake+500;//wake
			}
			else this.wakeup+=500;
			break;
		case 2:
			if((oldlight==1 && direc==2) || (oldlight==2 && direc==1)) {
				stop_flag = true;
				this.wakeup = this.oldlightwake+500;
			}else this.wakeup+=500;
			break;
		case 3:
			if((oldlight==1 && direc==2) || (oldlight==2 && direc==3)) {
				stop_flag = true;
				this.wakeup = this.oldlightwake+500;
			}else this.wakeup+=500;
			break;
		case 4:
			if((oldlight==1 && direc==4) || (oldlight==2 && direc==3)) {
				stop_flag = true;
				this.wakeup = this.oldlightwake+500;
			}else this.wakeup+=500;
			break;
		default: this.wakeup+=500;break;
		}
		
		/*
		for(int j=0;j<dr.size();j++) {
			if(dr.get(j).wakeup>this.wakeup) {
				dr.remove(this);
				dr.add(j,this);
				break;
			}
		}*/

		if(stop_flag) {
			//设置下次到达节点信息
			this.next_point = new Point(this.point.x,this.point.y);
			switch(direc) {
			case 1:
				this.next_point.y++;
				break;
			case 2:
				this.next_point.x++;
				break;
			case 3:
				this.next_point.y--;
				break;
			case 4:
				this.next_point.x--;
				break;
			}
		}
		else {
			//保留当前节点红绿灯信息，更新方向，流量
			this.wait_count+=500;
			Point old = new Point(this.point.x,this.point.y);
			switch(direc) {
			case 1:
				this.point.y++;
				break;
			case 2:
				this.point.x++;
				break;
			case 3:
				this.point.y--;
				break;
			case 4:
				this.point.x--;
				break;
			}
			//System.out.println(this.No+" "+gv.getTime()+" "+this.point);
			this.dir=direc;
			this.oldlight=this.light.getlight(point);
			if(this.oldlight!=0) this.oldlightwake = this.light.waketime();
			else this.oldlightwake = 0;
			
			//ma.addFlow(old, this.point, 1);
			Map.AddFlow(old.x, old.y, this.point.x, this.point.y);
			guigv.AddFlow(old.x, old.y, this.point.x, this.point.y);
			//System.out.println(this.No+"updategui");
			tg.SetTaxiStatus(this.No, this.point, curstatus);//waiting
		}
		return stop_flag;
	}
	public boolean go() {
		/**
		 * @REQUIRES:des_point exists;
		 * @MODIFIES:point,order.out.write,tg,time;
		 * @EFFECTS:\result==!(arrived(des_point));
		 *			time==\old(time)+500;
		 * 			point==(Point)path[0];
		 * 			order.out.write("taxi message...");
		 * 			update TaxiGUI tg;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked(ma);
		 */
		if(this.next_point==null) { 
		setPath(des_point.x,des_point.y);
		Point next = new Point(this.path[0]/80,this.path[0]%80);
		if(next.x-this.point.x > 0) dir = 2;
		else if(next.x-this.point.x < 0) dir = 4;
		else if(next.y-this.point.y > 0) dir = 1;
		else dir = 3;
		this.jud_light(dir,this.sta_num(this.getStatus()));
		
		//ma.addFlow(next, this.point, 1);
			//this.point=next;
			//t++;
			this.time+=500;
			try {
				this.order.getFw().write(String.format("%d",this.time)+"ms: Taxi "+this.No+" at point ("+this.getX()+","+this.getY()+")"+System.getProperty("line.separator"));
				this.order.getFw().flush();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			tg.SetTaxiStatus(this.No, this.point, this.sta_num(this.getStatus()));
			
		}
		else {
			if(this.next_point.x-this.point.x > 0) this.dir = 2;
			else if(this.next_point.x-this.point.x < 0) this.dir = 4;
			else if(this.next_point.y-this.point.y > 0) this.dir = 1;
			else this.dir = 3;
			//ma.addFlow(this.point, this.next_point, 1);
			Map.AddFlow(this.point.x, this.point.y, this.next_point.x, this.next_point.y);
			guigv.AddFlow(this.point.x, this.point.y, this.next_point.x, this.next_point.y);
			//this.wait_count+=500;
			this.time = this.wakeup;
			this.oldlight = 0;
			//this.point=this.next_point;
			//System.out.println(this.No+" "+gv.getTime()+" "+this.point);
			this.next_point=null;
			tg.SetTaxiStatus(this.No, this.point, this.sta_num(this.getStatus()));//waiting
			//this.wakeup+=500;
			this.jud_light(dir,this.sta_num(this.getStatus()));
			/*
			for(int j=0;j<dr.size();j++) {
				if(dr.get(j).wakeup>this.wakeup) {
					dr.remove(this);
					dr.add(j,this);
				}
			}
			*/
			
		}
		//System.out.println(this.No+" "+this.point+"gogogo");
		if(this.arrived(this.des_point)) return false;
		return true;
	}
	public  String toString() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==\this;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return "Taxi: "+this.getNo()+System.getProperty("line.separator")+"Pos:("+this.getPoint().x+","+this.getPoint().y+")"+System.getProperty("line.separator")+"Status: "+this.getStatus()+System.getProperty("line.separator")+"Credit: "+this.credit+System.getProperty("line.separator");
	}
	/*public void run() {
		//try {
		this.time=gv.getTime();
		long from=this.time;
		long to = from+500;
		this.wakeup = to;
		while(true) {
			//System.out.println("sleep for"+(to-from)+"ms");
			if(to<from) gv.stay(0);
			else gv.stay(to-from);
			//System.out.println(this.No+"wake at"+gv.getTime());
			synchronized(this) {
				switch(this.getStatus()) {
				case WAITING://wait for serve2
					if(this.getSignal()) {
						if(this.getPoint().equals(this.getOrderPoint())) {
							this.arrived(this.getOrderPoint());
							this.setPath(this.getOrderDesPoint().x,this.getOrderDesPoint().y);
							this.empty=false;
							this.setStatus(TaxiStatus.STOP);
						}
						else {
							this.setPath(this.getOrderPoint().x,this.getOrderPoint().y);
							this.setStatus(TaxiStatus.DISPATCHED);
						}
						this.time+=500;
						this.wakeup+=500;
					}
					else {
						//System.out.println(wait_count);
						this.random_go();
						if(this.getWait()>=20000) {
							this.setStatus(TaxiStatus.STOP);
							this.resetWait();
							break;
						}
						
					}
					//System.out.println(this.No+" *********** should be"+this.wakeup);
					break;
				case STOP://stop0
					//gv.stay(1000);
					this.stop_counter(500);//sop_+
					if(this.getStop()>=1000) {
						if(!this.empty) this.setStatus(TaxiStatus.SERVING);
						else this.setStatus(TaxiStatus.WAITING);
						//this.random_go();哼
						this.resetStop();
						break;
					}
					this.wakeup+=500;
					break;
				case DISPATCHED://received3
					//System.out.println("car"+this.getNo()+"receiving");
					if(!this.go()){//update wakeup
						this.setPath(this.getOrderDesPoint().x,this.getOrderDesPoint().y);
						this.empty=false;
						this.setStatus(TaxiStatus.STOP);//get on
					}
					break;
				case SERVING://serving1
					if(!this.go()){
						this.empty=true;
						this.setStatus(TaxiStatus.STOP);
						this.signal=false;
						this.getOrder().setEnd();
					}
					break;
				}
				to = this.wakeup;
				//System.out.println(this.No+"wakeup"+to);
			}
			//ma.copyFlow();
			//ma.resetnewsFlow();
			//guigv.ClearFlow();
			
			from = gv.getTime();
			//System.out.println(this.No+"sleepfrom"+from);
		}
		//}catch(Exception e) {
		//	System.out.println(e);
		//}
	}*/
}
