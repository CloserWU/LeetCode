package com.wushuai.contest;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>contest177</p>
 * <p>
 *     List<Integer>[] res = new List[3];
 *     dfs
 * </p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-19 08:41
 */
public class contest177 {

    /**
     * 1360. 日期之间隔几天
     *
     * @param date1
     * @param date2
     * @return
     */
    public int daysBetweenDates(String date1, String date2) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(date1);
            d2 = format.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return (int) (Math.abs(d1.getTime() - d2.getTime()) / 24 / 3600 / 1000);
    }


    /**
     * 1361. 验证二叉树
     */
    int[] leftChild, rightChild;
    boolean[] visited;

    public boolean validateBinaryTreeNodes(int n, int[] leftChild, int[] rightChild) {
        // 统计每个结点的父亲数：1. 若父亲数大于1则为false  2.若父亲数等于0的不是1个则为false
        int[] indegreeCnt = new int[n];
        for (int i = 0; i < n; i++) {
            if ((leftChild[i] != -1 && ++indegreeCnt[leftChild[i]] > 1)
                    || (rightChild[i] != -1 && ++indegreeCnt[rightChild[i]] > 1)) {
                return false;
            }
        }
        int root = -1;
        for (int i = 0; i < n; i++) {
            if (indegreeCnt[i] == 0) {
                if (root != -1) {
                    return false;
                }
                root = i;
            }
        }

        // 上面的判断可以解决单个连通域的树判断、及部分多连通域情况
        // 例如下面的情况无法判断，需要再判断连通域的个数。从root一遍dfs之后，若存在结点未被访问说明多个连通域。
        // 1 <--- 0 <--->  2， 4 --->3
        this.leftChild = leftChild;
        this.rightChild = rightChild;
        visited = new boolean[n];
        dfs(root);
        for (boolean v : visited) {
            if (!v) {
                return false;
            }
        }
        return true;
    }

    private void dfs(int v) {
        if (v == -1) {
            return;
        }
        visited[v] = true;
        dfs(leftChild[v]);
        dfs(rightChild[v]);
    }


    class Pair {
        int x;
        int y;
        int dist;

        public Pair(int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }

    /**
     * 1362. 最接近的因数 优先队列，额能整除的加入队列，并按两数dist排序
     *
     * @param num
     * @return
     */
    public int[] closestDivisors(int num) {
        int[] res = new int[2];
        Queue<Pair> queue = new PriorityQueue<>(new Comparator<Pair>() {
            @Override
            public int compare(Pair o1, Pair o2) {
                return Integer.compare(o1.dist, o2.dist);
            }
        });
        int nums = num + 1;
        for (int i = 1; i <= Math.sqrt(nums); i++) {
            if (nums % i == 0) {
                queue.add(new Pair(i, nums / i, nums / i - i));
            }
        }
        nums = num + 2;
        for (int i = 1; i <= Math.sqrt(nums); i++) {
            if (nums % i == 0) {
                queue.add(new Pair(i, nums / i, nums / i - i));
            }
        }
        Pair peek = queue.peek();
        res[0] = peek.x;
        res[1] = peek.y;
        return res;
    }

    /**
     * 1363. 形成三的最大倍数
     *
     * 贪心思想：看数组的sum模3余几，如果余0，直接将整个数组返回；
     * 否则进行讨论一下：
     * 1.余1：去掉最小的mod(3)为1的数（数量足够），或者去掉两个mod(3)为2的数（前者数量不足）
     * 2.余2：去掉最小的mod(3)为2的数（数量足够），或者去掉两个mod(3)为1的数（前者数量不足）
     * 1和2是一种对偶关系，可以使用flag，3-flag 统一方程的思想来表示，这样可以减少代码量。
     *
     *
     * @param digits
     * @return
     */
    public String largestMultipleOfThree(int[] digits) {
        List<Integer>[] res = new List[3];
        for (int i = 0; i < 3; i++) {
            res[i] = new ArrayList<>();
        }
        //求和
        int sum = 0;
        for (int i : digits) {
            res[i % 3].add(i);
            sum += i;
        }
        //排序
        for (int i = 0; i < 3; i++) {
            res[i].sort(Collections.reverseOrder());
        }
        //分情况讨论
        int flag = sum % 3;
        if (flag != 0) {
            if (res[flag].size() > 0) {
                res[flag].remove(res[flag].size() - 1);
            } else if (res[3 - flag].size() > 1) {
                res[3 - flag].remove(res[3 - flag].size() - 1);
                res[3 - flag].remove(res[3 - flag].size() - 1);
            } else {
                res[1].clear();
                res[2].clear();
            }
        }
        //合并求最后的结果
        res[0].addAll(res[1]);
        res[0].addAll(res[2]);
        res[0].sort(Collections.reverseOrder());
        StringBuilder sb = new StringBuilder();
        for (Integer i : res[0]) {
            sb.append(i);
        }
        // 前导零
        if (sb.length() != 0 && sb.charAt(0) == '0') {
            return "0";
        }
        return sb.toString();
    }

    @Test
    public void test1() {
        contest177 obj = new contest177();
//        System.out.println(obj.daysBetweenDates("2019-06-29", "2019-06-30"));
//        System.out.println(obj.daysBetweenDates("2020-01-15", "2019-12-31"));
//        obj.validateBinaryTreeNodes();
        System.out.println(Arrays.toString(obj.closestDivisors(8)));
        System.out.println(Arrays.toString(obj.closestDivisors(123)));
        System.out.println(Arrays.toString(obj.closestDivisors(999)));
    }
}

