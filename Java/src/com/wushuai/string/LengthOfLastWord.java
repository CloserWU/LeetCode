package com.wushuai.string;

import org.junit.Test;

/**
 * <p>LengthOfLastWord</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-07 09:59
 */
public class LengthOfLastWord {
    @Test
    public void test1() {
        System.out.println(lengthOfLastWord(" "));
    }


    public int lengthOfLastWord(String s) {
        String[] split = s.trim().split(" ");
        if (split.length == 0) {
            return 0;
        }
//        System.out.println(Arrays.toString(split));
        return split[split.length - 1].length();
    }
}

