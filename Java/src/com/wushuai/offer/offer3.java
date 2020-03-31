package com.wushuai.offer;

import org.junit.Test;

import java.util.*;

/**
 * <p>offer3</p>
 * <p>
 * Collections.reverse
 * Deque
 * Queue	                        Deque
 * 添加元素到队尾	    add(E e) / offer(E e)	        addLast(E e) / offerLast(E e)
 * 取队首元素并删除	    E remove() / E poll()	        E removeFirst() / E pollFirst()
 * 取队首元素但不删除	E element() / E peek()	        E getFirst() / E peekFirst()
 * 添加元素到队首	    无	                            addFirst(E e) / offerFirst(E e)
 * 取队尾元素并删除	    无	                            E removeLast() / E pollLast()
 * 取队尾元素但不删除	无	                            E getLast() / E peekLast()
 * </p>
 *
 * 面试题32 - II. 从上到下打印二叉树 II <层序遍历>
 * 面试题34. 二叉树中和为某一值的路径  -> Path  <根到叶子节点的路径集合>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-31 09:21
 */
public class offer3 {

    /**
     * 面试题31. 栈的压入、弹出序列
     *
     * @param pushed
     * @param popped
     * @return
     */
    public boolean validateStackSequences(int[] pushed, int[] popped) {
        int lp = pushed.length;
        int i = 0;
        int j = 0;
        if (lp == 0) {
            return true;
        }
        Stack<Integer> stack = new Stack<>();
        // pushed = [1,2,3,4,5], popped = [4,5,3,2,1]
        while (true) {
            if (stack.empty() || stack.peek() != popped[j]) {
                while (stack.empty() || stack.peek() != popped[j]) {
                    if (i == lp) {
                        return false;
                    }
                    stack.push(pushed[i++]);
                }
                stack.pop();
                j++;
            } else if (stack.peek() == popped[j]) {
                stack.pop();
                j++;
            }
            if (j == lp && stack.empty()) {
                return true;
            }
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
     * 面试题32 - I. 从上到下打印二叉树
     *
     * @param root
     * @return
     */
    public int[] levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        int[] a = new int[1000];
        int idx = 0;
        if (root == null) {
            return new int[]{};
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            root = queue.poll();
            a[idx++] = root.val;
            if (root.left != null) {
                queue.add(root.left);
            }
            if (root.right != null) {
                queue.add(root.right);
            }
        }
        return Arrays.copyOf(a, idx);
    }

    /**
     * 面试题32 - II. 从上到下打印二叉树 II
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder_v2(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return new ArrayList<>();
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (; size > 0; size--) {
                root = queue.poll();
                list.add(root.val);
                if (root.left != null) {
                    queue.add(root.left);
                }
                if (root.right != null) {
                    queue.add(root.right);
                }
            }
            res.add(list);
            list = new ArrayList<>();
        }
        return res;
    }

    /**
     * 面试题32 - III. 从上到下打印二叉树 III
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder_v3(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return new ArrayList<>();
        }
        int i = 0;
        queue.add(root);
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (; size > 0; size--) {
                root = queue.poll();
                list.add(root.val);
                if (root.left != null) {
                    queue.add(root.left);
                }
                if (root.right != null) {
                    queue.add(root.right);
                }
            }
            if (i % 2 == 1) {
                Collections.reverse(list);
            }
            i++;
            res.add(list);
            list = new ArrayList<>();
        }
        return res;
    }

    /**
     * 面试题33. 二叉搜索树的后序遍历序列
     * <p>
     * 后序遍历的逆序是 root->right->left
     *
     * @param postorder
     * @return
     */
    public boolean verifyPostorder(int[] postorder) {
        // 单调栈使用，单调递增的单调栈
        Deque<Integer> queue = new LinkedList<>();
        int pervElem = Integer.MAX_VALUE;
        // 逆向遍历，就是翻转的先序遍历
        for (int i = postorder.length - 1; i >= 0; i--) {
            // 左子树元素必须要小于递增栈被peek访问的元素，否则就不是二叉搜索树
            if (postorder[i] > pervElem) {
                return false;
            }
            while (!queue.isEmpty() && postorder[i] < queue.getLast()) {
                // 数组元素小于单调栈的元素了，表示往左子树走了，记录下上个根节点
                // 找到这个左子树对应的根节点，之前右子树全部弹出，不再记录，因为不可能在往根节点的右子树走了
                pervElem = queue.pollLast();
            }
            // 这个新元素入栈
            queue.addLast(postorder[i]);
        }
        return true;
    }

    List<List<Integer>> res = new ArrayList<>();
    int sum = 0;

    void Judge(TreeNode root, List<Integer> list, int sum) {
        if (root != null) {
            List<Integer> tmp  =new ArrayList<>();
            list.add(root.val);
            if (root.left == null && root.right == null) {
                if (sum + root.val == this.sum) {
                    res.add(list);
                }
            }
            tmp.addAll(list);
            Judge(root.left, tmp, sum + root.val);
            tmp = new ArrayList<>();
            tmp.addAll(list);
            Judge(root.right, tmp, sum + root.val);
        }
    }

    void Path(TreeNode root, List<Integer> list) {
        if (root != null) {
            List<Integer> tmp  =new ArrayList<>();
            list.add(root.val);
            if (root.left == null && root.right == null) {
                res.add(list);
            }
            tmp.addAll(list);
            Path(root.left, tmp);
            tmp = new ArrayList<>();
            tmp.addAll(list);
            Path(root.right, tmp);
        }
    }

    /**
     * 面试题34. 二叉树中和为某一值的路径
     *
     * @param root
     * @param sum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        this.sum = sum;
        Judge(root, new ArrayList<>(), 0);
        return this.res;
    }

    @Test
    public void test1() {
        offer3 o = new offer3();
    }
}

