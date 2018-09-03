public class OutputHandler {
	/** overview:输出处理类，有关于无效请求的输出方法以及正确执行指令的输出处理
	 * 表示对象: None;
	 * 抽象函数：None;
	 * 不变式：true;
	 */

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result==true;
	 */
	public boolean repOK(){
		return true;
	}

	/** @REQUIRES : type==1 || type==2 || type==3 || type==4;
	 * @MODIFIES : None;
	 * @EFFECTS : \request.equals(when type==1 or type == 2 ,print the information of valid request to the console
	 *  when type==3 or type==4, print the information of same request to the console)
	 */
	public static void outToConsole(int type,String str){ //异常输入处理
		if(type == 1){ //格式无效
			System.out.print("INVALID");
			System.out.println("["+str+"]");
		}
		else if (type == 2){ //时间乱序
			System.out.println("#时间乱序，无效请求:");
			System.out.print("INVALID");
			System.out.println("["+str+"]");
		}
		else if (type == 3){ //FR同质请求
			String num_pattern = "[^\\d+]+";
			String[] strs = str.split(num_pattern);
			int req_location = Integer.parseInt(strs[1]);
			long req_time = Long.parseLong(strs[2]);
			String UpDown = "";
			for(int i=0;i < str.length();i++)
				if(str.charAt(i) == 'D')  UpDown = "DOWN";
				else if (str.charAt(i) == 'U')  UpDown = "UP";
			System.out.println(String.format("#SAME[FR,%d,%s,%d]",req_location,UpDown,req_time));
		}
		else if(type == 4){ //ER同质请求
			String num_pattern = "[^\\d+]+";
			String[] strs = str.split(num_pattern);
			int req_location = Integer.parseInt(strs[1]);
			long req_time = Long.parseLong(strs[2]);
			System.out.println(String.format("#SAME[ER,%d,%d]",req_location,req_time));
		}
	}

	/** @REQUIRES : cur_floor >= 1 && cur_floor <= 10 && (UpDown=="STILL" || UpDown=="UP" || UpDown=="DOWN");
	 * @MODIFIES : None;
	 * @EFFECTS : \request.equals(print the valid information of request to the console)
	 */
	public static void outToConsole2(String str,int cur_floor, String UpDown, double cur_time){ //正常指令的输出处理
		String num_pattern = "[^\\d+]+";
		String[] strs = str.split(num_pattern);
		int req_location = Integer.parseInt(strs[1]);
		long req_time = Long.parseLong(strs[2]);
		String req_updown = null;
		if(str.charAt(1)=='E') {
			if(UpDown == "STILL")
				System.out.println(String.format("[ER,%d,%d]/(%d,%s,%.1f)", req_location, req_time, cur_floor, UpDown, cur_time+1.0));
			else
				System.out.println(String.format("[ER,%d,%d]/(%d,%s,%.1f)", req_location, req_time, cur_floor, UpDown, cur_time));
		} else if(str.charAt(1) == 'F'){
			for(int i=0;i < str.length();i++)
				if(str.charAt(i) == 'D')  req_updown = "DOWN";
				else if (str.charAt(i) == 'U')  req_updown = "UP";
			if(UpDown == "STILL")
				System.out.println(String.format("[FR,%d,%s,%d]/(%d,%s,%.1f)", req_location,req_updown,req_time, cur_floor, UpDown, cur_time+1));
			else
				System.out.println(String.format("[FR,%d,%s,%d]/(%d,%s,%.1f)", req_location,req_updown,req_time, cur_floor, UpDown, cur_time));
		}
	}
}
