#ifndef TEST1_H
#define TEST1_H
#include<iostream>
#include<string>
#include "Date.h"
class Test1
{
    public:
        Test1();
        virtual ~Test1();
        int function1(int p);
        int function2(int& p);
        int function3(int* p);
    protected:

    private:
        int age;
        std::string name;
        std::string creditNumber;
        std::string birthday;
        Date date;
};

#endif // TEST1_H
