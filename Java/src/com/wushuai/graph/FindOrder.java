package com.wushuai.graph;

import org.junit.Test;

import java.util.*;

/**
 * <p>FindOrder</p>
 * <p>
 *     210
 * </p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-10 09:06
 */
public class FindOrder {

    public int[] findOrder(int numCourses, int[][] pre) {
        List<List<Integer>> map = new ArrayList<>(Collections.nCopies(numCourses, null));
        int[] inDegree = new int[numCourses];
        for (int i = 0; i < pre.length; i++) {
            int from = pre[i][1];
            int to = pre[i][0];
            List<Integer> edge = map.get(from);
            if (edge == null) {
                edge = new ArrayList<>();
            }
            edge.add(to);
            inDegree[to]++;
            map.set(from, edge);
        }

        Queue<Integer> queue = new LinkedList<>();
        int[] res = new int[numCourses];
        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }
        int number = 0;
        while (!queue.isEmpty()) {
            Integer peek = queue.poll();
            res[number] = peek;
            number++;
            List<Integer> list = map.get(peek);
            if (list == null) {
                continue;
            }
            for (Integer to : list) {
                inDegree[to]--;
                if (inDegree[to] == 0) {
                    queue.add(to);
                }
            }
        }
        if (number == numCourses) {
            return res;
        } else {
            return new int[]{};
        }
    }

    @Test
    public void test1() {
        FindOrder o = new FindOrder();
        System.out.println(Arrays.toString(o.findOrder(2, new int[][]{{0 ,1}})));
    }
}

