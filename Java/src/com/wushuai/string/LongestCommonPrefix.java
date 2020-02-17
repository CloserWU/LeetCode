package com.wushuai.string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>LongestCommonPrefix</p>
 * <p>14</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-01 15:27
 */
public class LongestCommonPrefix {
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        List<String> list = new ArrayList<>(Arrays.asList(strs));
        String str = list.get(0);
        for (String aList : list) {
            while (!aList.startsWith(str)) {
                if (str.length() == 1) {
                    return "";
                }
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }

    @Test
    public void test() {
        System.out.println(longestCommonPrefix(new String[] {"BABCDFDSDASS", "BABCDFG"}));
    }
}

