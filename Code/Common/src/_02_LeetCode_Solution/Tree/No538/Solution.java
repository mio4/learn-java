package _02_LeetCode_Solution.Tree.No538;

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
    private static List<Integer> nums = new ArrayList<>();
    public TreeNode convertBST(TreeNode root) {
        //1. 遍历二叉树，获取所有的数
        getArray(root);
        //2. 遍历二叉树，重建二叉树
        convert(root);
        return root;
    }

    private void getArray(TreeNode root){
        if(root != null){
            nums.add(root.val);
            getArray(root.left);
            getArray(root.right);
        }
    }

    private void convert(TreeNode root){
        if(root != null){
            root.val += getLargerSum(root.val);
            convert(root.left);
            convert(root.right);
        }
    }

    private int getLargerSum(int val){
        int sum = 0;
        for(int i=0; i < nums.size(); i++){
            if(val < nums.get(i)){
                sum += nums.get(i);
            }
        }
        return sum;
    }
}