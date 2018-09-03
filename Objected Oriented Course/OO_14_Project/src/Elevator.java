public class Elevator {
	/** overview:电梯类，记录了电梯的灯属性，并且拥有更改和查看相应灯的方法
	 * 表示对象：int[] er_light, int[][] fr_light, String[] er_req, String[][] fr_req, int[] er_index, int[][] fr_index
	 * 抽象函数：AF(c) = (_er_light,_fr_light,_er_req,_fr_req,_er_index,_fr_index)
	 * 			where _er_light==c.er_light,_fr_light==c.fr_light,_er_req==c.er_req,_fr_req==c.fr_req,_er_index==c.er_index,_fr_index==fr_index
	 * 不变式：true
	 */
	final static int UP = 0;
	final static int DOWN = 1;
	private int[] er_light = new int[11]; //电梯内的灯
	private int[][] fr_light = new int[11][2]; //电梯外的灯
	private String[] er_req = new String[11]; //电梯内的请求
	private String[][] fr_req = new String[11][2]; //电梯外的请求
	private int[] er_index = new int[11]; //用于相同时间时按照顺序输出
	private int[][] fr_index = new int[11][2]; //...

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result==true;
	 *
	 */
	public boolean repOK(){
		return true;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this;
	 * @EFFECTS : ( \req.getFrEr()=="ER" && is_same ) ==> OutputHandler.outToConsole(4,req.getString())
	 *			( \req.getFrEr()=="ER" && !is_same ) ==> (req.setIs_light)
	 *		  	( \req.getFrEr()=="FR" && is_same ) ==> OutputHandler.outToConsole(3,req.getString())
	 *		    ( \req.getFrEr()=="FR" && !is_same ) ==> (req.setIs_light)
	 */
	public void lightOn(Request req,int index){ //:亮灯
		//判断这条请求是否已经亮灯
		if(req.getIs_light())
			return;
		boolean is_same = false;
		if(req.getFrEr() == "ER"){
			if(this.er_light[req.getReq_location()] == 1){ //已经亮灯，同质请求
				OutputHandler.outToConsole(4,req.getString());
				Scheduler.req_is_used[req.getIndex()] =  true;
				is_same = true;
			}
			//如果不是同质请求，才将请求附加到灯上
			if(!is_same) {
				this.er_light[req.getReq_location()] = 1;
				er_req[req.getReq_location()] = req.getString();
				er_index[req.getReq_location()] = index;
				req.setIs_light();
			}
		} else if(req.getFrEr() == "FR"){
			if(req.getUpDown() == "UP" && this.fr_light[req.getReq_location()][0] == 1){//已经亮灯，同质请求
				OutputHandler.outToConsole(3, req.getString());
				Scheduler.req_is_used[req.getIndex()] =  true;
				is_same = true;
			} else if(req.getUpDown() == "DOWN" && this.fr_light[req.getReq_location()][1] == 1){//已经亮灯，同质请求
				OutputHandler.outToConsole(3, req.getString());
				Scheduler.req_is_used[req.getIndex()] =  true;
				is_same = true;
			}
			if(req.getUpDown() == "UP") {
				//不是同质请求，附加请求到对应灯上，方便输出
				if(!is_same) {
					this.fr_light[req.getReq_location()][UP] = 1;
					fr_req[req.getReq_location()][UP] = req.getString();
					fr_index[req.getReq_location()][UP] = index;
					req.setIs_light();
				}
			}
			else if(req.getUpDown() == "DOWN") {
				if(!is_same) {
					this.fr_light[req.getReq_location()][DOWN] = 1;
					fr_req[req.getReq_location()][DOWN] = req.getString();
					fr_index[req.getReq_location()][DOWN] = index;
					req.setIs_light();
				}
			}
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this;
	 * @EFFECTS : type == 1 ==> (off the er light);
	 * type == 2 ==> (off the fr light)
	 */
	public void lightOff(int type, int cur_floor, int UpOrDown){
		if(type == 1){
			this.er_light[cur_floor] = 0;
		} else if(type == 2){
			if(UpOrDown == UP) {
				this.fr_light[cur_floor][UP] = 0;
			} else if(UpOrDown == DOWN){
				this.fr_light[cur_floor][DOWN] = 0;
			}
		}
 	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.er_light;
	 */
	public int[] getEr_light(){
		return this.er_light;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.fr_light;
	 */
	public int[][] getFr_light(){
		return this.fr_light;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.er_req;
	 */
	public String[] getEr_req(){
		return this.er_req;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.fr_req;
	 */
	public String[][] getFr_req(){
		return this.fr_req;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.er_index;
	 */
	public int[] getEr_index(){
		return this.er_index;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == this.fr_index;
	 */
	public int[][] getFr_index(){
		return this.fr_index;
	}

}
