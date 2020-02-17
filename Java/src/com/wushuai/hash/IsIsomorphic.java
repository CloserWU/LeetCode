package com.wushuai.hash;

import org.junit.Test;

import java.util.*;

/**
 * <p>IsIsomorphic</p>
 * <p>205</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-13 11:08
 */
public class IsIsomorphic {
    @Test
    public void test1() {
        System.out.println(isIsomorphic("footitle", "barpaper"));
        System.out.println(isIsomorphic("foo", "bar"));
        System.out.println(isIsomorphic("ab", "aa"));
        System.out.println(isIsomorphic("paper", "title"));
    }

    public boolean isIsomorphic(String s, String t) {
        Map<Character, Character> map = new HashMap<>();
        for (int i = 0; i < t.length(); i++) {
            if (!map.containsKey(s.charAt(i)) && !map.containsValue(t.charAt(i))) {
                map.put(s.charAt(i), t.charAt(i));
            }
        }
        char[] tc = t.toCharArray();
        char[] sc = s.toCharArray();
        for (int i = 0; i < tc.length; i++) {
            if (!map.containsKey(sc[i])) {
                return false;
            }
            sc[i] = map.get(sc[i]);
        }
        return new String(tc).equals(new String(sc));
    }
}

