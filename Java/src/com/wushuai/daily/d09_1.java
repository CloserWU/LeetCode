package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d09_1</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-09-01 9:11
 */
public class d09_1 {

    /**
     * 9.01 486. 预测赢家
     * dp[i][j] 表示当数组剩下的部分为下标 i 到下标 j 时，
     * 当前玩家与另一个玩家的分数之差的最大值，注意当前玩家不一定是先手。
     * https://leetcode-cn.com/problems/predict-the-winner/solution/yu-ce-ying-jia-by-leetcode-solution/
     *
     * @param nums
     * @return
     */
    public boolean PredictTheWinner(int[] nums) {
        int n = nums.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            dp[i][i] = nums[i];
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                dp[i][j] = Math.max(nums[i] - dp[i + 1][j], nums[j] - dp[i][j - 1]);
            }
        }
        return dp[0][n - 1] >= 0;
    }


    /**
     * 9.02 剑指 Offer 20. 表示数值的字符串
     *
     * @param s
     * @return
     */
    public boolean isNumber(String s) {
        return s.matches("^(\\s)*(\\+|-)?(([0-9]*\\.[0-9]+)|([0-9]+\\.[0-9]*)|[0-9]+)((e|E)(\\+|-)?[0-9]+)?(\\s)*$");
    }


    /**
     * 9.03 51. N 皇后
     * 经典题
     *
     * @param n
     * @return
     */
    public List<List<String>> solveNQueens(int n) {
        int[] queens = new int[n];
        Arrays.fill(queens, -1);
        Set<Integer> columns = new HashSet<>();
        Set<Integer> diagonals1 = new HashSet<>();
        Set<Integer> diagonals2 = new HashSet<>();
        List<List<String>> res = new ArrayList<>();
        backtrack(res, columns, diagonals1, diagonals2, 0, queens);
        return res;
    }

    void backtrack(List<List<String>> res, Set<Integer> columns, Set<Integer> diagonals1,
                   Set<Integer> diagonals2, int row, int[] queens) {
        if (row == queens.length) {
            List<String> s = generateBoard(queens);
            res.add(s);
            return;
        }
        for (int i = 0; i < queens.length; i++) {
            if (columns.contains(i)) {
                continue;
            }
            int diagonal1 = row - i;
            if (diagonals1.contains(diagonal1)) {
                continue;
            }
            int diagonal2 = row + i;
            if (diagonals2.contains(diagonal2)) {
                continue;
            }
            queens[row] = i;
            columns.add(i);
            diagonals1.add(diagonal1);
            diagonals2.add(diagonal2);
            backtrack(res, columns, diagonals1, diagonals2, row + 1, queens);
            queens[row] = -1;
            columns.remove(i);
            diagonals1.remove(diagonal1);
            diagonals2.remove(diagonal2);
        }
    }

    List<String> generateBoard(int[] queens) {
        List<String> list = new ArrayList<>();
        for (int queen : queens) {
            char[] row = new char[queens.length];
            Arrays.fill(row, '.');
            row[queen] = 'Q';
            list.add(new String(row));
        }
        return list;
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
     * 9.04 257. 二叉树的所有路径
     *
     * @param root
     * @return
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> res = new ArrayList<>();
        dfs(res, new ArrayList<>(), root);
        return res;
    }

    void dfs(List<String> res, List<Integer> path, TreeNode root) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            path.add(root.val);
            StringBuilder sb = new StringBuilder();
            for (Integer in : path) {
                sb.append("->");
                sb.append(in);
            }
            res.add(sb.substring(2));
            return;
        }
        path.add(root.val);
        if (root.left != null) {
            dfs(res, path, root.left);
            path.remove(path.size() - 1);
        }
        if (root.right != null) {
            dfs(res, path, root.right);
            path.remove(path.size() - 1);
        }
    }


    /**
     * 9.05 60. 第k个排列
     * **从小到大全排列**
     *
     * @param n
     * @param k
     * @return
     */
    public String getPermutation(int n, int k) {
        List<String> res = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int[] arr = new int[n];
        boolean[] visit = new boolean[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        getPermutation(res, arr, visit, sb, k);
//        System.out.println(res);
//        Collections.sort(res);
        return res.get(k - 1);
    }


    void getPermutation(List<String> res, int[] arr, boolean[] visit, StringBuilder sb, int k) {
        if (res.size() == k) {
            return;
        }
        if (sb.length() == arr.length) {
            res.add(sb.toString());
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            if (!visit[i]) {
                sb.append(arr[i]);
                visit[i] = true;
                getPermutation(res, arr, visit, sb, k);
                sb.setLength(sb.length() - 1);
                visit[i] = false;
            }
        }
    }

    @Test
    public void test() {
        d09_1 o = new d09_1();
        System.out.println(getPermutation(3, 3));
        System.out.println(getPermutation(4, 9));
    }
}
