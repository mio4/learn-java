package Taxi;

import java.awt.Point;
import java.util.Random;

public class Taxi extends Thread{
	/*Overview : 出租车线程，等待状态下随机移动以及停止服务，收到请求后接送客

	 */
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
	private int relaxCnt; //20s休息一次
	private int car_dire; //车辆朝向
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

	/** @REQUIRES : map!=null,taxiGUI!=null,GuiInfo!=null,flowMonitor!=null;
	 * @MODIFIES : this.id,this.credit,this.map,this.taxiGUI,this.GuiInfo,this.flowMonitor;
	 * @EFFECTS : None;
	 */
	public Taxi(int id, int[][] map,TaxiGUI taxiGUI,guiInfo GuiInfo,FlowMonitor flowMonitor) {
		this.id = id;
		this.credit = 0;
		this.map = map;
		this.taxiGUI = taxiGUI;
		this.GuiInfo = GuiInfo;
		this.flowMonitor = flowMonitor;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @Effects : \result == invariant(this);
	 */
	public boolean repOK(){
		if(srcx < 0 || srcy < 0 ||dstx < 0 || dsty < 0)
			return false;
		if(taxiGUI == null || GuiInfo == null)
			return false;
		return true;
	}

	/** @Requires : None;
	 * @Modifies : this.credit,this.status,this.posx,this.posy;
	 * @Effects : \request.equals(让出租车选择流量最小的道路运行以及对于不同的请求，改变出租车的坐标和状态);
	 */
	@Override
	public void run() {

		try {
			while(true) {
				isRelax(); //检查是否休息
				switch(state) {
				case 0: { //停止运行
					Relax(); //休息一次
					break;
				}
				case 1 : { //表示服务状态
					dealOrder(dstx,dsty,"Service");
					break;
				}
				case 2 : { //处于等待服务状态
					//RandomMove();
					newRandomMove();
					break;
				}
				case 3 : { //处于准备服务状态：在去接单的路上
					dealOrder(srcx,srcy,"Prepare");
					break;
				}
				default: break;
				}
			}
		} catch (Exception e) {}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \request.equals(set the taxi coordinate on the GUI);
	 */
	public void setGuiPoint() {
		Point p = new Point();
		p.x = posx;
		p.y = posy;
		this.taxiGUI.SetTaxiStatus(id, p, state);
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this.state,this.relaxCnt;
	 * @EFFECTS : (relaxCnt==100) ==> (state==0 && relaxCnt==0);
	 */
	public void isRelax() {

		if(this.relaxCnt==40) {
			this.state = 0;
			this.relaxCnt = 0; //复位
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this.state;
	 * @EFFECTS : \request.equals(sleep 1000ms and change the taxi state);
	 */
	public void Relax() {
		try {
			setGuiPoint();
			Thread.sleep(1000);
			this.state = 2;
		} catch (Exception e) {}
	}
	public void dealOrder(int x,int y,String type){
		if(type.equals("Prepare")){
			this.credit += 3; //信用增加
			outputHandler = new OutputHandler(str); //初始化文本文件
			outputHandler.IniSendTaxi(str);
		} else if (type.equals("Service")){
			outputHandler.IniSendPerson(str);
		}
		int distance = GuiInfo.distance(posx,posy,x,y);
		int direction = 0;
		while(posx!=x || posy!=y){
			boolean IsPath[] = {false,false,false,false};
			IsPath = judgePath(IsPath);
			if(IsPath[UP] && GuiInfo.distance(posx-1,posy,x,y)==distance-1) direction = UP;
			else if (IsPath[DOWN] && GuiInfo.distance(posx+1, posy,x, y)==distance-1) direction = DOWN;
			else if (IsPath[LEFT] && GuiInfo.distance(posx, posy-1, x, y)==distance-1) direction = LEFT;
			else if (IsPath[RIGHT] && GuiInfo.distance(posx, posy+1, x, y)==distance-1) direction = RIGHT;
			else ;
			oneMove(direction,1);
			distance-=1;
			if(type.equals("Prepare")){
				outputHandler.SendTaxi(str,posx,posy,time);
			} else if (type.equals("Service")) {
				outputHandler.SendPerson(str, posx, posy);
			}
		}
		//到达乘客处，休息一秒
		try {
			Thread.sleep(1000);
		} catch (Exception e) {}
		//转换状态
		if(type.equals("Prepare")){
			this.state = 1;
		} else if (type.equals("Service")){
			outputHandler.Finish(str);
			this.state = 2;
		}
	}

	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \request.equals(call the function judgePath(),getFlow(),getMinFlowDirection(),oneMove());
	 */
	public void newRandomMove(){

		boolean path_connect[] = {false,false,false,false};
		int flow[] = {0,0,0,0};
		int move_direction;
		path_connect = judgePath(path_connect); //道路是否连通
		flow = getFlow(flow); //获取四个方向的流量
		move_direction = getMinFlowDirection(flow,path_connect); //找到合适的方向
		oneMove(move_direction,0); //走一步
	}

	/** @REQUIRES: flow!=null;
	 * @MODIFIES: None;
	 * @EFFECTS: \request.equals(get the flow of the road round the taxi)
	\result == flow;
	 */
	public int[] getFlow(int[] flow){ //获取出租车周围四条边的流量

		flow[UP] = this.flowMonitor.getFlow(posx,posy,UP);
		flow[DOWN] = this.flowMonitor.getFlow(posx,posy,DOWN);
		flow[LEFT] = this.flowMonitor.getFlow(posx,posy,LEFT);
		flow[RIGHT] = this.flowMonitor.getFlow(posx,posy,RIGHT);
		return flow;
	}

	/** @REQUIRES: flow!=null,path_connect!=null;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == move_direction;
	 */
	public int getMinFlowDirection(int[] flow,boolean[] path_connect){ //选择能够走的最小流量的边
		int min = Integer.MAX_VALUE;
		Random random = new Random();
		int[] random_array = new int[4];
		int random_array_cnt = 0;
		int move_direction = 0;
		for(int i=0; i < 4;i++)
			if(flow[i] <= min && path_connect[i]){
				//min = flow[i];
				//move_direction = i;
				random_array[random_array_cnt++] = i;
			}
		move_direction = random_array[random.nextInt(random_array_cnt)];
		return move_direction;
	}

	/** @REQUIRES: None;
	 * @MODIFIES: this.posx,this.posy,this.relaxCnt;
	 * @EFFECTS: \request.equals(move the taxi one step);
	 */
	public void oneMove(int direction,int type){ //输入出租车移动的方向；作用是出租车移动一步：更改坐标，增加流量

		int[][] light = taxiGUI.getLightStatus();
		int sleepTime = 5;
		long light_begin_time = System.currentTimeMillis();
		if(direction == UP) {
			while(car_dire == UP && light[posx][posy]==1){ //车头向北，方向向北，遇到南北红绿灯
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {}
			}
			while(car_dire == RIGHT && light[posx][posy]==2){
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {}
			}
			car_dire = UP;
			this.posx-=1;
			this.flowMonitor.addFlow(posx,posy,UP);
		} else if (direction == DOWN){
			while(car_dire == DOWN && light[posx][posy]==1) {
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {}
			}
			while(car_dire == LEFT && light[posx][posy]==2){
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {}
			}
			car_dire = DOWN;
			this.posx+=1;
			this.flowMonitor.addFlow(posx,posy,DOWN);
		} else if (direction == LEFT){
			while(car_dire == LEFT && light[posx][posy]==2){
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {}
			}
			while(car_dire == UP && light[posx][posy]==1){
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {}
			}
			car_dire = LEFT;
			this.posy-=1;
			this.flowMonitor.addFlow(posx,posy,LEFT);
		} else if (direction == RIGHT){
			while(car_dire == RIGHT && light[posx][posy]==2){
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {}
			}
			while(car_dire == DOWN && light[posx][posy]==1){
				try {
					Thread.sleep(sleepTime);
				} catch (Exception e) {}
			}
			car_dire = RIGHT;
			this.posy+=1;
			this.flowMonitor.addFlow(posx,posy,RIGHT);
		}
		long end_light_time = System.currentTimeMillis();
		if(type == 1){
			this.outputHandler.addLightTime(end_light_time-light_begin_time);
		}
		setGuiPoint();
		this.relaxCnt+=1;
		try {
			Thread.sleep(500);
		} catch (Exception e){}
	}

	/** @REQUIRES : 0<=srcx<=79 && 0<=srcy<=79 && 0<=dstx<=79 && 0<=dsty<=79;
	 * @MODIFIES : this.str,this.srcx,this.srcy,this.dstx,this.dsty,this.time,this.state,this.relaxCnt;
	 * @EFFECTS : None;
	 */
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

	/** @REQUIRES: IsPath!=null;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == IsPath;
	 */
	public boolean[] judgePath(boolean[] IsPath) { //判断是否连通
		this.map = taxiGUI.getMap();
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

	/** @REQUIRES : None;
	 * @MODIFIES : this.credit;
	 * @EFFECTS : None;
	 */
	public void ImproveCredit() { //抢单，信用增加
		this.credit += 1;
	}

	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \request.equals(initial the taxi in random coordinate and output the information of it);
	 */
	public void initialCar() { //设置出租车初始位置
		this.state = 2;
		Random random = new Random();
		this.posx = random.nextInt(80);
		this.posy = random.nextInt(80);
		this.car_dire = UP;
		setGuiPoint();
		OutputHandler.InitialToConsole(this.id,posx,posy);
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == id;
	 */
	public int getTaxiId() { //获得出租车标号

		return this.id;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == p;
	 */
	public Point getPosition() { //获取出租车当前位置
		Point p = new Point();
		p.x = this.posx;
		p.y = this.posy;
		return p;
	}

	/**@REQUIRES: None;
	 @MODIFIES: None;
	 @EFFECTS: \result == credit;
	 */
	public int getCredit() { //获得出租车信用
		return this.credit;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == state;
	 */
	public int getTaxiState() { //获取出租车当前状态
		return this.state;
	}
	
	/*******************************************************************
			指导书中要求的接口
	*******************************************************************/
	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == p;
	 */
	public synchronized Point getPosx() { //返回当前Taxi坐标
		Point p = new Point();
		p.x = this.posx;
		p.y = this.posy;
		return p;
	}
	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == System.currentTimeMills();
	 */
	public synchronized long getTime() { //查询当前时刻

		return System.currentTimeMillis(); 
	}

	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: \result == state;
	 */
	public synchronized int getTState() { //查询出租车状态
		return this.state;
	}

	/**********************************************************************
	 Load File使用的方法
	 **********************************************************************/

	/** @REQUIRES: None;
	 * @MODIFIES: this.credit;
	 * @EFFECTS: None;
	 */
	public void setCredit(int credit){
		this.credit = credit;
	}

	/** @REQUIRES: None;
	 * @MODIFIES: this.state;
	 * @EFFECTS: None;
	 */
	public void setState(int state){
		this.state = state;
	}

	/** @REQUIRES: None;
	 * @MODIFIES: this.posx,this.posy;
	 * @EFFECTS: None;
	 */
	public void setCoor(int x, int y){
		this.posx = x;
		this.posy = y;
	}
}
