package com.wushuai.graph;

import org.junit.Test;

import java.util.*;

/**
 * <p>CalcEquation</p>
 * <p>399</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-11 19:30
 */
public class CalcEquation {

    class Edge {
        double val;
        String to;

        public Edge(double val, String to) {
            this.val = val;
            this.to = to;
        }
    }

    void insertEdge(String from, String to, double value, Map<String, List<Edge>> graph) {
        List<Edge> edges = graph.get(from);
        if (edges == null) {
            edges = new ArrayList<>();
        }
        edges.add(new Edge(value, to));
        graph.put(from, edges);
    }

    double dfs(String from, String end, double value, Map<String, List<Edge>> graph, List<String> visit) {
        if (from.equals(end)) {
            return value;
        }
        visit.add(from);
        List<Edge> edges = graph.get(from);
        if (edges == null) {
            return -1.0;
        }
        for (Edge edge : edges) {
            if (!visit.contains(edge.to)) {
                double val = dfs(edge.to, end, value * edge.val, graph, visit);
                if (val != -1) {
                    return val;
                }
            }
        }
        return -1.0;
    }

    /**
     * 399. 除法求值
     * 建立带权值的有向图 （不含重复边）
     * 求每个开始节点到结束节点的带权路径积
     * 采用dfs递归法，同时记录访问数组防止死循环
     *
     * @param equations
     * @param values
     * @param queries
     * @return
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, List<Edge>> graph = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            String from = equations.get(i).get(0);
            String to = equations.get(i).get(1);
            insertEdge(from, to, values[i], graph);
            insertEdge(to, from, 1.0 / values[i], graph);
        }
        double[] res = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String from = queries.get(i).get(0);
            String end = queries.get(i).get(1);
            if (graph.containsKey(from) && graph.containsKey(end)) {
                res[i] = dfs(from, end, 1.0, graph, new ArrayList<>());
            } else {
                res[i] = -1.0;
            }
        }
        return res;
    }

    @Test
    public void test1() {
        CalcEquation o = new CalcEquation();
        List<List<String>> lists = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("a");
        list.add("b");
        lists.add(list);
        List<String> list1 = new ArrayList<>();
        list1.add("b");
        list1.add("c");
        lists.add(list1);
        double[] val = new double[]{2.0,3.0};
        List<List<String>> lists1 = new ArrayList<>();
        List<String> list3 = new ArrayList<>();
        list3.add("a");
        list3.add("c");
        lists1.add(list3);
        List<String> list4 = new ArrayList<>();
        list4.add("b");
        list4.add("a");
        lists1.add(list4);
        List<String> list5 = new ArrayList<>();
        list5.add("a");
        list5.add("e");
        lists1.add(list5);
        List<String> list6 = new ArrayList<>();
        list6.add("a");
        list6.add("a");
        lists1.add(list6);
        List<String> list7 = new ArrayList<>();
        list7.add("x");
        list7.add("x");
        lists1.add(list7);

        System.out.println(Arrays.toString(o.calcEquation(lists, val, lists1)));


    }
}

