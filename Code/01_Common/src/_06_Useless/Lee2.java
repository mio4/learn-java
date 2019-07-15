package _06_Useless;

import java.util.ArrayList;
import java.util.List;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) { val = x; }
}

public class Lee2 {
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        List<Integer> s1 = new ArrayList<>();
        List<Integer> s2 = new ArrayList<>();
        ListNode t1 = l1;
        while(t1 != null){
            s1.add(t1.val);
            t1 = t1.next;
        }
        ListNode t2 = l2;
        while(t2 != null){
            s2.add(t2.val);
            t2 = t2.next;
        }

        List<Integer> res = new ArrayList<>();
        int i = 0, j = 0;
        int flow = 0;
        int cur = 0;
        while(i < s1.size() && j < s2.size()){
            cur = (s1.get(i) + s2.get(j) + flow) % 10;
            flow =(s1.get(i) + s2.get(j) + flow) / 10;
            res.add(cur);
            i++;
            j++;
        }
        while(i < s1.size()){
            cur = (s1.get(i) + flow ) %10;
            flow= (s1.get(i) + flow ) /10;
            res.add(cur);
            i++;
        }
        while(j < s2.size()){
            cur = (s2.get(j) + flow) %10;
            flow =(s2.get(j) + flow) /10;
            res.add(cur);
            j++;
        }
        //对于最后进位的判断
        if(flow != 0){
            res.add(flow);
        }
        ListNode head = new ListNode(res.get(0));
        ListNode r = head;
        for(int k=1; k < res.size();k++){
            ListNode t = new ListNode(res.get(k));
            r.next = t;
            r = t;
        }

//        ListNode head = new ListNode(res.get(res.size()-1));
//        ListNode r = head;
//        for(int k=res.size()-2; k >= 0; k--){
//            ListNode t = new ListNode(res.get(k));
//            r.next = t;
//            r = t;
//        }
        return head;
    }



    public static void main(String[] args){
        ListNode t1 = new ListNode(2);
        ListNode t2 = new ListNode(4);
        ListNode t3 = new ListNode(3);
        t1.next = t2;
        t2.next = t3;

        ListNode s1 = new ListNode(5);
        ListNode s2 = new ListNode(6);
        ListNode s3 = new ListNode(4);
        s1.next = s2;
        s2.next = s3;

        addTwoNumbers(t1,s1);

//        ListNode t1 = new ListNode(1);
//        ListNode t2 = new ListNode(8);
//        t1.next = t2;
//
//        ListNode s1 = new ListNode(0);
//        addTwoNumbers(t1,s1);
    }
}
