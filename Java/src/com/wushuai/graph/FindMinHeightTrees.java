package com.wushuai.graph;

import org.junit.Test;

import java.util.*;

/**
 * <p>FindMinHeightTrees</p>
 * <p>310</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-10 09:37
 */
public class FindMinHeightTrees {

    void insertEdge(int from, int to, List<List<Integer>> graph) {
        List<Integer> edge = graph.get(from);
        if (edge == null) {
            edge = new ArrayList<>();
        }
        for (Integer anEdge : edge) {
            if (anEdge == to) {
                return;
            }
        }
        edge.add(to);
        graph.set(from, edge);
    }

    /**
     * 复杂度太高 TLE
     * 对于每个点，计算它所连接边的最大深度
     * @param from
     * @param graph
     * @param visit
     * @return
     */
    int dfs(int from, List<List<Integer>> graph, List<Boolean> visit) {
        int high = 0;
        List<Integer> edge = graph.get(from);
        if (edge == null) {
            return Integer.MAX_VALUE;
        }
        for (Integer e : edge) {
            if (!visit.get(e)) {
                visit.set(e, true);
                high = Integer.max(high, 1 + dfs(e, graph, visit));
            }
        }
        return high;
    }

    /**
     * 310. 最小高度树
     *
     * 拓扑排序， 将度为1的边加入队列
     * 取出队列元素，消去与之相邻的边，将度为1的边加入队列，
     * 最后出队的就是最深的
     *
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new ArrayList<>();
        if (n == 1) {
            res.add(0);
            return res;
        }
        if (n == 2) {
            res.add(0);
            res.add(1);
            return res;
        }
        int[] degree = new int[n];
        List<List<Integer>> graph = new ArrayList<>();
        // 不能用Collections.nCopied(n, new ArrayList<>());
        // 这是将new的List 的地址 重复赋值给了graph的n个元素， 他们都指向一个地址
        // 要分别new 出来
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        // 构造无向图邻接表
        for (int i = 0; i < edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];
            degree[from]++;
            degree[to]++;
            graph.get(from).add(to);
            graph.get(to).add(from);
        }
        // 无向图边缘节点入队
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 1) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            // 每次清空res，最后一次加入res的就是最深的点
            res.clear();
            // 获取所有边缘节点， 消去他们的边
            // size要事先存储，否则边poll边计算size，是错误的
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int peek = queue.poll();
                List<Integer> edge = graph.get(peek);
                res.add(peek);
                // 无向图边缘节点入队
                for (Integer to : edge) {
                    degree[to]--;
                    if (degree[to] == 1) {
                        queue.add(to);
                    }
                }
            }
        }
        return res;
    }

    /**
     * TLE
     * @param n
     * @param edges
     * @return
     */
    public List<Integer> findMinHeightTrees_v0(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>(Collections.nCopies(n, null));
        for (int i = 0; i < edges.length; i++) {
            insertEdge(edges[i][0], edges[i][1], graph);
            insertEdge(edges[i][1], edges[i][0], graph);
        }
        int[] high = new int[n];
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < graph.size(); i++) {
            List<Boolean> visit = new ArrayList<>(Collections.nCopies(n, false));
            visit.set(i, true);
            high[i] = dfs(i, graph, visit);
            min = Integer.min(min, high[i]);
        }
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (high[i] == min) {
                res.add(i);
            }
        }
        return res;
    }

    @Test
    public void test1() {
        FindMinHeightTrees o = new FindMinHeightTrees();
//        System.out.println(o.findMinHeightTrees(4, new int[][]{{1, 0},{1, 2},{1, 3}}));
        System.out.println(o.findMinHeightTrees(6, new int[][]{{0, 3},{1, 3},{2, 3},{4, 3},{5, 4}}));
        System.out.println(o.findMinHeightTrees(6, new int[][]{{1, 3},{2, 1},{4, 2},{5, 1}}));
        System.out.println(o.findMinHeightTrees(2, new int[][]{{0, 1}}));
    }
}

