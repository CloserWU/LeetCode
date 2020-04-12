package com.wushuai.daily;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * <p>d3</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-11 08:32
 */
public class d3 {

    /**
     * 4.11
     * 887. 鸡蛋掉落
     * <p>
     * dp[i][j] = min{max{dp[i - k][j], dp[k - 1][j - 1]} + 1 | 1 <= k <= i}}
     * dp[i][j] 表示i层楼 j个鸡蛋的 最坏情况需要抛几次才能确定临界楼层(F)
     * <p>
     * 在[1, i]的区间中，选择一个k， 在k层楼抛一次，
     * - 若没有碎，说明F在[k + 1, i] 即dp[i - k][j]
     * (若k = i) 则表示第i层抛没有碎，直接能得到结果0，但在计算机二维数组中，会计算dp[0][j]，
     * 若数组初始化为0，则不需要额外处理边界
     * - 若碎了， 说明F在[1, k - 1] 即dp[k - 1][j - 1]
     * 加上抛的一次 即 一次在k层楼抛时，最多需要抛max{dp[i - k][j], dp[k - 1][j - 1]} + 1 次
     * 选择所有的k 得到所有k对应的最小值 即为dp[i][j]
     * <p>
     * O(KN^2)
     *
     * @param K
     * @param N
     * @return
     */
    public int superEggDrop(int K, int N) {
        int[][] dp = new int[N + 1][K + 1];
        for (int i = 1; i <= K; i++) {
            dp[1][i] = 1;
        }
        for (int i = 1; i <= N; i++) {
            dp[i][1] = i;
        }
        for (int i = 2; i <= N; i++) {
            for (int j = 2; j <= K; j++) {
                dp[i][j] = Integer.MAX_VALUE - 3;
                for (int k = 1; k <= i; k++) {
                    // 第k层鸡蛋碎了，那么需要求的 第k-1层 j - 1 个鸡蛋的情况
                    // 第k层级蛋没有碎，那么需要求得 第 i - k 层 j个鸡蛋的情况  （当k为i时，即为0；应该特殊判断，刚好二维数组初始化为0，这是个trick，就省去了判断）
                    // 两者的最大值加1 对应在第k层抛的最坏情况
                    // 遍历所有可能的k 即 1=<k<= i 求得所有情况的最小值，即得dp[i][j] 即第i层j个鸡蛋
                    dp[i][j] = Integer.min(dp[i][j], Integer.max(dp[i - k][j], dp[k - 1][j - 1]) + 1);
                }
            }
        }
        return dp[N][K];
    }

    /**
     * https://leetcode-cn.com/problems/super-egg-drop/solution/dong-tai-gui-hua-zhi-jie-shi-guan-fang-ti-jie-fang/
     * O(KNlog(N))
     *
     * @param K
     * @param N
     * @return
     */
    public int superEggDrop_v2(int K, int N) {
        int[][] dp = new int[N + 1][K + 1];
        for (int i = 1; i <= K; i++) {
            dp[1][i] = 1;
        }
        for (int i = 1; i <= N; i++) {
            dp[i][1] = i;
        }
        for (int i = 2; i <= N; i++) {
            for (int j = 2; j <= K; j++) {
                // dp[k - 1][j - 1]：根据语义，k 增大的时候，楼层大小越大，它的值就越大；
                // dp[i - k][j]：根据语义，k 增大的时候，楼层大小越小，它的值就越小。
                // 二者的较大值的最小点在它们交汇的地方。
                // 找到dp[i - k][j] <= dp[k - i][j - 1] 最大的那个 k 值即可。这里使用二分查找算法。
                int left = 1;
                int right = i;
                while (left < right) {
                    int mid = left + (right - left + 1) / 2;
                    if (dp[i - mid][j] < dp[mid - 1][j - 1]) {
                        right = mid - 1;
                    } else {
                        left = mid;
                    }
                }
                dp[i][j] = Integer.max(dp[i - left][j], dp[left - 1][j - 1]) + 1;
            }
        }
        return dp[N][K];
    }

    /**
     * Ax + By + C = 0
     */
    class Line {
        double A;
        double B;
        double C;

        public Line(double a, double b, double c) {
            A = a;
            B = b;
            C = c;
        }
    }

    class Point {
        double x;
        double y;

        public Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    /**
     * 判断两条线是否平行
     * A1/B1 == A2/B2。
     * 或 A1*B2 == A2*B1
     * @param line1
     * @param line2
     * @return
     */
    Boolean judgeParallel(Line line1, Line line2) {
        return line1.A * line2.B == line2.A * line1.B;
    }

    /**
     * 获得两点间距
     * @param point1
     * @param point2
     * @return
     */
    double getDistance(Point point1, Point point2) {
        return Math.sqrt(Math.pow(point1.x - point2.x, 2) + Math.pow(point1.y - point2.y, 2));
    }

    /**
     * 判断point1 是否在线段start - end 上
     * distance(p,s) + distance(p,e) == distance(s, e)，
     * 精确到小数点后3位即可
     * @param point1
     * @param start
     * @param end
     * @return
     */
    Boolean judgeInline(Point point1, Point start, Point end) {
        return Math.abs(getDistance(point1, start) + getDistance(point1, end) - getDistance(start, end)) < 1e-3;
    }

    /**
     * 获得两平行线端的交点
     * 分别查看两线段的顶点 是否在另一线段上， 若在就加入结果集
     * 最后选取结果集中x最小的点
     * @param start1
     * @param start2
     * @param end1
     * @param end2
     * @return
     */
    Point getIntersectionOfParallelLine(Point start1, Point start2, Point end1, Point end2) {
        List<Point> res = new ArrayList<>();
        if (judgeInline(start1, start2, end2)) {
            res.add(start1);
        }
        if (judgeInline(end1, start2, end2)) {
            res.add(end1);
        }
        if (judgeInline(start2, start1, end1)) {
            res.add(start2);
        }
        if (judgeInline(end2, start1, end1)) {
            res.add(end2);
        }
        if (res.size() == 0) {
            return null;
        }
        res.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return Double.compare(o1.x, o2.x);
            }
        });
        return res.get(0);
    }

    /**
     * 获取两不平直线(段)的交点
     * 得到交点坐标
     * x = (c2 * b1 - c1 * b2) / (a1 * b2 - a2 * b1)
     * y = (c1 * a2 - c2 * a1) / (a1 * b2 - a2 * b1)
     * 如果(x,y)在两线段上，则(x,y)即为答案，否则交点不存在。
     * @param line1
     * @param line2
     * @param start1
     * @param start2
     * @param end1
     * @param end2
     * @return
     */
    Point getIntersection(Line line1, Line line2, Point start1, Point start2, Point end1, Point end2) {
        double x = (line2.C * line1.B - line1.C * line2.B) / (line1.A * line2.B - line2.A * line1.B);
        double y = (line1.C * line2.A - line2.C * line1.A) / (line1.A * line2.B - line2.A * line1.B);
        Point point = new Point(x, y);
        if (judgeInline(point, start1, end1) && judgeInline(point, start2, end2)) {
            return point;
        } else {
            return null;
        }
    }

    /**
     * 4.12
     * 面试题 16.03. 交点
     *
     * https://leetcode-cn.com/problems/intersection-lcci/solution/c-yi-ban-shi-qiu-jiao-dian-by-time-limit/
     *
     * @param start1
     * @param end1
     * @param start2
     * @param end2
     * @return
     */
    public double[] intersection(int[] start1, int[] end1, int[] start2, int[] end2) {
        Point s1 = new Point(start1[0], start1[1]);
        Point e1 = new Point(end1[0], end1[1]);
        Point s2 = new Point(start2[0], start2[1]);
        Point e2 = new Point(end2[0], end2[1]);
        Line line1 = new Line(e1.y - s1.y, s1.x - e1.x, e1.x * s1.y - s1.x * e1.y);
        Line line2 = new Line(e2.y - s2.y, s2.x - e2.x, e2.x * s2.y - s2.x * e2.y);
        Point res = null;
        if (judgeParallel(line1, line2)) {
            res = getIntersectionOfParallelLine(s1, s2, e1, e2);
        } else {
            res = getIntersection(line1, line2, s1, s2, e1, e2);
        }
        if (res != null) {
            return new double[]{res.x, res.y};
        } else {
            return new double[]{};
        }
    }

    @Test
    public void test1() {
        d3 o = new d3();
//        System.out.println(o.superEggDrop_v2(2, 100));
        int[] s1 = new int[]{0, 0};
        int[] e1 = new int[]{3, 3};
        int[] s2 = new int[]{1, 1};
        int[] e2 = new int[]{2, 2};
        System.out.println(Arrays.toString(o.intersection(s1, e1, s2, e2)));
    }
}

