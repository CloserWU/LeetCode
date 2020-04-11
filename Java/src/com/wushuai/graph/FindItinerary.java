package com.wushuai.graph;

import org.junit.Test;

import java.util.*;

/**
 * <p>FindItinerary</p>
 * <p>332</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-11 11:32
 */
public class FindItinerary {

    /**
     * 332. 重新安排行程
     * https://leetcode-cn.com/problems/reconstruct-itinerary/solution/javadfsjie-fa-by-pwrliang/
     *
     * @param tickets
     * @return
     */
    public List<String> findItinerary(List<List<String>> tickets) {
        Map<String, Queue<String>> graph = new HashMap<>();
        List<String> res = new ArrayList<>();
        String from;
        String to;
        for (List<String> ticket : tickets) {
            from = ticket.get(0);
            to = ticket.get(1);
            Queue<String> queue = graph.get(from);
            if (queue == null) {
                queue = new PriorityQueue<>();
            }
            queue.add(to);
            graph.put(from, queue);
        }
        Stack<String> stack = new Stack<>();
        stack.push("JFK");
        // DFS
        // 对每个栈顶元素， 若它有相邻边 将相邻边入栈，并走到相邻边，同时消去这条边，若没有以他开始的边，就将它加入res
        // 栈里保存的就是路径
        // 对栈中的每个元素，一次加入到res的0个位置（反向插入）
        while (!stack.isEmpty()) {
            Queue<String> queue = new PriorityQueue<>();
            while ((queue = graph.get(stack.peek())) != null && queue.size() > 0) {
                stack.push(queue.poll());
            }
            res.add(0, stack.pop());
        }
        return res;
    }

    @Test
    public void test1() {
        FindItinerary o = new FindItinerary();
        List<List<String>> tickets = new ArrayList<>();
        List<String> list = new ArrayList<>();
        list.add("JFK");
        list.add("SFO");
        tickets.add(list);
        List<String> list1 = new ArrayList<>();
        list1.add("JFK");
        list1.add("ATL");
        tickets.add(list1);
        List<String> list2 = new ArrayList<>();
        list2.add("SFO");
        list2.add("ATL");
        tickets.add(list2);
        List<String> list3 = new ArrayList<>();
        list3.add("ATL");
        list3.add("JFK");
        tickets.add(list3);
        List<String> list4 = new ArrayList<>();
        list4.add("ATL");
        list4.add("SFO");
        tickets.add(list4);
        List<String> list5 = new ArrayList<>();
        list5.add("SFO");
        list5.add("ABC");
        tickets.add(list5);
        System.out.println(o.findItinerary(tickets));
    }
}

