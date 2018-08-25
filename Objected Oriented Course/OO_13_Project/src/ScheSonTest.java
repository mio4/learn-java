
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before; 
import org.junit.After;

import java.util.ArrayList;
import java.util.List;

/** 
* ScheSon Tester. 
* Overview:对于ScheSon类的测试
* @author <Authors name> 
* @since <pre>六月 4, 2018</pre> 
* @version 1.0 
*/ 
public class ScheSonTest {

@Before
public void before() throws Exception { 
} 

@After
public void after() throws Exception { 
}

/** 
* 
* Method: get_req_list(List<Request> req_list) 
* 
*/ 
@Test
public void testGet_req_list() throws Exception { 
//TODO: Test goes here...
	ScheSon sc = new ScheSon();
	ArrayList<Request> list = new ArrayList<Request>();
	sc.get_req_list(list);
} 

/** 
* 
* Method: initial_floor() 
* 
*/ 
@Test
public void testInitial_floor() throws Exception { 
//TODO: Test goes here...
	ScheSon sc = new ScheSon();
	sc.initial_floor();
}

/**
 *
 * Method: repOK()
 */
@Test
public void testRepOK() throws Exception{
	ScheSon sc = new ScheSon();
	Assert.assertEquals(sc.repOK(),true);
}

/** 
* 
* Method: command(boolean sch_return) 
* 
*/ 
@Test
public void testCommand() throws Exception { 
//TODO: Test goes here...
	ScheSon sc = new ScheSon();
	Assert.assertEquals(sc.command(false),false);
	Assert.assertEquals(sc.command(true),true);
} 

/** 
* 
* Method: schedule() 
* 
*/ 
@Test
public void testSchedule() throws Exception { 
//TODO: Test goes here...
	//对于基本功能的测试以及bug的复现
	ScheSon sc = new ScheSon();
	ArrayList<Request> req_list = new ArrayList<Request>();
	//首先进行功能测试，发现按照捎带的设计情况，并不能实现相关分支代码的实现，所以某些代码段本质是无效的
	//Test Sample 1
	Request r1 = new Request("(FR,1,UP,0)");
	r1.get_info();
	req_list.add(r1);
	//r2,同质请求(能够处理的类型)
	Request r2 = new Request("(FR,1,UP,0)");
	r2.get_info();
	req_list.add(r2);
	//add r22
	Request r22 = new Request("(FR,3,UP,2)");
	r22.get_info();
	req_list.add(r22);
	//add r23
	Request r23 = new Request("(FR,1,UP,5)");
	r23.get_info();
	req_list.add(r23);
	//r3
	Request r3 = new Request("(ER,1,20)");
	r3.get_info();
	req_list.add(r3);
	//r4
	Request r4 = new Request("(ER,9,7)");
	r4.get_info();
	req_list.add(r4);
	//r5
	Request r5 = new Request("(FR,8,DOWN,10)");
	r5.get_info();
	req_list.add(r5);
	//r6
	Request r6 = new Request("(ER,7,13)");
	r6.get_info();
	req_list.add(r6);
	sc.get_req_list(req_list);
	sc.initial_floor();
	sc.schedule();

	//Test Sample 2
	ScheSon sc2 = new ScheSon();
	ArrayList<Request> req_list2 = new ArrayList<Request>();
	Request rt20 = new Request("(FR,7,UP,0)");
	rt20.get_info();
	req_list2.add(rt20);
	Request rt21 = new Request("(FR,3,UP,0)");
	rt21.get_info();
	req_list2.add(rt21);
	Request rt22 = new Request("(FR,1,UP,10)");
	rt22.get_info();
	req_list2.add(rt22);
	Request rt23 = new Request("(FR,8,UP,12)");
	rt23.get_info();
	req_list2.add(rt23);
	Request rt24 = new Request("(ER,9,14)");
	rt24.get_info();
	req_list2.add(rt24);
	Request rt25 = new Request("(FR,1,UP,10)");
	rt25.get_info();
	req_list2.add(rt25);
	sc2.get_req_list(req_list2);
	sc2.initial_floor();
	sc2.schedule();
}

@Test
public void test3() throws Exception{
	//Test Sample 3
	ScheSon sc3 = new ScheSon();
	ArrayList<Request> req_list3 = new ArrayList<Request>();
	Request rt30 = new Request("(FR,1,UP,0)");
	rt30.get_info();
	req_list3.add(rt30);
	Request rt31 = new Request("(FR,10,DOWN,1)");
	rt31.get_info();
	req_list3.add(rt31);
	Request rt32 = new Request("(ER,7,2)");
	rt32.get_info();
	req_list3.add(rt32);
	Request rt33 = new Request("(ER,6,2)");
	rt33.get_info();
	req_list3.add(rt33);
	sc3.get_req_list(req_list3);
	sc3.initial_floor();
	sc3.schedule();
}

@Test
public void test4() throws Exception{
	//Test Sample 4 --测试剩下没有被捎带的部分
	ScheSon sc4 = new ScheSon();
	ArrayList<Request> req_list4 = new ArrayList<Request>();
	Request rt40 = new Request("(FR,1,UP,0)");
	rt40.get_info();
	req_list4.add(rt40);
	Request rt41 = new Request("(FR,6,DOWN,1)");
	rt41.get_info();
	req_list4.add(rt41);
	Request rt42 = new Request("(ER,1,6)");
	rt42.get_info();
	req_list4.add(rt42);
	Request rt43 = new Request("(ER,1,6)");
	rt43.get_info();
	req_list4.add(rt43);
	Request rt44 = new Request("(ER,1,6)");
	rt44.get_info();
	req_list4.add(rt44);
	sc4.get_req_list(req_list4);
	sc4.initial_floor();
	sc4.schedule();
}

@Test
public void test5() throws Exception{
	//Test Sample 5 ： 关于单条语句的捎带和多条语句的捎带问题
	//复现：
	//请求楼层全部处于(e.e_n,r主.n]，全部捎带
	//部分请求处于[1,r主.n)和[e.e_n,10]，这类请求在本次主请求处理完成后，选队列位置最前的成为主请求
	//请求楼层全部处于[r主.n,e.e_n)，全部捎带
	//部分请求处于[1,r主.n)和[e.e_n,10]，这类请求在本次主请求处理完成后，选队列位置最前的成为主请求
	ScheSon sc5 = new ScheSon();
	ArrayList<Request> req_list5 = new ArrayList<Request>();
	Request rt50 = new Request("(FR,1,UP,0)");
	rt50.get_info();
	req_list5.add(rt50);
	Request rt51 = new Request("(ER,5,3)");
	rt51.get_info();
	req_list5.add(rt51);
	Request rt52 = new Request("(ER,2,4)");
	rt52.get_info();
	req_list5.add(rt52);
	Request rt53 = new Request("(ER,1,6)");
	rt53.get_info();
	req_list5.add(rt53);
	Request rt54 = new Request("(FR,2,UP,9)");
	rt54.get_info();
	req_list5.add(rt54);
	Request rt55 = new Request("(FR,10,DOWN,13)");
	rt55.get_info();
	req_list5.add(rt55);
	Request rt56 = new Request("(ER,4,13)");
	rt56.get_info();
	req_list5.add(rt56);
	Request rt57 = new Request("(FR,6,DOWN,13)");
	rt57.get_info();
	req_list5.add(rt57);
	Request rt58 = new Request("(ER,5,14)");
	rt58.get_info();
	req_list5.add(rt58);
	Request rt59 = new Request("(FR,8,DOWN,16)");
	rt59.get_info();
	req_list5.add(rt59);
	Request rt510 = new Request("(FR,7,UP,19)");
	rt510.get_info();
	req_list5.add(rt510);
	Request rt511 = new Request("(FR,6,UP,20)");
	rt511.get_info();
	req_list5.add(rt511);
	Request rt512 = new Request("(ER,1,25)");
	rt512.get_info();
	req_list5.add(rt512);
	Request rt513 = new Request("(FR,6,DOWN,26)");
	rt513.get_info();
	req_list5.add(rt513);
	Request rt514 = new Request("(ER,3,26)");
	rt514.get_info();
	req_list5.add(rt514);
	Request rt515 = new Request("(ER,6,28)");
	rt515.get_info();
	req_list5.add(rt515);
	Request rt516 = new Request("(FR,6,DOWN,28)");
	rt516.get_info();
	req_list5.add(rt516);
	Request rt517 = new Request("(FR,10,DOWN,29)");
	rt517.get_info();
	req_list5.add(rt517);
	Request rt518 = new Request("(FR,7,DOWN,30)");
	rt518.get_info();
	req_list5.add(rt518);
	Request rt519 = new Request("(FR,3,UP,32)");
	rt519.get_info();
	req_list5.add(rt519);
	Request rt520 = new Request("(ER,9,32)");
	rt520.get_info();
	req_list5.add(rt520);
	Request rt521 = new Request("(FR,3,UP,36)");
	rt521.get_info();
	req_list5.add(rt521);
	Request rt522 = new Request("(FR,1,UP,40)");
	rt522.get_info();
	req_list5.add(rt522);
	Request rt523 = new Request("(ER,9,41)");
	rt523.get_info();
	req_list5.add(rt523);
	Request rt524 = new Request("(ER,8,41)");
	rt524.get_info();
	req_list5.add(rt524);
	Request rt525 = new Request("(ER,1,41)");
	rt525.get_info();
	req_list5.add(rt525);
	Request rt526 = new Request("(FR,9,DOWN,43)");
	rt526.get_info();
	req_list5.add(rt526);
	Request rt527 = new Request("(ER,7,43)");
	rt527.get_info();
	req_list5.add(rt527);
	Request rt528 = new Request("(FR,1,UP,44)");
	rt528.get_info();
	req_list5.add(rt528);
	Request rt529 = new Request("(FR,2,DOWN,45)");
	rt529.get_info();
	req_list5.add(rt529);
	Request rt530 = new Request("(ER,5,46)");
	rt530.get_info();
	req_list5.add(rt530);
	Request rt531 = new Request("(FR,2,UP,47)");
	rt531.get_info();
	req_list5.add(rt531);
	Request rt532 = new Request("(FR,9,DOWN,47)");
	rt532.get_info();
	req_list5.add(rt532);
	Request rt533 = new Request("(FR,8,UP,47)");
	rt533.get_info();
	req_list5.add(rt533);
	Request rt534 = new Request("(FR,2,DOWN,49)");
	rt534.get_info();
	req_list5.add(rt534);
	Request rt535 = new Request("(FR,10,DOWN,49)");
	rt535.get_info();
	req_list5.add(rt535);
	Request rt536 = new Request("(ER,10,50)");
	rt536.get_info();
	req_list5.add(rt536);
	Request rt537 = new Request("(ER,3,52)");
	rt537.get_info();
	req_list5.add(rt537);
	Request rt538 = new Request("(ER,2,53)");
	rt538.get_info();
	req_list5.add(rt538);
	Request rt539 = new Request("(FR,10,DOWN,55)");
	rt539.get_info();
	req_list5.add(rt539);
	Request rt540 = new Request("(ER,1,55)");
	rt540.get_info();
	req_list5.add(rt540);
	Request rt541 = new Request("(FR,9,DOWN,56)");
	rt541.get_info();
	req_list5.add(rt541);
	Request rt542 = new Request("(ER,1,59)");
	rt542.get_info();
	req_list5.add(rt542);
	Request rt543 = new Request("(FR,1,UP,62)");
	rt543.get_info();
	req_list5.add(rt543);
	Request rt544 = new Request("(FR,9,UP,64)");
	rt544.get_info();
	req_list5.add(rt544);
	Request rt545 = new Request("(FR,4,UP,64)");
	rt545.get_info();
	req_list5.add(rt545);
	Request rt546 = new Request("(ER,3,67)");
	rt546.get_info();
	req_list5.add(rt546);
	Request rt547 = new Request("(ER,3,69)");
	rt547.get_info();
	req_list5.add(rt547);
	Request rt548 = new Request("(FR,10,DOWN,69)");
	rt548.get_info();
	req_list5.add(rt548);
	Request rt549 = new Request("(ER,3,71)");
	rt549.get_info();
	req_list5.add(rt549);
	sc5.get_req_list(req_list5);
	sc5.initial_floor();
	sc5.schedule();
	List<String> out5 = sc5.pass_String();
	//打印出结果，对比得知不能完成多条语句的捎带
	for(String s : out5)
		System.out.println(s);
}



@Test
public void test7() throws Exception{
	//1.	合法输入->单一捎带->e.sta==UP->楼层请求e.e_n < r.n <= e.n 捎带
	//2.	合法输入->单一捎带-> e.sta==UP->楼层请求r.n > e.n 捎带
	ScheSon sc7 = new ScheSon();
	ArrayList<Request> req_list7 = new ArrayList<Request>();
	Request rt70 = new Request("(FR,1,UP,0)");
	rt70.get_info();
	req_list7.add(rt70);
	Request rt71 = new Request("(FR,5,DOWN,1)");
	rt71.get_info();
	req_list7.add(rt71);
	Request rt72 = new Request("(FR,5,UP,1)");
	rt72.get_info();
	req_list7.add(rt72);
	Request rt73 = new Request("(ER,5,1)");
	rt73.get_info();
	req_list7.add(rt73);
	sc7.get_req_list(req_list7);
	sc7.initial_floor();
	sc7.schedule();
	List<String> out7 = sc7.pass_String();
	//打印出结果，对比得知不能完成多条语句的捎带
	//for(String s : out7)
	//	System.out.println(s);
}

@Test
public void test8() throws Exception{
	//1.	合法输入->单一捎带->e.sta==DOWN->楼层请求e.n < r.n <= e.e_n 捎带
	//2.	合法输入->单一捎带-> e.sta==DOWN->楼层请求r.n < e.n 捎带
	//3.	合法输入->单一捎带->e.sta==STILL->WFS状态 不捎带
	ScheSon sc8 = new ScheSon();
	ArrayList<Request> req_list8 = new ArrayList<Request>();
	Request rt80 = new Request("(FR,1,UP,0)");
	rt80.get_info();
	req_list8.add(rt80);
	Request rt81 = new Request("(ER,5,1)");
	rt81.get_info();
	req_list8.add(rt81);
	Request rt82 = new Request("(FR,2,UP,2)");
	rt82.get_info();
	req_list8.add(rt82);
	Request rt83 = new Request("(FR,3,DOWN,2)");
	rt83.get_info();
	req_list8.add(rt83);
	Request rt84 = new Request("(FR,3,UP,2)");
	rt84.get_info();
	req_list8.add(rt84);
	Request rt85 = new Request("(FR,6,UP,2)");
	rt85.get_info();
	req_list8.add(rt85);
	Request rt86 = new Request("(ER,6,2)");
	rt86.get_info();
	req_list8.add(rt86);
	Request rt87 = new Request("(FR,8,UP,2)");
	rt87.get_info();
	req_list8.add(rt87);
	Request rt88 = new Request("(FR,8,DOWN,2)");
	rt88.get_info();
	req_list8.add(rt88);
	Request rt89 = new Request("(ER,8,2)");
	rt89.get_info();
	req_list8.add(rt89);
	Request rt810 = new Request("(ER,9,3)");
	rt810.get_info();
	req_list8.add(rt810);
	Request rt811 = new Request("(FR,9,UP,3)");
	rt811.get_info();
	req_list8.add(rt811);
	sc8.get_req_list(req_list8);
	sc8.initial_floor();
	sc8.schedule();
	List<String> out8 = sc8.pass_String();
	//打印出结果，对比得知不能完成多条语句的捎带
	System.out.println("");
	for(String s : out8)
		System.out.println(s);
}

/** 
* 
* Method: pass_String() 
* 
*/ 
@Test
public void testPass_String() throws Exception { 
//TODO: Test goes here...
	ScheSon sc = new ScheSon();
	Assert.assertTrue(sc.pass_String().size()==0);
} 

/** 
* 
* Method: LesserOrNot(Request r1, Request r2) 
* 
*/ 
@Test
public void testLesserOrNot() throws Exception { 
//TODO: Test goes here...
	ScheSon sc = new ScheSon();
	Request req1 = new Request("(FR,1,UP,0)");
	req1.changeValueQueue("FR","UP",1,1,"(FR,1,UP,0)");
	Request req2 = new Request("(FR,3,UP,0)");
	req2.changeValueQueue("FR","UP",1,3,"(FR,1,UP,0)");
	Request req3 = new Request("(FR,1,UP,1)");
	req3.changeValueSort("FR","UP",1,3,"(FR,1,UP,0)");
	Request req4 = new Request("(FR,1,UP,1)");
	req4.changeValueSort("FR","UP",2,3,"(FR,1,UP,0)");
	Assert.assertEquals(sc.LesserOrNot(req1,req2),true);
	Assert.assertEquals(sc.LesserOrNot(req1,req3),true);
	Assert.assertEquals(sc.LesserOrNot(req2,req1),false);
	Assert.assertEquals(sc.LesserOrNot(req3,req4),true);
} 

/** 
* 
* Method: bubbleSort(Request[] req_list, int cnt) 
* 
*/ 
@Test
public void testBubbleSort() throws Exception { 
//TODO: Test goes here...
	ScheSon sc = new ScheSon();
	Request req1 = new Request("(FR,1,UP,0)");
	req1.changeValueQueue("FR","UP",1,1,"(FR,1,UP,0)");
	Request req2 = new Request("(FR,3,UP,0)");
	req2.changeValueQueue("FR","UP",1,3,"(FR,1,UP,0)");
	Request req3 = new Request("(FR,1,UP,1)");
	req3.changeValueQueue("FR","UP",1,3,"(FR,1,UP,0)");
	Request[] reqs = new Request[3];
	reqs[0] = req1;
	reqs[1] = req2;
	reqs[2] = req3;
	sc.bubbleSort(reqs,3);
} 


} 
