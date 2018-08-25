package Taxi;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.regex.Pattern;

//在RequestDeal将处理后的请求发送到请求队列之前，需要对请求是否是同质请求进行判断
public class RequestDeal extends Thread{
	private Scanner in;
	private ArrayList<String> reqSame = new ArrayList<String>();
	private ArrayList<Long> timeSame = new ArrayList<Long>(); //save time/100
	private RequestQueue requestQueue;
	//作业九新增
	private ReadFile readFile;
	private TaxiGUI taxiGUI;
	private Taxi[] taxis = new Taxi[100];
	private FlowMonitor flowMonitor;
	private guiInfo GuiInfo;
	private boolean[] taxi_initialed = new boolean[100];
	
	public RequestDeal(RequestQueue requestQueue,ReadFile readFile, TaxiGUI taxiGUI,FlowMonitor flowMonitor,guiInfo GuiInfo) {
		/**@REQUIRES: readFile!=null;taxiGUI!=null;flowMonitor!=null;GuiInfo!=null;
		* @MODIFIES: requestQueue,readFile,taxiGUI,flowMonitor,GuiInfo
		* @EFFECTS: None
		*/
		in = new Scanner(System.in);
		this.requestQueue = requestQueue;
		this.readFile = readFile;
		this.taxiGUI = taxiGUI;
		this.flowMonitor = flowMonitor;
		this.GuiInfo = GuiInfo;
		Arrays.fill(this.taxi_initialed,false);
	}
	
	@Override
	public void run() {
		/**	@Requires: None
		 * @Modifies: readFile,taxiGUI
		 * @Effects: \request.equals(读入请求，对于OPEN/CLOSE请求开关道路，对于合法CR请求添加到请求队列中)
		 */
		try {
			//判断是否需要初始化时load file
			String str;
			System.out.println("Do you want to load file(y/n):");
			str = in.nextLine();
			if(str.equals("y")) {
				str = in.nextLine();
				loadFile(str);
			}
			else if(str.equals("n")) {
				this.readFile.ReadMap("map.txt",80);
				this.taxiGUI.LoadMap(readFile.getMap(), 80);
				GuiInfo.map = readFile.getMap();
				GuiInfo.initmatrix();
				for(int i=0; i < 100;i++) {
					taxis[i] = new Taxi(i,readFile.getMap(),taxiGUI,GuiInfo,flowMonitor);
					taxis[i].setName("Taxi "+i);
					taxis[i].start();
				}
			}
			while(true) {
				str = in.nextLine();
				if(getReqType(str) == -1)
					System.out.println("Invalid Req");
				else if (getReqType(str) == 1) {
					changeRoadResolve(str);
				}
				else if (getReqType(str) == 2) {
					getPassReq(str); //获取乘客请求
				}
			}
		} catch (Exception e) {}
	}

	public int getReqType(String str){
		/** @REQUIRES: None
		 * @MODIFIES: None
		* @EFFECTS: (Request==CR ==> \result=1) &&(Request==CLOSE/OPEN ==> \result=2)
		*/
		if (str.charAt(1) == 'O') return 1; //Open Road
		else if (str.charAt(1) == 'C' && str.charAt(2) == 'L') return 1; //Close Road
		else if (str.charAt(1) == 'C' && str.charAt(2) == 'R') return 2; //CR
		else return -1; //Invalid
	}

	/*********************************文件读取处理**************************************************/
	public int loadFile(String str){
		/** @REQUIRES: None
		 * @MODIFIES: None
		 * @EFFECTS: 读取指定路径文件，加载地图，初始化出租车，初始化流量，给定初始请求
		 */
		String[] strs = str.split(" ");
		String file_path = strs[1];
		String tempString;
		String read_flag = "head";
		File file = new File(file_path);
		try {
		BufferedReader br = new BufferedReader(new FileReader(file));
			while((tempString=br.readLine()) != null){
				if(read_flag.equals("head")){
					if(!tempString.equals("#No 9 Test File#")) return -1;
					else read_flag = "uselessLine";
				}
				//readMap部分
				if(read_flag.equals("readMap")) {
					if(tempString.equals("")){ //路径为空，加载默认地图
						this.readFile.ReadMap("map.txt",80);
					} else {
						this.readFile.ReadMap(tempString, 80);
					}
					this.taxiGUI.LoadMap(readFile.getMap(),80);
					GuiInfo.map = readFile.getMap();
					GuiInfo.initmatrix();
					//开启出租车线程
					for(int i=0; i < 100;i++) {
						taxis[i] = new Taxi(i,readFile.getMap(),taxiGUI,GuiInfo,flowMonitor);
						taxis[i].setName("Taxi "+i);
						//不在这里开启出租车线程
						//taxis[i].start();
					}
					read_flag = "uselessLine";
				}
				//change flow部分
				else if (read_flag.equals("readFlow")) {
					if(tempString.equals("#end_flow")) { //检查是否读到了flow末尾
						read_flag = "uselessLine";
						continue;
					}
					String flow_pattern = "\\(\\d{1,2},\\d{1,2}\\)\\(\\d{1,2},\\d{1,2}\\)\\d{1,10}";
					if(!Pattern.matches(flow_pattern,tempString))
						continue;
					String[] temp_strs = tempString.split("[^\\d+]+");
					int srcx = Integer.parseInt(temp_strs[1]);
					int srcy = Integer.parseInt(temp_strs[2]);
					int dstx = Integer.parseInt(temp_strs[3]);
					int dsty = Integer.parseInt(temp_strs[4]);
					int temp_flow = Integer.parseInt(temp_strs[5]);
					if(!FlowMonitor.isNeighbor(srcx,srcy,dstx,dsty))
						continue;
					this.flowMonitor.setFlow(srcx,srcy,dstx,dsty,temp_flow);
				}
				//initial taxi部分
				else if (read_flag.equals("readTaxi")){
					if(tempString.equals("#end_taxi")){
						read_flag = "uselessLine";
						continue;
					}
					String[] temp_strs = tempString.split("[^\\d+]+");
					int taxi_num = Integer.parseInt(temp_strs[0]);
					int taxi_sta = Integer.parseInt(temp_strs[1]);
					int taxi_cre = Integer.parseInt(temp_strs[2]);
					int taxi_x = Integer.parseInt(temp_strs[3]);
					int taxi_y = Integer.parseInt(temp_strs[4]);
					taxis[taxi_num].start();
					taxis[taxi_num].setCoor(taxi_x,taxi_y);
					taxis[taxi_num].setState(taxi_sta);
					taxis[taxi_num].setCredit(taxi_cre);
					taxi_initialed[taxi_num] = true;
				}
				//request部分
				else if(read_flag.equals("readRequest")){
					if(tempString.equals("#end_request")){
						read_flag = "uselessLine";
						continue;
					}
					getPassReq(tempString); //当做一般的请求进行处理
				}

				if(tempString.equals("#map")) read_flag = "readMap";
				else if(tempString.equals("#flow")) read_flag = "readFlow";
				else if(tempString.equals("#taxi")) read_flag = "readTaxi";
				else if(tempString.equals("#request")) read_flag = "readRequest";
				else if(tempString.equals("#end_flow") || tempString.equals("#end_taxi") || tempString.equals("#end_request"))
					read_flag = "uselessLine";

			}
			//所有情况执行完成之后，再初始化剩下的出租车
			for(int i=0; i < 100;i++){
				if(!taxi_initialed[i]){
					taxis[i].start();;
				}
			}
		} catch(Exception e) {}
		return -1;
	}

	public Taxi[] getTaxi(){ //将RequestDeal类里出租车传送到Main函数里
		/** @REQUIRES: taxis!=null
		 * @MODIFIES: None
		 * @EFFECTS: \result == taxis
		 */
		return this.taxis;
	}

	/*********************************道路改变处理**************************************************/
	public int changeRoadResolve(String str) { //解析改变路径请求字符串
		/** @REQUIRES: str!=null
		 * @MODIFIES: None
		 * @EFFECTS: 如果是OPEN指令则打开指定道路，如果是CLOSE指令则关闭指定道路
		 */
		String resolve = "[^\\d+]+";
		String openForm = "\\[OPEN,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]";
		String closeForm = "\\[CLOSE,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]";
		if(!Pattern.matches(openForm,str) && !Pattern.matches(closeForm,str)){
			System.out.println(str + "请求无效");
			return -1;
		} else { //提取信息
			String[] strs = str.split(resolve);
			int srcx = Integer.parseInt(strs[1]);
			int srcy = Integer.parseInt(strs[2]);
			int dstx = Integer.parseInt(strs[3]);
			int dsty = Integer.parseInt(strs[4]);
			if(FlowMonitor.isNeighbor(srcx,srcy,dstx,dsty)){
				Point p1 = new Point();
				p1.x = srcx;
				p1.y = srcy;
				Point p2 = new Point();
				p2.x = dstx;
				p2.y = dsty;
				if(str.charAt(1)=='O'){
					taxiGUI.SetRoadStatus(p1,p2,1);
				} else if (str.charAt(1)=='C'){
					taxiGUI.SetRoadStatus(p1,p2,0);
				}
			}
			return 1;
		}
	}
	/*********************************乘客请求处理*************************************************/
	public void getPassReq(String str){
		/** @REQUIRES: str!=null
		 * @MODIFIES: None
		 * @EFFECTS: 将合法乘客请求添加到RequestQueue中
		 */
		str = str.replaceAll(" ","");
		long time = System.currentTimeMillis(); //获取请求发生时间
		if(!CheckSame(str,time)) { //不是是同质请求
			Request req = new Request(str);
			req.passReqResolve();;
			if(req.getSrcx()!=-1) { //如果是有效请求
				requestQueue.addReq(str,req.getSrcx(), req.getSrcy(), req.getDstx(), req.getDsty(), time);
				addToSame(str,time/100);
			}
		}
	}

	public boolean CheckSame(String str, long time) { //检查是否是同质请求
		/** @REQUIRES: str!=null && time>0
		 * @MODIFIES: None
		 * @EFFECTS: 同质请求返回true，非同质请求返回false
		 */
		str = str.replaceAll(" ","");
		time = time / 100;
		int len = this.reqSame.size();
		for(int i=0; i < len;i++) {
			if(str.equals(reqSame.get(i)) && time==this.timeSame.get(i)) { //请求相同，并且发出的时间相同
				System.out.println("同质请求" + str);
				return true; //是同质请求
			}
		}
		return false; //不是同质请求
	}
	
	public void addToSame(String str, long time) { //将合理请求放置在List中，以便后续判断是否是同质请求
		/** @REQUIRES: str!=null
		 * @MODIFIES: reqSame,timeSame
		 * @EFFECTS: 同质请求添加到同质判断队列
		 */
		this.reqSame.add(str);
		this.timeSame.add(time);
	}
}