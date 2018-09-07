package Num20_2;

public class Solution {
	public static boolean isNumeric(char[] str) {
		boolean appNum; //是否出现数字
		boolean appRadix; //是否出现小数点
		boolean appSig; //是否出现正负号

		if(str == null || str.length == 0) //边界条件
			return false;




		return true;
	}

	public static void main(String[] args){
		String s1 = "+100";
		String s2 = "5e2";
		String s3 = "-123";
		String s4 = "3.14159";
		String s5 = "-1E-16";
		String s6 = "12e";
		String s7 = "1a3.14";
		String s8 = "1.2.3";
		String s9 = "+-5";
		String s10 = "12e+5.4";
		System.out.println("s1" + isNumeric(s1.toCharArray()));
		System.out.println("s2" + isNumeric(s2.toCharArray()));
		System.out.println("s3" + isNumeric(s3.toCharArray()));
		System.out.println("s4" + isNumeric(s4.toCharArray()));
		System.out.println("s5" + isNumeric(s5.toCharArray()));
		System.out.println("s6" + isNumeric(s6.toCharArray()));
		System.out.println("s7" + isNumeric(s7.toCharArray()));
		System.out.println("s8" + isNumeric(s8.toCharArray()));
		System.out.println("s9" + isNumeric(s9.toCharArray()));
		System.out.println("s10" + isNumeric(s10.toCharArray()));
		char[] cs = {'a','b'};
		System.out.println(String.valueOf(cs));
	}
}
