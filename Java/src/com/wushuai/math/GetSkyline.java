package com.wushuai.math;

import org.junit.Test;

import java.util.*;

/**
 * <p>GetSkyline</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-17 19:05
 */
public class GetSkyline {

    /**
     * 218. 天际线问题
     *
     * 内存超限
     *
     * @param buildings
     * @return
     */
    public List<List<Integer>> getSkyline(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        if (buildings.length == 0) {
            return res;
        }
        int[][] map = new int[10001][buildings[buildings.length - 1][1] + 1];
        int right = 0;
        int left = 10005;
        for (int i = 0; i < buildings.length; i++) {
            int x = buildings[i][2];
            int y1 = buildings[i][0];
            int y2 = buildings[i][1];
            right = Integer.max(right, y2);
            left = Integer.min(left, y1);
            for (int j = 0; j < x; j++) {
                map[j][y1] = 1;
                map[j][y2] = 1;
            }
            for (int j = y1; j <= y2; j++) {
                map[x][j] = 1;
            }
        }
        int x = 0, y = left, in = 0;
        while (y != right) {
            map[x][y] = 0;
            if (map[x + 1][y] == 1) {
                x++;
                in = 0;
            } else if (x == 0 || map[x][y + 1] == 1) {
                if (in == 0) {
                    List<Integer> list = new ArrayList<>();
                    list.add(y);
                    list.add(x);
                    res.add(list);
                }
                in = 1;
                y++;
            } else if (map[x - 1][y] == 1) {
                x--;
                in = 0;
            }
            if (y == right) {
                List<Integer> list = new ArrayList<>();
                list.add(y);
                list.add(0);
                res.add(list);
            }
        }
        return res;
    }

    @Test
    public void test() {
        GetSkyline o = new GetSkyline();
        List<List<Integer>> skyline = o.getSkyline(new int[][]{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}});
        for (int i = 0; i < skyline.size(); i++) {
            List<Integer> list = skyline.get(i);
            for (int j = 0; j < list.size(); j++) {
                System.out.print(list.get(j));
            }
            System.out.println();
        }
    }
}
