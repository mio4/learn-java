package _06_Useless;

/**
 * some problem
 */
public class Shaizi {
    public static double cal(int n, int sum){
        //递归终止条件
        if(6*n < sum || (n >= 1 && sum < 0)){
            return 0;
        }
        if(n < 1 && sum != 0){
            return 0;
        }
        if(n < 1 && sum == 0){
            return 1;
        }
        if(n == 1){
            return (double)1/6;
        }

        double res = 0;
        for(int i=1; i <= 6; i++){
            res += cal(1,i)*cal(n-1,sum-i);
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(cal(1,3)); //1/6
        System.out.println(cal(2,12)); //1/36
        System.out.println(cal(2,3)); //2/36
        System.out.println(cal(3,3)); //1/(6*6*6)
        System.out.println(cal(3,4)); //3/(6*6*6)
    }
}
