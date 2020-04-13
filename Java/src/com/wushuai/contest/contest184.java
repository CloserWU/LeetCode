package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest184</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-13 11:29
 */
public class contest184 {

    /**
     * 1408. 数组中的字符串匹配
     *
     * @param words
     * @return
     */
    public List<String> stringMatching(String[] words) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < words.length; i++) {
            for (int j = 0; j < words.length; j++) {
                if (i == j) {
                    continue;
                }
                if (words[i].contains(words[j])) {
                    if (!res.contains(words[j])) {
                        res.add(words[j]);
                    }
                }
            }
        }
        return res;
    }

    /**
     * 1409. 查询带键的排列
     *
     * @param queries
     * @param m
     * @return
     */
    public int[] processQueries(int[] queries, int m) {
        List<Integer> p = new LinkedList<>();
        for (int i = 0; i < m; i++) {
            p.add(i + 1);
        }
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int pos = p.indexOf(queries[i]);
            p.remove((int) pos);
            p.add(0, queries[i]);
            res[i] = pos;
        }
        return res;
    }

    /**
     * 1410. HTML 实体解析器
     *
     * @param text
     * @return
     */
    public String entityParser(String text) {
        Map<String, String> map = new HashMap<>();
        map.put("&quot;", "\\\"");
        map.put("&apos;", "'");
        map.put("&amp;", "&");
        map.put("&gt;", ">");
        map.put("&lt;", "<");
        map.put("&frasl;", "/");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                StringBuilder tmp = new StringBuilder();
                while (i < text.length() && text.charAt(i) != ';') {
                    tmp.append(text.charAt(i));
                    i++;
                }
                if (i < text.length() && text.charAt(i) == ';') {
                    tmp.append(';');
                }
                if (map.containsKey(tmp.toString())) {
                    sb.append(map.get(tmp.toString()));
                } else {
                    sb.append(tmp.toString());
                }
            } else {
                sb.append(text.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 1411. 给 N x 3 网格图涂色的方案数
     *
     * @param n
     * @return
     */
    public int numOfWays(int n) {
        int tmp = 1000000007;
        long repeat = 6;
        long unRepeat = 6;
        if (n == 1) {
            return 12;
        }
        for (int i = 1; i < n; i++) {
            long newRepeat = (repeat * 3) % tmp + (unRepeat * 2) % tmp;
            long newUnRepeat = (repeat * 2) % tmp + (unRepeat * 2) % tmp;
            repeat = newRepeat;
            unRepeat = newUnRepeat;
        }
        return (int) ((repeat + unRepeat) % tmp);
    }

    @Test
    public void test1() {
        contest184 o = new contest184();
        System.out.println(o.stringMatching(new String[]{"mass", "as", "hero", "superhero"}));
        System.out.println(o.stringMatching(new String[]{"leetcode", "et", "code"}));
        System.out.println(o.stringMatching(new String[]{"blue", "green", "bu"}));
        System.out.println(o.stringMatching(new String[]{}));
        System.out.println(o.stringMatching(new String[]{"leetcoder", "leetcode", "od", "hamlet", "am"}));

        System.out.println(Arrays.toString(o.processQueries(new int[]{3, 1, 2, 1}, 5)));
        System.out.println(Arrays.toString(o.processQueries(new int[]{4, 1, 2, 2}, 4)));
        System.out.println(Arrays.toString(o.processQueries(new int[]{7, 5, 5, 8, 3}, 8)));

        System.out.println(o.entityParser("&amp; is an HTML entity but &ambassador; is not."));
        System.out.println(o.entityParser("and I quote: &quot;...&quot;"));
        System.out.println(o.entityParser("Stay home! Practice on Leetcode :)"));
        System.out.println(o.entityParser("x &gt; y &amp;&amp; x &lt; y is always false"));
        System.out.println(o.entityParser("leetcode.com&frasl;problemset&frasl;all"));
        System.out.println(o.entityParser("&leetcode.com&frasl;problemset&frasl;all&"));
        System.out.println(o.entityParser(";leetcode.com&frasl;problemset&frasl;all&amp"));

        System.out.println(o.numOfWays(2));
        System.out.println(o.numOfWays(3));
        System.out.println(o.numOfWays(7));
        System.out.println(o.numOfWays(5000));
    }
}

