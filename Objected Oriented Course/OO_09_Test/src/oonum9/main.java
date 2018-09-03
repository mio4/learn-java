package oonum9;
import java.awt.Point;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @REQUIRES: none;
 * @MODIFIES: none;
 * @EFFECTS: none;
 */
class mapInfo{

	public int[][] map=new int[80][80];
	/**
	 * @REQUIRES: (\exist String file);
	 * @MODIFIES:  int[][] map;
	 * @EFFECTS:  map!=null;
	 */
	public void readmap(String path){
		
		Scanner scan=null;
		File file=new File(path);
		if(file.exists()==false){
			System.out.println("地图文件不存在,程序退出");
			System.exit(1);
			return;
		}
		try {
			scan = new Scanner(new File(path));
		} catch (FileNotFoundException e) {
			
		}
		for(int i=0;i<80;i++){
			String[] strArray = null;
			try{
				strArray=scan.nextLine().split("");
			}catch(Exception e){
				System.out.println("地图文件信息有误，程序退出");
				System.exit(1);
			}
			for(int j=0;j<80;j++){
				try{
					this.map[i][j]=Integer.parseInt(strArray[j]);
				}catch(Exception e){
					System.out.println("地图文件信息有误，程序退出");
					System.exit(1);
				}
			}
		}
		scan.close();
	}
	/**
	 * @REQUIRES: (\exist Point p );
	 * @MODIFIES: (\all int[][] term);
	 * @EFFECTS: none;
	 */
	public int[][] getpoint(Point p){
	   int[][] term = new int[4][3];
	   int i = 0;
	   if(p.x>0) {
	   if(map[p.x-1][p.y] >= 2) {
			   term[i][0]= p.x-1;
			   term[i][1]= p.y;
			   term[i][2]= 1;
	   }
	   }
	   i++;
	   if(map[p.x][p.y] == 1||map[p.x][p.y] == 3) {
			   term[i][0] = p.x;
			   term[i][1] = p.y+1;
			   term[i][2] = 1;
	   }
	   i++;
	   if(map[p.x][p.y]>=2) {
		   term[i][0] = p.x+1;
		   term[i][1] = p.y;
		   term[i][2] = 1;
	   }
	   i++;
	   if(p.y>2) {
		 
	    if(map[p.x][p.y-1] == 1||map[p.x][p.y-1] == 3) {
		   term[i][0] = p.x;
		   term[i][1] = p.y-1;
		   term[i][2] = 1;
		   
	    }
	   }
	   return term;
		
	}
}
public class main {
	  public static Taxi[] taxiline = new Taxi[100];
	  static final String restr ="Load";
	  /**
		 * @REQUIRES: (\exist num);
		 * @MODIFIES: none;
		 * @EFFECTS: state!=null;
		 */
	  public static void get_im(int num) {
		  System.out.println(System.currentTimeMillis()+":("+taxiline[num].getlo()[0]+","+taxiline[num].getlo()[1]+")state:"+taxiline[num].gets());
	  }
	  public static void get_taxi(int state) {
		  
		  for(int i = 0;i<100;i++) {
			  if(taxiline[i].gets() == state) {
				  System.out.print(i+" ");
			  }
		  }
	  }
	  /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS:  map != null;
		 */
      public static void main (String[] args) {
    	 
    	int i = 0;
    	PrintStream ps1 = null; 
    	//Taxi[] taxiline = new Taxi[100];
    	TaxiGUI gui=new TaxiGUI();
    	guiInfo gi = new guiInfo();
  		mapInfo mi=new mapInfo();
  		guigv gv = new guigv();
  		Tray tray = new Tray();
  		String output = "output.txt";
  		try{
    		ps1 = new PrintStream(output); 
    			}catch(Exception e) {   e.printStackTrace();  }
  		Putout putout = new Putout(ps1);
  		Scanner scan=null;
  		Scanner scan1 = new Scanner(System.in);
  		Requestline re = new Requestline(gui,mi,tray,gi,taxiline,putout,scan1);
  		String strinf;
  		String str = "";
  		Pattern pa1 = Pattern.compile(restr);
   	    Matcher m1 = pa1.matcher(str);
   	    Requestline re1;
   	    
  		while(true) {
  		  str = scan1.nextLine();
  		  m1 = pa1.matcher(str);
  		  if(!m1.find()) {
  			  continue;
  		  }
  		  else {
  		  String[] strarray = str.split(",");
		  File f = new File(strarray[1]);
		  
		  try {
			  scan = new Scanner(new File(strarray[1]));
			} catch (FileNotFoundException e) {
			}
		  while(true) {
			  strinf = scan.nextLine();
			  if(strinf.equals("#map")) {
				  strinf = scan.nextLine();
				  mi.readmap(strinf);//在这里设置地图文件路径
				  continue;
			  }
			  if(strinf.equals("#flow")) {
				  strinf = scan.nextLine();
				  while(!strinf.equals("#end_flow")) {
				  int j;
				  int k;
				  int num;
				  int[] term = new int[5]; 
				  String[] strarray1 = strinf.split("[,)]");
				  for(j=0;j<5;j++) {
						num = 0;
						for(k = 0; k<strarray1[j].length();k++) {
						   if(strarray1[j].charAt(k)>='0'&&strarray1[j].charAt(k)<='9') {
							   num = num*10 +strarray1[j].charAt(k)-'0';
						   }
						   else {
							   continue;
						   }
						}
						term[j] = num;
					}
				   gv.SetFlow(term[0], term[1], term[2], term[3], term[4]);
				   strinf = scan.nextLine();
				  }
				   continue;
			  }
			  if(strinf.equals("#taxi")) {
				  strinf = scan.nextLine();
				  while(!strinf.equals("#end_taxi")) {
					  int j;
					  int k;
					  int num;
					  int[] term = new int[5]; 
					  String[] strarray1 = strinf.split("[,]");
					  for(j=0;j<5;j++) {
							num = 0;
							for(k = 0; k<strarray1[j].length();k++) {
							   if(strarray1[j].charAt(k)>='0'&&strarray1[j].charAt(k)<='9') {
								   num = num*10 +strarray1[j].charAt(k)-'0';
							   }
							   else {
								   continue;
							   }
							}
							term[j] = num;
					   }
					   tray.init(term[0]);
					   tray.setstatus(term[0],term[1]);
					   tray.addcr(term[0],term[2]);
					   int[] term1 = new int[2];
					   term1[0] = term[3];
					   term1[1] = term[4];
					   tray.addnl(term[0], term1);
					   strinf = scan.nextLine();
				  }
				  continue;
			  }
			  if(strinf.equals("#request")) {
				  re1 = new Requestline(gui,mi,tray,gi,taxiline,putout,scan);
				  //re1.start();
				  break;
			  }

			  
		  }
		  break;
  		  }
  		}
  		gui.LoadMap(mi.map, 80);
  		
  		
  		gi.map = mi.map;
  		gi.initmatrix();
  		for(i = 0; i<100; i++) {
  			Taxi t = new Taxi(i,gui,mi,tray,gi,putout,gv);
  			taxiline[i] = t;
  			t.start();
  		}
  		re1.start();
  		re.start();
  		
      }
}
