package com.wushuai.daily;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>d2</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-09 10:15
 */
public class d2 {

    /**
     * 4.06
     * 72. 编辑距离
     * dp[i][j] 代表 word1 到 i 位置转换成 word2 到 j 位置需要最少步数
     * 当 word1[i] == word2[j]，dp[i][j] = dp[i-1][j-1]；
     * 当 word1[i] != word2[j]，dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1
     * 其中，dp[i-1][j-1] 表示替换操作，dp[i-1][j] 表示删除操作，dp[i][j-1] 表示插入操作。
     * 注意，针对第一行，第一列要单独考虑，我们引入 ''
     * https://leetcode-cn.com/problems/edit-distance/solution/zi-di-xiang-shang-he-zi-ding-xiang-xia-by-powcai-3/
     */
    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word1.length() + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Integer.min(Integer.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }


    /**
     * 4.07
     * 面试题 01.07. 旋转矩阵
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        // 反对角转置矩阵
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix.length - i - 1; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[matrix.length - j - 1][matrix.length - i - 1];
                matrix[matrix.length - j - 1][matrix.length - i - 1] = tmp;
            }
        }
        // 行变换
        for (int i = 0; i < matrix[0].length / 2; i++) {
            int[] tmp = matrix[i];
            matrix[i] = matrix[matrix[0].length - i - 1];
            matrix[matrix[0].length - i - 1] = tmp;
        }
    }

    /**
     * 4.08
     * 面试题13. 机器人的运动范围
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount(int m, int n, int k) {
        if (k == 0) {
            return 1;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            map.put(i, 9);
        }
        map.put(9, 18);
        map.put(10, 28);
        map.put(11, 38);
        map.put(12, 48);
        map.put(13, 58);
        map.put(14, 68);
        map.put(15, 78);
        map.put(16, 88);
        map.put(17, 98);
        map.put(18, 107);
        map.put(19, 117);
        map.put(20, 127);
        int res = 0;
        m = Math.min(m, map.get(k) + 1);
        n = Math.min(n, map.get(k) + 1);
        int[][] board = new int[m][n];
        int tmp = map.get(k) / 10 + 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < Math.min((tmp - i / 10) * 10, n); j++) {
                board[i][j] = i / 100 + (i % 100) / 10 + (i % 100) % 10;
                board[i][j] += j / 100 + (j % 100) / 10 + (j % 100) % 10;
                if (board[i][j] <= k) {
                    res++;
                }
            }
        }
        return res;
    }

    void backTrack(int left, int right, StringBuilder str, List<String> list) {
        if (left > right) {
            return;
        }
        if (left < 0 || right < 0) {
            return;
        }
        if (left == 0 && right == 0) {
            list.add(str.toString());
            return;
        }
        str.append('(');
        backTrack(left - 1, right, str, list);
        str.setLength(str.length() - 1);

        str.append(')');
        backTrack(left, right - 1, str, list);
        str.setLength(str.length() - 1);
    }

    /**
     * 4.09
     * 22. 括号生成
     *
     * @param n
     * @return
     */
    public List<String> generateParenthesis(int n) {
        StringBuilder str = new StringBuilder();
        List<String> list = new ArrayList<>();
        backTrack(n, n, str, list);
        return list;
    }

    /**
     * 4.10
     * 151. 翻转字符串里的单词
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        String[] split = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            sb.append(' ');
            sb.append(split[i]);
        }
        return sb.substring(1);
    }

    @Test
    public void test1() {
        d2 o = new d2();
        o.rotate(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
    }
}

