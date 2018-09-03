import java.util.*;
/*
 *Overview: 电梯类：主要是用于存储和输出请求
 */
public class Elevator {
	//格式化输出需要
	private List<String> screen_out = new ArrayList<String>();

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == invariant(this);
	 */
	public boolean repOK(){
		return true;
	}

	/** @REQUIRES : screen_out!=null;
	 * @MODIFIES : this.screen_out;
	 * @EFFECTS : this.screen_out == screen_out && \result = true;
	 */
	public boolean get_String(List<String> screen_out)
	{
		//这个方法命名明显有问题，可以改为setScreenOut
		this.screen_out = screen_out;
		return true;
	}
	
	//toString方法：获取电梯运行状态
	@Override
	/** @REQUIRES : screen_out!=null;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == null; 
	 */
	public String toString()
	{
		//toString函数是为了自定义输出对象，这里显然是不恰当使用，可以改为printScreenOut函数
		String str = null;
		for(String s : this.screen_out)
			System.out.println(s);
		return str;
	}
}
