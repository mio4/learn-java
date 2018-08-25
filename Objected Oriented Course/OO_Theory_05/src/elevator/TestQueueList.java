package elevator;

import elevator.Query.Direction;

public class TestQueueList {
	public static void main(String[] args) throws Exception {
		
	QueryList ql=new QueryList(10,1);
	
	//测试不合法的remove
	try {
	System.out.println("remove from empty queue:"+ql.remove(3));
	} catch (InvalidRemoveException e) {
		System.out.println(e.getMessage());
	}
	
	ql.append(new Query(10,0.0));
	ql.append(new Query(8,1.0,Query.Direction.UP));
	ql.append(new Query(5,1.5,Query.Direction.DOWN));
	
	//测试合法的remove
	System.out.println("remove the first element from the queue:"+ql.remove(0));
	
	//测试不合法的remove
	try {
	System.out.println("remove an nonexistent element from the queue:"+ql.remove(-1));
	System.out.println("remove an exceeded element from the queue:"+ql.remove(4));
	} catch (InvalidRemoveException e) {
		System.out.println(e.getMessage());
	}
	
	//测试append方法
	Query req1 = new Query(3,1,Direction.UP);
	try {
		System.out.println("the size of QueryList before appending: "+ql.getSize());
		ql.append(req1);
		System.out.println("the size of QueryList after appending: " + ql.getSize());
	} catch(Exception e) {e.getMessage();}
	//测试pickupQuery方法
	Elevator elevator = new Elevator(10,1);
	System.out.println("Query是否为空 " + elevator.emptyQuery()); //return false ==> the query is empty
	elevator.pickupQuery(req1);
	System.out.println("Query是否为空 " + elevator.emptyQuery()); //return ture ==> add succeeded
	elevator.moveForQuery();
	//测试checkFinishedQuery方法
	System.out.println("是否存在已经完成的方法" + elevator.checkFinishedQuery()); //schedule succeeded
	
 }
}
