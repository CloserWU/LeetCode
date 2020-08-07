package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d06_1</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-05 9:14
 */
public class d06_1 {

    /**
     * 6.01 1431. 拥有最多糖果的孩子
     *
     * @param candies
     * @param extraCandies
     * @return
     */
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int max = 0;
        List<Boolean> res = new ArrayList<>(Collections.nCopies(candies.length, false));
        for (int i = 0; i < candies.length; i++) {
            max = Integer.max(max, candies[i]);
        }
        for (int i = 0; i < candies.length; i++) {
            if (candies[i] + extraCandies >= max) {
                res.set(i, true);
            }
        }
        return res;
    }


    /**
     * 6.02 剑指 Offer 64. 求1+2+…+n
     *
     * @param n
     * @return
     */
    public int sumNums(int n) {
        if (n == 0) {
            return 0;
        }
        return n + sumNums(n - 1);
    }


    /**
     * 6.03 837. 新21点
     * pass, 无意义题
     *
     * @param N
     * @param K
     * @param W
     * @return
     */
    public double new21Game(int N, int K, int W) {
        if (K == 0) {
            return 1.0;
        }
        double[] dp = new double[K + W];
        for (int i = K; i <= N && i < K + W; i++) {
            dp[i] = 1.0;
        }
        dp[K - 1] = 1.0 * Math.min(N - K + 1, W) / W;
        for (int i = K - 2; i >= 0; i--) {
            dp[i] = dp[i + 1] - (dp[i + W + 1] - dp[i + 1]) / W;
        }
        return dp[0];
    }


    /**
     * 6.04 238. 除自身以外数组的乘积
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        res[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        int r = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= r;
            r *= nums[i];
        }
        return res;
    }


    /**
     * 6.05 剑指 Offer 29. 顺时针打印矩阵
     *
     * @param matrix
     * @return
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int[] res = new int[row * col];
        if (row == 1) {
            res = Arrays.copyOf(matrix[0], col);
            return res;
        }
        if (col == 1) {
            for (int i = 0; i < row; i++) {
                res[i] = matrix[i][0];
            }
            return res;
        }
        int i = 0, x = 0, y = 0;
        int[] dx = new int[]{0, 1, 0, -1};
        int[] dy = new int[]{1, 0, -1, 0};
        int p = 0;
        while (i != row * col) {
            res[i] = matrix[x][y];
            matrix[x][y] = Integer.MIN_VALUE;
            i++;
            if (x == row - 1 && y == 0) {
                p++;
            }
            if (x == row - 1 && y == col - 1) {
                p++;
            }
            if (x == 0 && y == col - 1) {
                p++;
            }
            if (matrix[x + dx[p % 4]][y + dy[p % 4]] == Integer.MIN_VALUE) {
                p++;
            }
            x += dx[p % 4];
            y += dy[p % 4];

        }
        return res;
    }


    /**
     * 6.06 128. 最长连续序列
     * https://leetcode-cn.com/problems/longest-consecutive-sequence/solution/zui-chang-lian-xu-xu-lie-by-leetcode-solution/
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int res = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int tmp = 1;
                while (set.contains(num + 1)) {
                    tmp++;
                    num++;
                }
                res = Integer.max(res, tmp);
            }
        }
        return res;
    }


    /**
     * 6.07 126. 单词接龙 II
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        return null;
    }


    int findRoot(int x, int[] graph) {
        while (x != graph[x]) {
            x = graph[x];
        }
        return x;
    }

    void union(int x, int y, int[] graph, int[] height) {
        x = findRoot(x, graph);
        y = findRoot(y, graph);
        if (x != y) {
            if (height[x] > height[y]) {
                graph[y] = x;
            } else if (height[x] < height[y]) {
                graph[x] = y;
            } else {
                graph[x] = y;
                height[y]++;
            }
        }
    }

    /**
     * 6.08 990. 等式方程的可满足性
     *
     * @param equations
     * @return
     */
    public boolean equationsPossible(String[] equations) {
        int[] graph = new int[26];
        int[] height = new int[26];
        for (int i = 0; i < 26; i++) {
            graph[i] = i;
            height[i] = 0;
        }
        Set<Integer> exist = new HashSet<>();
        for (String str : equations) {
            char op = str.charAt(1);
            if (op == '=') {
                int x = str.charAt(0) - 'a';
                int y = str.charAt(3) - 'a';
                union(x, y, graph, height);
            }
        }
        for (String str : equations) {
            char op = str.charAt(1);
            if (op == '!') {
                int x = str.charAt(0) - 'a';
                int y = str.charAt(3) - 'a';
                if (findRoot(x, graph) == findRoot(y, graph)) {
                    return false;
                }
            }
        }

        return true;
    }


    int translateNumRes = 0;

    void dfs(char[] chars, int idx) {
        if (idx == chars.length) {
            translateNumRes++;
            return;
        }
        dfs(chars, idx + 1);
        if (idx + 1 < chars.length && chars[idx] - '0' > 0) {
            int num = (chars[idx] - '0') * 10 + chars[idx + 1] - '0';
            if (num <= 25) {
                dfs(chars, idx + 2);
            }
        }
    }

    /**
     * 6.09 剑指 Offer 46. 把数字翻译成字符串
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {
        char[] chars = String.valueOf(num).toCharArray();
        dfs(chars, 0);
        return translateNumRes;
    }

    @Test
    public void test() {
        d06_1 o = new d06_1();
        System.out.println(equationsPossible(new String[]{"e==d", "e==a", "f!=d", "b!=c", "a==b"}));

        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrix5 = new int[][]{{1, 2}, {4, 5}, {7, 8}};
        int[][] matrix4 = new int[][]{{1, 2, 3}, {7, 8, 9}};
        int[][] matrix1 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        int[][] matrix3 = new int[][]{{1}, {5}, {9}};
        int[][] matrix2 = new int[][]{{9, 10, 11, 12}};
        int[][] matrix6 = new int[][]{};
        int[][] matrix7 = new int[][]{{}};
        System.out.println(Arrays.toString(spiralOrder(matrix6)));
        System.out.println(Arrays.toString(spiralOrder(matrix7)));
        System.out.println(Arrays.toString(spiralOrder(matrix2)));
        System.out.println(Arrays.toString(spiralOrder(matrix)));
        System.out.println(Arrays.toString(spiralOrder(matrix1)));
        System.out.println(Arrays.toString(spiralOrder(matrix3)));
        System.out.println(Arrays.toString(spiralOrder(matrix4)));
        System.out.println(Arrays.toString(spiralOrder(matrix5)));
    }
}
