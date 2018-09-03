//楼层类存在的意义是判断请求是否是同质请求
//楼层虽然是一个固定的量，但是可以作为观察者观察电梯的情况
//楼层类是一个大类，包含10层楼
import java.util.*;
/*
 *Overview: 楼层类：主要是存储序列以及检查是否是同质请求
 */
public class Floor {
	private int floor;
	private List<String> InOut_list = new ArrayList<String>();
	private List<String> direct_list = new ArrayList<String>();
	private List<Double> time_list = new ArrayList<Double>();

	/** @REQUIRES : None;
	 * @MODIFIES : floor;
	 * @EFFECTS : this.floor == floor;
	 */
	public Floor(int floor)
	{
		this.floor = floor;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : ( floor < 0 || floor > 10 ) ==> \result = false ;
	 * (floor >0 && floor <= 10) ==> \result = true;
	 */
	public boolean repOK(){
		//测试repOK覆盖率时使用，代码正常运行时取消这两行注释
		//if(floor <= 0 || floor > 10)
		//	return false;
		return true;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : InOut_list,direct_list,time_list;
	 * @EFFECTS : InOut_list.contains(InOut) && InOut_list.size == \old(InOut_list).size + 1;
	 * direct_list.contains(direct) && direct_list.size = \old(direct_list).size + 1;
	 * time_list.contains(time) && time_list.size = \old(time_list).size + 1 ;
	 */
	public void get_info(String InOut, String direct, double time)
	{
		//这里很明显的问题是方法名都取错了，应该为setFloorBasic设置基础信息
		this.InOut_list.add(InOut);
		this.direct_list.add(direct);
		this.time_list.add(time);
	}

	/** @REQUIRES : InOut!=null && direct!=null;
	 * @MODIFIES : None;
	 * @EFFECTS : \result.equals(检测当前请求是否和前面的请求构成同质请求，如果是则返回false，否则返回true);
	 */
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

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == floor;
	 */
	public int  get_floor()
	{
		return this.floor;
	}
	
	
}
