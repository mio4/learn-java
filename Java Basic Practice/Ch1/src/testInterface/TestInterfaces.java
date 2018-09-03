package testInterface;

interface Painter{
	public void eat();
	public void paint();
}

interface Singer{
	public void sing();
	public void sleep();
}

class Student implements Singer{
	private String name;

	public Student(String name){
		this.name = name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return this.name;
	}

	@Override
	public void sing(){
		System.out.println("Student is singing");
	}

	@Override
	public void sleep(){
		System.out.println("Student is sleeping");
	}
}

class Teacher implements Painter,Singer{
	private String name;

	public Teacher(String name){
		this.name = name;
	}

	@Override
	public void sing(){
		System.out.println("Teacher is singing");
	}

	@Override
	public void sleep(){
		System.out.println("Teacher is sleeping");
	}

	@Override
	public void eat(){
		System.out.println("Teacher is eating cakes");
	}

	@Override
	public void paint(){
		System.out.println("Teacher is painting");
	}

	public void teaches(){
		System.out.println("teach me ");
	}
}

public class TestInterfaces {
	public static void main(String[] args){
		Singer s1 = new Student("me");
		s1.sing();
		s1.sleep();
		Singer s2 = new Teacher("you");
		s2.sing();
		s2.sleep();
		Painter p1 = (Painter) s2;
		p1.eat();
	}
}
