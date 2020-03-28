package com.wushuai.offer;

import org.junit.Test;

import java.util.*;

/**
 * <p>offer1</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-26 20:49
 */
public class offer1 {
    /**
     * 面试题03. 数组中重复的数字
     *
     * @param nums
     * @return
     */
    public int findRepeatNumber(int[] nums) {
        int[] t = new int[nums.length + 1];
        for (int i = 0; i < nums.length; i++) {
            t[nums[i]]++;
            if (t[nums[i]] >= 2) {
                return nums[i];
            }
        }
        return 0;
    }

    /**
     * 面试题04. 二维数组中的查找
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean findNumberIn2DArray(int[][] matrix, int target) {
        int row = matrix.length;
        int col = matrix[0].length;
        if (col == 0) {
            return false;
        }
        for (int i = 0; i < row; i++) {
            if (target < matrix[i][0]) {
                return false;
            }
            for (int j = 0; j < col; j++) {
                if (target == matrix[i][j]) {
                    return true;
                } else if (target < matrix[i][j]) {
                    break;
                }
            }
        }
        return false;
    }

    /**
     * 面试题05. 替换空格
     *
     * @param s
     * @return
     */
    public String replaceSpace(String s) {
        return s.replaceAll(" ", "%20"); // slow
        /*StringBuilder sb = new StringBuilder(); // fast
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == ' ') {
                sb.append("%20");
            } else {
                sb.append(chars[i]);
            }
        }
        return sb.toString();*/
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 面试题06. 从尾到头打印链表
     * <p>
     * todo list to array
     *
     * @param head
     * @return
     */
    public int[] reversePrint(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(0, head.val);
            head = head.next;
        }
        int[] res = new int[list.size()];
   /*     Integer[] integers = list.toArray(new Integer[0]);
        Integer[] array = new Integer[list.size()];
        list.toArray(array); // fill the array*/
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    int[] preOrder = null;
    int[] inOrder = null;
    int idx = 0;


    TreeNode build(int begin, int end) {
        //若下标不合法，则直接返回nul。这是递归的出口，很重要，不然树会建成每个节点都有俩孩子，错误
        if (begin > end) {
            return null;
        }
        //preorder遍历
        int target = preOrder[idx++];
        TreeNode root = new TreeNode(target);
        //叶子节点直接返回，非叶子节点递归求左右子树
        if (begin < end) {
            for (int i = begin; i <= end; i++) {
                if (target == inOrder[i]) {
                    root.left = build(begin, i - 1);
                    root.right = build(i + 1, end);

                    break;
                }
            }
        }
        return root;
    }

    /**
     * 面试题07. 重建二叉树
     * 依次遍历preorder，并记录相应元素值target
     * 对每个值都在inorder中查找，并将inorder划分为两部分，相应递归建立左右子树
     * 若某个部分只有一个元素，则不继续查找下一个target，当作叶子节点返回
     *
     * @param preorder
     * @param inorder
     * @return
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        idx = 0;
        preOrder = preorder;
        inOrder = inorder;
        TreeNode root = build(0, preorder.length - 1);
        return root;
    }

    /**
     * 面试题09. 用两个栈实现队列
     * <p>
     * Your CQueue object will be instantiated and called as such:
     * CQueue obj = new CQueue();
     * obj.appendTail(value);
     * int param_2 = obj.deleteHead();
     */
    class CQueue {
        Stack<Integer> in = null;
        Stack<Integer> out = null;

        public CQueue() {
            in = new Stack<>();
            out = new Stack<>();
        }

        public void appendTail(int value) {
            in.push(value);
        }

        public int deleteHead() {
            if (out.empty()) {
                while (!in.empty()) {
                    out.push(in.pop());
                }
            }
            if (out.empty()) {
                return -1;
            } else {
                return out.pop();
            }
        }
    }

    /**
     * 面试题10- I. 斐波那契数列
     *
     * @param n
     * @return
     */
    public int fib(int n) {
        if (n == 0 || n == 1) {
            return n;
        }
        int f_n = 0;
        int f_n_1 = 1;
        int f_n_2 = 0;
        for (int i = 2; i <= n; i++) {
            f_n = (f_n_1 + f_n_2) % 1000000007;
            f_n_2 = f_n_1;
            f_n_1 = f_n;
        }
        return f_n;
    }

    /**
     * 面试题10- II. 青蛙跳台阶问题
     *
     * @param n
     * @return
     */
    public int numWays(int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1 || n == 2) {
            return n;
        }
        int f_n = 0;
        int f_n_1 = 2;
        int f_n_2 = 1;
        for (int i = 3; i <= n; i++) {
            f_n = (f_n_1 + f_n_2) % 1000000007;
            f_n_2 = f_n_1;
            f_n_1 = f_n;
        }
        return f_n;
    }

    /**
     * 面试题11. 旋转数组的最小数字
     *
     * @param numbers
     * @return
     */
    public int minArray(int[] numbers) {
        int l = 0, r = numbers.length - 1;
        while (l < r) {
            int mid = (r - l) / 2 + l;
            if (numbers[mid] < numbers[r]) {
                r = mid;
            } else if (numbers[mid] > numbers[r]) {
                l = mid + 1;
            } else {
                r--;
            }
        }
        return numbers[l];
    }

    class Point {
        int row;
        int col;

        Point(int r, int c) {
            row = r;
            col = c;
        }

        @Override
        public boolean equals(Object obj) {
            Point p1 = (Point) obj;
            return p1.col == col && p1.row == row;
        }
    }

    List<Point> ps = new ArrayList<>();
    String w;
    char[][] b;
    int row = 0;
    int col = 0;

    boolean Judge(Point p, int idx) {
        if (b[p.row][p.col] == w.charAt(idx)) {
            if (idx == w.length() - 1) {
                return true;
            }
            boolean flag = false;
            if (p.row + 1 < row) {
                Point pd = new Point(p.row + 1, p.col);
                if (!ps.contains(pd)) {
                    ps.add(pd);
                    flag = Judge(pd, idx + 1);
                }
                if (flag) {
                    return true;
                }
                ps.remove(pd);
            }

            if (p.row - 1 >= 0) {
                Point pu = new Point(p.row - 1, p.col);
                if (!ps.contains(pu)) {
                    ps.add(pu);
                    flag = Judge(pu, idx + 1);
                }
                if (flag) {
                    return true;
                }
                ps.remove(pu);
            }

            if (p.col - 1 >= 0) {
                Point pl = new Point(p.row, p.col - 1);
                if (!ps.contains(pl)) {
                    ps.add(pl);
                    flag = Judge(pl, idx + 1);
                }
                if (flag) {
                    return true;
                }
                ps.remove(pl);
            }

            if (p.col + 1 < col) {
                Point pr = new Point(p.row, p.col + 1);
                if (!ps.contains(pr)) {
                    ps.add(pr);
                    flag = Judge(pr, idx + 1);
                }
                if (flag) {
                    return true;
                }
                ps.remove(pr);
            }
        }
        return false;
    }

    /**
     * 面试题12. 矩阵中的路径
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        row = board.length;
        col = board[0].length;
        w = word;
        b = board;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (word.charAt(0) == board[i][j]) {
                    ps.clear();
                    ps.add(new Point(i, j));
                    if (Judge(new Point(i, j), 0)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Test
    public void test1() {
        offer1 obj = new offer1();
//        obj.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
//        obj.buildTree(new int[]{1, 2, 8, 3, 5, 7, 6, 4, 9}, new int[]{8, 3, 2, 7, 5, 6, 1, 4, 9});
//        obj.buildTree(new int[]{1, 2, 4, 8, 9, 5, 10, 3, 6, 7}, new int[]{8, 4, 9, 2, 10, 5, 1, 6, 3, 7});
        System.out.println(obj.exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}, "ABCCEC"));
        System.out.println(obj.exist(new char[][]{{'A','B'},{'C','D'}}, "ABCD"));
        System.out.println(obj.exist(new char[][]{{'A','B'}}, "AB"));
        System.out.println(obj.exist(new char[][]{{'A','B'}}, "BA"));
        System.out.println(obj.exist(new char[][]{{'A','A'},{'A','A'}}, "AAAAAA"));
    }
}

