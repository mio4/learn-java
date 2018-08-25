/*
 扫描队列，在所有没有执行的指令中，选择到目前时间为止的指令,使用这些指令对电梯亮灯（如果已经亮灯，则是同质请求）：
 按层运行电梯，如果到了一层灯亮，输出结果，对电梯熄灯(每次0.5s之后，需要重新扫描队列，判断是否进行亮灯)
 选定一条作为主请求，在执行到主请求的时候有捎带可能（中间楼层有亮灯）
 主请求执行完毕之后（到达目标楼层），从队列中选择目前还没有执行的，时间最早的请求作为主请求
 直到队列为空，退出程序
 Core：将对于捎带和同质的判断转换为对于每一个楼层内外灯的判断，统一处理
 */
import java.util.ArrayList;
import java.util.Arrays;

public class Scheduler {
	/** overview:调度器类，
	 * 表示对象: double cur_time,ArrayList req_list, int cur_floor, boolean[] req_is_used, Elevator elevator, String cur_UpDown,Request cur_request, int goal_location
	 * 抽象函数：AF(c) = (_cur_time,_req_list,_cur_floor,_req_is_used,_elevator,_cur_UpDown,_cur_request,_goal_location) where
	 * _cur_time==cur_time,_req_list==req_list,_cur_floor==cur_floor,_req_is_used==req_is_used,_elevator==elevator,_cur_request==cur_request,_goal_location==goal_location
	 * 不变式：(req_list~=null && req_is_used!=null);
	 */
	//电梯属性
	private double cur_time = 0; //当前时间
	private ArrayList<Request> req_list;
	private int cur_floor = 1; //电梯当前楼层
	protected static boolean[] req_is_used; //请求是否被使用
	private Elevator elevator = new Elevator();
	private String cur_UpDown = "STILL";
	//主请求
	private Request cur_request;
	private int goal_location; //主请求目标楼层，在判断时和附请求分开

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result==(req_list!=null && req_is_used!=null)
	 */
	public boolean repOK(){
		if(req_list==null || req_is_used==null)
			return false;
		return true;
	}

	/** @REQUIRES : req_list!=null;
	 * @MODIFIES : this;
	 * @EFFECTS : \request.equals(initial the req_list and req_is_used)
	 */
	public Scheduler(ArrayList<Request> req_list){
		this.req_list = req_list;
		setReqIndex();
		this.req_is_used = new boolean[req_list.size()];
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this;
	 * @EFFECTS : \request.equals(schedule the elevator requests:get the main request and deal it, when the req_list is empty, end the dispatch)
	 */
	public void schedule(){
		while(true){
			getMainRequest();
			//如果主请求时间>cur_time，直接跳转
			if(cur_request.getReqTime() > cur_time)
				cur_time = cur_request.getReqTime();
			//执行主请求
			while(true) {
				scanList();
				move();
				lightJudge();
				if(cur_floor == goal_location) { //完成当前主请求
					break;
				}
			}
			//判断是否需要结束调度
			if(isEnd())
				break;
		}
	}

	/** @REQUIRES : req_list!=null;
	 * @MODIFIES : this;
	 * @EFFECTS : \request.equals(initial the req_list and req_is_used)
	 */
	public void scanList(){
		//扫描队列，找到截至到目前时间之前的未使用的请求,点亮对应楼层的灯
		//判断一个请求是否被使用的标志：是否已经被处理（同质|输出对应请求的结果）
		for(int i=0;i < this.req_list.size();i++){
			Request req = this.req_list.get(i);
			if(req.getReqTime() <= cur_time && !req_is_used[i]){
				this.elevator.lightOn(req,i);
				//this.req_is_used[i] = true;
			}
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this;
	 * @EFFECTS : (cur_UpDown=="UP") ==> (scan the floor between cur_floor and 10, if light ,mark it as the main request, end the function )
	 * 			  (cur_UpDown=="DOWN") ==> (scan the floor between cur_floor and 1, if light, mark it as the main request, end the function)
	 * 			  (else scan the req_list, find the earliest request and mark it the main request)
	 */
	public void getMainRequest(){
		//如果当前电梯运动方向向上，那么cur_floor和10之间是否有ER亮灯（有则作为主请求）
		//如果当前电梯运动方向向下，那么cur_floor和1之间是否有ER亮灯（有则作为主请求）
		//如果没有ER捎带情况，从所有没有处理的请求中找到最近的那一条
		Request req;
		int[] er_light = elevator.getEr_light();
		if(cur_UpDown == "UP"){
			//向上，是否有ER捎带
			for(int i=cur_floor;i < 11;i++){
				if(er_light[i] == 1){
					req = new Request(elevator.getEr_req()[i]);
					req.dealString();
					this.cur_request = req;
					//this.goal_location = this.req_list.get(i).getReq_location();
					this.goal_location = req.getReq_location();
					return;
				}
			}
		} else if (cur_UpDown == "DOWN"){
			//向下，是否有ER捎带
			for(int i=cur_floor; i > 0 ;i--){
				if(er_light[i] == 1){
					req = new Request(elevator.getEr_req()[i]);
					req.dealString();
					this.cur_request = req;
					//this.goal_location = this.req_list.get(i).getReq_location();
					this.goal_location = req.getReq_location();
					return;
				}
			}
		}
		//如果没有捎带请求，找到最近的一条请求
		for(int i=0;i < this.req_list.size();i++){
			if(!req_is_used[i]){
				this.cur_request = this.req_list.get(i);
				this.goal_location = this.req_list.get(i).getReq_location();
				break;
			}
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : (cur_floor==goal_location) ==> (cur_UpDown=="SITLL");
	 * 			  (cur_floor<goal_location) ==> (cur_UpDown=="UP");
	 * 			  (cur_floor>goal_location) ==> (cur_UpDown=="DOWN");
	 */
	public void move(){
		//模拟电梯运行
		if(this.cur_floor == this.goal_location) {
			cur_UpDown = "STILL";
		}
		else if(this.cur_floor < this.goal_location) {
			moveUp();
			cur_UpDown = "UP";
		}
		else if(this.cur_floor > this.goal_location) {
			moveDown();
			cur_UpDown = "DOWN";
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : add the floor and time
	 */
	public void moveUp(){
		//电梯上移一个单位
		this.cur_floor++;
		this.cur_time+=0.5;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : add the time and reduce the floor
	 */
	public void moveDown(){
		//电梯下移一个单位
		this.cur_floor--;
		this.cur_time+=0.5;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : add the time
	 */
	public void openDoor(){
		//电梯开门一次
		this.cur_time+=1;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : fisrt, judge where the light is on
	 * 			  if the er light and fr light is on, out to the console by sort and call light off function and call the open door function
	 */
	public void lightJudge(){ //每到一层楼，进行判断与处理
		//是否是亮灯楼层：主请求和其他请求的亮灯一致处理
		int[] er_light = elevator.getEr_light();
		int[][] fr_light =elevator.getFr_light();
		//按照顺序输出
		boolean[] sorted_out = new boolean[3];
		Arrays.fill(sorted_out,false);
		int[] sorted_out_index = new int[3];
		//sorted_out_index:本楼层是否需要输出并且熄灯的指令
		//到达一层楼之后，从中找到指令方向和当前指令相同的指令，然后熄灯
		//对于方向不同的指令，要排除
		sorted_out_index[0] = elevator.getEr_index()[cur_floor]; //0--ER
		sorted_out_index[1] = elevator.getFr_index()[cur_floor][0]; //1--FR UP
		sorted_out_index[2] = elevator.getFr_index()[cur_floor][1]; //1--FR DOWN
		int min_index = Integer.MAX_VALUE;
		//sorted_out决定是否是需要执行的指令，特别对于FR指令
		if(er_light[cur_floor] == 1){
			sorted_out[0] = true;
		}
		if(fr_light[cur_floor][0] == 1){
			//add~~~~~~~~~~~~~~~~~~~~~~~~
			Request req = new Request(elevator.getFr_req()[cur_floor][0]);
			req.dealString();
			if(cur_UpDown == "DOWN" && cur_floor != goal_location) //电梯向上灯亮，但是电梯目前向下运行（并且这条请求不是主请求）
				sorted_out[1] = false;
			else
				sorted_out[1] = true;
		}
		if(fr_light[cur_floor][1] == 1){
			//add~~~~~~~~~~~~~~~~~~~~~~~~
			Request req = new Request(elevator.getFr_req()[cur_floor][1]);
			req.dealString();
			if(cur_UpDown == "UP" && cur_floor != goal_location) //电梯向下灯亮，但是电梯目前向上运行（并且这条请求不是主请求）
				sorted_out[2] = false;
			else
				sorted_out[2] = true;
		}
		//第一遍扫描，得到最前面的指令
		for(int i=0;i < 3;i++){
			if(min_index > sorted_out_index[i] && sorted_out[i])
				min_index = sorted_out_index[i];
		}
		//输出第一条，并且判断是否开关门
		boolean will_open_door = false;
		for(int i=0;i < 3;i++){
			if(min_index == sorted_out_index[i] && sorted_out[i]){
				outAndOff(i);
				will_open_door = true;
				sorted_out[i] = false;
			}
		}
		min_index = Integer.MAX_VALUE;
		//输出可能的第二条
		for(int i=0;i < 3;i++){
			if(min_index > sorted_out_index[i] && sorted_out[i])
				min_index = sorted_out_index[i];
		}
		for(int i=0;i < 3;i++){
			if(min_index == sorted_out_index[i] && sorted_out[i]){
				outAndOff(i);
				sorted_out[i] = false;
			}
		}
		//输出可能的第三条
		for(int i=0;i < 3;i++)
			if(sorted_out[i]){
				outAndOff(i);
			}
		//开关门
		if(will_open_door)
			openDoor();
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : (type==0) ==> (out the er information to console and call the light off function)
	 * 			  (type==1) ==> (out the fr information to console and call the light off function)
	 * 			  (type==2) ==> (out the fr information to console and call the light off function)
	 */
	public void outAndOff(int type){ //输出并且熄灯，并且标记这条请求被完全执行
		if(type == 0) {
			OutputHandler.outToConsole2(elevator.getEr_req()[cur_floor],cur_floor,cur_UpDown,cur_time);
			req_is_used[elevator.getEr_index()[cur_floor]] = true;
			elevator.lightOff(1,cur_floor,0);
		} else if(type == 1){
			OutputHandler.outToConsole2(elevator.getFr_req()[cur_floor][0],cur_floor,cur_UpDown,cur_time);
			req_is_used[elevator.getFr_index()[cur_floor][0]] = true;
			elevator.lightOff(2,cur_floor,0);
		} else if(type == 2){
			OutputHandler.outToConsole2(elevator.getFr_req()[cur_floor][1],cur_floor,cur_UpDown,cur_time);
			req_is_used[elevator.getFr_index()[cur_floor][1]] = true;
			elevator.lightOff(2,cur_floor,1);
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : (all req is used and all light is off) ==> (\result == true)
	 * 			  (exist req is not used ) ==> (\result == false)
	 * 			  (exist light is not off) ==> (\result == false)
	 */
	public boolean isEnd(){ //是否需要结束调度
		//完成调度的条件：队列中所有请求都已经亮灯，所有的灯都熄灭了
		boolean is_all_req_light = true;
		for(int i=0;i < req_is_used.length;i++)
			if(!req_is_used[i]){
				is_all_req_light = false;
				break;
			}
		boolean is_light_off = true;
		int[] er_light = elevator.getEr_light();
		int[][] fr_light = elevator.getFr_light();
		for(int i=0;i < 11;i++)
			if(er_light[i] == 1 || fr_light[i][0] == 1 || fr_light[i][1] == 1){
				is_light_off = false;
				break;
			}
		return (is_all_req_light & is_light_off);
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : set the index of request
	 */
	public void setReqIndex(){
		//对每一个请求编号
		for(int i=0;i < this.req_list.size();i++)
			this.req_list.get(i).setIndex(i);
	}
}
