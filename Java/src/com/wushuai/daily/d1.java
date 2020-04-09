package com.wushuai.daily;

import org.junit.Test;

import java.math.BigInteger;
import java.util.*;

/**
 * <p>d1</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-07 11:05
 */
public class d1 {

    /**
     * 4.01
     * 1111. 有效括号的嵌套深度
     *
     * @param seq
     * @return
     */
    public int[] maxDepthAfterSplit(String seq) {
        int stack = 0;
        int[] res = new int[seq.length()];
        // 求出每个括号的深度
        // ((())()())
        // 1233222221
        for (int i = 0; i < seq.length(); i++) {
            if (seq.charAt(i) == '(') {
                res[i] = ++stack;
            } else {
                res[i] = stack--;
            }
        }
        // 深度位偶数的括号分配给A，奇数的分配给B
        // ((())()())
        // BABBAAAAAB
        for (int i = 0; i < res.length; i++) {
            if (res[i] % 2 == 0) {
                res[i] = 1;
            } else {
                res[i] = 0;
            }
        }
        return res;
    }

    /**
     * 4.02
     * 289. 生命游戏
     * https://leetcode-cn.com/problems/game-of-life/solution/c-wei-yun-suan-yuan-di-cao-zuo-ji-bai-shuang-bai-b/
     *
     * @param board
     */
    public void gameOfLife(int[][] board) {
        // 左上逆时针 x、y的梯度
        int[] dy = new int[]{-1, 0, 1, 1, 1, 0, -1, -1};
        int[] dx = new int[]{-1, -1, -1, 0, 1, 1, 1, 0};
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                int sum = 0;
                for (int k = 0; k < 8; k++) {
                    int nx = i + dx[k];
                    int ny = j + dy[k];
                    if (nx >= 0 && nx < board.length && ny >= 0 && ny < board[0].length) {
                        // 与 只加最低位
                        sum += (board[nx][ny] & 1);
                    }
                }
                if (board[i][j] == 1) {
                    if (sum == 3 || sum == 2) {
                        // 依然存活 ，高位置1
                        board[i][j] |= 2;
                    }
                    // 死亡 高位置0 不变
                } else {
                    if (sum == 3) {
                        // 复活 高位置1
                        board[i][j] |= 2;
                    }
                }
            }
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] >>>= 1;
            }
        }
    }


    /**
     * 4.03
     * 8. 字符串转换整数 (atoi)
     *
     * @param str
     * @return
     */
    public int myAtoi(String str) {
        str = str.trim();
        if (str.length() == 0) {
            return 0;
        }
        if (str.charAt(0) == '+' || str.charAt(0) == '-') {
            if (str.length() == 1) {
                return 0;
            }
            if (str.charAt(1) > '9' || str.charAt(1) < '0') {
                return 0;
            }
        } else if (str.charAt(0) > '9' || str.charAt(0) < '0') {
            return 0;
        }


        String s = str.split("\\s+")[0];
        while (s.charAt(s.length() - 1) > '9' || str.charAt(s.length() - 1) < '0') {
            s = s.substring(0, s.length() - 1);
        }
        if (s.length() == 0) {
            return 0;
        }
        if (s.contains(".")) {
            s = s.split("\\.")[0];
        }
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && (s.charAt(0) == '+' || s.charAt(0) == '-')) {
                continue;
            }
            if (s.charAt(i) > '9' || s.charAt(i) < '0') {
                s = s.substring(0, i);
                break;
            }
        }
        BigInteger bg = new BigInteger(s);
        if (bg.compareTo(BigInteger.valueOf(-2147483648L)) < 0) {
            return -2147483648;
        } else if (bg.compareTo(BigInteger.valueOf(2147483647L)) > 0) {
            return 2147483647;
        } else {
            return bg.intValue();
        }
    }

    /**
     * DFA 有限自动机
     * ''        +/-     number      other
     * ---------------------------------
     * start     | start    signed   in_number   end
     * signed    | end      end      in_numner   end
     * in_number | end      end      in_numner   end
     * end       | end      end      end         end
     */
    class Automation {
        int[][] dfa;

        Automation() {
            dfa = new int[][]{
                    {0, 1, 2, 3},
                    {3, 3, 2, 3},
                    {3, 3, 2, 3},
                    {3, 3, 3, 3}
            };
        }

        int getCol(char c) {
            if (c == ' ') {
                return 0;
            } else if (c <= '9' && c >= '0') {
                return 2;
            } else if (c == '+' || c == '-') {
                return 1;
            } else {
                return 3;
            }
        }

        boolean match(String text) {
            int state = 0;
            int i;
            for (i = 0; i < text.length(); i++) {
                state = dfa[state][getCol(text.charAt(i))];
                if (state == 3 && i != text.length() - 1) {
                    return false;
                }
            }
            return true;
        }

        int getTextValue(String text) {
            long ans = 0;
            int sign = 1;
            int state = 0;
            for (int i = 0; i < text.length(); i++) {
                state = dfa[state][getCol(text.charAt(i))];
                if (state == 2) {
                    ans = ans * 10 + text.charAt(i) - '0';
                    ans = sign == 1 ? Math.min(ans, Integer.MAX_VALUE) : Math.min(ans, -(long) Integer.MIN_VALUE);
                } else if (state == 1) {
                    sign = text.charAt(i) == '+' ? 1 : -1;
                }
            }
            return (int) ans * sign;
        }

    }

    public int myAtoi_v2(String str) {
        Automation automation = new Automation();
        return automation.getTextValue(str);
    }

    /**
     * 4.04
     * 42. 接雨水
     * <p>
     * 对每个位置，找到左边最高的柱子，再找到右边最高的柱子，然后选取两者最小值， 与当前位置柱子高度相减，
     * 得到这个位置能存多少雨水
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int[] left = new int[height.length];
        int maxLeft = 0;
        int[] right = new int[height.length];
        int maxRight = 0;
        int res = 0;
        for (int i = 0; i < height.length; i++) {
            left[i] = Integer.max(maxLeft, height[i]);
            maxLeft = left[i];
        }
        for (int i = height.length - 1; i >= 0; i--) {
            right[i] = Integer.max(maxRight, height[i]);
            maxRight = right[i];
        }
        for (int i = 1; i < height.length - 1; i++) {
            res += Math.max(Math.min(left[i - 1], right[i + 1]) - height[i], 0);
        }
        return res;
    }


    class Cache {
        int key;
        int value;
        int visit;
        long time;

        Cache(int key, int value, int visit, long time) {
            this.key = key;
            this.value = value;
            this.visit = visit;
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Cache cache = (Cache) o;

            if (key != cache.key) {
                return false;
            }
            if (value != cache.value) {
                return false;
            }
            return visit == cache.visit;
        }

        @Override
        public int hashCode() {
            int result = key;
            result = 31 * result + value;
            result = 31 * result + visit;
            return result;
        }
    }

    /**
     * 4.05
     * 460. LFU缓存
     * Your LFUCache object will be instantiated and called as such:
     * LFUCache obj = new LFUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */
    class LFUCache {

        int capacity = 0;
        Map<Integer, Cache> caches = null;
        List<Cache> list = null;
        long time = 0;

        public LFUCache(int capacity) {
            caches = new HashMap<>();
            list = new ArrayList<>();
            this.capacity = capacity;
        }

        public int get(int key) {
            if (capacity == 0) {
                return -1;
            }
            ++time;
            if (caches.containsKey(key)) {
                Cache cache = caches.get(key);
                int pos = list.indexOf(cache);

                cache.visit += 1;
                cache.time = time;

                list.set(pos, cache);
                caches.put(key, cache);

                list.sort(new Comparator<Cache>() {
                    @Override
                    public int compare(Cache o1, Cache o2) {
                        if (o2.visit == o1.visit) {
                            return Long.compare(o2.time, o1.time);
                        }
                        return Integer.compare(o2.visit, o1.visit);
                    }
                });
                return cache.value;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            ++time;
            if (caches.containsKey(key)) {
                Cache cache = caches.get(key);
                int pos = list.indexOf(cache);
                cache.visit += 1;
                cache.value = value;
                cache.time = time;
                list.set(pos, cache);
                caches.put(key, cache);
            } else {
                Cache cache = new Cache(key, value, 1, time);
                if (list.size() == capacity) {
                    caches.remove(list.get(list.size() - 1).key);
                    list.remove(list.size() - 1);
                }
                list.add(cache);
                caches.put(key, cache);
            }
            list.sort(new Comparator<Cache>() {
                @Override
                public int compare(Cache o1, Cache o2) {
                    if (o2.visit == o1.visit) {
                        return Long.compare(o2.time, o1.time);
                    }
                    return Integer.compare(o2.visit, o1.visit);
                }
            });
        }
    }


    @Test
    public void test1() {
        d1 o = new d1();

//        System.out.println(o.minDistance("acoood", "aoooad"));
//        LFUCache cache = new LFUCache(0 /* capacity (缓存容量) */);
//
//        cache.put(0, 0);
//        cache.get(0);
        /*cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));       // 返回 1
        cache.put(3, 3);    // 去除 key 2
        System.out.println(cache.get(2));       // 返回 -1 (未找到key 2)
        System.out.println(cache.get(3));       // 返回 3
        cache.put(4, 4);    // 去除 key 1
        System.out.println(cache.get(1));       // 返回 -1 (未找到 key 1)
        System.out.println(cache.get(3));       // 返回 3
        System.out.println(cache.get(4));       // 返回 4
*/

        System.out.println(o.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
        System.out.println(o.myAtoi("   -42"));
        System.out.println(o.myAtoi("4193 with words"));
        System.out.println(o.myAtoi("words and 987"));
        System.out.println(o.myAtoi("-91283472332"));
        System.out.println(o.myAtoi("91283472332"));
        System.out.println(o.myAtoi("-2147483648"));
        System.out.println(o.myAtoi(" 874 in s"));
        System.out.println(o.myAtoi(" 3.1512744"));
        System.out.println(o.myAtoi("-63...45845"));

        o.gameOfLife(new int[][]{{0, 1, 0}, {0, 0, 1}, {1, 1, 1}, {0, 0, 0}});

//        System.out.println(Arrays.toString(o.maxDepthAfterSplit("(()())")));
//        System.out.println(Arrays.toString(o.maxDepthAfterSplit("()(())()")));
        System.out.println(Arrays.toString(o.maxDepthAfterSplit("((())()())")));

    }
}

