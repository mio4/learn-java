package ttt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //获取输入
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String[] words = s.split(" ");

//        if(words.length == 2){
//            if(contain2(words[0],words[1])){
//                System.out.println("true");
//            }
//            else{
//                System.out.println("false");
//            }
//        }
//        else{
//
//        }
        //打印结果
       // System.out.println("false");


    }

    private static void helper(String[] words){

    }

    private static void permutation(String[] words){
        swap(words,0,0);
    }

    private static void swap(String[] words,int i,int j){

    }


    private static boolean contain1(String s1,String s2){
        int[] a1 = new int[128];
        int[] a2 = new int[128];
        for(int i=0; i < s1.length(); i++){
            a1[s1.charAt(i)] = 1;
        }
        for(int i=0; i < s2.length(); i++){
            a2[s2.charAt(i)] = 1;
        }
        for(int i=0; i < 127; i++){
            if(a1[i]==1 && a2[i]==1){
                return true;
            }
        }
        return false;
    }

    private static boolean contain2(String s1,String s2){
        int[] a1 = new int[128];
        int[] a2 = new int[128];
        for(int i=0; i < s1.length(); i++){
            a1[s1.charAt(i)] = 1;
        }
        for(int i=0; i < s2.length(); i++){
            a2[s2.charAt(i)] = 1;
        }
        int mark = 0;
        for(int i=0; i < 127; i++){
            if(a1[i]==1 && a2[i]==1){
                mark++;
            }
        }
        if(mark >= 2){
            return true;
        }
        return false;
    }

}
