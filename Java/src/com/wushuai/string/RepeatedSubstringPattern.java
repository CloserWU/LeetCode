package com.wushuai.string;

import org.junit.Test;

/**
 * <p>RepeatedSubstringPattern</p>
 * <p>459</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-09 10:50
 */
public class RepeatedSubstringPattern {
    @Test
    public void test1() {
        System.out.println(repeatedSubstringPattern("abcac"));
    }

    /**
     * ‘\1’ 匹配的是 所获取的第1个()匹配的引用。例如，’(\d)\1’ 匹配两个连续数字字符。如33aa 中的33
     * ‘\2’ 匹配的是 所获取的第2个()匹配的引用。
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern(String s) {
        return s.matches("(\\w+)\\1+");
    }
}

