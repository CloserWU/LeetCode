package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>dcontest23</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-06 11:57
 */
public class dcontest23 {

    /**
     * 1399. 统计最大组的数目
     * @param n
     * @return
     */
    public int countLargestGroup(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        int max = 0;
        for (int i = 1; i <= n; i++) {
            int t = 0, j = i;
            while (j != 0) {
                t += j % 10;
                j /= 10;
            }
            if (map.containsKey(t)) {
                map.put(t, map.get(t) + 1);
            } else {
                map.put(t, 1);
            }
            max = Integer.max(max, map.get(t));
        }
        int res = 0;
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (e.getValue() == max) {
                res++;
            }
        }
        return res;
    }

    /**
     * 1400. 构造 K 个回文字符串
     * 构成回文串的特点是， 1 个字母可以构成回文串，2 个相同的字母可以构成回文串
     * 所以只要想一下单个（奇数个）字母怎么处理
     * 对字母计数
     * 判断奇数个字母的数量
     * @param s
     * @param k
     * @return
     */
    public boolean canConstruct(String s, int k) {
        if (s.length() < k) {
            return false;
        }
        int[] cnt = new int[26];
        for (int i = 0; i < s.length(); i++) {
            cnt[s.charAt(i) - 'a']++;
        }
        int odd = 0;
        for (int i = 0; i < cnt.length; i++) {
            if (cnt[i] % 2 == 1) {
                odd += 1;
            }
        }
        return odd <= k;
    }


    /**
     * 1401. 圆和矩形是否有重叠
     * @param radius
     * @param x_center
     * @param y_center
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return
     */
    public boolean checkOverlap(int radius, int x_center, int y_center, int x1, int y1, int x2, int y2) {
        if (x1 <= x_center && x2 >= x_center && y1 <= y_center && y2 >= y_center) {
            return true;
        } else if (x_center > x2 && y_center <= y2 && y_center >= y1) {
            return radius >= x_center - x2;
        } else if (x_center < x1 && y_center <= y2 && y_center >= y1) {
            return radius >= x1 - x_center;
        } else if (y_center < y1 && x_center <= x2 && x_center >= x1) {
            return radius >= y1 - y_center;
        } else if (y_center > y2 && x_center <= x2 && x_center >= x1) {
            return radius >= y_center - y2;
        } else {
            double v1 = Math.pow(x1 - x_center, 2) + Math.pow(y2 - y_center, 2);
            double v2 = Math.pow(x2 - x_center, 2) + Math.pow(y1 - y_center, 2);
            double v3 = Math.pow(x1 - x_center, 2) + Math.pow(y1 - y_center, 2);
            double v4 = Math.pow(x2 - x_center, 2) + Math.pow(y2 - y_center, 2);
            double d1 = Math.pow(radius, 2);
            double d2 = Double.min(Double.min(v1, v2), Double.min(v3, v4));
            return d2 <= d1;
        }
    }

    /**
     * 1402. 做菜顺序
     * @param satisfaction
     * @return
     */
    public int maxSatisfaction(int[] satisfaction) {
        Arrays.sort(satisfaction);
        int sum = 0;
        int res = 0;
        for (int i = satisfaction.length - 1; i >= 0; i--) {
            sum += satisfaction[i];
            if (sum < 0) {
                break;
            }
            res += sum;
        }
        return res;
    }

    @Test
    public void test1() {
        dcontest23 o = new dcontest23();
        System.out.println(o.countLargestGroup(13));
        System.out.println(o.countLargestGroup(2));
        System.out.println(o.countLargestGroup(15));
        System.out.println(o.countLargestGroup(24));
        System.out.println(o.countLargestGroup(1));

        System.out.println(o.checkOverlap(1, 0, 0, 1, -1, 3, 1));
        System.out.println(o.checkOverlap(1, 0, 0, -1, 0, 0, 1));
        System.out.println(o.checkOverlap(1, 1, 1, -3, -3, 3, 3));
        System.out.println(o.checkOverlap(1, 1, 1, 1, -3, 2, -1));
    }
}

