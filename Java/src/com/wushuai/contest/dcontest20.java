package com.wushuai.contest;

import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;

/**
 * <p>dcontest20</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-20 17:45
 */
public class dcontest20 {
    /**
     * 1356. 根据数字二进制下 1 的数目排序
     * 十进制转成十六进制：
     * Integer.toHexString(int i)
     * 十进制转成八进制
     * Integer.toOctalString(int i)
     * 十进制转成二进制
     * Integer.toBinaryString(int i)
     * 十六进制转成十进制
     * <p>
     * Integer.valueOf("FFFF",16).toString()
     * 八进制转成十进制
     * Integer.valueOf("876",8).toString()
     * 二进制转十进制
     * Integer.valueOf("0101",2).toString()
     * <p>
     * parseInt("-FF", 16)
     *
     * @param arr
     * @return
     */
    public int[] sortByBits(int[] arr) {
        func1[] o = new func1[arr.length];
        int[] res = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            String str = Integer.toBinaryString(arr[i]);
            int numi = 0;
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == '1') {
                    numi++;
                }
            }
            o[i] = new func1(str, numi, arr[i]);
        }
        Arrays.sort(o, new Comparator<func1>() {
            @Override
            public int compare(func1 o1, func1 o2) {
                if (o1.num1 == o2.num1) {
                    return Integer.compare(o1.val, o2.val);
                }
                return Integer.compare(o1.num1, o2.num1);
            }
        });
        for (int i = 0; i < o.length; i++) {
            res[i] = o[i].val;
        }
        return res;
    }

    class func1 {
        String str;
        int num1;
        int val;

        public func1(String str, int num1, int val) {
            this.str = str;
            this.num1 = num1;
            this.val = val;
        }
    }


    /**
     * 1357. 每隔 n 个顾客打折
     * Your Cashier object will be instantiated and called as such:
     * Cashier obj = new Cashier(n, discount, products, prices);
     * double param_1 = obj.getBill(product,amount);
     */
    class Cashier {
        int n;
        int discount;

        int[] map;
        int idx;

        public Cashier(int n, int discount, int[] products, int[] prices) {
            this.n = n;
            this.discount = discount;
            this.idx = 1;
            // 这里要开到题目要求最满
            this.map = new int[201];
            for (int i = 0; i < products.length; i++) {
                map[products[i]] = prices[i];
            }
        }

        public double getBill(int[] product, int[] amount) {
            double sum = 0;
            for (int i = 0; i < product.length; i++) {
                sum += map[product[i]] * amount[i];
            }
            if (idx == n) {
                idx = 1;
                sum *= 1 - (double) discount / 100;
            } else {
                idx++;
            }
            return sum;
        }
    }

    /**
     * 1358. 包含所有三种字符的子字符串数目
     *
     * 双指针
     * 右指针从左到右遍历字符串，遇到a，b，c就记录次数，
     * 一旦abc次数都大于1了，就从左指针开始遍历
     * 遇到a，b，c，相应次数就减一，直到左右指针区间内abc不同时出现，
     * 每次循环，都将右指针到末尾的间隔累加起来，表示以左指针开头的，含有a，b，c的字串个数
     *
     * @param s
     * @return
     */
    public int numberOfSubstrings(String s) {
        int pos1 = 0, pos2 = 0;
        int res = 0;
        int[] list = new int[3];
        for(pos2 = 0; pos2 < s.length(); pos2++) {
            if(s.charAt(pos2) == 'a' || s.charAt(pos2) == 'b' || s.charAt(pos2) == 'c') {
                list[s.charAt(pos2) - 'a']++;
            }
            while(list[0] != 0 && list[1] != 0 && list[2] != 0) {
                res += s.length() - pos2;
                if(s.charAt(pos1) == 'a' || s.charAt(pos1) == 'b' || s.charAt(pos1) == 'c') {
                    list[s.charAt(pos1) - 'a']--;
                }
                pos1++;
            }
        }
        return res;
    }

    int MOD = 1000000007;

    /**
     * 1359. 有效的快递序列数目
     * 数学题
     *
     * 考虑某个 2 的解 (P1,P2,D1,D2)。不难发现，将 P3 和 D3 插入到此序列中无非以下两种方法：
     * 1 插入的时候二者连续出现。根据约束，只能先 P3 后 D3，可插入的空位有 C<1, 5> = 5 个；
     * 2 插入的时候二者隔开出现。根据约束，D3 插入的空位只能在 P3 后，把这两个空位开成一组，共有 C<2, 5> = 10 组。
     * 因此，从 (P1,P2,D1,D2) 出发扩展出来的 3 的解共有 (5 + 10 =15) 个。
     * 而每一个 2 的解都如上述方法能扩展出来 15 个 3 的解，且不难看出这些解之间没有任何重复，
     * 那么 3 的解就是 6 * 15 = 90。
     *
     *
     * P(n)=P(n−1)×(C<2, 2(n-1)+1> + C<1, 2(n-1)+1>
     *
     * @param n
     * @return
     */
    public int countOrders(int n) {
        long P = 1;
        for (int i = 2; i <= n; i++) {
            long a = 2 * (i - 1) + 1;
            long b = a * (a - 1) / 2 + a;
            P = (b * P) % MOD;
        }
        return (int)P;
    }


    @Test
    public void test1() {
        dcontest20 obj = new dcontest20();
        System.out.println(Arrays.toString(obj.sortByBits(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8})));
    }
}

