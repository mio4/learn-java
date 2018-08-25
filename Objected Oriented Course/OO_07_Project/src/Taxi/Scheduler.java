package Taxi;
//调度器类
public class Scheduler extends Thread{
	private RequestQueue requestQueue;
	private Taxi[] taxis;
	private guiInfo GuiInfo;
	private TaxiGUI taxiGUI;
	
	public Scheduler(RequestQueue requestQueue, TaxiGUI taxiGUI) {
		this.requestQueue = requestQueue;
		this.taxiGUI = taxiGUI;
	}
	
	@Override
	public void run() {	
		try {
			while(true) {
				DealReq();		
			}
		} catch (Exception e) {}
	}

	public void DealReq() {
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
		this.taxis = taxis;
	}
	public void getGuiInfo(guiInfo GuiInfo) {
		this.GuiInfo = GuiInfo;
	}
}


