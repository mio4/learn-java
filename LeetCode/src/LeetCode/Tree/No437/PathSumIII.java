package LeetCode.Tree.No437;

class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
 }
public class PathSumIII {
    private int result;
    public int pathSum(TreeNode root, int sum) {
        int[] tmp = new int[1];
        recursion(1,root,tmp,sum);
        return result;
    }

    private void recursion(int height,TreeNode root,int[] memory,int sum){
        int[] tmp = new int[height];

        if(root.left != null){
            recursion(height+1,root.left,sum);
        }
        if(root.right != null){
            recursion(height+1,root.right,sum);
        }
    }
}
