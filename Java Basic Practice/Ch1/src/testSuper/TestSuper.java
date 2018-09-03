package testSuper;

class FatherClass{
	protected int value;
	protected void fun(){
		value = 100;
		System.out.println("父类的value属性值" + value);
	}
}

class ChildClass extends FatherClass{
	public int value;
	public void fun(){
		super.fun();
		value = 200; //子类的value
		System.out.println("子类的value"+ value);

		System.out.println("***");
		System.out.println(value);
		System.out.println(super.value);
	}
}

public class TestSuper {
	public static void main(String[] args){
		ChildClass cc = new ChildClass();
		cc.fun();
	}
}
