
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class List {
	String []filepath = new String [1500];
	String []trigger = new String [1500];
	String []mission = new String[1500];
	Scanner in;
	int flag = 0;
	int num = 0;
	String s;
	String fs;
	public void get_list() {
		while(true) {
			in = new Scanner(System.in);
			s = in.nextLine();
			if("END".equals(s)) {
				System.out.println("输入作业完毕");
				break;
			}
			String []list = s.split(" ");
			File f = new File("D://Test" + list[1]);
			if(!f.exists()) {
				System.out.println("文件：" + list[1] + "不存在，该条监测指令无效");
				flag = 1;
			}
			else if("Modified".equals(list[2]) && "recover".equals(list[4])) {
				System.out.println("Modified触发器不能recover，该条监测指令无效");
				flag = 1;
			}
			else if("size-changed".equals(list[2]) && "recover".equals(list[4])) {
				System.out.println("size-changed触发器不能recover，该条监测指令无效");
				flag = 1;
			}
			if(list.length > 4) {
			filepath[num] = list[1];
			trigger[num] = list[2];
			mission[num] = list[4];
			num++;}
			if(flag == 1) {
				num--;
				flag = 0;
			}
			
		}
	}
}
