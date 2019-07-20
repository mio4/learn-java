package _07_t.s1;

import java.util.Arrays;

public class Solution {

    private static int res;

    public static int coinChange(int[] coins, int amount) {
        res = Integer.MAX_VALUE;
        Arrays.sort(coins);
        helper(coins,amount,0);
        return (res == Integer.MAX_VALUE ? -1 : res);
    }

    private static void helper(int[] coins,int cur_amount,int cur){
        if(cur_amount < 0){
            return ;
        }
        else if(cur_amount == 0){
            res = Math.min(res,cur);
            return;
        }
        else{
            for(int i=coins.length-1; i >= 0; i--){
                helper(coins,cur_amount-coins[i],cur+1);
            }
        }
    }

    public static void main(String[] args) {
        int[] nums = {1,2,5};
        System.out.println(coinChange(nums,11));
    }
}
