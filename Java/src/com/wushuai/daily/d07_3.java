package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d07_3</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-21 21:22
 */
public class d07_3 {

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 7.21 95. 不同的二叉搜索树 II
     * https://leetcode-cn.com/problems/unique-binary-search-trees-ii/solution/
     *
     * @param n
     * @return
     */
    public List<TreeNode> generateTrees(int n) {
        if (n == 0) {
            return new LinkedList<>();
        }
        return generateTrees(1, n);
    }

    public List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> allTrees = new LinkedList<>();
        if (start > end) {
            allTrees.add(null);
            return allTrees;
        }

        // 枚举可行根节点
        for (int i = start; i <= end; i++) {
            // 获得所有可行的左子树集合
            List<TreeNode> leftTrees = generateTrees(start, i - 1);

            // 获得所有可行的右子树集合
            List<TreeNode> rightTrees = generateTrees(i + 1, end);

            // 从左子树集合中选出一棵左子树，从右子树集合中选出一棵右子树，拼接到根节点上
            for (TreeNode left : leftTrees) {
                for (TreeNode right : rightTrees) {
                    TreeNode currTree = new TreeNode(i);
                    currTree.left = left;
                    currTree.right = right;
                    allTrees.add(currTree);
                }
            }
        }
        return allTrees;
    }


    /**
     * 7.22 剑指 Offer 11. 旋转数组的最小数字
     *
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        int left = 0, right = numbers.length - 1;
        while (left < right) {
            int mid = ((right - left) >> 1) + left;
            if (numbers[mid] > numbers[right]) {
                left = mid + 1;
            } else if (numbers[mid] < numbers[right]) {
                right = mid;
            } else {
                right--;
            }
        }
        return numbers[left];
    }


    /**
     * 7.23 64. 最小路径和
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        int row = grid.length;
        if (row == 0) {
            return 0;
        }
        int col = grid[0].length;
        if (col == 0) {
            return 0;
        }
        int[][] dp = new int[row][col];
        dp[0][0] = grid[0][0];
        for (int i = 1; i < row; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }
        for (int i = 1; i < col; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                dp[i][j] = Integer.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }
        return dp[row - 1][col - 1];
    }


    /**
     * 7.24 1025. 除数博弈
     * **博弈论**
     *
     * @param N
     * @return
     */
    public boolean divisorGame(int N) {
        return N % 2 == 0;
    }


    /**
     * 7.25 410. 分割数组的最大值
     * **二分贪心**
     *
     * @param nums
     * @param m
     * @return
     */
    public int splitArray(int[] nums, int m) {
        int left = Integer.MIN_VALUE, right = 0;
        for (int num : nums) {
            right += num;
            left = Integer.max(num, left);
        }
        while (left < right) {
            int mid = ((right - left) >> 1) + left;
            if (check(nums, mid, m)) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    boolean check(int[] nums, int x, int m) {
        int cnt = 0, res = 1;
        for (int num : nums) {
            cnt += num;
            if (cnt > x) {
                res++;
                cnt = num;
            }
        }
        return res <= m;
    }


    /**
     * 7.26 329. 矩阵中的最长递增路径
     * **记忆化递归**
     *
     * @param matrix
     * @return
     */
    public int longestIncreasingPath(int[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};
        int row = matrix.length;
        int col = matrix[0].length;
        int[][] memo = new int[row][col];
        int res = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                res = Integer.max(res, dfs(matrix, dx, dy, i, j, memo));
            }
        }
        return res;
    }

    int dfs(int[][] matrix, int[] dx, int[] dy, int x, int y, int[][] memo) {
        if (memo[x][y] != 0) {
            return memo[x][y];
        }
        // 本身为1
        memo[x][y]++;
        int row = matrix.length;
        int col = matrix[0].length;
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            if (newX < row && newX >= 0 && newY < col && newY >= 0
                    && matrix[x][y] <= matrix[newX][newY]) {
                memo[x][y] = Integer.max(memo[x][y],
                        1 + dfs(matrix, dx, dy, newX, newY, memo));
            }
        }
        return memo[x][y];
    }

    @Test
    public void test() {
        d07_3 o = new d07_3();
        System.out.println(generateTrees(3));
    }
}
