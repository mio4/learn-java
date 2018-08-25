package Taxi;

import java.awt.Point;
import java.util.Random;

public class Taxi extends Thread{
	//随机数方向定义
	private static final int UP = 0;
	private static final int DOWN = 1;
	private static final int LEFT = 2;
	private static final int RIGHT = 3;
	private int[][] map;
	private TaxiGUI taxiGUI;
	private guiInfo GuiInfo;
	//出租车信息
	private int id; //出租车编号
	private int credit; //出租车信用
	private int posx;
	private int posy;
	private int state; //出租车状态  0表示停止运行，1表示服务，2表示等待服务，3表示准备服务
	private int relaxCnt; //100次休息一次
	//请求信息
	private String str;
	private int srcx;
	private int srcy;
	private int dstx;
	private int dsty;
	private long time;
	//一条请求分配一个文件
	private OutputHandler outputHandler;	
	public Taxi(int id, int[][] map,TaxiGUI taxiGUI,guiInfo GuiInfo) {
		this.id = id;
		this.credit = 0;
		this.map = map;
		this.taxiGUI = taxiGUI;
		this.GuiInfo = GuiInfo;
	}
	
	@Override
	public void run() {
		try {
			initialCar(); //初始化出租车
			while(true) {
				isRelax(); //检查是否休息
				switch(state) {
				case 0: { //停止运行
					Relax(); //休息一次
					break;
				}
				case 1 : { //表示服务状态
					OnService();
					break;
				}
				case 2 : { //处于等待服务状态
					RandomMove();
					break;
				}
				case 3 : { //处于准备服务状态：在去接单的路上 
					OnSendRoad();
					break;
				}
				default: break;
				}
			}
		} catch (Exception e) {}
	}
	
	public void setGuiPoint() {
		Point p = new Point();
		p.x = posx;
		p.y = posy;
		this.taxiGUI.SetTaxiStatus(id, p, state);
	}
	
	public void isRelax() { 
		if(this.relaxCnt==100) {
			this.state = 0;
			this.relaxCnt = 0; //复位
		}
	}
	
	public void Relax() {
		try {
			setGuiPoint();
			Thread.sleep(1000);
			this.state = 2;
		} catch (Exception e) {}
	}
	
	public void OnService() {
		outputHandler.IniSendPerson(str);
		while(posx!=dstx || posy!=dsty) { //没有到达乘客处
			int distance = GuiInfo.distance(posx, posy, dstx, dsty);
			boolean IsPath[] = {false, false, false, false};
			IsPath = judgePath(IsPath);
 			if(IsPath[UP]==true && GuiInfo.distance(posx-1,posy,dstx,dsty)==distance-1) posx-=1;
 			else if (IsPath[DOWN]==true && GuiInfo.distance(posx+1, posy,dstx, dsty)==distance-1) posx+=1;
 			else if (IsPath[LEFT]==true && GuiInfo.distance(posx, posy-1, dstx, dsty)==distance-1) posy-=1;
 			else if (IsPath[RIGHT]==true && GuiInfo.distance(posx, posy+1, dstx, dsty)==distance-1) posy+=1;
 			setGuiPoint();
 			outputHandler.SendPerson(str, posx, posy);
 			
 			try {
 				Thread.sleep(200); 
 			} catch (Exception e) {}
		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {}
		outputHandler.Finish(str);
		this.state = 2;
	}
	
	public void RandomMove() { //出租车等待服务阶段随机行动
		//地图连通性
		boolean IsPath[] = {false, false, false, false};
		IsPath = judgePath(IsPath);
		Random random = new Random();
		int path = random.nextInt(3);
		while(IsPath[path] == false) path = random.nextInt(4);
		if(path == UP) posx-=1;
		else if (path == DOWN) posx+=1;
		else if (path == LEFT) posy-=1;
		else if (path == RIGHT) posy+=1;
		setGuiPoint();
		this.relaxCnt += 1;
		try {
			Thread.sleep(200);
		} catch (Exception e) {}
	}
	
	public void changeToSend(String str,int srcx, int srcy, int dstx, int dsty, long time) {
		this.str = str;
		this.srcx = srcx;
		this.srcy = srcy;
		this.dstx = dstx;
		this.dsty = dsty;
		this.time = time;
		this.state = 3;
		this.relaxCnt = 0; //重置
	}
	
	public boolean[] judgePath(boolean[] IsPath) { //判断是否连通
		//右方和下方是否连通
		if(map[posx][posy]==0) IsPath[RIGHT] = IsPath[DOWN] = false;
		else if (map[posx][posy]==1) {IsPath[RIGHT] = true; IsPath[DOWN] = false;}
		else if (map[posx][posy]==2) {IsPath[RIGHT] = false; IsPath[DOWN] = true;}
		else if (map[posx][posy]==3) IsPath[RIGHT] = IsPath[DOWN] = true; 
		//左方和上方是否连通
		if(posx==0 && posy!=0) { //处于上方非边界
			if(map[posx][posy-1]==1 || map[posx][posy-1]==3) IsPath[LEFT] = true;
		} else if (posy==0 && posx!=0) { //处于左方非边界
			if(map[posx-1][posy]==2 || map[posx-1][posy]==3) IsPath[UP] = true;
		} else if (posx!=0 && posy!=0){ //处于非左上边界地区
			if(map[posx][posy-1]==1 || map[posx][posy-1]==3) IsPath[LEFT] = true;
			if(map[posx-1][posy]==2 || map[posx-1][posy]==3) IsPath[UP] = true;
		}
		return IsPath;
	}
	
	public void OnSendRoad() { //接单路上
		this.credit += 3; //信用增加
		outputHandler = new OutputHandler(str); //初始化文本文件
		outputHandler.IniSendTaxi(str);
		while(posx!=srcx || posy!=srcy) { //没有到达乘客处
			int distance = GuiInfo.distance(posx, posy, srcx, srcy);
			boolean IsPath[] = {false, false, false, false};
			IsPath = judgePath(IsPath);
 			if(IsPath[UP]==true && GuiInfo.distance(posx-1,posy,srcx,srcy)==distance-1) posx-=1;
 			else if (IsPath[DOWN]==true && GuiInfo.distance(posx+1, posy,srcx, srcy)==distance-1) posx+=1;
 			else if (IsPath[LEFT]==true && GuiInfo.distance(posx, posy-1, srcx, srcy)==distance-1) posy-=1;
 			else if (IsPath[RIGHT]==true && GuiInfo.distance(posx, posy+1, srcx, srcy)==distance-1) posy+=1;
 			setGuiPoint();
 			outputHandler.SendTaxi(str,posx,posy,time);
 			try {
 				Thread.sleep(200); //200！！！！！！！！！！
 			} catch (Exception e) {}
		}
		//到达乘客处，休息一秒
		try {
			Thread.sleep(1000);
		} catch (Exception e) {}
		//转换状态
		this.state = 1; 
	}

	public void ImproveCredit() { //抢单，信用增加
		this.credit += 1;
	}
		
	public void initialCar() { //设置出租车初始位置
		this.state = 2;
		Random random = new Random();
		this.posx = random.nextInt(80);
		this.posy = random.nextInt(80);
		setGuiPoint();
		OutputHandler.InitialToConsole(this.id,posx,posy);
	}
	
	public int getTaxiId() { //获得出租车标号
		return this.id;
	}
	public Point getPosition() { //获取出租车当前位置
		Point p = new Point();
		p.x = this.posx;
		p.y = this.posy;
		return p;
	}
	public int getCredit() { //获得出租车信用
		return this.credit;
	}
	public int getTaxiState() { //获取出租车当前状态
		return this.state; 
	}
	
	/*******************************************************************
			指导书中要求的接口
	*******************************************************************/
	public synchronized Point getPosx() { //返回当前Taxi坐标
		Point p = new Point();
		p.x = this.posx;
		p.y = this.posy;
		return p;
	}
	public synchronized long getTime() { //查询当前时刻
		return System.currentTimeMillis(); 
	}
	public synchronized int getTState() { //查询出租车状态
		return this.state;
	}
	
}
