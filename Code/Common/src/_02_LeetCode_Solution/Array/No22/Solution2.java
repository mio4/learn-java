package _02_LeetCode_Solution.Array.No22;

import java.util.ArrayList;
import java.util.List;

class Solution2 {
    private static List<String> res = new ArrayList<>();

    public static List<String> generateParenthesis(int n) {
        helper("","",n,n);
        return res;
    }

    private static void helper(String curRst,String s,int l,int r){
        //递归终止条件
        if(l == 0 && r == 0){
            res.add(curRst);
        }

        //添加左括号
        if(l != 0){
            helper(curRst + "(",s + "(",l-1,r);
        }
        //添加右括号
        if(r != 0 && !s.equals("") && s.charAt(s.length()-1)  == '('){
            helper(curRst + ")",s.substring(0,s.length()-1),l,r-1);
        }

    }

    private static void printList(){
        for(int i = 0; i < res.size(); i++){
            System.out.println(res.get(i));
        }
    }

    public static void main(String[] args) {
        generateParenthesis(3);
        printList();
    }
}