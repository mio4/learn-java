package _01_Algorithm.Sort;

import org.junit.Test;

import java.util.Arrays;

public class testArrays {

    @Test
    public void test(){
        int[] a = {1,2,3};
        int[] b = Arrays.copyOf(a,3);
        System.out.println(Arrays.toString(b));
    }
}
