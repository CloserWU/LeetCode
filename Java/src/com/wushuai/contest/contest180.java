package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest180</p>
 * <p>
 *     Arrays.sort
 *     1e9+7 浮点
 *     1000000007 整形
 * </p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-16 11:27
 */
public class contest180 {
    /**
     * 1380. 矩阵中的幸运数
     */
    public List<Integer> luckyNumbers(int[][] matrix) {
        int row = matrix.length;
        int col = matrix[0].length;
        List<Integer> colMax = new ArrayList<>();
        List<Integer> rowMin = new ArrayList<>();
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            int min = matrix[i][0];
            for (int j = 0; j < col; j++) {
                min = Math.min(min, matrix[i][j]);
            }
            rowMin.add(min);
        }
        for (int i = 0; i < col; i++) {
            int max = matrix[0][i];
            for (int j = 0; j < row; j++) {
                max = Math.max(max, matrix[j][i]);
            }
            colMax.add(max);
        }
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (colMax.contains(matrix[i][j]) && rowMin.contains(matrix[i][j])) {
                    result.add(matrix[i][j]);
                }
            }
        }
        return result;
    }

    /**
     *  1381. 设计一个支持增量操作的栈
     * Your CustomStack object will be instantiated and called as such:
     * CustomStack obj = new CustomStack(maxSize);
     * obj.push(x);
     * int param_2 = obj.pop();
     * obj.increment(k,val);
     */
    class CustomStack {
        int[] stack;
        int begin = 0, end = -1; // end栈顶元素
        int size = 0;

        public CustomStack(int maxSize) {
            stack = new int[10000];
            size = maxSize;
        }

        public void push(int x) {
            if (end - begin + 1 < size) {
                stack[++end] = x;
            }
        }

        public int pop() {
            if (end < begin) {
                return -1;
            }
            return stack[end--];
        }

        public void increment(int k, int val) {
            for (int i = begin; i <= end; i++) {
                if ((i - begin + 1) > k) {
                    break;
                }
                stack[i] += val;
            }
        }
    }

    /**
     *  1382. 将二叉搜索树变平衡
     *  递归+二分
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    List<Integer> list = null;

    void inOrder(TreeNode root, List<Integer> list) {
        if (root != null) {
            inOrder(root.left, list);
            list.add(root.val);
            inOrder(root.right, list);
        }
    }

    TreeNode build(int left, int right) {
        //出口
        if (left < 0 || right < 0 || left > right) {
            return null;
        }
        //叶子节点
        if (left == right) {
            TreeNode my = new TreeNode(list.get(left));
            my.left = null;
            my.right = null;
            return my;
        } else {  //二分
            int mid = (left + right) / 2;
            int val = list.get(mid);
            TreeNode my = new TreeNode(val);
            my.left = build(left, mid - 1);
            my.right = build(mid + 1, right);
            return my;
        }


    }

    public TreeNode balanceBST(TreeNode root) {
        list = new ArrayList<>();
        inOrder(root, list);
        return build(0, list.size() - 1);
    }




    /**
     *  1383. 最大的团队表现值
     *
     * 当两种方案的efficiency相等时，speed之和更大的方案显然更优。
     * 题目的输入决定了最多有 n 种 efficiency。
     * 对于每种 efficiency 肯定都会存在最优的方案。
     * 最终答案肯定就是这个n种方案里面最优的那个。
     * 问题转化成了如何快速求出每种 efficiency 的最优方案：
     *
     * 从大到小枚举efficiency，使用一个数组维护可选的职员。因为efficiency不断减小，所以职员只会被加入这个数组，而绝不会被删除。
     * 在数组种选取 k 个最大的 speed(可使用堆排序维护)，使用**当前**枚举到 efficiency 与 k个最大的speed之和相乘作为当前 efficiency 的最优解。
     * 注意： 选取的 k 个最大speed对应的efficiency可能都大于当前枚举的 efficiency，但是这并不影响最终答案的正确性。因为如果这个选择方案的确为最终答案的话，则其值必然记录在其他 efficiency 的最优解中。
     * 编程技巧：使用 std::priority_queue 代替堆排序代码，提高编码速度。
     */
    class Engineer {
        int efficiency;
        int speed;

        Engineer(int efficiency, int speed) {
            this.efficiency = efficiency;
            this.speed = speed;
        }
    }

    public int maxPerformance(int n, int[] speed, int[] efficiency, int k) {
        List<Engineer> list = new ArrayList<>();
        //按速度从大到小排序
        Queue<Long> queue = new PriorityQueue<>(new Comparator<Long>() {
            @Override
            public int compare(Long o1, Long o2) {
                return Long.compare(o1,o2);
            }
        });
        for (int i = 0; i < n; i++) {
            list.add(new Engineer(efficiency[i], speed[i]));
        }
        //按效率从大到小排序
        list.sort(new Comparator<Engineer>() {
            @Override
            public int compare(Engineer o1, Engineer o2) {
                return Integer.compare(o2.efficiency, o1.efficiency);
            }
        });
        long sumSpeed = 0, ans = 0;
        for (int i = 0; i < n; i++) {
            queue.add((long) list.get(i).speed);
            sumSpeed += list.get(i).speed;
            // 当前最低的efficiency
            ans = Math.max(ans, sumSpeed * list.get(i).efficiency);
            if (queue.size() == k) {
                Long aLong = queue.poll();
                sumSpeed -= aLong;
            }
        }
        //在java中 1e9+7为浮点数(double)数据类型，1000000007为整型(int)，为此，
        // 当一个数对他们进行取余运算之后，所得答案的数据类型是不同的，而且精度也是同的。
        return (int) (ans % 1000000007);
    }

    @Test
    public void test1() {
        int[] speed = new int[]{2,10,3,1,5,8};
        int[] e = new int[]{5,4,3,9,7,2};
        System.out.println(maxPerformance(6, speed, e, 2));
    }
}

