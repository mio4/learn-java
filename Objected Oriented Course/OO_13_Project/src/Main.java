import java.util.*;
/*
 *Overview: 主函数类：对于输入的序列进行处理以及构造电梯、调度器对象
 */
public class Main {
	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == true;
	 */
	public boolean repOK(){
		return true;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result.equals(作为主函数，读入电梯请求，处理电梯请求);
	 */
	public static void main(String[] args)
	{
		try
		{
			int str_cnt = 0; 
			Request req;
			boolean zero_time = false; //判断第一条指令请求时间是否为为零
			boolean first_req_flag = false; //判断第一条指令是否为指定请求
			Scanner in = new Scanner(System.in);
			List<Request> req_list = new ArrayList<Request>();
			String str = in.nextLine();
			str = str.replaceAll(" ","");
			while(!str.equals("RUN") && str_cnt <= 100)
			{			
				//判断第一条请求是否是指定请求
				if(!str.equals("(FR,1,UP,0)") && first_req_flag==false)
				{
					System.out.print("INVALID");
					System.out.println("["+str+"]");
					str = in.nextLine();
					str = str.replaceAll(" ", "");
					continue;
				}
				else 
					first_req_flag = true;
				
				req = new Request(str);
				req.RidSpace();			
				//判断基本格式是否正确，正确的请求添加到请求队列中
				if(req.FormatJudge()==true && req.get_info()==true)
					req_list.add(req);
				else 
				{
					str = in.nextLine();
					str = str.replaceAll(" ", "");
					continue;
				}
				//如果输入的请求格式正确，再判断起始时间是否为零
				if(zero_time==false && req.get_time() != 0)
				{
					System.out.print("INVALID");
					System.out.println("["+str+"]");
					req_list.remove(req_list.size()-1); 
				}
				else if (zero_time == false && req.get_time()==0)
					zero_time = true;
				//判断输入的请求时间上是否非减
				if(req_list.size() >= 2 && req.get_time() < req_list.get(req_list.size()-2).get_time())
				{
					System.out.print("INVALID");
					System.out.println("["+str+"]");
					req_list.remove(req_list.size()-1); 
				}
				//100条请求限制，下一条请求读取
				str_cnt += 1; 
				if(str_cnt==100 && str!="RUN")
					System.exit(0);
				str = in.nextLine();
				str = str.replaceAll(" ", "");
			}

			ScheSon scheson = new ScheSon();
			scheson.initial_floor();
			scheson.get_req_list(req_list);
			scheson.command(scheson.schedule());
			Elevator elevator = new Elevator();
			elevator.get_String(scheson.pass_String());
			elevator.toString();
						
			in.close();
		}
		catch(Exception e) 
		{
			System.exit(0);
		}
	}
}
