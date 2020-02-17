package com.wushuai.string;

import org.junit.Test;

/**
 * <p>CanConstruct</p>
 * <p>383</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-08 09:37
 */
public class CanConstruct {
    @Test
    public void test1() {
        System.out.println(canConstruct("c", ""));
    }

    public boolean canConstruct(String a, String b) {

        int idx = 0;
        int len = 0;
        while (idx < a.length()) {
            len = b.indexOf(a.charAt(idx));
            if (len != -1) {
                idx++;
                b = b.substring(0, len) + b.substring(len + 1);
            } else {
                return false;
            }
        }
        return true;
    }
}

