package _02_LeetCode_Solution.Array.No581;

import java.util.Arrays;

class Solution {
    public static int findUnsortedSubarray(int[] nums) {
        if(nums == null || nums.length == 0 || isAsc(nums)){
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for(int i=0; i < nums.length;i++){
            for(int j=i; j <= nums.length; j++){
                int[] a1 = Arrays.copyOfRange(nums,0,i);
                int[] a2 = Arrays.copyOfRange(nums,i,j);
                int[] a3 = Arrays.copyOfRange(nums,j,nums.length);
                if(isAsc(a1) && isAsc(a3)){
                    Arrays.sort(a2);
                    int a1_max = a1.length == 0 ? Integer.MIN_VALUE : a1[a1.length-1];
                    int a2_min = a2.length == 0 ? Integer.MAX_VALUE : a2[0];
                    int a2_max = a2.length == 0 ? Integer.MIN_VALUE : a2[a2.length-1];
                    int a3_min = a3.length == 0 ? Integer.MAX_VALUE : a3[0];
                    if(a1_max <= a2_min && a2_max <= a3_min && a1_max <= a3_min){
                        min = (j-i < min) ? (j-i) : min;
                    }
                }
            }
        }

        return min;
    }

    //判断数组是否升序
    private static boolean isAsc(int[] a){
        for(int i=0; i < a.length-1; i++){
            if(a[i] > a[i+1]){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        int[] a = {2, 6, 4, 8, 10, 9, 15};
        System.out.println(findUnsortedSubarray(a));

        int[] a2 = {1,2,3,4};
        System.out.println(findUnsortedSubarray(a2));

        int[] a3 = {2,1};
        System.out.println(findUnsortedSubarray(a3));

        int[] a4 = {1,3,2,3,3};
        System.out.println(findUnsortedSubarray(a4));
    }
}
