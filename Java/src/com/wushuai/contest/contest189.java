package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest189</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-06-14 8:48
 */
public class contest189 {


    /**
     * 1450. 在既定时间做作业的学生人数
     *
     * @param startTime
     * @param endTime
     * @param queryTime
     * @return
     */
    public int busyStudent(int[] startTime, int[] endTime, int queryTime) {
        int res = 0;
        for (int i = 0; i < startTime.length; i++) {
            if (startTime[i] <= queryTime && endTime[i] >= queryTime) {
                res++;
            }
        }
        return res;
    }


    class pair {
        String str;
        int idx;

        public pair(String str, int idx) {
            this.str = str;
            this.idx = idx;
        }
    }

    /**
     * 1451. 重新排列句子中的单词
     *
     * @param text
     * @return
     */
    public String arrangeWords(String text) {
        text = text.toLowerCase();
        String[] strings = text.split(" ");
        Queue<pair> queue = new PriorityQueue<>(new Comparator<pair>() {
            @Override
            public int compare(pair o1, pair o2) {
                if (o1.str.length() == o2.str.length()) {
                    return Integer.compare(o1.idx, o2.idx);
                }
                return Integer.compare(o1.str.length(), o2.str.length());
            }
        });
        for (int i = 0; i < strings.length; i++) {
            queue.add(new pair(strings[i], i));
        }
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            pair poll = queue.poll();
            sb.append(' ');
            sb.append(poll.str);
        }
        sb.setCharAt(1, Character.toUpperCase(sb.charAt(1)));
        return sb.substring(1);
    }


    /**
     * 1452. 收藏清单
     * <p>
     * list的contains开销是O(m)，所以containsAll开销是O(m*n)；
     * set的contains开销是O(1)，所以containsAll开销是O(n)。
     * 直接用set.containsAll就行
     * set本质是哈希
     *
     * @param favoriteCompanies
     * @return
     */
    public List<Integer> peopleIndexes(List<List<String>> favoriteCompanies) {
        List<Integer> res = new ArrayList<>();
        for (List<String> tmp : favoriteCompanies) {
            Collections.sort(tmp);
        }
        boolean flag;
        for (int i = 0; i < favoriteCompanies.size(); i++) {
            flag = true;
            List<String> x = favoriteCompanies.get(i);
            for (int j = 0; j < favoriteCompanies.size(); j++) {
                if (i != j) {
                    List<String> y = favoriteCompanies.get(j);
                    // boolean b = new HashSet<>(x).containsAll(new HashSet<>(y));
                    if (containsAll(x, y)) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                res.add(i);
            }
        }
        return res;
    }

    /**
     * b containsAll a (yes or not)
     *
     * @param a
     * @param b
     * @return
     */
    boolean containsAll(List<String> a, List<String> b) {
        if (a.size() > b.size()) {
            return false;
        }
        int i = 0, j = 0;
        while (i != a.size() && j != b.size()) {
            if (a.get(i).equals(b.get(j))) {
                i++;
                j++;
            } else {
                j++;
            }
        }
        return i == a.size();
    }


    class point {
        double x, y;

        public point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }


    //算两点距离
    double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    //计算圆心
    point f(point a, point b, int r) {
        //算中点
        point mid = new point((a.x + b.x) / 2.0, (a.y + b.y) / 2.0);
        //AB距离的一半
        double d = dist(a.x, a.y, mid.x, mid.y);
        //计算h
        double h = Math.sqrt(r * r - d * d);
        //计算垂线
        point ba = new point(b.x - a.x, b.y - a.y);
        point hd = new point(-ba.y, ba.x);
        double len = Math.sqrt(hd.x * hd.x + hd.y * hd.y);
        hd.x /= len;
        hd.y /= len;
        hd.x *= h;
        hd.y *= h;
        return new point(hd.x + mid.x, hd.y + mid.y);
    }


    /**
     * 1453. 圆形靶内的最大飞镖数量
     * https://leetcode-cn.com/problems/maximum-number-of-darts-inside-of-a-circular-dartboard/solution/c-xiang-liang-suan-yuan-xin-jian-dan-yi-dong-by-sm/
     *
     * @param points
     * @param r
     * @return
     */
    public int numPoints(int[][] points, int r) {
        int ans = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                int cnt = 0;
                if (i == j) {
                    // 一个点，直接把这个点当圆心，看有几个点在半径范围内
                    for (int k = 0; k < points.length; k++) {
                        double dist = dist(points[i][0], points[i][1], points[k][0], points[k][1]);
                        if (dist <= r) {
                            cnt++;
                        }
                    }
                } else {
                    // 两个点，按垂直平分线和半径找到圆心，看有几个点在半径范围内
                    double d = dist(points[i][0], points[i][1], points[j][0], points[j][1]);
                    if (d / 2 > r) {
                        continue;
                    }
                    point a = new point(points[i][0], points[i][1]);
                    point b = new point(points[j][0], points[j][1]);
                    point res = f(a, b, r);
                    for (int k = 0; k < points.length; k++) {
                        double tmp = dist(res.x, res.y, points[k][0], points[k][1]);
                        if (tmp <= r) {
                            cnt++;
                        }
                    }
                }
                ans = Integer.max(ans, cnt);
            }
        }
        return ans;
    }


    @Test
    public void test() {
        contest189 o = new contest189();
    }
}
