#include "../include/Array.h"

std::vector<int> Array::twoSum(std::vector<int>& nums, int target){
    std::vector<int> ans;
    std::vector<int>::iterator it;
    for (int i = 0; i < nums.size() - 1; i++ ) {
        for (it = nums.begin() + i + 1; it != nums.end(); it++) {
            if ( nums[i] + *it == target ) {
                ans.push_back(i);
                ans.push_back(it - nums.begin());
                return ans;
            }
        }
    }
    return ans;
}

int Array::removeDuplicates(std::vector<int>& nums){
    // O(n^2)  648ms
    if(nums.size() == 0) return 0;
    for(int i = 0; i < nums.size() - 1; i++) {
        if(nums[i] == nums[i + 1]){
            for(int j = i; j < nums.size() - 1; j++) {
                nums[j] = nums[j + 1];
            }
            nums.pop_back();
            i--;
        }
    }
    return nums.size();

    //O(n)  40ms
    /**
    if(nums.size() == 0) return 0;
    int i = 0;
    for(int j = 1; j < nums.size(); j++){
        if(nums[i] != nums[j]){
            i++;
            nums[i] = nums[j];
        }
    }
    return i + 1;
    */
}

int Array::removeElement(std::vector<int>& nums, int val){
    int len = nums.size();
    if(len == 0) return 0;
    int j = nums.size() - 1;
    for(int i = 0; i <= j; i++){
        if(nums[i] == val){
            nums[i] = nums[j];
            j--;
            i--;
            len--;
        }
    }
    return len;
}

int Array::searchInsert(std::vector<int>& nums, int target){
    if(nums.size() == 0) return 0;
    for(int i = 0; i < nums.size(); i++) {
        if(nums[i] == target) return i;
        else if(i > 0 && nums[i - 1] < target && nums[i] > target) return i;
        else if(i == 0 && nums[i] > target) return 0;
    }
    return nums.size();
}

/**
自底向上 动态规划
1.义一个函数f(n) => 到第n个数为止的子数列的最大和，存在一个递推关系f(n) = max(f(n-1) + A[n], A[n])
 => 到第n个点为止的子数列最大和，即为，到第n-1个点为止的子数列最大和加上第n个点的值 与 第n个点的值 的最大值;
2.将这些最大和保存下来后，取最大的那个就是，最大子数组和。因为最大连续子数组 等价于 最大的以n个数为结束点的子数列和
*/
int Array::maxSubArray(std::vector<int>& nums){
    if(nums.size() == 0) return NULL;
    int q = 0;  /// q的初始值值得注意， 不能为INT_MIN， 加上一个负值后会出现越界
    int res = INT_MIN;
    int len = nums.size();
    for(int i = 0; i < len; i++) {  /// 最好将所有轮次都在循环中执行，i设置为0
        q = std::max(nums[i], nums[i] + q); /// 到第i个点的子序列最大和
        res = std::max(res, q);
    }
    res = std::max(res, q);
    return res;
}

/// 66
std::vector<int> Array::plusOne(std::vector<int>& digits) {
    /*long long sum = 0;
    for(int i = 0; i < digits.size(); i++) {
        sum = sum * 10 + digits[i];
    }
    sum++;
    std::vector<int> res;
    while(sum != 0) {
        res.push_back(sum % 10);
        sum /= 10;
    }
    for(int i = 0; i < res.size(); i ++) {
        int temp = res[i];
        res[i] = res[res.size() - i - 1];
        res[res.size() - i - 1] = temp;
    }
    return res;*/
    digits[digits.size() - 1]++;
    int len = digits.size();
    for(int i = len - 1; i > 0; i--) {
        if(digits[i] == 10) {
            digits[i] = 0;
            digits[i - 1]++;
        }
    }
    if(digits[0] == 10){
        digits[0] = 0;
        digits.push_back(1);
        for(int i = 0; i < len; i++)
            digits[i + 1] = digits[i];
        digits[0] = 1;
    }

    return digits;
}


/// 88
void Array::merge(std::vector<int>& nums1, int m, std::vector<int>& nums2, int n){
    std::vector<int> res;
    int j = 0, k = 0;
    while(j < m && k < n) {
        if(nums1[j] > nums2[k]) {
            res.push_back(nums2[k++]);
        } else {
            res.push_back(nums1[j++]);
        }
    }
    if(j < m) {
        while(j < m) {
            res.push_back(nums1[j++]);
        }
    }
    if(k < n) {
        while(k < n) {
            res.push_back(nums2[k++]);
        }
    }
    nums1 = res;
}


/// 118
/*
1
1 1
1 2 1
1 3 3 1
1 4 6 4 1
*/
std::vector<std::vector<int>> Array::generate(int numRows) {
    std::vector<std::vector<int>> res;
    for(int i = 0; i < numRows; i++) {
        std::vector<int> row;
        row.push_back(1);
        std::vector<int> tmp;
        for(int j = 1; j < i; j++) {
            tmp = res[i - 1];
            row.push_back(tmp[j] + tmp[j - 1]);
        }
        if(i != 0) row.push_back(1);
        res.push_back(row);
    }
    return res;
}

/// 119
std::vector<int> Array::getRow(int rowIndex) {
    std::vector<int> tmp;
    std::vector<int> row;
    for(int i = 0; i <= rowIndex; i++) {
        row.clear();
        row.push_back(1);
        for(int j = 1; j < i; j++) {
            row.push_back(tmp[j] + tmp[j - 1]);
        }
        if(i != 0) row.push_back(1);
        tmp = row;
    }
    return row;
}


/// 121
/// 动态规划 前i天的最大收益 = max{前i-1天的最大收益，第i天的价格-前i-1天中的最小价格}
int Array::maxProfit(std::vector<int>& prices) {
    if(prices.size() == 0) return 0;
    int minPro = prices[0];
    int maxPro = 0;
    for(int i = 1; i < prices.size(); i++) {
        maxPro = std::max(maxPro, prices[i] - minPro);
        if(minPro > prices[i]) minPro = prices[i];
    }
    return maxPro;
}


/// 122
/**
[7, 1, 5, 6] 第二天买入，第四天卖出，收益最大（6-1），
所以一般人可能会想，怎么判断不是第三天就卖出了呢?
这里就把问题复杂化了，根据题目的意思，
当天卖出以后，当天还可以买入，
所以其实可以第三天卖出，第三天买入，第四天又卖出
（（5-1）+ （6-5） === 6 - 1）。
所以算法可以直接简化为只要今天比昨天大，就卖出。
*/
int Array::maxProfit_v2(std::vector<int>& prices) {
    if(prices.size() == 0) return 0;
    int maxPro = 0;
    for(int i = 1; i < prices.size(); i++) {
        if(prices[i] > prices[i - 1]) maxPro += prices[i] - prices[i - 1];
    }
    return maxPro;
}


/// 167
std::vector<int> Array::twoSum_v2(std::vector<int>& numbers, int target) {
    /* 超时
    int i = 0, j = 1;
    std::vector<int> res;
    while(i < numbers.size() - 1 && numbers[i] <= target) {
        int sum = 0;
        while(j < numbers.size()) {
            sum = numbers[i] + numbers[j];
            if(sum == target) {
                res.push_back(i + 1);
                res.push_back(j + 1);
                return res;
            } else {
                j++;
            }
        }
        i++;
        j = i + 1;
    }
    return res;*/
    int l = 0, r = numbers.size() - 1;
    std::vector<int> res;
    while(l < r) {
        if(numbers[l] + numbers[r] == target) {
            res.push_back(l + 1);
            res.push_back(r + 1);
            return res;
        } else if(numbers[l] + numbers[r] < target)
            l++;
        else
            r--;
    }
    return res;
}

/// 169
int Array::majorityElement(std::vector<int>& nums) {
    if(nums.size() == 0) return 0;
    int num = nums[0];
    int idx = 1;
    for(int i = 1; i < nums.size(); i++) {
        if(nums[i] == num) {
            idx++;
        } else {
            idx--;
        }
        if(idx == 0) {
            num = nums[i];
            idx = 1;
        }
    }
    return num;
}


/// 189
void Array::rotate(std::vector<int>& nums, int k) {
    if(nums.size() == 0 || nums.size() == 1) return;
    k %= nums.size();
    /*for(int i = 0; i < nums.size() / 2; i++) {
        int temp = nums[i];
        nums[i] = nums[nums.size() - i - 1];
        nums[nums.size() - i - 1] = temp;
    }
    for(int i = 0; i < (k % nums.size()) / 2; i++) {
        int temp = nums[i];
        nums[i] = nums[k - i - 1];
        nums[k - i - 1] = temp;
    }
    for(int i = 0; i < (nums.size() - k % nums.size()) / 2; i++) {
        int temp = nums[i + k];
        nums[i + k] = nums[nums.size() - i - 1];
        nums[nums.size() - i - 1] = temp;
    }*/
    k = k % nums.size();
    reverse(nums.begin(), nums.begin() + nums.size() - k);
    reverse(nums.begin() + nums.size() - k, nums.end());
    reverse(nums.begin(), nums.end());
}


/// 217
bool Array::containsDuplicate(std::vector<int>& nums) {
    std::set<int> s;
    for(auto i : nums) {
        s.insert(i);
    }
    return s.size() != nums.size();
}


/// 219
bool Array::containsNearbyDuplicate(std::vector<int>& nums, int k) {
    std::unordered_map<int, int> m;
    for(int i = 0; i < nums.size(); i++) {
        if(m.count(nums[i]) != NULL && i - m[nums[i]] <= k) return true;
        m[nums[i]] = i;
    }
    return false;
}

/// 268
int Array::missingNumber(vector<int>& nums) {
    int res = nums.size();
    for (int i = 0; i < nums.size(); ++i){
        res ^= nums[i];
        res ^= i;
    }
    return res;
}





