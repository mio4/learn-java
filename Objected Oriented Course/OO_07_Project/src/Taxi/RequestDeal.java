package Taxi;
import java.util.*;
//在RequestDeal将处理后的请求发送到请求队列之前，需要对请求是否是同质请求进行判断
public class RequestDeal extends Thread{
	private Scanner in;
	private ArrayList<String> reqSame = new ArrayList<String>();
	private ArrayList<Long> timeSame = new ArrayList<Long>(); //save time/100
	private RequestQueue requestQueue;
	
	public RequestDeal(RequestQueue requestQueue) {
		in = new Scanner(System.in);
		this.requestQueue = requestQueue;
	}
	
	@Override
	public void run() {
		try {
			String str;
			long time;
			while(true) {
				str = in.nextLine(); //只要发现读取了下一行
				str = str.replaceAll(" ","");
				time = System.currentTimeMillis(); //获取请求发生时间
				if(CheckSame(str,time) == false) { //不是是同质请求
					Request req = new Request(str);
					req.Resolve();
					if(req.getSrcx()!=-1) { //如果是有效请求 
						requestQueue.addReq(str,req.getSrcx(), req.getSrcy(), req.getDstx(), req.getDsty(), time);
						addToSame(str,time/100);
					}
				} 	
			}
		} catch (Exception e) {}
	}
	
	public boolean CheckSame(String str, long time) { //检查是否是同质请求
		str = str.replaceAll(" ","");
		time = time / 100;
		int len = this.reqSame.size();
		for(int i=0; i < len;i++) {
			if(str.equals(reqSame.get(i)) && time==this.timeSame.get(i)) { //请求相同，并且发出的时间相同
				System.out.println("同质请求" + str);
				return true; //是同质请求
			}
		}
		return false; //不是同质请求
	}
	
	public void addToSame(String str, long time) { //将合理请求放置在List中，以便后续判断是否是同质请求
		this.reqSame.add(str);
		this.timeSame.add(time);
	}
}