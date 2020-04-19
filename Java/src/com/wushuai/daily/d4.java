package com.wushuai.daily;

import org.junit.Test;

import java.util.*;

/**
 * <p>d4</p>
 * <p>description</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-16 8:52
 */
public class d4 {

    class Slot {
        int left;
        int right;

        public Slot(int left, int right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public String toString() {
            return "{" +
                    "left=" + left +
                    ", right=" + right +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Slot slot = (Slot) o;
            return left == slot.left &&
                    right == slot.right;
        }

        @Override
        public int hashCode() {
            return Objects.hash(left, right);
        }
    }

    /**
     * 4.16
     * 56. 合并区间
     *
     * @param intervals
     * @return
     */
    public int[][] merge(int[][] intervals) {
        List<Slot> list = new ArrayList<>();
        for (int i = 0; i < intervals.length; i++) {
            int left = intervals[i][0];
            int right = intervals[i][1];
            list.add(new Slot(left, right));
        }
        list.sort(new Comparator<Slot>() {
            @Override
            public int compare(Slot o1, Slot o2) {
                if (o1.left == o2.left) {
                    return Integer.compare(o1.right, o2.right);
                }
                return Integer.compare(o1.left, o2.left);
            }
        });
        List<Slot> res = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Slot next = list.get(i);
            int left = next.left;
            int right = next.right;
            if (res.size() == 0 || res.get(res.size() - 1).right < left) {
                res.add(new Slot(left, right));
            } else {
                Slot back = res.get(res.size() - 1);
                back.right = Integer.max(right, back.right);
                res.set(res.size() - 1, back);
            }
        }
        int[][] ress = new int[res.size()][2];
        for (int i = 0; i < ress.length; i++) {
            System.out.println(res.get(i));
            ress[i][0] = res.get(i).left;
            ress[i][1] = res.get(i).right;
        }
        System.out.println();
        return ress;
    }

    public int[][] merge_v2(int[][] intervals) {
        // wrong
        /*Set<Slot> set = new LinkedHashSet<>();
        for (int i = 0; i < intervals.length; i++) {
            int left = intervals[i][0];
            int right = intervals[i][1];
            set.add(new Slot(left, right));
        }
        List<Slot> list = new ArrayList<>(set);
        list.sort(new Comparator<Slot>() {
            @Override
            public int compare(Slot o1, Slot o2) {
                if (o1.left == o2.left) {
                    return Integer.compare(o1.right, o2.right);
                }
                return Integer.compare(o1.left, o2.left);
            }
        });
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = list.size() - 1; i >= 1; i--) {
            Slot next = list.get(i);
            min = Integer.min(next.left, min);
            max = Integer.max(next.right, max);
            Slot pre = list.get(i - 1);
            if (pre.right >= max && pre.left <= min) {
                list = list.subList(0, i);
                continue;
            }
            if (next.left == next.right && i < list.size() - 1) {
                next = list.get(i + 1);
                if (next.left <= pre.right && next.left >= pre.left) {
                    pre.right = Integer.max(pre.right, next.right);
                    list.remove(i);
                    list.remove(i);
                    continue;
                }
            }
            if (next.left <= pre.right && next.left >= pre.left) {
                pre.right = Integer.max(pre.right, next.right);
                list.remove(i);
            }
        }
        int[][] res = new int[list.size()][2];
        for (int i = 0; i < res.length; i++) {
            System.out.println(list.get(i));
            res[i][0] = list.get(i).left;
            res[i][1] = list.get(i).right;
        }
        System.out.println();
        return res;*/
        return null;
    }

    /**
     * 4.17
     * 55. 跳跃游戏
     *
     * @param nums
     * @return
     */
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        boolean flag = true;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                flag = false;
                for (int j = i - 1; j >= 0; j--) {
                    if (i == nums.length - 1 && i - j < nums[j]) {
                        flag = true;
                        break;
                    }
                    if (i - j < nums[j]) {
                        flag = true;
                        break;
                    }
                }
            }
            if (!flag) {
                return false;
            }
        }
        return true;
    }

    public boolean canJump_v2(int[] nums) {
        int maxReach = 0;
        for (int i = 0; i < nums.length; i++) {
            maxReach = Integer.max(maxReach - 1, nums[i]);
            if (maxReach == 0 && i != nums.length - 1) {
                return false;
            }
        }
        return true;
    }


    /**
     * 4.19
     * 11. 盛最多水的容器
     * <p>
     * 双指针 *****
     *
     * @param height
     * @return
     */
    public int maxArea(int[] height) {
        int left = 0, right = height.length - 1;
        int res = 0;
        while (left < right) {
            res = Integer.max(res, Integer.min(height[left], height[right]) * (right - left));
            if (height[left] > height[right]) {
                right--;
            } else {
                left++;
            }
        }
        return res;
    }

    /**
     * 466. 统计重复个数
     *
     * 双指针， s1一个针织， s2一个指针，顺序操作，当s1指针走到末尾，则s1的个数加一，指针归零
     * 当s2的指针走到末尾，s2个数加一，指针归零
     * 到最后，nums2说明匹配个几个s2
     * 题目要求每n2个s2为一组，则答案就是num2 / n2
     *
     *
     * @param s1
     * @param n1
     * @param s2
     * @param n2
     * @return
     */
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        // 1720ms
        // 实验可得，对字符数组的操作快于对字符串的操作
        // 直接用s1.charAt() 会超时，数组不会1720ms
        int index = 0, num1 = 0, num2 = 0;
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        while (num1 < n1) {
            for (int i = 0; i < c1.length; i++) {
                if (c1[i] == c2[index]) {
                    // s2走到末尾， 归零
                    if (index == c2.length - 1) {
                        index = 0;
                        num2++;
                    } else {
                        index++;
                    }
                }
            }
            // s1走到末尾，归零，还有剩余s1个数，则重新开始
            num1++;
        }
        return num2 / n2;
    }


    @Test
    public void test() {
        d4 o = new d4();
        o.merge(new int[][]{{0, 0}, {1, 2}, {5, 5}, {2, 4}, {3, 3}, {5, 6}, {5, 6}, {4, 6}, {0, 0}, {1, 2}, {0, 2}, {4, 5}});
        o.merge(new int[][]{{2, 6}, {6, 8}, {4, 4}, {7, 10}, {5, 5}, {6, 7}, {9, 9}, {6, 10}});
        o.merge(new int[][]{{2, 6}, {6, 8}, {4, 4}, {7, 10}, {5, 5}, {6, 7}, {9, 9}, {6, 10}, {11, 11}});
        o.merge(new int[][]{{1, 1}, {2, 6}, {6, 8}, {4, 4}, {7, 10}, {5, 5}, {6, 7}, {9, 9}, {6, 10}, {11, 11}});
        o.merge(new int[][]{{2, 2}, {2, 6}, {6, 8}, {4, 4}, {7, 10}, {5, 5}, {6, 7}, {9, 9}, {6, 10}, {11, 11}});
        o.merge(new int[][]{{2, 6}, {6, 8}, {4, 4}, {7, 10}, {5, 5}, {6, 7}, {9, 9}, {6, 11}, {11, 11}});
        o.merge(new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}});
        o.merge(new int[][]{{1, 4}, {4, 5}});
        o.merge(new int[][]{{1, 3}, {3, 7}, {2, 6}, {2, 8}, {11, 12}, {5, 9}, {0, 7}, {3, 4}});
        o.merge(new int[][]{{1, 10}, {2, 3}, {4, 5}, {6, 7}, {8, 9}});

        System.out.println(o.canJump(new int[]{0}));
        System.out.println(o.canJump(new int[]{0, 1, 2, 3}));
        System.out.println(o.canJump(new int[]{4, 2, 1, 0, 4, 2, 1, 0, 1, 2, 3, 0}));
        System.out.println(o.canJump(new int[]{3, 2, 1, 0, 4}));
        System.out.println(o.canJump(new int[]{2, 3, 1, 1, 4}));

        System.out.println(o.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}));
        System.out.println(o.maxArea(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 6}));

        System.out.println(o.getMaxRepetitions("acb", 4, "ab", 2));
        System.out.println(o.getMaxRepetitions("acb", 8, "cb", 2));
        System.out.println(o.getMaxRepetitions("acb", 8, "cbv", 2));
        System.out.println(o.getMaxRepetitions("acb", 8, "cbb", 2));
        System.out.println(o.getMaxRepetitions("acb", 7, "cbb", 2));
    }
}
