package Taxi;

public class Select {
	/*Overview : 用于用户测试（测试出租车状态）的接口类
	 */
	private Taxi[] taxis;

	/** @REQUIRES : None;
	 * @MODIFIES : this.taxis;
	 * @EFFECTS : None;
	 */
	public Select(Taxi[] taxis) {
		this.taxis = taxis;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : System.out;\Request.equals("选择测试的出租车状态");
	 */
	public void SelectTaxi(int state) {
		int i;
		for(i=0; i < 100;i++)
			System.out.println(state + "状态的出租车：");
			if(this.taxis[i].getTaxiState() == state)
				System.out.println(i);
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == invariant(this);
	 */
	public boolean repOK(){
		if(this.taxis == null)
			return false;
		return true;
	}
}
