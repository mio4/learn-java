import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Map {
	/**
	 * @overview:Save road map, graph metrics and flow information.
	 * You can call methods in Map to update flow information, get flow information, clear flow information, update map and calculate the shortest path.
	 * When you call the method to calculate the shortest path, the path list can be saved in path[], which you can fetch by calling getpath();
	 */
	private static Drivers dr;
	private int[][]map=new int[80][80];
	public int[][]graph=new int[6400][6400];
	//private int[][]distance=new int[6400][6400];
	//private int[][]sflow=new int[6400][6400];
	private int[]path=new int[6400];
	private int[]flow=new int[6400];
	//private int[][]newsflow=new int[6400][6400];
	public static HashMap<String,Integer> flowmap = new HashMap<String,Integer>();//当前流量
	//public static HashMap<String,Integer> memflowmap = new HashMap<String,Integer>();//之前统计的流量
	public Map(File f,Drivers dr) {
		/**
		 * @REQUIRES:f.exist && dr!=null;
		 * @MODIFIES:map[][],graph[][];
		 * @EFFECTS:initialize map[][],graph[][];
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		this.dr=dr;
		transmap(f);
		init();
	}
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == \this.dr!=null && (\all,int m;map[][],m>=0) && (\all,int g;graph[][],g>=0) && (\all,int f;flow[],f>=0);
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		boolean mapminus=false,graphminus=false,flowminus=false;
		for(int i=0;!mapminus && i<80;i++) {
			for(int j=0;!mapminus && j<80;j++) {
				if(map[i][j]<0) mapminus=true;
			}
		}
		for(int i=0;!graphminus && i<6400;i++) {
			for(int j=0;!graphminus && j<6400;j++) {
				if(graph[i][j]<0) graphminus=true;
			}
		}
		for(int j=0;!flowminus && j<6400;j++) {
			if(flow[j]<0) flowminus=true;
		}
		return (this.dr!=null && mapminus && graphminus && flowminus);
	}
	public static void UpdateFlow() {
		/**
		 * @REQUIRES:(\all,Taxi t;\this.dr,t!=null);
		 * @MODIFIES:Map.flowmap;
		 * @EFFECTS:update Map.flowmap;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:\locked(Map.flowmap);
		 */
		synchronized(Map.flowmap) {
			Map.ClearFlow();
			guigv.ClearFlow();
			//synchronized(dr) {
			for(int i=0;i<dr.size();i++) {
			Taxi taxi = dr.get(i);
				
				Point pre =null;
				//synchronized(taxi) {
					//System.out.println("map_scanning"+taxi);
				switch(taxi.dir) {
				case 1:
					pre = new Point(taxi.point.x,taxi.point.y-1);
					break;
				case 2:
					pre = new Point(taxi.point.x-1,taxi.point.y);
					break;
				case 3:
					pre = new Point(taxi.point.x,taxi.point.y+1);
					break;
				case 4:
					pre = new Point(taxi.point.x+1,taxi.point.y);
					break;
				default:pre = new Point(0,0);break;
				}
				//System.out.println("map_scanningover"+taxi);
				//}
				Map.AddFlow(pre.x, pre.y, taxi.point.x, taxi.point.y);
				guigv.AddFlow(pre.x, pre.y, taxi.point.x, taxi.point.y);
				
			//}
			}
			}
	}
	public static String Key(int x1,int y1,int x2,int y2){//生成唯一的Key
		/**
		 * @REQUIRES:x1,x2,y1,y2>=0 && x1,x2,y1,y2<80
		 * @MODIFIES:
		 * @EFFECTS:\result == "x1,y1,x2,y2";
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		return ""+x1+","+y1+","+x2+","+y2;
	}
	public static int GetFlow(int x1,int y1,int x2,int y2){//查询流量信息
		/**
		 * @REQUIRES:x1,x2,y1,y2>=0 && x1,x2,y1,y2<80
		 * @MODIFIES:
		 * @EFFECTS:\result == (Map.flowmap.get(Key(x1,y1,x2,y2))==null) ? 0 : Map.flowmap.get(Key(x1,y1,x2,y2));
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:\locked(Map.flowmap);
		 */
		synchronized (Map.flowmap) {
			return (Map.flowmap.get(Key(x1,y1,x2,y2))==null) ? 0 : Map.flowmap.get(Key(x1,y1,x2,y2));
		}
	}
	public static void AddFlow(int x1,int y1,int x2,int y2){//增加一个道路流量
		/**
		 * @REQUIRES:x1,x2,y1,y2>=0 && x1,x2,y1,y2<80
		 * @MODIFIES:
		 * @EFFECTS:(Map.flowmap.get(Key(x1,y1,x2,y2))==\(old)(Map.flowmap.get(Key(x1,y1,x2,y2))+1;
		 * 			(Map.flowmap.get(Key(x2,y2,x1,y1))==\(old)(Map.flowmap.get(Key(x2,y2,x1,y1))+1;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:\locked(Map.flowmap);
		 */
		synchronized (Map.flowmap) {
			//查询之前的流量数量
			int count=0;
			count=Map.GetFlow(x1,y1,x2,y2);
			//添加流量
			Map.flowmap.put(Key(x1,y1,x2,y2), count+1);
			Map.flowmap.put(Key(x2,y2,x1,y1), count+1);
		}
	}
	public static void ClearFlow(){
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:Map.flowmap=new HashMap<String, Integer>();
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:\locked(Map.flowmap);
		 */
		synchronized (Map.flowmap) {
			Map.flowmap=new HashMap<String, Integer>();
		}
	}
	public synchronized void changemap(File f) {
		/**
		 * @REQUIRES:f.exist;
		 * @MODIFIES:this.map[][],this.graph[][];
		 * @EFFECTS:translate map[][] by reading file f, update graph[][];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		transmap(f);
		init();
	}
	public synchronized void changeroad(Point p1,Point p2,int open) {
		/**
		 * @REQUIRES:p1,p2 in 80*80,open==0||open==1;
		 * @MODIFIES:graph[][],map[][];
		 * @EFFECTS:update graph[][],map[][];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		if(open==0) open=gv.MAXNUM;
		graph[p1.x*80+p1.y][p2.x*80+p2.y]=open;
		graph[p2.x*80+p2.y][p1.x*80+p1.y]=open;
		
	}
	public synchronized int[][] getmap() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==map[][];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return map;
	}
	public synchronized int[] getpath() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==path[];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		return path;
	}
	//public synchronized int[][] getsflow() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result==sflow[][];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
	//	return sflow;
	//}
	//public synchronized void setRoadStatus(Point p1,Point p2,int sta) {
	//	graph[p1.x+p1.y*80][p2.x+p2.y*80]=sta;
	//}
	//public synchronized void addFlow(Point p1,Point p2,int n) {
		/**
		 * @REQUIRES:p1 and p2 in 80*80 matrix;
		 * @MODIFIES:newsflow[][];
		 * @EFFECTS:newsflow[p1.x*80+p1.y][p2.x*80+p2.y]==\old(newsflow[p1.x*80+p1.y][p2.x*80+p2.y])+n;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
	//	newsflow[p1.x*80+p1.y][p2.x*80+p2.y]+=n;
	//	newsflow[p2.x*80+p2.y][p1.x*80+p1.y]+=n;
	//}
	//public synchronized void addOldFlow(Point p1,Point p2,int n) {
		/**
		 * @REQUIRES:p1 and p2 in 80*80 matrix;
		 * @MODIFIES:sflow[][];
		 * @EFFECTS:sflow[p1.x*80+p1.y][p2.x*80+p2.y]==\old(sflow[p1.x*80+p1.y][p2.x*80+p2.y])+n;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
	//	sflow[p1.x*80+p1.y][p2.x*80+p2.y]+=n;
	//	sflow[p2.x*80+p2.y][p1.x*80+p1.y]+=n;
	//}
	//public synchronized void resetnewsFlow() {
		/**
		 * @REQUIRES:newsflow[][] size is 6400*6400;
		 * @MODIFIES:
		 * @EFFECTS:\result==sflow[][];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
	//	for(int i=0;i<6400;i++) {
	//		for(int j=0;j<6400;j++) newsflow[i][j]=0;
	//	}
	//}
	//public synchronized void copyFlow() {
		/**
		 * @REQUIRES:sflow and newsflow[][] size is 6400*6400;
		 * @MODIFIES:sflow[][];
		 * @EFFECTS:update sflow[][] using newsflow[][];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
	//	for(int i=0;i<6400;i++) {
	//		for(int j=0;j<6400;j++) sflow[i][j]=newsflow[i][j];
	//	}
	//}
	public synchronized void init() {
		/**
		 * @REQUIRES:sflow[][] and graph[][] and newsflow[][] size is 6400*6400,map[][] size is 80*80;
		 * @MODIFIES:sflow[][],graph[][],newsflow[][],map[][];
		 * @EFFECTS:initial sflow[][],graph[][],newsflow[][],map[][] metrix;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		for (int i = 0; i < 6400; i++) {
			for (int j = 0; j < 6400; j++) {
				//sflow[i][j]=0;
				//newsflow[i][j]=0;
				if (i == j) {
					graph[i][j] = 0;
					//System.out.println("i="+i+"j="+j+"g="+graph[i][j]);
				}
				else {
					graph[i][j] = gv.MAXNUM;
				}
			}
		}
		for (int i = 0; i < 80; i++) {
			for (int j = 0; j < 80; j++) {
				if (map[i][j] == 1 || map[i][j] == 3) {
					graph[i * 80 + j][i * 80 + j + 1] = 1;
					graph[i * 80 + j + 1][i * 80 + j] = 1;
				}
				if (map[i][j] == 2 || map[i][j] == 3) {
					graph[i * 80 + j][(i + 1) * 80 + j] = 1;
					graph[(i + 1) * 80 + j][i * 80 + j] = 1;
				}
			}
		}
	}
	private synchronized void transmap(File file){//读入地图信息
		/**
		 * @REQUIRES:file.exist;
		 * @MODIFIES:this.map[][],System.out;
		 * @EFFECTS:read file to update map[][];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		if(file.exists()==false){
			System.out.println("地图文件不存在,程序退出");
			System.exit(1);
			//return null;
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			int i=0;
			String ss = "";
		while((ss = br.readLine())!=null){
			if(i>79) throw new Exception();
			String[] strArray = null;
			try{
				String s = ss.replaceAll(" ", "");
				s = s.replaceAll("\\t", "");
				strArray=s.split("");
			}catch(Exception e){
				System.out.println("地图文件信息有误，程序退出.");
				System.exit(1);
			}
			for(int j=0;j<80;j++){
				try{
					int t =Integer.parseInt(strArray[j]);
					if(t<0||t>3) {
						throw new Exception();
					}
					if(j==79 && (t==1||t==3)) {
						throw new Exception();
					}
					if(i==79 && (t==2||t==3)) {
						throw new Exception();
					}
					map[i][j]=t;
				}catch(Exception e){
					System.out.println(strArray.length);
					System.out.println("地图文件信息有误，程序退出..");
					System.exit(1);
				}
			}
			i++;
			
			if(strArray.length>80) throw new Exception();
		}
		br.close();
		}catch(Exception e) {
			System.out.println("地图文件信息有误，程序退出!");
			System.exit(1);
		}
		//return map;
	}
	public synchronized int[] spfa(int v0,int vt) {
		/**
		 * @REQUIRES:(0<=v0) && (vt<=6399);
		 * @MODIFIES:path[],flow[];
		 * @EFFECTS:calculate the shortest path from v0 to vt;
		 *			\result==dist[vt];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		//System.out.println(gv.getTime());
		//System.out.println("spfabegin");
		int INF=1000000,NODE=graph.length;
		boolean [] flag = new boolean[6400];//whether in queue
		int []dist = new int[6400];
		for(int i=0;i<NODE;i++) {
			dist[i]=INF;
			path[i]=-1;
			flag[i]=false;
			flow[i]=INF;
			//Map.ClearFlow();
		}
		Map.ClearFlow();
		for(int i=0;i<dr.size();i++) {
			Taxi taxi = dr.get(i);
			Point pre =null;
			switch(taxi.dir) {
			case 1:
				pre = new Point(taxi.getPoint().x,taxi.getPoint().y-1);
				break;
			case 2:
				pre = new Point(taxi.getPoint().x-1,taxi.getPoint().y);
				break;
			case 3:
				pre = new Point(taxi.getPoint().x,taxi.getPoint().y+1);
				break;
			case 4:
				pre = new Point(taxi.getPoint().x+1,taxi.getPoint().y);
				break;
			default:pre = new Point(0,0);break;
			}
			Map.AddFlow(pre.x, pre.y, taxi.getPoint().x, taxi.getPoint().y);
		}
		
		dist[v0]=0;
		flag[v0]=true;
		path[v0]=0;
		flow[v0]=0;
		int v=0;
		ArrayList <Integer>que = new ArrayList <Integer>();
		que.add(v0);
		int []adj={-1,1,-80,80};
		while(!que.isEmpty()) {
			v=que.get(0);
			que.remove(0);
			flag[v]=false;
			for(int i=0;i<4;i++) {
				if(((v%80)==0) && (i==0)) continue;
				if(((v%80)==79) && (i==1)) continue;
				if(((int)(v/80)==0) && (i==2)) continue;
				if(((int)(v/80)==79) && (i==3)) continue;
				if(graph[v][v+adj[i]]!=0) {
					int x1=v/80;
					int y1=v%80;
					int x2=(v+adj[i])/80;
					int y2=(v+adj[i])%80;
					if((dist[v+adj[i]]>dist[v]+graph[v][v+adj[i]]) || (dist[v+adj[i]]==dist[v]+graph[v][v+adj[i]] && flow[v+adj[i]]>flow[v]+Map.GetFlow(x1,y1,x2,y2))) {
						dist[v+adj[i]]=dist[v]+graph[v][v+adj[i]];
						flow[v+adj[i]]=flow[v]+Map.GetFlow(x1,y1,x2,y2);
						path[v+adj[i]]=v;
						if(v==vt) {
							//System.out.println(gv.getTime());
							return dist;
						}
						if(flag[v+adj[i]]==false) {
							if(!que.isEmpty() && dist[v+adj[i]]<dist[que.get(0)])
								que.add(0, v+adj[i]);
							else que.add(v+adj[i]);
							flag[v+adj[i]]=true;
						}
					}
				}
			}
		}
		return dist;
	}
}
