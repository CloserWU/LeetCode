package com.wushuai.offer;

import org.junit.Test;

import java.util.*;

/**
 * <p>offer1</p>
 * <p>
 * ArrayList<String> list=new ArrayList<String>();
 * String[] strarr = new String[list.size()];
 * list.toArray(strarr);
 * //String[] strarr = list.toArray(new String[list.size()]);
 * <p>
 * String[] s = {"a","b","c"};
 * List list = java.util.Arrays.asList(s);
 * ArrayList newList = new ArrayList<>(list)
 * </p>
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
                    ps.add(pd);  // 一个新状态
                    flag = Judge(pd, idx + 1);
                    ps.remove(pd); //新状态失败，恢复原装状态
                    if (flag) {
                        return true;
                    }
                }
                /* 这样写会有错误， 要想恢原装态，则必须紧跟，初始状态，中间若有其他代码则会导致错误
                 if (!ps.contains(pd)) {
                    ps.add(pd);  // 一个新状态
                    flag = Judge(pd, idx + 1);
                  }

                  if (flag) {
                        return true;
                   }
                   ps.remove(pd);  //新状态失败，恢复原装状态
                 */
            }

            if (p.row - 1 >= 0) {
                Point pu = new Point(p.row - 1, p.col);
                if (!ps.contains(pu)) {
                    ps.add(pu);
                    flag = Judge(pu, idx + 1);
                    ps.remove(pu);
                    if (flag) {
                        return true;
                    }
                }

            }

            if (p.col - 1 >= 0) {
                Point pl = new Point(p.row, p.col - 1);
                if (!ps.contains(pl)) {
                    ps.add(pl);
                    flag = Judge(pl, idx + 1);
                    ps.remove(pl);
                    if (flag) {
                        return true;
                    }
                }
            }

            if (p.col + 1 < col) {
                Point pr = new Point(p.row, p.col + 1);
                if (!ps.contains(pr)) {
                    ps.add(pr);
                    flag = Judge(pr, idx + 1);
                    ps.remove(pr);
                    if (flag) {
                        return true;
                    }
                }
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

    /**
     * 面试题13. 机器人的运动范围
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount(int m, int n, int k) {
        if (k == 0) {
            return 1;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 9; i++) {
            map.put(i, 9);
        }
        map.put(9, 18);
        map.put(10, 28);
        map.put(11, 38);
        map.put(12, 48);
        map.put(13, 58);
        map.put(14, 68);
        map.put(15, 78);
        map.put(16, 88);
        map.put(17, 98);
        map.put(18, 107);
        map.put(19, 117);
        map.put(20, 127);
        int res = 0;
        m = Math.min(m, map.get(k) + 1);
        n = Math.min(n, map.get(k) + 1);
        int[][] board = new int[m][n];
        int tmp = map.get(k) / 10 + 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < Math.min((tmp - i / 10) * 10, n); j++) {
                board[i][j] = i / 100 + (i % 100) / 10 + (i % 100) % 10;
                board[i][j] += j / 100 + (j % 100) / 10 + (j % 100) % 10;
                if (board[i][j] <= k) {
                    res++;
                }
            }
        }
        return res;
    }

    int m, n, k;
    boolean[][] visited;

    /**
     * 面试题13. 机器人的运动范围
     * DFS
     * <p>
     * https://leetcode-cn.com/problems/ji-qi-ren-de-yun-dong-fan-wei-lcof/solution/mian-shi-ti-13-ji-qi-ren-de-yun-dong-fan-wei-dfs-b/
     *
     * @param m
     * @param n
     * @param k
     * @return
     */
    public int movingCount_v2(int m, int n, int k) {
        this.m = m;
        this.n = n;
        this.k = k;
        this.visited = new boolean[m][n];
        return dfs(0, 0, 0, 0);
    }

    public int dfs(int i, int j, int si, int sj) {
        if (i < 0 || i >= m || j < 0 || j >= n || k < si + sj || visited[i][j]) {
            return 0;
        }
        visited[i][j] = true;
        return 1
                + dfs(i + 1, j, (i + 1) % 10 != 0 ? si + 1 : si - 8, sj)
                + dfs(i, j + 1, si, (j + 1) % 10 != 0 ? sj + 1 : sj - 8);
    }

    /**
     * 面试题14- I. 剪绳子
     *
     * @param n
     * @return
     */
    public int cuttingRope(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j < i; j++) {
                dp[i] = Math.max(dp[i], dp[j] * (i - j));
                dp[i] = Math.max(dp[i], j * (i - j));
            }
        }
        return dp[n];
    }

    /**
     * 面试题14- II. 剪绳子 II
     * <p>
     * n     乘积     子数字
     * 2       1       1 1
     * 3       2       1 2
     * 4       4       2 2
     * 5       6       2 3
     * 6       9       3 3
     * 7       12      2 2 3
     * 8       18      2 3 3
     * 9       27      3 3 3
     * 10      36      2 2 3 3
     * 11      54      2 3 3 3
     * 12      81      3 3 3 3
     * 13      108     2 2 3 3 3
     * 14      162     2 3 3 3 3
     * 15      243     3 3 3 3 3
     * 16      324     2 2 3 3 3 3
     * 17      486     2 3 3 3 3 3
     * 18      729     3 3 3 3 3 3
     * 19      972     2 2 3 3 3 3 3
     * 20      1458    2 3 3 3 3 3 3
     * 21      2187    3 3 3 3 3 3 3
     * 22      2916    2 2 3 3 3 3 3 3
     * 23      4374    2 3 3 3 3 3 3 3
     * 24      6561    3 3 3 3 3 3 3 3
     * 25      8748    2 2 3 3 3 3 3 3 3
     * 26      13122   2 3 3 3 3 3 3 3 3
     * 27      19683   3 3 3 3 3 3 3 3 3
     * 28      26244   2 2 3 3 3 3 3 3 3 3
     *
     * @param n
     * @return
     */
    public int cuttingRope_v2(int n) {
        if (n == 2) {
            return 1;
        }
        if (n == 3) {
            return 2;
        }
        long res = 1;
        while (n > 4) {
            res = res * 3 % 1000000007;
            n -= 3;
        }
        return (int) (res * n % 1000000007);
    }

    /**
     * 面试题15. 二进制中1的个数
     *
     * bitCount统计整数的二进制表达式中的bit位为1的位数（汉明重量）
     * jdk1.7
     * public static int bitCount(int i) {
     *         i = i - ((i >>> 1) & 0x55555555);
     *         i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
     *         i = (i + (i >>> 4)) & 0x0f0f0f0f;
     *         i = i + (i >>> 8);
     *         i = i + (i >>> 16);
     *         return i & 0x3f;
     * }
     * @param n
     * @return
     */
    public int hammingWeight(int n) {
        return Integer.bitCount(n);
    }

    @Test
    public void test1() {
        offer1 obj = new offer1();
//        obj.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
//        obj.buildTree(new int[]{1, 2, 8, 3, 5, 7, 6, 4, 9}, new int[]{8, 3, 2, 7, 5, 6, 1, 4, 9});
//        obj.buildTree(new int[]{1, 2, 4, 8, 9, 5, 10, 3, 6, 7}, new int[]{8, 4, 9, 2, 10, 5, 1, 6, 3, 7});
//        System.out.println(obj.exist(new char[][]{{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}}, "ABCCEC"));
//        System.out.println(obj.exist(new char[][]{{'A','B'},{'C','D'}}, "ABCD"));
//        System.out.println(obj.exist(new char[][]{{'A','B'}}, "AB"));
//        System.out.println(obj.exist(new char[][]{{'A','B'}}, "BA"));
//        System.out.println(obj.exist(new char[][]{{'A','A'},{'A','A'}}, "AAAAA"));
//        System.out.println(obj.movingCount(16, 8, 4));
//        System.out.println(obj.movingCount(2, 3, 1));
//        System.out.println(obj.movingCount(3, 1, 0));
//        System.out.println(obj.movingCount(14, 8, 10));
//        System.out.println(obj.movingCount(100, 100, 10));
//        System.out.println(obj.movingCount(3, 8, 5));
//        System.out.println(Arrays.toString(obj.cuttingRope(58)));
        System.out.println(obj.hammingWeight(00000000000000000000000000001011));
    }
}


