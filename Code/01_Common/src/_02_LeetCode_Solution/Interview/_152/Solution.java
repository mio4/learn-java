package _02_LeetCode_Solution.Interview._152;

public class Solution {
    public static int maxProduct(int[] nums) {
        int[] dp = new int[nums.length];
        //初始化DP数组
        for(int i=0; i < nums.length; i++){
            dp[i] = nums[i];
        }

        //状态转移过程
        for(int i=1; i < nums.length; i++){
            int cur = nums[i];
            int max = cur;
            for(int j=i-1;j>=0;j--){
                cur *= nums[j];
                max = Math.max(max,cur);
            }
            dp[i] = Math.max(dp[i-1],max);
        }

        return dp[nums.length-1];
    }

    public static void main(String[] args) {
        int[] a1 = {2,3,-2,4};
        System.out.println(maxProduct(a1));
        int[] a2 = {-2,0,-1};
        System.out.println(maxProduct(a2));
    }
}
