package com.wushuai.string;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>AddBinary</p>
 * <p>67</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-07 10:13
 */
public class AddBinary {
    @Test
    public void test1() {
        System.out.println(addBinary("11111111", "100101"));
    }

    public String addBinary(String a, String b) {
        String tmp;
        if (a.length() < b.length()) {
            tmp = a;
            a = b;
            b = tmp;
        }
        char[] chars1 = a.toCharArray();
        char[] chars2 = b.toCharArray();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (char aChar : chars1) {
            list1.add(aChar - 48);
        }
        for (char bChar : chars2) {
            list2.add(bChar - 48);
        }
        Collections.reverse(list1);
        Collections.reverse(list2);
        for (int i = 0; i < Math.min(list1.size(), list2.size()); i++) {
            list1.set(i, list1.get(i) + list2.get(i));
        }
        for (int i = 0; i < list1.size(); i++) {
            if (list1.get(i) >= 2) {
                list1.set(i, list1.get(i) - 2);
                if (i != list1.size() - 1) {
                    list1.set(i + 1, list1.get(i + 1) + 1);
                } else {
                    list1.add(1);
                }
            }
        }

        Collections.reverse(list1);
//        Character[] tmp1 = new Character[]{};
//        Character[] characters = list1.toArray(tmp1);
        StringBuilder sb = new StringBuilder();
        for (Integer ch : list1) {
            sb.append(ch);
        }
        return sb.toString();

    }

}

