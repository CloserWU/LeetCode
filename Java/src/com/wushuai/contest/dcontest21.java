package com.wushuai.contest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <p>dcontest21</p>
 * <p>
 * 数组(包装类数组) 转 list
 * 1.List list = Arrays.asList(strArray);
 * 2.List<String> list = new ArrayList<String>(Arrays.asList(strArray));
 * 3.List< String> arrayList = new ArrayList<String>(strArray.length);
 * Collections.addAll(arrayList, strArray);
 * <p>
 * 状态压缩 func2
 * </p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-19 14:36
 */
public class dcontest21 {
    /**
     * 1370. 上升下降字符串
     *
     * @param s
     * @return
     */
    public String sortString(String s) {
        StringBuilder sb = new StringBuilder();
        int len = s.length();
        char[] chars = s.toCharArray();
        boolean[] visit = new boolean[len];
        Arrays.sort(chars);
        char before;
        while (len != 0) {
            before = 0;
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] > before && !visit[i]) {
                    sb.append(chars[i]);
                    before = chars[i];
                    visit[i] = true;
                    len--;
                }
            }
            before = 128;
            for (int i = chars.length - 1; i >= 0; i--) {
                if (chars[i] < before && !visit[i]) {
                    sb.append(chars[i]);
                    before = chars[i];
                    visit[i] = true;
                    len--;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 1371. 每个元音包含偶数次的最长子字符串
     * <p>
     * 状态压缩
     * <p>
     * 将5个元音字母出现次数的奇偶视为一种状态，一共有32种状态，
     * 不妨使用一个整数代表状态，第0位为1表示a出现奇数次，第一位为1表示e出现奇数次……以此类推。仅有状态0符合题意。
     * 而如果子串[0，i]与字串[0,j]状态相同，那么字串[i+1,j]的状态一定是0，
     * 因此可以记录每个状态第一次出现的位置，此后再出现该状态时相减即可。
     * 需要注意状态0首次出现的位置应该设定为-1。
     * <p>
     * 在计算状态的时候可以利用异或运算。
     * <p>
     * <p>
     * 1、cur 记录了遍历到当前下标 i 的 a、e、i、o、u 的情况，是一个前缀和的概念，并且是“异或前缀和”；
     * 把前缀异或和的信息表示在一个二进制只有 5 位的整数 cur 里，方便以后查找；
     * 2、定义成“异或前缀和”是因为中间遍历的那些元音字符相同的都抵消了，这是符合题目的要求，中间遍历的那些字符出现偶数次元音字符；
     * 子串中 a、e、i、o、u 只出现偶数次，就相当于在这个子串里异或和为 0；
     * 3、由于要记录最长的符合要求的子串的长度，于是只需要记录第一次出现的“前缀异或和”，
     * 以后再次出现的“异或和”的时候，将下标相减（注意考虑边界情况）。
     * 因此把所有的前缀异或和信息保存在一个哈希表里，由于这里所有的前缀异或和状态有限，可以用数组代替。
     * 这个哈希表的 key 是前缀异或和对应的整数 cur， value 是当前遍历到的下标，
     * 初始化的时候赋值为一个特殊值，表示当前的前缀异或和没有出现。
     *
     * @param s
     * @return
     */
    public int findTheLongestSubstring(String s) {
        int[] pre = new int[32];
        Arrays.fill(pre, Integer.MAX_VALUE);
        pre[0] = -1;
        int cur = 0;
        int ans = 0;
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'a':
                    cur ^= 1;
                    break;
                case 'e':
                    cur ^= 2;
                    break;
                case 'i':
                    cur ^= 4;
                    break;
                case 'o':
                    cur ^= 8;
                    break;
                case 'u':
                    cur ^= 16;
                    break;
                default:
                    break;
            }
            if (pre[cur] == Integer.MAX_VALUE) {
                pre[cur] = i;
            } else {
                ans = Math.max(ans, i - pre[cur]);
            }
        }
        return ans;
    }


    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    // dir true 向左右  false 向右走
    int Judge(TreeNode root, int len, boolean dir) {
        if (root != null) {
            if (dir) {
                return Judge(root.left, len + 1, false);
            } else {
                return Judge(root.right, len + 1, true);
            }
        }
        return len;
    }


    /**
     * 1372. 二叉树中的最长交错路径
     * 中序遍历树，对每个节点都Judge判断一下，他的最大交叉深度
     * 方法没问题，时间复杂度较高 TTL
     * @param root
     * @return
     */
    public int longestZigZag(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int res = 0;
        res = Math.max(Judge(root.left, 0, false), Judge(root.right, 0, true));
        res = Math.max(res, longestZigZag(root.left));
        res = Math.max(res, longestZigZag(root.right));
        return res;
    }


    int ans = 0;

    void Judge(TreeNode root, boolean isLeft, int len) {
        if (root == null) {
            ans = Math.max(len, ans);
            return;
        }
        if (isLeft) {
            Judge(root.left, false, len + 1);
            // 从下一个右点从新开始，这个点的左子树就不用再**递归**访问了，节省时间
            Judge(root.right, true, 0);
        } else {
            Judge(root.right, true, len + 1);
            // 从下一个左点从新开始，这个点的右子树就不用再**递归**访问了，节省时间
            Judge(root.left, false, 0);
        }
    }

    public int longestZigZag_v2(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 0;
        }
        Judge(root.right, true, 0);
        Judge(root.left, false, 0);
        return ans;
    }


    /**
     * 1373. 二叉搜索子树的最大键值和
     * 方法没问题 时间超限TTL   这是一种自顶向下的方法，那么越靠近底部的节点，就会被访问很多次
     * @正确的方法应该是，访问一个节点时，判断其左**子树**和右**子树**是否是BST，这个不走利用递归，同时记录最大值。
     * @这是自底向上的方法，保证每个节点访问一次
     *
     *  TODO ***值得深思***
     */
    int maxBst = 0;

    List<Integer> bst = null;

    ////////////////////begin////////////////////////////
    // 以每个节点为根，中序了一遍，靠下的节点访问很多次
    void inOrder_v0(TreeNode root) {
        if (root != null) {
            inOrder_v0(root.left);
            bst.add(root.val);
            inOrder_v0(root.right);
        }
    }

    int JudgeBST_v0(TreeNode root) {
        bst = new ArrayList<>();
        inOrder_v0(root);
        int sum = 0;
        if (bst.size() == 1) {
            return bst.get(0);
        }
        sum += bst.get(0);
        for (int i = 1; i < bst.size(); i++) {
            sum += bst.get(i);
            if (bst.get(i - 1) >= bst.get(i)) {
                return -1;
            }
        }
        return sum;
    }

    void func_v0(TreeNode root) {
        if (root != null) {
            func_v0(root.left);
            int sum;
            if ((sum = JudgeBST_v0(root)) != -1) {
                maxBst = Math.max(maxBst, sum);
            }
            func_v0(root.right);
        }
    }

    public int maxSumBST_v0(TreeNode root) {
        func_v0(root);
        return maxBst;
    }
    ////////////////////////end/////////////////////////////////

    /**
     * 无效时返回-1 WA了，因为有些测试用例更好有点为-1，所以边界值也很重要
     * @param root
     * @return
     */
    int JudgeBST(TreeNode root) {
        if (root != null) {
            int left = JudgeBST(root.left);
            int right = JudgeBST(root.right);

            if (left == 0 && right == 0) {
                // 两子树都为空
                maxBst = Math.max(root.val, maxBst);
                return root.val;
            } else if (left == 0) {
                //左子树为空
                if (root.val < root.right.val && right != Integer.MIN_VALUE) {
                    maxBst = Math.max(right + root.val, maxBst);
                    return right + root.val;
                } else {
                    return Integer.MIN_VALUE;
                }
            } else if (right == 0) {
                //右子树为空
                if (root.val > root.left.val && left != Integer.MIN_VALUE) {
                    maxBst = Math.max(left + root.val, maxBst);
                    return left + root.val;
                } else {
                    return Integer.MIN_VALUE;
                }
            }

            // 左右都不是BST
            if (left == Integer.MIN_VALUE || right == Integer.MIN_VALUE) {
                return Integer.MIN_VALUE;
            }
            if (root.val > root.left.val && root.val < root.right.val) {
                // 左右都是BST， 加上root还是BST
                maxBst = Math.max(left + right + root.val, maxBst);
                return left + right + root.val;
            } else {
                // 加上root不是BST
                return Integer.MIN_VALUE;
            }
        }
        return 0;
    }

    public int maxSumBST(TreeNode root) {
        // 不需要它的返回值，需要maxBst
        JudgeBST(root);
        return maxBst;
    }



    @Test
    public void test1() {
        dcontest21 obj = new dcontest21();
        System.out.println(obj.sortString("aaaabbbbcccc"));
        System.out.println(obj.sortString("rat"));
        System.out.println(obj.sortString("leetcode"));
        System.out.println(obj.sortString("ggggggg"));
        System.out.println(obj.sortString("spo"));
    }
}

