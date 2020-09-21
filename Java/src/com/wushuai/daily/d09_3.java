package com.wushuai.daily;

/**
 * <p>d09_3</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-09-21 9:08
 */
public class d09_3 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    /**
     * 9.21 538. 把二叉搜索树转换为累加树
     *
     * @param root
     * @return
     */
    public TreeNode convertBST(TreeNode root) {
        dfs(root);
        return root;
    }

    // 比当前节点大的所有节点值的和
    int val = 0;

    void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        dfs(root.right);
        val += root.val;
        root.val += val;
        dfs(root.left);
    }
}
