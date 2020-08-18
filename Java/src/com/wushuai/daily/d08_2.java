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


    /**
     * 8.13 43. 字符串相乘
     *
     * @param num1
     * @param num2
     * @return
     */
    public String multiply(String num1, String num2) {
        int add = 0;
        String res = "0";
        if ("0".equals(num1) || "0".equals(num2)) {
            return res;
        }
        for (int i = num1.length() - 1; i >= 0; i--) {
            StringBuilder sb = new StringBuilder();
            for (int j = i; j < num1.length() - 1; j++) {
                sb.append(0);
            }
            for (int j = num2.length() - 1; j >= 0; j--) {
                int k = (num1.charAt(i) - '0') * (num2.charAt(j) - '0') + add;
                sb.append(k % 10);
                add = k / 10;
            }
            while (add != 0) {
                sb.append(add % 10);
                add /= 10;
            }
            // sb逆序
            res = addString(res, sb.toString());
        }
        return new StringBuilder(res).reverse().toString();
    }

    String addString(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int add = 0;
        int i = 0, j = 0;
        for (; i < num1.length() && j < num2.length(); i++, j++) {
            int x = (num1.charAt(i) - '0') + (num2.charAt(j) - '0') + add;
            sb.append(x % 10);
            add = x / 10;
        }
        while (i < num1.length()) {
            int x = (num1.charAt(i++) - '0') + add;
            sb.append(x % 10);
            add = x / 10;
        }
        while (j < num2.length()) {
            int x = (num2.charAt(j++) - '0') + add;
            sb.append(x % 10);
            add = x / 10;
        }
        while (add != 0) {
            sb.append(add % 10);
            add /= 10;
        }
        return sb.toString();
    }


    /**
     * 8.14 20. 有效的括号
     *
     * @param s
     * @return
     */
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (char ch : s.toCharArray()) {
            if (ch == '[' || ch == '(' || ch == '{') {
                stack.push(ch);
            } else {
                if (stack.isEmpty()) {
                    return false;
                }
                if (ch == ')' && stack.peek() != '(') {
                    return false;
                }
                if (ch == ']' && stack.peek() != '[') {
                    return false;
                }
                if (ch == '}' && stack.peek() != '{') {
                    return false;
                }
                stack.pop();
            }
        }
        return stack.isEmpty();
    }


    /**
     * 8.15 546. 移除盒子
     * too hard  copy
     * https://leetcode-cn.com/problems/remove-boxes/solution/yi-chu-he-zi-by-leetcode-solution/
     *
     * @param boxes
     * @return
     */
    public int removeBoxes(int[] boxes) {
        int[][][] dp = new int[100][100][100];
        return calculatePoints(boxes, dp, 0, boxes.length - 1, 0);
    }

    public int calculatePoints(int[] boxes, int[][][] dp, int l, int r, int k) {
        if (l > r) {
            return 0;
        }
        if (dp[l][r][k] != 0) {
            return dp[l][r][k];
        }
        while (r > l && boxes[r] == boxes[r - 1]) {
            r--;
            k++;
        }
        dp[l][r][k] = calculatePoints(boxes, dp, l, r - 1, 0) + (k + 1) * (k + 1);
        for (int i = l; i < r; i++) {
            if (boxes[i] == boxes[r]) {
                dp[l][r][k] = Math.max(dp[l][r][k], calculatePoints(boxes, dp, l, i, k + 1) + calculatePoints(boxes, dp, i + 1, r - 1, 0));
            }
        }
        return dp[l][r][k];
    }


    /**
     * 8.16 733. 图像渲染
     *
     * @param image
     * @param sr
     * @param sc
     * @param newColor
     * @return
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        int color = image[sr][sc];
        if (color == newColor) {
            return image;
        }
        int row = image.length;
        int col = image[0].length;
        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{sr, sc});
        image[sr][sc] = newColor;
        while (!queue.isEmpty()) {
            int[] poll = queue.poll();
            sr = poll[0];
            sc = poll[1];
            for (int i = 0; i < 4; i++) {
                int newSr = sr + dx[i];
                int newSc = sc + dy[i];
                if (newSr >= 0 && newSr < row && newSc >= 0 && newSc < col) {
                    if (image[newSr][newSc] == color) {
                        queue.add(new int[]{newSr, newSc});
                        image[newSr][newSc] = newColor;
                    }
                }
            }
        }
        return image;
    }
    
    
    /**
     * 8.17 110. 平衡二叉树
     */
    public boolean isBalanced(TreeNode root) {
        return helper(root) != -1;
    }
    
    int helper(TreeNode root) {
        if (root != null) {
            int left = helper(root.left);
            if (left == -1) {
                return -1;
            }
            int right = helper(root.right);
            if (right == -1) {
                return -1;
            }
            if (Math.abs(right - left) <= 1) {
                return 1 + Math.max(right, left);
            }
            return -1;
        }
        return 0;
    }
    
    
    
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }
    
    /**
     * 8.18 109. 有序链表转换二叉搜索树
     */
    public TreeNode sortedListToBST(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        return recursive(0, list.size() - 1, list);
    }

    TreeNode recursive(int left, int right, List<Integer> list) {
        if (left > right) {
            return null;
        } else if (left == right) {
            return new TreeNode(list.get(left));
        } else {
            int mid = (left + right) >>> 1;
            TreeNode root = new TreeNode(list.get(mid));
            root.left = recursive(left, mid - 1, list);
            root.right = recursive(mid + 1, right, list);
            return root;
        }
    }

    @Test
    public void test() {
        d08_2 o = new d08_2();
        System.out.println(floodFill(new int[][]{{0, 0, 0}, {0, 1, 1}}, 1, 1, 1));
        System.out.println(multiply("0", "456"));
        System.out.println(multiply("1", "456"));
        System.out.println(multiply("1", "1"));
        System.out.println(multiply("123", "456"));
        System.out.println(multiply("2", "3"));
        solve(new char[][]{{'X', 'X', 'X', 'X'}, {'X', 'O', 'O', 'X'}, {'X', 'X', 'O', 'X'}, {'X', 'O', 'X', 'X'}});
    }
}
