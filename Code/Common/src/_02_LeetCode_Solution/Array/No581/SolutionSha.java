package _02_LeetCode_Solution.Array.No581;

public class SolutionSha {
    public static int findUnsortedSubarray(int[] nums) {
        int left,right,i,j;
        boolean flag;
        left = 0;
        right = -1;
        for( i=0, flag = true;i<nums.length && flag;i++){
            for( j=i+1;j<nums.length;j++){
                if(nums[j] < nums[i]){
                    left = i;
                    flag=false;
//                    System.out.println(left);
                    break;
                }
            }
        }

        for(i=nums.length-1, flag = true;i>=0 && flag;i--){
            for( j=i-1;j >= 0;j--){
                if(nums[j] > nums[i]){
                    right = i;
                    flag =false;
                    break;
                }
            }
        }
//        System.out.println(right);
//        System.out.println();
        return right-left+1;

    }

    public static void main(String[] args) {
        int[] a = {2, 6, 4, 8, 10, 9, 15};
        System.out.println(findUnsortedSubarray(a));

        int[] a2 = {1,2,3,4};
        System.out.println(findUnsortedSubarray(a2));

        int[] a3 = {2,1};
        System.out.println(findUnsortedSubarray(a3));
    }
}
