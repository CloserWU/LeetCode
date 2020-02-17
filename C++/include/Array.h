#include<bits/stdc++.h>
using std::vector;
using std::unordered_map;
using std::iterator;
using std::string;
using std::sort;
using std::reverse;
using std::abs;
using std::find;
using std::cout;
using std::endl;
using std::cin;
class Array{
public:
    std::vector<int> twoSum(std::vector<int>& nums, int target);
    int removeDuplicates(std::vector<int>& nums);
    int removeElement(std::vector<int>& nums, int val);
    int searchInsert(std::vector<int>& nums, int target);
    int maxSubArray(std::vector<int>& nums);
    std::vector<int> plusOne(std::vector<int>& digits); // 66
    void merge(std::vector<int>& nums1, int m, std::vector<int>& nums2, int n); // 88
    std::vector<std::vector<int>> generate(int numRows); // 118
    std::vector<int> getRow(int rowIndex); // 119
    int maxProfit(std::vector<int>& prices); // 121
    int maxProfit_v2(std::vector<int>& prices); //122
    std::vector<int> twoSum_v2(std::vector<int>& numbers, int target); // 167
    int majorityElement(std::vector<int>& nums); // 169
    void rotate(std::vector<int>& nums, int k); // 189
    bool containsDuplicate(std::vector<int>& nums); // 217
    bool containsNearbyDuplicate(std::vector<int>& nums, int k); // 219
    int missingNumber(std::vector<int>& nums); // 268
};
