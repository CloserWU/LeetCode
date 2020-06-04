package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d12</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-05-19 9:52
 */
public class d12 {

    /**
     * 5.06
     * 983. 最低票价
     * https://leetcode-cn.com/problems/minimum-cost-for-tickets/solution/zui-di-piao-jie-by-leetcode-solution/
     * <p>
     * 对于一年中的任意一天：
     * 如果这一天不是必须出行的日期，那我们可以贪心地选择不买。这是因为如果今天不用出行，那么也不必购买通行证，并且通行证越晚买越好。所以有 dp(i)=dp(i+1)；
     * 如果这一天是必须出行的日期，我们可以选择买 1，7 或 30 天的通行证。若我们购买了 j 天的通行证，那么接下来的 j - 1 天，我们都不再需要购买通行证，只需要考虑第 i + j天及以后即可。因此，我们有
     * dp(i)=min{cost(j)+dp(i+j)},j∈{1,7,30}
     *
     * @param days
     * @param costs
     * @return
     */
    public int mincostTickets(int[] days, int[] costs) {
        int[] dp = new int[367];
        int[] tmp = new int[]{1, 7, 30};
        Set<Integer> set = new HashSet<>();
        for (Integer day : days) {
            set.add(day);
        }
        for (int i = 365; i >= 0; i--) {
            if (!set.contains(i)) {
                dp[i] = dp[i + 1];
            } else {
                dp[i] = 1000000;
                for (int j = 0; j < 3; j++) {
                    if (i + tmp[j] < 366) {
                        dp[i] = Integer.min(dp[i], costs[j] + dp[i + tmp[j]]);
                    } else {
                        dp[i] = Integer.min(dp[i], costs[j]);
                    }
                }
            }
        }
        return dp[1];
    }

    /**
     * 146. LRU缓存机制
     */
    class LRUCache {
        Map<Integer, Integer> cache = new HashMap<>();
        Deque<Integer> queue = new LinkedList<>();
        int capacity = 0;

        public LRUCache(int capacity) {
            this.capacity = capacity;
        }

        public int get(int key) {
            if (cache.containsKey(key)) {
                queue.remove(key);
                queue.addFirst(key);
                return cache.get(key);
            }
            return -1;
        }

        public void put(int key, int value) {
            if (cache.containsKey(key)) {
                cache.put(key, value);
            } else {
                if (queue.size() == capacity) {
                    Integer poll = queue.pollLast();
                    cache.remove(poll);
                }
                cache.put(key, value);
                queue.addFirst(key);
            }
        }
    }

    /**
     * 4. 寻找两个正序数组的中位数
     * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/xun-zhao-liang-ge-you-xu-shu-zu-de-zhong-wei-s-114/
     * <p>
     * 求第k大的数
     * 若数组长度和为计数，则中位数为total/ 2，即求第total/ 2个数
     * 若为偶数，则中位数为（total/2 + total/2 + 1） / 2
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len1 = nums1.length, len2 = nums2.length;
        int totalLen = len1 + len2;
        if (totalLen % 2 == 1) {
            return getKthElement(nums1, nums2, totalLen / 2 + 1);
        } else {
            int k = getKthElement(nums1, nums2, totalLen / 2);
            int k1 = getKthElement(nums1, nums2, totalLen / 2 + 1);
            return (k1 * 1.0 + k * 1.0) / 2.0;
        }

    }

    /**
     * 寻找两个有序数组中第k个数
     * 假设两个有序数组分别是 A 和 B。要找到第 k 个元素，
     * 我们可以比较 A[k/2−1] 和 B[k/2−1]，其中 / 表示整数除法。
     * 由于 A[k/2−1] 和 B[k/2−1] 的前面分别有 \A[0..k/2−2] 和 B[0..k/2−2]，
     * k/2−1 个元素，对于A[k/2−1] 和 B[k/2−1] 中的较小值，
     * 最多只会有 (k/2−1)+(k/2−1)≤k/2−2 个元素比它小，那么它就不能是第 k小的数了。
     * <p>
     * 因此我们可以归纳出三种情况：
     * 如果 A[k/2−1]<B[k/2−1]，则比 A[k/2−1] 小的数最多只有 A 的前 k/2−1 个数和 B 的前 k/2−1 个数，
     * 即比 A[k/2−1] 小的数最多只有 k−2 个，因此 A[k/2−1] 不可能是第 k 个数，
     * A[0] 到 A[k/2−1] 也都不可能是第 k 个数，可以全部排除。
     * <p>
     * 如果 A[k/2−1]>B[k/2−1]，则可以排除 B[0] 到 B[k/2−1]。
     * <p>
     * 如果 A[k/2−1]=B[k/2−1]，则可以归入第一种情况处理。
     *
     * @param nums2
     * @param k
     * @return
     */
    int getKthElement(int[] nums1, int[] nums2, int k) {
        int len1 = nums1.length, len2 = nums2.length;
        int idx1 = 0, idx2 = 0;
        while (true) {
            if (idx1 == len1) {
                return nums2[idx2 + k - 1];
            }
            if (idx2 == len2) {
                return nums1[idx1 + k - 1];
            }
            if (k == 1) {
                return Integer.min(nums1[idx1], nums2[idx2]);
            }
            int half = k / 2;
            int newIdx1 = Integer.min(idx1 + half, len1) - 1;
            int newIdx2 = Integer.min(idx2 + half, len2) - 1;
            if (nums1[newIdx1] <= nums2[newIdx2]) {
                k = k - (newIdx1 - idx1 + 1);
                idx1 = newIdx1 + 1;
            } else {
                k = k - (newIdx2 - idx2 + 1);
                idx2 = newIdx2 + 1;
            }
        }
    }

    /**
     * 5. 最长回文子串
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        Boolean[][] dp = new Boolean[s.length()][s.length()];
        for (Boolean[] booleans : dp) {
            Arrays.fill(booleans, false);
        }
        String ans = "";
        for (int l = 0; l < s.length(); l++) {
            for (int i = 0; i < s.length() - l; i++) {
                int j = i + l;
                if (l == 0) {
                    dp[i][j] = true;
                } else if (l == 1) {
                    dp[i][j] = (s.charAt(i) == s.charAt(j));
                } else {
                    dp[i][j] = (s.charAt(i) == s.charAt(j) && dp[i + 1][j - 1]);
                }
                if (dp[i][j] && l + 1 > ans.length()) {
                    ans = s.substring(i, i + l + 1);
                }
            }
        }
        return ans;
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 25. K 个一组翻转链表
     * 每次划去长度为k的链表，并记录头的前一个和尾的后一个，反转后，接到原链表上
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // 头的前一个
        ListNode preTail = new ListNode(-1), tmp = preTail;
        preTail.next = head;
        int idx = 0;
        while (true) {
            ListNode root = head, tail = root, nextRoot;
            // 前一个刚好划分完，直接返回
            // 返回时要返回第一次划分的头
            if (root == null) {
                return tmp.next;
            }
            for (int i = 0; i < k - 1; i++) {
                tail = tail.next;
                // 剩余链表长度不够k，直接返回
                if (tail == null) {
                    return tmp.next;
                }
            }
            // 尾的下一个
            nextRoot = tail.next;
            tail.next = null;
            // 反转
            List<ListNode> reverse = reverse(root);
            if (idx == 0) {
                // 第一次划分结束，记录头，作为将来的返回
                tmp = preTail;
            }
            // 原来的头前一个的next指向新的头
            preTail.next = reverse.get(0);
            // 新的尾指向原来的尾的下一个
            reverse.get(1).next = nextRoot;
            // 更新头尾
            head = nextRoot;
            preTail = reverse.get(1);
            idx++;
        }
    }

    List<ListNode> reverse(ListNode head) {
        List<ListNode> res = new ArrayList<>();
        ListNode tmp = null;
        ListNode p = head, q;
        while (p != null) {
            q = p.next;
            p.next = tmp;
            tmp = p;
            p = q;
        }
        res.add(tmp); // 新的头
        res.add(head); // 新的尾
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

    /**
     * 236. 二叉树的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        List<TreeNode> path1 = new ArrayList<>();
        findPath(path1, p, root);
        List<TreeNode> path2 = new ArrayList<>();
        findPath(path2, q, root);
        int i = 0, j = 0;
        while (i < path1.size() && j < path2.size()) {
            if (path1.get(i) == path2.get(j)) {
                i++;
                j++;
            } else {
                break;
            }
        }
        if (i == path1.size()) {
            return path1.get(i - 1);
        }
        if (j == path2.size()) {
            return path2.get(j - 1);
        }
        return path1.get(i - 1);

    }

    void findPath(List<TreeNode> path, TreeNode target, TreeNode root) {
        if (root != null) {
            path.add(root);
            if (root != target) {
                if (path.size() != 0 && path.get(path.size() - 1) == target) {
                    return;
                }
                findPath(path, target, root.left);
                if (path.size() != 0 && path.get(path.size() - 1) == target) {
                    return;
                }
                findPath(path, target, root.right);
                if (path.size() != 0 && path.get(path.size() - 1) == target) {
                    return;
                }
                path.remove(path.size() - 1);
            }
        }
    }

    @Test
    public void test() {
        d12 o = new d12();
        System.out.println(o.mincostTickets(new int[]{2}, new int[]{2, 7, 15}));
        System.out.println(o.mincostTickets(new int[]{1, 4, 6, 7, 8, 20}, new int[]{2, 7, 15}));
        System.out.println(o.mincostTickets(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 31}, new int[]{2, 7, 15}));
        System.out.println(o.mincostTickets(new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 30, 365}, new int[]{2, 7, 15}));

        System.out.println(o.findMedianSortedArrays(new int[]{1, 2, 3, 6, 7, 9}, new int[]{4, 5, 9, 10, 15}));
        System.out.println(o.longestPalindrome("cbbd"));
        System.out.println(o.longestPalindrome("ababababa"));
        System.out.println(o.longestPalindrome("babad"));
    }
}
