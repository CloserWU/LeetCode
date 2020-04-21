package com.wushuai.offer;

import org.junit.Test;

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


    /**
     * 面试题20. 调整数组顺序使奇数位于偶数前面
     * @param nums
     * @return
     */
    public int[] exchange(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            if ((nums[left] & 1) != 1) {
                int tmp = nums[left];
                nums[left] = nums[right];
                nums[right] = tmp;
                right--;
            } else {
                left++;
            }
        }
        return nums;
    }

    /**
     * 面试题22. 链表中倒数第k个节点
     * @param head
     * @param k
     * @return
     */
    public ListNode getKthFromEnd(ListNode head, int k) {
        ListNode res = head;
        int i = 0;
        while (head != null) {
            i++;
            if (i >= k) {
                res = res.next;
            }
            head = head.next;
        }
        return res;
    }

    /**
     * 面试题24. 反转链表
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode pre = null;
        ListNode next = head.next;
        while (next != null) {
            head.next = pre;
            pre = head;
            head = next;
            next = next.next;
        }
        head.next = pre;
        return head;
    }

    /**
     * 面试题25. 合并两个排序的链表
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }
        ListNode root = new ListNode(-1);
        root.next = l1;
        ListNode pre = root;
        ListNode bak = root;
        while (l2 != null) {
            if (l1 == null || l1.val >= l2.val) {
                ListNode l2Next = l2.next;
                pre.next = l2;
                l2.next = l1;
                pre = l2;
                l2 = l2Next;
            } else {
                l1 = l1.next;
                pre = pre.next;
            }
        }
        return bak.next;
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

