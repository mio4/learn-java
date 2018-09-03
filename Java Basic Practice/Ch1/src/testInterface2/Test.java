package testInterface2;

interface Valuable{
	public double getMoney();
}

interface Protectable{
	public void beProtected();
}

interface A extends Protectable{ //接口之间的关系需要使用extends而不是implements
	public void f();
}

abstract class Animal{
	private String name;

	abstract void enjoy();
}

class GoldenMonkey extends Animal implements Valuable,Protectable{

	public void beProteced(){
		System.out.println("living in the room");
	}

	@Override
	public double getMoney(){
		return 10000;
	}

	@Override
	public void enjoy(){

	}

}

public class Test {

}
