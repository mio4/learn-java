package Taxi;

import java.awt.Point;
import java.util.Random;

public class Taxi extends Thread{
	//随机数方向定义
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	private int[][] map;
	private TaxiGUI taxiGUI;
	private guiInfo GuiInfo;
	//出租车信息
	private int id; //出租车编号
	private int credit; //出租车信用
	private int posx; //出租车坐标
	private int posy; //出租车坐标
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
	//流量监控器
	private FlowMonitor flowMonitor;
	public Taxi(int id, int[][] map,TaxiGUI taxiGUI,guiInfo GuiInfo,FlowMonitor flowMonitor) {
		/** @REQUIRES: map!=null,taxiGUI!=null,GuiInfo!=null,flowMonitor!=null
		 * @MODIFIES: id,credit,map,taxiGUI,GuiInfo,flowMonitor
		 * @EFFECTS: None
		 */
		this.id = id;
		this.credit = 0;
		this.map = map;
		this.taxiGUI = taxiGUI;
		this.GuiInfo = GuiInfo;
		this.flowMonitor = flowMonitor;
	}
	
	@Override
	public void run() {
		/** @Requires: None
		* @Modifies:credit,status,posx,posy
		* @Effects: 让出租车选择流量最小的道路运行以及对于不同的请求，改变出租车的坐标和状态
		*/
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
					//RandomMove();
					newRandomMove();
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
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: set the taxi coordinate on the GUI
		 */
		Point p = new Point();
		p.x = posx;
		p.y = posy;
		this.taxiGUI.SetTaxiStatus(id, p, state);
	}
	
	public void isRelax() {
		/** @REQUIRES: None
		 * @MODIFIES: state,relaxCnt
		 * @EFFECTS: (relaxCnt==100) ==> (state==0 && relaxCnt==0)
		 */
		if(this.relaxCnt==100) {
			this.state = 0;
			this.relaxCnt = 0; //复位
		}
	}
	
	public void Relax() {
		/** @REQUIRES: None
		 * @MODIFIES: state
		 * @EFFECTS: sleep 1000ms and change the taxi state
		 */
		try {
			setGuiPoint();
			Thread.sleep(1000);
			this.state = 2;
		} catch (Exception e) {}
	}
	
	public void OnService() {
		/** @REQUIRES: None
		 * @MODIFIES: posx,posy
		 * @EFFECTS: send the taxi to take the passenger and change the taxi coordinate
		 */
		outputHandler.IniSendPerson(str);
		while(posx!=dstx || posy!=dsty) { //没有到达乘客处
			int distance = GuiInfo.distance(posx, posy, dstx, dsty);
			boolean IsPath[] = {false, false, false, false};
			IsPath = judgePath(IsPath);
 			if(IsPath[UP] && GuiInfo.distance(posx-1,posy,dstx,dsty)==distance-1) posx-=1;
 			else if (IsPath[DOWN] && GuiInfo.distance(posx+1, posy,dstx, dsty)==distance-1) posx+=1;
 			else if (IsPath[LEFT] && GuiInfo.distance(posx, posy-1, dstx, dsty)==distance-1) posy-=1;
 			else if (IsPath[RIGHT] && GuiInfo.distance(posx, posy+1, dstx, dsty)==distance-1) posy+=1;
 			setGuiPoint();
 			outputHandler.SendPerson(str, posx, posy);
 			try {
 				Thread.sleep(500);
 			} catch (Exception e) {}
		}
		try {
			Thread.sleep(1000);
		} catch (Exception e) {}
		outputHandler.Finish(str);
		this.state = 2;
	}

	public void newRandomMove(){
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: call the function judgePath(),getFlow(),getMinFlowDirection(),oneMove()
		 */
		boolean path_connect[] = {false,false,false,false};
		int flow[] = {0,0,0,0};
		int move_direction;
		path_connect = judgePath(path_connect); //道路是否连通
		flow = getFlow(flow); //获取四个方向的流量
		move_direction = getMinFlowDirection(flow,path_connect); //找到合适的方向
		oneMove(move_direction); //走一步
	}

	public int[] getFlow(int[] flow){ //获取出租车周围四条边的流量
		/** @REQUIRES: flow!=null;
		 * @MODIFIES: None;
		 * @EFFECTS: get the flow of the road round the taxi
		 			\result == flow;
		 */
		flow[UP] = this.flowMonitor.getFlow(posx,posy,UP);
		flow[DOWN] = this.flowMonitor.getFlow(posx,posy,DOWN);
		flow[LEFT] = this.flowMonitor.getFlow(posx,posy,LEFT);
		flow[RIGHT] = this.flowMonitor.getFlow(posx,posy,RIGHT);
		return flow;
	}

	public int getMinFlowDirection(int[] flow,boolean[] path_connect){ //选择能够走的最小流量的边
		/** @REQUIRES: flow,path_connect
		 * @MODIFIES: None
		 * @EFFECTS: \result == move_direction
		 */
		int min = Integer.MAX_VALUE;
		int move_direction = 0;
		for(int i=0; i < 4;i++)
			if(flow[i] < min && path_connect[i]){
				min = flow[i];
				move_direction = i;
			}
		return move_direction;
	}

	public void oneMove(int direction){ //输入出租车移动的方向；作用是出租车移动一步：更改坐标，增加流量
		/** @REQUIRES: None
		 * @MODIFIES: posx,posy,relaxCnt
		 * @EFFECTS: move the taxi one step
		 */
		if(direction == UP) {
			this.posx-=1;
			this.flowMonitor.addFlow(posx,posy,UP);
		} else if (direction == DOWN){
			this.posx+=1;
			this.flowMonitor.addFlow(posx,posy,DOWN);
		} else if (direction == LEFT){
			this.posy-=1;
			this.flowMonitor.addFlow(posx,posy,LEFT);
		} else if (direction == RIGHT){
			this.posy+=1;
			this.flowMonitor.addFlow(posx,posy,RIGHT);
		}
		setGuiPoint();
		this.relaxCnt+=1;
		try {
			Thread.sleep(500);
		} catch (Exception e){}
	}


	public void changeToSend(String str,int srcx, int srcy, int dstx, int dsty, long time) {
		/** @REQUIRES : 0<=srcx<=79 && 0<=srcy<=79 && 0<=dstx<=79 && 0<=dsty<=79
		* @MODIFIES: str,srcx,srcy,dstx,dsty,time,state,relaxCnt
		* @EFFECTS: None
		*/
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
		/** @REQUIRES: IsPath!=null
		 * @MODIFIES: None
		 * @EFFECTS: \result == IsPath
		 */
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
		/** @REQUIRES: None
		 * @MODIFIES: credit,posx,posy
		 * @EFFECTS: send the taxi to take the passenger
		 */
		this.credit += 3; //信用增加
		outputHandler = new OutputHandler(str); //初始化文本文件
		outputHandler.IniSendTaxi(str);
		while(posx!=srcx || posy!=srcy) { //没有到达乘客处
			int distance = GuiInfo.distance(posx, posy, srcx, srcy);
			boolean IsPath[] = {false, false, false, false};
			IsPath = judgePath(IsPath);
 			if(IsPath[UP] && GuiInfo.distance(posx-1,posy,srcx,srcy)==distance-1) posx-=1;
 			else if (IsPath[DOWN] && GuiInfo.distance(posx+1, posy,srcx, srcy)==distance-1) posx+=1;
 			else if (IsPath[LEFT] && GuiInfo.distance(posx, posy-1, srcx, srcy)==distance-1) posy-=1;
 			else if (IsPath[RIGHT] && GuiInfo.distance(posx, posy+1, srcx, srcy)==distance-1) posy+=1;
 			setGuiPoint();
 			outputHandler.SendTaxi(str,posx,posy,time);
 			try {
 				Thread.sleep(500);
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
		/** @REQUIRES: None
		 * @MODIFIES: credit
		 * @EFFECTS: None
		 */
		this.credit += 1;
	}
		
	public void initialCar() { //设置出租车初始位置
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: initial the taxi in random coordinate and output the information of it
		 */
		this.state = 2;
		Random random = new Random();
		this.posx = random.nextInt(80);
		this.posy = random.nextInt(80);
		setGuiPoint();
		OutputHandler.InitialToConsole(this.id,posx,posy);
	}
	
	public int getTaxiId() { //获得出租车标号
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: \result == id
		 */
		return this.id;
	}
	public Point getPosition() { //获取出租车当前位置
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: \result == p
		 */
		Point p = new Point();
		p.x = this.posx;
		p.y = this.posy;
		return p;
	}
	public int getCredit() { //获得出租车信用
		/**@REQUIRES: None
		 @MODIFIES: None
		 @EFFECTS: \result == credit
		 */
		return this.credit;
	}
	public int getTaxiState() { //获取出租车当前状态
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: \result == state
		 */
		return this.state;
	}
	
	/*******************************************************************
			指导书中要求的接口
	*******************************************************************/
	public synchronized Point getPosx() { //返回当前Taxi坐标
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: \result == p
		 */
		Point p = new Point();
		p.x = this.posx;
		p.y = this.posy;
		return p;
	}
	public synchronized long getTime() { //查询当前时刻
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: \result == System.currentTimeMills()
		 */
		return System.currentTimeMillis(); 
	}
	public synchronized int getTState() { //查询出租车状态
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: \result == state
		 */
		return this.state;
	}


	/**********************************************************************
	 Load File使用的方法
	 **********************************************************************/
	public void setCredit(int credit){
		/** @REQUIRES: None
		 * @MODIFIES: credit
		 * @EFFECTS: None
		 */
		this.credit = credit;
	}
	public void setState(int state){
		/** @REQUIRES: None
		 * @MODIFIES: state
		 * @EFFECTS: None
		 */
		this.state = state;
	}
	public void setCoor(int x, int y){
		/** @REQUIRES: None
		 * @MODIFIES: posx,posy
		 * @EFFECTS: None
		 */
		this.posx = x;
		this.posy = y;
	}
	/**********************************************************************
	第一次出租车作业中使用的方法
	**********************************************************************/

	public void RandomMove() { //出租车等待服务阶段随机行动
		/** @REQUIRES: None
		 * @MODIFIES: posx,posy
		 * @EFFECTS: change the coordinate of the taxi
		 */
		//地图连通性
		boolean IsPath[] = {false, false, false, false};
		IsPath = judgePath(IsPath);
		Random random = new Random();
		int path = random.nextInt(3);
		while(!IsPath[path]) path = random.nextInt(4);
		oneMove(path); //移动一步
	}
}
