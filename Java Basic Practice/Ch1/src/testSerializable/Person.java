package testSerializable;

import java.io.Serializable;

/*
	测试对象的序列化和反序列化
 */
public class Person implements Serializable {
	private static final long serialVersionUID = 5790870173163471406L;
	private int age;
	private String name;
	private String sex;

	public int getAge(){
		return this.age;
	}

	public String getName(){
		return this.name;
	}

	public String getSex(){
		return this.sex;
	}

	public void setAge(int age){
		this.age = age;
	}

	public void setName(String name){
		this.name = name;
	}

	public void setSex(String sex){
		this.sex = sex;
	}
}
