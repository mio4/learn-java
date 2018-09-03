package testSuper2;

class SuperClass{
	protected int value;
	public SuperClass(int value){
		this.value = value;
	}
}

class SubClass extends SuperClass{
	private int value;

	public SubClass(){
		super(300);
		System.out.println(super.value);
	}
}
public class TestSuper {
	public static void main(String[] args){
		SubClass sc = new SubClass();
	}
}
