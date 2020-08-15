package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d07_2</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-11 19:38
 */
public class d07_2 {


    /**
     * 7.11 315. 计算右侧小于当前元素的个数
     * 树状数组  too hard
     *
     * @param nums
     * @return
     */
    public List<Integer> countSmaller(int[] nums) {
        return null;
    }


    /**
     * 7.12 174. 地下城游戏
     * https://leetcode-cn.com/problems/dungeon-game/solution/di-xia-cheng-you-xi-by-leetcode-solution/
     * dp
     *
     * @param dungeon
     * @return
     */
    public int calculateMinimumHP(int[][] dungeon) {
        int row = dungeon.length;
        int col = dungeon[0].length;
        int[][] dp = new int[row + 1][col + 1];
        for (int[] ints : dp) {
            Arrays.fill(ints, Integer.MAX_VALUE);
        }
        dp[row][col - 1] = 1;
        dp[row - 1][col] = 1;
        for (int i = row - 1; i >= 0; i--) {
            for (int j = col - 1; j >= 0; j--) {
                int minn = Integer.min(dp[i + 1][j], dp[i][j + 1]);
                dp[i][j] = Integer.max(minn - dungeon[i][j], 1);
            }
        }
        return dp[0][0];
    }


    /**
     * 7.13 350. 两个数组的交集 II
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        Map<Integer, Integer> map1 = new HashMap<>();
        Map<Integer, Integer> map2 = new HashMap<>();
        for (int i : nums1) {
            map1.put(i, map1.getOrDefault(i, 0) + 1);
        }
        for (int i : nums2) {
            map2.put(i, map2.getOrDefault(i, 0) + 1);
        }
        int i = 0;
        int[] res = new int[Integer.min(nums1.length, nums2.length)];
        for (Map.Entry<Integer, Integer> e : map1.entrySet()) {
            int key = e.getKey();
            if (map2.containsKey(key)) {
                int epoch = Integer.min(e.getValue(), map2.get(key));
                for (int j = 0; j < epoch; j++) {
                    res[i++] = key;
                }
            }
        }
        return Arrays.copyOf(res, i);
    }


    /**
     * 7.14 120. 三角形最小路径和
     *
     * @param triangle
     * @return
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        for (int i = 1; i < triangle.size(); i++) {
            int n1 = triangle.get(i - 1).get(0);
            int n2 = triangle.get(i - 1).get(triangle.get(i - 1).size() - 1);
            triangle.get(i).set(0, triangle.get(i).get(0) + n1);
            triangle.get(i).set(triangle.get(i).size() - 1, triangle.get(i).get(triangle.get(i).size() - 1) + n2);
        }
        for (int i = 2; i < triangle.size(); i++) {
            for (int j = 1; j < triangle.get(i).size() - 1; j++) {
                int up = triangle.get(i - 1).get(j);
                int upAndLeft = triangle.get(i - 1).get(j - 1);
                int self = triangle.get(i).get(j);
                triangle.get(i).set(j, Integer.min(up, upAndLeft) + self);
            }
        }
        List<Integer> lastRow = triangle.get(triangle.size() - 1);
        int res = Integer.MAX_VALUE;
        for (Integer num : lastRow) {
            res = Integer.min(res, num);
        }
        return res;
    }


    /**
     * 7.15 96. 不同的二叉搜索树
     *
     * @param n
     * @return
     */
    public int numTrees(int n) {
        long C = 1;
        for (int i = 0; i < n; ++i) {
            C = C * 2 * (2 * i + 1) / (i + 2);
        }
        return (int) C;
    }


    /**
     * 7.16 785. 判断二分图
     * <p>
     * BFS + 贪心
     * 将图分为两种颜色，两种颜色相互间隔开，即为二分图
     * 每次bfs都将未访问到的点置为与上次相反的颜色
     *
     * @param graph
     * @return
     */
    public boolean isBipartite(int[][] graph) {
        // 0 未访问  1 蓝色  -1 红色
        int[] color = new int[graph.length];
        for (int i = 0; i < color.length; i++) {
            if (color[i] == 0) {
                boolean b = bfs(graph, i, color);
                if (!b) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean bfs(int[][] graph, int idx, int[] color) {
        Queue<Integer> queue = new LinkedList<>();
        color[idx] = 1;
        queue.add(idx);
        while (!queue.isEmpty()) {
            int preNode = queue.poll();
            int preColor = color[preNode];
            int[] edges = graph[preNode];
            for (int i = 0; i < edges.length; i++) {
                if (color[graph[preNode][i]] != preColor) {
                    if (color[graph[preNode][i]] == 0) {
                        color[graph[preNode][i]] = -preColor;
                        queue.add(graph[preNode][i]);
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }


    /**
     * 7.17 35. 搜索插入位置
     *
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == target) {
                return i;
            }
            if (i == nums.length - 1 && nums[i] < target) {
                return i + 1;
            }
            if (i > 0 && nums[i] > target && nums[i - 1] < target) {
                return i;
            }
        }
        return 0;
    }


    /**
     * 7.18 97. 交错字符串
     * 定义 f(i, j)表示 s1的前 i 个元素和 s2的前 jj个元素是否能交错组成 s3的前 i + j个元素
     * https://leetcode-cn.com/problems/interleaving-string/solution/jiao-cuo-zi-fu-chuan-by-leetcode-solution/
     *
     * @param s1
     * @param s2
     * @param s3
     * @return
     */
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        boolean[][] dp = new boolean[s1.length() + 1][s2.length() + 1];
        dp[0][0] = true;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[0].length; j++) {
                int t = i + j - 1;
                if (i > 0) {
                    dp[i][j] = dp[i][j] || (dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(t));
                }
                if (j > 0) {
                    dp[i][j] = dp[i][j] || (dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(t));
                }
            }
        }
        return dp[s1.length()][s2.length()];
    }

    @Test
    public void test() {
        d07_2 o = new d07_2();
        List<List<Integer>> triangle = new ArrayList<>();
        List<Integer> l1 = Arrays.asList(2);
        List<Integer> l2 = Arrays.asList(3, 4);
        List<Integer> l3 = Arrays.asList(6, 5, 7);
        List<Integer> l4 = Arrays.asList(4, 1, 8, 3);
        triangle.add(l1);
        triangle.add(l2);
        triangle.add(l3);
        triangle.add(l4);
        System.out.println(minimumTotal(triangle));
    }
}
