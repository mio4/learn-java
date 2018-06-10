class People{
	private String name = "me";
	private int age;

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public int getAge(){
		return age;
	}

	public void setAge(int age){
		this.age = age;
	}
}
public class Reflection {
	public static void main(String[] args) throws Exception{
		//使用new 创建类的实例
		People p1 = new People();
		System.out.println(p1.getClass());
		//利用反射创建实例
		//Class clazz = People.class;
		//Class clazz = Class.forName("People");
		Class clazz = p1.getClass();
		People p2 = (People) clazz.newInstance();
		System.out.println(p2.getName());
	}
}
