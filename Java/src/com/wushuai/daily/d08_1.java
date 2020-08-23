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


    /**
     * 滑动窗口+哈希表
     * https://leetcode-cn.com/problems/smallest-range-covering-elements-from-k-lists/solution/zui-xiao-qu-jian-by-leetcode-solution/
     *
     * @param nums
     * @return
     */
    public int[] smallestRangeV2(List<List<Integer>> nums) {
        int size = nums.size();
        // 建立<数组元素，下标序列>哈希表
        Map<Integer, List<Integer>> indices = new HashMap<Integer, List<Integer>>();
        int xMin = Integer.MAX_VALUE, xMax = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            for (int x : nums.get(i)) {
                List<Integer> list = indices.getOrDefault(x, new ArrayList<Integer>());
                list.add(i);
                indices.put(x, list);
                // 数组元素最小值
                xMin = Math.min(xMin, x);
                // 数组元素最大值
                xMax = Math.max(xMax, x);
            }
        }
        // 下标序列出现次数
        int[] freq = new int[size];
        // 有几个下标出现了
        int inside = 0;
        // 双指针直到右指针大于最大元素值 （枚举最小元素到最大元素）
        int left = xMin, right = xMin - 1;
        int bestLeft = xMin, bestRight = xMax;
        // right移动，并更新下标出现次数和下标个数
        while (right < xMax) {
            right++;
            // 获取这个数组元素的下标序列
            if (indices.containsKey(right)) {
                // 遍历下标序列
                for (int x : indices.get(right)) {
                    freq[x]++;
                    // 若下标第一次出现，则下标个数+1
                    if (freq[x] == 1) {
                        inside++;
                    }
                }
                // nums数组中所有下标都出现过一次了，
                while (inside == size) {
                    // 更新
                    if (right - left < bestRight - bestLeft) {
                        bestLeft = left;
                        bestRight = right;
                    }
                    // 移动左指针，直到不满足(下标不是全部出现)
                    if (indices.containsKey(left)) {
                        for (int x : indices.get(left)) {
                            freq[x]--;
                            if (freq[x] == 0) {
                                inside--;
                            }
                        }
                    }
                    left++;
                }
            }
        }
        return new int[]{bestLeft, bestRight};
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


    /**
     * ==字典树==
     * https://leetcode-cn.com/problems/palindrome-pairs/solution/hui-wen-dui-by-leetcode-solution/
     */
    class Trie {
        int[] ch = new int[26];

        int flag;

        public Trie() {
            flag = -1;
        }
    }

    List<Trie> trieTree = new ArrayList<>();

    public void insertTrie(String s, int id) {
        int len = s.length(), add = 0;
        for (int i = 0; i < len; i++) {
            int x = s.charAt(i) - 'a';
            if (trieTree.get(add).ch[x] == 0) {
                trieTree.add(new Trie());
                trieTree.get(add).ch[x] = trieTree.size() - 1;
            }
            add = trieTree.get(add).ch[x];
        }
        trieTree.get(add).flag = id;
    }

    public int findWordTrie(String s, int left, int right) {
        int add = 0;
        for (int i = right; i >= left; i--) {
            int x = s.charAt(i) - 'a';
            add = trieTree.get(add).ch[x];
            if (add == 0) {
                return -1;
            }
        }
        return trieTree.get(add).flag;
    }

    public boolean isPalindrome(String s, int left, int right) {
        int len = right - left + 1;
        for (int i = 0; i < len / 2; i++) {
            if (s.charAt(left + i) != s.charAt(right - i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 8.06 336. 回文对
     * 重点：字典树 ->https://leetcode-cn.com/problems/palindrome-pairs/solution/hui-wen-dui-by-leetcode-solution/
     *
     * @param words
     * @return
     */
    public List<List<Integer>> palindromePairs(String[] words) {
        trieTree.add(new Trie());
        int n = words.length;
        for (int i = 0; i < n; i++) {
            insertTrie(words[i], i);
        }
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int m = words[i].length();
            for (int j = 0; j <= m; j++) {
                if (isPalindrome(words[i], j, m - 1)) {
                    int findLeft = findWordTrie(words[i], 0, j - 1);
                    if (findLeft != -1 && findLeft != i) {
                        res.add(Arrays.asList(i, findLeft));
                    }
                }
                if (j != 0 && isPalindrome(words[i], 0, j - 1)) {
                    int findRight = findWordTrie(words[i], j, m - 1);
                    if (findRight != -1 && findRight != i) {
                        res.add(Arrays.asList(findRight, i));
                    }
                }
            }
        }
        return res;
    }


    /**
     * 8.07 100. 相同的树
     *
     * @param p
     * @param q
     * @return
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p != null && q != null) {
            if (p.val == q.val) {
                boolean left = isSameTree(p.left, q.left);
                if (!left) {
                    return false;
                }
                return isSameTree(p.right, q.right);
            }
        }
        return p == null && q == null;
    }


    /**
     * 8.08 99. 恢复二叉搜索树
     *
     * @param root
     */
    public void recoverTree(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        TreeNode x = null, y = null, pre = null;
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            if (pre != null && root.val < pre.val) {
                y = root;
                if (x == null) {
                    x = pre;
                } else {
                    break;
                }
            }

            pre = root;
            root = root.right;
        }
        int tmp = x.val;
        x.val = y.val;
        y.val = tmp;
    }


    /**
     * 8.09 93. 复原IP地址
     *
     * @param s
     * @return
     */
    public List<String> restoreIpAddresses(String s) {
        List<String> res = new ArrayList<>();
        if (s.length() > 12) {
            return res;
        }
        for (int i = 1; i <= 3 && i < s.length() - 2; i++) {
            for (int j = i + 1; j <= i + 3 && j < s.length() - 1; j++) {
                for (int k = j + 1; k <= j + 3 && k < s.length(); k++) {
                    if (isValidIp(s, i, j, k)) {
                        StringBuilder sb = new StringBuilder();
                        sb.append(s, 0, i);
                        sb.append('.');
                        sb.append(s, i, j);
                        sb.append('.');
                        sb.append(s, j, k);
                        sb.append('.');
                        sb.append(s, k, s.length());
                        res.add(sb.toString());
                    }
                }
            }
        }
        return res;
    }

    boolean isValidIp(String s, int i, int j, int k) {
        String[] strs = new String[4];
        strs[0] = s.substring(0, i);
        strs[1] = s.substring(i, j);
        strs[2] = s.substring(j, k);
        strs[3] = s.substring(k);
        for (String str : strs) {
            if (str.length() > 1 && str.charAt(0) == '0') {
                return false;
            }
            int t = Integer.parseInt(str);
            if (t > 255 || t < 0) {
                return false;
            }
        }
        return true;
    }


    /**
     * 8.10 696. 计数二进制子串
     *
     * @param s
     * @return
     */
    public int countBinarySubstrings(String s) {
        int res = 0, pre = 0, h = 0;
        int tmp = 0;
        for (int i = 0; i < s.length(); ) {
            while (i < s.length() && s.charAt(i) - '0' == tmp) {
                h++;
                i++;
            }
            tmp = 1 - tmp;
            res += Integer.min(pre, h);
            pre = h;
            h = 0;
        }
        return res;
    }


    @Test
    public void test() {
        d08_1 o = new d08_1();
        System.out.println(countBinarySubstrings("00110011"));
        System.out.println(countBinarySubstrings("10101"));
        System.out.println(restoreIpAddresses("121015123"));
    }
}
