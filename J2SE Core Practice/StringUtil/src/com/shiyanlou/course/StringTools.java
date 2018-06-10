package com.shiyanlou.course;

import java.util.Scanner;

public class StringTools {
	public static void main(String[] args){
		String a = new String();
		String b = new String();
		String c = new String();

		Scanner sc = new Scanner(System.in);
		System.out.println("input a string:");
		a = sc.nextLine();
		b = a.trim();
		if(b.equals("trim")){
			c = b.substring(0,2);
		} else {
			c = "";
		}
		System.out.println("a " + a);
		System.out.println("a length " + a.length());
		System.out.println("b " + b);
		System.out.println("c " + c);
	}
}
