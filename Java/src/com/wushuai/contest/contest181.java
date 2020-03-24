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
    }
}

