package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d12</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-05-19 9:52
 */
public class d12 {

    /**
     * 5.06
     * 983. 最低票价
     * https://leetcode-cn.com/problems/minimum-cost-for-tickets/solution/zui-di-piao-jie-by-leetcode-solution/
     * <p>
     * 对于一年中的任意一天：
     * 如果这一天不是必须出行的日期，那我们可以贪心地选择不买。这是因为如果今天不用出行，那么也不必购买通行证，并且通行证越晚买越好。所以有 dp(i)=dp(i+1)；
     * 如果这一天是必须出行的日期，我们可以选择买 1，7 或 30 天的通行证。若我们购买了 j 天的通行证，那么接下来的 j - 1 天，我们都不再需要购买通行证，只需要考虑第 i + j天及以后即可。因此，我们有
     * dp(i)=min{cost(j)+dp(i+j)},j∈{1,7,30}
     *
     * @param days
     * @param costs
     * @return
     */
    public int mincostTickets(int[] days, int[] costs) {
        int[] dp = new int[367];
        int[] tmp = new int[]{1, 7, 30};
        Set<Integer> set = new HashSet<>();
        for (Integer day : days) {
            set.add(day);
        }
        for (int i = 365; i >= 0; i--) {
            if (!set.contains(i)) {
                dp[i] = dp[i + 1];
            } else {
                dp[i] = 1000000;
                for (int j = 0; j < 3; j++) {
                    if (i + tmp[j] < 366) {
                        dp[i] = Integer.min(dp[i], costs[j] + dp[i + tmp[j]]);
                    } else {
                        dp[i] = Integer.min(dp[i], costs[j]);
                    }
                }
            }
        }
        return dp[1];
    }

    @Test
    public void test() {
        d12 o = new d12();
        System.out.println(o.mincostTickets(new int[]{2}, new int[]{2, 7, 15}));
        System.out.println(o.mincostTickets(new int[]{1, 4, 6, 7, 8, 20}, new int[]{2, 7, 15}));
        System.out.println(o.mincostTickets(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 31}, new int[]{2, 7, 15}));
        System.out.println(o.mincostTickets(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 365}, new int[]{2, 7, 15}));
    }
}
