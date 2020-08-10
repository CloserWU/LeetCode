package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d06_1</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-05 9:14
 */
public class d06_1 {

    /**
     * 6.01 1431. 拥有最多糖果的孩子
     *
     * @param candies
     * @param extraCandies
     * @return
     */
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int max = 0;
        List<Boolean> res = new ArrayList<>(Collections.nCopies(candies.length, false));
        for (int i = 0; i < candies.length; i++) {
            max = Integer.max(max, candies[i]);
        }
        for (int i = 0; i < candies.length; i++) {
            if (candies[i] + extraCandies >= max) {
                res.set(i, true);
            }
        }
        return res;
    }


    /**
     * 6.02 剑指 Offer 64. 求1+2+…+n
     *
     * @param n
     * @return
     */
    public int sumNums(int n) {
        if (n == 0) {
            return 0;
        }
        return n + sumNums(n - 1);
    }


    /**
     * 6.03 837. 新21点
     * pass, 无意义题
     *
     * @param N
     * @param K
     * @param W
     * @return
     */
    public double new21Game(int N, int K, int W) {
        if (K == 0) {
            return 1.0;
        }
        double[] dp = new double[K + W];
        for (int i = K; i <= N && i < K + W; i++) {
            dp[i] = 1.0;
        }
        dp[K - 1] = 1.0 * Math.min(N - K + 1, W) / W;
        for (int i = K - 2; i >= 0; i--) {
            dp[i] = dp[i + 1] - (dp[i + W + 1] - dp[i + 1]) / W;
        }
        return dp[0];
    }


    /**
     * 6.04 238. 除自身以外数组的乘积
     *
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int[] res = new int[nums.length];
        res[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] * nums[i - 1];
        }
        int r = 1;
        for (int i = nums.length - 1; i >= 0; i--) {
            res[i] *= r;
            r *= nums[i];
        }
        return res;
    }


    /**
     * 6.05 剑指 Offer 29. 顺时针打印矩阵
     *
     * @param matrix
     * @return
     */
    public int[] spiralOrder(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }
        int row = matrix.length;
        int col = matrix[0].length;
        int[] res = new int[row * col];
        if (row == 1) {
            res = Arrays.copyOf(matrix[0], col);
            return res;
        }
        if (col == 1) {
            for (int i = 0; i < row; i++) {
                res[i] = matrix[i][0];
            }
            return res;
        }
        int i = 0, x = 0, y = 0;
        int[] dx = new int[]{0, 1, 0, -1};
        int[] dy = new int[]{1, 0, -1, 0};
        int p = 0;
        while (i != row * col) {
            res[i] = matrix[x][y];
            matrix[x][y] = Integer.MIN_VALUE;
            i++;
            if (x == row - 1 && y == 0) {
                p++;
            }
            if (x == row - 1 && y == col - 1) {
                p++;
            }
            if (x == 0 && y == col - 1) {
                p++;
            }
            if (matrix[x + dx[p % 4]][y + dy[p % 4]] == Integer.MIN_VALUE) {
                p++;
            }
            x += dx[p % 4];
            y += dy[p % 4];

        }
        return res;
    }


    /**
     * 6.06 128. 最长连续序列
     * https://leetcode-cn.com/problems/longest-consecutive-sequence/solution/zui-chang-lian-xu-xu-lie-by-leetcode-solution/
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int res = 0;
        for (int num : set) {
            if (!set.contains(num - 1)) {
                int tmp = 1;
                while (set.contains(num + 1)) {
                    tmp++;
                    num++;
                }
                res = Integer.max(res, tmp);
            }
        }
        return res;
    }


    /**
     * 6.07 126. 单词接龙 II
     * 暴力回溯，TTL
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        List<List<String>> res = new ArrayList<>();
        List<String> res1 = new ArrayList<>();
        res1.add(beginWord);
        boolean[] visit = new boolean[wordList.size()];
        for (int i = 0; i < wordList.size(); i++) {
            if (isLadder(beginWord, wordList.get(i))) {
                res1.add(wordList.get(i));
                visit[i] = true;
                dfs(res, wordList.get(i), endWord, wordList, res1, visit);
                visit[i] = false;
                res1.remove(res1.size() - 1);
            }
        }

        int minLen = Integer.MAX_VALUE;
        for (List<String> list : res) {
            minLen = Integer.min(minLen, list.size());
        }
        List<List<String>> res0 = new ArrayList<>();
        for (List<String> list : res) {
            if (list.size() == minLen) {
                res0.add(list);
            }
        }
        return res0;
    }

    void dfs(List<List<String>> res, String beginWord, String endWord, List<String> wordList, List<String> res1, boolean[] visit) {
        if (beginWord.equals(endWord)) {
            List<String> tmp = new ArrayList<>(res1);
            res.add(tmp);
        }
        for (int i = 0; i < wordList.size(); i++) {
            if (!visit[i] && isLadder(beginWord, wordList.get(i))) {
                res1.add(wordList.get(i));
                visit[i] = true;
                dfs(res, wordList.get(i), endWord, wordList, res1, visit);
                visit[i] = false;
                res1.remove(res1.size() - 1);
            }
        }
    }

    boolean isLadder(String a, String b) {
        int n = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                n++;
            }
            if (n > 1) {
                return false;
            }
        }
        return n == 1;
    }


    /**
     * 6.07 126. 单词接龙 II
     * 建无向图，两个相差一的字符串为一条边
     * 然后dijkstra
     * https://leetcode-cn.com/problems/word-ladder-ii/solution/dan-ci-jie-long-ii-by-leetcode-solution/
     *
     * @param beginWord
     * @param endWord
     * @param wordList
     * @return
     */
    public List<List<String>> findLadders_v1(String beginWord, String endWord, List<String> wordList) {
        final int INF = 1 << 20;
        Map<String, Integer> wordId = new HashMap<>();// 单词到id的映射
        ArrayList<String> idWord = new ArrayList<>(); // id到单词的映射
        ArrayList<Integer>[] edges; // 图的边
        int id = 0;
        // 将wordList所有单词加入wordId中 相同的只保留一个 // 并为每一个单词分配一个id
        for (String word : wordList) {
            if (!wordId.containsKey(word)) {
                wordId.put(word, id++);
                idWord.add(word);
            }
        }
        // 若endWord不在wordList中 则无解
        if (!wordId.containsKey(endWord)) {
            return new ArrayList<>();
        }
        // 把beginWord也加入wordId中
        if (!wordId.containsKey(beginWord)) {
            wordId.put(beginWord, id++);
            idWord.add(beginWord);
        }

        // 初始化存边用的数组
        edges = new ArrayList[idWord.size()];
        for (int i = 0; i < idWord.size(); i++) {
            edges[i] = new ArrayList<>();
        }
        // 添加边
        for (int i = 0; i < idWord.size(); i++) {
            for (int j = i + 1; j < idWord.size(); j++) {
                // 若两者可以通过转换得到 则在它们间建一条无向边
                if (transformCheck(idWord.get(i), idWord.get(j))) {
                    edges[i].add(j);
                    edges[j].add(i);
                }
            }
        }

        int dest = wordId.get(endWord); // 目的ID
        List<List<String>> res = new ArrayList<>(); // 存答案
        int[] cost = new int[id]; // 到每个点的代价
        for (int i = 0; i < id; i++) {
            cost[i] = INF; // 每个点的代价初始化为无穷大
        }

        // 将起点加入队列 并将其cost设为0
        Queue<ArrayList<Integer>> q = new LinkedList<>();
        ArrayList<Integer> tmpBegin = new ArrayList<>();
        tmpBegin.add(wordId.get(beginWord));
        q.add(tmpBegin);
        cost[wordId.get(beginWord)] = 0;

        // 开始广度优先搜索
        while (!q.isEmpty()) {
            ArrayList<Integer> now = q.poll();
            int last = now.get(now.size() - 1); // 最近访问的点
            if (last == dest) { // 若该点为终点则将其存入答案res中
                ArrayList<String> tmp = new ArrayList<>();
                for (int index : now) {
                    tmp.add(idWord.get(index)); // 转换为对应的word
                }
                res.add(tmp);
            } else { // 该点不为终点 继续搜索
                for (int i = 0; i < edges[last].size(); i++) {
                    int to = edges[last].get(i);
                    // 此处<=目的在于把代价相同的不同路径全部保留下来
                    if (cost[last] + 1 <= cost[to]) {
                        cost[to] = cost[last] + 1;
                        // 把to加入路径中
                        ArrayList<Integer> tmp = new ArrayList<>(now);
                        tmp.add(to);
                        q.add(tmp); // 把这个路径加入队列
                    }
                }
            }
        }
        return res;
    }

    /**
     * 两个字符串是否可以通过改变一个字母后相等
     *
     * @param str1
     * @param str2
     * @return
     */
    boolean transformCheck(String str1, String str2) {
        int differences = 0;
        for (int i = 0; i < str1.length() && differences < 2; i++) {
            if (str1.charAt(i) != str2.charAt(i)) {
                ++differences;
            }
        }
        return differences == 1;
    }


    /**
     * 6.08 990. 等式方程的可满足性
     *
     * @param equations
     * @return
     */
    public boolean equationsPossible(String[] equations) {
        int[] graph = new int[26];
        int[] height = new int[26];
        for (int i = 0; i < 26; i++) {
            graph[i] = i;
            height[i] = 0;
        }
        Set<Integer> exist = new HashSet<>();
        for (String str : equations) {
            char op = str.charAt(1);
            if (op == '=') {
                int x = str.charAt(0) - 'a';
                int y = str.charAt(3) - 'a';
                union(x, y, graph, height);
            }
        }
        for (String str : equations) {
            char op = str.charAt(1);
            if (op == '!') {
                int x = str.charAt(0) - 'a';
                int y = str.charAt(3) - 'a';
                if (findRoot(x, graph) == findRoot(y, graph)) {
                    return false;
                }
            }
        }
        return true;
    }


    int findRoot(int x, int[] graph) {
        while (x != graph[x]) {
            x = graph[x];
        }
        return x;
    }

    void union(int x, int y, int[] graph, int[] height) {
        x = findRoot(x, graph);
        y = findRoot(y, graph);
        if (x != y) {
            if (height[x] > height[y]) {
                graph[y] = x;
            } else if (height[x] < height[y]) {
                graph[x] = y;
            } else {
                graph[x] = y;
                height[y]++;
            }
        }
    }


    /**
     * 6.09 剑指 Offer 46. 把数字翻译成字符串
     *
     * @param num
     * @return
     */
    public int translateNum(int num) {
        char[] chars = String.valueOf(num).toCharArray();
        dfs(chars, 0);
        return translateNumRes;
    }


    int translateNumRes = 0;

    void dfs(char[] chars, int idx) {
        if (idx == chars.length) {
            translateNumRes++;
            return;
        }
        dfs(chars, idx + 1);
        if (idx + 1 < chars.length && chars[idx] - '0' > 0) {
            int num = (chars[idx] - '0') * 10 + chars[idx + 1] - '0';
            if (num <= 25) {
                dfs(chars, idx + 2);
            }
        }
    }


    /**
     * 6.10 9. 回文数
     *
     * @param x
     * @return
     */
    public boolean isPalindrome(int x) {
        String str = String.valueOf(x);
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - i - 1)) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test() {
        System.out.println(isPalindrome(-121));

        System.out.println(findLadders("hit", "cog", Arrays.asList("hot", "dot", "dog", "lot", "log", "cog")));
        System.out.println(findLadders("hit", "cog", Arrays.asList("hot", "dot", "dog", "lot", "log")));

        System.out.println(equationsPossible(new String[]{"e==d", "e==a", "f!=d", "b!=c", "a==b"}));

        int[][] matrix = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[][] matrix5 = new int[][]{{1, 2}, {4, 5}, {7, 8}};
        int[][] matrix4 = new int[][]{{1, 2, 3}, {7, 8, 9}};
        int[][] matrix1 = new int[][]{{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};
        int[][] matrix3 = new int[][]{{1}, {5}, {9}};
        int[][] matrix2 = new int[][]{{9, 10, 11, 12}};
        int[][] matrix6 = new int[][]{};
        int[][] matrix7 = new int[][]{{}};
        System.out.println(Arrays.toString(spiralOrder(matrix6)));
        System.out.println(Arrays.toString(spiralOrder(matrix7)));
        System.out.println(Arrays.toString(spiralOrder(matrix2)));
        System.out.println(Arrays.toString(spiralOrder(matrix)));
        System.out.println(Arrays.toString(spiralOrder(matrix1)));
        System.out.println(Arrays.toString(spiralOrder(matrix3)));
        System.out.println(Arrays.toString(spiralOrder(matrix4)));
        System.out.println(Arrays.toString(spiralOrder(matrix5)));
    }
}
