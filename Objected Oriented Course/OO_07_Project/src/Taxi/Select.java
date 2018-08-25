package Taxi;

public class Select {
	private Taxi[] taxis;
	public Select(Taxi[] taxis) {
		this.taxis = taxis;
	}
	public void SelectTaxi(int state) {
		int i;
		for(i=0; i < 100;i++)
			System.out.println(state + "状态的出租车：");
			if(this.taxis[i].getTaxiState() == state)
				System.out.println(i);
	}
}
