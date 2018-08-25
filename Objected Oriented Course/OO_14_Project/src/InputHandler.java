import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputHandler {
	/** overview:输入处理类，获取用户的输入，并且进行格式检查，有保存和获取合法指令的方法
	 * 表示对象: Scanner sc, ArrayList req_list;
	 * 抽象函数：AF(c) = (_sc,_req_list)
	 * 			where _sc==sc,_req_list==req_list
	 * 不变式：sc!=null && req_list!=null
	 */
	private Scanner sc = new Scanner(System.in);
	private ArrayList<Request> req_list = new ArrayList<Request>();

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result==(sc!=null && req_list!=null)
	 */
	public boolean repOK(){
		if(sc==null || req_list==null)
			return false;
		return true;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this;
	 * @EFFECTS : (str=="RUN" || line_cnt >= 100) ==> end the Scanner
	 * 			  (str!="RUN" && line_cnt < 100) ==> (
	 * 			  (first request != "(FR,1,UP,0)") ==> continue the loop
	 * 			  (formatJudge(str) == false) ==> continue the loop
	 * 			  add the req to the req_list
	 * 			  )
	 */
	public void getInput(){
		try {
			String str = (sc.nextLine()).replace(" ","");
			int line_cnt = 0;
			boolean is_first_req = true; //第一条指令判断
			while(!str.equals("RUN") && line_cnt < 100){
				//判断第一条指令是否是(FR,1,UP,0)，第一条请求保证了起始时间必须是0
				if(!str.equals("(FR,1,UP,0)") && is_first_req) {
					OutputHandler.outToConsole(1, str);
					str = (sc.nextLine()).replace(" ","");
					continue;
				}
				is_first_req = false;
				//判断指令基本格式是否正确
				if(!formatJudge(str)){
					OutputHandler.outToConsole(1,str);
					str = (sc.nextLine()).replace(" ","");
					continue;
				}
				Request req = new Request(str);
				if(req.dealString())
					this.req_list.add(req);
				line_cnt++;
				str = (sc.nextLine()).replace(" ","");
				continue;
			}
			//超过一百条请求，不接受新的请求读入
			sc.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : (if string contains illegal char, return false);
	 * 			  (if string doesn't match the standard ER or FR request, return true)
	 * 			  (else return true)
	 */
	public boolean formatJudge(String str){
		//判断输入的字符串是否含有非法字符
		String legal_pattern = "[\\(\\)FREDOWNUP,0-9+]+";
		boolean legal_flag = Pattern.matches(legal_pattern,str);
		if(!legal_flag)
			return false;
		//判断输入的字符串是否是标准格式
		String format_pattern = "\\(FR,[+]?\\d{1,10},\\b(DOWN|UP)\\b,\\d{1,}\\)|\\(ER,[+]?\\d{1,10},\\d{1,}\\)";
		boolean format_flag = Pattern.matches(format_pattern,str);
		return format_flag;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.req_list;
	 */
	public ArrayList<Request> getRequest(){
		return this.req_list;
	}
}
