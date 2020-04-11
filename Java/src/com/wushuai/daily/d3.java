package com.wushuai.daily;

import org.junit.Test;

import java.util.Arrays;

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

    @Test
    public void test1() {
        d3 o = new d3();
        System.out.println(o.superEggDrop_v2(2, 100));
    }
}

