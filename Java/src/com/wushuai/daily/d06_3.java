package com.wushuai.daily;

import org.junit.Test;

import java.util.Arrays;

/**
 * <p>d06_3</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-22 11:24
 */
public class d06_3 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    /**
     * 6.21 124. 二叉树中的最大路径和
     *
     * @param root
     * @return
     */
    public int maxPathSum(TreeNode root) {
        int[] res = new int[1];
        res[0] = Integer.MIN_VALUE;
        maxPathSum(root, res);
        return res[0];
    }

    int maxPathSum(TreeNode root, int[] res) {
        if (root == null) {
            return 0;
        }
        // 递归计算左右子节点的最大贡献值
        // 只有在最大贡献值大于 0 时，才会选取对应子节点
        int left = Math.max(maxPathSum(root.left, res), 0);
        int right = Math.max(maxPathSum(root.right, res), 0);
        // 节点的最大路径和取决于该节点的值与该节点的左右子节点的最大贡献值
        int max = root.val + left + right;
        // 更新答案
        res[0] = Math.max(res[0], max);
        // 返回节点的最大贡献值
        return root.val + Math.max(left, right);
    }


    /**
     * 6.22 面试题 16.18. 模式匹配
     * https://leetcode-cn.com/problems/pattern-matching-lcci/solution/mo-shi-pi-pei-by-leetcode-solution/
     *
     * @param pattern
     * @param value
     * @return
     */
    public boolean patternMatching(String pattern, String value) {
        int count_a = 0, count_b = 0;
        for (char ch: pattern.toCharArray()) {
            if (ch == 'a') {
                ++count_a;
            } else {
                ++count_b;
            }
        }
        if (count_a < count_b) {
            int temp = count_a;
            count_a = count_b;
            count_b = temp;
            char[] array = pattern.toCharArray();
            for (int i = 0; i < array.length; i++) {
                array[i] = array[i] == 'a' ? 'b' : 'a';
            }
            pattern = new String(array);
        }
        if (value.length() == 0) {
            return count_b == 0;
        }
        if (pattern.length() == 0) {
            return false;
        }
        for (int len_a = 0; count_a * len_a <= value.length(); ++len_a) {
            int rest = value.length() - count_a * len_a;
            if ((count_b == 0 && rest == 0) || (count_b != 0 && rest % count_b == 0)) {
                int len_b = (count_b == 0 ? 0 : rest / count_b);
                int pos = 0;
                boolean correct = true;
                String value_a = "", value_b = "";
                for (char ch: pattern.toCharArray()) {
                    if (ch == 'a') {
                        String sub = value.substring(pos, pos + len_a);
                        if (value_a.length() == 0) {
                            value_a = sub;
                        } else if (!value_a.equals(sub)) {
                            correct = false;
                            break;
                        }
                        pos += len_a;
                    } else {
                        String sub = value.substring(pos, pos + len_b);
                        if (value_b.length() == 0) {
                            value_b = sub;
                        } else if (!value_b.equals(sub)) {
                            correct = false;
                            break;
                        }
                        pos += len_b;
                    }
                }
                if (correct && !value_a.equals(value_b)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 6.23 67. 二进制求和
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        StringBuilder sb1 = new StringBuilder(a).reverse();
        StringBuilder sb2 = new StringBuilder(b).reverse();
        StringBuilder res = new StringBuilder();
        int add = 0;
        int i = 0, j = 0;
        while (i < sb1.length() && j < sb2.length()) {
            int x = sb1.charAt(i) - '0' + sb2.charAt(j) - '0' + add;
            add = 0;
            if (x > 1) {
                add = 1;
                x -= 2;
            }
            res.append(x);
            i++;
            j++;
        }
        while (i < sb1.length()) {
            int x = sb1.charAt(i++) - '0' + add;
            add = 0;
            if (x > 1) {
                add = 1;
                x -= 2;
            }
            res.append(x);
        }
        while (j < sb2.length()) {
            int x = sb2.charAt(j++) - '0' + add;
            add = 0;
            if (x > 1) {
                add = 1;
                x -= 2;
            }
            res.append(x);
        }
        if (add != 0) {
            res.append(1);
            add = 0;
        }
        return res.reverse().toString();
    }


    /**
     * 6.24 16. 最接近的三数之和
     *
     * @param nums
     * @param target
     * @return
     */
    public int threeSumClosest(int[] nums, int target) {
        int res = Integer.MAX_VALUE;
        int ans = 0;
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1, right = nums.length - 1;
            while (left < right) {
                int add = nums[i] + nums[left] + nums[right];
                if (Math.abs(target - add) < res) {
                    ans = add;
                    res = Math.abs(target - add);
                }
                if (add > target) {
                    right--;
                } else if (add < target) {
                    left++;
                } else {
                    return target;
                }
            }
        }
        return ans;
    }


    @Test
    public void test() {
        d06_3 o = new d06_3();
        System.out.println(threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
    }
}
