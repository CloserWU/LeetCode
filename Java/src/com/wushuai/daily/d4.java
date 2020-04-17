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
        System.out.println(o.canJump(new int[]{4,2,1,0,4,2,1,0,1,2,3,0}));
        System.out.println(o.canJump(new int[]{3,2,1,0,4}));
        System.out.println(o.canJump(new int[]{2,3,1,1,4}));
    }
}
