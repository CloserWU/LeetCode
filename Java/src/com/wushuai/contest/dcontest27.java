package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>dcontest27</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-06-16 16:22
 */
public class dcontest27 {


    /**
     * 1460. 通过翻转子数组使两个数组相等
     *
     * @param target
     * @param arr
     * @return
     */
    public boolean canBeEqual(int[] target, int[] arr) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : target) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        for (int i : arr) {
            map.put(i, map.getOrDefault(i, 0) - 1);
        }
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() != 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * 1461. 检查一个字符串是否包含所有长度为 K 的二进制子串
     *
     * @param s
     * @param k
     * @return
     */
    public boolean hasAllCodes(String s, int k) {
        Set<String> set = new HashSet<>();
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder(s);
        for (int i = 0; i <= chars.length - k; i++) {
            set.add(sb.substring(i, i + k));
        }
        return set.size() * 1.0 == Math.pow(2, k);
    }


    void insertEdge(List<List<Integer>> graph, int from, int to) {
        List<Integer> edge = graph.get(from);
        if (edge == null) {
            edge = new ArrayList<>();
        }
        edge.add(to);
        graph.set(from, edge);
    }

    boolean find(List<List<Integer>> graph, int from, int to) {
        List<Integer> edge = graph.get(from);
        if (edge == null) {
            return false;
        }
        for (Integer tos : edge) {
            if (tos.equals(to)) {
                return true;
            }
            boolean b = find(graph, tos, to);
            if (b) {
                return true;
            }
        }
        return false;
    }


    /**
     * 1462. 课程安排 IV
     * 每次都find，会有重复查询。比如 1 -> 2 -> 3 -> 4. 查过 1 -> 2，再查 1 -> 4时还是从1开始，实际可以从2开始
     * 用set记录从根到其他节点的键值对，然后直接查表即可
     *
     * @param n
     * @param prerequisites
     * @param queries
     * @return
     */
    public List<Boolean> checkIfPrerequisite(int n, int[][] prerequisites, int[][] queries) {
        List<List<Integer>> graph = new ArrayList<>(Collections.nCopies(n, null));
        List<Boolean> res = new ArrayList<>();
        for (int[] edge : prerequisites) {
            int from = edge[0];
            int to = edge[1];
            insertEdge(graph, from, to);
        }
        /* TTL
        for (int[] edge: queries) {
            int from  = edge[0];
            int to = edge[1];
            res.add(find(graph, from, to));
        }
        */

        Set<String> set = new HashSet<>();
        for (int i = 0; i < n; i++) {
            bfs(graph, i, n, set);
        }

        for (int[] val : queries) {
            res.add(set.contains(val[0] + "_" + val[1]));
        }
        return res;
    }

    void bfs(List<List<Integer>> graph, int from, int n, Set<String> set) {
        if (graph.get(from) == null) {
            return;
        }
        Queue<Integer> queue = new LinkedList<>();
        queue.add(from);
        boolean[] visit = new boolean[n];
        visit[from] = true;
        while (!queue.isEmpty()) {
            Integer fr = queue.poll();
            List<Integer> edge = graph.get(fr);
            if (edge == null) {
                continue;
            }
            for (Integer to : edge) {
                if (!visit[to]) {
                    visit[to] = true;
                    set.add(from + "_" + to);
                    queue.add(to);
                }
            }
        }
    }

    @Test
    public void test() {
        dcontest27 o = new dcontest27();
        System.out.println(o.hasAllCodes("00110110", 2));
        System.out.println(o.hasAllCodes("00110", 2));
        System.out.println(o.hasAllCodes("0110", 1));
        System.out.println(o.hasAllCodes("0110", 2));
        System.out.println(o.hasAllCodes("0000000001011100", 4));
    }
}
