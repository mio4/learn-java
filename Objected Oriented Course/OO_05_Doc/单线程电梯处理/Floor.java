//楼层类存在的意义是判断请求是否是同质请求
//楼层虽然是一个固定的量，但是可以作为观察者观察电梯的情况
//楼层类是一个大类，包含10层楼
import java.util.*;
public class Floor {
	private int floor;
	private List<String> InOut_list = new ArrayList<String>();
	private List<String> direct_list = new ArrayList<String>();
	private List<Double> time_list = new ArrayList<Double>();
	
	public Floor(int floor)
	{
		this.floor = floor;
	}
	
	public void get_info(String InOut, String direct, double time)
	{
		this.InOut_list.add(InOut);
		this.direct_list.add(direct);
		this.time_list.add(time);
	}
	
	public boolean check(String InOut, String direct, long time)
	{
		for(int i=0 ;i < this.time_list.size(); i++)
		{
			//ER 上一条是STILL 时间差满足 楼层相同
			if(InOut.equals("ER") && this.InOut_list.get(i).equals("ER") && this.direct_list.get(i)!=null && this.direct_list.get(i).equals("STILL") && time<=this.time_list.get(i))
				return false;
			if(InOut == this.InOut_list.get(i) && direct == this.direct_list.get(i))
			{
				if(time <= this.time_list.get(i)+1)
					return false;
			}
		}
		return true;
	}
	
	public int  get_floor()
	{
		return this.floor;
	}
	
	
}
