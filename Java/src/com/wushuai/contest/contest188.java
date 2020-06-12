package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest188</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-06-12 9:02
 */
public class contest188 {


    /**
     * 1441. 用栈操作构建数组
     *
     * @param target
     * @param n
     * @return
     */
    public List<String> buildArray(int[] target, int n) {
        List<String> res = new ArrayList<>();
        int idx = 0, cur = 1;
        while (idx != target.length) {
            int cnt = target[idx];
            while (cnt != cur) {
                res.add("Push");
                res.add("Pop");
                cur++;
            }
            res.add("Push");
            idx++;
            cur++;
        }
        return res;
    }


    /**
     * 1442. 形成两个异或相等数组的三元组数目
     *
     * @param arr
     * @return
     */
    public int countTriplets(int[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                for (int k = j; k < arr.length; k++) {
                    int a = arr[i], b = arr[j];
                    for (int l = i + 2; l < j; l++) {
                        a ^= arr[l];
                    }
                    for (int l = j + 1; l <= k; l++) {
                        b ^= arr[l];
                    }
                    if (a == b) {
                        res++;
                    }
                }
            }

        }
        return res;
    }


    /**
     * 观察到a = arr[i] ^ arr[i + 1] ^ ... ^ arr[j - 1],b = arr[j] ^ arr[j + 1] ^ ... ^ arr[k],
     * 那么根据位运算的规则， a^b=arr[i]^ arr[i + 1] ^ ... ^ arr[k]，即i到k。
     * 此外，根据位运算，如果a^b==0,那么a==b，这是逆否命题，即反推也成立。
     *
     * @param arr
     * @return
     */
    public int countTripletsV2(int[] arr) {
        int res = 0;
        for (int i = 0; i < arr.length - 1; i++) {
            int tmp = arr[i];
            for (int k = i + 1; k < arr.length; k++) {
                tmp ^= arr[k];
                if (tmp == 0) {
                    res += k - i;
                }
            }
        }
        return res;
    }


    void dijkstra(List<List<Integer>> graph, int from, int[] dist) {
        Arrays.fill(dist, 1000000);
        dist[from] = 0;
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(from);
        while (!queue.isEmpty()) {
            from = queue.poll();
            List<Integer> edge = graph.get(from);
            for (Integer to : edge) {
                if (dist[to] > dist[from] + 1) {
                    dist[to] = dist[from] + 1;
                    queue.add(to);
                }
            }
        }
    }

    void insertEdge(int from, int to, List<List<Integer>> graph) {
        List<Integer> ed = graph.get(from);
        if (ed == null) {
            ed = new ArrayList<>();
        }
        ed.add(to);
        graph.set(from, ed);
    }


    /**
     * 1443. 收集树上所有苹果的最少时间
     *
     * @param n
     * @param edges
     * @param hasApple
     * @return
     */
    public int minTime(int n, int[][] edges, List<Boolean> hasApple) {
        int[] dist = new int[n];
        List<List<Integer>> graph = new ArrayList<>(Collections.nCopies(n, null));
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            insertEdge(from, to, graph);
            insertEdge(to, from, graph);
        }
        dijkstra(graph, 0, dist);
        int res = 0;
        for (int i = 0; i < hasApple.size(); i++) {
            if (hasApple.get(i)) {
                res += dist[i] * 2;
            }
        }
        return res;
    }

    /**
     *
     * @param n
     * @param edges
     * @param hasApple
     * @return
     */
    public int minTimeV2(int n, int[][] edges, List<Boolean> hasApple) {
        int res = 0;
        for (int i = edges.length - 1; i >= 0; i--) {
            if (hasApple.get(edges[i][1])) {
                hasApple.set(edges[i][0], true);
            }
        }
        for (int[] edge : edges) {
            if (hasApple.get(edge[0]) && hasApple.get(edge[1])) {
                res += 2;
            }
        }
        return res;
    }

    @Test
    public void test() {
        contest188 o = new contest188();
        System.out.println(o.countTriplets(new int[]{}));
        System.out.println(o.minTimeV2(7, new int[][]{{0, 1}, {0, 2}, {1, 4}, {1, 5}, {2, 3}, {2, 6}}, Arrays.asList(false, false, true, false, true, true, false)));
        System.out.println(o.minTimeV2(7, new int[][]{{0, 1}, {0, 2}, {1, 4}, {1, 5}, {2, 3}, {2, 6}}, Arrays.asList(false, false, true, false, false, true, false)));
    }
}
