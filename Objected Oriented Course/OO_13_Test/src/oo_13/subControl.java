package oo_13;
 
import java.util.ArrayList;

class subControl extends Control {
	/**@OVERVIEW:子类调度器继承父类调度器
	 * @IHERIT:Control|command();isSame();
	 */
	private double time;
	private ArrayList<String> outputStr = new ArrayList<String>();
	public boolean repOK() {
		if(outputStr!=null) return true;
		return false;
	}
	public String getOutput(int n) {
		/**@REQUIRES:0<=n<outputStr.size;
		 * @MODIFIES:NONE
		 * @EFFECTS: \result =  this.outputStr.get(n);
		 */
		return this.outputStr.get(n);
	}
	public void setUp() {
		/**@REQUIRES:NONE
		 * @MODIFIES:this.time
		 * @EFFECTS: \result = (this.time == 0) && (outputStr.clear)
		 */
		this.time=0;
		outputStr.clear();
	}
	public void command(Requeue r,int n) {
		/**@REQUIRES:r!=null
		 * @MODIFIES:this.time
		 * @EFFECTS: control the elevator to work for the request
		 */
		Elevator e=new Elevator();
		String move="";
		double tt;
		for(int i=0;i<r.returnNum();i++) {
			if(r.visit(i)==null)continue;
			Request rr=r.visit(i);
			double startTime=Math.max(this.time,rr.gettime());
			int floorFrom=e.getFloorNow();
			int floorTo=(rr.getIn())?rr.gettofloor():rr.getfloorLoc();
			if(floorFrom>floorTo) {
				tt=(floorFrom-floorTo)*0.5+1+startTime;
			}
			else if(floorFrom<floorTo) {
				tt=(-floorFrom+floorTo)*0.5+1+startTime;
			}
			else {
				tt=1+startTime;
			}
			this.time=tt;
			//System.out.println(r);
			
			move=(e.getFloorNow()==floorTo)?"STILL":(e.getFloorNow()>floorTo)?"DOWN":"UP";
			//System.out.println(startTime +" "+(tt-1));
			i=this.rideSys(r,i,startTime,tt-1,floorFrom,floorTo,e,"]/("+floorTo+","+move+",",move);
			e.setFloor(floorTo);
			
		}
	}
	public int rideSys(Requeue r,int i,double start,double end,int floorFrom,int floorTo,Elevator e,String str,String move) {
		/**@REQUIRES:r!=null && i<queue.head && start<end && 0<floorFrom,floorTo=<10 && move==("UP"||"DOWN"||"STILL") && e!=null
		 * @MODIFIES:this.time
		 * @EFFECTS: scan the queue to see if there are some requests to be carried;
		 */
		int result=i;
		int extra=0,flag=0;
		String[] output=new String[15];
		String[] rrr=new String[50];
		double[] mark=new double[15]; 
		mark[floorTo]=end-1;
		for(int j=i+1;j<r.returnNum();j++) {
			Request passenger=r.visit(j); 
			if(passenger==null) continue;
			
			if(passenger.gettime()>=end+extra || passenger.gettime()<r.visit(i).gettime()) break;
			else if(passenger.getIn()) {
				double k=Math.abs(passenger.gettofloor()-floorFrom)*0.5+start+((passenger.gettofloor()==e.getFloorNow())?extra-1:extra);
				if(floorFrom<passenger.gettofloor() && floorFrom<floorTo) {
					if(passenger.gettofloor()<=floorTo) {
						if((passenger.gettofloor()-floorFrom)*0.5+start+extra>passenger.gettime()) {
							if(passenger.gettofloor()==floorTo) rrr[flag++]=passenger.getR();
							else if(mark[passenger.gettofloor()]!=0) {
								output[passenger.gettofloor()]+=mark[passenger.gettofloor()]+")\n["+passenger.getR()+"]/("+passenger.gettofloor()+",UP,";
							}
							else{
								this.time+=1;
								output[passenger.gettofloor()]="["+passenger.getR()+"]/("+passenger.gettofloor()+",UP,";
								e.setFloor(passenger.gettofloor());
								extra++;
								mark[passenger.gettofloor()]=k;
								mark[floorTo]++;
								//System.out.println(extra+end);
							}
							r.setNull(j);
						}
						
						else continue;
					}
					else {
						result=(result!=i)?result:j;
					}
				}
				else if(floorFrom>passenger.gettofloor() && floorFrom>floorTo) {
					//
					if(passenger.gettofloor()>=floorTo) {
						if((-passenger.gettofloor()+floorFrom)*0.5+start+extra>passenger.gettime()) {
							
							if(passenger.gettofloor()==floorTo) rrr[flag++]=passenger.getR();
							else if(mark[passenger.gettofloor()]!=0) {
								output[passenger.gettofloor()]+=mark[passenger.gettofloor()]+")\n["+passenger.getR()+"]/("+passenger.gettofloor()+",DOWN,";
							}
							else {
								this.time+=1;
								output[passenger.gettofloor()]="["+passenger.getR()+"]/("+passenger.gettofloor()+",DOWN,";
								e.setFloor(passenger.gettofloor());
								extra++;
								mark[passenger.gettofloor()]=k;
								mark[floorTo]++;
							} 
							//System.out.println(flag);
							r.setNull(j);
						}
						else continue;
					}
					else {
						result=(result!=i)?result:j;
					}
				}
				else continue;
			}
			else if(passenger.getIn()==false) {
				double k=Math.abs(passenger.getfloorLoc()-floorFrom)*0.5+start+((passenger.getfloorLoc()==e.getFloorNow())?extra-1:extra);
				if(floorFrom<passenger.getfloorLoc() && passenger.getUP()==true && floorFrom<floorTo) {
					if( passenger.getfloorLoc()<=floorTo&&(k>passenger.gettime())) {
						if(passenger.getfloorLoc()==floorTo) rrr[flag++]=passenger.getR();
						else if(mark[passenger.getfloorLoc()]!=0 ) {
							output[passenger.getfloorLoc()]+=mark[passenger.getfloorLoc()]+")\n["+passenger.getR()+"]/("+passenger.getfloorLoc()+",UP,";
							//System.out.println(output[passenger.gettofloor()]);
						}
						else {
							this.time+=1;
							output[passenger.getfloorLoc()]="["+passenger.getR()+"]/("+passenger.getfloorLoc()+",UP,";
							e.setFloor(passenger.getfloorLoc());
							extra++;
							mark[passenger.getfloorLoc()]=k;
							mark[floorTo]++;
							
						}
						r.setNull(j);
					}
					else {
						continue;
					}
				}
				else if(floorFrom>passenger.getfloorLoc() && passenger.getUP()==false && floorFrom>floorTo) {
					
					if(passenger.getfloorLoc()>=floorTo&&(k>passenger.gettime())) {
						
						if(passenger.getfloorLoc()==floorTo) rrr[flag++]=passenger.getR();
						else if(mark[passenger.getfloorLoc()]!=0) {
							output[passenger.getfloorLoc()]+=mark[passenger.getfloorLoc()]+")\n["+passenger.getR()+"]/("+passenger.getfloorLoc()+",UP,";
						}
						else {
							this.time+=1;
							output[passenger.getfloorLoc()]="["+passenger.getR()+"]/("+passenger.getfloorLoc()+",DOWN,";
							e.setFloor(passenger.getfloorLoc());
							extra++;
							mark[passenger.getfloorLoc()]=k;
							mark[floorTo]++;
						}
						r.setNull(j);
					}
					else continue;
				}
				else continue;
			}
		}
		String sss;
		double ttt;
		for(int j=1;j<11;j++) {
			if(mark[j]==0) continue;
			for(int m=j+1;m<11;m++) {
				if(mark[m]==0) continue;
				if(mark[j]>mark[m]) {
					sss=output[m];
					output[m]=output[j];
					output[j]=sss;
					ttt=mark[m];
					mark[m]=mark[j];
					mark[j]=ttt;
				}
			}
			if(output[j]!=null) {
				System.out.printf("%s%.1f)\n",output[j],mark[j]);
				//System.out.println(output[j]+(double)mark[j]+")");
				outputStr.add(output[j]+(double)mark[j]+")");
			}
			
		}
		System.out.printf("[%s]/(%d,%s,%.1f)\n",r.visit(i).getR(),floorTo,move,(move.equals("STILL")?this.time:this.time-1));
		//System.out.println("["+r.visit(i).getR()+"]/("+floorTo+","+move+","+(double)(move.equals("STILL")?this.time:this.time-1)+")");
		outputStr.add("["+r.visit(i).getR()+"]/("+floorTo+","+move+","+(double)(move.equals("STILL")?this.time:this.time-1)+")");
		while(flag>0) {
			flag--;
			System.out.printf("[%s%s%.1f)\n",rrr[flag],str,this.time-1);
			//System.out.println("["+rrr[flag]+str+(double)(this.time-1)+")");
			outputStr.add("["+rrr[flag]+str+(double)(this.time-1)+")");
		}
		r.setRequest(r.visit(result), i);
		r.setNull(result);
		return (result!=i)?i-1:i;
	}
}


