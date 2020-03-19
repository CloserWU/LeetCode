package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest179</p>
 * <p>
 *     dfs递归
 * </p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-17 08:51
 */
public class contest179 {

    public String generateTheString(int n) {
        StringBuilder sb = new StringBuilder();
        if (n % 2 == 0) {
            for (int i = 0; i < n - 1; i++) {
                sb.append("a");
            }
            sb.append("b");
        } else {
            for (int i = 0; i < n; i++) {
                sb.append("a");
            }
        }
        return sb.toString();
    }

    /**
     * 1375. 灯泡开关 III
     * 每次点亮的灯泡，若是所有已点亮灯泡的最右边的那个， 则蓝色+1， （此时点亮最远的灯的位置恰巧等于点亮灯的个数。）
     * @param light
     * @return
     */
    public int numTimesAllBlue(int[] light) {
        int ans = 0, maxReach = 0;
        for (int i = 0; i < light.length; i++) {
            maxReach = Math.max(maxReach, light[i]);
            if (i + 1 == maxReach) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * 1376. 通知所有员工所需的时间
     * 自底向上
     * @param n
     * @param headID
     * @param manager
     * @param informTime
     * @return
     */
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        int res = 0;
        for (int i = 0; i < manager.length; i++) {
            if (informTime[i] == 0) {
                int total = 0;
                int j = manager[i];
                while (j != -1) {
                    total += informTime[j];
                    j = manager[j];
                }
                res = Math.max(res, total);
            }
        }
        return res;
    }


    /**
     * 1377. T 秒后青蛙的位置
     * 这道题有趣的是， 要在bfs中采集每步的信息，这就不能在一个方法中倒腾，要用递归，递归函数的参数就是记录信息的绝佳位置
     * 每次青蛙跳，都到知道他下一步能眺望哪些位置，相当于图的一个结点，其相邻为访问节点的个数，
     *
     */

    int[] visit;
    int[][] edge;

    /**
     *
     * @param t 每次递归减一，表示剩余步数
     * @param target
     * @param p 概率， 每次用相邻节点 * 本来的p
     * @param node 当前访问节点
     * @return
     */
    int bfs(int t, int target, int p, int node) {
        // 剩余步数为0
        if (0 > t) {
            return 0;
        }
        int tmp = 0;
        // 记录相邻节点
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < edge.length; i++) {
            if (edge[i][0] == node && visit[edge[i][1] - 1] == 0) {
                tmp++;
                list.add(edge[i][1]);
                visit[edge[i][1] - 1] = 1;
            }
        }
        // 若为最后一步 或 没有相邻节点 则返回
        if (t == 0 || list.size() == 0) {
            return (node == target) ? p : 0;
        }
        int res = 0;
        // bfs
        for (int i = 0; i < list.size(); i++) {
            if ((res = bfs(t - 1, target, p * tmp, list.get(i))) != 0) {
                return res;
            }
        }
        return 0;
    }


    /**
     * 递归bfs
     * @param n
     * @param edges 本身表达了有向边， edges<from,to>边集表达了一个有向图， 现在重构为无向图edge<from, to>
     * @param t
     * @param target
     * @return
     */
    public double frogPosition(int n, int[][] edges, int t, int target) {
        visit = new int[n];
        edge = new int[(n - 1) * 2][2];
        int index = 1;
        for (int i = 0; i < (n - 1) * 2 - 1; i += 2) {
            edge[i][0] = edges[i / 2][0];
            edge[i][1] = edges[i / 2][1];
            edge[i + 1][1] = edges[i / 2][0];
            edge[i + 1][0] = edges[i / 2][1];
            visit[i / 2] = 0;
        }
        visit[0] = 1;
        int p = bfs(t, target, 1, 1);
        if (p == 0) {
            return 0;
        } else {
            return 1.0 / p;
        }

        /* 非递归版dfs
        Stack<Integer> stack = new Stack<>();
        stack.push(index);
        while (!stack.isEmpty()) {
            Integer peek = stack.peek();
            if (peek == target) {
                break;
            }

            stack.pop();
            tmp = 0;
            for (int i = 0; i < (n - 1) * 2 - 1; i++) {
                if (edge[i][0] == peek && visit[edge[i][1] - 1] == 0) {
                    stack.push(edge[i][1]);
                    tmp++;
                    visit[edge[i][1] - 1] = 1;
                }
            }

        }*/
    }

    @Test
    public void test1() {
        contest179 obj = new contest179();
        int[][] edge = new int[][]{{1, 2}, {1, 3}, {1, 7}, {2, 4}, {2, 6}, {3, 5}};
        System.out.println(obj.frogPosition(7, edge, 1, 7));
    }
}

