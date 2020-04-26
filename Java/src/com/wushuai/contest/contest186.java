package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest186</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-26 10:23
 */
public class contest186 {

    /**
     * 5392. 分割字符串的最大得分
     *
     * @param s
     * @return
     */
    public int maxScore(String s) {
        int zero = 0, one = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                one++;
            }
        }
        int res = 0, o = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == '0') {
                zero++;
            } else {
                o++;
            }
            res = Integer.max(res, zero + one - o);
        }
        return res;
    }

    /**
     * 5393. 可获得的最大点数
     *
     * @param cardPoints
     * @param k
     * @return
     */
    public int maxScore(int[] cardPoints, int k) {
        int res = 0;
        for (int i = 0; i < k; i++) {
            res += cardPoints[i];
        }
        int tmp = res;
        for (int i = 0; i < k; i++) {
            tmp = tmp - cardPoints[k - i - 1] + cardPoints[cardPoints.length - i - 1];
            res = Integer.max(tmp, res);
        }
        return res;
    }


    /**
     * 5394. 对角线遍历 II
     *
     * @param nums
     * @return
     */
    public int[] findDiagonalOrder(List<List<Integer>> nums) {
        int maxY = 0;
        for (int i = 0; i < nums.size(); i++) {
            List<Integer> list = nums.get(i);
            maxY = Integer.max(maxY, list.size());
        }
        int[][] map = new int[nums.size()][maxY];
        for (int i = 0; i < nums.size(); i++) {
            Arrays.fill(map[i], -1);
            List<Integer> list = nums.get(i);
            for (int j = 0; j < list.size(); j++) {
                map[i][j] = list.get(j);
            }
        }
        int[] res = new int[100000];
        int i = 0, idx = 0;

        for (int num = 0; num < map.length + maxY - 1; num++) {
            for (int j = 0; j < maxY; j++) {
                i = num - j;
                if ((i >= 0) && (i < map.length)) {
                    if (map[i][j] != -1) {
                        res[idx++] = map[i][j];
                    }
                }
            }
        }
        return Arrays.copyOf(res, idx);
    }

    @Test
    public void test() {
        contest186 o = new contest186();
//        System.out.println(o.maxScore("011101"));
//        System.out.println(o.maxScore("00111"));
//        System.out.println(o.maxScore("1111"));
//        System.out.println(o.maxScore("0000"));
//        System.out.println(o.maxScore("0101010"));
//        System.out.println(o.maxScore("111110000"));
//        System.out.println(o.maxScore(new int[]{96, 90, 41, 82, 39, 74, 64, 50, 30}, 8));
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        list.add(1);
        List<List<Integer>> lists = new ArrayList<>();
        lists.add(list);
        lists.add(list);
        System.out.println(Arrays.toString(o.findDiagonalOrder(lists)));

    }
}
