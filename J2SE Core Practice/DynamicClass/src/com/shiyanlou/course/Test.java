package com.shiyanlou.course;

import java.io.File;
import java.lang.reflect.Constructor;

public class Test {
	public static void main(String[] args){
		try {
			//获取File的Contructor对象
			Constructor<File> constructor = File.class.getDeclaredConstructor(String.class);
			System.out.println("create file object by reflection");
			File file = constructor.newInstance("MyFile.txt");
			file.createNewFile();
			System.out.println("is file created? "  + file.exists());
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
