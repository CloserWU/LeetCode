package com.wushuai.string;

import org.junit.Test;

/**
 * <p>CountSegments</p>
 * <p>434</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-08 10:32
 */
public class CountSegments {
    @Test
    public void test1() {
        System.out.println(countSegments(", , , , a, eaefa, love live! mu'sic forever"));
    }

    public int countSegments(String s) {
        return (s == null || s.trim().length() == 0) ? 0 : s.trim().split("\\s+").length;
    }
}

