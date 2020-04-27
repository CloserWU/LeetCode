package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d6</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-26 8:50
 */
public class d6 {

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 4.26
     * 23. 合并K个排序链表
     *
     * @param lists K个排序链表
     * @return 合并K个排序链表
     */
    public ListNode mergeKLists(ListNode[] lists) {
        Queue<ListNode> queue = new PriorityQueue<>(new Comparator<ListNode>() {
            @Override
            public int compare(ListNode o1, ListNode o2) {
                return Integer.compare(o1.val, o2.val);
            }
        });
        ListNode root = new ListNode(-1);
        ListNode head = root;
        for (ListNode node : lists) {
            if (node != null) {
                queue.add(node);
            }
        }
        while (!queue.isEmpty()) {
            ListNode node = queue.poll();
            root.next = node;
            root = root.next;
            if (node.next != null) {
                queue.add(node.next);
            }
        }
        return head.next;
    }

    /**
     * 4.27
     * 33. 搜索旋转排序数组
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = (left + right) >>> 1;
            if ((target <= nums[mid]) ^ (target >= nums[left]) ^ (nums[mid] < nums[left])) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left == right && nums[left] == target ? left : -1;
    }

    @Test
    public void test() {
        d6 o = new d6();
        System.out.println(o.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 6));
        System.out.println(o.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 5));
        System.out.println(o.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 4));
        System.out.println(o.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 1));
        System.out.println(o.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 0));
        System.out.println(o.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 2));
        System.out.println(o.search(new int[]{4, 5, 6, 7, 0, 1, 2}, 7));
        System.out.println(o.search(new int[]{0, 1, 2, 3, 4, 5, 6, 7}, 0));
        System.out.println(o.search(new int[]{0, 1, 2, 3, 4, 5, 6, 7}, 8));
        System.out.println(o.search(new int[]{0, 1, 2, 3, 4, 5, 6, 7}, 6));
        System.out.println(o.search(new int[]{2,3,4,5,1}, 4));
        System.out.println(o.search(new int[]{2,3,4,5,1}, 5));
        System.out.println(o.search(new int[]{2,3,4,5,1}, 1));
        System.out.println(o.search(new int[]{5, 6, 7, 0, 1, 2, 3}, 6));
        System.out.println(o.search(new int[]{5, 6, 7, 0, 1, 2, 3}, 5));
        System.out.println(o.search(new int[]{5, 6, 7, 0, 1, 2, 3}, 3));
        System.out.println(o.search(new int[]{5, 6, 7, 0, 1, 2, 3}, 0));
        System.out.println(o.search(new int[]{5, 6, 7, 0, 1, 2, 3}, 2));
    }
}
