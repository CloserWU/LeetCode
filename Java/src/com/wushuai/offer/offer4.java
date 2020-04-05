package com.wushuai.offer;

import org.junit.Test;

import java.util.*;

/**
 * <p>offer4</p>
 * <p>
 * List<int[]> 面试题57
 * </p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-03 08:51
 */
public class offer4 {

    int res = 0;

    void dfs(String str, int n) {
        if (n >= str.length() - 1) {
            res++;
            return;
        }
        dfs(str, n + 1);
        if (str.charAt(n) - '0' > 0 && n + 1 < str.length() && (str.charAt(n) - '0') * 10 + str.charAt(n + 1) - '0' < 26) {
            dfs(str, n + 2);
        }
    }

    /**
     * 面试题46. 把数字翻译成字符串
     * <p>
     * dfs
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {
        res = 0;
        String str = String.valueOf(num);
        dfs(str, 0);
        return res;
    }

    /**
     * 面试题47. 礼物的最大价值
     * <p>
     * 基础dp
     *
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {
        int[][] dp = new int[grid.length + 1][grid[0].length + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j] + grid[i - 1][j - 1], dp[i][j - 1] + grid[i - 1][j - 1]);
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }


    /**
     * 面试题48. 最长不含重复字符的子字符串
     * <p>
     * 利用map记录当前不重复字母最新出现的位置<char, pos>, 若有重复字母出现，则更新map中相应字符的pos
     * 出现重复字符时记录一下最长不重复子串 的左边界在哪里
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int res = 0;
        int left = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), i);
            } else {
                left = Math.max(left, map.get(s.charAt(i)) + 1);
                map.put(s.charAt(i), i);
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }

    /**
     * TODO
     * 面试题49. 丑数
     * <p>
     * 较大的丑数一定能够通过 某较小丑数 × 某因子 得到。
     * <p>
     * 采取用三个指针的方法，n2,n3,n5
     * n2指向的数字下一次永远*2，n3指向的数字下一次永远*3，n5指向的数字永远*5
     * 我们从2*n2 3*n3 5*n5选取最小的一个数字，作为第k个丑数
     * 如果第K个丑数==2*p2，也就是说前面0-p2个丑数*2不可能产生比第K个丑数更大的丑数了，所以p2++
     * p3,p5同
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        int[] dp = new int[n];
        int a = 0, b = 0, c = 0;
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            int n2 = dp[a] * 2, n3 = dp[b] * 3, n5 = dp[c] * 5;
            dp[i] = Math.min(Math.min(n2, n3), n5);
            if (dp[i] == n2) {
                a++;
            }
            if (dp[i] == n3) {
                b++;
            }
            if (dp[i] == n5) {
                c++;
            }
        }
        return dp[n - 1];
    }


    /**
     * 面试题50. 第一个只出现一次的字符
     *
     * @param s
     * @return
     */
    public char firstUniqChar(String s) {
        if (s.length() == 0) {
            return ' ';
        }
        Map<Character, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
            } else {
                map.put(s.charAt(i), 1);
            }
        }
        for (Map.Entry<Character, Integer> e : map.entrySet()) {
            if (e.getValue() == 1) {
                return e.getKey();
            }
        }
        return ' ';
    }


    /**
     * TODO
     * 面试题51. 数组中的逆序对
     *
     * @param nums
     * @return
     */
    public int reversePairs(int[] nums) {
        int len = nums.length;
        if (len < 2) {
            return 0;
        }
        int[] temp = new int[len];
        return reversePairs(nums, 0, len - 1, temp);
    }

    /**
     * 计算在数组 nums 的索引区间 [left, right] 内统计逆序对
     *
     * @param nums  待统计的数组
     * @param left  待统计数组的左边界，可以取到
     * @param right 待统计数组的右边界，可以取到
     * @return
     */
    private int reversePairs(int[] nums, int left, int right, int[] temp) {
        // 极端情况下，就是只有 1 个元素的时候，这里只要写 == 就可以了，不必写大于
        if (left == right) {
            return 0;
        }

        int mid = (left + right) >>> 1;

        int leftPairs = reversePairs(nums, left, mid, temp);
        int rightPairs = reversePairs(nums, mid + 1, right, temp);

        int reversePairs = leftPairs + rightPairs;
        if (nums[mid] <= nums[mid + 1]) {
            return reversePairs;
        }

        int reverseCrossPairs = mergeAndCount(nums, left, mid, right, temp);
        return reversePairs + reverseCrossPairs;

    }

    /**
     * [left, mid] 有序，[mid + 1, right] 有序
     *
     * @param nums
     * @param left
     * @param mid
     * @param right
     * @param temp
     * @return
     */
    private int mergeAndCount(int[] nums, int left, int mid, int right, int[] temp) {
        // 复制到辅助数组里，帮助我们完成统计
        for (int i = left; i <= right; i++) {
            temp[i] = nums[i];
        }
        int i = left;
        int j = mid + 1;
        int res = 0;
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                // i 用完了，只能用 j
                nums[k] = temp[j];
                j++;
            } else if (j > right) {
                // j 用完了，只能用 i
                nums[k] = temp[i];
                i++;
            } else if (temp[i] <= temp[j]) {
                // 此时前数组元素出列，不统计逆序对
                nums[k] = temp[i];
                i++;
            } else {
                // 此时后数组元素出列，统计逆序对，快就快在这里，一次可以统计出一个区间的个数的逆序对
                nums[k] = temp[j];
                j++;
                res += (mid - i + 1);
            }
        }
        return res;
    }


    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
            next = null;
        }
    }

    /**
     * 面试题52. 两个链表的第一个公共节点
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int lenA = 0;
        ListNode bakA = headA;
        while (headA != null) {
            lenA++;
            headA = headA.next;
        }
        int lenB = 0;
        ListNode bakB = headB;
        while (headB != null) {
            lenB++;
            headB = headB.next;
        }
        headA = bakA;
        headB = bakB;
        if (lenA > lenB) {
            while (lenA > lenB) {
                headA = headA.next;
                lenA--;
            }
        } else if (lenA < lenB) {
            while (lenB > lenA) {
                headB = headB.next;
                lenB--;
            }
        }
        while (headA != null) {
            if (headA == headB) {
                return headA;
            }
            headA = headA.next;
            headB = headB.next;
        }
        return null;
        /*
        https://leetcode-cn.com/problems/liang-ge-lian-biao-de-di-yi-ge-gong-gong-jie-dian-lcof/solution/shuang-zhi-zhen-fa-lang-man-xiang-yu-by-ml-zimingm/
        ListNode h1 = headA, h2 = headB;
        while (h1 != h2) {
            h1 = h1 == null ? headB : h1.next;
            h2 = h2 == null ? headA : h2.next;
        }
        return h1;
         */
    }

    /**
     * TODO
     *
     * @param arr
     * @param target
     * @return
     */
    int binarySearch(int[] arr, int target) {
        int low = 0, high = arr.length - 1;
        while (low <= high) {
            int mid = (low + high) >>> 1;
            if (arr[mid] > target) {
                high = mid - 1;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    /**
     * 面试题53 - I. 在排序数组中查找数字 I
     *
     * @param nums
     * @param target
     * @return
     */
    public int search(int[] nums, int target) {
        if (nums.length == 0) {
            return 0;
        }
        int idx = binarySearch(nums, target);
        if (idx == -1) {
            return 0;
        }
        int low = idx, high = idx;
        for (int i = idx + 1; i < nums.length; i++) {
            if (nums[i] == nums[idx]) {
                high++;
            } else {
                break;
            }
        }
        for (int i = idx - 1; i >= 0; i--) {
            if (nums[i] == nums[idx]) {
                low--;
            } else {
                break;
            }
        }
        return high - low + 1;
    }

    /**
     * 面试题53 - II. 0～n-1中缺失的数字
     * <p>
     * 有序数组就想到二分
     *
     * @param nums
     * @return
     */
    public int missingNumber(int[] nums) {
        int i = 0, j = nums.length - 1;
        while (i <= j) {
            int m = (i + j) >>> 1;
            if (nums[m] == m) {
                i = m + 1;
            } else {
                j = m - 1;
            }
        }
        return i;

        /*int total = nums.length * (nums.length + 1) / 2;
        int t = 0;
        for (int num : nums) {
            t += num;
        }
        return total - t;*/
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    int k = 0;

    int inOrder(TreeNode root) {
        if (root != null && k >= 0) {
            int left = inOrder(root.right);
            if (left != Integer.MIN_VALUE) {
                return left;
            }
            if (k == 1) {
                return root.val;
            }
            k--;
            int right = inOrder(root.left);
            if (right != Integer.MIN_VALUE) {
                return right;
            }
        }
        return Integer.MIN_VALUE;
    }

    /**
     * 面试题54. 二叉搜索树的第k大节点
     * <p>
     * 第k大 右根左
     * 第k小 左根右
     *
     * @param root
     * @param k
     * @return
     */
    public int kthLargest(TreeNode root, int k) {
        this.k = k;
        return inOrder(root);
    }

    /**
     * 面试题55 - I. 二叉树的深度
     *
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root != null) {
            int left = maxDepth(root.left) + 1;
            int right = maxDepth(root.right) + 1;
            return Integer.max(left, right);
        }
        return 0;
        /* c++  TODO 层序
        if(root == NULL) return 0;
        std::queue<TreeNode*> que;
        int level = 0, last = 0;
        int _front = -1, rear = -1;
        que.push(root);rear++;
        while(_front < rear){
            TreeNode* t = que.front();_front++;que.pop();
            if(t && t->left != NULL) {que.push(t->left);rear++;}
            if(t && t->right != NULL) {que.push(t->right);rear++;}
            if(last == _front){
                level++;
                last = rear;
            }

        }
        return level;
         */
    }

    boolean flag = true;

    int Judge(TreeNode root) {
        if (root != null) {
            int left = Judge(root.left) + 1;
            int right = Judge(root.right) + 1;
            if (Math.abs(left - right) >= 2) {
                flag = false;
            }
            return Math.max(left, right);
        }
        return 0;
    }

    /**
     * 面试题55 - II. 平衡二叉树
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if (root != null) {
            Judge(root);
        }
        return flag;
    }

    /**
     * TODO
     * 面试题56 - I. 数组中数字出现的次数
     * <p>
     * https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof/solution/zhi-chu-xian-yi-ci-de-shu-xi-lie-wei-yun-suan-by-a/
     *
     * @param nums
     * @return
     */
    public int[] singleNumbers(int[] nums) {
//        所有数字异或的结果
        int ret = 0;
        int a = 0, b = 0;
        for (int i = 0; i < nums.length; i++) {
            ret ^= nums[i];
        }
//        找到第一位不是0的
        int h = 1;
        while ((ret & h) == 0) {
            h <<= 1;
        }
        for (int i = 0; i < nums.length; i++) {
//             根据该位是否为0将其分为两组
            if ((h & nums[i]) == 0) {
                a ^= nums[i];
            } else {
                b ^= nums[i];
            }
        }
        return new int[]{a, b};
    }


    /**
     * TODO
     * 面试题56 - II. 数组中数字出现的次数 II
     * https://leetcode-cn.com/problems/shu-zu-zhong-shu-zi-chu-xian-de-ci-shu-lcof/solution/zhi-chu-xian-yi-ci-de-shu-xi-lie-wei-yun-suan-by-a/
     *
     * @param nums
     * @return
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
//          记录当前 bit 有多少个1
            int cnt = 0;
//           记录当前要操作的 bit
            int bit = 1 << i;
            for (int j = 0; j < nums.length; j++) {
                if ((nums[j] & bit) != 0) {
                    cnt += 1;
                }
            }
//            不等于0说明唯一出现的数字在这个 bit 上是1
            if (cnt % 3 != 0) {
                res |= bit;
            }
        }
        return res;
    }

    /**
     * 面试题57. 和为s的两个数字
     * <p>
     * TLE
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    return new int[]{nums[i], nums[j]};
                } else if (nums[i] + nums[j] == target) {
                    break;
                }
            }
        }
        return new int[]{};
    }

    /**
     * TODO
     * 双指针 O(n)
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum_v2(int[] nums, int target) {
        int l = 0, r = nums.length - 1;
        while (l < r) {
            int res = nums[l] + nums[r];
            if (res == target) {
                return new int[]{nums[l], nums[r]};
            } else if (res > target) {
                r--;
            } else {
                l++;
            }
        }
        return new int[]{};
    }

    /**
     * 面试题57 - II. 和为s的连续正数序列
     *
     * @param target
     * @return
     */
    public int[][] findContinuousSequence(int target) {
        List<int[]> lists = new ArrayList<>();
        for (int i = 1; i < target; i++) {
            List<Integer> list = new ArrayList<>();
            int res = 0;
            for (int j = i; j < target; j++) {
                res += j;
                list.add(j);
                if (res == target) {
                    int[] tmp = new int[list.size()];
                    for (int k = 0; k < list.size(); k++) {
                        tmp[k] = list.get(k);
                    }
                    lists.add(tmp);
                    break;
                }
                if (res > target) {
                    break;
                }
            }
        }
        return lists.toArray(new int[0][]);
    }

    /**
     * 面试题58 - I. 翻转单词顺序
     *
     * @param s
     * @return
     */
    public String reverseWords(String s) {
        String[] split = s.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = split.length - 1; i >= 0; i--) {
            sb.append(' ');
            sb.append(split[i]);
        }
        return sb.substring(1);
    }

    /**
     * 面试题58 - II. 左旋转字符串
     *
     * @param s
     * @param n
     * @return
     */
    public String reverseLeftWords(String s, int n) {
        return s.substring(n) + s.substring(0, n);
    }

    /**
     * 面试题59 - I. 滑动窗口的最大值
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (k == 0) {
            return new int[]{};
        }
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length - k + 1; i++) {
            int max = Integer.MIN_VALUE;
            for (int j = i; j < k; j++) {
                max = Math.max(max, nums[j]);
            }
            list.add(max);
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    /**
     * 线性复杂度实现
     * 记录队列的最大值，思想方法同 面试题59 - II. 队列的最大值 maxQueue.gif
     *
     * @param nums
     * @param k
     * @return
     */
    public int[] maxSlidingWindow_v2(int[] nums, int k) {
        if (k == 0) {
            return new int[]{};
        }
        List<Integer> list = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        Deque<Integer> maxQueue = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            queue.add(nums[i]);
            if (maxQueue.isEmpty() || maxQueue.getLast() > nums[i]) {
                maxQueue.addLast(nums[i]);
            } else {
                while (!maxQueue.isEmpty() && maxQueue.getLast() < nums[i]) {
                    maxQueue.removeLast();
                }
                maxQueue.addLast(nums[i]);
            }
            if (queue.size() == k) {
                list.add(maxQueue.getFirst());
                if (queue.peek().equals(maxQueue.getFirst())) {
                    maxQueue.removeFirst();
                }
                queue.poll();
            }
        }
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    /**
     * 面试题59 - II. 队列的最大值
     * Your MaxQueue object will be instantiated and called as such:
     * MaxQueue obj = new MaxQueue();
     * int param_1 = obj.max_value();
     * obj.push_back(value);
     * int param_3 = obj.pop_front();
     * <p>
     * 最大值出队后，无法知道队列里的下一个最大值。
     * 我们只需记住当前最大值出队后，队列里的下一个最大值即可。
     * 具体方法是使用一个双端队列 deque，在每次入队时，如果 deque 队尾元素小于即将入队的元素 value，
     * 则将小于 value 的元素全部出队后，再将 value 入队；否则直接入队。
     * https://leetcode-cn.com/problems/dui-lie-de-zui-da-zhi-lcof/solution/ru-he-jie-jue-o1-fu-za-du-de-api-she-ji-ti-by-z1m/
     * <p>
     * ![gif](https://pic.leetcode-cn.com/9d038fc9bca6db656f81853d49caccae358a5630589df304fc24d8999777df98-fig3.gif)
     */
    class MaxQueue {
        Queue<Integer> queue = null;
        int[] deque = null;
        int begin, end;

        public MaxQueue() {
            queue = new LinkedList<>();
            deque = new int[10000];
            begin = 0;
            end = -1;
        }

        public int max_value() {
            if (queue.isEmpty()) {
                return -1;
            }
            return deque[begin];
        }

        public void push_back(int value) {
            if (end != -1 && value > deque[end]) {
                while (end >= begin && deque[end] < value) {
                    end--;
                }
            }
            deque[++end] = value;
            queue.add(value);
        }

        public int pop_front() {
            if (queue.isEmpty()) {
                return -1;
            }
            if (queue.peek() == deque[begin]) {
                begin++;
            }
            return queue.poll();
        }
    }

    public long factorial(long number) {
        /*
        int[] fab = new int[11];
        fab[0] = 1;
        for (int i = 1; i < 11; i++) {
            fab[i] = fab[i - 1] * i;
        }
         */
        if (number <= 1) {
            return 1;
        }
        return number * factorial(number - 1);
    }


    /**
     * TLE  时间复杂度太高O(n*6^n) 指数 n=10 时， 执行需要8s
     * 单纯使用递归搜索解空间的时间复杂度为 6^n，会造成超时错误，因为存在重复子结构
     * getCount(2,4)=getCount(1,1)+getCount(1,2)+getCount(1,3)
     * getCount(2, 6) = getCount(1, 1) + getCount(1, 2) + getCount(1, 3) + getCount(1, 4) + getCount(1, 5)
     * 存在大量的重复计算。
     *
     * @param n
     * @param m
     * @param local
     * @param localLen
     * @return
     */
    int dfs(int n, int m, int local, int localLen) {
        if (localLen == n) {
            return 0;
        }
        int res = 0;
        for (int i = 1; i <= 6; i++) {
            if (local + i == m && localLen + 1 == n) {
                return 1;
            }
            if (local + i < m) {
                res += dfs(n, m, local + i, localLen + 1);
            } else {
                break;
            }
        }
        return res;
    }

    /**
     * 面试题60. n个骰子的点数
     *
     * @param n
     * @return
     */
    public double[] twoSum(int n) {
        double[] res = new double[5 * n + 1];
        double sum = Math.pow(6, n);
        for (int i = 0; i < 5 * n + 1; i++) {
            res[i] = dfs(n, i + n, 0, 0) / sum;
        }
        return res;
    }

    /**
     * 面试题60. n个骰子的点数
     * dp
     * dp[i][j]，表示投掷完 i 枚骰子后，点数 j 的出现次数
     * dp[i][j] = sum(dp[i-1][j-k]) (1<=k<=6 && k<=j)
     * 前i个骰子得到j的次数=与j之差小于等于6大于等于1的前i-1个骰子的j之和 （姓党与青蛙一次可以跳n阶）
     * <p>
     * 关于空间优化的反向遍历 查看ppt
     * https://leetcode-cn.com/problems/nge-tou-zi-de-dian-shu-lcof/solution/nge-tou-zi-de-dian-shu-dong-tai-gui-hua-ji-qi-yo-3/
     *
     * @param n
     * @return
     */
    public double[] twoSum_v2(int n) {
        int[][] dp = new int[n + 1][6 * n + 1];
        for (int i = 1; i <= 6; i++) {
            dp[1][i] = 1;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = i; j <= 6 * i; j++) {
                for (int k = 1; k <= 6; k++) {
                    if (j - k <= 0) {
                        break;
                    }
                    dp[i][j] += dp[i - 1][j - k];
                }
            }
        }
        int sum = (int) Math.pow(6, n);
        double[] res = new double[5 * n + 1];
        for (int i = 0; i < 5 * n + 1; i++) {
            res[i] = dp[n][i + n] * 1.0 / sum;
        }
        return res;
    }


    @Test
    public void test1() {
        offer4 o = new offer4();
        System.out.println(o.translateNum(25));
        System.out.println(o.lengthOfLongestSubstring("acbhasgfshjdafkasdhgfsacdbkh"));
        long begin = System.currentTimeMillis();
        o.twoSum_v2(2);
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
        System.out.println(Arrays.toString(o.maxSlidingWindow_v2(new int[]{1, 3, -1, -3, 5, 3, 8}, 3)));
    }
}

