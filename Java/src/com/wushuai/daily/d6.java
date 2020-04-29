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

    /**
     * 4.28
     * 面试题56 - I. 数组中数字出现的次数
     *
     * @param nums
     * @return
     */
    public int[] singleNumbers(int[] nums) {
        // 找到 两个出现次数2为1的ab的异或和a^b，全员异或即可
        int ret = 0;
        for (int num : nums) {
            ret ^= num;
        }
        // 然后找到二进制ret中最低位1的所在位，用&与操作
        int mask = 1;
        while ((ret & mask) == 0) {
            mask <<= 1;
        }
        // nums每个数都与mask异或，就能将nums分为两组
        // 且两个相同的数必定能分到同一组，
        // 两个不同的数必定能分到不同组 -> 因为这一位是1，表示a和b在这一位上一个1一个0，1和0 &与0的结果是不同的，能分为两组
        int a = 0, b = 0;
        for (int num : nums) {
            if ((num & mask) == 0) {
                a ^= num;
            } else {
                b ^= num;
            }
        }
        return new int[]{a, b};
    }

    interface MountainArray {
        public int get(int index);

        public int length();
    }

    /**
     * 4.29
     * 1095. 山脉数组中查找目标值
     *
     * @param target
     * @param mountainArr
     * @return
     */
    public int findInMountainArray(int target, MountainArray mountainArr) {
        int peek = findPeek(mountainArr);
        System.out.println(peek);
        int res = binarySearch(0, peek, mountainArr, target);
        if (res != -1) {
            return res;
        }
        return binarySearchReverse(peek, mountainArr.length() - 1, mountainArr, target);

    }

    int findPeek(MountainArray mountainArr) {
        int left = 0, right = mountainArr.length() - 1;
        while (left < right) {
            int mid = (left + right) >>> 1;
            int midNum = mountainArr.get(mid);
            int rightNum = mountainArr.get(mid + 1);
            if (midNum < rightNum) {
                left = mid + 1;
            } else {
                right = mid  -1;
            }
        }
        return left;
    }

    int binarySearch(int left, int right, MountainArray mountainArr, int target) {
        while (left <= right) {
            int mid = (left + right) >>> 1;
            int midNum = mountainArr.get(mid);
            if (midNum > target) {
                right = mid - 1;
            } else if (midNum < target) {
                left = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    int binarySearchReverse(int left, int right, MountainArray mountainArr, int target) {
        while (left <= right) {
            int mid = (left + right) >>> 1;
            int midNum = mountainArr.get(mid);
            if (midNum > target) {
                left = mid + 1;
            } else if (midNum < target) {
                right = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
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
        System.out.println(o.search(new int[]{2, 3, 4, 5, 1}, 4));
        System.out.println(o.search(new int[]{2, 3, 4, 5, 1}, 5));
        System.out.println(o.search(new int[]{2, 3, 4, 5, 1}, 1));
        System.out.println(o.search(new int[]{5, 6, 7, 0, 1, 2, 3}, 6));
        System.out.println(o.search(new int[]{5, 6, 7, 0, 1, 2, 3}, 5));
        System.out.println(o.search(new int[]{5, 6, 7, 0, 1, 2, 3}, 3));
        System.out.println(o.search(new int[]{5, 6, 7, 0, 1, 2, 3}, 0));
        System.out.println(o.search(new int[]{5, 6, 7, 0, 1, 2, 3}, 2));
    }
}
