package Taxi;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;

public class Window extends Thread{ //为每一个请求开一个窗口线程
	private final int windowOpenTime = 7500; //窗口开放时间
	private Taxi[] taxis;
	private guiInfo GuiInfo;
	private TaxiGUI taxiGUI;
	private ArrayList<Integer> Ids = new ArrayList<Integer>(); //存储的是接单车辆的ID
	private ArrayList<Integer> Ids2 = new ArrayList<Integer>(); //存储相同最高信用的车辆
	private boolean[] tUsed = new boolean[100]; //车辆是否被使用
	private String str;
	private int srcx;
	private int srcy;
	private int dstx;
	private int dsty;
	private long time;
	private int chooseId = -1; //选择的出租车标号
	
	public Window(Taxi[] taxis, guiInfo GuiInfo, TaxiGUI taxiGUI) {
		Arrays.fill(tUsed, false);
		this.taxis = taxis;
		this.GuiInfo = GuiInfo;
		this.taxiGUI = taxiGUI;
	}
	public void getReq(String str,int srcx, int srcy,int dstx, int dsty, long time) { 
		this.str = str;
		this.srcx = srcx;
		this.srcy = srcy;		
		this.dstx = dstx;
		this.dsty = dsty;
		this.time = time;
	}
	
	@Override
	public void run() {
		try {
			long start_time = System.currentTimeMillis();
			setGrid();
			while(true) {
				Detect(); 
				if(System.currentTimeMillis() - start_time > windowOpenTime) {
					sendTaxi();
					break;
				}
			}
		} catch (Exception e) {}
	}
	
	public void setGrid() {
		//画个四格格子
		Point p1 = new Point();
		p1.x = this.srcx;
		p1.y = this.srcy;
		Point p2 = new Point();
		p2.x = this.dstx;
		p2.y = this.dsty;
		taxiGUI.RequestTaxi(p1, p2);
	}
	
	public void Detect() {//检查是否存在可以接单的出租车
		for(int i=0; i < 100; i++) {
			int tx = this.taxis[i].getPosition().x; //tx:出租车x位置
			int ty = this.taxis[i].getPosition().y; //ty:出租车y位置
			int tstate = this.taxis[i].getTaxiState(); //出租车状态
			if((tx>=srcx-2 && tx <= srcx+2) && (ty>=srcy-2 && ty <= srcy+2) && tUsed[i] == false && tstate!=0) { //在格子内
				this.Ids.add(i);
				tUsed[i] = true;
				taxis[i].ImproveCredit(); //抢单成功，信用+1
			}
		}
	}
	public void sendTaxi() { //派出出租车
		if(this.Ids.size() == 0) { //没有车相应
			System.out.println("没有车相应需求");
		} else {		
			//所有抢单的出租车车号
			OutputHandler.AllTaxi(time,str,this.Ids);
			int maxCredit = -1;
			//两遍扫描，找到最高信用
			for(int i=0; i < this.Ids.size(); i++) {
				if(maxCredit < this.taxis[Ids.get(i)].getCredit()) maxCredit = this.taxis[Ids.get(i)].getCredit();
			}
			for(int i=0; i < this.Ids.size(); i++) {
				if(maxCredit == this.taxis[Ids.get(i)].getCredit()) this.Ids2.add(Ids.get(i));
			}
			//两遍扫描，找到最合适车辆
			int distance = 10000;
			for(int i=0;i < this.Ids2.size(); i++) {
				Point p = taxis[Ids2.get(i)].getPosition();
				if(GuiInfo.distance(srcx,srcy,p.x, p.y) < distance) distance =  GuiInfo.distance(srcx,srcy,p.x, p.y);
			}
			for(int i=0;i < this.Ids2.size(); i++) {
				Point p = taxis[Ids2.get(i)].getPosition();
				if(GuiInfo.distance(srcx,srcy,p.x, p.y) >= distance-2 && GuiInfo.distance(srcx,srcy,p.x, p.y) <= distance+2) { //i就是接单的出租车
					this.chooseId = Ids2.get(i); //选择车辆标号
					OutputHandler.ChooseTaxi(str, chooseId);
					taxis[chooseId].changeToSend(str,srcx, srcy, dstx, dsty, time);
					break;
				}
			}
		}
	}
	public int getChoose() {
		return this.chooseId;
	}
}
