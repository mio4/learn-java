package Taxi;

import java.util.ArrayList;
import java.util.Iterator;
import java.awt.Point;

public class Taxi2 extends Taxi {
	/*Overview : 继承得到的出租车线程，等待状态下随机移动以及停止服务，收到请求后接送客
				 新增了关于VIP出租车的判定
	 */
	public Taxi2(int id, int[][] map,TaxiGUI taxiGUI,guiInfo GuiInfo,FlowMonitor flowMonitor) {
		/** @REQUIRES : map!=null,taxiGUI!=null,GuiInfo!=null,flowMonitor!=null;
		 * @MODIFIES : super.id,super.map,super.taxiGUI,super.GuiInfo,super.flowMonitor;
		 * @EFFECTS : None;
		 */
		super(id,map,taxiGUI,GuiInfo,flowMonitor);
	}

	public boolean repOK(){
		/** @REQUIRES: None;
		* @MODIFIES: None;
		* @Effects: \result == invariant(this);
		* @Invariant(this): super.repOK();
		*/
		return super.repOK();
	}

	public void setVip(int type){
		/** @REQUIRES : None;
		 * @MODIFIES : None;
		 * @EFFECTS : \result = superVip(type);
		 */
		super.setVip(type);
	}


	public Iterator<VIPRequest> iterator(){
		/** @REQUIRES : None;
		 * @MODIFIES : None;
		 * @EFFECTS : \result = super.iterator();
		 */
		return super.iterator();
	}
}
