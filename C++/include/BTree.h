#include<bits/stdc++.h>
#include<string>
class BTree {
private:
    int val;
    BTree *left;
    BTree *right;
public:
    BTree(int x) : val(x), left(NULL), right(NULL) {};
    virtual ~BTree();
    BTree* generateBTree(std::vector<int> v);
    bool isSameTree(BTree* p, BTree* q);
    bool isSymmetric(BTree* a, BTree* b);
    int maxDepth(BTree* root); // recursive
    int maxDepth_v2(BTree* root); // level order non-recursive
    void preOrder(BTree *root);
    void preOrder_v2(BTree *a, BTree *b, bool& flag);
    std::vector<std::vector<int> > levelOrder_v2(BTree* root);
    BTree* sortedArrayToBST(std::vector<int>& nums);
    BTree* sortedArrayToBST(std::vector<int>& nums, int low, int high);
    bool isBalanced(BTree* root);
    int isBalanced(BTree* root, bool& flag);
    int minDepth(BTree* root);
    bool hasPathSum(BTree* root, int sum);
    void inOrder(BTree* root, int val, std::vector<int>& v);
    BTree* lowestCommonAncestor(BTree* root, BTree* p, BTree* q);
    std::vector<std::string> binaryTreePaths(BTree* root);
    void binaryTreePaths_dfs(BTree* root, std::vector<std::string>& res, std::string path);
    int sumOfLeftLeaves(BTree* root);
    int ans;
    int pathSum(BTree* root, int sum);
    void pathSum_back(BTree* root, int sum);
    std::vector<int> findMode(BTree* root);
    void findMode_back(BTree* root, BTree*& pre, int& cur, int& max, std::vector<int>& res);
    int getMinimumDifference(BTree* root);
    void getMinimumDifference_back(BTree* root, BTree*& pre, int& _min);
    BTree* convertBST(BTree* root);
    void convertBST_back(BTree* root, int& val);

};
