import java.util.*;
public class Elevator {
	//格式化输出需要
	private List<String> screen_out = new ArrayList<String>();
	
	//获取字符串
	public void get_String(List<String> screen_out)
	{
		this.screen_out = screen_out;
	}
	
	//toString方法：获取电梯运行状态
	@Override
	public String toString()
	{
		String str = null;
		for(String s : this.screen_out)
			System.out.println(s);
		return str;
	}
}
