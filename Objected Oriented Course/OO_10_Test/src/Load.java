import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.Scanner;

public class Load {
	/**
	 * @overview:To load file and load map, lightmap. It sets taxi initial information, flow information and adds some orders.
	 * Once read over, call TaxiGUI to load map.
	 */
	private Map ma;
	private LightSystem light;
	private Drivers dr;
	private OrderHandler oh;
	private TaxiGUI tg;
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == \this.ma!=null && \this.dr!=null && \this.oh!=null && \this.tg!=null && \this.light!=null;
		 */
		return (ma!=null && dr!=null && oh!=null && tg!=null && light!=null);
	}
	public Load(Map ma,Drivers dr,OrderHandler oh,TaxiGUI tg,LightSystem light) {
		/**
		 * @REQUIRES:ma!=null && dr!=null && oh!=null && tg!=null && light!=null;
		 * @MODIFIES:ma.sflow[][];
		 * @EFFECTS:update ma.sflow[][];
		 */
		this.light = light;
		this.ma=ma;
		this.dr=dr;
		this.oh=oh;
		this.tg=tg;
	}
	private Point randompoint(Point def) {
		/**
		 * @REQUIRES:def in 80*80 metrix;
		 * @MODIFIES:
		 * @EFFECTS:\result==random point except point def;
		 */
		Random r = new Random();
		int x = r.nextInt(80);
		int y = r.nextInt(80);
		Point np = new Point(x,y);
		while(np.equals(def)) {
			x = r.nextInt(80);
			y = r.nextInt(80);
			np = new Point(x,y);
		}
		return np;
	}
	private void mapcase(String st) {
		/**
		 * @REQUIRES:st is valid format;
		 * @MODIFIES:ma.map[][],ma.graph[][];
		 * @EFFECTS:update ma.map[][],ma.graph[][];
		 */
		File newmap = new File(st);
		ma.changemap(newmap);
		
	}
	private void flowcase(String st) {
		/**
		 * @REQUIRES:st is valid format;
		 * @MODIFIES:ma.sflow[][];
		 * @EFFECTS:update ma.sflow[][];
		 */
		Point p1 = null,p2 = null;
		String []flowst = st.split(" ");
		for(int i=0;i<2;i++) {
		String s1 = flowst[i].replaceAll("\\(", "");
		String s2 = s1.replaceAll("\\)", "");
		String []flowpoint = s2.split(",");
		if(i==0) p1 = new Point(Integer.valueOf(flowpoint[0]),Integer.valueOf(flowpoint[1]));
		else p2 = new Point(Integer.valueOf(flowpoint[0]),Integer.valueOf(flowpoint[1]));
		}
		int count = Integer.valueOf(flowst[2]);
		//ma.addOldFlow(p1, p2, count);//old
		for(int i=0;i<count;i++) Map.AddFlow(p1.x, p1.y, p2.x, p2.y);
		for(int i=0;i<count;i++) guigv.AddFlow(p1.x, p1.y, p2.x, p2.y);
	}
	private void taxicase(String st) {
		/**
		 * @REQUIRES:st is valid format;
		 * @MODIFIES:dr;
		 * @EFFECTS:update (Taxi)st.message;
		 */
		String []taxist = st.split(" ");
		int no = Integer.valueOf(taxist[0]);
		int status = Integer.valueOf(taxist[1]);
		int credit = Integer.valueOf(taxist[2]);
		String point = taxist[3].replaceAll("\\(", "");
		point = point.replaceAll("\\)", "");
		String []taxipoint = point.split("\\,");
		Point p = new Point(Integer.valueOf(taxipoint[0]),Integer.valueOf(taxipoint[1]));
		Taxi refreshtaxi = dr.get(no);//from no0-99
		TaxiStatus ts = refreshtaxi.num_sta(status);
		
		refreshtaxi.setCredit(credit);
		refreshtaxi.setPoint(p);
		refreshtaxi.iniStatus(ts);
		Point r1 = randompoint(p);
		Point r2 = randompoint(r1);
		if(ts.equals(TaxiStatus.DISPATCHED)) {
			Passenger mmpsg = new Passenger(r1,r2,refreshtaxi);
			refreshtaxi.dispatch(mmpsg);
			refreshtaxi.setPath(mmpsg.getX(),mmpsg.getY());
		}
		else if(ts.equals(TaxiStatus.SERVING)) {
			Passenger mmpsg = new Passenger(r2,r1,refreshtaxi);
			refreshtaxi.dispatch(mmpsg);
			refreshtaxi.setdesPoint(r1);
			refreshtaxi.empty=false;
			refreshtaxi.signal=false;
		}
	}
	private void ordercase(String st) {
		/**
		 * @REQUIRES:st is a valid order;
		 * @MODIFIES:oh.orders;
		 * @EFFECTS:oh.orders.contains((Passenger)st);
		 */
		oh.input(st);
	}
	private void lightcase(String st) {
		File f = new File(st);
		light.translightmap(f);
	}
	private boolean loadread(File f) {
		/**
		 * @REQUIRES:f.exist;
		 * @MODIFIES:ma,dr,oh,System.out;
		 * @EFFECTS:\result==f.exist;
		 * 			read f to check whether change map,add flow,change taxi message,add order;
		 */
		if(!f.exists()) return false;
		else {
			String mapstart = "#map";
			String mapend = "#end_map";
			String flowstart = "#flow";
			String flowend = "#end_flow";
			String taxistart = "#taxi";
			String taxiend = "#end_taxi";
			String orderstart = "#request";
			String orderend = "#end_request";
			String lightstart = "#light";
			String lightend = "#end_light";//2 4    0 9    0 10
			String st = "";
			int label=0;//map:1 flow:2 taxi:3 order:4 else:0
			try {
				FileInputStream fis = new FileInputStream(f);
				InputStreamReader isr = new InputStreamReader(fis);
				BufferedReader br = new BufferedReader(isr);
				while((st=br.readLine())!=null) {
					//System.out.println(st);
					if(st.equals(mapstart)) {
						label=1;
						continue;
					}
					else if(st.equals(mapend)) {
						label=0;
						tg.LoadMap(ma.getmap(), 80);
						continue;
					}
					else if(st.equals(flowstart)) {
						label=2;
						continue;
					}
					else if(st.equals(flowend)) {
						label=0;
						continue;
					}
					else if(st.equals(taxistart)) {
						label=3;
						continue;
					}
					else if(st.equals(taxiend)) {
						label=0;
						continue;
					}
					else if(st.equals(orderstart)) {
						label=4;
						continue;
					}
					else if(st.equals(orderend)) {
						label=0;
						continue;
					}
					else if(st.equals(lightstart)) {
						label=5;
						continue;
					}
					else if(st.equals(lightend)) {
						label=0;
						continue;
					}
					switch(label) {
					case 1:
						mapcase(st);
						break;
					case 2:
						flowcase(st);
						break;
					case 3:
						taxicase(st);
						break;
					case 4:
						ordercase(st);
						break;
					case 5:
						lightcase(st);
						break;
					default:break;
					}
				}
				br.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				System.out.println("something went wrong");
				return false;
			}
			return true;
		}
	}
	private File load(String s) {
		/**
		 * @REQUIRES:File(s).exist;
		 * @MODIFIES:
		 * @EFFECTS:\result==(s is a valid LOAD request);
		 */
		String loadpattern = "LOAD";
		String []st = s.split(" ");
		if(st.length==2 && st[0].equals(loadpattern)) {
			try {
				File f = new File(st[1]);
				if(f.exists()) {
					//loadfile=f;
					return f;
				}
			}catch(Exception e) {
				//e.printStackTrace();
				System.out.println("LOAD FORMAT ERROR");
				return null;
			}
		}
		return null;
	}
	public int[][] inputload() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:ma,dr,System.out,System.in;
		 * @EFFECTS:\result==ma.map[][];
		 * 			System.out("...");
		 */
		Scanner sc = new Scanner(System.in);
		String s=sc.nextLine();
		while(true) {
			File loadfile = load(s);
			if(loadfile!=null) {
				System.out.println("load read begin");
				loadread(loadfile);
				break;
			}
			s= sc.nextLine();
		}
		//sc.close();
		return ma.getmap();
	}
}
