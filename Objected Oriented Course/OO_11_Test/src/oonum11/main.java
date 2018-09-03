package oonum11;

import java.awt.Point;
import java.io.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


class mapInfo{
	
	/**
	 * @OVERVIEW: 该类负责将文件中的道路信息和红绿灯信息抽象化存在数组中方便使用 ;
	 * @INHERIT: none;
	 */
	public int[][] map=new int[80][80];
	public int[][] light = new int[80][80];
	/**
	 * @REQUIRES: none;
	 * @MODIFIES: none;
	 * @EFFECTS: none;
	 */
	public boolean repOK() {
		int i,j;
		for(i = 0;i<80;i++) {
			for(j = 0;j<80;j++) {
				if(map[i][j]>3||map[i][j]<0) return false;
				if(light[i][j]!= 0 &&light[i][j]!=1) return false;
			}
		}
		return true;
	}
	/**
	 * @REQUIRES: none;
	 * @MODIFIES:  this.map;
	 * @EFFECTS:  this.map!=\old(this).map;
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
	 * @REQUIRES: none;
	 * @MODIFIES:  this.light;
	 * @EFFECTS:  this.light!=\old(this).light;
	 */
    public void readlight(String path){
		
		Scanner scan=null;
		File file=new File(path);
		if(file.exists()==false){
			System.out.println("红绿灯文件不存在,程序退出");
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
				System.out.println("红绿灯文件信息有误，程序退出");
				System.exit(1);
			}
			int k = 0;
			for(int j=0;j<80&&k<strArray.length;k++){
				//try{
					if(strArray[k].equals("1")||strArray[k].equals("0")) {
						this.light[i][j]=Integer.parseInt(strArray[k]);
						j++;
					}
				//}catch(Exception e){
					//System.out.println("红绿灯文件信息有误，程序退出");
					//System.exit(1);
				//}
			}
		}
		scan.close();
	}
	/**
	 * @REQUIRES: (\exist p );
	 * @MODIFIES: none;
	 * @EFFECTS: term!= none;
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
	/**
	 * @OVERVIEW: 该类是主函数;
	 * @INHERIT: none;
	 */
	  public static Taxi[] taxiline = new Taxi[100];
	  public static Taxinew[] taxinewline = new Taxinew[30];
	  static final String restr ="Load";
	  /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
	  public boolean repOK() {
			int i;
			for(i = 0;i<100;i++) {
				if(taxiline[i] == null) return false;
			}
			return true;
	}
	  /**
		 * @REQUIRES: (\all int i; 0<=i<100; this.taxiline[i].location!=null&& this.taxiline[i].state!= null);
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 * 
		 */
	  public static void get_im(int num) {
		  System.out.println(System.currentTimeMillis()+":("+taxiline[num].getlo()[0]+","+taxiline[num].getlo()[1]+")state:"+taxiline[num].gets());
	  }
	  /**
		 * @REQUIRES: (\all int i; 0<=i<100; this.taxiline[i].location!=null&& this.taxiline[i].state!= null);
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 * 
		 */
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
		 * @EFFECTS:  none;
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
  		  File f;
  		  if(strarray.length>1) {
		   f = new File(strarray[1]);}
  		  else {
  			System.out.println("格式不正确,程序退出");
			System.exit(1);
			return;
  		  }
		  if(f.exists()==false){
				System.out.println("文件不存在,程序退出");
				System.exit(1);
				return;
			}
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
			  if(strinf.equals("#light")) {
				  strinf = scan.nextLine();
				  mi.readlight(strinf);//在这里设置红绿灯文件路径
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
  		Light li = new Light(mi.light,mi.map,gui);
  		
  		gi.map = mi.map;
  		gi.initmatrix();
  		for(i = 0; i<100; i++) {
  			if(i<30) {
  			Taxinew t = new Taxinew(i,gui,mi,tray,gi,putout,gv,li);
  			gui.SetTaxiType(i, 1);
  			taxiline[i] = t;
  			taxinewline[i] = t;
  			t.start();}
  			else {
  				Taxi t = new Taxi(i,gui,mi,tray,gi,putout,gv,li);
  				gui.SetTaxiType(i, 0);
  	  			taxiline[i] = t;
  	  			t.start();
  			}
  		}
  		re1.start();
  		re.start();
  		li.start();
  		test test = new test(taxinewline);
  		test.start();
      }
}
