package ttt.ttt;

import java.util.*;


class Node{
    public char c;
    public int cnt;
}
public class Main {
    private static Comparator<Node> comparator = new Comparator<Node>() {
        @Override
        public int compare(Node o1, Node o2) {
            return o1.cnt - o2.cnt;
        }
    };

    public static void main(String[] args) {
        PriorityQueue<Node> queue = new PriorityQueue<Node>(comparator);
        HashMap<Character,Integer> map = new HashMap<>();

        //获取输入
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        for(int i=0; i < s.length(); i++){
            char c = s.charAt(i);
            if(map.containsKey(c)){
                map.put(c,map.get(c)+1);
            }
            else{
                map.put(c,1);
            }
        }

        //优先队列处理
        Set<Map.Entry<Character, Integer>> entries = map.entrySet();
        for(Map.Entry entry : entries){
            Node node = new Node();
            node.c = (char)entry.getKey();
            node.cnt = (int)entry.getValue();
            queue.add(node);
        }

        Iterator<Node> iterator = queue.iterator();
        //尝试通过打表
        int[][] convert = new int[10][10];
        convert[1] = new int[]{1}; //只有一种字符
        convert[2] = new int[]{1,1}; //只有两种字符
        convert[3] = new int[]{2,2,2}; //只有三种字符
        convert[4] = new int[]{2,2,2,3}; //只有三种字符
        convert[5] = new int[]{};
        convert[6] = new int[]{2,2,2,3,4,4};
        int len = 0;
        int i = 0;
        while(iterator.hasNext()){
            Node node = iterator.next();
            //加密算法
            len += node.cnt * convert[queue.size()][i];
            i++;
        }
        System.out.println(len);
    }
}
