package LeetCode.Array.No49;

import java.util.Arrays;

public class test {
    public static void main(String[] args) {
        String s = "eat";
        char[] c = s.toCharArray();
        Arrays.sort(c);
        s = new String(c);
        System.out.println(s);
    }
}
