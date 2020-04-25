package com.wushuai.tree;

import org.junit.Test;

/**
 * <p>BuildTree</p>
 * <p>105</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-25 10:20
 */
public class BuildTree {
    int idx = 0;

    TreeNode build(int left, int right, int[] preOrder, int[] inOrder) {
        if (left > right || idx >= preOrder.length) {
            return null;
        }
        if (left == right) {
            return new TreeNode(preOrder[idx++]);
        }
        TreeNode root = new TreeNode(preOrder[idx++]);
        for (int i = left; i <= right ; i++) {
            if (inOrder[i] == preOrder[idx - 1]) {
                root.left = build(left, i - 1,  preOrder, inOrder);
                root.right = build(i + 1, right,  preOrder, inOrder);
                break;
            }
        }
        return root;
    }

    /**
     * 105. 从前序与中序遍历序列构造二叉树
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(0, preorder.length - 1, preorder, inorder);
    }

    @Test
    public void test() {
        BuildTree o = new BuildTree();
        System.out.println(o.buildTree(new int[]{3,9,8,2,4,20,15,7}, new int[]{2,8,4,9,3,15,20,7}));
    }
}
