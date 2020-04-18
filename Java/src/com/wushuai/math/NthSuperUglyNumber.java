package com.wushuai.math;

import org.junit.Test;

/**
 * <p>NthSuperUglyNumber</p>
 * <p>313</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-18 19:44
 */
public class NthSuperUglyNumber {

    /**
     * 313. 超级丑数
     *
     * 2^a * 7^b * 13^c * 19^d
     * 由小到大
     * 需要记录 dp[i] * 2 , dp[j] * 7, dp[k] * 13, dp[w] * 19 中的最小值
     * dp[i]为上一个乘2的来的最大的数
     * dp[j]为上一个乘7的来的最大的数
     * ...
     *
     * @param n
     * @param primes
     * @return
     */
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] dp = new int[n];
        int[] power = new int[primes.length];
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < power.length; j++) {
                dp[i] = Integer.min(dp[i], dp[power[j]] * primes[j]);
            }
            for (int j = 0; j < power.length; j++) {
                if (dp[i] == dp[power[j]] * primes[j]) {
                    power[j]++;
                }
            }
        }
        return dp[n - 1];
    }

    @Test
    public void test() {
        NthSuperUglyNumber o = new NthSuperUglyNumber();
        System.out.println(o.nthSuperUglyNumber(12, new int[]{2, 7, 13, 19}));
    }
}
