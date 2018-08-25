import java.util.ArrayList;
import java.util.Scanner;

//获取输入
public class InputHandler {	
	private String str;
	private String[] strs;
	private int cnt = 0; //有效请求条数
	private ArrayList<String> obje = new ArrayList<String>(); //监控对象
	private ArrayList<String> trig = new ArrayList<String>(); //触发器
	private ArrayList<String> task = new ArrayList<String>(); //执行任务
	private final static String[] strs1 = {"renamed", "modified", "path-changed", "size-changed"}; 
	private final static String[] strs2 = {"record-summary", "record-detail", "recover"};
	
	public InputHandler() {
		
	}
	
	public boolean work() {
		Scanner sc = new Scanner(System.in);
		while(true) {
			//是否超过10个线程
			if(cnt > 10) { 
				System.out.println("More than 10 monitors");
				System.exit(0);
			}
			str = sc.nextLine();
			//是否结束读入
			if(str.equals("run")) {
				System.out.println("Input Finished");
				sc.close();
				return true;
			}
			strs = str.split(" ");
			//检查格式0
			SafeFile f = new SafeFile(strs[1]);
			if(!f.exists()) {
				System.out.println("Invalid Request");
				continue;
			}			
			//格式检查1
			if(!strs[0].equals("IF") || !strs[3].equals("THEN")) {
				System.out.println("Invalid Request");
				continue;
			}
			//格式检查2
			boolean flag = false;
			for(String s : strs1) {
				if(s.equals(strs[2])) flag = true;
			}
			if(flag == false) {
				System.out.println("Invalid Request");
				continue;
			}
			flag = false;
			for(String s : strs2) {
				if(s.equals(strs[4])) flag = true;
			}
			if(flag == false) {
				System.out.println("Invalid Request");
				continue;
			}
			//检查格式3
			if(strs[4].equals("recover") && !(strs[2].equals("renamed") || strs[2].equals("path-changed"))) {
				System.out.println("Invalid Request");
				continue;
			}
			//提取信息
			obje.add(strs[1]);
			trig.add(strs[2]);
			task.add(strs[4]);
			cnt++;
			System.out.println("Request OK");
		}
	}
	
	public String getObje(int i) {
		return obje.get(i);
	}
	public String getTrig(int i) {
		return trig.get(i);
	}
	public String getTask(int i) {
		return task.get(i);
	}
	public int getCnt() {
		return cnt;
	}
}
