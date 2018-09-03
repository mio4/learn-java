package elevator;

import java.util.ArrayList;

//interface
interface SchedulableCarrier {
	final double moveTime = 0.5, callTime = 1.0;
	abstract boolean moveUP();
	abstract boolean moveDOWN();
	abstract boolean callOpenAndClose();
}

//implement
public class Elevator implements SchedulableCarrier {
/*OVERVIEW: 电梯类，模拟电梯实体，能够响应用户请求并改变电梯状态。该类自己记录和管理电梯运动过程中的状态变化，包括当前停靠的楼层，停靠、开关门之后的时间，当前运动方向。*/

	private int highLevel, lowLevel;//最高楼层和最低楼层
	private Query curStatus;//电梯当前状态（记录停靠楼层、开关门之后时间、当前运动方向）
	private ArrayList<Query> curHandleQuery;//当前捎带请求队列

	public Elevator(int high, int low){ 
	/**
	 *@REQUIRES：(high > low)&&(high > 0)&&(low > 0)&&(high<=highLevel);
	 *@MODIFIES: this;
  	 *@EFFECTS： (\result = this) && (this.curStatus != null) && (this.highLevel == high) && (this.lowLevel == low)&&(this.curHandleQuery.isEmpty());
	*/
		highLevel = high;
		lowLevel = low;
		curStatus = new Query(1, 0);
		curHandleQuery = new ArrayList<Query>();
	}

	@Override 
	
	public boolean moveUP() {
		/**@REQUIRES: None;
		 * @MODIFIES: this.curStatus
		 * @EFFECTS: (this.getCurFloor()+1>highLevel ==> \result = false)
		 * (this.getCurFloor() < highLevel ==> (curStatus!=null && \result = true))
		 */
		if(getCurFloor() + 1 > highLevel) {
			return false;
		} else {
			curStatus = new Query(getCurFloor() + 1, getCurTime() + moveTime, Query.Direction.UP);
			return true;
		}
	}

	@Override
	
	public boolean moveDOWN() {
		if(getCurFloor() - 1 < lowLevel) {
			return false;
		} else {
			curStatus = new Query(getCurFloor() - 1, getCurTime() + moveTime, Query.Direction.DOWN);
			return true;
		}
	}

	@Override
	
	public boolean callOpenAndClose() {
		curStatus = new Query(getCurFloor(), getCurTime() + callTime);
		return true;
	}

	@Override
	public String toString() {
		return "(" + this.getCurFloor() + "," + this.getCurDirect() + "," + this.getCurQuery().getTarget() + ")";
	}
	
	
	public int getCurFloor() {
		return curStatus.getTarget();
	}
	
	public double getCurTime() {
		return curStatus.getTime();
	}

	public Query.Direction getCurDirect() {
		return curStatus.getDirection();
	}

	public Query getCurQuery() {
		return emptyQuery() ? null : curHandleQuery.get(0);
	}
	
	public boolean emptyQuery() {
		return curHandleQuery.isEmpty();
	}
	
	//OVERVIEW:检查是否有已经完成的请求
	public boolean checkFinishedQuery(){
	/**
	 *@REQUIRES：None
	 *@MODIFIES: curHandleQuery
	 *@EFFECTS: 
	 *(\all int i; 0 <= i< curHandleQuery.size;curHandleQuery[i].targetFloor!=this.curStatus.targetFloor)==>\result==false;
	 *(\all int i; 0 <= i< curHandleQuery.size; old(curHandleQuery)[i].targetFloor==(this.curStatus.tartgetFloor)==>(curHandleQuery.contains(\old(curHandleQuery)[i])==false)&&(curHandleQuery.size = \old(curHandleQuery).size - 1)\result==true); 
    */   
    //请根据规格修改代码
	for(int i = 0; i < curHandleQuery.size(); i++) {
			Query pickedQuery = curHandleQuery.get(i);
			if(pickedQuery.getTarget() == getCurFloor()) {
				System.out.printf("(%d, %s, %.1f)\t(%s)\n", getCurFloor(), "STAY", getCurTime(), pickedQuery.toString());
				curHandleQuery.remove(i);
                break;
			} else if (i == this.curHandleQuery.size()-1)
				return false;
		}
	return true;
	}
	
	
	
	//OVERVIEW:将符合捎带条件的请求加入电梯当前处理队列
	public void pickupQuery(Query req) { 
    /**
     *@REQUIRES：(req!=null)&&(req.queryTime<=this.curStatus.queryTime)&&(req.queryDirection==this.curStatus.Direction);
     *@MODIFIES: this;
     *@EFFECTS: 
     *(\old(this).curHandleQuery.isEmpty())==>(this.curStatus ==req);
     *(\all int i, j;  0 <= i & i < j & j < curHandleQuery.size; (curHandleQuery.size == \old(curHandleQuery).size+1) && (curHandleQuery.contains(req)==true)&&(curHandleQuery[i].queryTime<=curHandleQuery[j].queryTime)
	*/
	//请根据规格补充代码
		if(this.curHandleQuery.isEmpty())
			this.curStatus = req;
		for(int i=0; i < this.curHandleQuery.size();i++) {
			if(this.curHandleQuery.get(i).getTime() > req.getTime()) {
				this.curHandleQuery.add(i,req);
				return;
			}
		}
		this.curHandleQuery.add(req);
		return;
	}
	


	//电梯响应当前捎带请求队列
	public void moveForQuery() throws Exception {
		/**@REQUIRES: None;
		 * @MODIFIES: this.curHandleQuery;System.out;
		 * @EFFECTS: normal_behavior
		 * checkFinishedQuery() ==> callOpenAndClose();
		 * directDelta==-1 ==> moveDOWN() && directDelta==1 ==>moveUP()
		 * req.getTarget() == getCurFloor() ==> curHandleQuery.remove(0);
		 * !Query.checkTime(getCurTime()) ==> exceptional_behavior(Time Out Of Range);
		 */
		if(emptyQuery()) {
			return;
		}
		
		Query req = getCurQuery();		
		boolean ifOpenAndClose = false;
		
		//开始主请求工作之前，查询当前捎带队列中是否有已完成请求，若有，全部剔除，然后开关门一次
		ifOpenAndClose=checkFinishedQuery();
		if(ifOpenAndClose) {
			callOpenAndClose();
			return;
		}
				
		//执行主请求，逐层更新电梯状态
		int directDelta = (int)Math.signum(req.getTarget() - getCurFloor());
		String curDirect;
		switch(directDelta) {
			case -1 : {
				curDirect = "DOWN";
				moveDOWN();
				break;
			}
			case 0 : {
				curDirect = "STAY";
				break;
			}
			case 1 : {
				curDirect = "UP";
				moveUP();
				break;
			}
			default : throw new Exception("Invalid Status.");
		}
		if(Query.checkTime(getCurTime()) == false) {
			throw new Exception("Time Out Of Range");
		}
		
		// 每更新完一次电梯状态查询当前捎带队列中是否有已完成请求，若有，全部剔除，并开关门一次
		ifOpenAndClose=checkFinishedQuery();
				
		//主请求完成
		if(req.getTarget() == getCurFloor()) {
			ifOpenAndClose = true;
			System.out.printf("(%d, %s, %.1f)\n", getCurFloor(), curDirect, getCurTime());
			curHandleQuery.remove(0);
		}			
		if(ifOpenAndClose) {
			callOpenAndClose();
		}
	}
		
}

