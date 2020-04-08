package com.wushuai.graph;

import org.junit.Test;

import java.util.*;

/**
 * <p>CanFinish</p>
 * <p>207</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-08 10:54
 */
public class CanFinish {


    /**
     * 207. 课程表
     *
     * @param numCourses
     * @param pre
     * @return
     */
    public boolean canFinish(int numCourses, int[][] pre) {
        // 构造图 <顶点， 边集> 简单化 小标从0开始
        // 注意要将全部顶点初始化为null
        List<List<Integer>> graph = new ArrayList<>(Collections.nCopies(numCourses, null));
        int[] inDegree = new int[numCourses];
        for (int[] aPre : pre) {
            int to = aPre[0];
            int from = aPre[1];
            List<Integer> edge;
            if (graph.get(from) == null) {
                edge = new ArrayList<>();
            } else {
                edge = graph.get(from);
            }
            edge.add(to);
            graph.set(from, edge);
            inDegree[to]++;
        }

        // 拓扑排序
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < inDegree.length; i++) {
            if (0 == inDegree[i]) {
                queue.add(i);
            }
        }
        int number = 0;
        while (!queue.isEmpty()) {
            number++;
            List<Integer> list = graph.get(queue.poll());
            // 没有出度的点
            if (list == null) {
                continue;
            }
            for (Integer aList : list) {
                inDegree[aList]--;
                if (inDegree[aList] == 0) {
                    queue.add(aList);
                }
            }
        }
        return numCourses == number;
    }

    @Test
    public void test1() {
        CanFinish o = new CanFinish();
        System.out.println(o.canFinish(2, new int[][]{{0 ,1}}));
    }
}

