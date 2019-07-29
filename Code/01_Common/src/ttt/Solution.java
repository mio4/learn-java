package ttt;

import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //输出数据处理
        String line1 = sc.nextLine();
        String line2 = sc.nextLine();
        String[] st = line1.split(" ");
        int[] nums1 = new int[st.length];
        for(int i=0; i < st.length; i++){
            nums1[i] = Integer.valueOf(st[i]);
        }
        String[] st2 = line2.split(" ");
        int[] nums2 = new int[st2.length];
        for(int i=0; i < st2.length; i++){
            nums2[i] = new Integer(st2[i]);
        }

        //进行判断
        int index=0;
        for(int i=0; i < nums1.length-1; i++){
            if(nums1[i] > nums1[i+1]){
                index = i+1;
                break;
            }
        }

        int max = Integer.MIN_VALUE;
        for(int i=0; i < nums2.length; i++){
            if(index == nums1.length-1){ //如果x是nums1的最后一个元素
                if(nums2[i] > nums1[index-1]){
                    max = Math.max(max,nums2[i]);
                }
            }
            else{ //一般情况
                if(nums2[i] > nums1[index-1] && nums2[i] < nums1[index+1]){
                    max = Math.max(max,nums2[i]);
                }
            }
        }
        //输出结果
        if(max == Integer.MIN_VALUE){
            //重新判断
            for(int i=0; i < nums2.length; i++){
                if (index - 1 == 0) { //第一个元素
                    if(nums2[i]  < nums1[index]){
                        max = Math.max(max,nums2[i]);
                    }
                }
                else{
                    if(nums2[i] < nums1[index] && nums2[i] > nums2[index-2]){
                        max = Math.max(max,nums2[i]);
                    }
                }
            }
            if(max == Integer.MIN_VALUE){
                System.out.println("NO");
            }
            else{
                nums1[index-1] = max;
                for(int i=0; i < nums1.length-1;i++){
                    System.out.print(nums1[i] + " ");
                }
                System.out.println(nums1[nums1.length-1]);
            }
        }
        else{
            nums1[index] = max;
            for(int i=0; i < nums1.length-1;i++){
                System.out.print(nums1[i] + " ");
            }
            System.out.println(nums1[nums1.length-1]);
        }


    }
}
