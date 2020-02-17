package com.wushuai.string;

import org.junit.Test;

/**
 * <p>DetectCapitalUse</p>
 * <p>520</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-09 11:19
 */
public class DetectCapitalUse {
    @Test
    public void test1() {
        System.out.println(detectCapitalUse("Google"));
        System.out.println(detectCapitalUse("leetCode"));
        System.out.println(detectCapitalUse("leetcode"));
        System.out.println(detectCapitalUse("flaG"));
        System.out.println(detectCapitalUse("USSA"));
    }

    /**
     * python
     * return word[1:] == word[1:].lower() or word.upper() == word
     * return word.upper()==word or word.lower()==word or word.title()==word
     * return word.islower() or word.isupper() or word.istitle()
     * @param word
     * @return
     */
    public boolean detectCapitalUse(String word) {
        return word.matches("(\\w?[a-z]*)|[A-Z]*");
    }
}

