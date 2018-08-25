

public class AllTaxi implements Runnable{
	/**
	 * @overview:
	 * AllTaxi is used to manage Taxi List, which includes updating taxi's condition and controlling the moving of the taxi. 
	 * When this thread starts, it scans the Taxi List to checkout which taxi needs updating.
	 */
	private Drivers drivers;
	//private TaxiGUI tg;
	TaxiSystem ts;
	private Map ma;
	public AllTaxi(Drivers drivers,Map ma,TaxiSystem ts) {
		/**
		 * @REQUIRES:drivers.size!=0 && ma!=null && ts!=null && (\all Taxi t,drivers,t!=null);
		 * @MODIFIES:drivers,ma;
		 * @EFFECTS:initialize drivers,ma;
		 */
		this.drivers = drivers;
		//taxi.tg=tg;
		this.ma=ma;
		this.ts = ts;
	}
	public boolean repOK() {
		/**
		 * @REQUIRES:\this.drivers.size!=0 && \this.ma!=null && \this.ts!=null && (\all Taxi t,drivers,t!=null);
		 * @MODIFIES:
		 * @EFFECTS:\result == \this.drivers.size!=0 && \this.ma!=null && \this.ts!=null && (\all Taxi t,drivers,t!=null);
		 */
		if (this.drivers.size()==0 || this.ts==null || this.ma==null) return false;
		for(int i=0;i<drivers.size();i++) if(drivers.get(i)==null) return false;
		return true;
	}
	public void run() {
		/**
		 * @REQUIRES:
		 * @MODIFIES:drivers;
		 * @EFFECTS:update taxi's condition every 500ms;
		 *		(\exist Taxi t,drivers,t.status==WAITING && t.signal==true) ==> (
		 *				t has arrived at order point ==> t.empty==false,t.status==STOP,t.setpath(order.des_point);
		 *				t has not arrived at order point ==> t.setpath(order.point),t.status==DISPATCHED;
		 *				t.time==\old(t.time)+500;);
		 *		(\exist Taxi t,drivers,t.status==WAITING && t.signal==false) ==>(
		 *				t.random_go;
		 *				t has been waiting for 20s ==> t.status==STOP;);
		 *		(\exist Taxi t,drivers,t.status==STOP) ==>(
		 *				t has been stopping for 1s ==>(
		 *					t carry no passenger ==> t.status==WAITING;
		 *					t carry passengers ==> t.status==SERVING;));
		 *		(\exist Taxi t,drivers,t.status==DISPATCHED) ==>(
		 *				t has arrived at order.point ==> t.status==STOP;
		 *				t has not arrived at order.point ==> t.go;);
		 *		(\exist Taxi t,drivers,t.status==SERVING) ==>(
		 *				t has arrived at order.des_point ==> t.status==STOP && order.setend;
		 *				t has not arrived at order.des_point ==> t.go;);
		 * @THREAD.REQUIRES:
		 * @THREAD.EFFECTS:\locked(\all Taxi t,dirvers);
		 */
		//try {

		Taxi []wakelist = new Taxi[100];
		int tail=0;
			while(true) {
				//System.out.println("sleep for"+(to-from)+"ms");
				//System.out.println(taxi.No+"wake at"+gv.getTime());
				for(int i=0;i<drivers.size();i++) {
					Taxi taxi = drivers.get(i);
					
					if(taxi.wakeup <= gv.getTime()) {
						//System.out.println("wakeup"+taxi.wakeup+"now"+gv.getTime());
						wakelist[tail++]=taxi;
					}
				}
				if(tail==0) continue;
				synchronized(ts) {
				for(int i=0;i<tail;i++) {
					Taxi taxi = wakelist[i];
				//synchronized(taxi) {
					switch(taxi.getStatus()) {
					case WAITING://wait for serve2
						//System.out.println();
						if(taxi.getSignal()) {
							if(taxi.getPoint().equals(taxi.getOrderPoint())) {
								taxi.arrived(taxi.getOrderPoint());
								taxi.setPath(taxi.getOrderDesPoint().x,taxi.getOrderDesPoint().y);
								taxi.empty=false;
								taxi.stop_counter(500);
								taxi.setStatus(TaxiStatus.STOP);
							}
							else {
								taxi.setPath(taxi.getOrderPoint().x,taxi.getOrderPoint().y);
								taxi.setStatus(TaxiStatus.DISPATCHED);
							}
							taxi.time+=500;
							taxi.wakeup+=500;
							for(int j=0;j<drivers.size();j++) {
								if(drivers.get(j).wakeup>taxi.wakeup) {
									drivers.remove(taxi);
									drivers.add(j,taxi);
								}
							}
						}
						else {
							//System.out.println(wait_count);
							taxi.random_go();
							if(taxi.getWait()>=20000) {
								taxi.setStatus(TaxiStatus.STOP);
								taxi.resetWait();
								//System.out.println(gv.getTime()+taxi.getNo()+" "+taxi.point);
								break;
							}
							
						}
						//System.out.println(taxi.No+" *********** should be"+taxi.wakeup);
						//System.out.println(gv.getTime()+taxi.getNo()+" "+taxi.point);
						break;
					case STOP://stop0
						//gv.stay(1000);
						taxi.stop_counter(500);//sop_+
						if(taxi.getStop()>=1000) {
							if(!taxi.empty) taxi.setStatus(TaxiStatus.SERVING);
							else taxi.setStatus(TaxiStatus.WAITING);
							//taxi.random_go();哼
							taxi.resetStop();
							taxi.wakeup+=500;
							break;
						}
						taxi.wakeup+=500;
						for(int j=0;j<drivers.size();j++) {
							if(drivers.get(j).wakeup>taxi.wakeup) {
								drivers.remove(taxi);
								drivers.add(j,taxi);
							}
						}
						//System.out.println(taxi.getNo()+" "+taxi.point);
						break;
					case DISPATCHED://received3
						//System.out.println("car"+taxi.getNo()+"receiving");
						if(!taxi.go()){//update wakeup
							taxi.setPath(taxi.getOrderDesPoint().x,taxi.getOrderDesPoint().y);
							taxi.empty=false;
							taxi.setStatus(TaxiStatus.STOP);//get on
						}
						//System.out.println(gv.getTime()+taxi.getNo()+" "+taxi.point);
						break;
					case SERVING://serving1
						if(!taxi.go()){
							taxi.empty=true;
							taxi.setStatus(TaxiStatus.STOP);
							taxi.signal=false;
							taxi.getOrder().setEnd();
						}
						//System.out.println(gv.getTime()+taxi.getNo()+" "+taxi.point);
						break;
					}
					//to = taxi.wakeup;
					//System.out.println(taxi.No+"wakeup"+to);
				}
				
				//ma.copyFlow();
				//ma.resetnewsFlow();
				//guigv.ClearFlow();
				
				//from = gv.getTime();
				//System.out.println(taxi.No+"sleepfrom"+from);
			//}
				ts.notify();
				}
				tail=0;
			}
		//}catch(Exception e) {
		//	System.out.println(e);
		//}
	}
}
