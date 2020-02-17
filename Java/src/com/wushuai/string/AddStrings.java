package com.wushuai.string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>AddStrings</p>
 * <p>415</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-08 10:24
 */
public class AddStrings {
    @Test
    public void test1() {
        System.out.println(addStrings("12345811111", "897463112"));
    }


    /**
     * 字符串加法、链表加法以及二进制加法之类的都可以这么写
     *
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings_v2(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int carry = 0, i = num1.length() - 1, j = num2.length() - 1;
        while (i >= 0 || j >= 0 || carry != 0) {
            if (i >= 0) {
                carry += num1.charAt(i--) - '0';
            }
            if (j >= 0) {
                carry += num2.charAt(j--) - '0';
            }
            sb.append(carry % 10);
            carry /= 10;
        }
        return sb.reverse().toString();
    }

    public String addStrings(String num1, String num2) {
        String tmp;
        if (num1.length() < num2.length()) {
            tmp = num1;
            num1 = num2;
            num2 = tmp;
        }
        char[] chars1 = num1.toCharArray();
        char[] chars2 = num2.toCharArray();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (char aChar : chars1) {
            list1.add(aChar - 48);
        }
        for (char bChar : chars2) {
            list2.add(bChar - 48);
        }
        Collections.reverse(list1);
        Collections.reverse(list2);
        for (int i = 0; i < Math.min(list1.size(), list2.size()); i++) {
            list1.set(i, list1.get(i) + list2.get(i));
        }
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i) >= 10) {
                list1.set(i, list1.get(i) - 10);
                if (i != list1.size() - 1) {
                    list1.set(i + 1, list1.get(i + 1) + 1);
                } else {
                    list1.add(1);
                }
            }
        }

        Collections.reverse(list1);
        StringBuilder sb = new StringBuilder();
        for (Integer ch : list1) {
            sb.append(ch);
        }
        return sb.toString();
    }
}

