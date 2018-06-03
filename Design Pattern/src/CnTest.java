//适配器模式的简单例子
//
//国标插口
interface CnPluginInterface{
	void chargeWith2Pins();
}
//实现国标插座的充电方法
class CnPlugin implements CnPluginInterface{
	public void chargeWith2Pins(){
		System.out.println("charge with CnPlugin");
	}
}
//在中国充电
class Home{
	private CnPluginInterface cnPlugin;

	public Home(){}

	public Home(CnPluginInterface cnPlugin){
		this.cnPlugin = cnPlugin;
	}

	public void setPlugin(CnPluginInterface cnPlugin){
		this.cnPlugin = cnPlugin;
	}

	public void charge(){
		cnPlugin.chargeWith2Pins();
	}
}
public class CnTest {

	public static void main(String[] args){
		CnPluginInterface cnPlugin = new CnPlugin();
		Home home = new Home(cnPlugin);
		home.charge();
	}
}
