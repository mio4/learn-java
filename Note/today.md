









一维DP

最长乘积子序列 

-  要求是连续的子序列

```java
//使用两个DP数组->简化到使用两个标记位
//imax：包含当前子序列的最大乘积
//imin：包含当前子序列的最小乘积
//如果遇到负数，那么imax*当前负数 "可能" 会成为最小的数，所以乘积之前需要翻转
public static int maxProduct(int[] nums) {
    int imax = 1;
    int imin = 1;
    int max = Integer.MIN_VALUE;
    for(int i=0; i < nums.length; i++){
      if(nums[i] <= 0){
        int t = imax;
        imax = imin;
        imin = t;
      }
      imax = Math.max(nums[i],nums[i] * imax);
      imin = Math.min(nums[i],nums[i] * imin);
      max = Math.max(max,imax);
    }
    return max;
}
```









494. 目标和 

```
输入: nums: [1, 1, 1, 1, 1], S: 3
输出: 5
解释: 

-1+1+1+1+1 = 3
+1-1+1+1+1 = 3
+1+1-1+1+1 = 3
+1+1+1-1+1 = 3
+1+1+1+1-1 = 3

一共有5种方法让最终目标和为3。
```



```java
    //使用暴力递归——不考虑时间复杂度
		private static int res = 0;
    public static int findTargetSumWays(int[] nums, int S) {
        helper(nums,0,S);
        return res;
    }

    private static void helper(int[] nums,int index,int sum){
        if(index == nums.length){
            if(sum == 0)
                res++;
            return;
        }
        helper(nums,index+1,sum-nums[index]);
        helper(nums,index+1,sum+nums[index]);
    }
```



```java
//TODO 继续优化 使用DP 或者转换为01背包问题

```









347.出现次数最多的5个URL/出现次数排名前K的元素

```java
//1. 使用Hashmap+优先队列+自定义统计类
class Item{
    int val = 0;
    int cnt = 0;
}
public class Solution {
    private static Comparator<Item> comparator = new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {
            return o2.cnt - o1.cnt;
        }
    };

    private static PriorityQueue<Item> queue = new PriorityQueue<>(comparator);
    public static List<Integer> topKFrequent(int[] nums, int k) {
        HashMap<Integer,Integer> map = new HashMap<>();
        for(int i=0; i < nums.length; i++){
            if(map.containsKey(nums[i])){
                map.put(nums[i],map.get(nums[i])+1);
            }
            else{
                map.put(nums[i],1);
            }
        }

        Iterator<Map.Entry<Integer,Integer>> iterator = map.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<Integer,Integer> entry = iterator.next();
            Item item = new Item();
            item.val = entry.getKey();
            item.cnt = entry.getValue();
            queue.add(item);
        }

        List<Integer> res = new ArrayList<>();
        for(int i=0; i < k; i++){
            res.add(queue.poll().val);
        }
        return res;
    }
}
```



















221.最大正方形

> 这种题居然都可以用DP解决

`if(dp[i][j] == '1') dp[i][j] = min(dp[i-1][j-1],dp[i-1][j],dp[i][j-1])+1`

```java
    public static int maximalSquare(char[][] matrix) {
        if(matrix.length == 0){
            return 0;
        }
        int[][] dp = new int[matrix.length][matrix[0].length];
        int max_len = 0;
        for(int i=0; i < matrix.length; i++){
            for(int j=0; j < matrix[0].length; j++){
                if(i == 0 || j == 0){
                    dp[i][j] = matrix[i][j] - '0';
                }
                else{
                    if(matrix[i][j] == '1'){
                        int min = Math.min(dp[i-1][j-1],dp[i-1][j]);
                        min = Math.min(min,dp[i][j-1]);
                        dp[i][j] = min + 1;
                    }
                }
                max_len = Math.max(max_len,dp[i][j]);
            }
        }
        return max_len*max_len;
    }
```



































239.滑动窗口最大值



```java

```

















































