package elevator;

public class Scheduler {
	private Elevator elevator;
	private QueryList queryList;
	private int highLevel, lowLevel;
	
	public Scheduler() {
		this(10, 1);
	}
	
	//初始化调度器
	public Scheduler(int high, int low) {
		if(low != 1) {
			low = 1;
		}
		if(high < low) {
			high = low;
		}
		highLevel = high;
		lowLevel = low;
		elevator = new Elevator(high, low);
		queryList = new QueryList(high, low);
	}
	
	public boolean addQuery(Query req) throws Throwable {
		return queryList.append(req);
	}
	protected Elevator getElevator() {
		return elevator;
	}
	protected QueryList getQueryList() {
		return queryList;
	}
	protected int getHighLevel() {
		return highLevel;
	}
	protected int getLowLevel() {
		return lowLevel;
	}
}


 class ALS_Scheduler extends Scheduler {
	 
	public ALS_Scheduler() {
		this(10, 1);
	}
	public ALS_Scheduler(int high, int low) {
		super(high, low);
	}
	public void runElevator() {
		// fetch super.properties
		Elevator elevator = getElevator();
		QueryList queryList = getQueryList();
		int highLevel = getHighLevel(), lowLevel = getLowLevel();
		// A Little Smart schedule
		if(queryList.getSize() == 0) {
			System.out.println("Empty Query.");
			return;
		} else if(queryList.getQuery(0).getTime() != 0) {
			System.out.println("Query Not Started At Time 0.");
			return;
		}
		//扫描当前所有请求的队列，判断是否有电梯当前状态下可以捎带的请求，若有添加到电梯当前处理请求队列
		while(queryList.getSize() > 0) {
			try {
				//取队首请求,作为电梯需要响应的主请求
				Query query = queryList.getQuery(0);
				int target = query.getTarget();
				if(target < lowLevel || target > highLevel) {
					throw new Exception("Invalid Query.");
				}
			    //将该主请求添加到电梯的当前捎带处理队列中
				elevator.pickupQuery(query);
				//将该主请求从所有请求队列中移除
				queryList.remove(0);
				//电梯到达主请求目标楼层时的该层指令不被捎带
				boolean ifPick = query.getTarget() != elevator.getCurFloor();
				//判断当前电梯运动主请求可以捎带的请求方向
				Query.Direction pickedDirect = ifPick ? (query.getTarget() < elevator.getCurFloor() ? Query.Direction.DOWN : Query.Direction.UP) : Query.Direction.NONE;
				
				//只要捎带队列中有请求未执行完，就继续查询是否还有需要捎带的请求
				do {
					//打描请求队列获得可以进行捎带的所有请求，加入到电梯当前待处理队列中
					if(ifPick) {
						//获得电梯当前停靠的楼层
						int curFloor = elevator.getCurFloor();
						//获得捎带队列的队首请求（即当前主请求）的目标楼层
						int targetFloor = elevator.getCurQuery().getTarget();//
						
						for(int index = 0; index < queryList.getSize(); ++index) {
							Query req = queryList.getQuery(index);
							//若该时间段还没有产生新的请求，直接跳出循环
							//返回继续按照捎带请求队列运动，执行elevator.moveforQuery
							//使用break的前提条件是所有请求已经是按时序排列
							if(elevator.getCurTime() < req.getTime()) {
								break;
							}
							//如果请求来自电梯内，请求的方向满足捎带要求，则将请求加入电梯捎带队列
							if(req.getDirection() == Query.Direction.NONE) { // ER
								if(pickedDirect == Query.Direction.UP && req.getTarget() >= curFloor
								|| pickedDirect == Query.Direction.DOWN && req.getTarget() <= curFloor) {
									elevator.pickupQuery(req);//捎带
									queryList.remove(index);
									--index;
								}
								//如果请求来自楼层，请求的方向满足捎带要求，则将请求加入电梯捎带队列
							} else if(req.getDirection() == pickedDirect) { // FR
								if(pickedDirect == Query.Direction.UP && req.getTarget() >= curFloor && req.getTarget() <= targetFloor
								|| pickedDirect == Query.Direction.DOWN && req.getTarget() <= curFloor && req.getTarget() >= targetFloor) {
									elevator.pickupQuery(req);//捎带
									queryList.remove(index);
									--index;
								}
							}
						}
					}
					
					//响应捎带处理队列中的所有请求
					elevator.moveForQuery();
				} while(!elevator.emptyQuery());
			} catch(Exception except) {
				System.out.print(except.getMessage());
				System.out.println("Ignored.");
			}
		}
		queryList.clear();
	}
}