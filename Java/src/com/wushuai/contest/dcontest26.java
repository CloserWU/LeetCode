package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>dcontest26</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-06-13 9:36
 */
public class dcontest26 {

    /**
     * 1446. 连续字符
     *
     * @param s
     * @return
     */
    public int maxPower(String s) {
        char[] chars = s.toCharArray();
        char ch = chars[0];
        int num = 1;
        int res = 1;
        for (int i = 1; i < chars.length; i++) {
            if (ch != chars[i]) {
                res = Integer.max(res, num);
                num = 1;
                ch = chars[i];
            } else {
                num++;
            }
        }
        res = Integer.max(res, num);
        return res;
    }


    int gcd(int i, int j) {
        if (j == 0) {
            return i;
        }
        return gcd(j, i % j);
    }

    /**
     * 1447. 最简分数
     *
     * @param n
     * @return
     */
    public List<String> simplifiedFractions(int n) {
        List<String> res = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                if (gcd(i, j) == 1) {
                    res.add(j + "/" + i);
                }
            }
        }
        return res;
    }

    public class TreeNode {
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

    int res = 0;

    void inOrder(TreeNode root, int max) {
        if (root != null) {
            if (root.val >= max) {
                res++;
            }
            max = Integer.max(max, root.val);
            inOrder(root.left, max);
            inOrder(root.right, max);
        }
    }

    int goodNodes(TreeNode root, int max) {
        if (root != null) {
            int ans = 0;
            if (root.val >= max) {
                ans++;
            }
            max = Integer.max(max, root.val);
            ans += goodNodes(root.left, max);
            ans += goodNodes(root.right, max);
            return ans;
        }
        return 0;
    }

    public int goodNodes(TreeNode root) {
//        inOrder(root, Integer.MIN_VALUE);
//        return res;
        return goodNodes(root, Integer.MIN_VALUE);
    }


    /**
     * 1449. 数位成本和为目标值的最大数字
     * 完全背包
     * 一般看到(恰好)构成target的字样, 大多与背包有关. 本题就是每个数字可以无限选, 即为完全背包
     * dp[i][j]:前i个元素, 恰好构成成本为j时, 构成的最大的整数(整数用字符串表示, 无效状态用'#'表示)
     * dp[i][j] = max(dp[i][j - cost[i]] + value[i], dp[i - 1][j])
     * <p>
     * https://leetcode-cn.com/problems/form-largest-integer-with-digits-that-add-up-to-target/solution/xiang-xi-jiang-jie-wan-quan-bei-bao-zhuang-tai-de-/
     *
     * @param cost
     * @param target
     * @return
     */
    public String largestNumber(int[] cost, int target) {
        // target不一定比cost[i]大，所以用常数初始化
        String[][] dp = new String[cost.length + 1][5000 + 1];
        int[] value = new int[cost.length];
        for (int i = 0; i < value.length; i++) {
            value[i] = i + 1;
        }
        // 初始化dp非法状态，背包大小为0，却要装入。非法
        Arrays.fill(dp[0], "#");
        dp[0][0] = "";
        // value 和 cost 都是从下标0开始，所以要减1
        for (int i = 1; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                String a = "", b = "";
                if (j - cost[i - 1] >= 0 && !"#".equals(dp[i][j - cost[i - 1]])) {
                    a = value[i - 1] + dp[i][j - cost[i - 1]];
                }
                b = dp[i - 1][j];
                dp[i][j] = stringMax(a, b);
            }
        }
        if ("#".equals(dp[cost.length][target])) {
            return "0";
        }
        return dp[cost.length][target];
    }

    private String stringMax(String a, String b) {
        if (a.length() > b.length()) {
            return a;
        } else if (a.length() < b.length()) {
            return b;
        } else {
            if (a.compareTo(b) > 0) {
                return a;
            } else {
                return b;
            }
        }
    }

    @Test
    public void test() {
        dcontest26 o = new dcontest26();
        System.out.println(o.largestNumber(new int[]{4, 3, 2, 5, 6, 7, 2, 5, 5}, 9));
    }
}
