package _02_LeetCode_Solution.Array.No10;

public class Solution3 {
    public static boolean isMatch(String s, String p) {
        //TODO 正则表达式-优化-使用动态规划 DP
        return false;
    }

    public static void main(String[] args) {
        String sss3 = "";
        String ppp3 = "c*c*";
        System.out.println(isMatch(sss3,ppp3));


        String sss4 = "bbbba";
        String ppp4 = ".*a*a";
        System.out.println(isMatch(sss4,ppp4));

        String sss5 = "ab";
        String ppp5 = ".*c";
        System.out.println(isMatch(sss5,ppp5)); //false

        String ss0 = "aaa";
        String pp0 = "aaaa";
        System.out.println(isMatch(ss0,pp0)); //false


        String s0 = "a";
        String p0 = "ab*";
        System.out.println(isMatch(s0,p0)); //true

        String s1 = "aab";
        String p1 = "c*a*b*";
        System.out.println(isMatch(s1,p1)); //true

        String s2 = "aa";
        String p2 = "a";
        System.out.println(isMatch(s2,p2)); //false

        String s3 = "mississippi";
        String p3 = "mis*is*p*";            //false
        System.out.println(isMatch(s3,p3));

        String s4 = "ab";
        String p4 = ".*";                   //true
        System.out.println(isMatch(s4,p4));

        String s5 = "aa";
        String p5 = "a*";                   //true
        System.out.println(isMatch(s5,p5));

        String s6 = "aab";
        String p6 = "c*a*b";                //true
        System.out.println(isMatch(s6,p6));

        String s7 = "aaa";
        String p7 = "a*a";                  //true
        System.out.println(isMatch(s7,p7));
    }
}
