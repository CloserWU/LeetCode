package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d06_3</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-22 11:24
 */
public class d06_3 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    /**
     * 6.21 124. 二叉树中的最大路径和
     *
     * @param root
     * @return
     */
    public int maxPathSum(TreeNode root) {
        int[] res = new int[1];
        res[0] = Integer.MIN_VALUE;
        maxPathSum(root, res);
        return res[0];
    }

    int maxPathSum(TreeNode root, int[] res) {
        if (root == null) {
            return 0;
        }
        // 递归计算左右子节点的最大贡献值
        // 只有在最大贡献值大于 0 时，才会选取对应子节点
        int left = Math.max(maxPathSum(root.left, res), 0);
        int right = Math.max(maxPathSum(root.right, res), 0);
        // 节点的最大路径和取决于该节点的值与该节点的左右子节点的最大贡献值
        int max = root.val + left + right;
        // 更新答案
        res[0] = Math.max(res[0], max);
        // 返回节点的最大贡献值
        return root.val + Math.max(left, right);
    }


    /**
     * 6.22 面试题 16.18. 模式匹配
     * https://leetcode-cn.com/problems/pattern-matching-lcci/solution/mo-shi-pi-pei-by-leetcode-solution/
     *
     * @param pattern
     * @param value
     * @return
     */
    public boolean patternMatching(String pattern, String value) {
        int count_a = 0, count_b = 0;
        for (char ch : pattern.toCharArray()) {
            if (ch == 'a') {
                ++count_a;
            } else {
                ++count_b;
            }
        }
        if (count_a < count_b) {
            int temp = count_a;
            count_a = count_b;
            count_b = temp;
            char[] array = pattern.toCharArray();
            for (int i = 0; i < array.length; i++) {
                array[i] = array[i] == 'a' ? 'b' : 'a';
            }
            pattern = new String(array);
        }
        if (value.length() == 0) {
            return count_b == 0;
        }
        if (pattern.length() == 0) {
            return false;
        }
        for (int len_a = 0; count_a * len_a <= value.length(); ++len_a) {
            int rest = value.length() - count_a * len_a;
            if ((count_b == 0 && rest == 0) || (count_b != 0 && rest % count_b == 0)) {
                int len_b = (count_b == 0 ? 0 : rest / count_b);
                int pos = 0;
                boolean correct = true;
                String value_a = "", value_b = "";
                for (char ch : pattern.toCharArray()) {
                    if (ch == 'a') {
                        String sub = value.substring(pos, pos + len_a);
                        if (value_a.length() == 0) {
                            value_a = sub;
                        } else if (!value_a.equals(sub)) {
                            correct = false;
                            break;
                        }
                        pos += len_a;
                    } else {
                        String sub = value.substring(pos, pos + len_b);
                        if (value_b.length() == 0) {
                            value_b = sub;
                        } else if (!value_b.equals(sub)) {
                            correct = false;
                            break;
                        }
                        pos += len_b;
                    }
                }
                if (correct && !value_a.equals(value_b)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 6.23 67. 二进制求和
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        StringBuilder sb1 = new StringBuilder(a).reverse();
        StringBuilder sb2 = new StringBuilder(b).reverse();
        StringBuilder res = new StringBuilder();
        int add = 0;
        int i = 0, j = 0;
        while (i < sb1.length() && j < sb2.length()) {
            int x = sb1.charAt(i) - '0' + sb2.charAt(j) - '0' + add;
            add = 0;
            if (x > 1) {
                add = 1;
                x -= 2;
            }
            res.append(x);
            i++;
            j++;
        }
        while (i < sb1.length()) {
            int x = sb1.charAt(i++) - '0' + add;
            add = 0;
            if (x > 1) {
                add = 1;
                x -= 2;
            }
            res.append(x);
        }
        while (j < sb2.length()) {
            int x = sb2.charAt(j++) - '0' + add;
            add = 0;
            if (x > 1) {
                add = 1;
                x -= 2;
            }
            res.append(x);
        }
        if (add != 0) {
            res.append(1);
            add = 0;
        }
        return res.reverse().toString();
    }


    /**
     * 6.24 16. 最接近的三数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {
        int res = Integer.MAX_VALUE;
        int ans = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int add = nums[i] + nums[left] + nums[right];
                if (Math.abs(target - add) < res) {
                    ans = add;
                    res = Math.abs(target - add);
                }
                if (add > target) {
                    right--;
                } else if (add < target) {
                    left++;
                } else {
                    return target;
                }
            }
        }
        return ans;
    }


    class Trie {
        boolean flag;
        Trie[] next;

        Trie() {
            flag = false;
            next = new Trie[26];
        }
    }

    void insert(String s, Trie root) {
        for (char ch : s.toCharArray()) {
            int x = ch - 'a';
            if (root.next[x] == null) {
                root.next[x] = new Trie();
            }
            root = root.next[x];
        }
        root.flag = true;
    }

    boolean find(String s, Trie root) {
        for (char ch : s.toCharArray()) {
            int x = ch - 'a';
            if (root.next[x] == null) {
                return false;
            }
            root = root.next[x];
        }
        return root.flag;
    }

    Trie trie = new Trie();

    /**
     * 6.25 139. 单词拆分
     * TTL
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak(String s, List<String> wordDict) {
        if (wordDict.size() == 0) {
            return "".equals(s);
        }
        Trie root = trie;
        for (String str : wordDict) {
            insert(str, root);
            root = trie;
        }
        return dfs(s, 0);
    }

    boolean dfs(String s, int idx) {
        Trie root = trie;
        for (int i = idx; i < s.length(); i++) {
            if (root == null) {
                return false;
            }
            int x = s.charAt(i) - 'a';
            if (root.flag) {
                boolean f = dfs(s, i);
                if (f) {
                    return true;
                }
            }
            root = root.next[x];
        }
        if (root == null) {
            return false;
        }
        return root.flag;
    }


    /**
     * 6.25 139. 单词拆分
     * dp
     * https://leetcode-cn.com/problems/word-break/solution/dan-ci-chai-fen-by-leetcode-solution/
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreakV2(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet<>(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 6.26 面试题 02.01. 移除重复节点
     *
     * @param head
     * @return
     */
    public ListNode removeDuplicateNodes(ListNode head) {
        Set<Integer> set = new HashSet<>();
        ListNode root = new ListNode(-1);
        ListNode tmp = root;
        root.next = head;
        while (head != null) {
            if (set.contains(head.val)) {
                root.next = head.next;
                head = head.next;
            } else {
                set.add(head.val);
                head = head.next;
                root = root.next;
            }
        }
        return tmp.next;
    }


    /**
     * 6.27 41. 缺失的第一个正数
     * 自定义哈希
     * https://leetcode-cn.com/problems/first-missing-positive/solution/que-shi-de-di-yi-ge-zheng-shu-by-leetcode-solution/
     *
     * @param nums
     * @return
     */
    public int firstMissingPositive(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0) {
                nums[i] = n + 1;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            int num = Math.abs(nums[i]);
            if (num <= n) {
                nums[num - 1] = -Math.abs(nums[num - 1]);
            }
        }
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                return i + 1;
            }
        }
        return n + 1;
    }


    /**
     * 6.28 209. 长度最小的子数组
     * 滑动窗口，注意边界值
     *
     * @param s
     * @param nums
     * @return
     */
    public int minSubArrayLen(int s, int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        int left = 0, right = 0;
        int cnt = nums[0], res = Integer.MAX_VALUE;
        do {
            while (cnt < s && right != nums.length - 1) {
                cnt += nums[++right];
            }
            while (cnt >= s && left <= right) {
                res = Integer.min(res, right - left + 1);
                cnt -= nums[left++];
            }
        } while (right != nums.length - 1);
        return res == Integer.MAX_VALUE ? 0 : res;
    }


    /**
     * 6.29 215. 数组中的第K个最大元素
     * 快排二分改造
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        return quickSelect(nums, 0, nums.length - 1, nums.length - k);
    }

    public int quickSelect(int[] a, int l, int r, int index) {
        int q = partition(a, l, r);
        if (q == index) {
            return a[q];
        } else {
            return q < index ?
                    quickSelect(a, q + 1, r, index) :
                    quickSelect(a, l, q - 1, index);
        }
    }

    int partition(int[] a, int l, int r) {
        int x = a[l];
        while (l < r) {
            while (l < r && x <= a[r]) {
                r--;
            }
            a[l] = a[r];
            while (l < r && x >= a[l]) {
                l++;
            }
            a[r] = a[l];
        }
        a[l] = x;
        return l;
    }

    /**
     * 6.30 剑指 Offer 09. 用两个栈实现队列
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


    @Test
    public void test() {
        d06_3 o = new d06_3();
        System.out.println(findKthLargest(new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6}, 2));
        System.out.println(minSubArrayLen(7, new int[]{7}));
        System.out.println(minSubArrayLen(7, new int[]{7, 7}));
        System.out.println(minSubArrayLen(7, new int[]{2, 3, 1, 2, 4, 3}));
        System.out.println(wordBreak("leetcode", new ArrayList<>(Arrays.asList("leet", "code"))));
        System.out.println(wordBreak("applepenapple", new ArrayList<>(Arrays.asList("apple", "pen"))));
        System.out.println(wordBreak("catsandog", new ArrayList<>(Arrays.asList("cats", "dog", "sand", "and", "cat"))));
        System.out.println(threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
    }
}
