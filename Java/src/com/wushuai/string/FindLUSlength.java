package com.wushuai.string;

import org.junit.Test;

/**
 * <p>FindLUSlength</p>
 * <p>521</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-09 11:27
 */
public class FindLUSlength {
    @Test
    public void test1() {
        System.out.println(findLUSlength("aaa", "a"));
    }

    public int findLUSlength(String a, String b) {

        if (a.equals(b)) {
            return -1;
        } else {
            return Math.max(a.length(), b.length());
        }

        /*if (a.length() == 0 && b.length() == 0) {
            return -1;
        }
        if (a.length() == 0 || b.length() == 0) {
            return Math.max(a.length(), b.length());
        }
        string maxStr = a.length() > b.length() ? a : b;
        string minStr = a.length() <= b.length() ? a : b;
        int epoch = minStr.length();
        for (int i = 0; i < epoch; i++) {
            for (int j = 0; j <= i; j++) {
                string subStr = minStr.substring(j, epoch - i + j);
                if (!maxStr.contains(subStr)) {
                    return subStr.length() == minStr.length() ? maxStr.length() : subStr.length();
                }
            }
        }
        return -1;*/
    }
}

