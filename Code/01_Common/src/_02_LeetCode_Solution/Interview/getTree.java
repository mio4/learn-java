package _02_LeetCode_Solution.Interview;

public class getTree {

    public static TreeNode get1(){
        TreeNode root = new TreeNode(1);
        TreeNode r1 = new TreeNode(2);
        TreeNode r2 = new TreeNode(3);
        TreeNode r3 = new TreeNode(4);
        TreeNode r4 = new TreeNode(5);

        root.right = r1;
        r1.right = r2;
        r2.right = r3;
        r3.right = r4;

        return root;
    }
}
