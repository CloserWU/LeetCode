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
                if (head.right!= null) {
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

    @Test
    public void test() {
        d5 o = new d5();

    }
}
