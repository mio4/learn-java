package other.KaoYan.Chap7;

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
