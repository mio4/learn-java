package oo_13;

 
public class Elevator {
	/**@OVERVIEW:电梯类，保存电梯现在所在的楼层以及状态
	 */
	private int floorNow=1;
	private String status="STILL";
	public boolean repOK() {
		if(floorNow<11 && floorNow>0 && (status.equals("STILL")||status.equals("DOWN")||status.equals("UP"))) return true;
		return false;
	}
	public int getFloorNow() {
		/**@REQUIRES:NONE
		 * @MODIFIES:NONE
		 * @EFFECTS: \result=this.floorNow;
		 */
		return this.floorNow;
	}
	public void setFloor(int i) {
		/**@REQUIRES:0<i<=10;
		 * @MODIFIES:this.floorNow
		 * @EFFECTS: this.floorNow==i;
		 */
		this.floorNow=i;
	}
	public String toString() {
		/**@REQUIRES:NONE;
		 * @MODIFIES:NONE
		 * @EFFECTS: \result="("+this.floorNow+this.status+")";
		 */
		return "("+this.floorNow+this.status+")";   //toString
	}
	
	public interface elevatorMoveMethon{			//interface
		public void runUp();
		public void runDown();
		public void still();
	}
}

