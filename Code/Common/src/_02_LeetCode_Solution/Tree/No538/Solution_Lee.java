package _02_LeetCode_Solution.Tree.No538;

public class Solution_Lee {
    public static TreeNode convertBST(TreeNode root) {
        helper(root, 0);
        return root;
    }

    private static int helper(TreeNode node, int val){
        if(node == null)
            return 0;
        node.val += val + helper(node.right, val);
        return node.val + helper(node.left, node.val) - val;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        TreeNode n1 = new TreeNode(2);
        TreeNode n2 = new TreeNode(13);
        root.left = n1;
        root.right = n2;

        convertBST(root);
    }
}
