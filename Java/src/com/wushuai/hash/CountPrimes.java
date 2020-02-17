package com.wushuai.hash;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <p>CountPrimes</p>
 * <p>
 *     204
 *     Collections.nCopies
 * </p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-13 10:39
 */


/**
 * 厄拉多塞筛法. 比如说求20以内质数的个数,首先0,1不是质数.2是第一个质数
 * ,然后把20以内所有2的倍数划去.2后面紧跟的数即为下一个质数3,
 * 然后把3所有的倍数划去.3后面紧跟的数即为下一个质数5,再把5所有的倍数划去.
 * 以此类推.
 * def countPrimes(self, n: int) -> int:
 *         if n < 3:
 *             return 0
 *         else:
 *             # 首先生成了一个全部为1的列表
 *             output = [1] * n
 *             # 因为0和1不是质数,所以列表的前两个位置赋值为0
 *             output[0],output[1] = 0,0
 *              # 此时从index = 2开始遍历,output[2]==1,即表明第一个质数为2,然后将2的倍数对应的索引
 *              # 全部赋值为0. 此时output[3] == 1,即表明下一个质数为3,同样划去3的倍数.以此类推.
 *             for i in range(2,int(n**0.5)+1):
 *                 if output[i] == 1:
 *                     output[i*i:n:i] = [0] * len(output[i*i:n:i])
 *          # 最后output中的数字1表明该位置上的索引数为质数,然后求和即可.
 *         return sum(output)
 *
 *
 */
public class CountPrimes {
    @Test
    public void test1() {
        System.out.println(countPrimes(10));
    }

    public int countPrimes(int n) {
        if (n < 3) {
            return 0;
        }
        // https://blog.csdn.net/Tracycater/article/details/77592472
        List<Integer> list = new ArrayList<>(Collections.nCopies(n, 1));
        list.set(0, 0);
        list.set(1, 0);
        for (int i = 2; i < Math.sqrt(n) + 1; i++) {
            if (list.get(i) == 1) {
                for (int j = (int) Math.pow(i, 2); j < n; j += i) {
                    list.set(j, 0);
                }
            }

        }
        int res = 0;
        for (Integer aList : list) {
            if (aList == 1) {
                res++;
            }
        }
        return res;
    }
}

