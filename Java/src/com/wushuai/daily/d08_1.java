package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d08_1</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-02 21:24
 */
public class d08_1 {

    class Node {
        int val;
        int idx;

        public Node(int val, int idx) {
            this.idx = idx;
            this.val = val;
        }
    }

    /**
     * 记录窗口范围内，每种元素出现了几次，
     * 全部大于0为满足条件窗口，有一个小于等于0，为不满足窗口
     *
     * @param map
     * @return
     */
    boolean judgeMap(Map<Integer, Integer> map) {
        for (Map.Entry<Integer, Integer> e : map.entrySet()) {
            if (e.getValue() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 8.01 632. 最小区间
     * TTL ，并且无法处理重复元素
     *
     * @param nums
     * @return
     */
    public int[] smallestRange(List<List<Integer>> nums) {
        List<Node> list = new ArrayList<>();
        int k = nums.size();
        // 把所有数组归为一个，并记录每个元素属于第几个数组，用数据结构Node来存储
        for (int i = 0; i < nums.size(); i++) {
            List<Integer> l = nums.get(i);
            for (Integer j : l) {
                list.add(new Node(j, i));
            }
        }
        // 排序
        list.sort((o1, o2) -> {
            return Integer.compare(o1.val, o2.val);
        });
        // 滑动窗口，并建立一个hashmap，记录窗口范围内，每种元素出现了几次
        int left = 0, right = 0;
        Map<Integer, Integer> map = new HashMap<>(k);
        // 窗口初始化
        for (int i = 0; i < k; i++) {
            map.put(i, 0);
        }
        // 结果集
        List<int[]> res = new ArrayList<>();
        // 先右指针滑动，直到窗口满足条件，再滑动左指针，直到窗口不满足条件，加入一次结果集
        for (; right < list.size(); right++) {
            Node node = list.get(right);
            map.put(node.idx, map.getOrDefault(node.idx, 0) + 1);
//            set.add(node.idx);
            if (judgeMap(map)) {
                // move left
                while (judgeMap(map) && left <= right) {
                    Node node1 = list.get(left);
                    map.put(node1.idx, map.getOrDefault(node1.idx, 0) - 1);
//                    set.remove(node1.idx);
                    left++;
                }
                res.add(new int[]{list.get(left - 1).val, list.get(right).val});
            }
        }
        for (int[] in : res) {
            for (int i : in) {
                System.out.print(i);
            }
            System.out.println();
        }
        // 结果集排序
        res.sort((o1, o2) -> {
            return Integer.compare(o1[1] - o1[0], o2[1] - o2[0]);
        });
        return res.get(0);
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /**
     * 8.02 114. 二叉树展开为链表
     * 后续遍历
     * 递归主体：
     * 对于每一个节点，都默认，它的左节点是一个链表，右节点也是一个链表，
     * 这时需要将左边的链表头插到有节点链表。
     * 根据递归主体，想得到需要自底向上递归二叉树，所以后序递归。
     *
     * @param root
     */
    public void flatten(TreeNode root) {
        if (root != null) {
            flatten(root.left);
            flatten(root.right);
            if (root.left != null) {
                TreeNode node = root.left;
                // 找到左链表尾部
                while (node.right != null) {
                    node = node.right;
                }
                // 插入链表
                node.right = root.right;
                root.right = root.left;
                root.left = null;
            }
        }
    }


    /**
     * 8.03 415. 字符串相加
     *
     * @param num1
     * @param num2
     * @return
     */
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        StringBuilder n1 = new StringBuilder(num1).reverse();
        StringBuilder n2 = new StringBuilder(num2).reverse();
        char[] a = new char[Math.abs(n1.length() - n2.length())];
        Arrays.fill(a, '0');
        if (n1.length() < n2.length()) {
            n1.append(a);
        } else if (n1.length() > n2.length()) {
            n2.append(a);
        }
        char[] chars1 = n1.toString().toCharArray();
        char[] chars2 = n2.toString().toCharArray();
        int add = 0;
        for (int i = 0; i < chars1.length; i++) {
            int tmp = chars1[i] + chars2[i] - 2 * '0' + add;
            add = 0;
            if (tmp >= 10) {
                tmp -= 10;
                add = 1;
            }
            sb.append(tmp);
        }
        if (add == 1) {
            sb.append(1);
        }
        return sb.reverse().toString();
    }


    /**
     * 8.04 207. 课程表
     *
     * @param numCourses
     * @param prerequisites
     * @return
     */
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        List<List<Integer>> graph = new ArrayList<>(Collections.nCopies(numCourses, null));
        int[] inDegree = new int[numCourses];
        for (int[] edge : prerequisites) {
            int from = edge[1];
            int to = edge[0];
            List<Integer> edges = graph.get(from);
            if (edges == null) {
                edges = new ArrayList<>();
            }
            edges.add(to);
            inDegree[to]++;
            graph.set(from, edges);
        }
        int num = 0;
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        while (!queue.isEmpty()) {
            num++;
            Integer poll = queue.poll();
            List<Integer> edges = graph.get(poll);
            if (edges == null) {
                continue;
            }
            for (Integer to : edges) {
                inDegree[to]--;
                if (inDegree[to] == 0) {
                    queue.add(to);
                }
            }
        }
        return num == numCourses;
    }

    /**
     * 8.05 337. 打家劫舍 III
     *
     * @param root
     * @return
     */
    public int rob(TreeNode root) {
        int[] res = robInternal(root);
        return Integer.max(res[0], res[1]);
    }

    /**
     * res[0]代表选择当前节点情况下，其节点为根的子树，最大值是多少
     * res[1]代表不选择当前节点情况下，其节点为根的子树，最大值是多少
     *
     * @param root
     * @return
     */
    int[] robInternal(TreeNode root) {
        if (root == null) {
            return new int[2];
        }
        int[] left = robInternal(root.left);
        int[] right = robInternal(root.right);
        int[] res = new int[2];
        res[0] = root.val + left[1] + right[1];
        res[1] = Integer.max(left[0], left[1]) + Integer.max(right[0], right[1]);
        return res;
    }


    @Test
    public void test() {
        d08_1 o = new d08_1();

    }
}
