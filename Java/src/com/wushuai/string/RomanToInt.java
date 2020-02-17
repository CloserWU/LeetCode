package com.wushuai.string;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>RomanToInt</p>
 * <p>description</p>
 *
 * 13
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-01 15:25
 */
public class RomanToInt {
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<>();
        map.put('I', 1);
        map.put('V', 5);
        map.put('X', 10);
        map.put('L', 50);
        map.put('C', 100);
        map.put('D', 500);
        map.put('M', 1000);

        int res = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (map.get(s.charAt(i)) >= map.get(s.charAt(i + 1))) {
                res += map.get(s.charAt(i));
            } else {
                res -= map.get(s.charAt(i));
            }
        }
        res += map.get(s.charAt(s.length() - 1));
        return res;
    }


    @Test
    public void test1() {
        System.out.println(romanToInt("III"));
        System.out.println(romanToInt("LVIII"));
        System.out.println(romanToInt("MCMXCIV"));
    }
}

