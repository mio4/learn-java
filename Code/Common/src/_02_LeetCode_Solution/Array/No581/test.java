package _02_LeetCode_Solution.Array.No581;

import java.util.Arrays;

public class test {

    public static void main(String[] args) {
        int[] a = {1,2,3};
        int[] b = Arrays.copyOf(a,3);
        int[] c = Arrays.copyOfRange(a,0,3);
        System.out.println(Arrays.toString(b));
        System.out.println(Arrays.toString(c));
    }
}
