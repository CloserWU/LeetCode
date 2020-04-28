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
     * MLE
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

    /**
     * 左下到右上的对角线，每一条对角线的 i + j 值都相同
     * 左上到右下的对角线，每一条对角线的 i - j 值都相同
     *
     * @param nums
     * @return
     */
    public int[] findDiagonalOrderV2(List<List<Integer>> nums) {
        Map<Integer, List<Integer>> map = new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        });
        int len = 0;
        for (int i = 0; i < nums.size(); i++) {
            List<Integer> list = nums.get(i);
            for (int j = 0; j < list.size(); j++) {
                List<Integer> val;
                if (map.containsKey(i + j)) {
                    val = map.get(i + j);
                } else {
                    val = new ArrayList<>();
                }
                len++;
                val.add(list.get(j));
                map.put(i + j, val);
            }
        }
        int[] res = new int[len];
        int idx = 0;
        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            List<Integer> value = entry.getValue();
            for (int i = value.size() - 1; i >= 0; i--) {
                res[idx++] = value.get(i);
            }
        }
        return res;
    }

    /**
     * 1425. 带限制的子序列和
     * DP + 双端队列获取窗口值中的最大值，
     * 队列中放的是dp数组的范围内最大值，每次入队的都是计算后的dp数组值
     * 队列要先出队（先头在尾）再入队
     * dp[i] = {max(dp[i - j] + nums[i], nums[i]) | 1 <= j <= k}
     *
     * @param nums
     * @param k
     * @return
     */
    public int constrainedSubsetSum(int[] nums, int k) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int res = dp[0];
        Deque<Integer> deque = new LinkedList<>();
        deque.addLast(0);
        for (int i = 1; i < nums.length; i++) {
            // 出口头部是最大值，尾部是最小值
            dp[i] = Integer.max(dp[deque.getFirst()] + nums[i], nums[i]);
            //窗口右滑
            // 队列满，或窗口最右是窗口内最大的数，则执行头部出队操作
            if (deque.size() == k || i - deque.getFirst() == k) {
                deque.pollFirst();
            }
            // 若将要新加入的元素大于尾部，则尾部出队
            while (deque.size() > 0 && dp[i] > dp[deque.getLast()]) {
                deque.pollLast();
            }
            // 加入新元素（已经计算好的下标）
            deque.addLast(i);
            res = Integer.max(res, dp[i]);
        }
        return res;
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
        System.out.println(o.constrainedSubsetSum(new int[]{10, 2, -10, 5, 20}, 2));
        System.out.println(o.constrainedSubsetSum(new int[]{-1, -2, -3}, 1));
        System.out.println(o.constrainedSubsetSum(new int[]{10, -2, -10, -5, 20}, 2));

    }
}
