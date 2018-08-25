import java.util.ArrayList;
import java.util.List;

//Scheduler类判断请求是否合法，并且当做Queue类和Elevator类的桥梁
public class Scheduler{
	//电梯自身属性
	private int floor = 1;//电梯运行到的楼层
	private double TIME = 0; //系统时间，初始为零
	//输入变量属性
	private String InOut;
	private String direct;
	private long time;
	private int location;
	//格式化输出需要
	private List<String> screen_out = new ArrayList<String>();
	//楼层判定
	private Floor[] floors = new Floor[11];	
	
	public void get_info(String InOut,String direct,long time, int location)
	{
		this.InOut = InOut;
		this.direct = direct;
		this.time = time;
		this.location = location;
	}
	public List<String> pass_String()
	{
		return this.screen_out;
	}
	
	public void initial_floor()
	{
		for(int i=1 ; i <= 10; i++)
			this.floors[i] = new Floor(i);
	}
	
	//如果请求是可调度的，将请求传送到Elevator类
	public boolean command(boolean judge)
	{
		if (judge == true)
			return true;
		else 
			return false;
	}
	
	//判断请求是否是可调度的
	public boolean schedule()
	{
		String str;
		String outstr; //输出原有的请求
		double time_sub;
		//楼层里的请求
		if(this.InOut == "FR")
		{
			outstr = String.format("FR,%d,%s,%d",this.location,this.direct,this.time);
			if(this.floor == this.location) //同楼层请求
			{
				//判断是否是同质请求
				if(this.floors[this.location].check(this.InOut, this.direct, this.time) == false)
				{
					this.screen_out.add(String.format("SAME [FR,%d,%s,%d]",this.location,this.direct,this.time ));
					return false;
				}
				this.TIME += 1; //开关门时间
				this.time += 1;
				if(this.TIME < this.time)
					this.TIME = this.time;
				this.screen_out.add(String.format("[%s]/(%d,STILL,%.1f)", outstr,this.location,this.TIME));
				//同质请求
				this.floors[this.location].get_info(this.InOut,this.direct,this.TIME);
				return true;	
			}
			else if(this.floor < this.location)
			{
				//判断是否是同质请求
				if(this.floors[this.location].check(this.InOut, this.direct, this.time) == false)
				{
					this.screen_out.add(String.format("SAME [FR,%d,%s,%d]",this.location,this.direct,this.time ));
					return false;
				}
				time_sub =  (double) (this.location - this.floor)/2;
				if(this.TIME < this.time)
					this.TIME = this.time + time_sub;
				else 
					this.TIME = this.TIME + time_sub;

				this.screen_out.add(String.format("[%s]/(%d,UP,%.1f)", outstr,this.location,this.TIME));
				//同质请求
				this.floors[this.location].get_info(this.InOut,this.direct,this.TIME);
				//处理完毕
				this.TIME += 1; //开关门时间
				this.floor = this.location;
				return true;
			}
			else if(this.floor > this.location)
			{
				//判断是否是同质请求
				if(this.floors[this.location].check(this.InOut, this.direct, this.time) == false)
				{
					this.screen_out.add(String.format("SAME [FR,%d,%s,%d]",this.location,this.direct,this.time ));
					return false;
				}
				time_sub =  (double) (this.floor - this.location)/2;
				if(this.TIME < this.time)
					this.TIME = this.time + time_sub;
				else 
					this.TIME = this.TIME + time_sub;
				this.screen_out.add(String.format("[%s]/(%d,DOWN,%.1f)",outstr, this.location,this.TIME));
				//同质请求
				this.floors[this.location].get_info(this.InOut,this.direct,this.TIME);
				//处理完毕
				this.TIME += 1; //开关门时间
				this.floor = this.location;
				return true;
			}
		}
		
		//电梯里的请求
		else if (this.InOut == "ER")
		{
			outstr = String.format("ER,%d,%d",this.location,this.time);
			if(this.location == this.floor) //同楼层请求
			{
				//判断是否是同质请求
				if(this.floors[this.location].check(this.InOut, this.direct, this.time) == false)
				{
					this.screen_out.add(String.format("SAME [ER,%d,%d]",this.location,this.time ));
					return false;
				}
				this.TIME += 1; //开关门时间
				this.time += 1;
				if(this.TIME < this.time)
					this.TIME = this.time;
				this.screen_out.add(String.format("[%s]/(%d,STILL,%.1f)", outstr,this.location,this.TIME));
				//同质请求
				this.floors[this.location].get_info(this.InOut,"STILL",this.TIME);
				return true;	
			}
			else if(this.floor < this.location)
			{
				//判断是否是同质请求
				if(this.floors[this.location].check(this.InOut, this.direct, this.time) == false)
				{
					this.screen_out.add(String.format("SAME [ER,%d,%d]",this.location,this.time ));
					return false;
				}
				time_sub =  (double) (this.location - this.floor)/2;
				if(this.TIME < this.time)
					this.TIME = this.time + time_sub;
				else 
					this.TIME = this.TIME + time_sub;
				this.screen_out.add(String.format("[%s]/(%d,UP,%.1f)",outstr, this.location,this.TIME));
				//同质请求
				this.floors[this.location].get_info(this.InOut,this.direct,this.TIME);
				//处理完毕
				this.TIME += 1; //开关门时间
				this.floor = this.location;
				return true;	
			}
			else if (this.floor > this.location)
			{
				//判断是否是同质请求
				if(this.floors[this.location].check(this.InOut,this.direct, this.time) == false)
				{
					this.screen_out.add(String.format("SAME [ER,%d,%d]",this.location,this.time ));
					return false;
				}
				time_sub =  (double) (this.floor - this.location)/2;
				if(this.TIME < this.time)
					this.TIME = this.time + time_sub;
				else 
					this.TIME = this.TIME + time_sub;
				this.screen_out.add(String.format("[%s]/(%d,DOWN,%.1f)",outstr, this.location,this.TIME));
				//同质请求
				this.floors[this.location].get_info(this.InOut,this.direct,this.TIME);
				//处理完毕
				this.TIME += 1; //开关门时间
				this.floor = this.location;
				return true;	
			}
				
		}	
		return true;
	}	
}
