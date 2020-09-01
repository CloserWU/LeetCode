package com.wushuai.daily;

import org.junit.Test;

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

    @Test
    public void test() {
        d09_1 o = new d09_1();

    }
}
