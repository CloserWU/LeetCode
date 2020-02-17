package com.wushuai.string;

import org.junit.Test;

import java.util.Stack;

/**
 * <p>IsValid</p>
 * <p>20</p>
 *
 * @author closer
 * @version 1.0.0
 * @date 2020-02-01 16:16
 */
public class IsValid {
    public boolean isValid(String s) {
        if (s.length() == 1) {
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '[' || s.charAt(i) == '{') {
                stack.push(s.charAt(i));
            } else {
                if (stack.empty()) {
                    return false;
                }
                Character ch = stack.pop();
                if (s.charAt(i) == ']' && ch == '[') {
                    ;
                } else if (s.charAt(i) == ')' && ch == '(') {
                    ;
                } else if (s.charAt(i) == '}' && ch == '{') {
                    ;
                } else {
                    return false;
                }
            }
        }
        return stack.empty();
    }

    public boolean isValid_v2(String s) {
        while (s.contains("{}") || s.contains("[]") || s.contains("()")) {
            s = s.replaceAll("\\[\\]", "");
            s = s.replaceAll("\\(\\)", "");
            s = s.replaceAll("\\{\\}", "");
        }
        return s.equals("");
    }

    @Test
    public void test() {
        System.out.println(isValid("())))((("));
        System.out.println(isValid("[][]()(){[]([])}"));
        System.out.println(isValid_v2("[][]()(){[]([])}"));
        System.out.println(isValid_v2("(("));
    }
}

