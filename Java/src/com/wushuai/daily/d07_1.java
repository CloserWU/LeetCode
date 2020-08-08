package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

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

    /**
     * 7.04 32. 最长有效括号
     * 对于遇到的每个 ‘(’ ，我们将它的下标放入栈中
     * 对于遇到的每个 ‘)’ ，我们先弹出栈顶元素表示匹配了当前右括号：
     * 如果栈为空，说明当前的右括号为没有被匹配的右括号，我们将其下标放入栈中来更新我们之前提到的「最后一个没有被匹配的右括号的下标」
     * 如果栈不为空，当前右括号的下标减去栈顶元素即为「以该右括号为结尾的最长有效括号的长度」
     *
     * @param s
     * @return
     */
    public int longestValidParentheses(String s) {
        char[] chars = s.toCharArray();
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int res = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.empty()) {
                    // 记录断开子括号串的右括号边界
                    stack.push(i);
                } else {
                    res = Integer.max(res, i - stack.peek());
                }
            }
        }
        return res;
    }

    /**
     * 7.05 44. 通配符匹配
     *
     * @param s
     * @param p
     * @return
     */
    public boolean isMatch(String s, String p) {
        // too hard. to be finished
        return false;
    }


    /**
     * 7.06 3. 不同路径 II
     * 基础dp题 ，需要**掌握**
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int row = obstacleGrid.length;
        int col = obstacleGrid[0].length;
        int[][] dp = new int[row][col];
        for (int i = 0; i < row; i++) {
            if (obstacleGrid[i][0] == 1) {
                break;
            }
            dp[i][0] = 1;
        }
        for (int i = 0; i < col; i++) {
            if (obstacleGrid[0][i] == 1) {
                break;
            }
            dp[0][i] = 1;
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                if (obstacleGrid[i][j] == 1) {
                    dp[i][j] = 0;
                } else {
                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
                }
            }
        }
        return dp[row - 1][col - 1];
    }


    /**
     * 7.07 112. 路径总和
     *
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return sum - root.val == 0;
        }
        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }


    /**
     * 7.08 面试题 16.11. 跳水板
     *
     * @param shorter
     * @param longer
     * @param k
     * @return
     */
    public int[] divingBoard(int shorter, int longer, int k) {
        if (k == 0) {
            return new int[0];
        }
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i <= k; i++) {
            set.add(shorter * i + (k - i) * longer);
        }
        int[] res = new int[set.size()];
        Iterator<Integer> iterator = set.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            res[i++] = iterator.next();
        }
        Arrays.sort(res);
        return res;
    }


    /**
     * 字典树， 指针版本 （d08_1 数组简化版本）
     */
    class Trie {
        Trie[] next;
        boolean isEnd;

        public Trie() {
            isEnd = false;
            next = new Trie[26];
        }

        public void insert(String s) {
            Trie cur = this;
            for (int i = s.length() - 1; i >= 0; i--) {
                int x = s.charAt(i) - 'a';
                if (cur.next[x] == null) {
                    cur.next[x] = new Trie();
                }
                cur = cur.next[x];
            }
            cur.isEnd = true;
        }
    }


    /**
     * 7.09面试题 17.13. 恢复空格   == > reSpace.gif
     * 字典树 dp   （综合题， 重点掌握）
     * https://leetcode-cn.com/problems/re-space-lcci/solution/hui-fu-kong-ge-by-leetcode-solution/
     * 定义 dp[i] 表示考虑前 i 个字符最少的未识别的字符数量，从前往后计算 dp 值。
     * 考虑转移方程，每次转移的时候我们考虑第 j(j≤i) 个到第 i 个字符组成的子串 sentence[j−1⋯i−1]
     * （注意字符串下标从 0 开始）是否能在词典中找到，如果能找到的话按照定义转移方程即为
     * dp[i]=min(dp[i],dp[j−1])
     * <p>
     * 否则没有找到的话我们可以复用 dp[i−1] 的状态再加上当前未被识别的第 i 个字符，因此此时 dp 值为
     * dp[i]=dp[i−1]+1
     *
     * @param dictionary
     * @param sentence
     * @return
     */
    public int reSpace(String[] dictionary, String sentence) {
        int len = sentence.length();
        Trie trie = new Trie();
        int[] dp = new int[len + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (String s : dictionary) {
            trie.insert(s);
        }
        for (int i = 1; i <= len; i++) {
            dp[i] = dp[i - 1] + 1;
            Trie cur = trie;
            for (int j = i; j >= 1; j--) {
                int x = sentence.charAt(j - 1) - 'a';
                if (cur.next[x] == null) {
                    break;
                } else if (cur.next[x].isEnd) {
                    dp[i] = Integer.min(dp[i], dp[j - 1]);
                }
                if (dp[i] == 0) {
                    break;
                }
                cur = cur.next[x];
            }
        }
        return dp[len];
    }


    @Test
    public void test() {
        d07_1 o = new d07_1();

    }
}
