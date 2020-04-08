package com.wushuai.graph;

import org.junit.Test;

import java.util.*;

/**
 * <p>CloneGraph</p>
 * <p>133</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-08 09:29
 */
public class CloneGraph {

    class Node {
        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            neighbors = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _neighbors) {
            val = _val;
            neighbors = _neighbors;
        }
    }


    /**
     * 生成邻接表 map<key, List<Node>> == Node
     *
     * @param from
     * @param to
     * @param map
     */
    void build(int from, int to, Map<Integer, List<Node>> map) {
        if (map.containsKey(from)) {
            List<Node> nodes = map.get(from);
            boolean flag = true;
            for (Node e : nodes) {
                if (e.val == to) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                nodes.add(new Node(to));
            }
            map.put(from, nodes);
        } else {
            List<Node> nodes = new ArrayList<>();
            nodes.add(new Node(to));
            map.put(from, nodes);
        }
    }

    /**
     * 133. 克隆图
     *
     * @param node
     * @return
     */
    public Node cloneGraph(Node node) {
        Queue<Node> queue = new LinkedList<>();
        List<Integer> visit = new ArrayList<>();
        if (node == null) {
            return null;
        }
        Node root = new Node(node.val);
        Map<Integer, List<Node>> map = new HashMap<>();
        map.put(root.val, new ArrayList<>());
        queue.add(node);
        visit.add(node.val);
        // BFS 遍历 生成无向图 邻接表 map存存储
        while (!queue.isEmpty()) {
            Node poll = queue.poll();
            List<Node> neighbors = poll.neighbors;
            for (int i = 0; i < neighbors.size(); i++) {
                // map为引用传递，build中不用返回
                build(poll.val, neighbors.get(i).val, map);
                build(neighbors.get(i).val, poll.val, map);
                if (!visit.contains(neighbors.get(i).val)) {
                    queue.add(neighbors.get(i));
                    visit.add(neighbors.get(i).val);
                }
            }
        }
        // 由邻接表构造克隆图
        for (Map.Entry<Integer, List<Node>> e : map.entrySet()) {
            System.out.print(e.getKey());
            List<Node> nodes = e.getValue();
            for (int i = 0; i < nodes.size(); i++) {
                nodes.get(i).neighbors = map.get(nodes.get(i).val);
                System.out.print(" --> " + nodes.get(i).val);
            }
            System.out.println();
        }
        root.neighbors = map.get(root.val);
        return root;
    }

    /*
    public Node cloneGraph(Node node) {
        Map<Node, Node> lookup = new HashMap<>();
        return dfs(node, lookup);
    }

    private Node dfs(Node node, Map<Node,Node> lookup) {
        if (node == null) return null;
        if (lookup.containsKey(node)) return lookup.get(node);
        Node clone = new Node(node.val, new ArrayList<>());
        lookup.put(node, clone);
        for (Node n : node.neighbors)clone.neighbors.add(dfs(n,lookup));
        return clone;
    }
     */

    @Test
    public void test1() {
        CloneGraph o = new CloneGraph();
        Node root1 = new Node(1);
        Node root2 = new Node(2);
        Node root3 = new Node(3);
        Node root4 = new Node(4);
        List<Node> n1 = new ArrayList<>();
        n1.add(root2);
        n1.add(root4);
        root1.neighbors = n1;

        List<Node> n2 = new ArrayList<>();
        n2.add(root1);
        n2.add(root3);
        // 引用值， n1中的roo2也会被修改
        root2.neighbors = n2;

        List<Node> n3 = new ArrayList<>();
        n3.add(root2);
        n3.add(root4);
        root3.neighbors = n3;

        List<Node> n4 = new ArrayList<>();
        n4.add(root1);
        n4.add(root3);
        root4.neighbors = n4;

        System.out.println(o.cloneGraph(root1));
    }
}

