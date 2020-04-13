package com.wushuai.graph;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.junit.Test;

import java.util.*;

/**
 * <p>FindRedundantConnection</p>
 * <p>
 *     684
 *     对于以顶点标号位顶点键的图来说，初始化图时不要忘了将list全部初始化为null
 *     T[] target = Arrays.copyOf(T[] src, int newLength);   [0-newLength)
 *     System.arraycopy(T[] src, int posSrc(src开始拷贝下标), T[] target, int posTarget(target开始赋值下标), int length(拷贝长度));
 *
 * </p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-12 10:26
 */
public class FindRedundantConnection {

    void insertEdge(int from, int to, List<List<Integer>> graph) {
        List<Integer> edge = graph.get(from);
        if (edge == null) {
            edge = new ArrayList<>();
        }
        if (edge.contains((Object) to)) {
            return;
        }
        edge.add(to);
        graph.set(from, edge);
    }

    Boolean judgeLoop(int[] degree, List<List<Integer>> graph) {
        int number = 0;
        Queue<Integer> queue = new LinkedList<>();
        int nodeNum = 0;
        for (int i = 0; i < degree.length; i++) {
            if (degree[i] <= 1 && degree[i] >= 0) {
                queue.add(i);
            }
            // 当前图有几个节点
            if (degree[i] != -1) {
                nodeNum++;
            }
        }
        while (!queue.isEmpty()) {
            number++;
            int from = queue.poll();
            List<Integer> edge = graph.get(from);
            if (edge == null) {
                continue;
            }
            for (Integer anEdge : edge) {
                degree[anEdge]--;
                degree[from]--;
                if (degree[anEdge] <= 1 && degree[anEdge] > 0) {
                    queue.add(anEdge);
                }
            }
        }
        return number != nodeNum;
    }

    /**
     * 684. 冗余连接
     *
     * 本题 相当于 没插入一条边都要判断一下 当前是否有环 ，有就结束 然后排序输出
     * 对于有向图，要计算是否有环，拓扑排序即可。关键在于本题是无向图，
     * 计算图(无向图)是否有环时，采用如下方法
     *    1首先记录每个顶点的度
     *    2.找到度<=1的节点，消去与他相邻的边，并更新度
     *    重复2
     *    记录重复次数，
     *    若次数等于节点数，则没有环，否则有环
     *    https://www.cnblogs.com/tenosdoit/p/3644225.html
     *
     * 需要注意的小细节有
     * 判断是否有环算法中，不能直接在degree上修改，要在degree的深拷贝上修改
     *
     *
     * @param edges
     * @return
     */
    public int[] findRedundantConnection(int[][] edges) {
        List<Integer> res = new ArrayList<>();
        List<List<Integer>> graph = new ArrayList<>(Collections.nCopies(edges.length + 1, null));
        int[] degree = new int[edges.length + 1];
        // 全部置位1是因为， 本题没有给出节点数，所以在动态插入动态判断有无环的循环中， 需要统计当前图中有几个节点
        // 但是degree是事先已经初始化为总节点数了，所以利用degree不是-1的节点表示这个节点已经在图里了
        // 便于判断环的算法实现
        Arrays.fill(degree, -1);
        // 顶点标号从1开始。将0号位赋值先
        graph.set(0, new ArrayList<>());
        for (int i = 0; i < edges.length; i++) {
            int from = edges[i][0];
            int to = edges[i][1];
            insertEdge(from, to, graph);
            insertEdge(to, from, graph);
            if (degree[to] < 0) {
                degree[to] = 0;
            }
            degree[to]++;
            if (degree[from] < 0) {
                degree[from] = 0;
            }
            degree[from]++;
            int[] copyDegree = Arrays.copyOf(degree, degree.length);
            if (judgeLoop(copyDegree, graph)) {
                res.clear();
                res.add(from);
                res.add(to);
                Collections.sort(res);
                int[] ress = new int[res.size()];
                for (int j = 0; j < res.size(); j++) {
                    ress[j] = res.get(j);
                }
                return ress;
            }
        }
        return new int[]{};
    }

    @Test
    public void test1() {
        FindRedundantConnection o = new FindRedundantConnection();
        System.out.println(Arrays.toString(o.findRedundantConnection(new int[][]{{1, 2}, {1, 3}, {2, 3}})));
        System.out.println(Arrays.toString(o.findRedundantConnection(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}})));
        System.out.println(Arrays.toString(o.findRedundantConnection(new int[][]{{1, 2}, {2, 3}, {3, 4}, {2, 4}})));
    }
}

