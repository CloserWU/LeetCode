package com.wushuai.daily;

import org.junit.Test;

import java.util.Arrays;

/**
 * <p>d11</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-05-17 20:15
 */
public class d11 {

    /**
     * 5.04
     * 45. 跳跃游戏 II
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 50000000);
        dp[0] = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 1; j <= nums[i]; j++) {
                if (i + j < nums.length) {
                    dp[i + j] = Integer.min(dp[i + j], dp[i] + 1);
                }
            }

        }
        return dp[dp.length - 1];
    }

    public int jump_v2(int[] nums) {
        int ans = 0;
        int end = 0;
        int maxPos = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            maxPos = Integer.max(maxPos, nums[i] + i);
            if (i == end) {
                end = maxPos;
                ans++;
            }
        }
        return ans;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 5.05
     * 98. 验证二叉搜索树
     * WRONG !!!
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left != null && root.val <= root.left.val) {
            return false;
        }
        if (root.right != null && root.val >= root.right.val) {
            return false;
        }
        return isValidBST(root.left) && isValidBST(root.right);
    }

    boolean helper(TreeNode root, Integer low, Integer high) {
        if (root == null) {
            return true;
        }
        if (low != null && root.val <= low) {
            return false;
        }
        if (high != null && root.val >= high) {
            return false;
        }
        return helper(root.right, root.val, high) && helper(root.left, low, root.val);
    }

    public boolean isValidBST_v2(TreeNode root) {
        return helper(root, null, null);
    }

    @Test
    public void test() {
        d11 o = new d11();
        System.out.println(o.jump_v2(new int[]{2, 3, 1, 1, 4}));
    }
}
