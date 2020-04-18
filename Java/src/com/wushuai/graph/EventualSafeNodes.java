package com.wushuai.graph;

import org.junit.Test;

import java.util.*;

/**
 * <p>EventualSafeNodes</p>
 * <p>802</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-18 10:52
 */
public class EventualSafeNodes {

    /**
     * 802. 找到最终的安全状态
     * https://leetcode-cn.com/problems/find-eventual-safe-states/solution/zhao-dao-zui-zhong-de-an-quan-zhuang-tai-by-leetco/
     * 有向边反向建图 ，拓扑排序
     *
     * @param graph
     * @return
     */
    public List<Integer> eventualSafeNodes(int[][] graph) {
        List<List<Integer>> G = new ArrayList<>(Collections.nCopies(graph.length, null));
        int[] inDegree = new int[graph.length];
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                List<Integer> edges = G.get(graph[i][j]);
                if (edges == null) {
                    edges = new ArrayList<>();
                }
                int to = i;
                edges.add(to);
                inDegree[to]++;
                G.set(graph[i][j], edges);
            }
        }
        Queue<Integer> queue = new LinkedList<>();
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
                res.add(i);
            }
        }
        while (!queue.isEmpty()) {
            Integer poll = queue.poll();
            List<Integer> edges = G.get(poll);
            if (edges == null) {
                continue;
            }
            for (int i = 0; i < edges.size(); i++) {
                int to = edges.get(i);
                inDegree[to]--;
                if (inDegree[to] == 0) {
                    queue.add(to);
                    res.add(to);
                }
            }
        }
        Collections.sort(res);
        return res;
    }

    /**
     * DFS 颜色填充
     * dfs访问过程中， 如遇到颜色不是白的，说明之前访问过。若颜色位灰，说明有环。返回false。若颜色位黑，跳过
     * 如访问到底也没有灰，说明这条路径没有环，则回溯回去的灰色节点都置位黑色
     * @param graph
     * @return
     */
    public List<Integer> eventualSafeNodes_v2(int[][] graph) {
        List<Integer> res = new ArrayList<>();
        int[] color = new int[graph.length];
        for (int i = 0; i < color.length; i++) {
            if (dfs(graph, color, i)) {
                res.add(i);
            }
        }
        return res;
    }

    // colors: WHITE 0, GRAY 1, BLACK 2;
    boolean dfs(int[][] graph, int[] color, int idx) {
        if (color[idx] > 0) {
            // 黑色true， 灰色false
            return color[idx] == 2;
        }
        color[idx] = 1;
        int[] edges = graph[idx];
        for (int edge : edges) {
            if (color[edge] == 2) {
                // 黑色跳过
                continue;
            }
            if (color[edge] == 1 || !dfs(graph, color, edge)) {
                // 灰色，或此路径有环 退出dfs
                return false;
            }
        }
        color[idx] = 2;
        return true;
    }

    @Test
    public void test() {
        EventualSafeNodes o = new EventualSafeNodes();
        System.out.println(o.eventualSafeNodes(new int[][]{{1, 2}, {2, 3}, {5}, {0}, {5}, {}, {}}));
    }
}
