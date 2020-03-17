package com.wushuai.contest;

import org.junit.Test;
import java.util.*;

/**
 * <p>contest179</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-17 08:51
 */
public class contest179 {

    public String generateTheString(int n) {
        StringBuilder sb = new StringBuilder();
        if(n % 2 == 0) {
            for(int i = 0; i < n - 1; i++) {
                sb.append("a");
            }
            sb.append("b");
        } else {
            for(int i = 0; i < n; i++) {
                sb.append("a");
            }
        }
        return sb.toString();
    }

    public int numTimesAllBlue(int[] light) {
        int ans = 0, maxReach = 0;
        for (int i = 0; i < light.length; i++) {
            maxReach = Math.max(maxReach, light[i]);
            if (i + 1 == maxReach) {
                ans++;
            }
        }
        return ans;
    }


    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        int res = 0;
        for (int i = 0; i < manager.length; i++) {
            if (informTime[i] == 0) {
                int total = 0;
                int j = manager[i];
                while (j != -1) {
                    total += informTime[j];
                    j = manager[j];
                }
                res = Math.max(res, total);
            }
        }
        return res;
    }

    public double frogPosition(int n, int[][] edges, int t, int target) {
        int[] visit = new int[n];
        int[][] edge = new int[(n - 1) * 2][2];
        int index = 1;
        for (int i = 0; i < (n - 1)* 2 - 1; i += 2) {
            edge[i][0] = edges[i / 2][0];
            edge[i][1] = edges[i / 2][1];
            edge[i + 1][1] = edges[i / 2][0];
            edge[i + 1][0] = edges[i / 2][1];
            visit[i / 2] = 0;
        }
        visit[0] = 1;
        int p = 1;
        int time = 0;
        int tmp = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(index);
        while (!stack.isEmpty()) {
            Integer peek = stack.peek();
            if (peek == target) {
                break;
            }

            stack.pop();
            tmp = 0;
            for (int i = 0; i < (n - 1) * 2 - 1; i++) {
                if (edge[i][0] == peek && visit[edge[i][1] - 1] == 0) {
                    stack.push(edge[i][1]);
                    tmp++;
                    visit[edge[i][1] - 1] = 1;
                }
            }

        }
        return 0;
    }

    @Test
    public void test1() {
        contest179 obj = new contest179();
        int[][] edge = new int[][]{{1,2},{1,3},{1,7},{2,4},{2,6},{3,5}};
        System.out.println(obj.frogPosition(7, edge, 2, 4));
    }
}

