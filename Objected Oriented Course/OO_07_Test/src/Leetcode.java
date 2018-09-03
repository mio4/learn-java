
public class Leetcode {
	public static void main(String[] args) {
		int nums[] = {1,0,1,1,0,1};
		System.out.println(findMaxConsecutiveOnes(nums));
	}
	
	public static int findMaxConsecutiveOnes(int[] nums) {
        int maxCnt = 0;
        int cnt = 0;
        if(nums != null){
            for(int n : nums){
                if(n == 1){
                    cnt++;
                    maxCnt = cnt > maxCnt ? cnt : maxCnt;
                }
                else {
                    cnt = 0;
                }
            }
        }
        return maxCnt;
    }
}
