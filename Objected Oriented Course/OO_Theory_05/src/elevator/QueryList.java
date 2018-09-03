package elevator;

import java.util.ArrayList;
import elevator.Query.Direction;



class UnsortedException extends Exception {
	private static final long serialVersionUID = 1L;

	public UnsortedException(String message) {
		super(message);
	}
}
class InvalidRemoveException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidRemoveException(String message) {
		super(message);
	}
}

public class QueryList {
/**
 * OVERVIEW:请求队列类，管理乘客请求（Query）
 * 请求队列需要在添加请求时对请求的合法性进行二次判断，所以需要记录楼层的取值范围，队列中最后一个请求的时间
 * 对于不满足时间非降序的添加请求，需要抛出一个可辨识的异常便于处理逻辑，所以额外定义了一个UnsortedException类请求队列提供添加、遍历、清空的方法*/

    private ArrayList<Query> queue;//请求队列
	private int highLevel, lowLevel;//最高和最低楼层
	private double lastTime;//队列中最近一次请求的时间
	

	public QueryList(int high, int low, double time) {
		queue = new ArrayList<Query>();
		highLevel = high;
		lowLevel = low;
		lastTime = time;
	}
	
	public QueryList(int high, int low) {
		this(high, low, 0);
	}
	
	public boolean append(Query req) throws Exception{
    /**
     *@REQUIRES: req != null ;
     *@MODIFIES: this;
     *@EFFECTS: 
     *(this.lastTime>req.queryTime)==>exceptional_behavior(UnsortedException);
     *(req.targetFloor=low && req.queryDirection==Direction.DOWN)==>\result=false;
     *(req.targetFloor=high && req.queryDirection==Direction.UP)==>\result=false;
     *(this.queue.size == \old(this.queue).size+1) && (this.queue.contains(req)==true)&&(this.queue.lastTime==req.queryTime) && (\result==true); 
       */
	if(this.lastTime > req.getTime())
		throw new UnsortedException("UnsortedException");
	if(req.getTarget()==this.lowLevel && req.getDirection()==Direction.DOWN)
		return false;
	if(req.getTarget()==this.highLevel && req.getDirection()==Direction.UP)
		return false;
	this.queue.add(req);
		
	//请根据规格把该方法的代码补充完整
	return true;
	}
	

	public boolean remove(int index)throws InvalidRemoveException{
    /**
     *@MODIFIES:this
     *@EFFECTS:
     *(\old(this).get(index) !=null) ==> (this.size == \old(this).size-1) && (this.contains(\old(this).get(index))==false) && (\result==true) ; 
     *(\old(this).size ==0)==>exceptional_behavior(InvalidRemoveException);
     *(index >=\old(this).size) ==> exceptional_behavior (InvalidRemoveException);
     *(index < 0) ==> exceptional_behavior (InvalidRemoveException);
	*/
		if ((index < 0)	|| index>=queue.size()|| queue.size() == 0){
             throw new InvalidRemoveException("Invalid remove!") ;
		}
        queue.remove(index);
		return true;
	}
	

	public int getSize() {
		return queue.size();
	}
	
	public Query getQuery(int index) {
		return queue.get(index);
	}
	
	public void clear() {
		queue.clear();
	}
}