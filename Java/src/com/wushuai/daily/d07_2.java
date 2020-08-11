package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d07_2</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-11 19:38
 */
public class d07_2 {


    /**
     * 7.11 315. 计算右侧小于当前元素的个数
     * 树状数组  too hard
     *
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        return null;
    }


    /**
     * 7.12 174. 地下城游戏
     * https://leetcode-cn.com/problems/dungeon-game/solution/di-xia-cheng-you-xi-by-leetcode-solution/
     * dp
     *
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int row = dungeon.length;
        int col = dungeon[0].length;
        int[][] dp = new int[row + 1][col + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        dp[row][col - 1] = 1;
        dp[row - 1][col] = 1;
        for (int i = row - 1; i >= 0; i--) {
            for (int j = col - 1; j >= 0; j--) {
                int minn = Integer.min(dp[i + 1][j], dp[i][j + 1]);
                dp[i][j] = Integer.max(minn - dungeon[i][j], 1);
            }
        }
        return dp[0][0];
    }

    @Test
    public void test() {
        d07_2 o = new d07_2();

    }
}
