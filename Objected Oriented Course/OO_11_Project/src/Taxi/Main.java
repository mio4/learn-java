package Taxi;

public class Main {
	/*Overview : 开启流量监控线程，开始红绿灯线程，获取用户输入，开启调度线程
	 */

	/**@Requires : None;
	 * @Modifies : None;
	 * @Effects : \request.equals(读入请求，读入地图，出租车线程运行，调度器线程运行)
	 */
	public static void main(String[] args) {
		try {
			ReadFile readFile = new ReadFile();
			TaxiGUI taxiGUI = new TaxiGUI();
			//初始化最短路径地图
			guiInfo GuiInfo = new guiInfo();
			//流量监控
			FlowMonitor flowMonitor = new FlowMonitor();
			flowMonitor.setName("flowMonitor");
			flowMonitor.start();
			//红绿灯初始化
			Light light = new Light(taxiGUI);
			light.setName("light");
			System.out.println("/*红绿灯改变时间间隔：" + light.getlightChangeTime() + "*/");
			//获取输入
			RequestQueue requestQueue = new RequestQueue();
			RequestDeal requestDeal = new RequestDeal(requestQueue,readFile,taxiGUI,flowMonitor,GuiInfo);
			requestDeal.setName("requestDeal");
			requestDeal.start();
			Taxi2[] taxis = requestDeal.getTaxi();
			//红绿灯变化
			light.start();
			//调度开始
			Scheduler scheduler = new Scheduler(requestQueue,taxiGUI);
			scheduler.setName("scheduler");
			scheduler.getTaxis(taxis);
			scheduler.getGuiInfo(GuiInfo);
			scheduler.start();
			//第九次作业测试接口
			Select s = new Select(taxis);
			//s.SelectTaxi(0);
		} catch (Exception e) {}
	}
}
