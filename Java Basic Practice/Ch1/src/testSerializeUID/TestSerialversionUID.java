package testSerializeUID;

import java.io.*;

public class TestSerialversionUID {
	public static void main(String[] args) throws Exception{
		SerializeCustomer();
		Customer customer = DeSerializeCustomer();
		System.out.println(customer);
	}

	/*
		序列化操作
	 */
	public static void SerializeCustomer() throws FileNotFoundException,IOException{
		Customer customer = new Customer("mio",20);
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File("F://Customer.txt")));
		oo.writeObject(customer);
		System.out.println("序列化成功");
	}

	public static Customer DeSerializeCustomer() throws ClassNotFoundException,IOException{
		ObjectInputStream oi = new ObjectInputStream(new FileInputStream((new File("F://Customer.txt"))));
		Customer customer = (Customer) oi.readObject();
		System.out.println("反序列化成功");
		return customer;
	}

	/*
		反序列化操作
	 */
}

class Customer implements Serializable{
	private static final long serialVersionUID = 4008001218432176197L;
	private String name;
	private int age;

	private String gender;

	public 	Customer(String name, int age){
		this.name = name;
		this.age = age;
	}

	public Customer(String name, int age, String gender){
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	@Override
	public String toString(){
		return "name = " + name + ",age = " + age;
	}

}
