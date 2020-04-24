package com.wushuai.tree;

import org.junit.Test;

import java.util.*;

/**
 * <p>Rob</p>
 * <p>337</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-24 10:42
 */
public class Rob {

    /**
     * 337. 打家劫舍 III
     * 暴力 926ms
     *
     * @param root
     * @return
     */
    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int res = root.val;
        if (root.left != null) {
            res += rob(root.left.left) + rob(root.left.right);
        }
        if (root.right != null) {
            res += rob(root.right.left) + rob(root.right.right);
        }
        return Integer.max(res, rob(root.left) + rob(root.right));
    }


    /**
     * 记忆化递归  4ms
     *
     * @param root
     * @return
     */
    public int rob_v2(TreeNode root) {
        Map<TreeNode, Integer> map = new HashMap<>();
        return robInternal(map, root);
    }

    int robInternal(Map<TreeNode, Integer> memo, TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (memo.containsKey(root)) {
            return memo.get(root);
        }
        int res = root.val;
        if (root.left != null) {
            res += robInternal(memo, root.left.left) + robInternal(memo, root.left.right);
        }
        if (root.right != null) {
            res += robInternal(memo, root.right.left) + robInternal(memo, root.right.right);
        }
        res = Integer.max(res, robInternal(memo, root.left) + robInternal(memo, root.right));
        memo.put(root, res);
        return res;
    }


    /**
     * 树状dp 0ms
     * https://leetcode-cn.com/problems/house-robber-iii/solution/san-chong-fang-fa-jie-jue-shu-xing-dong-tai-gui-hu/
     *
     * @param root
     * @return
     */
    public int rob_v3(TreeNode root) {
        int[] res = robInternal(root);
        return Integer.max(res[0], res[1]);
    }

    int[] robInternal(TreeNode root) {
        if (root == null) {
            return new int[2];
        }
        int[] res = new int[2];
        int[] left = robInternal(root.left);
        int[] right = robInternal(root.right);
        res[0] = Integer.max(left[0], left[1]) + Integer.max(right[0], right[1]);
        res[1] = left[0] + right[0] + root.val;
        return res;
    }

    @Test
    public void test() {
        Rob o = new Rob();

    }
}
