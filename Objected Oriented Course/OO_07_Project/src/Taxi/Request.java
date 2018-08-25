package Taxi;

import java.util.regex.Pattern;
//Request类的作用是接受一个字符串输入，然后解析字符串，返回需要的值
public class Request {
	private String str;
	private int srcx = -1;
	private int srcy = -1;
	private int dstx = -1;
	private int dsty = -1;
	
	public Request(String s) {
		this.str = s;
	}
	
	public void Resolve(){ //解析字符串
		String resolve= "[^\\d+]+";
		String checkForm= "\\[CR,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]";
		if(Pattern.matches(checkForm, str) == false) {
			System.out.println("用户请求无效,请重新输入请求");
		} else { //提取信息
			String[] strs = str.split(resolve);
			this.srcx = Integer.parseInt(strs[1]);
			this.srcy = Integer.parseInt(strs[2]);
			this.dstx = Integer.parseInt(strs[3]);
			this.dsty = Integer.parseInt(strs[4]);
			if(srcx <0 || srcy <0 || dstx<0 || dsty <0 || srcx >80 || srcy>80 || dstx > 80 || dsty>80)
			{
				System.out.println("用户请求无效,请重新输入请求");
				this.srcx = -1;
				this.srcy = -1;
				this.dstx = -1;
				this.dsty = -1;
			}
		}
	}
	public int getSrcx() {
		return this.srcx;
	}
	public int getSrcy() {
		return this.srcy;
	}
	public int getDstx() {
		return this.dstx;
	}
	public int getDsty() {
		return this.dsty;
	}
}
