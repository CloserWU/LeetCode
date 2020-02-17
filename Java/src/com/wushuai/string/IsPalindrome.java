package com.wushuai.string;

import org.junit.Test;

/**
 * <p>IsPalindrome</p>
 * <p>125</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-07 11:10
 */
public class IsPalindrome {
    @Test
    public void test1() {
        System.out.println(isPalindrome(".0p"));
    }

    /**
     * 遇到求回文的问题 ，先用正则替换所有特殊字符
     * 在循环外，要return false，不能返回true
     * 若是直到循环结束才能判断为true，则在循环体中，在出循环的前面加入为true的判断
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        s = s.replaceAll("[^a-zA-Z0-9]", "");
        s = s.toLowerCase();
        if ("".equals(s) || s.length() == 1) {
            return true;
        }
        System.out.println(s);
        int i = 0, j = s.length() - 1;
        while (i < j) {
            char a = s.charAt(i);
            char b = s.charAt(j);
            if (a != b) {
                return false;
            }
            i++;
            j--;
            /*
             * true判断
             */
            if (i >= j) {
                return true;
            }

        }
        /*
         *  默认返回false
         */
        return false;
    }
}

