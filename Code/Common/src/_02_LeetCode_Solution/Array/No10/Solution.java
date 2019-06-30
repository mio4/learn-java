package _02_LeetCode_Solution.Array.No10;

class Solution {
    //不合法的情况 ** | *
//    public static boolean isMatch(String s, String p) {
//        if(s == null && p == null){
//            return true;
//        }
//        if(s == null){
//            return false;
//        }
//        if(p == null){
//            return false;
//        }
//        int index = 0; //标记p
//        for(int i=0; i < s.length();){
//            //前置判断
//            if(index >= p.length()){
//                return false;
//            }
//
//            if(isEqual(s.charAt(i),p.charAt(index))){
//                i++;
//                index++;
//            }
//            else{
//                if(index+1 < p.length() &&  p.charAt(index+1) == '*'){ //后面的*置空
//                    index+=2;
//                }
//                else if(p.charAt(index) == '*' && isEqual(s.charAt(i),p.charAt(index-1))){ //是*并且前面相同
//                    i++;
//                }
//                else if(p.charAt(index) == '*' && !isEqual(s.charAt(i),p.charAt(index-1))){ //是*并且不同
//                    index++;
//                }
//                else{
//                    return false;
//                }
//            }
//        }
//        //1. index == p.length()
//        //2. index == p.length()-1 && p.charAt(index) == '*'
//        //3. else return false;
//        if(index == p.length()){
//            return true;
//        }
//        if(index == p.length()-1 && p.charAt(index) == '*'){
//            return true;
//        }
//        return false;
//    }

//    private static  boolean isEqual(char a, char b){
//        return ((a == b) || (b == '.'));
//    }

//    public static void main(String[] args) {
//        String s1 = "aab";
//        String p1 = "c*a*b*";
//        System.out.println(isMatch(s1,p1));
//
//        String s2 = "aa";
//        String p2 = "a";
//        System.out.println(isMatch(s2,p2));
//
//        String s3 = "mississippi";
//        String p3 = "mis*is*p*";
//        System.out.println(isMatch(s3,p3));
//
//        String s4 = "ab";
//        String p4 = ".*";
//        System.out.println(isMatch(s4,p4));
//
//        String s5 = "aa";
//        String p5 = "a*";
//        System.out.println(isMatch(s5,p5));
//
//        String s6 = "aab";
//        String p6 = "c*a*b";
//        System.out.println(isMatch(s6,p6));
//
//        String s7 = "aaa";
//        String p7 = "a*a";
//        System.out.println(isMatch(s7,p7));
//    }
}
