class XYZ{
	public static String name = "static name";
	static{
		System.out.println("XYZ静态块");
	}
	public XYZ(){
		System.out.println("XYZ构造方法执行");
	}
}
public class TestMain {
	public static void main(String[] args){
		//Class的name属性如何查看?
		System.out.println(XYZ.name);
	}
}
