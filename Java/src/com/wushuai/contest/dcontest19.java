package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>dcontest19</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-22 11:16
 */
public class dcontest19 {

    /**
     * 1342. 将数字变成 0 的操作次数
     *
     * @param num
     * @return
     */
    public int numberOfSteps(int num) {
        int res = 0;
        while (num != 0) {
            if ((num & 1) == 1) {
                num--;
            } else {
                num /= 2;
            }
            res++;
        }
        return res;
    }

    /**
     * 1343. 大小为 K 且平均值大于等于阈值的子数组数目
     *
     * @param arr
     * @param k
     * @param threshold
     * @return
     */
    public int numOfSubarrays(int[] arr, int k, int threshold) {
//        Arrays.sort(arr);
        int res = 0;
        long ans = 0;
        int idx = k;
        while (idx != 0) {
            ans += arr[arr.length - idx];
            idx--;
        }
        if ((double) ans / k >= threshold * 1.0) {
            res++;
        }
        for (int i = arr.length - k - 1; i >= 0; i--) {
            ans -= arr[i + k];
            ans += arr[i];
            if ((double) ans / k >= threshold * 1.0) {
                res++;
            }
        }
        return res;
    }

    /**
     * 1344. 时钟指针的夹角
     *
     * @param hour
     * @param minutes
     * @return
     */
    public double angleClock(int hour, int minutes) {
        double res = Math.abs(hour % 12 * 30.0 + 30.0 * minutes / 60.0 - minutes / 60.0 * 360.0);
        if (res > 180.0) {
            return 360.0 - res;
        }
        return res;
    }

    /**
     * 1345. 跳跃游戏 IV
     * <p>
     * 前后的值会相互影响，所以不能用dp
     * <p>
     * WRONG!
     *
     * @param arr
     * @return
     */
    public int minJumps(int[] arr) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] dp = new int[arr.length + 1];
        for (int i = 0; i < arr.length; i++) {
            dp[i] = i;
            List<Integer> list;
            if (map.containsKey(arr[i])) {
                list = map.get(arr[i]);
                list.add(i);
            } else {
                list = new ArrayList<>();
                list.add(i);
                map.put(arr[i], list);
            }
        }
        dp[dp.length - 1] = 1000000;
        for (int i = 1; i < arr.length; i++) {
            List<Integer> list = map.get(arr[i]);
            dp[i] = Integer.min(dp[i - 1] + 1, dp[i]);
            dp[i] = Integer.min(dp[i + 1] + 1, dp[i]);
            for (Integer idx : list) {
                dp[i] = Integer.min(dp[i], dp[idx] + 1);
            }
            dp[i - 1] = Integer.min(dp[i - 1], dp[i] + 1);
            dp[i + 1] = Integer.min(dp[i + 1], dp[i] + 1);
            for (Integer idx : list) {
                dp[idx] = Integer.min(dp[idx], dp[i] + 1);
            }
        }
        return dp[arr.length - 1];
    }

    public int minJumps_v2(int[] arr) {
//        倒排加速(map既起到了倒排加速作用，又起到了记录值是否被访问的作用，如果有一个值被访问过了，删除该值对应的键)
        Map<Integer, List<Integer>> map = new HashMap<>();
        int[] dist = new int[arr.length];
        Arrays.fill(dist, 1000000);
        boolean[] visit = new boolean[arr.length];
        Queue<Integer> queue = new LinkedList<>();
        dist[arr.length - 1] = 0;
        queue.add(arr.length - 1);
        visit[arr.length - 1] = true;
        for (int i = 0; i < arr.length - 1; i++) {
            List<Integer> list;
            if (map.containsKey(arr[i])) {
                list = map.get(arr[i]);
            } else {
                list = new ArrayList<>();
            }
            list.add(i);
            map.put(arr[i], list);
        }
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            if (poll + 1 < arr.length && !visit[poll + 1] && map.containsKey(arr[poll + 1])) {
                dist[poll + 1] = Integer.min(dist[poll + 1], dist[poll] + 1);
                queue.add(poll + 1);
                visit[poll + 1] = true;
            }
            if (poll - 1 >= 0 && !visit[poll - 1] && map.containsKey(arr[poll - 1])) {
                dist[poll - 1] = Integer.min(dist[poll - 1], dist[poll] + 1);
                queue.add(poll - 1);
                visit[poll - 1] = true;
            }
            if (map.containsKey(arr[poll])) {
                List<Integer> list = map.get(arr[poll]);
                for (Integer v : list) {
                    if (!visit[v]) {
                        visit[v] = true;
                        dist[v] = Integer.min(dist[v], dist[poll] + 1);
                        queue.add(v);
                    }
                }
                map.remove(arr[poll]);
            }
        }
        return dist[0];
    }

    @Test
    public void test() {
        dcontest19 o = new dcontest19();
//        System.out.println(o.numberOfSteps(14));
//        System.out.println(o.numberOfSteps(8));
//        System.out.println(o.numberOfSteps(123));
//        System.out.println(o.numOfSubarrays(new int[]{11, 13, 17, 23, 29, 31, 7, 5, 2, 3}, 3, 5));
//        System.out.println(o.numOfSubarrays(new int[]{2, 2, 2, 2, 5, 5, 5, 8}, 3, 4));
//        System.out.println(o.numOfSubarrays(new int[]{1, 1, 1, 1, 1}, 1, 0));
//        System.out.println(o.numOfSubarrays(new int[]{7, 7, 7, 7, 7, 7, 7}, 7, 7));
//        System.out.println(o.numOfSubarrays(new int[]{4, 4, 4, 4}, 4, 1));

//        System.out.println(o.angleClock(12, 30));
//        System.out.println(o.angleClock(3, 30));
//        System.out.println(o.angleClock(3, 15));
//        System.out.println(o.angleClock(4, 50));
//        System.out.println(o.angleClock(12, 0));

        System.out.println(o.minJumps_v2(new int[]{100, -23, -23, 404, 100, 23, 23, 23, 3, 404}));
        System.out.println(o.minJumps_v2(new int[]{7}));
        System.out.println(o.minJumps_v2(new int[]{7, 6, 9, 6, 9, 6, 9, 7}));
        System.out.println(o.minJumps_v2(new int[]{6, 1, 9}));
        System.out.println(o.minJumps_v2(new int[]{11, 22, 7, 7, 7, 7, 7, 7, 7, 22, 13}));
    }
}
