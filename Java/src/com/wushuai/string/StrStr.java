package com.wushuai.string;

import org.junit.Test;

/**
 * <p>StrStr</p>
 * <p>28</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-06 09:32
 */
public class StrStr {
    @Test
    public void test1() {
        System.out.println(strStr("hello", "lel"));
    }

    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
}

