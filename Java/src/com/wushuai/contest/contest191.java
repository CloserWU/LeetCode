package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest191</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-06-17 8:48
 */
public class contest191 {

    /**
     * 1464. 数组中两元素的最大乘积
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int res = -1;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                res = Integer.max(res, (nums[i] - 1) * (nums[j] - 1));
            }
        }
        return res;
    }


    /**
     * 1465. 切割后面积最大的蛋糕
     *
     * @param h
     * @param w
     * @param horizontalCuts
     * @param verticalCuts
     * @return
     */
    public int maxArea(int h, int w, int[] horizontalCuts, int[] verticalCuts) {
        Arrays.sort(horizontalCuts);
        Arrays.sort(verticalCuts);
        int tmph1 = horizontalCuts[0];
        int tmph2 = h - horizontalCuts[horizontalCuts.length - 1];
        int h1 = Integer.max(tmph1, tmph2);
        for (int i = 1; i < horizontalCuts.length; i++) {
            h1 = Integer.max(h1, horizontalCuts[i] - horizontalCuts[i - 1]);
        }
        h = h1;
        int tmpw1 = verticalCuts[0];
        int tmpw2 = w - verticalCuts[verticalCuts.length - 1];
        int w1 = Integer.max(tmpw1, tmpw2);
        for (int i = 1; i < verticalCuts.length; i++) {
            w1 = Integer.max(w1, verticalCuts[i] - verticalCuts[i - 1]);
        }
        w = w1;
        long res = (long) w * h;
        return (int) (res % 1000000007);
    }


    int findRoot(int[] parent, int x) {
        while (parent[x] != x) {
            x = parent[x];
        }
        return x;
    }


    /**
     * 1466. 重新规划路线
     *
     * @param n
     * @param connections
     * @return
     */
    public int minReorder(int n, int[][] connections) {
        int[] parent = new int[n];
        boolean[] visit = new boolean[connections.length];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
        int res = 0;
        while (n != 1) {
            for (int i = 0; i < connections.length; i++) {
                if (!visit[i]) {
                    int i1 = connections[i][0];
                    int i2 = connections[i][1];
                    if (findRoot(parent, i1) == 0) {
                        res++;
                        parent[i2] = i1;
                        visit[i] = true;
                        n--;
                    } else if (findRoot(parent, i2) == 0) {
                        parent[i1] = i2;
                        visit[i] = true;
                        n--;
                    }
                }
            }
        }
        return res;
    }

    @Test
    public void test() {
        contest191 o = new contest191();
        System.out.println(o.maxArea(100000, 500000, new int[]{5, 102, 301, 5, 1, 954, 475}, new int[]{2, 987, 456, 123, 15}));
        System.out.println(o.minReorder(6, new int[][]{{0, 1}, {1, 3}, {2, 3}, {4, 0}, {4, 5}}));
    }
}
