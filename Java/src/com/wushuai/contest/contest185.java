package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest185</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-19 10:30
 */
public class contest185 {

    /**
     * 5388. 重新格式化字符串
     *
     * @param s
     * @return
     */
    public String reformat(String s) {
        List<Character> num = new ArrayList<>();
        List<Character> ch = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) <= 'z' && s.charAt(i) >= 'a') {
                ch.add(s.charAt(i));
            } else {
                num.add(s.charAt(i));
            }
        }
        StringBuilder sb = new StringBuilder();
        if (Math.abs(ch.size() - num.size()) > 1) {
            return "";
        } else {
            int turn = 1;
            if (num.size() > ch.size()) {
                turn = -1;
            }
            int idx1 = 0;
            int idx2 = 0;
            for (int i = 0; i < s.length(); i++) {
                if (turn == -1) {
                    sb.append(num.get(idx1++));
                } else {
                    sb.append(ch.get(idx2++));
                }
                turn = -turn;
            }
        }
        return sb.toString();
    }

    /**
     * 5389. 点菜展示表
     *
     * @param orders
     * @return
     */
    public List<List<String>> displayTable(List<List<String>> orders) {
        Map<String, List<String>> map = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
            }
        });
        Map<String, Integer> m = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        for (int i = 0; i < orders.size(); i++) {
            List<String> list = orders.get(i);
            String food = list.get(2);
            String table = list.get(1);
            if (!m.containsKey(food)) {
                m.put(food, 0);
            }
            List<String> foods;
            if (map.containsKey(table)) {
                foods = map.get(table);
            } else {
                foods = new ArrayList<>();
            }
            foods.add(food);
            map.put(table, foods);
        }
        List<List<String>> res = new ArrayList<>();
        List<String> firstCol = new ArrayList<>();
        firstCol.add("Table");
        firstCol.addAll(m.keySet());
        res.add(firstCol);

        for (Map.Entry<String, List<String>> e : map.entrySet()) {
            List<String> col = new ArrayList<>();
            col.add(e.getKey());
            List<String> foods = e.getValue();
            for (int i = 0; i < foods.size(); i++) {
                String food = foods.get(i);
                m.put(food, m.get(food) + 1);
            }
            for (Map.Entry<String, Integer> ee : m.entrySet()) {
                col.add(String.valueOf(ee.getValue()));
                ee.setValue(0);
            }
            res.add(col);
        }
        return res;
    }

    boolean insertOne(List<Integer> list, int pre, int ch) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == pre) {
                list.set(i, list.get(i) * 10 + ch);
                return true;
            }
        }
        return false;
    }

    /**
     * 5390. 数青蛙
     * <p>
     * O(n^2) TLE
     *
     * @param croakOfFrogs
     * @return
     */
    public int minNumberOfFrogs(String croakOfFrogs) {
        char[] chars = croakOfFrogs.toCharArray();
        List<Integer> list = new ArrayList<>();
        int left = 0, right = 0;
        int maxLen = 0;
        for (int i = 0; i < croakOfFrogs.length(); i++) {
            if (chars[i] == 'c') {
                list.add(1);
                right++;
                maxLen = Integer.max(maxLen, right - left);
            } else if (chars[i] == 'r') {
                boolean b = insertOne(list, 1, 2);
                if (!b) {
                    return -1;
                }
            } else if (chars[i] == 'o') {
                boolean b = insertOne(list, 12, 3);
                if (!b) {
                    return -1;
                }
            } else if (chars[i] == 'a') {
                boolean b = insertOne(list, 123, 4);
                if (!b) {
                    return -1;
                }
            } else if (chars[i] == 'k') {
                boolean b = insertOne(list, 1234, 5);
                if (!b) {
                    return -1;
                } else {
                    left++;
                }
            } else {
                return -1;
            }
        }
        for (int j = 0; j < list.size(); j++) {
            if (list.get(j) != 12345) {
                return -1;
            }
        }
        return maxLen;
    }

    public int minNumberOfFrogs_v2(String croakOfFrogs) {
        int c = 0, r = 0, o = 0, a = 0, k = 0;
        char[] chars = croakOfFrogs.toCharArray();
        int res = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'c') {
                if (k > 0) {
                    k--;
                } else {
                    res++;
                }
                c++;
            } else if (chars[i] == 'r') {
                r++;
                c--;
            } else if (chars[i] == 'o') {
                r--;
                o++;
            } else if (chars[i] == 'a') {
                o--;
                a++;
            } else if (chars[i] == 'k') {
                a--;
                k++;
            }

            if (c < 0 || r < 0 || o < 0 || a < 0 || k < 0) {
                break;
            }
        }
        if (c != 0 || r != 0 || o != 0 || a != 0) {
            return -1;
        }
        return res;
    }

    @Test
    public void test() {
        contest185 o = new contest185();
//        System.out.println(o.reformat("a0b1c2"));
//        System.out.println(o.reformat("leetcode"));
//        System.out.println(o.reformat("1229857369"));
//        System.out.println(o.reformat("covid2019"));
//        System.out.println(o.reformat("ab123"));
//        System.out.println(o.reformat("ab1233"));
//        System.out.println(o.reformat("aaaa111"));

        /*List<List<String>> in = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("David");
        list.add("3");
        list.add("Ceviche");
        in.add(list);
        list = new ArrayList<>();
        list.add("Corina");
        list.add("10");
        list.add("Beef Burrito");
        in.add(list);
        list = new ArrayList<>();
        list.add("David");
        list.add("3");
        list.add("Fried Chicken");
        in.add(list);
        list = new ArrayList<>();
        list.add("Carla");
        list.add("5");
        list.add("Water");
        in.add(list);
        list = new ArrayList<>();
        list.add("Carla");
        list.add("5");
        list.add("Ceviche");
        in.add(list);
        list = new ArrayList<>();
        list.add("Rous");
        list.add("3");
        list.add("Ceviche");
        in.add(list);
        o.displayTable(in);*/

        System.out.println(o.minNumberOfFrogs_v2("croakcroak"));
        System.out.println(o.minNumberOfFrogs_v2("crcoakroak"));
        System.out.println(o.minNumberOfFrogs_v2("croakcrook"));
        System.out.println(o.minNumberOfFrogs_v2("croakcroa"));
        System.out.println(o.minNumberOfFrogs_v2("cccroakroroakak"));
    }
}
