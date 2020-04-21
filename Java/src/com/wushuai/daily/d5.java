package com.wushuai.daily;

import org.junit.Test;

/**
 * <p>d5</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-21 9:31
 */
public class d5 {

    /**
     * 4.21
     * 1248. 统计「优美子数组」
     * @param nums
     * @param k
     * @return
     */
    public int numberOfSubarrays(int[] nums, int k) {
        int res = 0;
        int[] arr = new int[nums.length + 2];
        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            if ((nums[i] & 1) == 1) {
                arr[++idx] = i;
            }
        }
        arr[0] = -1;
        arr[idx + 1] = nums.length;
        for (int i = 1; i + k < idx + 2; i++) {
            res += (arr[i] - arr[i - 1]) * (arr[i + k] - arr[i + k - 1]);
        }
        return res;
    }

    @Test
    public void test() {
        d5 o = new d5();

    }
}
