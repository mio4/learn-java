package me.test.forname;
//错误: 找不到或无法加载主类 me.test.format.TestClassForName
//问题存疑
public class TestClassForName {

	public static void main(String[] args) throws ClassNotFoundException{
		System.out.println("?");
		(new TestClassForName()).loadclass();
	}

	@SuppressWarnings("unchecked")
	public Class<Test> loadclass() throws ClassNotFoundException{
		Class<Test> clazz = (Class<Test>) Class.forName("me.test.forname");
		return clazz;
	}
}
class Test{
	static {
		System.err.println("类的默认静态初始化块");
	}
	public Test(){
		System.err.println("实例化类");
	}
}
