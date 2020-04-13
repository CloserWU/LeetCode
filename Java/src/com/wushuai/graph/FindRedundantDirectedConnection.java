package com.wushuai.graph;

import org.junit.Test;

import java.util.*;

/**
 * <p>FindRedundantDirectedConnection</p>
 * <p>685</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-12 11:43
 */
public class FindRedundantDirectedConnection {

    void insertEdge(int from, int to, List<List<Integer>> graph) {
        List<Integer> edge = graph.get(from);
        if (edge == null) {
            edge = new ArrayList<>();
        }
        if (edge.contains((Object) to)) {
            return;
        }
        edge.add(to);
        graph.set(from, edge);
    }

    Boolean judgeLoop(int[] degree, List<List<Integer>> graph) {
        List<Integer> visit = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        int nodeNum = 0;
        for (int i = 0; i < degree.length; i++) {
            if (degree[i] >= 2) {
                return true;
            }
            if (degree[i] == 0) {
                queue.add(i);
            }
            if (degree[i] != -1) {
                nodeNum++;
            }
        }
        if (queue.isEmpty() && nodeNum > 1) {
            return true;
        }
        while (!queue.isEmpty()) {
            int from = queue.poll();
            if (visit.contains((Object) from)) {
                return true;
            }
            visit.add(from);
            List<Integer> edge = graph.get(from);
            if (edge == null) {
                continue;
            }
            for (Integer anEdge : edge) {
                degree[anEdge]--;
                if (degree[anEdge] == 0) {
                    queue.add(anEdge);
                }
            }
        }
        return false;
    }

    /**
     * 685. 冗余连接 II
     * <p>
     * 边插入边判断，此方法有缺陷。本题的环路不是有向环路，而是无向环路
     *
     * @param edges
     * @return
     */
    public int[] findRedundantDirectedConnection(int[][] edges) {
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> graph = new ArrayList<>(Collections.nCopies(edges.length + 1, null));
        int[] degree = new int[edges.length + 1];
        Arrays.fill(degree, -1);
        graph.set(0, new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];
            insertEdge(from, to, graph);
            if (degree[from] == -1) {
                degree[from] = 0;
            }
            if (degree[to] == -1) {
                degree[to] = 0;
            }
            degree[to]++;
            int[] copyDegree = Arrays.copyOf(degree, degree.length);
            if (judgeLoop(copyDegree, graph)) {
                res.clear();
                res.add(from);
                res.add(to);
                int[] ress = new int[res.size()];
                for (int j = 0; j < res.size(); j++) {
                    ress[j] = res.get(j);
                }
                return ress;
            }
        }
        return new int[]{};
    }


    int findRoot(int[] rp, int target) {
        while (rp[target] != target) {
            target = rp[target];
        }
        return target;
    }

    /**
     * 685. 冗余连接 II
     * <p>
     * 所有边插入完毕后，在进行判断
     * 若所有点的入度都为1，则一定有环，建立并查集查询
     * 若有个节点入度为2，则有两个指向它的节点，先删除位于数组靠后的，若不能连通，则答案是另一条边
     *
     * @param edges
     * @return
     */
    public int[] findRedundantDirectedConnection_v2(int[][] edges) {
        int[] degree = new int[edges.length + 1];
        int flag = -1;
        for (int i = 0; i < edges.length; i++) {
            int to = edges[i][1];
            degree[to]++;
            if (degree[to] == 2) {
                flag = to;
                break;
            }
        }
        if (flag == -1) {
            // 必有首尾相接的环
            int[] rp = new int[edges.length + 1];
            for (int i = 0; i < rp.length; i++) {
                rp[i] = i;
            }
            int r1 = 0, r0 = 0;
            // 每条边相连的节点都并为一棵树，更新并查集
            // 到某一条边，若两个节点已经在树中(只会有一棵树) 即rp[r1] = rp[r0];说明他们两个之前已经被访问过，即为答案
            for (int[] edge : edges) {
                r0 = findRoot(rp, edge[0]);
                r1 = findRoot(rp, edge[1]);
                if (r0 == r1) {
                    return edge;
                }
                else {
                    rp[r1] = rp[r0];
                }
            }
            return new int[0];
        } else {
            // 初始化并查集
            int previous = -1, behind = -1;
            int[] rp = new int[edges.length + 1];
            for (int i = 0; i < rp.length; i++) {
                rp[i] = i;
            }
            // 找到指向入度为2的节点的两条边
            for (int i = 0; i < rp.length; i++) {
                if (edges[i][1] == flag) {
                    if (previous == -1) {
                        previous = i;
                    } else {
                        behind = i;
                        break;
                    }
                }
            }
            // 一张图只能有一个生成树，当遇到后一个边时，跳过，计算其他接待你的根，
            // 若结果图有两个树根，说明不连通，即答案时第一条边
            int r0 = 0, r1 = 0, countRoot = 0;
            for (int i = 0; i < edges.length; i++) {
                if (i == behind) {
                    continue;
                }
                r0 = findRoot(rp, edges[i][0]);
                r1 = findRoot(rp, edges[i][1]);
                rp[r1] = rp[r0];
            }
            // 计算根数
            for (int i = 1; i < rp.length; i++) {
                if (rp[i] == i) {
                    countRoot++;
                }
            }
            if (countRoot == 2) {
                return edges[previous];
            } else {
                return edges[behind];
            }
        }

    }


    @Test
    public void test1() {
        FindRedundantDirectedConnection o = new FindRedundantDirectedConnection();
        System.out.println(Arrays.toString(o.findRedundantDirectedConnection_v2(new int[][]{{1, 2}, {1, 3}, {2, 3}})));
        System.out.println(Arrays.toString(o.findRedundantDirectedConnection_v2(new int[][]{{1, 2}, {2, 3}, {3, 1}})));
        System.out.println(Arrays.toString(o.findRedundantDirectedConnection_v2(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}})));
        System.out.println(Arrays.toString(o.findRedundantDirectedConnection_v2(new int[][]{{1, 2}, {2, 3}, {3, 4}, {2, 4}})));
        System.out.println(Arrays.toString(o.findRedundantDirectedConnection_v2(new int[][]{{2, 1}, {2, 3}, {3, 4}, {2, 4}})));
    }
}

