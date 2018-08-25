package Taxi;

import java.util.ArrayList;
import java.awt.Point;

//VIP出租车接单的信息
public class VIPRequest {
	/*Overview : 存储请求相关信息，提供给迭代器使用
	 */
	private String req;
	private long reqTime;
	private int srcx;
	private int srcy;
	private int dstx;
	private int dsty;
	//出租车接到乘客时的位置
	private int posx;
	private int posy;
	private ArrayList<String> path = new ArrayList<String>();


	public void setReq(String req,int srcx, int srcy, int dstx, int dsty,long time){
		/** @REQUIRES : req!=null && 0=<srcx<=79 && 0=<srcy<=79 && 0=<dstx<=79 &&0=<dsty<=79;
		 * @MODIFIES : req,srcx,srcy,dstx,dsty;
		 * @EFFECTS : this.req==req, this.srcx==srcx, this.srcy==srcy, this.dstx==dstx,this.dsty==dsty;
		 */
		this.req = req;
		this.srcx = srcx;
		this.srcy = srcy;
		this.dstx = dstx;
		this.dsty = dsty;
		this.reqTime = time;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == invariant(this);
	 */
	public boolean repOK(){
		if(req==null)
			return false;
		return true;
	}

	public void setTaxiCor(int x, int y){
		/** @REQUIRES : None;
		 * @MODIFIES : this.posx,this.posy;
		 * @EFFECTS : this.posx==x, this.posy==y;
		 */
		this.posx = x;
		this.posy = y;
	}


	public void addPath(int x, int y){
		/** @REQUIRES : 0=<x<=79 && y<=79;
		 * @MODIFIES : path;
		 * @EFFECTS : None;
		 */
		this.path.add("(" + x + "," + y + ")");
	}


	public long getReqTime(){
		/** @REQUIRES : None;
		 * @MODIFIES : None;
		 * @EFFECTS : \result = this.reqTime;
		 */
		return this.reqTime;
	}


	public Point getSrc(){
		/** @REQUIRES : None;
		 * @MODIFIES : None;
		 * @EFFECTS : \result = new Point(srcx,srcy);
		 */
		return new Point(srcx,srcy);
	}


	public Point getDst(){
		/** @REQUIRES : None;
		 * @MODIFIES : None;
		 * @EFFECTS : \result = new Point(dstx,dsty);
		 */
		return new Point(dstx,dsty);
	}


	public ArrayList<String> getPath(){
		/** @REQUIRES : None;
		 * @MODIFIES : None;
		 * @EFFECTS : \result = this.path;
		 */
		return this.path;
	}

}
