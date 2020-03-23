package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>dcontest22</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-23 08:42
 */
public class dcontest22 {

    /**
     * 1386. 两个数组间的距离值
     *
     * @param arr1
     * @param arr2
     * @param d
     * @return
     */
    public int findTheDistanceValue(int[] arr1, int[] arr2, int d) {
        int res = 0;
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                if (Math.abs(arr1[i] - arr2[j]) <= d) {
                    break;
                }
                if (j == arr2.length - 1) {
                    res++;
                }
            }
        }
        return res;
    }

    /**
     * 1387. 安排电影院座位
     * @param n
     * @param reservedSeats
     * @return
     */
    public int maxNumberOfFamilies(int n, int[][] reservedSeats) {
        Comparator<int[]> cmp = new Comparator<int[]>() {
            @Override
            public int compare(int[] o1, int[] o2) {
                if (o1[0] == o2[0]) {
                    return Integer.compare(o1[1], o2[1]);
                }
                return Integer.compare(o1[0], o2[0]);
            }
        };
        // 将座位按列和行从小到大排序
        Arrays.sort(reservedSeats, cmp);
        //当前行
        int row = reservedSeats[0][0];
        //前一行
        int preRow = 0;
        //不能开数组，开了就MTL
        int res = 0;
        // [第一排, 第一个有座位的那一排) ，这中间的4人坐先加上
        res += (row - preRow - 1) * 2;
        // [2-5]座位的标志  左边
        boolean fl = true;
        // [4-7]座位的标志  中间
        boolean fm = false;
        // [6-9]座位的标志  右边
        boolean fr = true;
        // 按行遍历，从左到右
        for (int i = 0; i < reservedSeats.length; i++) {
            if (row == reservedSeats[i][0]) {
                //2 3 有人座，则左边标志置false，同时把中间标志置true
                if (reservedSeats[i][1] >= 2 && reservedSeats[i][1] <= 3) {
                    fl = false;
                    fm = true;
                    fr = false;
                } else if (reservedSeats[i][1] >= 4 && reservedSeats[i][1] <= 7) {
                    //中间有人座。则这一排最多一个
                    fm = false;
                    // 兼顾左右情况
                    if (reservedSeats[i][1] >= 4 && reservedSeats[i][1] <= 5) {
                        fl = false;
                        fr = true;
                    } else if (reservedSeats[i][1] >= 6 && reservedSeats[i][1] <= 7) {
                        fr = false;
                    }
                } else if (reservedSeats[i][1] >= 8 && reservedSeats[i][1] <= 9) {
                    // 右边有人座
                    fr = false;
                }
                //最后一个座位访问结束， i减一，处理这一排
                if (reservedSeats.length == i + 1){
                    i--;
                    preRow = row;
                    row = n + 1;

                }
            } else {
                // 这一排最大设为3， 起码有一个标志为false
                int tmp = 3;
                // 不是最后一个座位，正常操作
                if (row != n + 1) {
                    preRow = row;
                    // 到下一个有人的排
                    row = reservedSeats[i--][0];
                }
                // 标志为false，就减一
                if (!fl) {
                    tmp--;
                }
                if (!fr) {
                    tmp--;
                }
                if (!fm) {
                    tmp--;
                }
                //累加大于0的，小于0没有意义
                if (tmp > 0) {
                    res += Math.min(tmp, 2);
                }
                // 加上(上一个有人的排, 下一个有人的排)区间里，空排含有的位置，每排俩
                res += (row - preRow - 1) * 2;
                // 一排结束，重置标志
                fl = fr = true;
                fm = false;
                // 最后一排有人的，结束，不用再循环下去了
                if (row == n + 1) {
                    break;
                }
            }
        }
        return res;
    }

    @Test
    public void test1() {
        dcontest22 obj = new dcontest22();
//        System.out.println(obj.maxNumberOfFamilies(3\, new int[][]{{2,3}}));
        System.out.println(obj.maxNumberOfFamilies(4, new int[][]{{4,3},{1,4},{4,6},{1,7}}));
    }
}

