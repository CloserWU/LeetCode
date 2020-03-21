package com.wushuai.contest;

import org.junit.Test;
import org.omg.CORBA.INTERNAL;

import java.util.*;

/**
 * <p>contest176</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-21 08:49
 */
public class contest176 {
    /**
     * 1351. 统计有序矩阵中的负数
     *
     * @param grid
     * @return
     */
    public int countNegatives(int[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] < 0) {
                    res += grid[0].length - j;
                    break;
                }
            }
        }
        return res;
    }

    /**
     * 1352. 最后 K 个数的乘积
     * 前缀和，时间复杂度为O(1)
     * 求任意区间乘积，记录0的前缀和，时间复杂度O(1)
     */
    class ProductOfNumbers {

        private List<Integer> list;
        private List<Integer> cnt0; //前缀和记录0的个数

        public ProductOfNumbers() {
            list = new ArrayList<>();
            cnt0 = new ArrayList<>();
            list.add(1);
            cnt0.add(0);
        }

        public void add(int num) {
            if (num == 0) {
                list.add(1);
                cnt0.add(cnt0.get(cnt0.size() - 1) + 1);
            } else {
                list.add(list.get(list.size() - 1) * num);
                cnt0.add(cnt0.get(cnt0.size() - 1));
            }
        }

        public int getProduct(int k) {
            return getProduct(list.size() - k, list.size() - 1);
        }

        // 表示的是第s个元素到第e个元素间的乘积
        public int getProduct(int s, int e) {
            if (e < 1 || e >= list.size() || s < 1 || s >= list.size() || s > e) {
                throw new RuntimeException("Invalid input");
            }
            if (!cnt0.get(s - 1).equals(cnt0.get(e))) {
                return 0;
            }
            return list.get(e) / list.get(s - 1);
        }
    }


    /**
     * 1353. 最多可以参加的会议数目
     * 按结束时间从小到大排序，结束时间相同的，按开始时间从小到大排序。
     *
     * 此类问题 ,  贪心 ，按结束时间排序
     *
     * @param events 会议
     * @return 最大参会数
     */
    public int maxEvents(int[][] events) {
        //首先排序：开始时间小的在前。这样是方便我们顺序遍历，把开始时间一样的都放进堆
        Arrays.sort(events, (o1, o2) -> o1[0] - o2[0]);
        //小顶堆
        Queue<Integer> queue = new PriorityQueue<>();
        //结果、开始时间、events下标、有多少组数据
        int end = 1, len = events.length, i = 0, res = 0;
        while (i < len || !queue.isEmpty()) {
            // 选取start = 当前时间， 将对应的end加入堆
            while (i < len && events[i][0] == end) {
                queue.add(events[i++][1]);
            }
            //将end小于当前时间的pop出来
            while (!queue.isEmpty() && queue.peek() < end) {
                queue.poll();
            }
            // 访问次活动
            if (!queue.isEmpty()) {
                queue.poll();
                res++;
            }
            //每次向前移动一格
            end++;
        }
        return res;
    }

    @Test
    public void test1() {
        contest176 obj = new contest176();
//        System.out.println(obj.countNegatives(new int[][]{{3, 2}, {1, 0}}));
        System.out.println(obj.maxEvents(new int[][]{{1,4},{4,4},{2,2},{3,4},{1,1}}));
    }

}

