#include "../include/Utils.h"

void showFunc(Date* v){
    std::cout << v->getDay() << " " << v->getYear() << " " << std::endl;
}
bool sortFunc(Date* d1, Date* d2){
    return d1->getYear() < d2->getYear();
}


int Utils::vectorOpt(){
    std::srand((short)time(NULL));


    std::vector<int> v(10);
    std::vector<Date*> d;
    //d.push_back(new Date(1998, 4, 24));
    //std::cout << d[0]->getDay() << std::endl;
    for (int i = 0; i < 10 ; i++) {
        Date* date = new Date(std::rand(), 04, i);
        d.push_back(date);
    }


    /** iteration method 1
    std::vector<Date*>::iterator iter = d.begin();
    for (iter = d.begin(); iter != d.end(); iter++){
        std::cout << (*iter)->getDay() << " ";
    }*/


    /// iteration method 2
    std::vector<Date*>::iterator beg = d.begin();
    std::vector<Date*>::iterator end = d.end();

    for_each(beg, end, showFunc);
    std::cout << std::endl;

    sortByYear(d);

    for_each(beg, end, showFunc);
    std::cout << std::endl;

    return 0;
}
int Utils::arrayOpt(){
    return 0;
}

int Utils::sortByYear(std::vector<Date*> &v){  // DO NOT forget & !!!
    std::sort(v.begin(), v.end(), sortFunc);
    /*std::vector<Date*>::iterator iter = v.begin();
    for (iter = v.begin(); iter != v.end(); iter++){
        std::cout << (*iter)->getYear() << " ";
    }*/
    return 0;
}

int Utils::sortByMonth(){
    return 0;
}

int Utils::sortByDay(){
    return 0;
}
long long Utils::fac(int x, int d){
    long long ans = 1;
    for(int i = 0; i < x; i++)
        ans *= d;
    return ans;

}

void Utils::Reverse(int x){
    long long maxNum = fac(31, 2);
    long long minNum = -fac(31, 2);

    long long ans = 0;
    while(x != 0) {
        ans = ans * 10 + (long long)x % 10;
        x /= 10;
    }

    if (ans >= maxNum || ans < minNum)
        std::cout << 0 << std::endl;
    else
        std::cout << ans << std::endl;

}
