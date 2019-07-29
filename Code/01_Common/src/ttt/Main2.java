package ttt;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main2 {
    public static void main(String[] args) {
        //平均返回时间最小 = （任务执行完成时刻 - 0） / 平均;
        //获取输入
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int M = sc.nextInt();
        int[] weight = new int[N];
        for(int i=0; i < N; i++){
            weight[i] = sc.nextInt();
        }
        int[][] marks = new int[N][N];
        for(int i=0; i < M; i++){
            int n1 = sc.nextInt();
            int n2 = sc.nextInt();
            marks[n2-1][n1-1] = 1;
        }
        int[] isDone = new int[N];
        int left = N;

        //处理过程
        List<Integer> res = new ArrayList<>();
        while(left > 0){
            //找到所有能够执行的任务
            List<Integer> list = new ArrayList<>();
            for(int i=0; i < N; i++){
                if(isFinished(i,marks) && isDone[i] == 0){
                    list.add(i);
                }
            }
            //找到这些任务中执行时间最小的一个
            int minIndex = Integer.MAX_VALUE;
            int minWeight = Integer.MAX_VALUE;
            for(int i=0; i < list.size();i++){
                if(minWeight > weight[list.get(i)]){
                    minWeight = weight[list.get(i)];
                    minIndex = list.get(i);
                }
            }
            //执行任务
            isDone[minIndex] = 1;
            //更新marks数组
            for(int i=0; i < N; i++){
               marks[i][minIndex] = 0;
            }
            res.add(minIndex);
            left--;
        }

        //输出结果
        for(int i=0; i < res.size()-1;i++){
            System.out.print((res.get(i)+1) + " ");
        }
        System.out.print((res.get(res.size()-1)+1));
    }

    private static boolean isFinished(int index,int[][] marks){ //判断任务是否完成
        for(int i=0; i < marks[0].length;i++){
            if(marks[index][i] == 1){
                return false;
            }
        }
        return true;
    }
}
