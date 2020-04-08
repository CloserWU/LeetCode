package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d1</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-07 11:05
 */
public class d1 {

    class Cache {
        int key;
        int value;
        int visit;
        long time;

        Cache(int key, int value, int visit, long time) {
            this.key = key;
            this.value = value;
            this.visit = visit;
            this.time = time;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Cache cache = (Cache) o;

            if (key != cache.key) {
                return false;
            }
            if (value != cache.value) {
                return false;
            }
            return visit == cache.visit;
        }

        @Override
        public int hashCode() {
            int result = key;
            result = 31 * result + value;
            result = 31 * result + visit;
            return result;
        }
    }

    /**
     * 4.05
     * 460. LFU缓存
     * Your LFUCache object will be instantiated and called as such:
     * LFUCache obj = new LFUCache(capacity);
     * int param_1 = obj.get(key);
     * obj.put(key,value);
     */
    class LFUCache {

        int capacity = 0;
        Map<Integer, Cache> caches = null;
        List<Cache> list = null;
        long time = 0;

        public LFUCache(int capacity) {
            caches = new HashMap<>();
            list = new ArrayList<>();
            this.capacity = capacity;
        }

        public int get(int key) {
            if (capacity == 0) {
                return -1;
            }
            ++time;
            if (caches.containsKey(key)) {
                Cache cache = caches.get(key);
                int pos = list.indexOf(cache);

                cache.visit += 1;
                cache.time = time;

                list.set(pos, cache);
                caches.put(key, cache);

                list.sort(new Comparator<Cache>() {
                    @Override
                    public int compare(Cache o1, Cache o2) {
                        if (o2.visit == o1.visit) {
                            return Long.compare(o2.time, o1.time);
                        }
                        return Integer.compare(o2.visit, o1.visit);
                    }
                });
                return cache.value;
            }
            return -1;
        }

        public void put(int key, int value) {
            if (capacity == 0) {
                return;
            }
            ++time;
            if (caches.containsKey(key)) {
                Cache cache = caches.get(key);
                int pos = list.indexOf(cache);
                cache.visit += 1;
                cache.value = value;
                cache.time = time;
                list.set(pos, cache);
                caches.put(key, cache);
            } else {
                Cache cache = new Cache(key, value, 1, time);
                if (list.size() == capacity) {
                    caches.remove(list.get(list.size() - 1).key);
                    list.remove(list.size() - 1);
                }
                list.add(cache);
                caches.put(key, cache);
            }
            list.sort(new Comparator<Cache>() {
                @Override
                public int compare(Cache o1, Cache o2) {
                    if (o2.visit == o1.visit) {
                        return Long.compare(o2.time, o1.time);
                    }
                    return Integer.compare(o2.visit, o1.visit);
                }
            });
        }
    }

    /**
     * 4.06
     * 72. 编辑距离
     * dp[i][j] 代表 word1 到 i 位置转换成 word2 到 j 位置需要最少步数
     * 当 word1[i] == word2[j]，dp[i][j] = dp[i-1][j-1]；
     * 当 word1[i] != word2[j]，dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1
     * 其中，dp[i-1][j-1] 表示替换操作，dp[i-1][j] 表示删除操作，dp[i][j-1] 表示插入操作。
     * 注意，针对第一行，第一列要单独考虑，我们引入 ''
     * https://leetcode-cn.com/problems/edit-distance/solution/zi-di-xiang-shang-he-zi-ding-xiang-xia-by-powcai-3/
     */
    public int minDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word1.length() + 1];
        for (int i = 0; i < dp.length; i++) {
            dp[i][0] = i;
        }
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i < dp.length; i++) {
            for (int j = 1; j < dp[0].length; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Integer.min(Integer.min(dp[i - 1][j], dp[i][j - 1]), dp[i - 1][j - 1]) + 1;
                }
            }
        }
        return dp[dp.length - 1][dp[0].length - 1];
    }


    /**
     * 4.07
     * 面试题 01.07. 旋转矩阵
     *
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        // 反对角转置矩阵
        for (int i = 0; i < matrix.length - 1; i++) {
            for (int j = 0; j < matrix.length - i - 1; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[matrix.length - j - 1][matrix.length - i - 1];
                matrix[matrix.length - j - 1][matrix.length - i - 1] = tmp;
            }
        }
        // 行变换
        for (int i = 0; i < matrix[0].length / 2; i++) {
            int[] tmp = matrix[i];
            matrix[i] = matrix[matrix[0].length - i - 1];
            matrix[matrix[0].length - i - 1] = tmp;
        }
    }


    @Test
    public void test1() {
        d1 o = new d1();
//        o.rotate(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}});
//        System.out.println(o.minDistance("acoood", "aoooad"));
        LFUCache cache = new LFUCache( 0 /* capacity (缓存容量) */ );

        cache.put(0,0);
        cache.get(0);
        /*cache.put(1, 1);
        cache.put(2, 2);
        System.out.println(cache.get(1));       // 返回 1
        cache.put(3, 3);    // 去除 key 2
        System.out.println(cache.get(2));       // 返回 -1 (未找到key 2)
        System.out.println(cache.get(3));       // 返回 3
        cache.put(4, 4);    // 去除 key 1
        System.out.println(cache.get(1));       // 返回 -1 (未找到 key 1)
        System.out.println(cache.get(3));       // 返回 3
        System.out.println(cache.get(4));       // 返回 4
*/
    }
}
