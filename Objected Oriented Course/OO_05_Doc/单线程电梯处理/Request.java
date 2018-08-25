//输入的多行字符串，每行都处理成一个Request对象,获取输入的所有的信息，以及判断输入是否合法
import java.util.regex.Pattern; 
public class Request {
	private String str;
	private String InOut; //电梯内|电梯外
	private String direct; //向上|向下
	private long time; //请求时间
	private double exetime; //运行时间
	private int location; //当前位置
	
	private String printstr;
	
	public Request(String str)
	{
		this.str = str;
	}
	
	//changeValue_sort用于ScheSon中的冒泡排序，交换两个Request类的4个基本值，方便打印
	public void changeValueSort(String InOut, String direct, long time, int location,String printstr)
	{
		this.InOut = InOut;
		this.direct = direct;
		this.time = time;
		this.location = location;
		this.printstr = printstr;
	}
	
	//changeValueQueue用于临时数组交换变量值
	public void changeValueQueue(String InOut, String direct, double time, int location,String printstr)
	{
		this.InOut = InOut;
		this.direct = direct;
		this.exetime = time;
		this.location = location;
		this.printstr = printstr;
	}
	
	//去掉字符串的空格
	public void RidSpace()
	{
		this.str = this.str.replaceAll(" ", "");
	}
	
	public boolean FormatJudge()
	{
		boolean flag1;
		boolean flag2;
		//判断输入的字符串是否含有非法字符
		String pattern1 = "[\\(\\)FREDOWNUP,0-9+]+";
		flag1 = Pattern.matches(pattern1,this.str);
		if(flag1 == false)
		{
			System.out.print("INVALID");
			System.out.println("["+str+"]");
			return false;
		}
		//判断输入的字符串是否是标准格式
		String pattern2 = "\\(FR,[+]?\\d{1,10},\\b(DOWN|UP)\\b,\\d{1,}\\)|\\(ER,[+]?\\d{1,10},\\d{1,}\\)";
		flag2 = Pattern.matches(pattern2, str);
		if(flag2 == false)
		{
			System.out.print("INVALID");
			System.out.println("["+str+"]");
			return false;
		}
			
		return true;
	}
	
	//获取输入字符串的信息
	public boolean get_info()
	{
		//已经满足(FR,3,DOWN,0)和(ER,1,2)这样格式的前提下，提取信息同时判断是否是有效输入
		//判断楼层是否合法以及时间是否满足4个字节信息
		String pattern1 = "[^\\d+]+";
		String[] nums = new String[100];
		nums = this.str.split(pattern1);
		if(nums[2].length()>10)
		{
			System.out.print("INVALID");
			System.out.println("["+str+"]");
			return false;
		}
			
		this.location = Integer.parseInt(nums[1]); //第二个是楼层
		this.time = Long.parseLong(nums[2]); //第三个是命令时间
		
		//楼层数字非法
		if(this.location <= 0 || this.location > 10)
		{
			System.out.print("INVALID");
			System.out.println("["+str+"]");
			return false;
		}
		//判断时间是否满足int类型
		if(this.time< 0 || this.time > Long.parseLong("4294967295"))
		{
			System.out.print("INVALID");
			System.out.println("["+str+"]");
			return false;
		}
		
		//提取信息,可能合法
		if(this.str.charAt(1) == 'F')
		{
			this.InOut = "FR"; //电梯外命令
			for(int i=0;i < this.str.length();i++)
				if(this.str.charAt(i) == 'D')  this.direct = "DOWN";
				else if (this.str.charAt(i) == 'U')  this.direct = "UP";
		}
		else if(this.str.charAt(1)=='E')
		{
			this.InOut = "ER"; //电梯内命令
		}	
		
		//1楼不能向下，10楼不能向上
		if((this.location==1 && this.direct=="DOWN") || (this.location==10 && this.direct=="UP"))
		{
			System.out.print("INVALID");
			System.out.println("["+str+"]");
			return false;
		}
		return true;
	}
	//返回成员变量
	public String get_InOut()
	{
		return this.InOut;
	}
	public String get_direct()
	{
		return this.direct;
	}
	public long get_time()
	{
		return this.time;
	}
	public double get_exetime()
	{
		return this.exetime;
	}
	public int get_location()
	{
		return this.location;
	}
	public String get_printstr()
	{
		return this.printstr;
	}
}
