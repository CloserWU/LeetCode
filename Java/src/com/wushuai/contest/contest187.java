package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest187</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-05-03 10:47
 */
public class contest187 {

    /**
     * 5400. 旅行终点站
     *
     * @param paths
     * @return
     */
    public String destCity(List<List<String>> paths) {
        Map<String, Integer> map = new HashMap<>();
        for (List<String> list : paths) {
            map.put(list.get(0), 0);
            map.put(list.get(1), 0);
        }
        for (List<String> list : paths) {
            map.put(list.get(0), map.getOrDefault(list.get(0), 0) + 1);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            if (entry.getValue() == 0) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 5401. 是否所有 1 都至少相隔 k 个元素
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean kLengthApart(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 1) {
                list.add(i);
            }
        }
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i) - list.get(i - 1) - 1 < k) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test() {
        contest187 o = new contest187();
        System.out.println(o.kLengthApart(new int[]{1, 0, 0, 1, 0, 1}, 2));
    }
}
