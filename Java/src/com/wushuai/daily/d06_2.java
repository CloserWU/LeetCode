package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d06_2</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-10 9:33
 */
public class d06_2 {

    /**
     * 6.11 739. 每日温度
     * *******单调栈******
     *
     * @param T
     * @return
     */
    public int[] dailyTemperatures(int[] T) {
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[T.length];
        stack.push(0);
        for (int i = 1; i < T.length; i++) {
            while (!stack.isEmpty() && T[stack.peek()] < T[i]) {
                Integer peek = stack.pop();
                res[peek] = i - peek;
            }
            stack.push(i);
        }
        return res;
    }


    /**
     * 6.12 15. 三数之和
     * 标签：数组遍历
     * 首先对数组进行排序，排序后固定一个数 nums[i]，再使用左右指针指向 nums[i]后面的两端，
     * 数字分别为 nums[L] 和 nums[R]，计算三个数的和 sum 判断是否满足为 0，满足则添加进结果集
     * 如果 nums[i]大于 0，则三数之和必然无法等于 0，结束循环
     * 如果 nums[i] == nums[i-1]，则说明该数字重复，会导致结果重复，所以应该跳过
     * 当 sum == 0 时，nums[L] == nums[L+1] 则会导致结果重复，应该跳过，L++
     * 当 sum == 0 时，nums[R] == nums[R−1] 则会导致结果重复，应该跳过，R−−
     * 时间复杂度：O(n^2)
     * https://leetcode-cn.com/problems/3sum/solution/hua-jie-suan-fa-15-san-shu-zhi-he-by-guanpengchn/
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length < 3) {
            return res;
        }
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            // 如果当前数字大于0，则三数之和一定大于0，所以结束循环
            if (nums[i] > 0) {
                break;
            }
            // 去重
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int target = nums[i] + nums[left] + nums[right];
                if (target < 0) {
                    left++;
                } else if (target > 0) {
                    right--;
                } else {
                    res.add(Arrays.asList(nums[i], nums[right], nums[left]));
                    // 去重
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // 去重
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                }
            }
        }
        return res;
    }


    /**
     * 6.13 70. 爬楼梯
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        if (n == 1) {
            return 1;
        }
        int[] dp = new int[n];
        dp[0] = 1;
        dp[1] = 2;
        for (int i = 2; i < n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[n - 1];
    }


    /**
     * 6.14 1300. 转变数组后最接近目标值的数组和
     * https://leetcode-cn.com/problems/sum-of-mutated-array-closest-to-target/solution/bian-shu-zu-hou-zui-jie-jin-mu-biao-zhi-de-shu-zu-/
     *
     * @param arr
     * @param target
     * @return
     */
    public int findBestValue(int[] arr, int target) {
        Arrays.sort(arr);
        int n = arr.length;
        int[] prefix = new int[n + 1];
        // 前缀和
        for (int i = 1; i <= n; i++) {
            prefix[i] = prefix[i - 1] + arr[i - 1];
        }
        int r = arr[n - 1];
        int res = 0, diff = target;
        for (int i = 0; i <= r; i++) {
            // binarySearch没找到key的话，返回-(low + 1)
            // 这里要得到low，也就是数组的左开端
            int idx = binarySearch(arr, i, 0, n);
            if (idx < 0) {
                idx = -idx - 1;
            }
            int cur = prefix[idx] + (n - idx) * i;
            if (Math.abs(cur - target) < diff) {
                res = i;
                diff = Math.abs(cur - target);
            }
        }
        return res;
    }

    /**
     * [low, high)  --->
     *
     * @param arr
     * @param key
     * @param low
     * @param high
     * @return
     */
    int binarySearch(int[] arr, int key, int low, int high) {
        high--;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (arr[mid] > key) {
                high = mid - 1;
            } else if (arr[mid] < key) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -(low + 1);
    }


    /**
     * 6.15 14. 最长公共前缀
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        if (strs == null || strs.length == 0) {
            return "";
        }
        String str = strs[0];
        for (String aList : strs) {
            while (!aList.startsWith(str)) {
                if (str.length() == 1) {
                    return "";
                }
                str = str.substring(0, str.length() - 1);
            }
        }
        return str;
    }



    /**
     * 6.16 297. 二叉树的序列化与反序列化
     */
    class Codec {

        // Encodes a tree to a single string.
        public String serialize(TreeNode root) {
            if (root == null) {
                return "[]";
            }
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            sb.append(root.val);
            sb.append(',');
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            while (!queue.isEmpty()) {
                TreeNode head = queue.poll();
                if (head.left == null) {
                    sb.append("null,");
                } else {
                    sb.append(head.left.val);
                    sb.append(',');
                    queue.add(head.left);
                }
                if (head.right == null) {
                    sb.append("null,");
                } else {
                    sb.append(head.right.val);
                    sb.append(',');
                    queue.add(head.right);
                }
            }
            int i;
            for (i = sb.length() - 1; i >=0 ; i--) {
                if (sb.charAt(i) <= '9' && sb.charAt(i) >= '0') {
                    break;
                }
            }
            return sb.substring(0, i + 1) + "]";
        }

        // Decodes your encoded data to tree.
        public TreeNode deserialize(String data) {
            if ("[]".equals(data)) {
                return null;
            }
            String[] strs = data.substring(1, data.length() - 1).split(",");
            TreeNode root = new TreeNode(Integer.parseInt(strs[0]));
            Queue<TreeNode> queue = new LinkedList<>();
            queue.add(root);
            for (int i = 1; i < strs.length; i += 2) {
                TreeNode head = queue.poll();
                if (!"null".equals(strs[i])) {
                    head.left = new TreeNode(Integer.parseInt(strs[i]));
                    queue.add(head.left);
                }
                if (i + 1 < strs.length && !"null".equals(strs[i + 1])) {
                    head.right = new TreeNode(Integer.parseInt(strs[i + 1]));
                    queue.add(head.right);
                }
            }
            return root;
        }
    }

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    @Test
    public void test() {
        d06_2 o = new d06_2();
        Codec codec = new Codec();
        System.out.println(codec.serialize(codec.deserialize("[1,2,3,null,null,4]")));
        System.out.println(codec.serialize(codec.deserialize("[1,2,3,null,null,4,5]")));
        System.out.println(codec.serialize(codec.deserialize("[1]")));
        System.out.println(codec.serialize(codec.deserialize("[]")));
        System.out.println(codec.serialize(codec.deserialize("[1,2]")));
        System.out.println(threeSum(new int[]{-1, 0, 1, 2, -1, -4}));
        System.out.println(threeSum(new int[]{0, 0, 0, 0}));
    }
}
