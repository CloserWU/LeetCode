package com.wushuai.daily;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>d09_2</p>
 * <p>
 * 9.11  9.10  9.09  9.08
 * </p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-09-11 16:35
 */
public class d09_2 {


    /**
     * 9.11 216. 组合总和 III
     *
     * @param k
     * @param n
     * @return
     */
    public List<List<Integer>> combinationSum3(int k, int n) {
        int[] arr = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        List<List<Integer>> res = new ArrayList<>();
        getCombination(res, k, n, 0, arr, new ArrayList<>());
        return res;
    }


    void getCombination(List<List<Integer>> res, int k, int n, int idx, int[] arr, List<Integer> list) {
        if (n == 0 && list.size() == k) {
            res.add(new ArrayList<>(list));
        }
        if (n < 0 || idx == arr.length || list.size() > k) {
            return;
        }
        for (int i = idx; i < arr.length; i++) {
            list.add(arr[i]);
            getCombination(res, k, n - arr[i], i + 1, arr, list);
            list.remove(list.size() - 1);
        }
    }

    @Test
    public void test() {
        d09_2 o = new d09_2();

    }
}
