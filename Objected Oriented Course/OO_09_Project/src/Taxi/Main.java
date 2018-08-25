package Taxi;

public class Main {
	public static void main(String[] args) {
		/**@Requires: None;
		* @Modifies: None;
		* @Effects:  读入请求，读入地图，出租车线程运行，调度器线程运行
		*			如果出现异常 ==> exceptional_behavior(e)
		*/
		try {
			ReadFile readFile = new ReadFile();
			TaxiGUI taxiGUI = new TaxiGUI();
			//初始化最短路径地图
			guiInfo GuiInfo = new guiInfo();
			//流量监控
			FlowMonitor flowMonitor = new FlowMonitor();
			flowMonitor.setName("flowMonitor");
			flowMonitor.start();
			//获取输入
			RequestQueue requestQueue = new RequestQueue();
			RequestDeal requestDeal = new RequestDeal(requestQueue,readFile,taxiGUI,flowMonitor,GuiInfo);
			requestDeal.setName("requestDeal");
			requestDeal.start();
			Taxi[] taxis = requestDeal.getTaxi();
			//调度开始
			Scheduler scheduler = new Scheduler(requestQueue,taxiGUI);
			scheduler.setName("scheduler");
			scheduler.getTaxis(taxis);
			scheduler.getGuiInfo(GuiInfo);
			scheduler.start();
			//测试接口
			Select s = new Select(taxis);
			//s.SelectTaxi(0);
		} catch (Exception e) {}
	}
}
