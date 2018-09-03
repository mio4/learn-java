package testInterface;

class Wine{
	public void fun1(){
		System.out.println("fun1 of wine");
		fun2();
	}
	
	public void fun2(){
		System.out.println("fun2 of wine");
	}
}

class JNC extends Wine{
	public void fun1(String s){
		System.out.println("fun1 of JNC");
		fun2();
	}
	
	public void fun2(){
		System.out.println("fun2 of JNC");
	}
}

public class test {
	public static void main(String[] args){
		Wine a = new JNC();
		//`a.fun1("a");
	}
}
