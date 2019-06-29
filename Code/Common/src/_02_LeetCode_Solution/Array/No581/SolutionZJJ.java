package _02_LeetCode_Solution.Array.No581;

import java.util.Arrays;

public class SolutionZJJ {
    public static int findUnsortedSubarray(int[] nums) {
        int[] asc = Arrays.copyOf(nums,nums.length);
        Arrays.sort(asc);
        int len = nums.length;
        int i = 0, j = len-1;
        while(i < len && nums[i] == asc[i]) i++;
        while(j > i && nums[j] == asc[j]) j--;

        return j-i+1;
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