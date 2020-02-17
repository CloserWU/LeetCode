#include "../include/Test1.h"

Test1::Test1()
{
    //ctor
    std::cout << "Construction function" << std::endl;
}

Test1::~Test1()
{
    //dtor
    std::cout << "De-Construction Function" <<std::endl;
}
int Test1::function1(int p){
    p = 1;
    return 0;
}
int Test1::function2(int& p){
    p = 1;
    return 0;
}
int Test1::function3(int* p){
    *p = 2;
    return 0;
}

