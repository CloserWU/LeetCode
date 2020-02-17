#include<iostream>
#include "../include/Date.h"
#include "../include/Utils.h"
#include "../include/Array.h"
#include "../include/BTree.h"

int main(){
    /**
    Test1* t = new Test1();
    Utils* util = new Utils();

    int p = 0;
    std::cout << p << std::endl;
    t->function1(p);
    std::cout << p << std::endl;
    t->function2(p);
    std::cout << p << std::endl;
    p = 0;
    t->function3(&p);
    std::cout << p << std::endl;


    util->vectorOpt();std::cout << std::endl;
    std::cout << util->fac(31, 2) << std::endl;
    util->Reverse( - util->fac(31, 2));


    BTree* tree = new BTree(1);
    std::vector<int> v;
    for(int i = 0; i < 10; i++){
        v.push_back(i);
    }
    v[1] = -1;
    BTree* root = tree->generateBTree(v);
    //tree->preOrder(root);
    tree->levelOrder_v2(root);
    return 0;
    std::cout << "hello world!" << std::endl;
    */
    Array obj;
    std::vector<int> para = {1,2};
    obj.rotate(para, 5);
    for(int i = 0; i < para.size(); i++) {
        std::cout<<para[i];
    }
    //std::cout<<1254 % 2222;
}
