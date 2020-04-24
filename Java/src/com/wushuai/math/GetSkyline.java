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

    public List<List<Integer>> getSkyline_v2(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        if (buildings.length == 0) {
            return res;
        }
        int[] map = new int[buildings[buildings.length - 1][1] + 1];
        for (int i = 0; i < buildings.length; i++) {
            int y = buildings[i][2];
            int x1 = buildings[i][0];
            int x2 = buildings[i][1];
            for (int j = x1; j <= x2; j++) {
                map[j] = Integer.max(map[j], y);
            }
        }
        for (int i = 1; i < map.length; i++) {
            if (map[i - 1] < map[i]) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                list.add(map[i]);
                res.add(list);
            } else if (map[i - 1] > map[i]) {
                List<Integer> list = new ArrayList<>();
                list.add(i - 1);
                list.add(map[i]);
                res.add(list);
            }
            if (i == map.length - 1) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                list.add(0);
                res.add(list);
            }
        }
        return res;
    }


    /**
     * wrong
     * @param buildings
     * @return
     */
    public List<List<Integer>> getSkyline_v3(int[][] buildings) {
        List<List<Integer>> res = new ArrayList<>();
        if (buildings.length == 0) {
            return res;
        }
        int right = buildings[0][1];
        int idx = 0;
        List<Integer> tmp = new ArrayList<>();
        tmp.add(buildings[0][0]);
        tmp.add(buildings[0][2]);
        res.add(tmp);
        for (int i = 1; i < buildings.length; i++) {
            int y = buildings[i][2];
            int x1 = buildings[i][0];
            int x2 = buildings[i][1];
            if (x1 == buildings[idx][0]) {
                if (y > buildings[idx][2]) {
                    res.get(res.size() - 1).set(1, y);
                }
            } else if (x1 <= right) {
                if (y > buildings[idx][2]) {
                    List<Integer> list = new ArrayList<>();
                    list.add(x1);
                    list.add(y);
                    res.add(list);
                } else if (y < buildings[idx][2]) {
                    List<Integer> list = new ArrayList<>();
                    list.add(buildings[idx][1]);
                    list.add(y);
                    res.add(list);
                }
            } else if (x1 > right){
                List<Integer> list = new ArrayList<>();
                list.add(buildings[idx][1]);
                list.add(0);
                res.add(list);
                list = new ArrayList<>();
                list.add(x1);
                list.add(y);
                res.add(list);
            }
            if (right <= x2) {
                idx = i;
            }
            right = Integer.max(right, x2);
        }
        tmp = new ArrayList<>();
        tmp.add(right);
        tmp.add(0);
        res.add(tmp);
        return res;
    }

    public List<List<Integer>> getSkyline_v4(int[][] buildings) {
        // 建立优先队列，左端点高度标记为负值放入，右端点高度标记为正值放入，并按先x轴后y轴排序
        Queue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return Integer.compare(o1[1], o2[1]);
                }
                return Integer.compare(o1[0], o2[0]);
            }
        });
        for (int[] building : buildings) {
            // 左端点
            queue.add(new int[]{building[0], -building[2]});
            // 右端点
            queue.add(new int[]{building[1], building[2]});
        }
        List<List<Integer>> res = new ArrayList<>();
        // 高度哈希表， key：高度 ，由高到低排序。  val：此高度出现次数
        Map<Integer, Integer> height = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o2, o1);
            }
        });
        int cntHigh = 0, cntLeft = 0;
        height.put(0, 1);
        while (!queue.isEmpty()) {
            int[] arr = queue.poll();
            // 左端点，放入高度hash表
            if (arr[1] < 0) {
                height.put(-arr[1], height.getOrDefault(-arr[1], 0) + 1);
            } else {
                // 右端点，减少此高度出现次数一次
                // 若减为0，则清除
                height.put(arr[1], height.get(arr[1]) - 1);
                if (height.get(arr[1]) == 0) {
                    height.remove(arr[1]);
                }
            }
            // 当前最大高度，若大了，就整理放入res
            int maxHigh = height.keySet().iterator().next();
            if (maxHigh != cntHigh) {
                cntHigh = maxHigh;
                cntLeft = arr[0];
                res.add(Arrays.asList(cntLeft, cntHigh));
            }
        }
        return res;
    }

    @Test
    public void test() {
        GetSkyline o = new GetSkyline();
        List<List<Integer>> skyline = o.getSkyline_v3(new int[][]{{2, 9, 10}, {3, 7, 15}, {5, 12, 12}, {15, 20, 10}, {19, 24, 8}});
        skyline = o.getSkyline_v3(new int[][]{{0,2147483646,2147483647}});
        skyline = o.getSkyline_v3(new int[][]{{1,2,8},{1,2,10},{2,3,8},{3,5,10},{4,5,8},{7,18,8},{8,10,8}});
        for (int i = 0; i < skyline.size(); i++) {
            List<Integer> list = skyline.get(i);
            for (int j = 0; j < list.size(); j++) {
                System.out.print(list.get(j));
            }
            System.out.println();
        }
    }
}
