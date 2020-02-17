package com.wushuai.string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>FirstUniqChar</p>
 * <p>387</p>
 *
 * 和出现次数有关的，不要犹豫，hash
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-08 10:06
 */
public class FirstUniqChar {
    @Test
    public void test1() {
        System.out.println(firstUniqChar_v2(""));
    }

    /**
     * 次序不确定
     * @param s s
     * @return
     */
    public int firstUniqChar(String s) {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            /// map.put(ch, map.getOrDefault(ch, 0) + 1);
            if (!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), 1);
            } else {
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
            }
        }
        for (Map.Entry<Character, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 1) {
                return s.indexOf(entry.getKey());
            }
        }
        return -1;
    }

    public int firstUniqChar_v2(String s) {
        List<Character> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (!list1.contains(s.charAt(i))) {
                list1.add(s.charAt(i));
                list2.add(1);
            } else {
                int idx = list1.indexOf(s.charAt(i));
                list2.set(idx, list2.get(idx) + 1);

            }
        }
        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i) == 1) {
                return s.indexOf(list1.get(i));
            }
        }
        return -1;
    }

    /**
     *
     * @param s
     * @return
     */
    public int firstUniqChar_v3(String s) {
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (s.indexOf(ch) == s.lastIndexOf(ch)) {
                return i;
            }
        }
        return -1;
    }
}

