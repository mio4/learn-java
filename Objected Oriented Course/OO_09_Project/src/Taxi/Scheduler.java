package Taxi;
//调度器类
public class Scheduler extends Thread{
	private RequestQueue requestQueue;
	private Taxi[] taxis;
	private guiInfo GuiInfo;
	private TaxiGUI taxiGUI;
	
	public Scheduler(RequestQueue requestQueue, TaxiGUI taxiGUI) {
		/** @REQUIRES: requestQueue!=null;taxiGUI!=null
		* @MODIFIES: requestQueue,taxiGUI
		* @EFFECTS: None
		*/
		this.requestQueue = requestQueue;
		this.taxiGUI = taxiGUI;
	}
	
	@Override
	public void run() {
		/** @REQUIRES: None
		* @MODIFIES: None
		* @EFFECTS: 不断执行DealReq()方法，处理请求
		*/
		try {
			while(true) {
				DealReq();		
			}
		} catch (Exception e) {}
	}

	public void DealReq() {
		/** @REQUIRES: None
		* @MODIFIES: None
		* @EFFECTS: 对于没有被分配的请求，开启窗口线程，然后分配到合适的出租车
		*/
		for(int i=0; i < requestQueue.getCnt();i++) { 
			if(requestQueue.getUsed(i) == false) { //如果请求没有使用，分配请求
				Window window = new Window(taxis,GuiInfo,taxiGUI); //初始化一个窗口线程
				window.getReq(requestQueue.getStr(i),requestQueue.getSrcx(i), requestQueue.getSrcy(i),requestQueue.getDstx(i),requestQueue.getDsty(i),requestQueue.getTime(i)); //传入请求
				window.start();
				requestQueue.changeUsed().set(i, true); //请求被使用
			}
		}
	}
	
	public void getTaxis(Taxi[] taxis) {
		/** @REQUIRES: None
		* @MODIFIES: None
		* @EFFECTS: \result == taxis
		*/
		this.taxis = taxis;
	}
	public void getGuiInfo(guiInfo GuiInfo) {
		/** @REQUIRES: None
		* @MODIFIES: None
		* @EFFECTS: \result == GuiInfo
		*/
		this.GuiInfo = GuiInfo;
	}
}


