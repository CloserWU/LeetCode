//
// Created by Closer on 2020/4/6.
//
#include <bits/stdc++.h>

using namespace std;

char leftOf(char c) {
    if (c == ')') return '(';
    if (c == '}') return '}';
    return ']';
}

// 判断括号序列是否合法
bool isValid(const string& str) {
    stack<char> left;
    for (char c : str) {
        if (c == '(' || c == '{' || c == '[')
            left.push(c);
        else // 字符c是右括号
            if (!left.empty() && leftOf(c) == left.top())
                left.pop();
            else
                // 和最近的左括号不匹配
                return false;
    }
    // 是否全部已匹配
    return left.empty();
}

void backTrack(int left, int right, string &str, vector<string> &v) {
    // 若左括号小于右括号 则不合法
    if (left < right) return;
    // 数量小于1不合法
    if (left < 0 || right < 0) return;
    // 左右括号都为0 则加入队列
    if (left == 0 && right == 0) {
        v.push_back(str);
        return;
    }
    // 尝试加一个左括号
    str.push_back('(');
    backTrack(left - 1, right, str, v);
    // 撤销选择
    str.pop_back();

    // 尝试一个右括号
    str.push_back(')');
    backTrack(left, right - 1, str, v);
    // 撤销选择
    str.pop_back();
}

vector<string> generateParenthesis(int n) {
    if (n == 0)
        return {};
    // 记录所有合法括号组合
    vector<string> v;
    // 回溯路径
    string str;
    // 可用左右括号记为n
    backTrack(n, n, str, v);
    return v;
}


