package com.wushuai.daily;

import java.util.*;

/**
 * <p>d10_1</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-10-01 20:20
 */
public class d10_1 {


    /**
     * 10.01 LCP 19. 秋叶收藏集
     *
     * @param leaves
     * @return
     */
    public int minimumOperations(String leaves) {
        int n = leaves.length();
        int[][] f = new int[n][3];
        f[0][0] = leaves.charAt(0) == 'y' ? 1 : 0;
        f[0][1] = f[0][2] = f[1][2] = Integer.MAX_VALUE;
        for (int i = 1; i < n; ++i) {
            int isRed = leaves.charAt(i) == 'r' ? 1 : 0;
            int isYellow = leaves.charAt(i) == 'y' ? 1 : 0;
            f[i][0] = f[i - 1][0] + isYellow;
            f[i][1] = Math.min(f[i - 1][0], f[i - 1][1]) + isRed;
            if (i >= 2) {
                f[i][2] = Math.min(f[i - 1][1], f[i - 1][2]) + isYellow;
            }
        }
        return f[n - 1][2];
    }


    /**
     * 10.02 771. 宝石与石头
     *
     * @param J
     * @param S
     * @return
     */
    public int numJewelsInStones(String J, String S) {
        Set<Character> set = new HashSet<>();
        for (char ch : J.toCharArray()) {
            set.add(ch);
        }
        int res = 0;
        for (char ch : S.toCharArray()) {
            if (set.contains(ch)) {
                res++;
            }
        }
        return res;
    }


    /**
     * 10.03 1. 两数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 10.04 2. 两数相加
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        while (l1 != null) {
            sb1.append(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            sb2.append(l2.val);
            l2 = l2.next;
        }
        int add = 0, i, j;
        for (i = 0, j = 0; i < sb1.length() && j < sb2.length(); i++, j++) {
            int x = sb1.charAt(i) - '0' + sb2.charAt(j) - '0' + add;
            if (x >= 10) {
                add = 1;
                x -= 10;
            } else {
                add = 0;
            }
            sb3.append(x);
        }
        while (i != sb1.length()) {
            int x = sb1.charAt(i++) - '0' + add;
            if (x >= 10) {
                add = 1;
                x -= 10;
            } else {
                add = 0;
            }
            sb3.append(x);
        }
        while (j != sb2.length()) {
            int x = sb2.charAt(j++) - '0' + add;
            if (x >= 10) {
                add = 1;
                x -= 10;
            } else {
                add = 0;
            }
            sb3.append(x);
        }
        if (add != 0) {
            sb3.append(1);
        }
        ListNode root = new ListNode();
        ListNode tmp = root;
        for (i = 0; i < sb3.length(); i++) {
            root.next = new ListNode(sb3.charAt(i) - '0');
            root = root.next;
        }
        return tmp.next;
    }


    /**
     * 10.05 18. 四数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 4) {
            return res;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 3; i++) {
            // 去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            // 剪枝
            if (nums[i] + nums[i + 1] + nums[i + 2] + nums[i + 3] > target) {
                break;
            }
            // 剪枝
            if (nums[i] + nums[nums.length - 3] + nums[nums.length - 2] + nums[nums.length - 1] < target) {
                continue;
            }
            for (int j = i + 1; j < nums.length - 2; j++) {
                // 去重
                if (j > i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                // 剪枝
                if (nums[i] + nums[j] + nums[j + 1] + nums[j + 2] > target) {
                    break;
                }
                // 剪枝
                if (nums[i] + nums[j] + nums[nums.length - 2] + nums[nums.length - 1] < target) {
                    continue;
                }
                int left = j + 1, right = nums.length - 1;
                while (left < right) {
                    int x = nums[i] + nums[j] + nums[left] + nums[right];
                    if (x > target) {
                        right--;
                    } else if (x < target) {
                        left++;
                    } else {
                        res.add(new ArrayList<>(Arrays.asList(nums[i], nums[j], nums[left], nums[right])));
                        // 去重
                        while (left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        left++;
                        while (left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        right--;
                    }
                }
            }
        }
        return res;
    }


    /**
     * 10.06 834. 树中距离之和
     * TTL
     *
     * @param N
     * @param edges
     * @return
     */
    public int[] sumOfDistancesInTreeV0(int N, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>(Collections.nCopies(N, null));
        for (int[] edge : edges) {
            int from = edge[0];
            int to = edge[1];
            List<Integer> e = graph.get(from);
            if (e == null) {
                e = new ArrayList<>();
            }
            e.add(to);
            graph.set(from, e);

            e = graph.get(to);
            if (e == null) {
                e = new ArrayList<>();
            }
            e.add(from);
            graph.set(to, e);
        }
        int[] dist = new int[N];
        int[] res = new int[N];
        Arrays.fill(res, 0);
        for (int i = 0; i < N; i++) {
            Arrays.fill(dist, Integer.MAX_VALUE);
            dijkstra(graph, dist, i);
            for (int j = 0; j < N; j++) {
                res[i] += dist[j];
            }
        }
        return res;
    }

    void dijkstra(List<List<Integer>> graph, int[] dist, int from) {
        dist[from] = 0;
        Queue<Integer> queue = new PriorityQueue<>();
        queue.add(from);
        while (!queue.isEmpty()) {
            from = queue.poll();
            List<Integer> edge = graph.get(from);
            for (Integer to : edge) {
                if (dist[from] + 1 < dist[to]) {
                    dist[to] = dist[from] + 1;
                    queue.add(to);
                }
            }
        }
    }

    int[] ans;
    int[] sz;
    int[] dp;
    List<List<Integer>> graph;

    /**
     * 树状dp
     *
     * @param N
     * @param edges
     * @return
     */
    public int[] sumOfDistancesInTree(int N, int[][] edges) {
        ans = new int[N];
        sz = new int[N];
        dp = new int[N];
        graph = new ArrayList<List<Integer>>();
        for (int i = 0; i < N; ++i) {
            graph.add(new ArrayList<Integer>());
        }
        for (int[] edge : edges) {
            int u = edge[0], v = edge[1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }
        dfs(0, -1);
        dfs2(0, -1);
        return ans;
    }

    public void dfs(int u, int f) {
        sz[u] = 1;
        dp[u] = 0;
        for (int v : graph.get(u)) {
            if (v == f) {
                continue;
            }
            dfs(v, u);
            dp[u] += dp[v] + sz[v];
            sz[u] += sz[v];
        }
    }

    public void dfs2(int u, int f) {
        ans[u] = dp[u];
        for (int v : graph.get(u)) {
            if (v == f) {
                continue;
            }
            int pu = dp[u], pv = dp[v];
            int su = sz[u], sv = sz[v];

            dp[u] -= dp[v] + sz[v];
            sz[u] -= sz[v];
            dp[v] += dp[u] + sz[u];
            sz[v] += sz[u];

            dfs2(v, u);

            dp[u] = pu;
            dp[v] = pv;
            sz[u] = su;
            sz[v] = sv;
        }
    }


    /**
     * 10.07 75. 颜色分类
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        int p0 = 0, p1 = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                int t = nums[i];
                nums[i] = nums[p1];
                nums[p1] = t;
                p1++;
            } else if (nums[i] == 0) {
                int t = nums[i];
                nums[i] = nums[p0];
                nums[p0] = t;
                if (p0 < p1) {
                    t = nums[i];
                    nums[i] = nums[p1];
                    nums[p1] = t;
                }
                p0++;
                p1++;
            }
        }
    }


    /**
     * 10.09 141. 环形链表
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        if (head == null)
            return false;
        //快慢两个指针
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            //慢指针每次走一步
            slow = slow.next;
            //快指针每次走两步
            fast = fast.next.next;
            //如果相遇，说明有环，直接返回true
            if (slow == fast)
                return true;
        }
        //否则就是没环
        return false;
    }


    /**
     * 10.10 142. 环形链表 II
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        if (head == null)
            return null;
        //快慢两个指针
        ListNode slow = head;
        ListNode fast = head;
        ListNode ptr = head;
        while (fast != null && fast.next != null) {
            //慢指针每次走一步
            slow = slow.next;
            //快指针每次走两步
            fast = fast.next.next;
            //如果相遇，说明有环，直接返回true
            if (slow == fast) {
                while (ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }
                return slow;
            }
        }
        //否则就是没环
        return null;
    }
}
