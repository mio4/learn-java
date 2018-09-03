import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class TestThread extends Thread {
	private Drivers dr;
	private PrintStream pr;
	public boolean repOK() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:
		 * @EFFECTS:\result == dr!=null && pr!=null;
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:
		 */
		return dr!=null && pr!=null;
	}
	public TestThread(Drivers dr) {
		/**
		 * @REQUIRES:dr!=null;
		 * @MODIFIES:dr,pr;
		 * @EFFECTS:initialize dr,pr;
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:
		 */
		this.dr = dr;
		File info = new File("information.txt");
		try {
			info.createNewFile();
			FileOutputStream out = new FileOutputStream(info);
			this.pr = new PrintStream(out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void taximsg(int i) {
		/**
		 * @REQUIRES:0<=i<100;
		 * @MODIFIES:pr;
		 * @EFFECTS:pr.println("taxi[i] message");
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:
		 */
		//search for the taxi
		Taxi t = dr.get(i);
		pr.println(gv.getTime()+"Taxi "+i+" time:"+String.format("%d", t.getTime())+" pos:("+t.getPoint().x+","+t.getPoint().y+") "+"status:"+t.getStatus());
	}
	public void staxi(TaxiStatus ts) {
		/**
		 * @REQUIRES:
		 * @MODIFIES:pr;
		 * @EFFECTS:(\exist Taxi t,dr==>(t.status==ts))==>pr.println("t message");
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:
		 */
		//search for taxis with status "ts"
		for(int i=0;i<this.dr.size();i++) {
			if(dr.get(i).getStatus().equals(ts))
				pr.println("status:"+dr.get(i).getStatus()+" Taxi"+dr.get(i).getNo()+" pos:("+dr.get(i).getPoint().x+","+dr.get(i).getPoint().y+") ");
		}
	}
	public void run() {
		/**
		 * @REQUIRES:add test code here;
		 * @MODIFIES:pr;
		 * @EFFECTS:pr.println("message");
		 *			output your message every 500ms;
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:
		 */
		try {
		while(true) {
			//pr.println(System.currentTimeMillis());
				taximsg(0);
				taximsg(1);
				//taximsg(2);
				//staxi(TaxiStatus.DISPATCHED);

				//staxi(TaxiStatus.SERVING);
			gv.stay(300);
		}
		}catch(Exception e) {
		//	e.printStackTrace();
		}
	}
}
