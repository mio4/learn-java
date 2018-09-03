package oo_13;
public class Control {
	/**@OVERVIEW:原始调度器
	 */ 
	private double time;
	private double[] sc=new double[100];
	public boolean repOK() {
		if(sc!=null) return true;
		return false;
	}
	public void setUp() {
		/**@REQUIRES:NONE;
		 * @MODIFIES:this.time;this.sc;
		 * @EFFECTS:\result = (this.time == 0) &&
		 * 					\all int i;0<i=<sc.length;
		 * 					sc[i]==0;
		 */
		this.time=0;
		for(int i = 0;i<sc.length;i++) sc[i]=0;
	}
	public boolean isSame(Request n,Request body) {
		/**@REQUIRES:n! = null && body != null;
		 * @MODIFIES:NONE
		 * @EFFECTS: \result=(n.equals(body));
		 */
		if(n.getIn()==body.getIn()) {
			if(n.getIn()==true&&(n.gettofloor()==body.gettofloor())) {
				return true;
			}
			if(n.getIn()==false&&(n.getUP()==body.getUP() && n.getfloorLoc()==body.getfloorLoc())) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	public void command(Requeue r) {
		/**@REQUIRES:r!=null
		 * @MODIFIES:this.sc,this.time
		 * @EFFECTS: control the elevator to work for the request
		 */
		Elevator e=new Elevator();
		int count,flag=0;;
		for(int i=0;r.visit(i)!=null;i++) {
			Request rr=r.visit(i);
			int floorLoc=e.getFloorNow();
			
			for(count=i-1,flag=0;count>=0;count--) {
				if(sc[count]==-1) continue;
				else if(sc[count]<rr.gettime()) {
					break;
				}
				else {
					if(isSame(r.visit(count),rr)) {
						sc[i]=-1;
						r.setNull(i);
						flag=1;
						System.out.println("#SAME["+rr.getR()+"]");
						break;
					}
				}
			}
			if(flag==1) continue;
			
			if(rr.getIn()==true) {
				if(floorLoc>rr.gettofloor()) {
					this.time=(floorLoc-rr.gettofloor())*0.5+1+Math.max(this.time,rr.gettime());
					e.setFloor(rr.gettofloor());
				}
				else if(floorLoc<rr.gettofloor()) {
					this.time=(-floorLoc+rr.gettofloor())*0.5+1+Math.max(this.time,rr.gettime());
					e.setFloor(rr.gettofloor());
				}
				else {
					this.time=1+Math.max(this.time,rr.gettime());
					e.setFloor(rr.gettofloor());
				}
			}
			else {
				if(floorLoc>rr.getfloorLoc()) {
					this.time=(floorLoc-rr.getfloorLoc())*0.5+1+Math.max(this.time,rr.gettime());
					e.setFloor(rr.getfloorLoc());
				}
				else if(floorLoc<rr.getfloorLoc()) {
					this.time=(-floorLoc+rr.getfloorLoc())*0.5+1+Math.max(this.time,rr.gettime());
					e.setFloor(rr.getfloorLoc());
				}
				else {
					this.time=1+Math.max(this.time,rr.gettime());
					e.setFloor(rr.getfloorLoc());
				}
			}
			sc[i]=this.time;
		}
	}
	
	
}

