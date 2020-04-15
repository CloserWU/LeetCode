package com.wushuai.graph;

import org.junit.Test;

import java.util.*;

/**
 * <p>NetworkDelayTime</p>
 * <p>743</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-15 09:59
 */
public class NetworkDelayTime {

    class Node {
        int val;
        int key;

        public Node(int val, int key) {
            this.val = val;
            this.key = key;
        }
    }

    void insertEdge(Map<Integer, List<Node>> graph, int from, int to, int val) {
        if (graph.containsKey(from)) {
            List<Node> nodes = graph.get(from);
            for (int i = 0; i < nodes.size(); i++) {
                if (nodes.get(i).key == to) {
                    nodes.get(i).val = val;
                    break;
                }
            }
            nodes.add(new Node(val, to));
        } else {
            List<Node> edges = new ArrayList<>();
            edges.add(new Node(val, to));
            graph.put(from, edges);
        }
    }

    void dijkstra(int[] dist, int from, Map<Integer, List<Node>> graph) {
        Queue<Node> queue = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                return Integer.compare(o1.val, o2.val);
            }
        });
        dist[from] = 0;
        queue.add(new Node(0, from));
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            if (!graph.containsKey(poll.key)) {
                continue;
            }
            List<Node> edges = graph.get(poll.key);
            for (int i = 0; i < edges.size(); i++) {
                int source = poll.key;
                int end = edges.get(i).key;
                int val = edges.get(i).val;
                if (dist[end] > dist[source] + val) {
                    dist[end] = dist[source] + val;
                    queue.add(new Node(dist[end], end));
                }
            }
        }
    }

    /**
     * 743. 网络延迟时间
     *
     * @param times
     * @param N
     * @param K
     * @return
     */
    public int networkDelayTime(int[][] times, int N, int K) {
        Map<Integer, List<Node>> graph = new HashMap<>();
        int INF = 100000;
        for (int i = 0; i < times.length; i++) {
            int from = times[i][0];
            int to = times[i][1];
            int val = times[i][2];
            insertEdge(graph, from, to, val);
        }
        int[] dist = new int[N + 1];
        Arrays.fill(dist, INF);
        dijkstra(dist, K, graph);
        int sum = 0;
        for (int i = 1; i < dist.length; i++) {
            if (dist[i] >= INF) {
                return -1;
            }
            sum = Integer.max(sum, dist[i]);
        }
        return sum;
    }

    @Test
    public void test1() {
        NetworkDelayTime o = new NetworkDelayTime();
        System.out.println(o.networkDelayTime(new int[][]{{2, 1, 1}, {2, 3, 1}, {3, 4, 1}}, 4, 2));
        System.out.println(o.networkDelayTime(new int[][]{{4, 3, 10}, {3, 2, 8}, {2, 1, 8}, {1, 4, 7}, {5, 1, 5}}, 5, 5));
    }
}

