package testSerializable;

import java.io.*;
import java.text.MessageFormat;

public class TestObjSerializeAndDeserialize {
	public static void main(String[] args) throws Exception{
		SerializePerson();
		Person p = DeSerializePerson();
		System.out.println(MessageFormat.format("name={0},age={1},sex={2}",p.getName(),p.getAge(),p.getSex()));
	}

	/*
		序列化对象
	 */
	public static void SerializePerson() throws FileNotFoundException,IOException{
		Person p = new Person();
		p.setName("mio");
		p.setAge(20);
		p.setSex("male");

		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(new File("F://person.txt")));
		oo.writeObject(p);
		System.out.println("对象序列化成功");
		oo.close();
	}

	/*
		反序列化对象
	 */
	public static Person DeSerializePerson() throws IOException,Exception {
		ObjectInputStream oi = new ObjectInputStream(new FileInputStream(new File("F://person.txt")));
		Person p = (Person) oi.readObject();
		System.out.println("对象反序列化成功");
		return p;
	}
}
