package com.wushuai.offer;

import org.junit.Test;

import java.text.DecimalFormat;

/**
 * <p>offer2</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-30 11:15
 */
public class offer2 {

    /**
     * 面试题16. 数值的整数次方
     * <p>
     * 快速幂遇到指数为负数时，先让底数取倒数
     *
     * @param x
     * @param n
     * @return
     */
    public double myPow(double x, int n) {
        boolean f = false;
        long N = n;
        if (N < 0) {
            N = -N;
            x = 1.0 / x;
        }
        double res = 1.0;
        while (N != 0) {
            if (N % 2 == 1) {
                res *= x;
            }
            N /= 2;
            x *= x;
        }
        return res;
    }

    /**
     * 面试题17. 打印从1到最大的n位数
     *
     * @param n
     * @return
     */
    public int[] printNumbers(int n) {
        int[] res = new int[(int) Math.pow(10, n)];
        for (int i = 0; i < res.length; i++) {
            res[i] = i + 1;
        }
        return res;
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 面试题18. 删除链表的节点
     *
     * @param head
     * @param val
     * @return
     */
    public ListNode deleteNode(ListNode head, int val) {
        ListNode root = new ListNode(-1);
        root.next = head;
        ListNode tmp = root;
        while (head != null) {
            if (head.val == val) {
                root.next = head.next;
                break;
            }
            root = head;
            head = head.next;
        }
        return tmp.next;
    }

    /**
     * 面试题20. 表示数值的字符串
     * + 1或多
     * ? 0或1
     * * 0或多
     * . 任意字符
     *
     * @param s
     * @return
     */
    public boolean isNumber(String s) {
        return s.matches("^(\\+|-)?(([0-9]*\\.[0-9]+)|([0-9]+\\.[0-9]*)|[0-9]+)(e(\\+|-)?[0-9]+)?$");
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    boolean Judge(TreeNode A, TreeNode B) {
        if (B == null && A == null) {
            return true;
        }
        if (B != null && A == null) {
            return false;
        }
        if (B == null) {
            return true;
        }
        if (A.val == B.val) {
            return Judge(A.left, B.left) && Judge(A.right, B.right);
        }
        return false;
    }

    /**
     * 面试题26. 树的子结构
     *
     * @param A
     * @param B
     * @return
     */
    public boolean isSubStructure(TreeNode A, TreeNode B) {
        if (B == null && A == null) {
            return false;
        }
        if (B == null || A == null) {
            return false;
        }
        if (A.val == B.val) {
            boolean f = Judge(A.left, B.left) && Judge(A.right, B.right);
            if (f) {
                return true;
            }
        }
        return isSubStructure(A.left, B) || isSubStructure(A.right, B);
    }

    @Test
    public void test1() {
        offer2 o = new offer2();
        double d = 0.;
//        System.out.println(o.myPow(2.00000, -2147483648));
        System.out.println(o.isNumber("2."));
        System.out.println(o.isNumber(".2"));
        System.out.println(o.isNumber("."));
        System.out.println(o.isNumber("-1E-16"));
        System.out.println(o.isNumber("12e"));
        System.out.println(o.isNumber("e9"));
        System.out.println(o.isNumber("+-5"));
    }

}

