package com.wushuai.daily;

import org.junit.Test;

/**
 * <p>d07_1</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-03 11:07
 */
public class d07_1 {

    /**
     * 7.01 718. 最长重复子数组
     * 令 dp[i][j] 表示 A[i:] 和 B[j:] 的最长公共前缀
     * 那么答案即为所有 dp[i][j] 中的最大值。
     * 如果 A[i] == B[j]，那么 dp[i][j] = dp[i + 1][j + 1] + 1，
     * 否则 dp[i][j] = 0。
     *
     * @param A
     * @param B
     * @return
     */
    public int findLength(int[] A, int[] B) {
        int[][] dp = new int[A.length][B.length];
        int res = 0;
        for (int i = 0; i < A.length; i++) {
            dp[i][B.length - 1] = A[i] == B[B.length - 1] ? 1 : 0;
        }
        for (int i = 0; i < B.length; i++) {
            dp[A.length - 1][i] = B[i] == A[A.length - 1] ? 1 : 0;
        }
        for (int i = A.length - 2; i >= 0; i--) {
            for (int j = B.length - 2; j >= 0; j--) {
                dp[i][j] = A[i] == B[j] ? dp[i + 1][j + 1] + 1 : 0;
                res = Integer.max(res, dp[i][j]);
            }
        }
        return res;
    }

    /**
     * 7.02 378. 有序矩阵中第K小的元素
     * https://leetcode-cn.com/problems/kth-smallest-element-in-a-sorted-matrix/solution/you-xu-ju-zhen-zhong-di-kxiao-de-yuan-su-by-leetco/
     *
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int left = matrix[0][0], right = matrix[n - 1][n - 1];
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (check(mid, k, matrix)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    boolean check(int mid, int k, int[][] matrix) {
        int n = matrix.length;
        int i = n - 1, j = 0;
        int num = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= mid) {
                num += i + 1;
                j++;
            } else {
                i--;
            }
        }
        return num >= k;
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
     * 7.03 108. 将有序数组转换为二叉搜索树
     *
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        return helper(nums, 0, nums.length - 1);
    }

    TreeNode helper(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        if (left == right) {
            return new TreeNode(nums[left]);
        }
        int mid = left + ((right - left) >> 1);
        TreeNode root = new TreeNode(nums[mid]);
        // right要放到left上面，因为mid是地板除
        root.right = helper(nums, mid + 1, right);
        root.left = helper(nums, left, mid - 1);
        return root;
    }

    @Test
    public void test() {
        d07_1 o = new d07_1();

    }
}
