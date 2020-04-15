package com.wushuai.graph;

import org.junit.Test;

/**
 * <p>MinSwapsCouples</p>
 * <p>765</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-15 11:14
 */
public class MinSwapsCouples {

    int findRoot(int[] father, int x) {
        while (father[x] != x) {
            x = findRoot(father, father[x]);
        }
        return x;
    }

    void union(int[] father, int[] height, int x, int y) {
        x = findRoot(father, x);
        y = findRoot(father, y);
        if (x != y) {
            if (height[x] > height[y]) {
                father[y] = x;
            } else if (height[x] < height[y]) {
                father[x] = y;
            } else {
                father[x]  =y;
                height[x]++;
            }
        }
    }

    /**
     * 765. 情侣牵手
     * 并查集
     * https://leetcode-cn.com/problems/couples-holding-hands/solution/bing-cha-ji-union-find-by-shty/
     *
     * @param row
     * @return
     */
    public int minSwapsCouples(int[] row) {
        int[] father = new int[row.length];
        int[] height = new int[row.length];
        for (int i = 0; i < father.length; i++) {
            father[i] = i;
        }
        for (int i = 0; i < row.length; i += 2) {
            // 要除以二， 0 和 1 是同根的 同理 8 9、 10 11
            union(father, height, row[i] / 2, row[i + 1] / 2);
        }
        int cnt = 0;
        for (int i = 0; i < father.length; i++) {
            if (i == findRoot(father, i)) {
                cnt++;
            }
        }
        return row.length - cnt;
    }


    @Test
    public void test1() {
        MinSwapsCouples o = new MinSwapsCouples();
        System.out.println(o.minSwapsCouples(new int[]{0, 2, 1, 3}));
        System.out.println(o.minSwapsCouples(new int[]{0, 7, 3, 6, 8, 2, 5, 1, 4, 9}));
        System.out.println(o.minSwapsCouples(new int[]{3, 2, 0, 1}));
    }
}

