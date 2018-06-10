class Base{
	static int num = 1;
	static {
		System.out.println("Base :" + num);
	}
}
public class TestBase {
	public static void main(String[] args) throws Exception{
		Class clazz = Base.class; //.class不会初始化类
		System.out.println("***");
		Class clazz2 = Class.forName("Base"); //Class.forName()方法会初始化引用的类
	}
}
