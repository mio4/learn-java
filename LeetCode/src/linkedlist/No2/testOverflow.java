package linkedlist.No2;

import java.math.BigInteger;

public class testOverflow {

    public static void main(String[] args){
        long n1 = 9999999991L;
        int n2 = 9;
        System.out.println((n1+n2)%10);
        System.out.println((n1+n2));
    }
}
