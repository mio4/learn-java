package Taxi;
//调度器类
public class Scheduler extends Thread{
	/*Overview : 将合法请求添加一个Window线程

	 */
	private RequestQueue requestQueue;
	private Taxi[] taxis;
	private guiInfo GuiInfo;
	private TaxiGUI taxiGUI;

	/** @REQUIRES: requestQueue!=null;taxiGUI!=null;
	 * @MODIFIES: this.requestQueue,this.taxiGUI;
	 * @EFFECTS: None;
	 */
	public Scheduler(RequestQueue requestQueue, TaxiGUI taxiGUI) {
		this.requestQueue = requestQueue;
		this.taxiGUI = taxiGUI;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @Effects : \result == invariant(this);
	 */
	public boolean repOK(){

		return true;
	}

	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \request.equals(不断执行DealReq()方法，处理请求);
	 */
	@Override
	public void run() {
		try {
			while(true) {
				DealReq();		
			}
		} catch (Exception e) {}
	}

	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \request.equals(对于没有被分配的请求，开启窗口线程，然后分配到合适的出租车);
	 */
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

	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == taxis;
	 */
	public void getTaxis(Taxi[] taxis) {
		this.taxis = taxis;
	}
	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == GuiInfo;
	 */
	public void getGuiInfo(guiInfo GuiInfo) {

		this.GuiInfo = GuiInfo;
	}
}


