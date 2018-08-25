import java.io.File;
import java.util.Random;

public class Main {
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == true;
		 */
		return true;
	}
	public static void main(String[] args) {
		try {
			/**
			 * @REQUIRES:
			 * @MODIFIES:
			 * @EFFECTS:initialize Orders,Drivers,TaxiGUI,Map,TaxiSystem,OrderHandler,Load,TestThread,LightSystem;
			 * 			load map and light map;
			 * 			start TaxiSystem,OrderHandler,TestThread,LightSystem;
			 */
			File map_f = new File("map.txt");
			File lightmap = new File("lightmap.txt");
			Orders o = new Orders();
			Drivers dr = new Drivers();
			TaxiGUI tg = new TaxiGUI();
			Map ma = new Map(map_f,dr);
			/*************************red light initialize*************************/
			Random r = new Random();
			int ratime = r.nextInt(501);
			ratime+=500;
			System.out.println("random red time"+ratime);
			LightSystem light = new LightSystem(lightmap,ratime,tg,ma);
			Thread lightt=new Thread(light);
			System.out.println("light ready...");
			
			/**************************scheduler initialize************************/
			TaxiSystem sys = new TaxiSystem(100
					,map_f,o,dr,tg,ma,light);
			Thread syss=new Thread(sys);
			syss.start();//taxi time begin//////////////////////////////////
			
			
			/**************************input handler*******************************/
			//System.out.println(gv.getTime()+"start scanning orders");
			OrderHandler handler = new OrderHandler(sys,o,tg,ma);
			Thread h = new Thread(handler);
			//you must input a txt file such as "Load load.txt"
			
			/**************************taxi manager initialize*********************/
			AllTaxi all=new AllTaxi(dr,ma,sys);
			Thread al = new Thread(all);
			
			
			
			/**************************load begin**********************************/
			Load lo = new Load(ma,dr,handler,tg,light);
			lo.inputload();
			
			
			light.checkoutmap();
			/****************************test thread initialize********************/
			TestThread test = new TestThread(dr);
			Thread test_ = new Thread(test);
			
			
			
			System.out.println("map and light_map ready...");
			light.updategui();
			
		
			for(int i=0;i<dr.size();i++) {
				Taxi t = dr.get(i);
				tg.SetTaxiStatus(t.getNo(), t.point, t.sta_num(t.status));
			}
			
			System.out.println("gui update finished");
			lightt.start();
			/*for(int i=0;i<dr.size();i++) {
				Taxi t = dr.get(i);
				Thread taxii=new Thread(t);
				taxii.start();
			}*/
			System.out.println("taxi start...");
			
			
			test_.start();
			
			System.out.println("please input order");
			h.start();
			al.start();
		}catch(Exception e) {
			//e.printStackTrace();
		}
		
		
	}
}
