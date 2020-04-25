package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d5</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-21 9:31
 */
public class d5 {

    /**
     * 4.21
     * 1248. 统计「优美子数组」
     *
     * @param nums
     * @param k
     * @return
     */
    public int numberOfSubarrays(int[] nums, int k) {
        int res = 0;
        int[] arr = new int[nums.length + 2];
        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i] & 1) == 1) {
                arr[++idx] = i;
            }
        }
        arr[0] = -1;
        arr[idx + 1] = nums.length;
        for (int i = 1; i + k < idx + 2; i++) {
            res += (arr[i] - arr[i - 1]) * (arr[i + k] - arr[i + k - 1]);
        }
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
     * 4.22
     * 199. 二叉树的右视图
     *
     * @param root
     * @return
     */
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        List<List<Integer>> lists = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> row = new ArrayList<>();
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode head = queue.poll();
                row.add(head.val);
                if (head.right != null) {
                    queue.add(head.right);
                }
                if (head.left != null) {
                    queue.add(head.left);
                }
            }
            lists.add(row);
        }
        for (List<Integer> row : lists) {
            res.add(row.get(0));
        }
        return res;
    }

    /**
     * 4.23
     * 面试题 08.11. 硬币
     * <p>
     * 完全背包
     *
     * @param n
     * @return
     */
    public int waysToChange(int n) {
        int[] dp = new int[n + 1];
        int[] coins = new int[]{25, 10, 5, 1};
        dp[0] = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = coins[i]; j < dp.length; j++) {
                dp[j] = (dp[j - coins[i]] + dp[j]) % 1000000007;
            }
        }
        return dp[n];
    }

    /**
     * 4.24  aha, birthday!!!
     * 面试题51. 数组中的逆序对
     *
     * @param nums
     * @return
     */
    public int reversePairs(int[] nums) {
        if (nums.length < 2) {
            return 0;
        }
        int[] temp = new int[nums.length];
        int pairs = reversePairs(nums, 0, nums.length - 1, temp);
        System.out.println(Arrays.toString(nums));
        return pairs;
    }

    int reversePairs(int[] nums, int left, int right, int[] temp) {
        if (right == left) {
            return 0;
        }
        int mid = (left + right) >>> 1;

        int leftPairs = reversePairs(nums, left, mid, temp);
        int rightPairs = reversePairs(nums, mid + 1, right, temp);

        int allParis = leftPairs + rightPairs;
        if (nums[mid] <= nums[mid + 1]) {
            return allParis;
        }

        return allParis + mergeAndSort(nums, left, right, mid, temp);
    }

    int mergeAndSort(int[] nums, int left, int right, int mid, int[] temp) {
        int i = left, j = mid + 1, res = 0;
        if (right + 1 - left >= 0) {
            System.arraycopy(nums, left, temp, left, right + 1 - left);
        }
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                nums[k] = temp[j++];
            } else if (j > right) {
                nums[k] = temp[i++];
            } else if (temp[i] <= temp[j]) {
                nums[k] = temp[i++];
            } else {
                nums[k] = temp[j++];
                res += (mid - i + 1);
            }
        }
        return res;
    }

    /**
     * 4.25
     * 46. 全排列
     *
     * 经典回溯
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        boolean[] visit = new boolean[nums.length];
        backTrack(res, new ArrayList<>(), visit, nums);
        return res;
    }

    void backTrack(List<List<Integer>> res, List<Integer> list, boolean[] visit, int[] nums) {
        if (list.size() == visit.length) {
            // 终态
            List<Integer> tmp = new ArrayList<>(list);
            // res.add(list) 最后会返回空，引入res add的是list的<引用>，而list在回溯时会逐个清除，所以用建立<new>一个tmp
            res.add(tmp);
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (!visit[i]) {
                // 加入状态
                visit[i] = true;
                list.add(nums[i]);
                backTrack(res, list, visit, nums);
                // 还原状态
                visit[i] = false;
                list.remove(list.size() - 1);
            }
        }
    }

    @Test
    public void test() {
        d5 o = new d5();
        System.out.println(o.waysToChange(10));
        System.out.println(o.reversePairs(new int[]{5, 2, 4, 9, 8, 7, 4, 5, 3, 2, 1, 6, 85, 2154, 15, 5, 4, 21, 5, 4, 1, 2, 5, 4, 1}));

        List<List<Integer>> lists = o.permute(new int[]{1, 2, 3});
        for (List<Integer> list : lists){
            System.out.println(list);
        }
    }
}
