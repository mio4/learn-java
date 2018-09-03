package Taxi;

import java.awt.Point;

public class Main {
	public static void main(String[] args) {
		try {
			//初始化地图
			ReadFile readFile = new ReadFile();
			readFile.ReadMap("map.txt",80);
			//用户图形界面
			TaxiGUI taxiGUI = new TaxiGUI();
			taxiGUI.LoadMap(readFile.getMap(), 80);
			//初始化最短路径地图
			guiInfo GuiInfo = new guiInfo();
			GuiInfo.map = readFile.getMap();
			GuiInfo.initmatrix();
			//获取乘客输入
			RequestQueue requestQueue = new RequestQueue();
			RequestDeal requestDeal = new RequestDeal(requestQueue);
			requestDeal.start();
			//出租车
			Taxi[] taxis = new Taxi[100];
			for(int i=0; i < 100;i++) { 
				taxis[i] = new Taxi(i,readFile.getMap(),taxiGUI,GuiInfo);
				taxis[i].start();
			}		
			//调度开始
			Scheduler scheduler = new Scheduler(requestQueue,taxiGUI);
			scheduler.getTaxis(taxis);
			scheduler.getGuiInfo(GuiInfo);
			scheduler.start();
			//测试接口
			Select s = new Select(taxis);
			//s.SelectTaxi(0);
		} catch (Exception e) {}	
	}
}
