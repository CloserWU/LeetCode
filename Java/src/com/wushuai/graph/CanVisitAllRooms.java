package com.wushuai.graph;

import org.junit.Test;

import java.util.*;

/**
 * <p>CanVisitAllRooms</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-21 15:20
 */
public class CanVisitAllRooms {

    /**
     * 841. 钥匙和房间
     *
     * @param rooms
     * @return
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        if (rooms.size() == 0 || rooms.size() == 1) {
            return true;
        }
        boolean[] visit = new boolean[rooms.size()];
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        visit[0] = true;
        while (!stack.isEmpty()) {
            Integer pop = stack.pop();
            List<Integer> edges = rooms.get(pop);
            for (Integer r : edges) {
                if (!visit[r]) {
                    stack.push(r);
                    visit[r] = true;
                }
            }
        }
        for (boolean b : visit) {
            if (!b) {
                return false;
            }
        }
        return true;
    }


    @Test
    public void test() {
        CanVisitAllRooms o = new CanVisitAllRooms();

    }
}
