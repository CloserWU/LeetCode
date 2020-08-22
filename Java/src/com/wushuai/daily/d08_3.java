package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d08_3</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-21 21:13
 */
public class d08_3 {

    /**
     * 8.21 111. 二叉树的最小深度
     *
     * @param root
     * @return
     */
    public int minDepth(d08_2.TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int left = minDepth(root.left);
            int right = minDepth(root.right);
            if (left == right && left == 0) {
                return 1;
            }
            if (left == 0) {
                return right;
            } else if (right == 0) {
                return left;
            }
            return 1 + Integer.min(left, right);
        }
    }


    public final double EPSILON = 1e-6;

    /**
     * 8.22 679. 24 点游戏
     *
     * @param nums
     * @return
     */
    public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<>();
        for (int num : nums) {
            list.add((double) num);
        }
        return solve(list);
    }

    boolean solve(List<Double> list) {
        if (list.size() == 0) {
            return false;
        }
        if (list.size() == 1) {
            return Math.abs(list.get(0) - 24.0) < EPSILON;
        }
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i != j) {
                    List<Double> list1 = new ArrayList<>();
                    for (int k = 0; k < list.size(); k++) {
                        if (k != i && k != j) {
                            list1.add(list.get(k));
                        }
                    }
                    for (int k = 0; k < 4; k++) {
                        if (k < 2 && i > j) {
                            continue;
                        }
                        if (k == 0) {
                            list1.add(list.get(i) + list.get(j));
                        } else if (k == 1) {
                            list1.add(list.get(i) * list.get(j));
                        } else if (k == 2) {
                            list1.add(list.get(i) - list.get(j));
                        } else if (k == 3) {
                            if (Math.abs(list.get(j)) < EPSILON) {
                                continue;
                            }
                            list1.add(list.get(i) / list.get(j));
                        }
                        if (solve(list1)) {
                            return true;
                        }
                        list1.remove(list1.size() - 1);
                    }
                }
            }
        }
        return false;
    }

    @Test
    public void test() {
        d08_3 o = new d08_3();
        judgePoint24(new int[]{1, 1, 2, 2});
    }
}
