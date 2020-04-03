package com.wushuai.offer;

import org.junit.Test;

import java.util.*;

/**
 * <p>offer4</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-03 08:51
 */
public class offer4 {

    int res = 0;

    void dfs(String str, int n) {
        if (n >= str.length() - 1) {
            res++;
            return;
        }
        dfs(str, n + 1);
        if (str.charAt(n) - '0' > 0 && n + 1 < str.length() && (str.charAt(n) - '0') * 10 + str.charAt(n + 1) - '0' < 26) {
            dfs(str, n + 2);
        }
    }

    /**
     * 面试题46. 把数字翻译成字符串
     * <p>
     * dfs
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {
        res = 0;
        String str = String.valueOf(num);
        dfs(str, 0);
        return res;
    }

    /**
     * 面试题47. 礼物的最大价值
     * <p>
     * 基础dp
     *
     * @param grid
     * @return
     */
    public int maxValue(int[][] grid) {
        int[][] dp = new int[grid.length + 1][grid[0].length + 1];
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                dp[i][j] = Math.max(dp[i - 1][j] + grid[i - 1][j - 1], dp[i][j - 1] + grid[i - 1][j - 1]);
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }


    /**
     * 面试题48. 最长不含重复字符的子字符串
     * <p>
     * 利用map记录当前不重复字母最新出现的位置<char, pos>, 若有重复字母出现，则更新map中相应字符的pos
     * 出现重复字符时记录一下最长不重复子串 的左边界在哪里
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        int res = 0;
        int left = 0;
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (!map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), i);
            } else {
                left = Math.max(left, map.get(s.charAt(i)) + 1);
                map.put(s.charAt(i), i);
            }
            res = Math.max(res, i - left + 1);
        }
        return res;
    }

    /**
     * 面试题49. 丑数
     * <p>
     * 较大的丑数一定能够通过 某较小丑数 × 某因子 得到。
     * <p>
     * 采取用三个指针的方法，n2,n3,n5
     * n2指向的数字下一次永远*2，n3指向的数字下一次永远*3，n5指向的数字永远*5
     * 我们从2*n2 3*n3 5*n5选取最小的一个数字，作为第k个丑数
     * 如果第K个丑数==2*p2，也就是说前面0-p2个丑数*2不可能产生比第K个丑数更大的丑数了，所以p2++
     * p3,p5同
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        int[] dp = new int[n];
        int a = 0, b = 0, c = 0;
        dp[0] = 1;
        for (int i = 1; i < n; i++) {
            int n2 = dp[a] * 2, n3 = dp[b] * 3, n5 = dp[c] * 5;
            dp[i] = Math.min(Math.min(n2, n3), n5);
            if (dp[i] == n2) {
                a++;
            }
            if (dp[i] == n3) {
                b++;
            }
            if (dp[i] == n5) {
                c++;
            }
        }
        return dp[n - 1];
    }


    /**
     * 面试题50. 第一个只出现一次的字符
     *
     * @param s
     * @return
     */
    public char firstUniqChar(String s) {
        if (s.length() == 0) {
            return ' ';
        }
        Map<Character, Integer> map = new LinkedHashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (map.containsKey(s.charAt(i))) {
                map.put(s.charAt(i), map.get(s.charAt(i)) + 1);
            } else {
                map.put(s.charAt(i), 1);
            }
        }
        for (Map.Entry<Character, Integer> e : map.entrySet()) {
            if (e.getValue() == 1) {
                return e.getKey();
            }
        }
        return ' ';
    }


    /**
     * 面试题51. 数组中的逆序对
     *
     * @param nums
     * @return
     */
    public int reversePairs(int[] nums) {
        int len = nums.length;
        if (len < 2) {
            return 0;
        }
        int[] temp = new int[len];
        return reversePairs(nums, 0, len - 1, temp);
    }

    /**
     * 计算在数组 nums 的索引区间 [left, right] 内统计逆序对
     *
     * @param nums  待统计的数组
     * @param left  待统计数组的左边界，可以取到
     * @param right 待统计数组的右边界，可以取到
     * @return
     */
    private int reversePairs(int[] nums, int left, int right, int[] temp) {
        // 极端情况下，就是只有 1 个元素的时候，这里只要写 == 就可以了，不必写大于
        if (left == right) {
            return 0;
        }

        int mid = (left + right) >>> 1;

        int leftPairs = reversePairs(nums, left, mid, temp);
        int rightPairs = reversePairs(nums, mid + 1, right, temp);

        int reversePairs = leftPairs + rightPairs;
        if (nums[mid] <= nums[mid + 1]) {
            return reversePairs;
        }

        int reverseCrossPairs = mergeAndCount(nums, left, mid, right, temp);
        return reversePairs + reverseCrossPairs;

    }

    /**
     * [left, mid] 有序，[mid + 1, right] 有序
     *
     * @param nums
     * @param left
     * @param mid
     * @param right
     * @param temp
     * @return
     */
    private int mergeAndCount(int[] nums, int left, int mid, int right, int[] temp) {
        // 复制到辅助数组里，帮助我们完成统计
        for (int i = left; i <= right; i++) {
            temp[i] = nums[i];
        }
        int i = left;
        int j = mid + 1;
        int res = 0;
        for (int k = left; k <= right; k++) {
            if (i > mid) {
                // i 用完了，只能用 j
                nums[k] = temp[j];
                j++;
            } else if (j > right) {
                // j 用完了，只能用 i
                nums[k] = temp[i];
                i++;
            } else if (temp[i] <= temp[j]) {
                // 此时前数组元素出列，不统计逆序对
                nums[k] = temp[i];
                i++;
            } else {
                // 此时后数组元素出列，统计逆序对，快就快在这里，一次可以统计出一个区间的个数的逆序对
                nums[k] = temp[j];
                j++;
                res += (mid - i + 1);
            }
        }
        return res;
    }

    @Test
    public void test1() {
        offer4 o = new offer4();
        System.out.println(o.translateNum(25));
        System.out.println(o.lengthOfLongestSubstring("acbhasgfshjdafkasdhgfsacdbkh"));
        System.out.println(o.lengthOfLongestSubstring("0123456789012345678901234567"));

    }
}

