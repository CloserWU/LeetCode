package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>dcontest25</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-05-03 9:37
 */
public class dcontest25 {

    /**
     * 5384. 拥有最多糖果的孩子
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
     * 5385. 改变一个整数能得到的最大差值
     *
     * @param num
     * @return
     */
    public int maxDiff(int num) {
        char[] chars = String.valueOf(num).toCharArray();
        char[] bak = Arrays.copyOf(chars, chars.length);
        int a = 0, b = 0;
        char tmp;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != '9') {
                tmp = chars[i];
                for (; i < chars.length; i++) {
                    if (chars[i] == tmp) {
                        chars[i] = '9';
                    }
                }
            }
        }
        a = Integer.parseInt(new String(chars));
        chars = bak;
        if (chars.length > 1) {
            if (chars[0] == '1') {
                for (int i = 1; i < chars.length; i++) {
                    if (chars[i] != '0' && chars[i] != '1') {
                        tmp = chars[i];
                        for (; i < chars.length; i++) {
                            if (chars[i] == tmp) {
                                chars[i] = '0';
                            }
                        }
                    }
                }
            } else {
                tmp = chars[0];
                chars[0] = '1';
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == tmp) {
                        chars[i] = '1';
                    }
                }
            }

        } else {
            chars[0] = '1';
        }
        b = Integer.parseInt(new String(chars));
        return a - b;
    }

    /**
     * 5386. 检查一个字符串是否可以打破另一个字符串
     *
     * @param s1
     * @param s2
     * @return
     */
    public boolean checkIfCanBreak(String s1, String s2) {
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        Arrays.sort(c1);
        Arrays.sort(c2);
        boolean f1 = true, f2 = true;
        for (int i = 0; i < c1.length; i++) {
            if (c1[i] < c2[i]) {
                f1 = false;
                break;
            }
        }
        for (int i = 0; i < c1.length; i++) {
            if (c2[i] < c1[i]) {
                f2 = false;
                break;
            }
        }
        return f1 || f2;
    }

    @Test
    public void test() {
        dcontest25 o = new dcontest25();
        System.out.println(o.maxDiff(555));
        System.out.println(o.maxDiff(9));
        System.out.println(o.maxDiff(1));
        System.out.println(o.maxDiff(10));
        System.out.println(o.maxDiff(123456));
        System.out.println(o.maxDiff(10000));
        System.out.println(o.maxDiff(9288));
        System.out.println(o.maxDiff(126510254));
        System.out.println(o.maxDiff(1101057));
        System.out.println(o.checkIfCanBreak("abc", "xya"));
        System.out.println(o.checkIfCanBreak("abe", "acd"));
        System.out.println(o.checkIfCanBreak("leetcodee", "interview"));
        System.out.println(o.checkIfCanBreak("zzzddd", "bbbddd"));

    }
}
