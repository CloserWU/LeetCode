package com.wushuai.daily;

import java.util.*;

/**
 * <p>d10_3</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-10-21 14:57
 */
public class d10_3 {

    /**
     * 10.21 925. 长按键入
     *
     * @param name
     * @param typed
     * @return
     */
    public boolean isLongPressedName(String name, String typed) {
        int i = 0, j = 0;
        while (i != name.length()) {
            char ch = name.charAt(i);
            if (j < typed.length()) {
                char chj = typed.charAt(j);
                if (ch == chj) {
                    j++;
                    i++;
                } else if (i > 0 && chj == name.charAt(i - 1)) {
                    j++;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        char ch = name.charAt(name.length() - 1);
        while (j < typed.length()) {
            if (typed.charAt(j) == ch) {
                j++;
            } else {
                return false;
            }
        }
        return true;
    }


    /**
     * 10.22 763. 划分字母区间·
     *
     * @param S
     * @return
     */
    public List<Integer> partitionLabels(String S) {
        Map<Character, Integer> map = new HashMap<>();
        char[] arr = S.toCharArray();
        for (int i = 0; i < arr.length; i++) {
            map.put(arr[i], i);
        }
        List<Integer> res = new ArrayList<>();
        int bound = map.get(arr[0]);
        int len = 0;
        for (int i = 0; i < arr.length; ) {
            while (i <= bound) {
                bound = Integer.max(bound, map.get(arr[i++]));
            }
            res.add(i - len);
            len = i;
            if (i < arr.length) {
                bound = Integer.max(bound, map.get(arr[i]));
            }
        }
        return res;
    }


    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 10.23 234. 回文链表
     *
     * @param head
     * @return
     */
    public boolean isPalindrome(ListNode head) {
        List<Integer> list = new ArrayList<>();
        while (head != null) {
            list.add(head.val);
            head = head.next;
        }
        for (int i = 0; i < list.size() / 2; i++) {
            if (!list.get(i).equals(list.get(list.size() - i - 1))) {
                return false;
            }
        }
        return true;
    }


    /**
     * 10.24 1024. 视频拼接
     *
     * @param clips
     * @param T
     * @return
     */
    public int videoStitching(int[][] clips, int T) {
        int[] maxn = new int[T];
        for (int[] clip : clips) {
            if (clip[0] < T) {
                maxn[clip[0]] = Integer.max(maxn[clip[0]], clip[1]);
            }
        }
        int pre = 0, last = 0, res = 0;
        for (int i = 0; i < T; i++) {
            last = Integer.max(last, maxn[i]);
            if (i == last) {
                return -1;
            }
            if (last == pre) {
                res++;
                pre = last;
            }
        }
        return res;
    }

    /**
     * 10.25 845. 数组中的最长山脉
     *
     * @param A
     * @return
     */
    public int longestMountain(int[] A) {
        int res = 0;
        for (int i = 0; i < A.length; i++) {
            int pivot = i, tmp = 0;
            while (pivot > 0 && A[pivot] > A[pivot - 1]) {
                tmp++;
                pivot--;
            }
            if (tmp == 0) {
                continue;
            }
            pivot = i;
            while (pivot < A.length - 1 && A[pivot] > A[pivot + 1]) {
                tmp++;
                pivot++;
            }
            if (pivot == i) {
                continue;
            }
            if (tmp >= 2) {
                res = Integer.max(res, tmp + 1);
            }
        }
        return res;
    }


    /**
     * 10.06 1365. 有多少小于当前数字的数字
     *
     * @param nums
     * @return
     */
    public int[] smallerNumbersThanCurrent(int[] nums) {
        int n = nums.length;
        int[] ret = new int[n];
        for (int i = 0; i < n; i++) {
            int cnt = 0;
            for (int num : nums) {
                if (num < nums[i]) {
                    cnt++;
                }
            }
            ret[i] = cnt;
        }
        return ret;
    }


    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }
    }


    /**
     * 10.27 144. 二叉树的前序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        Deque<TreeNode> queue = new LinkedList<>();
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        while (!queue.isEmpty() || root != null) {
            while (root != null) {
                res.add(root.val);
                queue.addFirst(root);
                root = root.left;
            }
            root = queue.pollFirst();
            if (root.right != null) {
                root = root.right;
            } else {
                root = null;
            }
        }
        return res;
    }


    /**
     * 10.28 1207. 独一无二的出现次数
     *
     * @param arr
     * @return
     */
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer, Integer> counter = new HashMap<>();
        for (int elem : arr)
            counter.put(elem, counter.getOrDefault(elem, 0) + 1);
        return counter.size() == new HashSet<Integer>(counter.values()).size();
    }


    /**
     * 10.29 129. 求根到叶子节点数字之和
     *
     * @param root
     * @return
     */
    public int sumNumbers(TreeNode root) {
        StringBuilder[] val = new StringBuilder[]{new StringBuilder()};
        int[] res = new int[]{0};
        dfs(root, val, res);
        return res[0];
    }

    void dfs(TreeNode root, StringBuilder[] val, int[] res) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            val[0].append(root.val);
            res[0] += Integer.parseInt(val[0].toString());
            val[0].setLength(val[0].length() - 1);
            return;
        }
        val[0].append(root.val);
        dfs(root.left, val, res);
        dfs(root.right, val, res);
        val[0].setLength(val[0].length() - 1);
    }


    /**
     * 10.30 463. 岛屿的周长
     *
     * @param grid
     * @return
     */
    public int islandPerimeter(int[][] grid) {
        int res = 0;
        int[] dx = new int[]{-1, 1, 0, 0};
        int[] dy = new int[]{0, 0, -1, 1};
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    int tmp = 0;
                    for (int k = 0; k < 4; k++) {
                        int newX = dx[k] + i;
                        int newY = dy[k] + j;
                        if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length) {
                            if (grid[newX][newY] == 1) {
                                tmp++;
                            }
                        }
                    }
                    res += 4 - tmp;
                }
            }
        }
        return res;
    }


    /**
     * 10.31 381. O(1) 时间插入、删除和获取随机元素 - 允许重复
     * Your RandomizedCollection object will be instantiated and called as such:
     * RandomizedCollection obj = new RandomizedCollection();
     * boolean param_1 = obj.insert(val);
     * boolean param_2 = obj.remove(val);
     * int param_3 = obj.getRandom();
     */
    static class RandomizedCollection {

        Map<Integer, Set<Integer>> idx;
        List<Integer> nums;

        /** Initialize your data structure here. */
        public RandomizedCollection() {
            idx = new HashMap<Integer, Set<Integer>>();
            nums = new ArrayList<Integer>();
        }

        /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
        public boolean insert(int val) {
            nums.add(val);
            Set<Integer> set = idx.getOrDefault(val, new HashSet<Integer>());
            set.add(nums.size() - 1);
            idx.put(val, set);
            return set.size() == 1;
        }

        /** Removes a value from the collection. Returns true if the collection contained the specified element. */
        public boolean remove(int val) {
            if (!idx.containsKey(val)) {
                return false;
            }
            Iterator<Integer> it = idx.get(val).iterator();
            int i = it.next();
            int lastNum = nums.get(nums.size() - 1);
            nums.set(i, lastNum);
            idx.get(val).remove(i);
            idx.get(lastNum).remove(nums.size() - 1);
            if (i < nums.size() - 1) {
                idx.get(lastNum).add(i);
            }
            if (idx.get(val).size() == 0) {
                idx.remove(val);
            }
            nums.remove(nums.size() - 1);
            return true;
        }

        /** Get a random element from the collection. */
        public int getRandom() {
            return nums.get((int) (Math.random() * nums.size()));
        }
    }


    public static void main(String[] args) {
        d10_3 o = new d10_3();
        RandomizedCollection collection = new RandomizedCollection();
        collection.insert(0);
        collection.remove(0);
        collection.insert(-1);
        collection.remove(0);
        collection.getRandom();


        System.out.println(o.longestMountain(new int[]{2, 3, 1}));


        System.out.println(o.partitionLabels("xxzzcd"));
        System.out.println(o.partitionLabels("ababcbacadefegdehijhklij"));
        System.out.println(o.partitionLabels("zbabcbacadefegdehijhklij"));
        System.out.println(o.partitionLabels("afvandjfhaksdhfkbglkfwkb"));
        System.out.println(o.partitionLabels("gdhfjhuqwdasdasdqwhihojo"));
    }
}
