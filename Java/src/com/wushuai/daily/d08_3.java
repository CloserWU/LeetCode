package com.wushuai.daily;

import org.junit.Test;

/**
 * <p>d08_3</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-21 21:13
 */
public class d08_3 {

    /**
     * 8.21 111. 二叉树的最小深度
     *
     * @param root
     * @return
     */
    public int minDepth(d08_2.TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int left = minDepth(root.left);
            int right = minDepth(root.right);
            if (left == right && left == 0) {
                return 1;
            }
            if (left == 0) {
                return right;
            } else if (right == 0) {
                return left;
            }
            return 1 + Integer.min(left, right);
        }
    }

    @Test
    public void test() {
        d08_3 o = new d08_3();

    }
}
