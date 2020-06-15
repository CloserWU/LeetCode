package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest190</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-06-15 9:32
 */
public class contest190 {


    /**
     * 1455. 检查单词是否为句中其他单词的前缀
     *
     * @param sentence
     * @param searchWord
     * @return
     */
    public int isPrefixOfWord(String sentence, String searchWord) {
        int res = 0;
        char[] search = searchWord.toCharArray();
        String[] strings = sentence.split(" ");
        for (int i = 0; i < strings.length; i++) {
            char[] target = strings[i].toCharArray();
            int idx1 = 0;
            while (idx1 != search.length && idx1 != target.length) {
                if (search[idx1] == target[idx1]) {
                    idx1++;
                } else {
                    break;
                }
            }
            if (idx1 == search.length) {
                return i;
            }
        }
        return -1;
    }


    boolean isVowel(char ch) {
        switch (ch) {
            case 'a':
                return true;
            case 'e':
                return true;
            case 'i':
                return true;
            case 'o':
                return true;
            case 'u':
                return true;
            default:
                return false;
        }
    }

    /**
     * 1456. 定长子串中元音的最大数目
     *
     * @param s
     * @param k
     * @return
     */
    public int maxVowels(String s, int k) {
        char[] chars = s.toCharArray();
        int tmp = 0;
        int res = 0;
        for (int i = 0; i < k; i++) {
            if (isVowel(chars[i])) {
                tmp++;
            }
        }
        res = Integer.max(res, tmp);
        for (int i = k; i < chars.length; i++) {
            if (isVowel(chars[i - k])) {
                tmp--;
            }
            if (isVowel(chars[i])) {
                tmp++;
            }
            res = Integer.max(res, tmp);
        }
        return res;
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    List<List<Integer>> paths = new ArrayList<>();


    void getPath(TreeNode root, List<Integer> path) {
        if (root != null) {
            path.add(root.val);
            if (root.left == null && root.right == null) {
                paths.add(new ArrayList<>(path));
                path.remove(path.size() - 1);
                return;
            }
            getPath(root.left, path);
            getPath(root.right, path);
            path.remove(path.size() - 1);
        }
    }

    public int pseudoPalindromicPaths(TreeNode root) {
        getPath(root, new ArrayList<>());
        int res = 0;
        for (List<Integer> list : paths) {
            System.out.println(list);
            int even = 0;
            Map<Integer, Integer> map = new HashMap<>();
            for (Integer node : list) {
                map.put(node, map.getOrDefault(node, 0) + 1);
            }
            for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                if (entry.getValue() % 2 == 1) {
                    even++;
                }
                if (even > 1) {
                    break;
                }
            }
            if (even <= 1) {
                res++;
            }
        }
        return res;
    }


    /**
     * 1458. 两个子序列的最大点积
     * https://leetcode-cn.com/problems/max-dot-product-of-two-subsequences/solution/c-dong-tai-gui-hua-yi-dong-by-smilyt_/
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int maxDotProduct(int[] nums1, int[] nums2) {
        int[][] dp = new int[nums1.length + 1][nums2.length + 1];
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                dp[i][j] = -1000001;
            }
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = nums1[i - 1] * nums2[j - 1];
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1] + dp[i][j]);
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j]);
                dp[i][j] = Math.max(dp[i][j], dp[i][j - 1]);
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][j - 1]);
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }

    @Test
    public void test() {
        contest190 o = new contest190();

    }
}
