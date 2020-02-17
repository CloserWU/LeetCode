#ifndef UTILS_H
#define UTILS_H
#include<bits/stdc++.h>
#include "Date.h"
class Utils {
public:
    int vectorOpt();
    int arrayOpt();// temporarily not supported
    void Reverse(int x);
    long long fac(int x, int d);
    int sortByYear(std::vector<Date*> &v);
    int sortByMonth();
    int sortByDay();
};



#endif // UTILS_H
