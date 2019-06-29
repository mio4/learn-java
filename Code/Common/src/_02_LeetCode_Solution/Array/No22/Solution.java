package _02_LeetCode_Solution.Array.No22;

import java.util.ArrayList;
import java.util.List;

class Solution {
    private static List<String> res = new ArrayList<String>();
    private static void addParenthesis(String s,int n, int sum, int left){
        if(s.length() == n){
            res.add(s);
            return;
        }
        if(sum >= 0 && s.length() < n){
            if(left < n/2){
                s += "(";
                addParenthesis( s, n, sum+1, left+1);
                s = s.substring(0,s.length()-1);
            }
            s += ")";
            addParenthesis( s, n, sum-1,left);
        }


    }
    public static List<String> generateParenthesis(int n) {
        addParenthesis("", n,0,0);
        return res;
    }

    private void helper(){

    }

    public static void printList(){
        for(int i=0; i < res.size(); i++){
            System.out.println(res.get(i));
        }
    }

    public static void main(String[] args) {
        generateParenthesis(6);
        printList();
    }
}
