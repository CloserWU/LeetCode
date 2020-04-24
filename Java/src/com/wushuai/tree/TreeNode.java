package com.wushuai.tree;

import java.util.Objects;

/**
 * <p>TreeNode</p>
 * <p>root</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-24 10:43
 */
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TreeNode treeNode = (TreeNode) o;
        return val == treeNode.val &&
                Objects.equals(left, treeNode.left) &&
                Objects.equals(right, treeNode.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(val, left, right);
    }
}
