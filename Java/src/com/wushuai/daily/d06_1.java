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
     * @param n
     * @return
     */
    public int sumNums(int n) {
        if (n == 0) {
            return 0;
        }
        return n + sumNums(n - 1);
    }


    @Test
    public void test() {
        d06_1 o = new d06_1();

    }
}
