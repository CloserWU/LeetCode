package com.wushuai.contest;

import org.junit.Test;

import java.util.Arrays;

/**
 * <p>contest181</p>
 * <p>description</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-03-24 16:05
 */
public class contest181 {
    /**
     * 1389. 按既定顺序创建目标数组
     * @param nums
     * @param index
     * @return
     */
    public int[] createTargetArray(int[] nums, int[] index) {
        int[] target = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            target[i] = -1;
        }
        for (int i = 0; i < nums.length; i++) {
            if (target[index[i]] != -1) {
                for (int j = nums.length - 1; j > index[i]; j--) {
                    target[j] = target[j - 1];
                }
            }
            target[index[i]] = nums[i];
        }
        return target;
    }

    /**
     * 1390. 四因数
     * @param nums
     * @return
     */
    public int sumFourDivisors(int[] nums) {
        int ans = 0;
        for (int i = 0; i < nums.length; i++) {
            int n = 2;
            int target = nums[i];
            int tmp = 1 + target;
            for (int j = 2; j <= Math.sqrt(target); j++) {
                if (target % j == 0) {
                    if (target / j == j) {
                        n += 1;
                        tmp += j;
                    } else {
                        n += 2;
                        tmp += target / j + j;
                    }
                }
            }
            if (n == 4) {
                ans += tmp;
            }
        }
        return ans;
    }

    /**
     * 1391. 检查网格中是否存在有效路径
     * 在grid上进行路径模拟
     * @param grid
     * @return
     */
    public boolean hasValidPath(int[][] grid) {
        int row = grid.length;
        int col = grid[0].length;
        // 每个street都有两个方向，需要分别考虑
        // 1 向上， 2 向下， 3向左， 4 向右
        int[] dx = new int[2];
        // 初始化为false
        boolean[] flag = new boolean[]{true, true};
        // 初始化两个方向 dx指 是从哪个方向到的这一格的时候
        if (grid[0][0] == 1) {
            dx[0] = 3;
            dx[1] = 4;
        } else if (grid[0][0] == 2) {
            dx[0] = 1;
            dx[1] = 2;
        }
        else if (grid[0][0] == 3) {
            dx[0] = 1;
            dx[1] = 4;
        }
        else if (grid[0][0] == 4) {
            dx[0] = 3;
            dx[1] = 1;
        }
        else if (grid[0][0] == 5) {
            dx[0] = 2;
            dx[1] = 4;
        }
        else {
            dx[0] = 2;
            dx[1] = 3;
        }
        // 分别从两个方向走，记录能不能达到目的地
        for (int k = 0; k < 2; k++) {
            int i = 0, j = 0;
            boolean tt = false;
            while (true) {
                // 越出边界，false
                if (j < 0 || i < 0 || i >= row || j >= col) {
                    flag[k] = false;
                    break;
                }
                //当走到目的地时，先不记录，看最后一格走不走得通，走了之后再判断
                if (i == row - 1 && j == col - 1) {
                    tt = true;
                }
                //每格分情况考虑， 并修改grid的索引，到下一步，并将记录去得方向
                // 对于street4，有往左走(3)到达4，和往上(1)走到达4两种情况；相应的，去的方向是下(2)和右(4)
                //如果从其他方向到达street4，则路是不连通的，直接返回
                switch (grid[i][j]) {
                    case 1:
                        if (dx[k] == 3) {
                            j--;
                            dx[k] = 3;
                        } else if (dx[k] == 4) {
                            j++;
                            dx[k] = 4;
                        } else {
                            flag[k] = false;
                        }
                        break;
                    case 2:
                        if (dx[k] == 1) {
                            i--;
                            dx[k] = 1;
                        } else if (dx[k] == 2) {
                            i++;
                            dx[k] = 2;
                        } else {
                            flag[k] = false;
                        }
                        break;
                    case 3:
                        if (dx[k] == 4) {
                            i++;
                            dx[k] = 2;
                        } else if (dx[k] == 1) {
                            j--;
                            dx[k] = 3;
                        } else {
                            flag[k] = false;
                        }
                        break;
                    case 4:
                        if (dx[k] == 3) {
                            dx[k] = 2;
                            i++;
                        } else if (dx[k] == 1) {
                            j++;
                            dx[k] = 4;
                        } else {
                            flag[k] = false;
                        }
                        break;
                    case 5:
                        if (dx[k] == 4) {
                            i--;
                            dx[k] = 1;
                        } else if (dx[k] == 2) {
                            dx[k] = 3;
                            j--;
                        } else {
                            flag[k] = false;
                        }
                        break;
                    case 6:
                        if (dx[k] == 2) {
                            dx[k]= 4;
                            j++;
                        } else if (dx[k] == 3) {
                            dx[k] = 1;
                            i--;
                        } else {
                            flag[k] = false;
                        }
                        break;
                    default:
                        break;
                }
                if (!flag[k]) {
                    break;
                }
                // 倘若走到了目的地，则要判断目的地和上一步是否连通
                // 若不连通，则flag[k]必定为false，所欲返回tt && flag[k]
                if (tt) {
                    flag[k] = tt && flag[k];
                    break;
                }
            }
        }
        return flag[0] || flag[1];
    }

    /**
     * 1392. 最长快乐前缀
     * 方法一
     * 字符串哈希 见 strhash.jpg
     * https://leetcode-cn.com/problems/longest-happy-prefix/solution/zui-chang-kuai-le-qian-zhui-zi-fu-chuan-hashjian-j/
     * 字符串种每个位置i， 计算[0,i]的哈希值，存到hash[i]中
     * 对于求字符串的最大公共前后缀，字符串哈希很方便。
     * 只需要O(n)一次遍历即可
     * 字符串哈希公式：
     * 对于子串S，和字符c
     *      H(S + c) = (H(S) * P + value[c]) MOD M;
     * 对于字串H(S + T) 和 字串H(S)
     *      H(T) = (H(S + T) - H(S) * p(length(T)) MOD M;
     *  hash[] 即为 H()
     *  p[] 即为p(length())
     *  hash[] 和 p[] 都用unsigned long long 存，溢出代表自动模2^64 即为M
     *  P为131
     *
     *  字符串哈希在前后缀问题效果极佳
     *  字符串哈希也能进行字符串模式匹配，但复杂度为O(n^2)，相当于传统暴力方法。
     *
     *  https://www.cnblogs.com/moyujiang/p/11213535.html
     * @param s
     * @return
     */
    public String longestPrefix(String s) {
        // java种没有无符号数
        /* Integer类
        int compareUnsigned(int x, int y)
        int  divideUnsigned(int dividend, int divisor)
        int  parseUnsignedInt(String s)
        int  parseUnsignedInt(String s, int radix)
        int  remainderUnsigned(int dividend,  int divisor)
        long  toUnsignedLong(int x)
        String toUnsignedString(int i)
        String toUnsignedString(int i, int radix)
         */
        /* c++
        string longestPrefix(string s) {
            int base = 131;
            unsigned long long p[100002];
            unsigned long long hash[10002];
            p[0] = 1;
            hash[0] = 0;
            // 构建hash[] p[]
            // H(S + c) = (H(S) * P + value[c]) MOD M;
            for (int i = 1; i <= s.length(); i++) {
                hash[i] = hash[i - 1] * bash + s[i - 1] - 'a' + 1;
                p[i] = p[i - 1] * bash;
            }
            //pre为[0,i]子串的hash值
            //suf为[len - i, len]字串的哈希值，但注意求suf时，根据图片里的公式描述，要计算P_Length,所以要计算p数组
            // H(T) = (H(S + T) - H(S) * p(length(T)) MOD M;
            for (int i = s.length() - 1; i >=1; i--) {
                unsigned long long pre = hash[i];
                unsigned long long suf = hash[s.length()] - hash[s.length() - i] * p[i];
                if (pre == suf) {
                    return s.substr(0, i);
                }
            }
            return "";
         }
         */
        return "";
    }

    class KMP {
        int[] next = null;

        void getNext(String pattern) {
            next = new int[pattern.length() + 1];
            next[0] = -1;
            int i = 0, j = -1;
            while (i < pattern.length()) {
                if (j == -1 || pattern.charAt(i) == pattern.charAt(j)) {
                    next[++i] = ++j;
                } else {
                    j = next[j];
                }
            }
        }

        // 在S种找到第一次P出现的位置
        int match(String pattern, String source) {
            getNext(pattern);
            int i = 0;
            int j = 0;
            int sLen = source.length();
            int pLen = pattern.length();
            while (i < sLen && j < pLen) {
                if (j == -1 || source.charAt(i) == pattern.charAt(j)) {
                    i++;
                    j++;
                } else {
                    j = next[j];
                }
            }
            if (j == pLen) {
                return i - j;
            }
            return -1;
        }
    }

    /**
     * 1392. 最长快乐前缀
     * 方法二
     * KMP， next数组的含义是，next[i]表示从p[0,i]字串的最大公共前后缀
     * 例    a b a b a b
     * next  0 0 1 2 3 4
     *       l e e t c o d e l e e t
     * next  0 0 0 0 0 0 0 0 1 2 3 4
     * 所以next的最后一个元素即为所求
     *
     *
     * https://segmentfault.com/a/1190000008575379?utm_medium=referral&utm_source=tuicool
     * @param s
     * @return
     */
    public String longestPrefix_v2(String s) {
        KMP o = new KMP();
        o.getNext(s);
        return s.substring(0, o.next[s.length()]);
    }

    @Test
    public void test1() {
        contest181 obj = new contest181();
        System.out.println(obj.hasValidPath(new int[][]{{2,4,3},{6,5,2}}));
        System.out.println(obj.hasValidPath(new int[][]{{1,2,1},{1,2,1}}));
        System.out.println(obj.hasValidPath(new int[][]{{1,1,2}}));
        System.out.println(obj.hasValidPath(new int[][]{{1,1,1,1,1,1,3}}));
        System.out.println(obj.hasValidPath(new int[][]{{2},{2},{2},{2},{2},{2},{6}}));
        System.out.println();
        System.out.println(obj.hasValidPath(new int[][]{{2,4,3},{6,5,2},{1,1,5}}));
        System.out.println(obj.hasValidPath(new int[][]{{4,1,3},{2,6,2},{6,1,5}}));
        System.out.println(obj.hasValidPath(new int[][]{{6,3,6},{4,5,2},{6,1,1}}));
        System.out.println(obj.hasValidPath(new int[][]{{3,4,3,2},{6,5,6,3},{4,1,1,5},{6,1,5,6}}));

        System.out.println(obj.longestPrefix_v2("level"));
        System.out.println(obj.longestPrefix_v2("ababab"));

        System.out.println("aaaaaa".substring(0, 3));
    }
}

