package _07_t.s3;

import java.util.ArrayList;
import java.util.List;


class TreeNode{
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x){
        val = x;
    }
}
class Solution {
    List<List<Integer>> res = new ArrayList<>();
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> r = new ArrayList<>();
        helper(root,1);
        for(int i=0; i < res.size(); i++){
            r.add(res.get(i).get(0));
        }
        return r;
    }

    public void helper(TreeNode root,int height){
        if(root == null){
            return;
        }

        List<Integer> t;
        if(res.size() < height){
            t = new ArrayList<>();
            res.add(t);
        }
        else{
            t = res.get(height-1);
        }

        t.add(root.val);

        helper(root.right,height+1);
        helper(root.left,height+1);
    }
}
