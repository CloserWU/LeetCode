package com.wushuai.daily;

import java.util.*;

/**
 * <p>d10_2</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-10-11 18:21
 */
public class d10_2 {


    /**
     * 10.11 416. 分割等和子集
     * dp[i][j] 表示从数组的 [0,i] 下标范围内选取若干个正整数（可以是 0 个），
     * 是否存在一种选取方案使得被选取的正整数的和等于 j
     * https://leetcode-cn.com/problems/partition-equal-subset-sum/solution/fen-ge-deng-he-zi-ji-by-leetcode-solution/
     *
     * @param nums
     * @return
     */
    public boolean canPartition(int[] nums) {
        if (nums.length < 2) {
            return false;
        }
        int maxNum = Integer.MIN_VALUE;
        int sum = 0;
        for (int num : nums) {
            maxNum = Integer.max(maxNum, num);
            sum += num;
        }
        if (sum % 2 == 1) {
            return false;
        }
        int target = sum / 2;
        if (maxNum > target) {
            return false;
        }
        boolean[][] dp = new boolean[nums.length][target + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = true;
        }
        dp[0][nums[0]] = true;
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (j >= nums[i]) {
                    dp[i][j] = dp[i - 1][j] | dp[i - 1][j - nums[i]];
                } else {
                    dp[i][j] = dp[i - 1][j];
                }
            }
        }
        return dp[nums.length - 1][target];
    }


    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * 10.12 530. 二叉搜索树的最小绝对差
     *
     * @param root
     * @return
     */
    public int getMinimumDifference(TreeNode root) {
        int pre = -1, ans = Integer.MAX_VALUE;
        int[] n = new int[]{-1, Integer.MAX_VALUE};
        inOrder(root, n);
        return n[1];
    }

    void inOrder(TreeNode root, int[] n) {
        if (root == null) {
            return;
        }
        inOrder(root.left, n);
        if (n[0] != -1) {
            n[1] = Integer.min(n[1], root.val - n[0]);
        }
        n[0] = root.val;
        inOrder(root.right, n);
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }
    }


    /**
     * 10.13 24. 两两交换链表中的节点
     *
     * @param head
     * @return
     */
    public ListNode swapPairs(ListNode head) {
        ListNode tmp = new ListNode();
        ListNode root = tmp;
        tmp.next = head;
        if (head == null || head.next == null) {
            return head;
        }
        ListNode next = head.next;
        while (next != null) {
            head.next = next.next;
            next.next = head;
            tmp.next = next;
            tmp = head;
            head = tmp.next;
            if (head.next == null) {
                return root.next;
            }
            next = head.next;
        }
        return root.next;
    }


    /**
     * 10.14 1002. 查找常用字符
     *
     * @param A
     * @return
     */
    public List<String> commonChars(String[] A) {
        List<Map<Character, Integer>> mapList = new ArrayList<>();
        for (String str : A) {
            Map<Character, Integer> map = new HashMap<>();
            for (char ch : str.toCharArray()) {
                map.put(ch, map.getOrDefault(ch, 0) + 1);
            }
            mapList.add(map);
        }
        List<String> list = new ArrayList<>();
        Map<Character, Integer> map = mapList.get(0);
        for (Map.Entry<Character, Integer> e : map.entrySet()) {
            boolean flag = true;
            int num = e.getValue();
            for (int i = 1; i < mapList.size(); i++) {
                Map<Character, Integer> map1 = mapList.get(i);
                if (!(map1.containsKey(e.getKey()))) {
                    flag = false;
                    break;
                }
                num = Integer.min(num, map1.get(e.getKey()));
            }
            if (flag) {
                for (int i = 0; i < num; i++) {
                    list.add(String.valueOf(e.getKey()));
                }
            }
        }
        return list;
    }

    static class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }

    ;


    /**
     * 10.15 116. 填充每个节点的下一个右侧节点指针
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        connect(root.left, root.right);
        return root;
    }

    void connect(Node left, Node right) {
        if (left != null && right != null) {
            left.next = right;
            connect(left.left, left.right);
            connect(right.left, right.right);
            connect(left.right, right.left);
        }
    }


    /**
     * 10.16 977. 有序数组的平方
     *
     * @param A
     * @return
     */
    public int[] sortedSquares(int[] A) {
        for (int i = 0; i < A.length; i++) {
            A[i] = (int) Math.pow(A[i], 2);
        }
        Arrays.sort(A);
        return A;
    }


    /**
     * 10.17 52. N皇后 II
     *
     * @param n
     * @return
     */
    public int totalNQueens(int n) {
        boolean[][] board = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            board[0][i] = true;
            dfs(board, 1);
            board[0][i] = false;
        }
        return res;
    }

    int res = 0;

    void dfs(boolean[][] board, int row) {
        if (row == board.length) {
            res++;
            return;
        }
        for (int i = 0; i < board.length; i++) {
            board[row][i] = true;
            if (check(board, row, i)) {
                dfs(board, row + 1);
            }
            board[row][i] = false;
        }
    }

    boolean check(boolean[][] board, int row, int col) {
        for (int i = 0; i < board.length; i++) {
            if (i != row && board[i][col]) {
                return false;
            }
            if (i != col && board[row][i]) {
                return false;
            }
        }
        int bakR = row, bakC = col;
        row = bakR - 1;
        col = bakC - 1;
        while (row >= 0 && col >= 0) {
            if (board[row--][col--]) {
                return false;
            }
        }
        row = bakR - 1;
        col = bakC + 1;
        while (row >= 0 && col < board.length) {
            if (board[row--][col++]) {
                return false;
            }
        }
        row = bakR + 1;
        col = bakC + 1;
        while (row < board.length && col < board.length) {
            if (board[row++][col++]) {
                return false;
            }
        }
        row = bakR + 1;
        col = bakC - 1;
        while (row < board.length && col >= 0) {
            if (board[row++][col--]) {
                return false;
            }
        }
        return true;
    }


    /**
     * 10.18 19. 删除链表的倒数第N个节点
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode tmp = new ListNode(-1);
        tmp.next = head;
        ListNode root = tmp;
        for (int i = 0; i < n; i++) {
            head = head.next;
        }
        while (head != null) {
            root = root.next;
            head = head.next;
        }
        root.next = root.next.next;
        return tmp.next;
    }


    /**
     * 10.19 844. 比较含退格的字符串
     *
     * @param S
     * @param T
     * @return
     */
    public boolean backspaceCompare(String S, String T) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        for (char ch : S.toCharArray()) {
            if (ch == '#') {
                if (sb1.length() > 0) {
                    sb1.deleteCharAt(sb1.length() - 1);
                }
            } else {
                sb1.append(ch);
            }
        }
        for (char ch : T.toCharArray()) {
            if (ch == '#') {
                if (sb2.length() > 0) {
                    sb2.deleteCharAt(sb2.length() - 1);
                }
            } else {
                sb2.append(ch);
            }
        }
        return sb1.toString().equals(sb2.toString());
    }


    /**
     * 10.20 143. 重排链表
     *
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null || head.next == null) {
            return;
        }
        List<ListNode> list = new ArrayList<>();
        int len = 0;
        ListNode tmp = head;
        while (tmp != null) {
            tmp = tmp.next;
            len++;
        }
        tmp = head;
        for (int i = 0; i < len; i++) {
            if (i >= (len -  len / 2)) {
                list.add(tmp);
            }
            tmp = tmp.next;
        }
        tmp = head;
        for (int i = 0; i < len / 2; i++) {
            ListNode node = list.get(list.size() - i - 1);
            node.next = null;
            ListNode next = tmp.next;
            if (node == next) {
                continue;
            }
            tmp.next = node;
            node.next = next;
            tmp = next;
        }
    }

}
