package palindromePartition;

import java.util.ArrayList;

public class Solution {

	public static ArrayList<ArrayList<String>> partition(String s) {
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		ArrayList<String> list = new ArrayList<String>();
		if(s == null || s.length() == 0){ //边界条件
			return result;
		}
		recallSolution(result,list,s);
		return result;
	}

	public static void recallSolution(ArrayList<ArrayList<String>> result, ArrayList<String> list, String s){ //回溯解法

	}

	public static boolean isParlindrome(String s){ //判断是否是回文串
		int i = 0;
		int j = s.length()-1;
		while(i < j){
			if(s.charAt(i) != s.charAt(j)){
				return false;
			}
			i++;
			j--;
		}
		return true;
	}

	public static void main(String[] args){

	}
}
