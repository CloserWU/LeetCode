package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d06_2</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-08-10 9:33
 */
public class d06_2 {

    /**
     * 6.11 739. 每日温度
     * *******单调栈******
     *
     * @param T
     * @return
     */
    public int[] dailyTemperatures(int[] T) {
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[T.length];
        stack.push(0);
        for (int i = 1; i < T.length; i++) {
            while (!stack.isEmpty() && T[stack.peek()] < T[i]) {
                Integer peek = stack.pop();
                res[peek] = i - peek;
            }
            stack.push(i);
        }
        return res;
    }

    @Test
    public void test() {
        d06_2 o = new d06_2();

    }
}
