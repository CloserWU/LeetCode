package com.wushuai.hash;

import org.junit.Test;

/**
 * <p>IsHappy</p>
 * <p>202</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-13 10:31
 */
public class IsHappy {
    @Test
    public void test1() {
        System.out.println(isHappy(19));
    }

    public boolean isHappy(int n) {
        while (n != 1) {
            n = func(n);
            if (n == 4) {
                return false;
            }
        }
        return true;
    }

    public int func(int n) {
        String s = String.valueOf(n);
        char[] chars = s.toCharArray();
        int res = 0;
        for (char aChar : chars) {
            res += Math.pow(aChar - '0', 2);
        }
        return res;
    }
}

