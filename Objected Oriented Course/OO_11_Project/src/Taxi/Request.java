package Taxi;

import java.util.regex.Pattern;
//Request类的作用是接受一个字符串输入，然后解析字符串，返回需要的值
public class Request {
	/*Overview : 将用户输入请求解析出出发地点以及目标地点
	 */
	private String str;
	private int srcx = -1;
	private int srcy = -1;
	private int dstx = -1;
	private int dsty = -1;

	/** @REQUIRES : None;
	 * @MODIFIES : this.str;
	 * @EFFECTS : str == s;
	 */
	public Request(String s) {

		this.str = s;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @Effects : \result == invariant(this);
	 */
	public boolean repOK(){

		if(srcx < 0 || srcy < 0 ||dstx < 0 || dsty < 0)
			return false;
		return true;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this.srcx,this.srcy,this.dstx,this.dsty;
	 * @EFFECTS : \request.equals(resolve the request to get the start coordinate and the destination coordinate);
	 */
	public void passReqResolve(){ //解析字符串

		String resolve= "[^\\d+]+";
		String checkForm= "\\[CR,\\(\\d{1,2},\\d{1,2}\\),\\(\\d{1,2},\\d{1,2}\\)\\]";
		if(!Pattern.matches(checkForm, str)) {
			System.out.println("用户请求无效,请重新输入请求");
		} else { //提取信息
			String[] strs = str.split(resolve);
			this.srcx = Integer.parseInt(strs[1]);
			this.srcy = Integer.parseInt(strs[2]);
			this.dstx = Integer.parseInt(strs[3]);
			this.dsty = Integer.parseInt(strs[4]);
			//格式检查：坐标超出地图范围，出发地和目的地相同
			if(srcx <0 || srcy <0 || dstx<0 || dsty <0 || srcx >=80 || srcy>=80 || dstx >=80 || dsty>=80 || (srcx==dstx && srcy==dsty))
			{
				System.out.println("用户请求无效,请重新输入请求");
				this.srcx = -1;
				this.srcy = -1;
				this.dstx = -1;
				this.dsty = -1;
			}
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == srcx;
	 */
	public int getSrcx() {
		return this.srcx;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == srcy;
	 */
	public int getSrcy() {
		return this.srcy;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == dstx;
	 */
	public int getDstx() {
		return this.dstx;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == dsty;
	 */
	public int getDsty() {
		return this.dsty;
	}
}
