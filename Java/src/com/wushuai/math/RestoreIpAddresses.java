package com.wushuai.math;

import org.junit.Test;

import java.util.*;

/**
 * <p>RestoreIpAddresses</p>
 * <p>
 *     93
 *     ip地址正则表达式
 * </p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-21 14:53
 */
public class RestoreIpAddresses {

    /**
     * 93. 复原IP地址
     * 将点插入字符串，暴力枚举插入，然后正则判断
     *
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        String regex = "^((((2[0-5][0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|\\d)\\.){4})$";
        List<String> res = new ArrayList<>();
        if (s.length() > 12) {
            return res;
        }
        for (int i = 1; i < s.length() - 2; i++) {
            for (int j = i + 1; j < s.length() - 1; j++) {
                for (int k = j + 1; k < s.length(); k++) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(s, 0, i);
                    sb.append('.');
                    sb.append(s, i, j);
                    sb.append('.');
                    sb.append(s, j, k);
                    sb.append('.');
                    sb.append(s, k, s.length());
                    sb.append('.');
                    if (sb.toString().matches(regex)) {
                        sb.setLength(sb.length() - 1);
                        res.add(sb.toString());
                    }

                }
            }
        }
        return res;
    }

    @Test
    public void test() {
        RestoreIpAddresses o = new RestoreIpAddresses();
        System.out.println(o.restoreIpAddresses("0000"));
    }
}
