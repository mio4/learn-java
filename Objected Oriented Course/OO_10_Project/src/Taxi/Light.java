package Taxi;

import java.util.Random;
import java.awt.Point;

public class Light extends Thread{
	/*Overview : 红绿灯线程，相隔指定时间改变地图中的所有红绿灯
	 */
	private TaxiGUI taxiGUI;
	private int lightChangeTime;

	/** @REQUIRES : None;
	 * @MODIFIES : this.taxiGUI;
	 * @EFFECTS : this.taxiGUI == taxiGUI;
	 */
	public Light(TaxiGUI taxiGUI){
		this.taxiGUI = taxiGUI;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \result == invariant(this);
	 */
	public boolean repOK(){
		if(taxiGUI == null)
			return false;
		return true;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : \request.equals(每隔一段时间改变红绿灯颜色);
	 */
	public void run(){
		while(true){
			try {
				Thread.sleep(this.lightChangeTime);
				changeLight();
				//test
				Thread.sleep(15000);
			} catch (Exception e) {}
		}
	}

	/** @REQUIRES : None;
	 * @MODIFIES : this.lightChangeTime;
	 * @EFFECTS : \result == this.lightChangeTime;
	 */
	public int getlightChangeTime(){ //获取红绿灯随机改变的时间
		Random random = new Random();
		this.lightChangeTime = random.nextInt(501)+500;
		return this.lightChangeTime;
	}

	/** @REQUIRES : None;
	 * @MODIFIES : None;
	 * @EFFECTS : light[i][j]==1 ==> \request.equals(set the light status to 2);
	 * light[i][j]==2 ==> \request.equals(set the light status to 1);
	 */
	public void changeLight(){ //改变红绿灯
		int[][] light = this.taxiGUI.getLightStatus();
		for(int i=0;i < 80;i++){
			for(int j=0;j < 80;j++){
				if(light[i][j] == 1)
					this.taxiGUI.SetLightStatus(new Point(i,j),2);
				else if (light[i][j] == 2)
					this.taxiGUI.SetLightStatus(new Point(i,j),1);
			}
		}
	}
}
