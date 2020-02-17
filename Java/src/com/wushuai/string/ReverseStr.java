package com.wushuai.string;

import org.junit.Test;

/**
 * <p>ReverseStr</p>
 * <p>
 *     reverseStr_v2
 *     swap值得背会
 * </p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-13 09:19
 */
public class ReverseStr {
    @Test
    public void test1() {
        System.out.println(reverseStr("abcdefghijklmno", 2));
        System.out.println(reverseStr("abcdefghijklmno", 3));
        System.out.println(reverseStr("abcdefg", 2));
    }

    public String reverseStr(String s, int k) {
        char[] chars = s.toCharArray();
        int i = 0;
        for (i = 0; i < chars.length / 2 / k; i++) {
            for (int j = 0; j < k / 2; j++) {
                char ch = chars[i * k * 2 + j];
                chars[i * k * 2 + j] = chars[i * k * 2 + k - j - 1];
                chars[i * k * 2 + k - j - 1] = ch;
            }
        }
        if (chars.length - (i * 2 * k) <= k) {
            for (int idx = 0; idx < (chars.length - (i * 2 * k)) / 2; idx++) {
                char ch = chars[idx + i * 2 * k];
                chars[idx + i * 2 * k] = chars[chars.length - idx - 1];
                chars[chars.length - idx - 1] = ch;
            }
        } else {
            for (int idx = 0; idx < k / 2; idx++) {
                char ch = chars[idx + i * 2 * k];
                chars[idx + i * 2 * k] = chars[i * 2 * k + k - idx - 1];
                chars[i * 2 * k + k - idx - 1] = ch;
            }
        }
        return new String(chars);
    }


    public static void swap(char[] a, int begin, int end) {
        while (begin < end) {
            a[begin] ^= a[end];
            a[end] ^= a[begin];
            a[begin] ^= a[end];
            begin++;
            end--;
        }
    }

    public String reverseStr_v2(String s, int k) {
        char[] c = s.toCharArray();
        int len = c.length;
        for (int i = 0; i < len; i = i + 2 * k) {
            if (i + 2 * k <= len || i + k <= len) {
                swap(c, i, i + k - 1);
            } else {
                swap(c, i, len - 1);
            }

        }
        return new String(c);
    }

}

