package other.KaoYan.Chap7;

import java.util.Arrays;

/**
 * http://www.cnblogs.com/chengxiao/p/6129630.html
 * （1）首先要定义大顶堆：arr[0...n-1] 则 arr[i] > arr[2*i+1] 并且 arr[2*i+2]
 * （2）对于最开始的数组生成一个大顶堆：从最后一个非叶子节点开始调整
 * （3）对于生成的大顶堆，根节点元素是整个数组中最大的元素
 *      （i）首先将根节点元素和数组最后一个元素置换，则最后一个元素是最大的元素【有一点冒泡排序的处理味道】
 *      （ii）上面的操作会打乱大顶堆，所以需要对于arr[0...n-2]重新生成大顶堆
 * （4）堆排序中涉及到的树的概念，但是并没有显示的存储树，而是根据数组元素位置和树节点之间的对应关系调整假想中的树，有点意思
 */
public class HeapSort {

    public static void heapSort(int[] arr){
        for(int i=arr.length/2-1; i >=0 ; i--){
            adjustHeap(arr,i,arr.length);
        }

        for(int j=arr.length-1;j > 0; j--){
            swap(arr,0,j);
            adjustHeap(arr,0,j); //这里只需要调整被打乱的根节点即可
        }
    }

    public static void adjustHeap(int[] arr, int i, int length){ //i的含义：调整树中第i个节点【和左右子树对比】
        int temp = arr[i];
        for(int k = 2*i+1; k < length; k = 2*k+1){
            if(k+1 < length && arr[k] < arr[k+1])
                k += 1;

            if(arr[k] > temp){
                arr[i] = arr[k];
                i = k;
            }else{
                break;
            }
        }
        arr[i] = temp;
    }

    public static void swap(int[] arr, int a, int b){
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }

    public static void main(String[] args){
        int[] arr = {9,8,7,6,5,4,3,2,1};
        //int[] arr = {4,6,8,5,9};
        heapSort(arr);
        System.out.println(Arrays.toString(arr));
    }
}
