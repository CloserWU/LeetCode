package com.wushuai.backtrack;

import org.junit.Test;

import java.util.*;

/**
 * <p>Exist</p>
 * <p>79</p>
 *
 * @author wushuai
 * @version 1.0.0
 * @date 2020-04-25 9:42
 */
public class Exist {

    class Point {
        int row;
        int col;

        Point(int x, int y) {
            this.row = x;
            this.col = y;
        }
    }

    /**
     * 79. 单词搜索
     *
     * @param board
     * @param word
     * @return
     */
    public boolean exist(char[][] board, String word) {
        boolean[][] visit = new boolean[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            Arrays.fill(visit[i], false);
        }
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (word.charAt(0) == board[i][j]) {
                    boolean b = judge(0, new Point(i, j), visit, board, word);
                    if (b) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    boolean judge(int idx, Point p, boolean[][] visit, char[][] board, String word) {
        if (idx == word.length() - 1) {
            if (word.charAt(idx) == board[p.row][p.col]) {
                return true;
            } else {
                return false;
            }
        }
        if (word.charAt(idx) != board[p.row][p.col]) {
            return false;
        }
        // 状态加入
        visit[p.row][p.col] = true;
        if (p.row > 0 && !visit[p.row - 1][p.col]) {
            boolean b = judge(idx + 1, new Point(p.row - 1, p.col), visit, board, word);
            if (b) {
                return true;
            }
        }
        if (p.col > 0 && !visit[p.row][p.col - 1]) {
            boolean b = judge(idx + 1, new Point(p.row, p.col - 1), visit, board, word);
            if (b) {
                return true;
            }
        }
        if (p.row < board.length - 1 && !visit[p.row + 1][p.col]) {
            boolean b = judge(idx + 1, new Point(p.row + 1, p.col), visit, board, word);
            if (b) {
                return true;
            }
        }
        if (p.col < board[0].length - 1 && !visit[p.row][p.col + 1]) {
            boolean b = judge(idx + 1, new Point(p.row, p.col + 1), visit, board, word);
            if (b) {
                return true;
            }
        }
        // 回溯
        visit[p.row][p.col] = false;
        return false;
    }

    @Test
    public void test() {
        Exist o = new Exist();

    }
}
