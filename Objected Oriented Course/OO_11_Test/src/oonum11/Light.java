package oonum11;
import java.awt.Point;
import java.util.Random;
public class Light extends Thread {
	
	/**
	 * @OVERVIEW: 该类是红绿灯线程类，通过文件读入的红绿灯信息设置红绿灯并控制刷新;
	 * @INHERIT: Thread;
	 */
      private int light[][] = new int[80][80];
      private int status = 1;
      private TaxiGUI gui;
      /**
		 * @REQUIRES: none;
		 * @MODIFIES: none;
		 * @EFFECTS: none;
		 */
	  public boolean repOK() {
			int i,j;
			for(i = 0;i<80;i++) {
				for(j = 0; j<80;j++) {
					if(light[i][j]!= 0&&light[i][j]!=1) {
						return false;
					}
				}
			}
			if(status!=1&&status!=2&&status!=0) return false;
			return true;
	}
      /**
  	 * @REQUIRES: (\exist light, map);
  	 * @MODIFIES: this;
  	 * @EFFECTS:  this.light!=\old(this).light;
  	 */
      public Light(int[][] light, int[][] map, TaxiGUI gui) {
    	  this.gui = gui;
    	  int i, j;
    	  Point p;
    	  for(i = 0; i<80; i++) {
    		  for(j = 0; j<80; j++) {
    			  if(light[i][j] == 1) {
    				  if(i>0&&i<79&&j>0&&j<79) {
    					  if(map[i][j] == 3&&(map[i-1][j] == 2||map[i-1][j] == 3||map[i][j-1] == 1|| map[i][j-1] == 3)){
    						  p = new Point(i, j);
    						  gui.SetLightStatus(p, 1);
    						  this.light[i][j] = 1;
    						  
    					  }
    					  else if((map[i][j] == 1||map[i][j] == 2)&&(map[i-1][j] == 2||map[i-1][j] == 3)&&(map[i][j-1] == 1|| map[i][j-1] == 3)) {
    						  p = new Point(i,j);
    						  gui.SetLightStatus(p, 1);
    						  this.light[i][j] = 1;
    					  }
    					  else {
    						  System.out.println("("+i+","+j+")不能设置红绿灯");
    					  }
    				  }
    				  else if(i == 0&&j!=0&&map[i][j] == 3&&(map[i][j-1] == 1|| map[i][j-1] == 3)){
    					  p = new Point(i,j);
						  gui.SetLightStatus(p, 1);
						  this.light[i][j] = 1;
    				  }
    				  else if(i == 79&&j!=0&&map[i][j] == 1&&(map[i-1][j] == 2||map[i-1][j] == 3)&&(map[i][j-1] == 1|| map[i][j-1] == 3)) {
    					  p = new Point(i,j);
						  gui.SetLightStatus(p, 1);
						  this.light[i][j] = 1;
    				  }
    				  else if(j == 0&&i!=0&&map[i][j] == 3&&(map[i-1][j] == 2||map[i-1][j] == 3)) {
    					  p = new Point(i,j);
						  gui.SetLightStatus(p, 1);
						  this.light[i][j] = 1;
    				  }
    				  else if(j == 79&&i!=0&&map[i][j]==2&&(map[i-1][j] == 2||map[i-1][j] == 3)&&(map[i][j-1] == 1|| map[i][j-1] == 3)) {
    					  p = new Point(i,j);
						  gui.SetLightStatus(p, 1);
						  this.light[i][j] = 1;
    				  }
    				  else {
    					  System.out.println("("+i+","+j+")不能设置红绿灯");
    				  }
    			  }
    			  else {
    				  continue;
    			  }
    		  }
    	  }
      }
      /**
    	 * @REQUIRES: i>=0 && i<80 && j>=0 && j<80;
    	 * @MODIFIES: none;
    	 * @EFFECTS:  none;
    	 */
      public int getlight(int i , int j) {
    	  return light[i][j];
      }
      /**
  	 * @REQUIRES: none;
  	 * @MODIFIES: none;
  	 * @EFFECTS:  none;
  	 */
      public int getstatus() {
    	  return status;
      }
      /**
    	 * @REQUIRES: none;
    	 * @MODIFIES: none;
    	 * @EFFECTS:  none;
    	 */
      public void run() {
    	  int time;
    	  Random r = new Random();
    	  Point p;
    	  int i,j;
    	  time = Math.abs(r.nextInt()%500);
    	  time += 500;
    	  //time  = 20000;
    	  while(true) {
    		  try {
    			  sleep(time);
    		  }catch(InterruptedException e) {}
    		  for(i = 0; i<80; i++) {
    			  for(j =0 ; j<80;j++) {
    				  if(light[i][j] == 1) {
    					  p = new Point(i,j);
						  if(status == 1) {
							  gui.SetLightStatus(p, 2);
						  }
						  else {
							  gui.SetLightStatus(p, 1);
						  }
    				  }
    			  }
    		  }
    		  if(status == 1) {
    			  status = 2;
    		  }
    		  else status = 1;
    	  }
      }
}


