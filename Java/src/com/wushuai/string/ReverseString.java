package com.wushuai.string;

import org.junit.Test;

/**
 * <p>ReverseString</p>
 * <p>344</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-07 12:00
 */
public class ReverseString {
    @Test
    public void test1() {
        reverseString(new char[] {'h','e','l','l','o'});
    }


    /**
     *  异或运算交换两个值
     *  a = a^b;
     *  b = a^b;
     *  a = a^b;
     *  位运算省去了O(1)的空间 同时运算的速度更快
     *  但是必须注意，如果a 和 b是指针指向同一地址时，
     *  此时a=b，a^b会将该地址的值变为0，
     *  这可就没有实现交换，反而出现错误了，
     *  注意使用环境，在此题中无影响。
     * @param s s
     */
    public void reverseString(char[] s) {
        int n = s.length;
        for (int i = 0; i < n / 2; ++i) {
            int j = n - 1 - i;
            s[i] ^= s[j];
            s[j] ^= s[i];
            s[i] ^= s[j];
        }
    }
}

