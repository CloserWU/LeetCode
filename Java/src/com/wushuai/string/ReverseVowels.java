package com.wushuai.string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>ReverseVowels</p>
 * <p>345</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-08 09:19
 */
public class ReverseVowels {
    @Test
    public void test1() {
        System.out.println(reverseVowels("wushuaidasjlhdjkIIIIidq"));
    }

    public String reverseVowels(String s) {
        List<Character> o = new ArrayList<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'A', 'E', 'I', 'O', 'U'));
        StringBuilder sb = new StringBuilder(s);
        int i = 0, j = s.length() - 1;
        while (i < j) {
            while (i < j && !o.contains(s.charAt(i))) {
                i++;
            }
            while (i < j && !o.contains(s.charAt(j))) {
                j--;
            }
            if (o.contains(s.charAt(j)) && o.contains(s.charAt(i))) {
                char tmp = sb.charAt(i);
                sb.setCharAt(i, sb.charAt(j));
                sb.setCharAt(j, tmp);
                i++;
                j--;
            }
        }
        return sb.toString();
    }
}

