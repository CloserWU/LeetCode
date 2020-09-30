package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

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


    /**
     * 9.26 113. 路径总和 II
     *
     * @param root
     * @param sum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(root, new ArrayList<>(), sum, res);
        return res;
    }

    void dfs(TreeNode root, List<Integer> list, int sum, List<List<Integer>> res) {
        if (root == null || root.val > sum) {
            return;
        }
        if (sum - root.val == 0 && root.left == null && root.right == null) {
            list.add(root.val);
            res.add(new ArrayList<>(list));
            list.remove(list.size() - 1);
        } else {
            list.add(root.val);
            dfs(root.left, list, sum - root.val, res);
            dfs(root.right, list, sum - root.val, res);
            list.remove(list.size() - 1);
        }
    }


    /**
     * 9.27 235. 二叉搜索树的最近公共祖先
     *
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        dfs(root, p, q);
        return target;
    }

    TreeNode target = null;


    boolean dfs(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return false;
        }
        boolean left = dfs(root.left, p, q);
        boolean right = dfs(root.right, p, q);
        if (target != null) {
            return false;
        }
        if (left && right) {
            target = root;
        }
        if ((root == p || root == q) && (left || right)) {
            target = root;
        }
        if (root == p || root == q) {
            return true;
        }
        return left || right;
    }


    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }


    /**
     * 9.28 117. 填充每个节点的下一个右侧节点指针 II
     *
     * @param root
     * @return
     */
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Node> level = new ArrayList<>(queue.size());
            while (!queue.isEmpty()) {
                level.add(queue.poll());
            }
            for (int i = 0; i < level.size(); i++) {
                if (i > 0) {
                    level.get(i - 1).next = level.get(i);
                }
                if (level.get(i).left != null) {
                    queue.add(level.get(i).left);
                }
                if (level.get(i).right != null) {
                    queue.add(level.get(i).right);
                }
            }
        }
        return root;
    }


    /**
     * 9.29 145. 二叉树的后序遍历
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        // pre是上次访问到的节点，如果root.right == pre 说明它的右子树以及访问完毕，可以访问root了
        TreeNode pre = null;
        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();
            // root保证左子树是空的
            if (root.right == null || root.right == pre) {
                res.add(root.val);
                pre = root;
                root = null;
            } else {
                stack.push(root);
                root = root.right;
            }
        }
        return res;
    }


    /**
     * 9.30 701. 二叉搜索树中的插入操作
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        TreeNode tmp = root;
        while (root != null) {
            if (root.val > val) {
                if (root.left == null) {
                    root.left = new TreeNode(val);
                    break;
                }
                root = root.left;
            } else {
                if (root.right == null) {
                    root.right = new TreeNode(val);
                    break;
                }
                root = root.right;
            }
        }
        return tmp;
    }


    @Test
    public void test() {

    }

}
