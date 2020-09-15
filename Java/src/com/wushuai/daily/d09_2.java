package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d09_2</p>
 * <p>
 * 9.11  9.10  9.09  9.08
 * </p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-09-11 16:35
 */
public class d09_2 {


    /**
     * 9.11 216. 组合总和 III
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<List<Integer>> res = new ArrayList<>();
        getCombination(res, k, n, 0, arr, new ArrayList<>());
        return res;
    }


    void getCombination(List<List<Integer>> res, int k, int n, int idx, int[] arr, List<Integer> list) {
        if (n == 0 && list.size() == k) {
            res.add(new ArrayList<>(list));
        }
        if (n < 0 || idx == arr.length || list.size() > k) {
            return;
        }
        for (int i = idx; i < arr.length; i++) {
            list.add(arr[i]);
            getCombination(res, k, n - arr[i], i + 1, arr, list);
            list.remove(list.size() - 1);
        }
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
     * 9.12 637. 二叉树的层平均值
     *
     * @param root
     * @return
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            long sum = 0;
            int count = 0;
            List<TreeNode> tmp = new ArrayList<>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    tmp.add(node.left);
                }
                if (node.right != null) {
                    tmp.add(node.right);
                }
                sum += (long) node.val;
                count++;
            }
            res.add((double) sum / count);
            queue.addAll(tmp);
        }
        return res;
    }


    /**
     * 9.13 79. 单词搜索
     * 经典回溯dfs
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};
        int row = board.length;
        int col = board[0].length;
        boolean[][] visit = new boolean[row][col];
        for (boolean[] b : visit) {
            Arrays.fill(b, false);
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (word.charAt(0) == board[i][j]) {
                    visit[i][j] = true;
                    boolean flag = dfs(board, visit, word, 1, dx, dy, i, j);
                    if (flag) {
                        return true;
                    }
                    visit[i][j] = false;
                }
            }
        }
        return false;
    }

    boolean dfs(char[][] board, boolean[][] visit, String word, int idx, int[] dx, int[] dy, int x, int y) {
        if (idx == word.length()) {
            return true;
        }
        int row = board.length;
        int col = board[0].length;
        for (int i = 0; i < 4; i++) {
            int newX = x + dx[i];
            int newY = y + dy[i];
            if (newX >= 0 && newX < row && newY >= 0 && newY < col &&
                    !visit[newX][newY] && word.charAt(idx) == board[newX][newY]) {
                visit[newX][newY] = true;
                boolean flag = dfs(board, visit, word, idx + 1, dx, dy, newX, newY);
                if (flag) {
                    return true;
                }
                visit[newX][newY] = false;
            }
        }
        return false;
    }


    /**
     * 9.14 94. 二叉树的中序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> res = new ArrayList<>();
        stack.push(root);
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root.left);
                root = root.left;
            }
            root = stack.pop();
            res.add(root.val);
            root = root.right;
        }
        return res;
    }


    /**
     * 9.15 37. 解数独
     * https://leetcode-cn.com/problems/sudoku-solver/solution/
     *
     * @param board
     */
    public void solveSudoku(char[][] board) {
        boolean[][] lines = new boolean[9][9];
        boolean[][] columns = new boolean[9][9];
        boolean[][][] blocks = new boolean[3][3][9];
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == '.') {
                    list.add(new int[]{i, j});
                } else {
                    int digit = board[i][j] - '0' - 1;
                    lines[i][digit] = columns[j][digit] = blocks[i / 3][j / 3][digit] = true;
                }
            }
        }
        dfs(board, 0, list, blocks, lines, columns, new boolean[]{false});
    }


    void dfs(char[][] board, int size, List<int[]> list,
             boolean[][][] blocks, boolean[][] lines, boolean[][] columns, boolean[] valid) {
        if (size == list.size()) {
            valid[0] = true;
            return;
        }
        int[] ints = list.get(size);
        int i = ints[0];
        int j = ints[1];
        for (int digit = 0; digit < 9 && !valid[0]; digit++) {
            if (!lines[i][digit] && !columns[j][digit] && !blocks[i / 3][j / 3][digit]) {
                lines[i][digit] = columns[j][digit] = blocks[i / 3][j / 3][digit] = true;
                board[i][j] = (char) (digit + '0' + 1);
                dfs(board, size + 1, list, blocks, lines, columns, valid);
                lines[i][digit] = columns[j][digit] = blocks[i / 3][j / 3][digit] = false;
            }
        }
    }


    @Test
    public void test() {
        d09_2 o = new d09_2();
        exist(new char[][]{{'a', 'a'}}, "aaa");
    }
}
