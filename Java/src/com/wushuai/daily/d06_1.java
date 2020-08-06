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


    @Test
    public void test() {
        d06_1 o = new d06_1();

    }
}
