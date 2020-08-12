package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d08_2</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-11 8:50
 */
public class d08_2 {

    /**
     * 8.11 130. 被围绕的区域
     *
     * @param board
     */
    public void solve(char[][] board) {
        int row = board.length;
        if (board.length == 0) {
            return;
        }
        int col = board[0].length;
        boolean[][] flag = new boolean[row][col];
        for (boolean[] b : flag) {
            Arrays.fill(b, false);
        }
        Queue<int[]> queue = new LinkedList<>();
        for (int i = 0; i < row; i++) {
            if (board[i][0] == 'O' && !flag[i][0]) {
                queue.add(new int[]{i, 0});
                flag[i][0] = true;
            }
            if (board[i][col - 1] == 'O' && !flag[i][col - 1]) {
                queue.add(new int[]{i, col - 1});
                flag[i][col - 1] = true;
            }
        }
        for (int i = 0; i < col; i++) {
            if (board[0][i] == 'O' && !flag[0][i]) {
                queue.add(new int[]{0, i});
                flag[0][i] = true;
            }
            if (board[row - 1][i] == 'O' && !flag[row - 1][i]) {
                queue.add(new int[]{row - 1, i});
                flag[row - 1][i] = true;
            }
        }
        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};
        while (!queue.isEmpty()) {
            int[] point = queue.poll();
            int x = point[0], y = point[1];
            for (int i = 0; i < 4; i++) {
                int x_ = x + dx[i];
                int y_ = y + dy[i];
                if (x_ >= 0 && x_ < row && y_ >= 0 && y_ < col && board[x_][y_] == 'O' && !flag[x_][y_]) {
                    queue.add(new int[]{x_, y_});
                    flag[x_][y_] = true;
                }
            }
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] == 'O' && !flag[i][j]) {
                    board[i][j] = 'X';
                }
            }
        }
    }


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
     * 8.12 133. 克隆图
     *
     * @param node
     * @return
     */
    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        Node res = null;
        // bfs
        Queue<Node> queue = new LinkedList<>();
        // 记录值到克隆节点的映射
        Map<Integer, Node> map = new HashMap<>();
        // 保证每个节点入队列一次
        Set<Integer> set = new HashSet<>();
        queue.add(node);
        set.add(node.val);
        while (!queue.isEmpty()) {
            Node root = queue.poll();
            // 每次新建节点时，都先从map中试着获取，若没有，则放入map
            // 实现节点于节点的交叉引用
            Node clone = new Node(root.val);
            if (map.containsKey(root.val)) {
                clone = map.get(root.val);
            }
            map.put(root.val, clone);
            List<Node> neighbors = root.neighbors;
            for (Node n : neighbors) {
                Node tmp = new Node(n.val);
                if (map.containsKey(n.val)) {
                    tmp = map.get(n.val);
                }
                map.put(n.val, tmp);
                clone.neighbors.add(tmp);
                if (!set.contains(n.val)) {
                    // bfs
                    queue.add(n);
                    set.add(n.val);
                }
            }
            if (root.val == 1) {
                res = clone;
            }
        }
        return res;
    }

    @Test
    public void test() {
        d08_2 o = new d08_2();
        solve(new char[][]{{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'X', 'X'}});
    }
}
