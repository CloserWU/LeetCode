package com.wushuai.tree;

import org.junit.Test;

import java.util.*;

/**
 * <p>PathSum</p>
 * <p>113</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-26 9:15
 */
public class PathSum {

    List<List<Integer>> res = new ArrayList<>();
    int sum = 0;


    /**
     * 113. 路径总和 II
     * 回溯
     *
     * @param root
     * @param sum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        this.sum = sum;
        path(new ArrayList<>(), 0, root);
        return res;
    }

    /**
     * 6ms
     *
     * @param root
     * @param list
     * @param sum
     */
    void path_v0(TreeNode root, List<Integer> list, int sum) {
        if (root != null) {
            List<Integer> tmp  =new ArrayList<>();
            list.add(root.val);
            if (root.left == null && root.right == null) {
                if (sum + root.val == this.sum) {
                    res.add(list);
                }
            }
            tmp.addAll(list);
            path_v0(root.left, tmp, sum + root.val);
            tmp = new ArrayList<>();
            tmp.addAll(list);
            path_v0(root.right, tmp, sum + root.val);
        }
    }

    /**
     * 2ms
     *
     * @param list
     * @param sSum
     * @param root
     */
    void path(List<Integer> list, int sSum, TreeNode root) {
        if (root != null) {
            // 加入状态
            list.add(root.val);
            if (sSum + root.val == sum && root.left == null && root.right == null) {
                List<Integer> tmp = new ArrayList<>(list);
                res.add(tmp);
                return;
            }
            if (root.left != null) {
                path(list, sSum + root.val, root.left);
                // 移除状态
                list.remove(list.size() - 1);
            }
            if (root.right != null) {
                path(list, sSum + root.val, root.right);
                // 移除状态
                list.remove(list.size() - 1);
            }
        }
    }

    /**
     * 1ms
     *
     * @param list
     * @param sSum
     * @param root
     */
    void path_v2(List<Integer> list, int sSum, TreeNode root) {
        if (root != null) {
            list.add(root.val);
            if (sSum + root.val == sum && root.left == null && root.right == null) {
                res.add(new ArrayList<>(list));
                // 每个节点的左右子树都访问过后，都要在list中删除，递归出口的节点不能忘
                list.remove(list.size() - 1);
                return;
            }
            path_v2(list, sSum + root.val, root.left);
            path_v2(list, sSum + root.val, root.right);
            list.remove(list.size() - 1);

        }
    }

    @Test
    public void test() {
        PathSum o = new PathSum();

    }
}
