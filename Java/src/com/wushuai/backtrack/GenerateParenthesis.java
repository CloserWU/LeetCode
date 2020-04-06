package com.wushuai.backtrack;

import org.junit.Test;

import java.util.*;

/**
 * <p>GenerateParenthesis</p>
 * <p>
 *     回溯算法
 *     22
 * </p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-04-06 09:00
 */
public class GenerateParenthesis {

    char leftOf(char c) {
        if (c == ')') {
            return '(';
        } else if (c == '}') {
            return '{';
        } else {
            return '[';
        }
    }

    boolean isValid(String str) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(' || str.charAt(i) == '{' || str.charAt(i) == '[') {
                stack.push(str.charAt(i));
            } else {
                if (!stack.isEmpty() && leftOf(str.charAt(i)) != stack.peek()) {
                    stack.pop();
                } else {
                    return false;
                }
            }
        }
        return stack.isEmpty();
    }


    void backTrack(int left, int right, StringBuilder str, List<String> list) {
        if (left > right) {
            return;
        }
        if (left < 0 || right < 0) {
            return;
        }
        if (left == 0 && right == 0) {
            list.add(str.toString());
            return;
        }
        str.append('(');
        backTrack(left - 1, right, str, list);
        str.setLength(str.length() - 1);

        str.append(')');
        backTrack(left, right - 1, str, list);
        str.setLength(str.length() - 1);
    }


    public List<String> generateParenthesis(int n) {
        StringBuilder str = new StringBuilder();
        List<String> list = new ArrayList<>();
        backTrack(n, n, str, list);
        return list;
    }

    @Test
    public void test1() {
        GenerateParenthesis o = new GenerateParenthesis();
        System.out.println(o.generateParenthesis(0));
    }
}

