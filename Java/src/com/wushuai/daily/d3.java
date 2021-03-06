package com.wushuai.daily;

import org.junit.Test;

import java.util.*;
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
     *
     * @param line1
     * @param line2
     * @return
     */
    Boolean judgeParallel(Line line1, Line line2) {
        return line1.A * line2.B == line2.A * line1.B;
    }

    /**
     * 获得两点间距
     *
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
     *
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
     *
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
     *
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
     * <p>
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

    /**
     * 4.12
     * 355. 设计推特
     * Your Twitter object will be instantiated and called as such:
     * Twitter obj = new Twitter();
     * obj.postTweet(userId,tweetId);
     * List<Integer> param_2 = obj.getNewsFeed(userId);
     * obj.follow(followerId,followeeId);
     * obj.unfollow(followerId,followeeId);
     */
    class Twitter {

        class Node {
            int userId;
            int tweetId;

            public Node(int userId, int tweetId) {
                this.userId = userId;
                this.tweetId = tweetId;
            }
        }

        List<Node> lists;
        Map<Integer, List<Integer>> userTable;

        /**
         * Initialize your data structure here.
         */
        public Twitter() {
            lists = new ArrayList<>();
            userTable = new HashMap<>();
        }

        /**
         * Compose a new tweet.
         */
        public void postTweet(int userId, int tweetId) {
            if (!userTable.containsKey(userId)) {
                List<Integer> list = new ArrayList<>();
                list.add(userId);
                userTable.put(userId, list);
            }
            lists.add(new Node(userId, tweetId));
        }

        /**
         * Retrieve the 10 most recent tweet ids in the user's news feed. Each item in the news feed must be posted by users who the user followed or by the user herself. Tweets must be ordered from most recent to least recent.
         */
        public List<Integer> getNewsFeed(int userId) {
            List<Integer> res = new ArrayList<>();
            List<Integer> followed = userTable.get(userId);
            if (followed == null) {
                return res;
            }
            int i = lists.size() - 1;
            while (res.size() < 10 && i >= 0) {
                Node node = lists.get(i);
                if (followed.contains((Object) node.userId)) {
                    res.add(node.tweetId);
                }
                i--;
            }
            return res;
        }

        /**
         * Follower follows a followee. If the operation is invalid, it should be a no-op.
         */
        public void follow(int followerId, int followeeId) {
            List<Integer> list = userTable.get(followerId);
            if (list == null) {
                list = new ArrayList<>();
                list.add(followerId);
            }
            list.add(followeeId);
            userTable.put(followerId, list);
        }

        /**
         * Follower unfollows a followee. If the operation is invalid, it should be a no-op.
         */
        public void unfollow(int followerId, int followeeId) {
            if (followeeId == followerId) {
                return;
            }
            List<Integer> list = userTable.get(followerId);
            if (list != null) {
                list.remove((Object) followeeId);
            }
        }
    }

    public class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    /**
     * 4.14
     * 445. 两数相加 II
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        while (l1 != null) {
            list1.add(l1.val);
            l1 = l1.next;
        }
        while (l2 != null) {
            list2.add(l2.val);
            l2 = l2.next;
        }
        Collections.reverse(list1);
        Collections.reverse(list2);
        List<Integer> t = null;
        if (list1.size() < list2.size()) {
            t = list1;
            list1 = list2;
            list2 = t;
        }
        int carry = 0;
        int i, j;
        for (i = 0, j = 0; i < list1.size() && j < list2.size(); i++, j++) {
            int tmp = list1.get(i) + list2.get(j) + carry;
            if (tmp >= 10) {
                tmp -= 10;
                carry = 1;
            } else {
                carry = 0;
            }
            list1.set(i, tmp);
        }
        while (carry == 1 && i < list1.size()) {
            int tmp = list1.get(i) + carry;
            if (tmp >= 10) {
                tmp -= 10;
                carry = 1;
            } else {
                carry = 0;
            }
            list1.set(i++, tmp);
        }
        if (carry == 1) {
            list1.add(1);
        }
        ListNode root = new ListNode(-1);
        for (i = 0; i < list1.size(); i++) {
            ListNode node = new ListNode(list1.get(i));
            node.next = root.next;
            root.next = node;
        }
        return root.next;
    }


    int bfs(int[][] matrix, int row, int col, Boolean[][] visit) {
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length || visit[row][col]) {
            return 999999;
        }
        if (matrix[row][col] == 0) {
            return 0;
        }
        int min = Integer.MAX_VALUE;
        visit[row][col] = true;
        min = Integer.min(min, bfs(matrix, row + 1, col, visit) + 1);
        min = Integer.min(min, bfs(matrix, row - 1, col, visit) + 1);
        min = Integer.min(min, bfs(matrix, row, col + 1, visit) + 1);
        min = Integer.min(min, bfs(matrix, row, col - 1, visit) + 1);
        visit[row][col] = false;
        return min;
    }

    /**
     * 4.15
     * 542. 01 矩阵
     * <p>
     * bfs O(n^4)
     * TLE 算法正确
     * dfs + 回溯
     * 需要注意一些地方，
     * - 每次递归结束后，要还原状态
     * - 二维数组初始化指挥初始化列，不会初始化行，行元素都为null
     *
     * @param matrix
     * @return
     */
    public int[][] updateMatrix(int[][] matrix) {
        int[][] res = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                Boolean[][] visit = new Boolean[matrix.length][matrix[0].length];
                for (int k = 0; k < visit.length; k++) {
                    Arrays.fill(visit[k], false);
                }
                res[i][j] = bfs(matrix, i, j, visit);
            }
        }
        return res;
    }

    /**
     * DP O(4 * n^2)
     * 一次往四个方向遍历，一次求最小值
     *
     * @param matrix
     * @return
     */
    public int[][] updateMatrix_v2(int[][] matrix) {
        int[][] res = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 1) {
                    res[i][j] = 999999;
                } else {
                    res[i][j] = 0;
                }
            }
        }
        // 左上到右下 （每个元素都向左看 向上看）
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i - 1 >= 0) {
                    res[i][j] = Integer.min(res[i][j], 1 + res[i - 1][j]);
                }
                if (j - 1 >= 0) {
                    res[i][j] = Integer.min(res[i][j], 1 + res[i][j - 1]);
                }
            }
        }
        // 右上到左下 （每个元素都向右看 向上看）
        for (int i = 0; i < matrix.length; i++) {
            for (int j = matrix[0].length - 1; j >= 0; j--) {
                if (i - 1 >= 0) {
                    res[i][j] = Integer.min(res[i][j], 1 + res[i - 1][j]);
                }
                if (j + 1 < matrix[0].length) {
                    res[i][j] = Integer.min(res[i][j], 1 + res[i][j + 1]);
                }
            }
        }
        // 右下到左上 （每个元素都向右看 向下看）
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = matrix[0].length - 1; j >= 0; j--) {
                if (i + 1 < matrix.length) {
                    res[i][j] = Integer.min(res[i][j], 1 + res[i + 1][j]);
                }
                if (j + 1 < matrix[0].length) {
                    res[i][j] = Integer.min(res[i][j], 1 + res[i][j + 1]);
                }
            }
        }
        // 左下到右上 （每个元素都向左看 向下看）
        for (int i = matrix.length - 1; i >= 0; i--) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (i + 1 < matrix.length) {
                    res[i][j] = Integer.min(res[i][j], 1 + res[i + 1][j]);
                }
                if (j - 1 >= 0) {
                    res[i][j] = Integer.min(res[i][j], 1 + res[i][j - 1]);
                }
            }
        }
        return res;
    }

    class Pair {
        int row;
        int col;

        public Pair(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }

    /**
     * BFS O(n^2)
     * https://leetcode-cn.com/problems/01-matrix/solution/01ju-zhen-by-leetcode-solution/
     * 从元素为0的位置开始向四个方向遍历， 每次都更新一圈，将更新好的入队，依次更新0，1，2，。。。
     *
     *  0
     *   0
     *
     *  =>
     *
     *   1
     *  101
     *   101
     *    1
     *
     *  =>
     *   2
     *  212
     * 21012
     *  21012
     *   212
     *    2
     * @param matrix
     * @return
     */
    public int[][] updateMatrix_v3(int[][] matrix) {
        int[][] res = new int[matrix.length][matrix[0].length];
        Boolean[][] visit = new Boolean[matrix.length][matrix[0].length];
        Queue<Pair> queue = new LinkedList<>();
        for (int i = 0; i < matrix.length; i++) {
            Arrays.fill(visit[i], false);
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    queue.add(new Pair(i, j));
                    visit[i][j] = true;
                    res[i][j] = 0;
                }
            }
        }
        int[] dx = new int[]{1, 0, -1, 0};
        int[] dy = new int[]{0, 1, 0, -1};
        while (!queue.isEmpty()) {
            Pair poll = queue.poll();
            for (int i = 0; i < 4; i++) {
                int nx = poll.row + dx[i];
                int ny = poll.col + dy[i];
                if (nx >= 0 && nx < matrix.length && ny >= 0 && ny < matrix[0].length && !visit[nx][ny]) {
                    res[nx][ny] = res[poll.row][poll.col] + 1;
                    visit[nx][ny] = true;
                    queue.add(new Pair(nx, ny));
                }
            }
        }
        return res;
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

        Twitter twitter = new Twitter();

// 用户1发送了一条新推文 (用户id = 1, 推文id = 5).
        twitter.postTweet(1, 5);

// 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
        System.out.println(twitter.getNewsFeed(1));

// 用户1关注了用户2.
        twitter.follow(1, 2);

// 用户2发送了一个新推文 (推文id = 6).
        twitter.postTweet(2, 6);

// 用户1的获取推文应当返回一个列表，其中包含两个推文，id分别为 -> [6, 5].
// 推文id6应当在推文id5之前，因为它是在5之后发送的.
        System.out.println(twitter.getNewsFeed(1));

// 用户1取消关注了用户2.
        twitter.unfollow(1, 2);

// 用户1的获取推文应当返回一个列表，其中包含一个id为5的推文.
// 因为用户1已经不再关注用户2.
        System.out.println(twitter.getNewsFeed(1));

        System.out.println(Arrays.toString(o.updateMatrix(new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 0, 0}})));
        System.out.println(Arrays.toString(o.updateMatrix(new int[][]{{0, 0, 0}, {0, 1, 0}, {1, 1, 1}})));

    }
}

