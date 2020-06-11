package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d11</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-05-17 20:15
 */
public class d11 {

    /**
     * 5.04
     * 45. 跳跃游戏 II
     *
     * @param nums
     * @return
     */
    public int jump(int[] nums) {
        int[] dp = new int[nums.length];
        Arrays.fill(dp, 50000000);
        dp[0] = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            for (int j = 1; j <= nums[i]; j++) {
                if (i + j < nums.length) {
                    dp[i + j] = Integer.min(dp[i + j], dp[i] + 1);
                }
            }

        }
        return dp[dp.length - 1];
    }

    public int jump_v2(int[] nums) {
        int ans = 0;
        int end = 0;
        int maxPos = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            maxPos = Integer.max(maxPos, nums[i] + i);
            if (i == end) {
                end = maxPos;
                ans++;
            }
        }
        return ans;
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
     * 5.05
     * 98. 验证二叉搜索树
     * WRONG !!!
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left != null && root.val <= root.left.val) {
            return false;
        }
        if (root.right != null && root.val >= root.right.val) {
            return false;
        }
        return isValidBST(root.left) && isValidBST(root.right);
    }

    boolean helper(TreeNode root, Integer low, Integer high) {
        if (root == null) {
            return true;
        }
        if (low != null && root.val <= low) {
            return false;
        }
        if (high != null && root.val >= high) {
            return false;
        }
        return helper(root.right, root.val, high) && helper(root.left, low, root.val);
    }

    public boolean isValidBST_v2(TreeNode root) {
        return helper(root, null, null);
    }


    /**
     * 76. 最小覆盖子串
     * 双指针滑动窗口，双指针初始化为s的头部
     * 首先移动右指针，直到窗口内的字串满足t
     * 并更新结果
     * 然后移动右指针，直到窗口内的字串不满足t
     * 并更新结果
     * <p>
     * 如何判断字串是否满足t？ 见contains
     * 并且需要注意，左右指针的位置，以及右指针的边界位置
     *
     * @param s
     * @param t
     * @return
     */
    public String minWindow(String s, String t) {
        String res = "";
        String tmp = "";
        int left = 0, right = 0;
        Map<Character, Integer> map = new HashMap<>();
        Map<Character, Integer> tMap = new HashMap<>();
        char[] sChars = s.toCharArray();
        char[] tChars = t.toCharArray();
        // 初始化map和tMap
        for (Character c : tChars) {
            map.put(c, 0);
            tMap.put(c, tMap.getOrDefault(c, 0) + 1);
        }
        while (left <= right) {
            // 如果右指针到头，这时再进行一次左指针的移动就可以了
            if (right >= s.length()) {
                // 移动左指针，直到不满足
                while (contains(map, tMap)) {
                    char ch = sChars[left];
                    if (map.containsKey(ch)) {
                        map.put(ch, map.get(ch) - 1);
                    }
                    left++;
                }
                // 若过出现右指针到头，左指针为0，说明整串不匹配，直接返回
                if (left == 0) {
                    return "";
                }
                // 最后一次更新，并break
                tmp = s.substring(left - 1, right);
                if ("".equals(res) || res.length() > tmp.length()) {
                    res = tmp;
                }

                break;
            }
            if (contains(map, tMap)) {
                // move left
                // 窗口满足，更新左指针，直到不满足，左指针位置在窗口左端的下一个位置
                while (contains(map, tMap)) {
                    char ch = sChars[left];
                    if (map.containsKey(ch)) {
                        map.put(ch, map.get(ch) - 1);
                    }
                    left++;
                }
                tmp = s.substring(left - 1, right);
                if ("".equals(res) || res.length() > tmp.length()) {
                    res = tmp;
                }
            } else {
                // move right
                // 窗口不满足，更新右指针，直到满足，右指针位置在窗口右端的下一个位置
                while (!contains(map, tMap)) {
                    char ch = sChars[right];
                    if (map.containsKey(ch)) {
                        map.put(ch, map.get(ch) + 1);
                    }
                    right++;
                    // 右指针到头，退出
                    if (right >= s.length()) {
                        break;
                    }
                }
                // 无法确定是右指针到头退出还是窗口满足后退出，所以再判断一下
                if (contains(map, tMap)) {
                    tmp = s.substring(left, right);
                    if ("".equals(res) || res.length() > tmp.length()) {
                        res = tmp;
                    }
                }
            }
        }
        return res;
    }

    /**
     * 一次比较每个字符出现次数，当子串某一字符出现次数少于t响应字符出现次数时，就说明不匹配
     *
     * @param map  子串的字符
     * @param tMap t的字符
     * @return
     */
    public boolean contains(Map<Character, Integer> map, Map<Character, Integer> tMap) {
        for (Map.Entry<Character, Integer> e : tMap.entrySet()) {
            Character key = e.getKey();
            if (map.get(key) < (tMap.get(key))) {
                return false;
            }
        }
        return true;
    }


    class StackNode {
        int index;
        int value;

        public StackNode(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }

    /**
     * 84. 柱状图中最大的矩形
     * 单调栈
     * 对于每一柱子，都找到它左边最近比它低的，右边最近比它低的，组成的矩形即为一个候选答案，
     * 如何非暴力的获取到两边比它低的柱子，
     * 利用单调栈，并开辟一个新的数组，作为某个柱子的左边最近比它低的柱子下标
     * 右边的相同，页也用单调栈
     * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/solution/zhu-zhuang-tu-zhong-zui-da-de-ju-xing-by-leetcode-/
     *
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        Stack<StackNode> stack = new Stack<>();
        stack.push(new StackNode(-1, -1));
        int[] left = new int[heights.length];
        int[] right = new int[heights.length];
        for (int i = 0; i < heights.length; i++) {
            StackNode node = new StackNode(i, heights[i]);
            // 直到栈顶元素的值小于当前元素，则栈顶元素的下标即为left数组这个位置的值，表示这个位置左边最近比它低的柱子的下标
            while (stack.peek().value >= node.value) {
                stack.pop();
            }
            left[i] = stack.peek().index;
            stack.push(node);
        }
        stack.clear();
        stack.push(new StackNode(heights.length, -1));
        for (int i = heights.length - 1; i >= 0; i--) {
            StackNode node = new StackNode(i, heights[i]);
            while (stack.peek().value >= node.value) {
                stack.pop();
            }
            right[i] = stack.peek().index;
            stack.push(node);
        }
        System.out.println(Arrays.toString(left));
        System.out.println(Arrays.toString(right));
        int ans = 0;
        for (int i = 0; i < heights.length; i++) {
            ans = Integer.max(ans, (right[i] - left[i] - 1) * heights[i]);
        }
        return ans;
    }

    /**
     * 69. x 的平方根
     *
     * @param x
     * @return
     */
    public int mySqrt(int x) {
        return (int) Math.sqrt(x);
    }

    /**
     * 152. 乘积最大子数组
     * https://leetcode-cn.com/problems/maximum-product-subarray/solution/cheng-ji-zui-da-zi-shu-zu-by-leetcode-solution/
     *
     * @param nums
     * @return
     */
    public int maxProduct(int[] nums) {
        int ans = Integer.MIN_VALUE;
        int[][] dp = new int[nums.length][2];
        dp[0][1] = nums[0];
        dp[0][0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            int a = dp[i - 1][0] * nums[i];
            int b = dp[i - 1][1] * nums[i];
            dp[i][0] = Integer.max(Integer.max(a, b), nums[i]);
            dp[i][1] = Integer.min(Integer.min(a, b), nums[i]);
            ans = Integer.max(ans, Integer.max(Integer.max(dp[i][0], dp[i][1]), nums[i]));
        }
        return ans;
    }

    /**
     * 221. 最大正方形
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int ans = matrix[0][0] - '0';
        int[][] dp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            dp[i][0] = matrix[i][0] - '0';
            ans = Integer.max(ans, dp[i][0]);
        }
        for (int i = 0; i < matrix[0].length; i++) {
            dp[0][i] = matrix[0][i] - '0';
            ans = Integer.max(ans, dp[0][i]);
        }
        for (int i = 1; i < matrix.length; i++) {
            for (int j = 1; j < matrix[0].length; j++) {
                if (matrix[i][j] == '1') {
                    dp[i][j] = Integer.min(dp[i - 1][j], Integer.min(dp[i - 1][j - 1], dp[i][j - 1])) + 1;
                } else {
                    dp[i][j] = 0;
                }
                ans = Integer.max(ans, dp[i][j]);
            }
        }
        return ans * ans;
    }

    /**
     * 287. 寻找重复数
     * https://leetcode-cn.com/problems/find-the-duplicate-number/solution/er-fen-fa-si-lu-ji-dai-ma-python-by-liweiwei1419/
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        int left = 0, right = nums.length;
        int cnt = 0;
        while (left < right) {
            cnt = 0;
            int mid = (left + right) >>> 1;
            for (int num : nums) {
                if (num <= mid) {
                    cnt++;
                }
            }
            if (cnt > mid) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }


    /**
     * 394. 字符串解码
     *
     * @param s
     * @return
     */
    public String decodeString(String s) {
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        while (s.indexOf('[') != -1) {
            sb = new StringBuilder();
            int begin = s.lastIndexOf('[');
            int end = s.indexOf(']', begin);
            int i = begin - 1;
            int num = 0;
            while (i >= 0 && chars[i] <= '9' && chars[i] >= '0') {
                i--;
            }
            for (int j = i + 1; j < begin; j++) {
                num = num * 10 + chars[j] - '0';
            }
            String tmp = s.substring(begin + 1, end);
            for (int j = 0; j < num; j++) {
                sb.append(tmp);
            }
            s = s.substring(0, i + 1) + sb.toString() + s.substring(end + 1);
        }
        return s;
    }


    /**
     * 155. 最小栈
     */
    class MinStack {
        Stack<Integer> stack = new Stack<>();
        Stack<Integer> min = new Stack<>();

        /**
         * initialize your data structure here.
         */
        public MinStack() {

        }

        public void push(int x) {
            stack.push(x);
            if (min.size() == 0 || min.peek() >= x) {
                min.push(x);
            }
        }

        public void pop() {
            Integer pop = stack.pop();
            if (pop.equals(min.peek())) {
                min.pop();
            }
        }

        public int top() {
            return stack.peek();
        }

        public int getMin() {
            return min.peek();
        }
    }

    /**
     * 198. 打家劫舍
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Integer.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            dp[i] = Integer.max(dp[i - 1], dp[i - 2] + nums[i]);
        }
        return dp[dp.length - 1];
    }

    /**
     * 572. 另一个树的子树
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (s != null) {
            boolean f = isSame(s, t);
            if (f) {
                return true;
            }
            boolean f1 = isSubtree(s.left, t);
            if (f1) {
                return f1;
            }
            boolean f2 = isSubtree(s.right, t);
            if (f2) {
                return f2;
            }
            return false;
        }
        return t == null;
    }

    boolean isSame(TreeNode s, TreeNode t) {
        if (s != null && t != null) {
            if (s.val == t.val) {
                return isSame(s.left, t.left) && isSame(s.right, t.right);
            } else {
                return false;
            }
        } else {
            return s == null && t == null;
        }
    }

    /**
     * 680. 验证回文字符串 Ⅱ
     *
     * @param s
     * @return
     */
    public boolean validPalindrome(String s) {
        int left = 0, right = s.length() - 1;
        char[] chars = s.toCharArray();
        while (left < right) {
            if (chars[left] == chars[right]) {
                left++;
                right--;
            } else {
                boolean f1 = true, f2 = true;
                int bakLeft = left;
                int bakRight = right;
                left++;
                while (left < right) {
                    if (chars[left] == chars[right]) {
                        left++;
                        right--;
                    } else {
                        f1 = false;
                        break;
                    }
                }
                left = bakLeft;
                right = bakRight;
                right--;
                while (left < right) {
                    if (chars[left] == chars[right]) {
                        left++;
                        right--;
                    } else {
                        f2 = false;
                        break;
                    }
                }
                return f1 || f2;
            }
        }
        return true;
    }


    @Test
    public void test() {
        d11 o = new d11();
        System.out.println(o.jump_v2(new int[]{2, 3, 1, 1, 4}));
        System.out.println(o.maxProduct(new int[]{-9, 0, 2, 4, -1, 2, 9, 4, -2, 5, -6, 5, 4, 7, -1, 1, 0, 5, -4}));

        System.out.println(o.validPalindrome("fdjksfgakjsdhfjlkbk"));

        System.out.println(o.decodeString("3[a]2[bc]"));
        System.out.println(o.decodeString("3[a2[c]]"));
        System.out.println(o.decodeString("2[abc]3[cd]ef"));
        System.out.println(o.decodeString("abc3[cd]xyz"));

        System.out.println(o.largestRectangleArea(new int[]{6, 7, 5, 2, 4, 5, 9, 3}));
        System.out.println(o.largestRectangleArea(new int[]{1, 1}));
        System.out.println(o.largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));

        System.out.println(o.minWindow("ADOBECODEBANC", "COD"));
        System.out.println(o.minWindow("ADOBECODEBANC", "ADOBECODEBANC"));
        System.out.println(o.minWindow("ADOBECODEBANC", "ABC"));
        System.out.println(o.minWindow("ADOBECODEBANC", "C"));
        System.out.println(o.minWindow("ADOBECODEBANC", "COB"));
        System.out.println(o.minWindow("ADOBECODEBANC", "COBN"));
        System.out.println(o.minWindow("ADOBECODEBANC", "A"));
        System.out.println(o.minWindow("ADOBECODEBANC", "AE"));
        System.out.println(o.minWindow("ADOBECODEBANC", "AZ"));
    }
}
