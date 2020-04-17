package com.wushuai.math;

import org.junit.Test;

/**
 * <p>ConvertToTitle</p>
 * <p>168</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-17 8:55
 */
public class ConvertToTitle {

    /**
     * 168. Excel表列名称
     *
     * @param n
     * @return
     */
    public String convertToTitle(int n) {
        StringBuilder sb = new StringBuilder();
        while (n != 0) {
            if (n % 26 == 0) {
                n -= 1;
                sb.append('Z');
            } else {
                sb.append((char) (n % 26 + 'A' - 1));
            }
            n /= 26;
        }
        sb.reverse();
        return sb.toString();
    }


    @Test
    public void test() {
        ConvertToTitle o = new ConvertToTitle();
        System.out.println(o.convertToTitle(701));
        System.out.println(o.convertToTitle(1));
        System.out.println(o.convertToTitle(26));
        System.out.println(o.convertToTitle(12339028));
        System.out.println(o.convertToTitle(11898977));
        System.out.println(o.convertToTitle(11898953));
    }
}
