package Taxi;

import java.awt.*;

//function:监控道路流量
public class FlowMonitor extends Thread{
	/*Overview : 监控整个地图的流量，每隔一段时间清空道路流量
	 */
	private int time = 50000;
	private int[][] flow = new int[6400][2]; //记录道路流量的数组

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : None;
	 */
	public FlowMonitor(){
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @Effects : \result == invariant(this);
	 */
	public boolean repOK(){
		if(flow == null)
			return false;
		return true;
	}

	/** @REQUIRES: None;
	 * @MODIFIES: this.flow;
	 * @EFFECTS: \request.equals(每隔500ms清空道路流量);
	 */
	@Override
	public void run(){

		while(true){
			clearFlow();
			try {
				Thread.sleep(time);
			} catch (Exception e) {}
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this.flow;
	 * @EFFECTS : \request.equals(对于(posx,posy)的特定边道路流量+1);
	 */
	public void addFlow(int posx,int posy,int type){ //道路流量+1,type=0,右边; type=1，下边
		if(type == Taxi.UP && posx+posy>=0 ) this.flow[posx+posy][1]+=1;
		else if (type == Taxi.DOWN && posx*80+posy-80>=0) this.flow[posx*80+posy-80][1]+=1;
		else if (type == Taxi.LEFT && posx*80+posy>=0) this.flow[posx*80+posy][0]+=1;
		else if (type == Taxi.RIGHT && posx*80+posy-1>=0) this.flow[posx*80+posy-1][0]+=1;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this.flow;
	 * @EFFECTS : \all flow[i][0]==0 && \all flow[i][1]==0;
	 */
	public void clearFlow(){ //每隔200ms 清空道路流量

		for(int i=0; i < 6400;i++) {
			this.flow[i][0] = 0;
			this.flow[i][1] = 0;
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : (type==0 && posx*80+posy-80 >= 0) ==> \result = flow[posx*80+posy-80][1]
	 *(type==1) ==> \result = flow[posx*80+posy][1]
	 *(type==2 && posx*80+posy-1 >= 0) ==> \result = flow[posx*80+posy-1][0]
	 *(type==3) ==> \result = flow[posx*80+posy][0]
	 */
	public int getFlow(int posx,int posy, int type){ //返回指定边的道路流量
		if(type == Taxi.UP) {
			if(posx * 80 + posy -80 >= 0)
				return this.flow[posx * 80 + posy - 80][1];
			else
				return 0;
		}
		else if (type == Taxi.DOWN) return this.flow[posx*80+posy][1];
		else if (type == Taxi.LEFT) {
			if(posx*80+posy-1 >= 0)
				return this.flow[posx*80+posy-1][0];
			else
				return 0;
		}
		else if (type == Taxi.RIGHT) return this.flow[posx*80+posy][0];
		else return 0;
	}

	/********************************************
	 关于Load File命令的方法
	 ********************************************/
	/** @REQUIRES: None;
	 * @MODIFIES: None;
	 * @EFFECTS: (distance==1) ==> \result = true
	(distance!=1) ==> \result = false
	 */
	public static boolean isNeighbor(int srcx,int srcy,int dstx, int dsty){ //判断两条边是否相邻
		if(srcx<0 || srcy<0 || dstx<0 || dsty<0 || srcx>79 || srcy>79 || dstx>79 || dsty>79 || (srcx==dstx && srcy==dsty))
			return false;
		int distance = (srcx-dstx)*(srcx-dstx) + (srcy-dsty)*(srcy-dsty);
		if(distance == 1)
			return true;
		return false;
	}

	/** @REQUIRES: None;
	 * @MODIFIES: this.flow;
	 * @EFFECTS: \request.equals(设置特定边的流量值);
	 */
	public void setFlow(int srcx,int srcy,int dstx,int dsty,int flow){

		if(dstx==srcx+1)
			this.flow[srcx*80+srcy][1] = flow;
		else if (dsty==dstx+1)
			this.flow[srcx*80+srcy][0] = flow;
	}
}
