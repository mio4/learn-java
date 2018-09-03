package oo_13; 
class Request {
	/**@OVERVIEW:请求类，用于存储电梯内外请求的关键参数
	 */
	private String forInput;
	private boolean in;
	private long time;
	
	private int floorLoc;
	private boolean up;
	
	private int toFloor;
	public boolean repOK() {
		return true;
	}
	public void inOrder(int i,Long long1,String s) {
		/**@REQUIRES:0<i<=10
		 * @MODIFIES:this.forInput, this.in,this.toFloor,this.time
		 * @EFFECTS: this.forInput.equals(s) && this.in && this.toFloor==i && this.time=long1;
		 */
		this.forInput=s;
		this.in=true;
		this.toFloor=i;
		this.time=long1;
	}
	public void outOrder(int i,String s,Long long1,String s1) {
		/**@REQUIRES:0<i<=10 && (s.equals("UP")||s.equals("DOWN"))
		 * @MODIFIES:this.forInput, this.up,this.floorLoc,this.time
		 * @EFFECTS: this.forInput.equals(s1) && !this.in && this.floorLoc==i && this.time=long1 && this.up==(s.equals("UP"))?true:false;
		 */
		this.forInput=s1;
		this.in =false;
		this.floorLoc=i;
		this.up=(s.equals("UP"))?true:false;
		this.time=long1;
	}
	public String getR() {
		/**@REQUIRES:NONE
		 * @MODIFIES:NONE
		 * @EFFECTS: \result=this.forInput;
		 */
		return this.forInput;
	}
	public boolean getIn() {
		/**@REQUIRES:NONE
		 * @MODIFIES:NONE
		 * @EFFECTS: \result=this.in;
		 */
		return this.in;
	}
	public boolean getUP() {
		/**@REQUIRES:NONE
		 * @MODIFIES:NONE
		 * @EFFECTS: \result=this.up;
		 */
		return this.up;
	}
	public int getfloorLoc() {
		/**@REQUIRES:NONE
		 * @MODIFIES:NONE
		 * @EFFECTS: \result=this.floorLoc;
		 */
		return this.floorLoc;
	}
	public int gettofloor() {
		/**@REQUIRES:NONE
		 * @MODIFIES:NONE
		 * @EFFECTS: \result=this.toFloor;
		 */
		return this.toFloor;
	}
	public long gettime() {
		/**@REQUIRES:NONE
		 * @MODIFIES:NONE
		 * @EFFECTS: \result=this.time;
		 */
		return this.time;
	}
}
