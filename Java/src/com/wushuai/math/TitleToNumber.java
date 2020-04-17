package com.wushuai.math;

import org.junit.Test;

/**
 * <p>TitleToNumber</p>
 * <p>171</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-17 8:50
 */
public class TitleToNumber {

    /**
     * 171. Excel表列序号
     *
     * @param s
     * @return
     */
    public int titleToNumber(String s) {
        int power = 0;
        int res = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            res += (s.charAt(i) - 'A' + 1) * Math.pow(26, power);
            power++;
        }
        return res;
    }

    @Test
    public void test() {
        TitleToNumber o = new TitleToNumber();
        System.out.println(o.titleToNumber("A"));
        System.out.println(o.titleToNumber("AB"));
        System.out.println(o.titleToNumber("ZY"));
        System.out.println(o.titleToNumber("ZYZYZ"));
        System.out.println(o.titleToNumber("YZYZY"));
        System.out.println(o.titleToNumber("YZYZA"));
    }
}
