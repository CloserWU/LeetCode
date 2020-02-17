#include"../include/BTree.h"

BTree::~BTree(){
    this->right = NULL;
    this->left = NULL;
    delete(this);
}

BTree* BTree::generateBTree(std::vector<int> v){
    std::vector<BTree*> root(v.size() + 1);
    root[0] = NULL;
    for(int i = 1; i <= v.size(); i++) {
        root[i] = new BTree(v[i - 1]);
    }
    for(int i = 1; i <= v.size() / 2; i++) {
        if(root[i]->val != -1) {
            if(i * 2 <= v.size() && root[i * 2]->val != -1) root[i]->left = root[i * 2];
            if(i * 2 + 1 <= v.size() && root[i * 2 + 1]->val != -1) root[i]->right = root[i * 2 + 1];
        }

    }
    return root[1];
}

void BTree::preOrder(BTree *root){
    if(root != NULL) {
        std::cout<< root->val << " ";
        preOrder(root->left);
        preOrder(root->right);
    }
}

void BTree::preOrder_v2(BTree *a, BTree *b, bool& flag){
    if(a == NULL && b != NULL) flag = false;
    else if(a != NULL && b == NULL) flag = false;
    else if(a == NULL && b == NULL) return;
    else {
        if(a->val != b->val) flag = false;
        preOrder_v2(a->left, b->left, flag);
        preOrder_v2(a->right, b->right, flag);
    }
}

bool BTree::isSameTree(BTree *p, BTree *q){
    bool flag = true;
    preOrder_v2(p, q, flag);
    return flag;
}

/**
public boolean isSameTree(TreeNode p, TreeNode q) {
    if(p==null && q==null){
        return true;
    }

    if(p!=null && q!=null && p.val==q.val  ){
        return isSameTree(p.left,q.left) && isSameTree(p.right,q.right);
    }else {
        return false;
    }
}
*/

bool BTree::isSymmetric(BTree* a, BTree* b){
    if(a == NULL && b == NULL) return true;
    if(a != NULL && b != NULL && a->val == b->val){
        bool f1 = isSymmetric(a->left, b->right);
        bool f2 = isSymmetric(a->right, b->left);
        return f1 && f2;
    } else return false;

}

int BTree::maxDepth(BTree* root){
    if(root != NULL) {
        int left = maxDepth(root->left);
        int right = maxDepth(root->right);
        return 1 + std::max(left, right);
    }
    return 0;

}
int BTree::maxDepth_v2(BTree* root){
    if(root == NULL) return 0;
    std::queue<BTree*> que;
    int level = 0, last = 0;
    int _front = -1, rear = -1;
    que.push(root);rear++;
    while(_front < rear){
        BTree* t = que.front();_front++;que.pop();
        if(t && t->left != NULL) {que.push(t->left);rear++;}
        if(t && t->right != NULL) {que.push(t->right);rear++;}
        if(last == _front){
            level++;
            last = rear;
        }

    }
    return level;
}

std::vector<std::vector<int> > BTree::levelOrder_v2(BTree* root){
    std::vector<std::vector<int> > v;
    if(root == NULL) return v;
    std::queue<BTree*> que;
    int last = 0, rear = -1, _front = -1;
    que.push(root);rear++;
    std::vector<int> row;
    while(_front < rear) {
        BTree* t = que.front();
        que.pop();
        _front++;
        if(t) row.push_back(t->val);
        if(t && t->left != NULL) {que.push(t->left);rear++;}
        if(t && t->right != NULL) {que.push(t->right);rear++;}
        if(_front == last){
            last = rear;
            v.push_back(row);
            row.clear();
        }
    }
    reverse(v.begin(), v.end());
    return v;
}
BTree* BTree::sortedArrayToBST(std::vector<int>& nums, int low, int high){
    if(high < low) return NULL;
    int mid = low + ((high - low) >> 1);
    BTree* root = new BTree(nums[mid]);
    root->left = sortedArrayToBST(nums, low, mid - 1);
    root->right = sortedArrayToBST(nums, mid + 1, high);
    return root;
}
BTree* BTree::sortedArrayToBST(std::vector<int>& nums) {
    return sortedArrayToBST(nums, 0, nums.size() - 1);
}
int BTree::isBalanced(BTree* root, bool& flag){
    if(root != NULL && flag) {
        int left = isBalanced(root->left, flag) + 1;
        int right = isBalanced(root->right, flag) + 1;
        if(abs(left - right) >= 2)
            flag = false;
        return std::max(left, right);
    }
    return 0;
}
bool BTree::isBalanced(BTree* root) {
    bool flag = true;
    isBalanced(root, flag);
    return flag;
}
int BTree::minDepth(BTree* root){
    if(root == NULL) return 0;
    int last = 0, level = 0;
    int _front = -1, rear = -1;
    std::queue<BTree*> que;
    que.push(root);rear++;
    while(!que.empty()) {
        BTree* t = que.front();
        que.pop();_front++;
        if(t == NULL);
        else if(t->left == NULL && t->right == NULL){
            return level + 1;
        }else {
            que.push(t->left);rear++;
            que.push(t->right);rear++;
        }
        if(last == _front){
            level++;
            last = rear;
        }
    }
    return level;
}
bool BTree::hasPathSum(BTree* root, int sum){
    std::vector<int> v;
    inOrder(root, 0, v);
    return (std::find(v.begin(), v.end(), sum) != v.end());
}
void BTree::inOrder(BTree* root, int val, std::vector<int>& v){
    if(root) {
        inOrder(root->left, val + root->val, v);
        inOrder(root->right, val + root->val, v);
        if(root->left == NULL && root->right == NULL)  v.push_back(val + root->val);
    }
}
/**
hasPathSum(TreeNode root, int sum) {
    if (root == null) {
        return false;
    }
    if (root.left == null && root.right == null) {
        return sum - root.val == 0;
    }
    return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
}
*/

BTree* BTree::lowestCommonAncestor(BTree* root, BTree* p, BTree* q){
    if((p->val - root->val) * (q->val - root->val) <= 0 )
        return root;
    if(p->val - root->val < 0 && q->val - root->val < 0)
        return lowestCommonAncestor(root->left, p, q);
    else
        return lowestCommonAncestor(root->right, p, q);
}

std::vector<std::string> BTree::binaryTreePaths(BTree* root){
    std::vector<std::string> res;
    binaryTreePaths_dfs(root, res, "");
    return res;
}

void BTree::binaryTreePaths_dfs(BTree* root, std::vector<std::string>& res, std::string path){
    if(root){
        path = path; //+ "->" + std::to_string(root->val) // + root->val
        binaryTreePaths_dfs(root->left, res, path);
        binaryTreePaths_dfs(root->right, res, path);
        if(root->left == NULL && root->right == NULL) {
            res.push_back(path.substr(2));
        }
    }
}

int BTree::sumOfLeftLeaves(BTree* root){
    if(root == NULL) return 0;
    else {
        int sum = 0;
        if(root->left) {
            if(root->left->left == NULL && root->left->right == NULL){
                sum = root->left->val;
            }
        }
        sum += sumOfLeftLeaves(root->left);
        sum += sumOfLeftLeaves(root->right);
        return sum;
    }
}

int BTree::pathSum(BTree* root, int sum){
    if(root == NULL) return 0;
    else {
       pathSum_back(root, sum);
       pathSum(root->left, sum);
       pathSum(root->right, sum);
    }
    return ans;
}
void BTree::pathSum_back(BTree* root, int sum){
    if(root == NULL) return;
    if(root->val == sum) ans++;
    pathSum_back(root->left, sum - root->val);
    pathSum_back(root->right, sum - root->val);
}

std::vector<int> BTree::findMode(BTree* root){
    std::vector<int> res;
    if(root == NULL) return res;
    int cur = 1, max = 0;
    BTree* pre = NULL;
    findMode_back(root, pre, cur, max, res);
    return res;
}

void BTree::findMode_back(BTree* root,BTree*& pre, int& cur, int& max, std::vector<int>& res){
    if(root == NULL) return;
    findMode_back(root->left, pre, cur, max, res);
    if(pre) cur = (root->val == pre->val) ? cur + 1 : 1;    // cur++ is wrong,  ++cur or cur + 1 is right

    if(cur == max) res.push_back(root->val);
    else if(cur > max) {res.clear();res.push_back(root->val);max = cur;}
    else ;
    pre = root;
    findMode_back(root->right, pre, cur, max, res);
}

int BTree::getMinimumDifference(BTree* root){
    if(!root) return NULL;
    int _min = 100000000;
    BTree* pre = NULL;
    getMinimumDifference_back(root, pre, _min);
    return _min;

}

void BTree::getMinimumDifference_back(BTree* root, BTree*& pre, int& _min){
    if(!root) return;
    if(pre) _min = std::min(std::abs(root->val - pre->val), _min);
    pre = root;
    getMinimumDifference_back(root, pre, _min);
}
//将二叉树转换为累加树
BTree* BTree::convertBST(BTree* root) {
    if(root == NULL) return root;
    int val = 0;
    convertBST_back(root, val);
    return root;
}

void BTree::convertBST_back(BTree* root, int& val){
    if(root){
        convertBST_back(root->right, val);
        val += root->val;
        root->val = val;
        convertBST_back(root->left, val);
    }
}
