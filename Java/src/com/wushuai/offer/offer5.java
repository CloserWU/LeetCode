package com.wushuai.offer;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>offer5</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-21 11:12
 */
public class offer5 {

    /**
     * 面试题63. 股票的最大利润
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int min = Integer.MAX_VALUE;
        int res = 0;
        for (int i = 0; i < prices.length; i++) {
            min = Integer.min(min, prices[i]);
            res = Integer.max(prices[i] - min, res);
        }
        return res;
    }

    /**
     * 面试题64. 求1+2+…+n
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

    public int sumNums_v2(int n) {
        boolean flag = n > 1 && (n += sumNums_v2(n - 1)) > 0;
        return n;
    }

    Pattern compile = Pattern.compile("^[\\+|-]?[0-9]+");

    /**
     * 面试题67. 把字符串转换成整数
     *
     * @param str
     * @return
     */
    public int strToInt(String str) {
        str = str.trim();
        Matcher matcher = compile.matcher(str);
        if (matcher.lookingAt()) {
            str = str.substring(0, matcher.end());
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                return str.charAt(0) == '-' ? Integer.MIN_VALUE : Integer.MAX_VALUE;
            }
        }
        return 0;
    }

    @Test
    public void test() {
        offer5 o = new offer5();
        System.out.println(o.sumNums_v2(10000));
    }
}
