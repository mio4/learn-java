package ttt;

import java.util.Scanner;

public class Main3 {
    public static void main(String[] args) {
        //1. 获取输入
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int[] width = new int[N];
        int[] weight = new int[N];
        for(int i=0; i < N; i++){
            width[i] = sc.nextInt();
        }
        for(int i=0; i < N; i++){
            weight[i] = sc.nextInt();
        }

        //2. 处理数据
        // 上方边长 < 下方边长
        // 上方weight sum <= 下方*7
        // max_height

    }
}
