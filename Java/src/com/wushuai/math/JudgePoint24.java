package com.wushuai.math;

import org.junit.Test;

import java.util.*;

/**
 * <p>JudgePoint24</p>
 * <p>679</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-19 9:44
 */
public class JudgePoint24 {

    double compute(int op, double x, double y) {
        if (op == 0) {
            return x + y;
        } else if (op == 1) {
            return x - y;
        } else if (op == 2) {
            return x * y;
        } else {
            if (y == 0) {
                return 0;
            }
            return x / y;
        }
    }

    /**
     * 基本24点，不带括号
     * 左结合
     *
     * @param nums
     * @return
     */
    public boolean judgePoint24(int[] nums) {
        int[] op = new int[]{0, 1, 2, 3};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    double res = 0;
                    res = compute(op[i], nums[0], nums[1]);
                    res = compute(op[j], res, nums[2]);
                    res = compute(op[k], res, nums[3]);
                    if (res == 24) {
                        System.out.print(op[i]);
                        System.out.print(op[j]);
                        System.out.println(op[k]);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 高级24点，带括号，实型运算
     * 左结合、右结合、中结合
     *
     * @param nums
     * @return
     */
    public boolean judgePoint24_v2(int[] nums) {
        int[] op = new int[]{0, 1, 2, 3};
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    double res = 0;
                    res = compute(op[i], nums[0], nums[1]);
                    res = compute(op[j], res, nums[2]);
                    res = compute(op[k], res, nums[3]);
                    if (Math.abs(res - 24) < 1e-3) {
                        System.out.print(1);
                        System.out.print(op[i]);
                        System.out.print(op[j]);
                        System.out.println(op[k]);
                        return true;
                    }
                    res = 0;
                    double res1 = 0;
                    res = compute(op[i], nums[0], nums[1]);
                    res1 = compute(op[j], nums[2], nums[3]);
                    res = compute(op[k], res, res1);
                    if (Math.abs(res - 24) < 1e-3) {
                        System.out.print(2);
                        System.out.print(op[i]);
                        System.out.print(op[j]);
                        System.out.println(op[k]);
                        return true;
                    }
                    res = 0;
                    res = compute(op[i], nums[2], nums[3]);
                    res = compute(op[j], nums[1], res);
                    res = compute(op[k], nums[0], res);
                    if (Math.abs(res - 24) < 1e-3) {
                        System.out.print(3);
                        System.out.print(op[i]);
                        System.out.print(op[j]);
                        System.out.println(op[k]);
                        return true;
                    }

                    res = 0;
                    res = compute(op[i], nums[1], nums[2]);
                    res = compute(op[j], res, nums[3]);
                    res = compute(op[k], nums[0], res);
                    if (Math.abs(res - 24) < 1e-3) {
                        System.out.print(4);
                        System.out.print(op[i]);
                        System.out.print(op[j]);
                        System.out.println(op[k]);
                        return true;
                    }
                    res = 0;
                    res = compute(op[i], nums[1], nums[2]);
                    res = compute(op[j], nums[0], res);
                    res = compute(op[k], res, nums[3]);
                    if (Math.abs(res - 24) < 1e-3) {
                        System.out.print(5);
                        System.out.print(op[i]);
                        System.out.print(op[j]);
                        System.out.println(op[k]);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean solve(List<Double> list, int[] op) {
        if (list.size() == 1) {
            if (Math.abs(list.get(0) - 24.0) < 1e-3) {
                return true;
            }
            return false;
        }

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                if (i == j) {
                    continue;
                }
                for (int k = 0; k < op.length; k++) {
                    // 临时数组，不能直接在list上修改，要保证list的元素顺序不会变，外层两个for才是正确有效的
                    // 复制数组
                    List<Double> tt = new ArrayList<>(list);
                    double a = list.get(i);
                    double b = list.get(j);
                    double tmp = compute(op[k], a, b);
                    tt.remove(a);
                    tt.remove(b);
                    tt.add(tmp);
                    boolean flag = solve(tt, op);
                    if (flag) {
                        return true;
                    }

                    tt = new ArrayList<>(list);
                    tmp = compute(op[k], b, a);
                    tt.remove(a);
                    tt.remove(b);
                    tt.add(tmp);
                    flag = solve(tt, op);
                    if (flag) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 679. 24 点游戏
     * 顶级24点，运算符带括号，且牌可以以任意顺序与操作符结合(牌可打乱顺序)，且运算过程为实型运算
     * 最初有4个数字，4个运算符
     * 首先选出任意两个数字（无序）12种选法，再选一个操作符，4种选法。问题变为3个数字，4个运算符
     * 然后选出两个数字（无序）6种选法，再选一个操作符，4种选法。问题变为2个数字，4个运算符
     * 最后选两个数字（无序）2种选法，再选一个操作符，4种选法。结束
     * 共12 * 4 * 6 * 4 * 2 * 4 = 9216种可能，暴力枚举即可
     *
     * @param nums
     * @return
     */
    public boolean judgePoint24_v3(int[] nums) {
        int[] op = new int[]{0, 1, 2, 3};
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            list.add((double) nums[i]);
        }
        return solve(list, op);
    }


    @Test
    public void test() {
        JudgePoint24 o = new JudgePoint24();
        System.out.println(o.judgePoint24_v3(new int[]{1, 2, 3, 4}));
        System.out.println(o.judgePoint24_v3(new int[]{1, 6, 7, 5}));
        System.out.println(o.judgePoint24_v3(new int[]{1, 2, 3, 5}));
        System.out.println(o.judgePoint24_v3(new int[]{8, 8, 7, 1}));
        System.out.println(o.judgePoint24_v3(new int[]{1, 2, 1, 2}));
        System.out.println(o.judgePoint24_v3(new int[]{6, 6, 6, 6}));
        System.out.println(o.judgePoint24_v3(new int[]{6, 6, 6, 5}));
        System.out.println(o.judgePoint24_v3(new int[]{2, 3, 6, 6}));
        System.out.println(o.judgePoint24_v3(new int[]{6, 2, 6, 3}));
        System.out.println(o.judgePoint24_v3(new int[]{4, 1, 8, 7}));
        System.out.println(o.judgePoint24_v3(new int[]{3, 3, 8, 2}));
        /*
[1, 6, 7, 5]
[4,1,8,7]
[1, 2, 3, 4]
[1, 6, 7, 5]
[1, 2, 3, 5]
[8, 8, 7, 1]
[1, 2, 1, 2]
[6, 6, 6, 6]
[6, 6, 6, 5]
[2, 3, 6, 6]
[6, 2, 6, 3]
[4, 1, 8, 7]
[3, 3, 8, 2]
[1,5,7,4]
[9,5,4,1]
[6,5,1,4]
[6,2,7,4]
[9,4,1,3]
[3,5,6,4]
         */
    }
}
