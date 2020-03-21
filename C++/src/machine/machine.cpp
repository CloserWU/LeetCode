//
// Created by Closer on 2020/3/21.
//
#include <bits/stdc++.h>

using namespace std;

/**
cout<<hex<<"hex:i="<<static_cast<unsigned int>(i)<<endl;
cout<<oct<<"oct:i="<<static_cast<unsigned int>(i)<<endl;
cout<<dec<<"dec:i="<<static_cast<unsigned int>(i)<<endl;
cout<<"bin:i="<<bitset<sizeof(unsigned int)*8>(i)<<endl;  // 指定位数
 printf("八进制  %o\n",a);
    printf("十六进制 %x\n",a);
    printf("十进制 %d\n",a);
    printf是格式化输出函数，它可以直接打印十进制，八进制，十六进制，输出控制符分别为%d, %o, %x, 但是它不存在二进制，
 */
void func1() {
    int n;
    int i = 0;
    int a[sizeof(unsigned int) * 8] = {0};
    while (cin >> n) {
        i = 0;
        memset(a, 0, sizeof(a));
        while (n != 0) {
            a[i] = n % 2;
            n /= 2;
        }
        for (int j = i - 1; j >= 0; --j) {
            printf("%d", a[j]);
        }
        printf("\n");
    }
}

void func2() {
    int n;
    cin >> n;
    priority_queue<int, vector<int>, greater<>> queue;
    for (int i = 0; i < n; ++i) {
        int rmp;
        cin >> rmp;
        queue.push(rmp);
    }
    int res = 0;
    while (queue.size() > 1) {
        int a = queue.top();
        queue.pop();
        int b = queue.top();
        queue.pop();
        res += a + b;
        queue.push(a + b);
    }
    cout << res << endl;

}


void func3() {
    int n;
    while (cin >> n) {
        int a[n];
        int even = 0;
        int odd = 0;
        for (int i = 0; i < n; ++i) {
            cin >> a[i];
            if (a[i] % 2 == 0) {
                odd++;
            } else {
                even++;
            }
        }
        if (odd > even) {
            cout << "NO" << endl;
        } else {
            cout << "YES" << endl;
        }
    }
}

void func4() {
    int n;
    while (cin >> n) {
        int a[n];
        for (int i = 0; i < n; ++i) {
            cin >> a[i];
        }
        sort(a, a + n);
        int k;
        cin >> k;
        int tmp = a[0];
        for (int i = 1; i < n; ++i) {
            if (k == 1) {
                cout << tmp << endl;
                break;
            }
            if (tmp != a[i]) {
                k--;
                tmp = a[i];
            }
        }
    }
}

void func4_v2() {
    int n;
    while (cin >> n) {
        priority_queue<int, vector<int>, greater<int>> queue;
        set<int> s;
        for (int i = 0; i < n; ++i) {
            int tmp;
            cin >> tmp;
            queue.push(tmp);
        }
        int idx;
        cin >> idx;
        for (int i = 0; i < n; ++i) {
            s.insert(queue.top());
            if (s.size() == idx) {
                cout << queue.top() << endl;
                break;
            }
            queue.pop();
        }
    }
}