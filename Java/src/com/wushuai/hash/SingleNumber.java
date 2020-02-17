package com.wushuai.hash;

import org.junit.Test;

/**
 * <p>SingleNumber</p>
 * <p>136</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-13 10:18
 */
public class SingleNumber {
    @Test
    public void test1() {
        System.out.println(singleNumber(new int[] {2, 2, 1}));
        System.out.println(singleNumber(new int[] {4, 2, 1, 1, 2}));
    }

    /**
     * 交换律：a ^ b ^ c <=> a ^ c ^ b
     *
     * 任何数于0异或为任何数 0 ^ n => n
     *
     * 相同的数异或为0: n ^ n => 0
     *
     * var a = [2,3,2,4,4]
     *
     * 2 ^ 3 ^ 2 ^ 4 ^ 4等价于 2 ^ 2 ^ 4 ^ 4 ^ 3 => 0 ^ 0 ^3 => 3
     *
     * @param nums
     *
     * @return
     */
    public int singleNumber(int[] nums) {
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            res ^= nums[i];
        }
        return res;
    }
}

