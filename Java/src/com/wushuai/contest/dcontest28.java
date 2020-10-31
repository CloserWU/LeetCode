package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>dcontest28</p>
 * <p>
 * Iterator<Map.Entry<Integer, Integer>> entries = map.entrySet().iterator();
 * for (Integer key : map.keySet()) {
 * for (Integer value : map.values()) {
 * </p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-06-21 21:59
 */
public class dcontest28 {

    /**
     * 1475. 商品折扣后的最终价格
     *
     * @param prices
     * @return
     */
    public int[] finalPrices(int[] prices) {
        int[] res = new int[prices.length];
        for (int i = 0; i < prices.length; i++) {
            res[i] = prices[i];
            for (int j = i + 1; j < prices.length; j++) {
                if (prices[i] >= prices[j]) {
                    res[i] = prices[i] - prices[j];
                    break;
                }
            }
        }
        return res;
    }


    /**
     * 1476. 子矩形查询
     * Your SubrectangleQueries object will be instantiated and called as such:
     * SubrectangleQueries obj = new SubrectangleQueries(rectangle);
     * obj.updateSubrectangle(row1,col1,row2,col2,newValue);
     * int param_2 = obj.getValue(row,col);
     */
    class SubrectangleQueries {
        int[][] rect = null;

        public SubrectangleQueries(int[][] rectangle) {
            rect = rectangle;
        }

        public void updateSubrectangle(int row1, int col1, int row2, int col2, int newValue) {
            for (int i = row1; i <= row2; i++) {
                for (int j = col1; j <= col2; j++) {
                    rect[i][j] = newValue;
                }
            }
        }

        public int getValue(int row, int col) {
            return rect[row][col];
        }
    }


    /**
     * 1477. 找两个和为目标值且不重叠的子数组
     *
     * @param arr
     * @param target
     * @return
     */
    public int minSumOfLengths(int[] arr, int target) {
        int left = 0, right = 0;
        Map<Integer, Integer> map = new LinkedHashMap<>();
        int sum = 0;
        while (right != arr.length) {
            sum += arr[right];
            if (sum > target) {
                while (sum > target) {
                    sum -= arr[left++];
                }
                if (left > right) {
                    left = ++right;
                } else {
                    sum -= arr[right];
                }
            } else if (sum < target) {
                right++;
            } else {
                map.put(left, right);
                while (sum >= target) {
                    sum -= arr[left++];
                }
                if (left > right) {
                    left = ++right;
                } else {
                    sum -= arr[right];
                }
            }
        }
        if (map.size() <= 1) {
            return -1;
        } else {
            int res = Integer.MAX_VALUE;
            Set<Integer> set = map.keySet();
            for (int i = 0; i < set.size() - 1; i++) {
                for (int j = i + 1; j < set.size(); j++) {
//                    if ()
                }
            }
            if (res == Integer.MAX_VALUE) {
                return -1;
            }
            return res;
        }
    }

    @Test
    public void test() {
        dcontest28 o = new dcontest28();
        System.out.println(o.minSumOfLengths(new int[]{3, 2, 2, 4, 3}, 3));
    }
}
