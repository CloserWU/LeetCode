package com.wushuai.contest;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>contest192</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-06-21 8:11
 */
public class contest192 {

    /**
     * 1470. 重新排列数组
     *
     * @param nums
     * @param n
     * @return
     */
    public int[] shuffle(int[] nums, int n) {
        int[] res = new int[2 * n];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            res[idx++] = nums[i];
            res[idx++] = nums[i + n];
        }
        return res;
    }

    /**
     * 1471. 数组中的 k 个最强值
     *
     * @param arr
     * @param k
     * @return
     */
    public int[] getStrongest(int[] arr, int k) {
        int[] bak = Arrays.copyOf(arr, arr.length);
        Arrays.sort(bak);
        int mid = bak[(arr.length - 1) / 2];
        System.out.println(mid);
        List<Integer> list = Arrays.stream(arr).boxed().sorted(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int a = Math.abs(o1 - mid);
                int b = Math.abs(o2 - mid);
                if (a > b) {
                    return -1;
                }
                if (a == b) {
                    return Integer.compare(o2, o1);
                }
                return 1;
            }
        }).collect(Collectors.toList());
        System.out.println(list);
        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = list.get(i);
        }
        return res;
    }


    /**
     * 1472. 设计浏览器历史记录
     * Your BrowserHistory object will be instantiated and called as such:
     * BrowserHistory obj = new BrowserHistory(homepage);
     * obj.visit(url);
     * String param_2 = obj.back(steps);
     * String param_3 = obj.forward(steps);
     */
    class BrowserHistory {

        List<String> pages = new ArrayList<>();
        int cnt = -1;

        public BrowserHistory(String homepage) {
            pages.add(homepage);
            cnt = 0;
        }

        public void visit(String url) {
            int idx = pages.size() - 1;
            while (idx != cnt) {
                pages.remove(idx--);
            }
            pages.add(url);
            cnt = pages.size() - 1;
        }

        public String back(int steps) {
            while (cnt != 0 && steps != 0) {
                cnt--;
                steps--;
            }
            return pages.get(cnt);
        }

        public String forward(int steps) {
            while (cnt != pages.size() - 1 && steps != 0) {
                steps--;
                cnt++;
            }
            return pages.get(cnt);
        }
    }


    @Test
    public void test() {
        contest192 o = new contest192();
        System.out.println(Arrays.toString(o.getStrongest(new int[]{1, 2, 3, 4, 5}, 2)));
        System.out.println(Arrays.toString(o.getStrongest(new int[]{6, 7, 11, 7, 6, 8}, 5)));
    }
}
