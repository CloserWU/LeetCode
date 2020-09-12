package com.wushuai.daily;

import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * <p>d09_2</p>
 * <p>
 * 9.11  9.10  9.09  9.08
 * </p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-09-11 16:35
 */
public class d09_2 {


    /**
     * 9.11 216. 组合总和 III
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<List<Integer>> res = new ArrayList<>();
        getCombination(res, k, n, 0, arr, new ArrayList<>());
        return res;
    }


    void getCombination(List<List<Integer>> res, int k, int n, int idx, int[] arr, List<Integer> list) {
        if (n == 0 && list.size() == k) {
            res.add(new ArrayList<>(list));
        }
        if (n < 0 || idx == arr.length || list.size() > k) {
            return;
        }
        for (int i = idx; i < arr.length; i++) {
            list.add(arr[i]);
            getCombination(res, k, n - arr[i], i + 1, arr, list);
            list.remove(list.size() - 1);
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

    /**
     * 9.12 637. 二叉树的层平均值
     *
     * @param root
     * @return
     */
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> res = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            long sum = 0;
            int count = 0;
            List<TreeNode> tmp = new ArrayList<>();
            while (!queue.isEmpty()) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    tmp.add(node.left);
                }
                if (node.right != null) {
                    tmp.add(node.right);
                }
                sum += (long) node.val;
                count++;
            }
            res.add((double) sum / count);
            queue.addAll(tmp);
        }
        return res;
    }

    @Test
    public void test() {
        d09_2 o = new d09_2();

    }
}
