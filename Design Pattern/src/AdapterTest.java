//英标插头
interface EnPluginInterface{
	void chargeWith3Pins();
}
//实现英标插座的充电方法
class EnPlugin implements EnPluginInterface{
	public void chargeWith3Pins(){
		System.out.println("charge with 3 pins");
	}
}
//适配器
class PluginAdapter implements CnPluginInterface{
	private EnPluginInterface enPlugin;

	public PluginAdapter(EnPluginInterface enPlugin){
		this.enPlugin = enPlugin;
	}

	//重载国标的充电方法为英标的充电方法
	@Override
	public void chargeWith2Pins(){
		enPlugin.chargeWith3Pins();
	}
}

//适配器测试类
public class AdapterTest {
	public static void main(String[] args){
		EnPluginInterface enPlugin = new EnPlugin();
		Home home = new Home();
		PluginAdapter pluginAdapter = new PluginAdapter(enPlugin);
		home.setPlugin(pluginAdapter);
		home.charge();
	}
}
