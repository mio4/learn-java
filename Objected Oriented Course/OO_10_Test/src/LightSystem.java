import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class LightSystem extends Thread{
	/**
	 * @overview:LightSystem contains light position in current map and time interval of light change.
	 */
	//private boolean[][] lightpos;//0:null 1:light
	private int[][] colormap = new int[80][80];//0:null 1:ew_green禁止上下 2:sn_green 禁止左转
	private TaxiGUI tg;
	private long time,to;
	private Map ma;
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == (\all,int color;colormap[][],color>=0) && 500<=\this.time<=1000 && \this.tg!=null && \this.ma!=null;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		boolean minus=false;
		for(int i=0;!minus && i<80;i++) {
			for(int j=0;j<80;j++) {
				if(colormap[i][j]<0) {
					minus=true;
					break;
				}
			}
		}
		return (tg!=null && ma!=null && time>=500 && time<=1000 && to>=0 && minus);
	}
	public LightSystem (File lightmap,long t,TaxiGUI tg,Map ma) {
		/**
		 * @REQUIRES:lightmap.exist && 500<=t<=1000 && tg!=null && ma!=null;
		 * @MODIFIES:\this.tg, \this.time, \this.to, \this.ma;
		 * @EFFECTS:\this.time == time && \this.to == to && \this.tg == tg && \this.ma == ma;
		 * 			initialize colormap[][];
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		this.time=t;
		this.to = 0;
		this.tg = tg;
		this.ma = ma;
		translightmap(lightmap);
	}
	public void checkoutmap() {
		/**
		 * @REQUIRES:\this.ma!=null && this.colormap!=null;
		 * @MODIFIES:\this.colormap[][];
		 * @EFFECTS:(\exist,int color;colormap[][],Point(i,j) link <= 2 point && color!=0 && tg.setlightstatus 0)
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		int size=80;
		int link[][]=new int[size][size];
		for(int i=0;i<size;i++) 
			for(int j=0;j<size;j++) link[i][j]=0;
		
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(i<size-1 && ma.graph[i*size+j][(i+1)*size+j]==1) link[i][j]++;
				if(j<size-1 && ma.graph[i*size+j][i*size+j+1]==1) link[i][j]++;
				if(i>0 && ma.graph[i*size+j][(i-1)*size+j]==1) link[i][j]++;
				if(j>0 && ma.graph[i*size+j][i*size+j-1]==1) link[i][j]++;
			}
		}
		for(int i=0;i<size;i++) {
			for(int j=0;j<size;j++) {
				if(link[i][j]<3 && colormap[i][j]!=0) {
					System.out.println("lightmap error at ("+i+","+j+")");
					colormap[i][j]=0;
					tg.SetLightStatus(new Point(i,j), 0);
				}
			}
		}
	}
	public synchronized long waketime() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == \this.to;
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked()
		 */
		return this.to;
	}
	public void translightmap(File file) {
		/**
		 * @REQUIRES:file.exist;
		 * @MODIFIES:\this.colormap[][];
		 * @EFFECTS:\this.colormap[][] reflects light map;
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		if(file.exists()==false){
			System.out.println("红绿灯地图文件不存在,程序退出");
			System.exit(1);
			//return null;
		}
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			int i=0;
			Random r = new Random();
			int racolor = r.nextInt(2);
			String ss = "";
		while((ss = br.readLine())!=null){
			if(i>79) throw new Exception();
			String[] strArray = null;
			try{
				String s = ss.replaceAll(" ", "");
				s = s.replaceAll("\\t", "");
				strArray=s.split("");
			}catch(Exception e){
				System.out.println("红绿灯地图文件信息有误，程序退出.");
				System.exit(1);
			}
			for(int j=0;j<80;j++){
				try{
					int t =Integer.parseInt(strArray[j]);
					if((t!=0) && (t!=1)) {
						//System.out.println(t+"红绿灯地图文件信息有误，程序退出..///");
						throw new Exception();
					}
					//lightpos[i][j]=(t==0)?false:true;
					
					colormap[i][j]=(t==1)?racolor+1:0;
					//tg.SetLightStatus(new Point(i,j), 1);
				}catch(Exception e){
					//System.out.println(strArray.length);
					System.out.println(e+"红绿灯地图文件信息有误，程序退出..");
					System.exit(1);
				}
			}
			i++;
			if(strArray.length>80) throw new Exception();
		}
		br.close();
		}catch(Exception e) {
			System.out.println("红绿灯地图文件信息有误，程序退出!");
			System.exit(1);
		}
	}
	public void updategui() {
		/**
		 * @REQUIRES:\this.tg!=null;
		 * @MODIFIES:\this.tg;
		 * @EFFECTS:(\all,int color;colormap[][],color==1 && tg.setlightstatus 1);
		 * @THREAD_REQUIRES:
		 * @THREAD_EFFECTS:
		 */
		for(int i=0;i<80;i++)
			for(int j=0;j<80;j++)
				if(colormap[i][j]==1) tg.SetLightStatus(new Point(i,j), 1);
	}
	public synchronized int getlight(Point p) {
		/**
		 * @REQUIRES:p!=null;
		 * @MODIFIES:
		 * @EFFECTS:\result == \this.colormap[p.x][p.y];
		 * @THREAD_REQUIRES:\locked(\this);
		 * @THREAD_EFFECTS:\locked();
		 */
		int x=p.x;
		int y=p.y;
		return colormap[x][y];
	}
	public void run() {
		/**
		 * @REQUIRES:\this.colormap[][]!=null;
		 * @MODIFIES:\this.colormap[][];
		 * @EFFECTS:(\all, int color;\this.colormap[][], ((\(old)color==1 && color==2)||(\(old)color==2 && color==1) ) && tg.setlightstatus);
		 * @THREAD_REQUIRES:\locked();
		 * @THREAD_EFFECTS:\locked(\this);
		 */
		try {
		long from = gv.getTime();
		to = from+time;
		while(true) {
			
			gv.stay(to-from);
			from = gv.getTime();
			synchronized(this) {
			for(int i=0;i<80;i++) {
				for(int j=0;j<80;j++) {
					if(colormap[i][j]==0) continue;
					if(colormap[i][j]==1) colormap[i][j]=2;
					else colormap[i][j]=1;
					tg.SetLightStatus(new Point(i,j), colormap[i][j]);
				}
			}
			
				to = from+this.time;
			}
			
		}
		}catch(Exception e) {
			
		}
	}
}
