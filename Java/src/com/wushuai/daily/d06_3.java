package com.wushuai.daily;

import org.junit.Test;

/**
 * <p>d06_3</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-22 11:24
 */
public class d06_3 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    /**
     * 6.21 124. 二叉树中的最大路径和
     *
     * @param root
     * @return
     */
    public int maxPathSum(TreeNode root) {
        int[] res = new int[1];
        res[0] = Integer.MIN_VALUE;
        maxPathSum(root, res);
        return res[0];
    }

    int maxPathSum(TreeNode root, int[] res) {
        if (root == null) {
            return 0;
        }
        // 递归计算左右子节点的最大贡献值
        // 只有在最大贡献值大于 0 时，才会选取对应子节点
        int left = Math.max(maxPathSum(root.left, res), 0);
        int right = Math.max(maxPathSum(root.right, res), 0);
        // 节点的最大路径和取决于该节点的值与该节点的左右子节点的最大贡献值
        int max = root.val + left + right;
        // 更新答案
        res[0] = Math.max(res[0], max);
        // 返回节点的最大贡献值
        return root.val + Math.max(left, right);
    }


    /**
     * 6.22 面试题 16.18. 模式匹配
     *
     * @param pattern
     * @param value
     * @return
     */
    public boolean patternMatching(String pattern, String value) {
        return false;
    }


    /**
     * 6.23 67. 二进制求和
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        return null;
    }


    /**
     * 6.24 16. 最接近的三数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {
        return 0;
    }


    @Test
    public void test() {
        d06_3 o = new d06_3();

    }
}
