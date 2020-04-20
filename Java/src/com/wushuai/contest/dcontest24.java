package com.wushuai.contest;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>dcontest24</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-20 8:24
 */
public class dcontest24 {

    /**
     * 5372. 逐步求和得到正数的最小值
     *
     * @param nums
     * @return
     */
    public int minStartValue(int[] nums) {
        int min = Integer.MAX_VALUE;
        int add = 0;
        for (int i = 0; i < nums.length; i++) {
            add += nums[i];
            min = Integer.min(min, add);
        }
        if (min >= 1) {
            return 1;
        }
        return Math.abs(min) + 1;
    }

    /**
     * 5373. 和为 K 的最少斐波那契数字数目
     * 斐波那契数列 46项开始 爆int
     *
     * @param k
     * @return
     */
    public int findMinFibonacciNumbers(int k) {
        int[] fab = new int[45];
        fab[0] = 1;
        fab[1] = 1;
        for (int i = 2; i < 45; i++) {
            fab[i] = fab[i - 1] + fab[i - 2];
        }
        int res = 0;
        for (int i = 44; i >= 0; i--) {
            if (k == 0) {
                break;
            }
            if (fab[i] <= k) {
                k -= fab[i];
                res++;
            }
        }
        return res;
    }

    void dfs(List<String> list, int len, int end, StringBuilder sb, char pre) {
        if (len < end) {
            if (pre == 'a') {
                dfs(list, len + 1, end, sb.append('b'), 'b');
                sb.setLength(sb.length() - 1);
                dfs(list, len + 1, end, sb.append('c'), 'c');
                sb.setLength(sb.length() - 1);
            } else if (pre == 'b') {
                dfs(list, len + 1, end, sb.append('a'), 'a');
                sb.setLength(sb.length() - 1);
                dfs(list, len + 1, end, sb.append('c'), 'c');
                sb.setLength(sb.length() - 1);
            } else if (pre == 'c') {
                dfs(list, len + 1, end, sb.append('a'), 'a');
                sb.setLength(sb.length() - 1);
                dfs(list, len + 1, end, sb.append('b'), 'b');
                sb.setLength(sb.length() - 1);
            } else {
                dfs(list, len + 1, end, sb.append('a'), 'a');
                sb.setLength(sb.length() - 1);
                dfs(list, len + 1, end, sb.append('b'), 'b');
                sb.setLength(sb.length() - 1);
                dfs(list, len + 1, end, sb.append('c'), 'c');
                sb.setLength(sb.length() - 1);
            }
        } else {
            list.add(sb.toString());
        }
    }


    /**
     * 5374. 长度为 n 的开心字符串中字典序第 k 小的字符串
     *
     * @param n
     * @param k
     * @return
     */
    public String getHappyString(int n, int k) {
        List<String> list = new ArrayList<>();
        dfs(list, 0, n, new StringBuilder(), ' ');
        Collections.sort(list);
        if (list.size() >= k) {
            return list.get(k - 1);
        } else {
            return "";
        }
    }

    /**
     * 5375. 恢复数组
     *
     * @param s
     * @param k
     * @return
     */
    public int numberOfArrays(String s, int k) {
        int temp = 1000000007;
        long[] dp = new long[s.length() + 1];
        dp[s.length()] = 1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) == '0') {
                dp[i] = 0;
                continue;
            }
            long parse = 0;
            for (int j = i; j < s.length(); j++) {
                parse = s.charAt(j) - '0' + parse * 10;
                if (parse <= k) {
                    dp[i] = (dp[i] + dp[j + 1]) % temp;
                } else {
                    break;
                }
            }
        }
        return (int) dp[0];
    }

    @Test
    public void test() {
        dcontest24 o = new dcontest24();
        System.out.println(o.findMinFibonacciNumbers(1));
        System.out.println(o.findMinFibonacciNumbers(7));
        System.out.println(o.findMinFibonacciNumbers(10));
        System.out.println(o.findMinFibonacciNumbers(19));
        System.out.println(o.findMinFibonacciNumbers(19545451));

        System.out.println(o.getHappyString(1, 3));
        System.out.println(o.getHappyString(1, 4));
        System.out.println(o.getHappyString(3, 9));
        System.out.println(o.getHappyString(2, 7));
        System.out.println(o.getHappyString(10, 100));
    }
}
