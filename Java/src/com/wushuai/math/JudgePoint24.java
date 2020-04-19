package com.wushuai.math;

import org.junit.Test;

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
     * <p>
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
     * 679. 24 点游戏
     * 高级24点，带括号
     * <p>
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


    @Test
    public void test() {
        JudgePoint24 o = new JudgePoint24();
        System.out.println(o.judgePoint24_v2(new int[]{1, 2, 3, 4}));
        System.out.println(o.judgePoint24_v2(new int[]{1, 6, 7, 5}));
        System.out.println(o.judgePoint24_v2(new int[]{1, 2, 3, 5}));
        System.out.println(o.judgePoint24_v2(new int[]{8, 8, 7, 1}));
        System.out.println(o.judgePoint24_v2(new int[]{1, 2, 1, 2}));
        System.out.println(o.judgePoint24_v2(new int[]{6, 6, 6, 6}));
        System.out.println(o.judgePoint24_v2(new int[]{6, 6, 6, 5}));
        System.out.println(o.judgePoint24_v2(new int[]{2, 3, 6, 6}));
        System.out.println(o.judgePoint24_v2(new int[]{6, 2, 6, 3}));
        System.out.println(o.judgePoint24_v2(new int[]{4, 1, 8, 7}));
        System.out.println(o.judgePoint24_v2(new int[]{3, 3, 8, 2}));
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
