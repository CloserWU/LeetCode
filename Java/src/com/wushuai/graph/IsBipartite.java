package com.wushuai.graph;

import org.junit.Test;

import java.util.LinkedList;
import java.util.Queue;

/**
 * <p>IsBipartite</p>
 * <p>
 *     DFS BFS 时要考虑联通分量
 *     785
 * </p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-17 11:14
 */
public class IsBipartite {

    boolean bfs(int[][] graph, int idx, int[] color) {
        Queue<Integer> queue = new LinkedList<>();
        color[idx] = 1;
        queue.add(idx);
        while (!queue.isEmpty()) {
            int preNode = queue.poll();
            int preColor = color[preNode];
            int[] edges = graph[preNode];
            for (int i = 0; i < edges.length; i++) {
                if (color[graph[preNode][i]] != preColor) {
                    if (color[graph[preNode][i]] == 0) {
                        color[graph[preNode][i]] = -preColor;
                        queue.add(graph[preNode][i]);
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * 785. 判断二分图
     * <p>
     * BFS + 贪心
     * 将图分为两种颜色，两种颜色相互间隔开，即为二分图
     * 每次bfs都将未访问到的点置为与上次相反的颜色
     *
     * @param graph
     * @return
     */
    public boolean isBipartite(int[][] graph) {
        // 0 未访问  1 蓝色  -1 红色
        int[] color = new int[graph.length];
        for (int i = 0; i < color.length; i++) {
            // 考虑连通分量
            if (color[i] == 0) {
                boolean b = bfs(graph, i, color);
                if (!b) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void test() {
        IsBipartite o = new IsBipartite();
        System.out.println(o.isBipartite(new int[][]{{1, 3}, {0, 2}, {1, 3}, {0, 2}}));
        System.out.println(o.isBipartite(new int[][]{{1, 2, 3}, {0, 2}, {0, 1, 3}, {0, 2}}));
        System.out.println(o.isBipartite(new int[][]{{2, 4}, {2, 3}, {1, 4, 0}, {1, 4}, {2, 3, 0}}));
        System.out.println(o.isBipartite(new int[][]{{2}, {2, 3}, {1, 0, 4}, {1, 4}, {2, 3}}));
    }
}
