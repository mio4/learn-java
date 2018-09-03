//ScheSon继承Scheduler类
import java.util.*;
public class ScheSon extends Scheduler implements EMove{
	
	//请求队列
	private List<Request> req_list;
	//电梯自身属性
	private int floor = 1;//电梯运行到的楼层
	private double TIME = 0; //系统时间
	//输入变量属性
	private String InOut = "Blank"; //请求是来源于电梯内还是电梯外
	private String direct = "Blank"; //对于FR请求，请求方向是UP还是DOWN
	private long time = 0; //请求时间
	private int location = 0; //对于FR请求，是请求所在楼层；对于ER请求，是请求目标楼层
	//格式化输出需要
	private List<String> screen_out = new ArrayList<String>();
	//楼层判定
	private Floor[] floors = new Floor[11];
	
	public void get_req_list(List<Request> req_list)
	{
		this.req_list = req_list;
	}
	
	@Override
	public void initial_floor()
	{
		for(int i=1 ; i <= 10; i++)
			this.floors[i] = new Floor(i);
	}
	
	@Override
	public boolean command(boolean sch_return)
	{
		if(sch_return==false)
			return false;
		return true;
	}
	
	@Override
	public boolean schedule()
	{
		int[] flags = new int[this.req_list.size()]; //req_list的标记数组,初始化为零
		String OutStr = "Blank"; //输出（前半部分）
		double time_sub = 0;
		double time_plan = 0; //预计完成时间
		boolean while_flag = true;//判断所有指令是否全部执行
		
		//副请求的相关属性
		String InOutF = "Blank";
		String directF = "Blank";
		double timeF = 0;
		int locationF =0;
		int floor_temp = 0;
		int plus_pass_cnt = 0; //捎带执行的次数
		
		Request[] vice_req_queue = new Request[10]; //主请求所有捎带请求的队列,排序后再整理到screen_out中,暂时改为10条
		for(int j=0;j<10;j++)
			vice_req_queue[j]=new Request("");
		
		
		while(while_flag)
		{
			while_flag = false;
			int i = 0;
			while(i < this.req_list.size())
			{
				if(flags[i]==0)
				{
					this.InOut = req_list.get(i).get_InOut();
					this.direct = req_list.get(i).get_direct();
					this.time = req_list.get(i).get_time();
					this.location = req_list.get(i).get_location();
					
					if(this.InOut=="FR") //主指令是FR
					{
						//判断是否是同质请求
						if(this.floors[this.location].check(this.InOut, this.direct, this.time) == false)
						{
							this.screen_out.add(String.format("#SAME[FR,%d,%s,%d]",this.location,this.direct,this.time ));
							flags[i]=3;
							i++;
							continue;
						}
						//本条请求不和前面的请求同质
						OutStr = String.format("FR,%d,%s,%d",this.location,this.direct,this.time);
						//对于STILL类请求，不需要考虑后续指令的捎带
						if(this.location == this.floor)
						{
							this.TIME += 1; //开关门时间
							this.time += 1;
							if(this.TIME < this.time)
								this.TIME = this.time;
							this.screen_out.add(String.format("[%s]/(%d,STILL,%.1f)", OutStr,this.location,this.TIME));
							flags[i]=1;
							i++;
						}
						//其他指令需要考虑到捎带可能
						else if(this.floor < this.location) //电梯向上运行
						{	
							plus_pass_cnt = 0;
							//从所有请求开始扫描，直到时间超过范围为止，找出能够捎带的为止
							if(this.time < this.TIME)
								time_plan = this.TIME + (double) (this.location - this.floor)/2;
							else 
								time_plan = this.time + (double) (this.location - this.floor)/2;
							for(int j=0;j < this.req_list.size();j++)
							{
								//请求发生在预计时间内
								if(this.req_list.get(j).get_time() <= time_plan)
								{
									//如果副请求为FR请求(未执行),并且不是当前指令，并且在请求发生时刻满足：电梯楼层<请求楼层<=目标楼层，捎带
									if(flags[j]==0 && j!=i && this.req_list.get(j).get_direct()=="UP" &&this.req_list.get(j).get_InOut()=="FR" && this.req_list.get(j).get_location()>(this.req_list.get(j).get_time()-this.time)*2+this.floor && this.req_list.get(j).get_location()<= this.location )
									{
										flags[j]=2;
										String OutStrF = String.format("FR,%d,%s,%d",this.req_list.get(j).get_location(),this.req_list.get(j).get_direct(),this.req_list.get(j).get_time());
										vice_req_queue[plus_pass_cnt].changeValueQueue(this.req_list.get(j).get_InOut(), this.req_list.get(j).get_direct(), this.TIME+(double)(this.req_list.get(j).get_location()-this.floor)/2, this.req_list.get(j).get_location(),OutStrF);	
										this.TIME+=1;
										plus_pass_cnt+=1;
										time_plan+=1; 
									}
									//如果副请求为ER请求(未执行)，并且在请求发生时刻满足：电梯楼层<请求楼层<=10楼，捎带
									if(flags[j]==0 && j!=i && this.req_list.get(j).get_InOut()=="ER" && this.req_list.get(j).get_location()>(this.req_list.get(j).get_time()-this.time)*2+this.floor && this.req_list.get(j).get_location()<=10 )
									{
										if(this.req_list.get(j).get_location() < this.location)
										{
											flags[j] = 2;
											String OutStrF = String.format("ER,%d,%d",this.req_list.get(j).get_location(),this.req_list.get(j).get_time());
											//this.screen_out.add(String.format("[%s]/(%d,UP,%.1f)", OutStrF,this.location,this.TIME));
											vice_req_queue[plus_pass_cnt].changeValueQueue(this.req_list.get(j).get_InOut(), this.req_list.get(j).get_direct(), this.TIME+(double)(this.req_list.get(j).get_location()-this.floor)/2, this.req_list.get(j).get_location(),OutStrF);	
											this.TIME+=1;
											plus_pass_cnt+=1;
											time_plan+=1;
										}
										else  //当主请求执行完成之后，这条捎带请求还没有执行完成
										{
											flags[j]=4;
										}
									}
								}
								else 
									break;
							}
							//执行所有能够在主请求之前执行的捎带
							vice_req_queue = bubbleSort(vice_req_queue,plus_pass_cnt);
							for(int j=0;j<plus_pass_cnt;j++)
							{
								this.screen_out.add(String.format("[%s]/(%d,UP,%.1f)", vice_req_queue[j].get_printstr(),vice_req_queue[j].get_location(),(double)vice_req_queue[j].get_exetime()));
							}		
							//执行主请求
							time_sub =  (double) (this.location - this.floor)/2;
							if(this.TIME < this.time)
								this.TIME = this.time + time_sub + plus_pass_cnt;
							else 
								this.TIME = this.TIME + time_sub;
							this.screen_out.add(String.format("[%s]/(%d,UP,%.1f)", OutStr,this.location,this.TIME));
							flags[i]=1;
							this.TIME += 1; //开关门时间
							this.floor = this.location;
												
						}
						else if(this.floor > this.location)
						{
							plus_pass_cnt = 0;
							//从所有请求开始扫描，直到时间超过范围为止，找出能够捎带的为止
							if(this.time < this.TIME)
								time_plan = this.TIME + (double) (this.floor - this.location)/2;
							else 
								time_plan = this.time + (double) (this.floor - this.location)/2;
							for(int j=0;j < this.req_list.size();j++)
							{
								//请求发生在预计时间内
								if(this.req_list.get(j).get_time() <= time_plan)
								{
									//如果副请求为FR请求(未执行),并且不是当前指令，并且在请求发生时刻满足：目标楼层<=请求楼层<电梯楼层，捎带
									if(flags[j]==0 && j!=i && this.req_list.get(j).get_direct()=="DOWN" && this.req_list.get(j).get_InOut()=="FR" && this.req_list.get(j).get_location()<this.floor-(this.req_list.get(j).get_time()-this.time)*2 && this.req_list.get(j).get_location()>= this.location )
									{
										flags[j]=2;
										String OutStrF = String.format("FR,%d,%s,%d",this.req_list.get(j).get_location(),this.req_list.get(j).get_direct(),this.req_list.get(j).get_time());
										vice_req_queue[plus_pass_cnt].changeValueQueue(this.req_list.get(j).get_InOut(), this.req_list.get(j).get_direct(), this.TIME+(double)(this.req_list.get(j).get_location()-this.floor)/2, this.req_list.get(j).get_location(),OutStrF);	
										this.TIME+=1;
										plus_pass_cnt+=1;
										time_plan+=1; 
									}
									//如果副请求为ER请求(未执行)，并且在请求发生时刻满足：1<=请求楼层<电梯楼层，捎带
									if(flags[j]==0 && j!=i && this.req_list.get(j).get_InOut()=="ER" && this.req_list.get(j).get_location()<this.floor-(this.req_list.get(j).get_time()-this.time)*2 && this.req_list.get(j).get_location()>=1 )
									{
										if(this.req_list.get(j).get_location() < this.location)
										{
											flags[j] = 2;
											String OutStrF = String.format("ER,%d,%d",this.req_list.get(j).get_location(),this.req_list.get(j).get_time());
											//this.screen_out.add(String.format("[%s]/(%d,UP,%.1f)", OutStrF,this.location,this.TIME));
											vice_req_queue[plus_pass_cnt].changeValueQueue(this.req_list.get(j).get_InOut(), this.req_list.get(j).get_direct(), this.TIME+(double)(this.req_list.get(j).get_location()-this.floor)/2, this.req_list.get(j).get_location(),OutStrF);	
											this.TIME+=1;
											plus_pass_cnt+=1;
											time_plan+=1;
										}
										else  //当主请求执行完成之后，这条捎带请求还没有执行完成
										{
											flags[j]=4;
										}
									}
								}
								else 
									break;
							}
							//执行所有能够在主请求之前执行的捎带
							vice_req_queue = bubbleSort(vice_req_queue,plus_pass_cnt);
							for(int j=0;j<plus_pass_cnt;j++)
							{
								this.screen_out.add(String.format("[%s]/(%d,DOWN,%.1f)", vice_req_queue[j].get_printstr(),vice_req_queue[j].get_location(),(double)vice_req_queue[j].get_exetime()));
							}		
							//执行主请求
							time_sub =  (double) (this.floor - this.location)/2;
							if(this.TIME < this.time)
								this.TIME = this.time + time_sub + plus_pass_cnt;
							else 
								this.TIME = this.TIME + time_sub;
							this.screen_out.add(String.format("[%s]/(%d,DOWN,%.1f)", OutStr,this.location,this.TIME));
							flags[i]=1;
							this.TIME += 1;
							this.floor = this.location;
														
						}
						//将可能未完成的请求作为主请求，来捎带其他请求
						for(int j=0;j<this.req_list.size();j++)
							if(flags[j]==4) //从所有请求的开头开始扫描，遇到的第一个没有执行的捎带请求，执行它，并且“销毁”后面所有没有执行的捎带请求
							{
								flags[j]=0;
								i=j;
								for(int m=0;m < this.req_list.size();m++)
								{
									if(flags[m]==4)
										flags[m]=0;
								}
								break;
							}	
						//添加指令到同质请求判断队列
						this.floors[this.location].get_info(this.InOut,this.direct,this.TIME);
						
					}
					else if(this.InOut=="ER") //主指令是ER
					{
						//判断是否是同质请求
						if(this.floors[this.location].check(this.InOut, this.direct, this.time) == false)
						{
							this.screen_out.add(String.format("#SAME[ER,%d,%d]",this.location,this.time ));
							flags[i]=3;
							continue;
						}
						//本条请求不和前面的请求同质
						OutStr = String.format("ER,%d,%d",this.location,this.time);
						//对于STILL类请求，不需要考虑后续指令的捎带
						if(this.location == this.floor)
						{
							this.TIME += 1; //开关门时间
							this.time += 1;
							if(this.TIME < this.time)
								this.TIME = this.time;
							this.screen_out.add(String.format("[%s]/(%d,STILL,%.1f)", OutStr,this.location,this.TIME));
							flags[i]=1;
							i++;
						}
						//其他指令需要考虑到捎带可能
						else if(this.floor < this.location) //电梯向上运行
						{
							plus_pass_cnt = 0;
							//从所有请求开始扫描，直到时间超过范围为止，找出能够捎带的为止
							//time_plan = this.time + (double)(this.location-this.floor)/2;
							//time_plan计算公式出错,并且忘记了更新楼层（在主请求执行完成之后）
							if(this.time < this.TIME)
								time_plan = this.TIME + (double) (this.location - this.floor)/2;
							else 
								time_plan = this.time + (double) (this.location - this.floor)/2;
							
							
							for(int j=0;j < this.req_list.size();j++)
							{
								//请求发生在预计时间内
								if(this.req_list.get(j).get_time() <= time_plan)
								{
									//如果副请求为FR请求(未执行),并且不是当前指令，并且在请求发生时刻满足：电梯楼层<请求楼层<=目标楼层，捎带
									if(flags[j]==0 && j!=i &&this.req_list.get(j).get_direct()=="UP" &&this.req_list.get(j).get_InOut()=="FR" && this.req_list.get(j).get_location()>(this.req_list.get(j).get_time()-this.time)*2+this.floor && this.req_list.get(j).get_location()<= this.location )
									{
										flags[j]=2;
										String OutStrF = String.format("FR,%d,%s,%d",this.req_list.get(j).get_location(),this.req_list.get(j).get_direct(),this.req_list.get(j).get_time());
										vice_req_queue[plus_pass_cnt].changeValueQueue(this.req_list.get(j).get_InOut(), this.req_list.get(j).get_direct(), this.TIME+(double)(this.req_list.get(j).get_location()-this.floor)/2, this.req_list.get(j).get_location(),OutStrF);	
										this.TIME+=1;
										plus_pass_cnt+=1;
										time_plan+=1; 
									}
									//如果副请求为ER请求(未执行)，并且在请求发生时刻满足：电梯楼层<请求楼层<=10楼，捎带
									if(flags[j]==0  &&j!=i && this.req_list.get(j).get_InOut()=="ER" && this.req_list.get(j).get_location()>(this.req_list.get(j).get_time()-this.TIME)*2+this.floor && this.req_list.get(j).get_location()<=10 )
									{
										if(this.req_list.get(j).get_location() < this.location)
										{
											flags[j] = 2;
											String OutStrF = String.format("ER,%d,%d",this.req_list.get(j).get_location(),this.req_list.get(j).get_time());
											vice_req_queue[plus_pass_cnt].changeValueQueue(this.req_list.get(j).get_InOut(), this.req_list.get(j).get_direct(), this.TIME+(double)(this.req_list.get(j).get_location()-this.floor)/2, this.req_list.get(j).get_location(),OutStrF);	
											this.TIME+=1;
											plus_pass_cnt+=1;
											time_plan+=1;
										}
										else  //当主请求执行完成之后，这条捎带请求还没有执行完成
										{
											flags[j]=4;
										}
									}
								}
								else 
									break;
							}
							//执行所有能够在主请求之前执行的捎带
							vice_req_queue = bubbleSort(vice_req_queue,plus_pass_cnt);
							for(int j=0;j<plus_pass_cnt;j++)
							{
								this.screen_out.add(String.format("[%s]/(%d,UP,%.1f)", vice_req_queue[j].get_printstr(),vice_req_queue[j].get_location(),(double)vice_req_queue[j].get_exetime()));
							}		
							//执行主请求
							time_sub =  (double) (this.location - this.floor)/2;
							if(this.TIME < this.time)
								this.TIME = this.time + time_sub + plus_pass_cnt;
							else 
								this.TIME = this.TIME + time_sub;
							this.screen_out.add(String.format("[%s]/(%d,UP,%.1f)", OutStr,this.location,this.TIME));
							flags[i]=1;
							this.TIME += 1; //开关门时间
							this.floor = this.location; //更新楼层
						}
						else if(this.floor > this.location) //电梯向下运行
						{
							plus_pass_cnt = 0;
							//从所有请求开始扫描，直到时间超过范围为止，找出能够捎带的为止
							//time_plan = this.time + (double)(this.floor-this.location)/2;
							if(this.time < this.TIME)
								time_plan = this.TIME + (double) (this.floor - this.location)/2;
							else 
								time_plan = this.time + (double) (this.floor - this.location)/2;
							
							
							for(int j=0;j < this.req_list.size();j++)
							{
								//请求发生在预计时间内
								if(this.req_list.get(j).get_time() <= time_plan)
								{
									//如果副请求为FR请求(未执行),并且不是当前指令，并且在请求发生时刻满足：目标楼层<=请求楼层<电梯楼层，捎带
									if(flags[j]==0 && j!=i && this.req_list.get(j).get_direct()=="DOWN"&&this.req_list.get(j).get_InOut()=="FR" && this.req_list.get(j).get_location()<this.floor-(this.req_list.get(j).get_time()-this.time)*2 && this.req_list.get(j).get_location()>= this.location )
									{
										flags[j]=2;
										String OutStrF = String.format("FR,%d,%s,%d",this.req_list.get(j).get_location(),this.req_list.get(j).get_direct(),this.req_list.get(j).get_time());
										vice_req_queue[plus_pass_cnt].changeValueQueue(this.req_list.get(j).get_InOut(), this.req_list.get(j).get_direct(), this.TIME+(double)(this.req_list.get(j).get_location()-this.floor)/2, this.req_list.get(j).get_location(),OutStrF);	
										this.TIME+=1;
										plus_pass_cnt+=1;
										time_plan+=1; 
									}
									//如果副请求为ER请求(未执行)，并且在请求发生时刻满足：1<=请求楼层<电梯楼层，捎带
									if(flags[j]==0 && j!=i && this.req_list.get(j).get_InOut()=="ER" && this.req_list.get(j).get_location()<this.floor-(this.req_list.get(j).get_time()-this.time)*2 && this.req_list.get(j).get_location()>=1 )
									{
										if(this.req_list.get(j).get_location() < this.location)
										{
											flags[j] = 2;
											String OutStrF = String.format("ER,%d,%d",this.req_list.get(j).get_location(),this.req_list.get(j).get_time());
											//this.screen_out.add(String.format("[%s]/(%d,UP,%.1f)", OutStrF,this.location,this.TIME));
											vice_req_queue[plus_pass_cnt].changeValueQueue(this.req_list.get(j).get_InOut(), this.req_list.get(j).get_direct(), this.TIME+(double)(this.req_list.get(j).get_location()-this.floor)/2, this.req_list.get(j).get_location(),OutStrF);	
											this.TIME+=1;
											plus_pass_cnt+=1;
											time_plan+=1;
										}
										else  //当主请求执行完成之后，这条捎带请求还没有执行完成
										{
											flags[j]=4;
										}
									}
								}
								else 
									break;
							}
							//执行所有能够在主请求之前执行的捎带
							vice_req_queue = bubbleSort(vice_req_queue,plus_pass_cnt);
							for(int j=0;j<plus_pass_cnt;j++)
							{
								this.screen_out.add(String.format("[%s]/(%d,DOWN,%.1f)", vice_req_queue[j].get_printstr(),vice_req_queue[j].get_location(),(double)vice_req_queue[j].get_exetime()));
							}		
							//执行主请求
							time_sub =  (double) (this.floor - this.location)/2;
							if(this.TIME < this.time)
								this.TIME = this.time + time_sub + plus_pass_cnt;
							else 
								this.TIME = this.TIME + time_sub;
							this.screen_out.add(String.format("[%s]/(%d,DOWN,%.1f)", OutStr,this.location,this.TIME));
							flags[i]=1;
							this.TIME += 1;
							this.floor = this.location;
						}
						//将可能未完成的请求作为主请求，来捎带其他请求
						for(int j=0;j<this.req_list.size();j++)
							if(flags[j]==4) //从所有请求的开头开始扫描，遇到的第一个没有执行的捎带请求，执行它，并且“销毁”后面所有没有执行的捎带请求
							{
								flags[j]=0;
								i=j;
								for(int m=0;m < this.req_list.size();m++)
								{
									if(flags[m]==4)
										flags[m]=0;
								}
								break;
							}	
						//添加指令到同质请求判断队列
						this.floors[this.location].get_info(this.InOut,this.direct,this.TIME);
					}
					
				}
				else i++;
			}
			//是否跳出循环
			for(int k=0; k < this.req_list.size();k++)
				if(flags[k]==0)
				{
					while_flag = true;
					continue;
				}
		}
		return true;
	}
	@Override
	public List<String> pass_String()
	{
		return this.screen_out;
	}
	
	public boolean LesserOrNot(Request r1, Request r2)
	{
		if(r1.get_location()<r2.get_location())
			return true;
		if(r1.get_location()==r2.get_location() && r1.get_time()<r2.get_time())
			return true;
		return false;
	}
	
	public Request[] bubbleSort(Request[] req_list ,int cnt) //cnt数组中元素的个数
	{
		int i,j;
		for(i=0;i<cnt;i++)
		{
			for(j=1;j<cnt-i;j++)
			{
				if(!LesserOrNot(req_list[j-1],req_list[j]))
				{
					Request temp = new Request("");
					temp.changeValueSort(req_list[j-1].get_InOut(), req_list[j-1].get_direct(), req_list[j-1].get_time(), req_list[j-1].get_location(),req_list[j-1].get_printstr());
					req_list[j-1].changeValueSort(req_list[j].get_InOut(), req_list[j].get_direct(), req_list[j].get_time(),req_list[j].get_location(), req_list[j].get_printstr());
					req_list[j].changeValueSort(temp.get_InOut(), temp.get_direct(), temp.get_time(), temp.get_location(),temp.get_printstr());
				}
			}
		}
		return req_list;
	}
	
}
