









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







































