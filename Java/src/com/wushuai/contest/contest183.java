package com.wushuai.contest;

import org.junit.Test;
import org.omg.CORBA.INTERNAL;

import java.util.*;

/**
 * <p>contest183</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-05 20:34
 */
public class contest183 {

    public List<Integer> minSubsequence(int[] nums) {
        List<Integer> list = new ArrayList<>();
        Arrays.sort(nums);
        int res = 0, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
        }
        for (int i = nums.length - 1; i >= 0; i--) {
            res += nums[i];
            if (res > sum - res) {
                for (int j = i; j < nums.length; j++) {
                    list.add(nums[j]);
                }
                break;
            }
        }
        Collections.reverse(list);
        return list;
    }

    public int numSteps(String s) {
        StringBuilder sb = new StringBuilder(s);
        sb.reverse();
        int res = 0;
        while (!"1".equals(sb.toString())) {
            res++;
            if (sb.charAt(0) == '1') {
                int carry = 0;
                sb.setCharAt(0, '2');
                for (int i = 0; i < sb.length(); i++) {
                    char ch = (char) ((int) sb.charAt(i) + carry);
                    if (ch >= '2') {
                        sb.setCharAt(i, (char) (ch - 2));
                        carry = 1;
                    } else {
                        sb.setCharAt(i, ch);
                        carry = 0;
                        break;
                    }
                }
                while (carry != 0) {
                    sb.append('1');
                    carry--;
                }
            } else {
                sb = new StringBuilder(sb.substring(1));
            }
        }
        return res;
    }

    class Node {
        char ch;
        int num;
        Node(char ch, int num) {
            this.ch = ch;
            this.num = num;
        }
    }

    public String longestDiverseString(int a, int b, int c) {
        StringBuilder sb = new StringBuilder();
        List<Node> list = new ArrayList<>();
        list.add(new Node('a', a));
        list.add(new Node('b', b));
        list.add(new Node('c', c));
        while (true) {
            list.sort(new Comparator<Node>() {
                @Override
                public int compare(Node o1, Node o2) {
                    return Integer.compare(o2.num, o1.num);
                }
            });
            if (sb.length() >= 2 &&
                    sb.charAt(sb.length() - 1) == list.get(0).ch &&
                    sb.charAt(sb.length() - 2) == list.get(0).ch) {
                if (list.get(1).num > 0) {
                    sb.append(list.get(1).ch);
                    list.get(1).num -= 1;
                } else {
                    break;
                }
            } else {
                if (list.get(0).num > 0) {
                    sb.append(list.get(0).ch);
                    list.get(0).num -= 1;
                } else {
                    break;
                }
            }
        }
        return sb.toString();
    }

    /**
     * 我们用一个数组 dp 来表示“在只剩下第 i 堆到最后一堆石子时，当前玩家最多能拿多少分”。
     * 假如算出了这个 dp 数组，那么最终答案就是判断 dp[0] 和（分数总和-dp[0]）之间的大小关系即可。
     *
     * 我们倒着计算这个 dp 数组。首先计算边界情况：dp[n-1] = s[n-1]，因为最后一个堆的时候只能拿走。
     *
     * 对于其他的 i，我们可以这样思考：当前你的选择有 “取走一、二、三堆”，结
     * 果就是给对方留下了 dp[i+1] dp[i+2] dp[i+3] 对应的情况。
     * 也就是对方能够得到的最高分就是 dp[i+1] dp[i+2] dp[i+3] 中的一个，
     * 而我们能得到的分数就是剩下的所有分数减去对方能拿到的分数。为了让我们拿到的更多，就得让对方拿到的最少。
     *
     * 因此有 dp[i]= sum{i,n} - min{dp[i+1],dp[i+2],dp[i+3]}，分别对应取走一堆、两堆、三堆石子的情况。
     *
     * 通过 dp[n-1] 和 递推式，利用动态规划我们就能算出 dp[0]，从而得到答案了。
     *
     * 博弈论题第一次见肯定懵逼。本题属于信息透明的平等博弈，是博弈论中最基础的一种，
     * 思路就是倒着从游戏的最后一步开始反着算，对每个状态计算“玩家从该状态开始能不能获胜/最多能拿多少分”，
     * 用类似动态规划的思想一直算到第一步。

     * @param stoneValue
     * @return
     */
    public String stoneGameIII(int[] stoneValue) {
        int[] dp = new int[stoneValue.length + 3];
        int sum = 0;
        for (int i = stoneValue.length - 1; i >= 0; i--) {
            dp[i] = Integer.MIN_VALUE;
            sum += stoneValue[i];
            for (int j = 1; j <= 3; j++) {
                dp[i] = Integer.max(dp[i], sum - dp[i + j]);
            }
        };
        if (sum - dp[0] == dp[0]) {
            return "Tie";
        } else if (sum - dp[0] > dp[0]) {
            return "Bob";
        } else {
            return "Alice";
        }
    }

    @Test
    public void test1() {
        contest183 o = new contest183();
//        System.out.println(o.numSteps("1101"));
//        System.out.println(o.numSteps("10"));
//        System.out.println(o.numSteps("1"));
        System.out.println(o.longestDiverseString(1,1,7));
        System.out.println(o.longestDiverseString(2,2,1));
        System.out.println(o.longestDiverseString(7,1,0));
        System.out.println(o.longestDiverseString(5,6,7));
        System.out.println(o.longestDiverseString(2,6,7));
        System.out.println(o.longestDiverseString(9,1,8));
        System.out.println(o.longestDiverseString(0,0,8));
        System.out.println(o.longestDiverseString(2,10,8));

        System.out.println(o.stoneGameIII(new int[]{-1,-2,-3}));
    }
}

