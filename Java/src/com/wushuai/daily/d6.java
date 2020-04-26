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
        for (ListNode node: lists) {
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

    @Test
    public void test() {
        d6 o = new d6();

    }
}
