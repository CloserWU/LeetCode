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


    /**
     * 8.23 201. 数字范围按位与
     *
     * @param m
     * @param n
     * @return
     */
    public int rangeBitwiseAnd(int m, int n) {
        int shift = 0;
        while (m < n) {
            m >>= 1;
            n >>= 1;
            ++shift;
        }
        return m << shift;
    }


    /**
     * 8.24 459. 重复的子字符串
     * ==KMP==
     * https://leetcode-cn.com/problems/repeated-substring-pattern/solution/zhong-fu-de-zi-zi-fu-chuan-by-leetcode-solution/
     *
     * @param s
     * @return
     */
    public boolean repeatedSubstringPattern(String s) {
        return kmp(s + s, s);
    }

    public boolean kmp(String query, String pattern) {
        int n = query.length();
        int m = pattern.length();
        int[] fail = new int[m];
        Arrays.fill(fail, -1);
        for (int i = 1; i < m; ++i) {
            int j = fail[i - 1];
            while (j != -1 && pattern.charAt(j + 1) != pattern.charAt(i)) {
                j = fail[j];
            }
            if (pattern.charAt(j + 1) == pattern.charAt(i)) {
                fail[i] = j + 1;
            }
        }
        int match = -1;
        for (int i = 1; i < n - 1; ++i) {
            while (match != -1 && pattern.charAt(match + 1) != query.charAt(i)) {
                match = fail[match];
            }
            if (pattern.charAt(match + 1) == query.charAt(i)) {
                ++match;
                if (match == m - 1) {
                    return true;
                }
            }
        }
        return false;
    }


    List<Integer> temp = new ArrayList<Integer>();
    List<List<Integer>> ans = new ArrayList<List<Integer>>();

    /**
     * 8.25 491. 递增子序列
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> findSubsequences(int[] nums) {
        dfs(0, Integer.MIN_VALUE, nums);
        return ans;
    }

    public void dfs(int cur, int last, int[] nums) {
        if (cur == nums.length) {
            if (temp.size() >= 2) {
                ans.add(new ArrayList<Integer>(temp));
            }
            return;
        }
        if (nums[cur] >= last) {
            temp.add(nums[cur]);
            dfs(cur + 1, nums[cur], nums);
            temp.remove(temp.size() - 1);
        }
        if (nums[cur] != last) {
            dfs(cur + 1, last, nums);
        }
    }


    /**
     * 8.26 17. 电话号码的字母组合
     *
     * @param digits
     * @return
     */
    public List<String> letterCombinations(String digits) {
        List<String> combinations = new ArrayList<String>();
        if (digits.length() == 0) {
            return combinations;
        }
        Map<Character, String> phoneMap = new HashMap<Character, String>() {{
            put('2', "abc");
            put('3', "def");
            put('4', "ghi");
            put('5', "jkl");
            put('6', "mno");
            put('7', "pqrs");
            put('8', "tuv");
            put('9', "wxyz");
        }};
        backtrack(combinations, phoneMap, digits, 0, new StringBuffer());
        return combinations;
    }

    public void backtrack(List<String> combinations, Map<Character, String> phoneMap, String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            combinations.add(combination.toString());
        } else {
            char digit = digits.charAt(index);
            String letters = phoneMap.get(digit);
            int lettersCount = letters.length();
            for (int i = 0; i < lettersCount; i++) {
                combination.append(letters.charAt(i));
                backtrack(combinations, phoneMap, digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }
    }


    /**
     * 8.27 332. 重新安排行程
     * Hierholzer 算法用于在连通图中寻找欧拉路径
     * 从起点出发，进行深度优先搜索。
     * 每次沿着某条边从某个顶点移动到另外一个顶点的时候，都需要删除这条边。
     * 如果没有可移动的路径，则将所在节点加入到栈中，并返回。
     *
     * @param tickets
     * @return
     */
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, Queue<String>> graph = new HashMap<>();
        List<String> res = new ArrayList<>();
        String from;
        String to;
        for (List<String> ticket : tickets) {
            from = ticket.get(0);
            to = ticket.get(1);
            Queue<String> queue = graph.get(from);
            if (queue == null) {
                queue = new PriorityQueue<>();
            }
            queue.add(to);
            graph.put(from, queue);
        }
        Stack<String> stack = new Stack<>();
        stack.push("JFK");
        // DFS
        // 精妙， 理解并掌握
        // 栈自底向上存的是路径，越靠底，表示最先访问到。
        // 对每个栈顶元素， 若它有相邻边 将相邻边入栈，并走到相邻边，同时消去这条边，若没有以他开始的边，就将它加入res
        // 栈里保存的就是路径
        // 对栈中的每个元素，一次加入到res的0个位置（反向插入）
        while (!stack.isEmpty()) {
            Queue<String> queue = new PriorityQueue<>();
            while ((queue = graph.get(stack.peek())) != null && queue.size() > 0) {
                stack.push(queue.poll());
            }
            res.add(stack.pop());
        }
        Collections.reverse(res);
        return res;
    }


    /**
     * 8.28 657. 机器人能否返回原点
     *
     * @param moves
     * @return
     */
    public boolean judgeCircle(String moves) {
        int x = 0, y = 0;
        for (int i = 0; i < moves.length(); i++) {
            char ch = moves.charAt(i);
            if (ch == 'R') {
                x++;
            } else if (ch == 'L') {
                x--;
            } else if (ch == 'U') {
                y++;
            } else {
                y--;
            }
        }
        return x == 0 && y == 0;
    }


    @Test
    public void test() {
        d08_3 o = new d08_3();
        judgePoint24(new int[]{1, 1, 2, 2});
    }
}
