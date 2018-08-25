import java.awt.Point;
import java.io.File;
import java.util.Random;

public class TaxiSystem implements Runnable{
	/**
	 * @overview:TaxiSystem contains drivers and orders.Drivers is Taxi List. Orders is order List. 
	 * Map is also saved here. When TaxiSystem is initialized, all taxi are initialized in the meantime.
	 */
	private Drivers drivers;
	private Orders orders;
	//private int[][]map;
	//private AllTaxi all;
	private TaxiGUI tg;
	//private Dijk Di;
	private Map ma;
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == map_f.exist && o!=null && dr!=null && tg!=null && ma!=null;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return orders!=null && drivers!=null && tg!=null && ma!=null;
	}
	public TaxiSystem(int count,File map_f,Orders o,Drivers dr,TaxiGUI tg,Map ma,LightSystem light) {
		/**
		 * @REQUIRES:0<count<=100 && map_f.exist && o!=null && dr!=null && tg!=null && ma!=null && light!=null;
		 * @MODIFIES:orders,drivers,ma,map[][],tg,tim,all;
		 * @EFFECTS:initialize orders,drivers,ma,map[][],tg,tim,all;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		this.orders = o;
		this.drivers = dr;
		//this.map = this.transmap(map_f);
		this.ma = ma;
		//this.map = this.ma.getmap();
		this.tg = tg;
		//this.tg.LoadMap(this.map, 80);
		Random r = new Random();
		//this.Di = di;
		long tim = System.currentTimeMillis();
		//this.Di.map=this.map;
		//this.Di.init();
		for(int i=0;i<count;i++) {
			int pos = r.nextInt(6400);
			Taxi t = new Taxi(i,new Point(pos/80,pos%80),tg,this.ma,light,dr);
			drivers.add(t);
			t.time=tim;
			t.wakeup=t.time;
		}
		//this.all=new AllTaxi(drivers,ma);
		//Thread al = new Thread(all);
		//al.start();
		
		System.out.println("taxi ready");
	}
	public void setroadstatus(Point p1,Point p2,int open) {
		/**
		 * @REQUIRES:p1,p2 in 80*80 && open==0||open==1;
		 * @MODIFIES:ma.graph[][],TaxiGUI.map[][];
		 * @EFFECTS:update ma.graph[][],TaxiGUI.map[][];
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:\locked(ma);
		 *   				\locked(tg);
		 */
		synchronized(ma) {
			synchronized(tg){
				ma.changeroad(p1, p2, open);
				tg.SetRoadStatus(p1, p2, open);
				
			}
		}
		
	}
	private int absolute(int i,int j) {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==((i<j)?j-i:i-j);
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:
		 */
		return (i<j)?j-i:i-j;
	}
	private boolean dis_allow(int x1,int x2,int y1,int y2) {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==((distance of(x1,y1)to(x2,y2))<=2);
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:
		 */
		//System.out.println(x1+" "+x2+" "+y1+" "+y2);
		return (absolute(x1,x2)<=2 && absolute(y1,y2)<=2);
	}
	private void send(Passenger p) {
		/**
		 * @REQUIRES:
		 * @MODIFIES:p;
		 * @EFFECTS:(\exist Taxi t,drivers==>(t.signal==false && t.getStatus()==TaxiStatus.WAITING && dis_allow(p.getX(),t.getX(),p.getY(),t.getY())))==>p.received(t);
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:\locked(p);
		 */
		//synchronized(p) {
			for(int i=0;i<drivers.size();i++) {
				boolean jud = dis_allow(p.getX(),drivers.get(i).point.x,p.getY(),drivers.get(i).point.y);
				//System.out.println(jud);
				if(drivers.get(i).signal==false && drivers.get(i).status==TaxiStatus.WAITING && jud) p.received(drivers.get(i));
			}
		//}
	}
	public void run() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:orders.size;
		 * @EFFECTS:remove finished order;
		 *			(\exist Passenger p,orders==>(!p.recv))==>send(p);
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:\locked(orders);
		 */
		try {
		while(true) {
			
			synchronized(this) {synchronized(orders) {
			orders.check();//remove end order
			for(int i=0;i<orders.size();i++) 
				if(!orders.get(i).getRecv()) {
					this.send(orders.get(i));
					//System.out.println("send finish");
				}
			//
			}
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}}
			gv.stay(100);
		}
		}catch(Exception e) {
			//System.out.println(e);
		}
	}
}
