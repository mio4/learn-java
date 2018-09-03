//工厂设计模式
interface Human{
	public void eat();
	public void sleep();
	public void beat();
}
class Male implements Human{
	public void eat(){
		System.out.println("male can eat");
	}
	public void sleep(){
		System.out.println("male can sleep");
	}
	public void beat(){
		System.out.println("male can be beat");
	}
}
class Female implements Human{
	public void eat(){
		System.out.println("female can eat");
	}
	public void sleep(){
		System.out.println("female can sleep");
	}
	public void beat(){
		System.out.println("female can be beat");
	}
}
class HumanFactory{
	public Human CreatHuman(String gender){
		if(gender.equals("Male"))
			return new Male();
		else if (gender.equals("Female"))
			return new Female();
		else {
			System.out.println("Please input Female/Male");
			return null;
		}
	}
}

public class FactoryTest{
	public static void main(String[] args){
		HumanFactory humanFactory = new HumanFactory();
		Human male = humanFactory.CreatHuman("Male");
		male.eat();
		male.sleep();
		male.beat();
		System.out.println("Nothing here~");
		System.out.println("You can do nothing here~");
		System.out.println("Yoush ");
		System.out.println("#include <stdio.h>");
		System.out.println("You are my dream");
		System.out.println("Try to write something here");
		System.out.println("Hey are you ok?");
		System.out.println("Hello Java do you ");
		System.out.println("1 23");

	}
}
