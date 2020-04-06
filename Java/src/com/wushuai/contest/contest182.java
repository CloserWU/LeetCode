package com.wushuai.contest;

import org.junit.Test;

import java.util.*;

/**
 * <p>contest182</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-06 21:14
 */
public class contest182 {

    /**
     * 1394. 找出数组中的幸运数
     *
     * @param arr
     * @return
     */
    public int findLucky(int[] arr) {
        int[] t = new int[500];
        for (int i = 0; i < arr.length; i++) {
            t[arr[i]] += 1;
        }
        for (int i = 499; i > 0; i--) {
            if (t[i] == i) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 1395. 统计作战单位数
     *
     * @param rating
     * @return
     */
    public int numTeams(int[] rating) {
        int res = 0;
        for (int i = 0; i < rating.length - 2; i++) {
            for (int j = i + 1; j < rating.length - 1; j++) {
                for (int k = j + 1; k < rating.length; k++) {
                    if (rating[i] < rating[j] && rating[j] < rating[k]) {
                        res++;
                    } else if (rating[i] > rating[j] && rating[j] > rating[k]) {
                        res++;
                    }
                }
            }
        }
        return res;
    }


    class Station {
        String name;
        int total;
        int line;

        Station(String name, int total, int line) {
            this.name = name;
            this.total = total;
            this.line = line;
        }
    }


    /**
     * 1396. 设计地铁系统
     * Your UndergroundSystem object will be instantiated and called as such:
     * UndergroundSystem obj = new UndergroundSystem();
     * obj.checkIn(id,stationName,t);
     * obj.checkOut(id,stationName,t);
     * double param_3 = obj.getAverageTime(startStation,endStation);
     */
    class UndergroundSystem {

        // stationName stationNameList
        Map<String, List<Station>> map = new HashMap<>();
        // id stationName
        Map<Integer, String> peo = new HashMap<>();
        // id time
        Map<Integer, Integer> time = new HashMap<>();

        public UndergroundSystem() {

        }

        public void checkIn(int id, String stationName, int t) {
            peo.put(id, stationName);
            time.put(id, t);
        }

        public void checkOut(int id, String stationName, int t) {
            String startStation = peo.get(id);
            String endStation = stationName;
            Integer tt = time.get(id);
            for (int j = 0; j < 2; j++) {
                List<Station> stations = map.get(startStation);
                if (stations == null) {
                    stations = new ArrayList<>();
                    stations.add(new Station(endStation, t - tt, 1));
                } else {
                    boolean exist = false;
                    for (int i = 0; i < stations.size(); i++) {
                        if (stations.get(i).name.equals(endStation)) {
                            exist = true;
                            Station station = stations.get(i);
                            station.line += 1;
                            station.total += t - tt;
                            stations.set(i, station);
                            break;
                        }
                    }
                    if (!exist) {
                        stations.add(new Station(endStation, t - tt, 1));
                    }
                }
                map.put(startStation, stations);

                String tmp = startStation;
                startStation = endStation;
                endStation = tmp;
            }
        }

        public double getAverageTime(String startStation, String endStation) {
            List<Station> stations = map.get(startStation);
            if (stations != null) {
                for (int i = 0; i < stations.size(); i++) {
                    if (stations.get(i).name.equals(endStation)) {
                        Station station = stations.get(i);
                        return station.total * 1.0 / station.line * 1.0;
                    }
                }
            }
            return -1;
        }
    }

    /**
     * 1397. 找到所有好字符串
     *
     * @param n
     * @param s1
     * @param s2
     * @param evil
     * @return
     */
    public int findGoodStrings(int n, String s1, String s2, String evil) {
        if (s1.contains(evil) && s2.contains(evil)) {
            return 0;
        }
        int res = 0;
        while (!s1.equals(s2)) {
            int pos = s1.lastIndexOf(evil);
            char[] chars = s1.toCharArray();
            int start;
            int carry = 0;
            if (pos == -1) {
                res = (res + 1) % 1000000007;
                chars[chars.length - 1] += 1;
                start = chars.length - 1;
            } else {
                chars[pos + evil.length() - 1] += 1;
                start = pos + evil.length() - 1;
            }
            for (int i = start; i >= 0; i--) {
                chars[i] += carry;
                if (carry == 1) {
                    carry = 0;
                }
                if (chars[i] > 'z') {
                    chars[i] -= 26;
                    carry = 1;
                }
                if (carry == 0) {
                    break;
                }
            }
            s1 = new String(chars);
        }
        if (!s1.contains(evil)) {
            res = (res + 1) % 1000000007;
        }
        return res;
    }


    @Test
    public void test1() {
        contest182 o = new contest182();
//        System.out.println(o.numTeams(new int[]{2, 5, 3, 4, 1}));
//        System.out.println(o.numTeams(new int[]{2, 1, 3}));
//        System.out.println(o.numTeams(new int[]{1, 2, 3, 4}));

//        System.out.println(o.findGoodStrings(2, "aa", "da", "b"));
//        System.out.println(o.findGoodStrings(8, "leetcode", "leetgoes", "leet"));
        System.out.println(o.findGoodStrings(6, "gxxxxx", "gzzzzz", "x"));
//        System.out.println(o.findGoodStrings(6, "aaaaaa", "zzzzzz", "xxxx"));
    }
}

