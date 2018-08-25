public class Request {
	/** overview:请求类，描述请求的信息，比如楼层、请求时间、请求方向以及对应灯是否被点亮
	 * 表示对象: long least_req_time,String str,String FrEr,String UpDown,int req_location,long req_time,int index, boolean is_light
	 * 抽象函数：AF(c) = (_least_req_time,_str,_FrEr,_UpDown,_req_location,_req_time,_index,_is_light) where
	 * 			_least_req_time == least_req_time, _str == str, _UpDown == UpDown, _req_location == req_location, _req_time == req_time,
	 * 			_index == index, _is_light == is_light
	 * 不变式：str!=null;
	 */
	private long least_req_time = 0; //保证请求的时间是非递减的
	private String str;
	private String FrEr;
	private String UpDown;
	private int req_location;
	private long req_time;
	//add the index of the request
	private int index = 0;
	//是否被点亮
	private boolean is_light = false;

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : str==null ==> \result==false
	 *  str!=null ==> \result==true
	 */
	public boolean repOK(){
		if(str == null)
			return false;
		return true;
	}

	/** @REQUIRES : str!=null;
	 * @MODIFIES : this;
	 * @EFFECTS : this.str==str;
	 */
	public Request(String str){
		this.str = str;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this;
	 * @EFFECTS : (req_time > Integer.MAX_VALUE || req_time < 0) ==> call the outToConsole function and return false;
	 * 			  (req_location < 1 || req_location > 10) ==> call the outToConsole function and return false;
	 * 			  ((req_location==1 && req_UpDown=="DOWN") || (req_location==10 && req_UpDown=="UP")) ==> call the outToConsole function and return false;
	 * 			  (req_time < least_req_time) ==> call the outToConsole function and return false;
	 * 			  else ==> get the FrEr and upDown;
	 */
	public boolean dealString(){
		//在格式正确的前提下处理字符串，注意时间非递减
		String num_pattern = "[^\\d+]+";
		String[] strs = new String[10];
		strs = this.str.split(num_pattern);
		this.req_location = Integer.parseInt(strs[1]);
		this.req_time = Long.parseLong(strs[2]);
		//是否是4字节非负整数
		if(req_time > Integer.MAX_VALUE || req_time < 0){
			OutputHandler.outToConsole(1,str);
			return false;
		}
		//楼层数字是否合法
		if(this.req_location <= 0 || this.req_location > 10){
			OutputHandler.outToConsole(1,str);
			return false;
		}
		//1楼不能向下，10楼不能向上
		if((this.req_location==1 && this.UpDown == "DOWN") || (this.req_location==10 && this.UpDown == "UP")){
			OutputHandler.outToConsole(1,str);
			return false;
		}
		//时间是否乱序
		if(req_time < least_req_time){
			OutputHandler.outToConsole(2,str);
			return false;
		}
		least_req_time = req_time;
		//构造合法请求
		if(this.str.charAt(1) == 'F'){
			this.FrEr = "FR";
			for(int i=0;i < this.str.length();i++)
				if(this.str.charAt(i) == 'D')  this.UpDown = "DOWN";
				else if (this.str.charAt(i) == 'U')  this.UpDown = "UP";
		} else if (this.str.charAt(1) == 'E'){
			this.FrEr = "ER";
		}
		return true;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.str;
	 */
	public String getString(){
		return this.str;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.UpDown;
	 */
	public String getUpDown(){
		return this.UpDown;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.FrEr;
	 */
	public String getFrEr(){
		return this.FrEr;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.req_location;
	 */
	public int getReq_location(){
		return this.req_location;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.req_time;
	 */
	public long getReqTime(){
		return this.req_time;
	}
	//同质处理尝试~~~

	/** @REQUIRES : index>0;
	 * @MODIFIES : this;
	 * @EFFECTS : this.index==index;
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.index;
	 */
	public int getIndex(){
		return  this.index;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this;
	 * @EFFECTS : this.is_light==true;
	 */
	public void setIs_light(){
		this.is_light = true;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.is_light;
	 */
	public boolean getIs_light(){
		return this.is_light;
	}
}
