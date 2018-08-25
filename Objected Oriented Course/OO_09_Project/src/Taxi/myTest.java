package Taxi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

public class myTest {

	public static void main(String[] args){
		//Scanner in = new Scanner(System.in);
		//String str = in.nextLine();
		/*
		String tempString = "\\(\\d{1,2},\\d{1,2}\\)\\(\\d{1,2},\\d{1,2}\\)\\d{1,10}";
		String str = "(0,0)(1,0)10";

		String[] strs = str.split("[^\\d+]+");

		//System.out.println(strs[0]);
		System.out.println(Pattern.matches(tempString,str));
		//for(String s : strs)
		//	System.out.println(s);
		String s = "1";
		int x = Integer.parseInt(s);
		System.out.println(x);
		*/
		String str = "[OPEN,(1,1),(2,2)]";
		String[] strs = str.split("[^\\d+]+");
		for(String s : strs)
			System.out.println(s);
	}
}
