package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest175</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-07 09:44
 */
public class contest175 {

    /**
     * 1346. 检查整数及其两倍数是否存在
     *
     * @param arr
     * @return
     */
    public boolean checkIfExist(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                if (i != j && arr[i] == 2 * arr[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 1347. 制造字母异位词的最小步骤数
     *
     * @param s
     * @param t
     * @return
     */
    public int minSteps(String s, String t) {
        int[] sarr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            sarr[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            sarr[t.charAt(i) - 'a']--;
        }
        int res = 0;
        for (int i = 0; i < 26; i++) {
            if (sarr[i] >= 1) {
                res += sarr[i];
            }
        }
        return res;
    }

    /**
     * 1348. 推文计数
     * Your TweetCounts object will be instantiated and called as such:
     * TweetCounts obj = new TweetCounts();
     * obj.recordTweet(tweetName,time);
     * List<Integer> param_2 = obj.getTweetCountsPerFrequency(freq,tweetName,startTime,endTime);
     */
    class TweetCounts {
        Map<String, List<Integer>> map;

        public TweetCounts() {
            map = new HashMap<>();
        }

        public void recordTweet(String tweetName, int time) {
            if (map.containsKey(tweetName)) {
                List<Integer> list = map.get(tweetName);
                list.add(time);
                map.put(tweetName, list);
            } else {
                List<Integer> list = new ArrayList<>();
                list.add(time);
                map.put(tweetName, list);
            }
        }

        public int getSecond(String freq) {
            if ("minute".equals(freq)) {
                return 60;
            } else if ("day".equals(freq)) {
                return 3600 * 24;
            } else {
                return 3600;
            }
        }

        public List<Integer> getTweetCountsPerFrequency(String freq, String tweetName, int startTime, int endTime) {
            int slot = getSecond(freq);
            List<Integer> list = map.get(tweetName);
            List<Integer> res = new ArrayList<>();
            int idx = 0;
            Collections.sort(list);
            for (int j = startTime; j <= endTime; j += slot) {
                int t = 0;
                while (idx < list.size() && list.get(idx) < startTime) {
                    idx++;
                }
                while (idx < list.size() && list.get(idx) >= startTime && list.get(idx) <= endTime && list.get(idx) < startTime + slot) {
                    idx++;
                    t++;
                }
                res.add(t);
                startTime += slot;
            }
            return res;
        }
    }

    /**
     * 1349. 参加考试的最大学生数
     *
     * 状压DP
     * dp[i][j] 表示当第 i 行的座位分布为 j 时，前 i 行可容纳的最大学生人数。
     * https://leetcode-cn.com/problems/maximum-students-taking-exam/solution/xiang-jie-ya-suo-zhuang-tai-dong-tai-gui-hua-jie-f/
     *
     * @param seats
     * @return
     */
    public int maxStudents(char[][] seats) {
        return 0;
    }


    @Test
    public void test1() {
        contest175 o = new contest175();
        TweetCounts tweetCounts = new TweetCounts();
        tweetCounts.recordTweet("tweet3", 0);
        tweetCounts.recordTweet("tweet3", 16);
        tweetCounts.recordTweet("tweet3", 66);
        System.out.println(tweetCounts.getTweetCountsPerFrequency("hour", "tweet3", 43, 1838));
    }
}

