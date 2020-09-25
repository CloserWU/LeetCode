package com.wushuai.daily;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>d09_3</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-09-21 9:08
 */
public class d09_3 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    /**
     * 9.21 538. 把二叉搜索树转换为累加树
     *
     * @param root
     * @return
     */
    public TreeNode convertBST(TreeNode root) {
        convert(root);
        return root;
    }

    // 比当前节点大的所有节点值的和
    int val = 0;

    void convert(TreeNode root) {
        if (root == null) {
            return;
        }
        convert(root.right);
        val += root.val;
        root.val += val;
        convert(root.left);
    }


    /**
     * 9.22 968. 监控二叉树
     * https://leetcode-cn.com/problems/binary-tree-cameras/solution/jian-kong-er-cha-shu-by-leetcode-solution/
     *
     * @param root
     * @return
     */
    public int minCameraCover(TreeNode root) {
        int[] array = dfs(root);
        return array[1];
    }

    public int[] dfs(TreeNode root) {
        if (root == null) {
            return new int[]{Integer.MAX_VALUE / 2, 0, 0};
        }
        int[] leftArray = dfs(root.left);
        int[] rightArray = dfs(root.right);
        int[] array = new int[3];
        array[0] = leftArray[2] + rightArray[2] + 1;
        array[1] = Math.min(array[0], Math.min(leftArray[0] + rightArray[1], rightArray[0] + leftArray[1]));
        array[2] = Math.min(array[0], leftArray[1] + rightArray[1]);
        return array;
    }


    /**
     * 9.23 617. 合并二叉树
     *
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 != null && t2 != null) {
            t1.val += t2.val;
            t1.left = mergeTrees(t1.left, t2.left);
            t1.right = mergeTrees(t1.right, t2.right);
            return t1;
        } else if (t1 == null && t2 == null) {
            return null;
        } else if (t2 == null) {
            t1.left = mergeTrees(t1.left, null);
            t1.right = mergeTrees(t1.right, null);
            return t1;
        } else {
            t2.left = mergeTrees(null, t2.left);
            t2.right = mergeTrees(null, t2.right);
            return t2;
        }
    }


    /**
     * 9.24 501. 二叉搜索树中的众数
     *
     * @param root
     * @return
     */
    public int[] findMode(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        int maxLength = 0;
        int[] maxlength = new int[]{0};
        findMode(root, maxlength, new int[]{Integer.MIN_VALUE}, new int[]{0});
        maxLength = maxlength[0];
        findMode(root, maxLength, new int[]{Integer.MIN_VALUE}, new int[]{0}, list);
        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
        return res;
    }

    void findMode(TreeNode root, int[] maxLength, int[] preVal, int[] preNum) {
        if (root != null) {
            findMode(root.left, maxLength, preVal, preNum);
            if (root.val == preVal[0]) {
                preNum[0]++;
            } else {
                preNum[0] = 1;
                preVal[0] = root.val;
            }
            maxLength[0] = Math.max(maxLength[0], preNum[0]);
            findMode(root.right, maxLength, preVal, preNum);
        }
    }

    void findMode(TreeNode root, int maxLength, int[] preVal, int[] preNum, List<Integer> list) {
        if (root != null) {
            findMode(root.left, maxLength, preVal, preNum, list);
            if (root.val == preVal[0]) {
                preNum[0]++;
            } else {
                preNum[0] = 1;
                preVal[0] = root.val;
            }
            if (preNum[0] == maxLength) {
                list.add(root.val);
            }
            findMode(root.right, maxLength, preVal, preNum, list);
        }
    }


    int post_idx;
    int[] postorder;
    int[] inorder;
    Map<Integer, Integer> idx_map = new HashMap<Integer, Integer>();

    public TreeNode helper(int in_left, int in_right) {
        // 如果这里没有节点构造二叉树了，就结束
        if (in_left > in_right) {
            return null;
        }

        // 选择 post_idx 位置的元素作为当前子树根节点
        int root_val = postorder[post_idx];
        TreeNode root = new TreeNode(root_val);

        // 根据 root 所在位置分成左右两棵子树
        int index = idx_map.get(root_val);

        // 下标减一
        post_idx--;
        // 构造右子树
        root.right = helper(index + 1, in_right);
        // 构造左子树
        root.left = helper(in_left, index - 1);
        return root;
    }

    /**
     * 9.25 106. 从中序与后序遍历序列构造二叉树
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        this.postorder = postorder;
        this.inorder = inorder;
        // 从后序遍历的最后一个元素开始
        post_idx = postorder.length - 1;

        // 建立（元素，下标）键值对的哈希表
        int idx = 0;
        for (Integer val : inorder) {
            idx_map.put(val, idx++);
        }

        return helper(0, inorder.length - 1);
    }


    @Test
    public void test() {

    }

}
