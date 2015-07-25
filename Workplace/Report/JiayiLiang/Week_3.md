Week 3 Report
================================
Problem 1 66-Plus one
--------------------------------
```cpp
//This problem is similar to the #2 Add Two Numbers, just pay attention to the carry when dealing with the first
//digit of the number.

class Solution {
public:
    vector<int> plusOne(vector<int>& digits) {
        int carry = 1;
        for(int i = digits.size()-1; i >= 0; i--){
            digits[i] = digits[i] + carry ;
            if(digits[i] > 9){
                digits[i] = digits[i] % 10;
                carry = 1;
            }else{
                carry = 0;
            }
            if(i == 0 && carry == 1){
                digits.insert(digits.begin(),1);
            }
        }
        return digits;
    }
};
```

Problem 2 119-Pascal’s Triangle II
--------------------------------
Basically, I will insert 1 at the head of vector each iteration and calculate result[j] based on result[j] and result[j+1]. 
For example, if I want to get [1, 3, 3, 1] from [1, 2, 1], I will first insert 1 and get [1, 1, 2, 1]. 
Then begin with j = 1, result[j] = result[j] + result[j+1]. second position is 1+2=3, 
third position is 2+1=3 Finally we get [1, 3, 3, 1]
```cpp
#include <vector>
using namespace std;
class Solution {
public:
    vector<int> getRow(int rowIndex) {
        vector<int> result(1,1);
        for(int i=1;i<=rowIndex;i++){
            result.insert(result.begin(),1);
            for(int j=1;j<result.size()-1;j++){
                result[j] = result[j] + result[j+1];
            }
        }
        return result;
    }
};
```
The basic idea is to iteratively update the array from the end to the beginning.
```cpp
class Solution {
public:
    vector<int> getRow(int rowIndex) {
        vector<int> result(rowIndex + 1,0);
        result[0] = 1;
        for(int i = 1; i < rowIndex + 1; i++){
            for(int j = i; j >= 1; j--){
                result[j] += result[j-1];
            }
        }
        return result;
    }
};
```

Problem 3 189-Rotate Array
--------------------------------
```cpp

/*Just literally rotate the array, prepend the last element to the array every time.*/

class Solution {
public:
    void rotate(vector<int>& nums, int k) {
        if(nums.size() == 0) return;
        int n = nums.size();
        k = k % n;
        for(int i = 0; i < k; i++){
            nums.insert(nums.begin(),nums[n-1]);
            nums.erase(nums.begin() + n);
        }
    }
};

//Same idea, but more consice code

 void rotate(vector<int>& nums, int k) 
    {
        for(int i=0;i<k;i++)
        {
            nums.insert(nums.begin(), nums.back());
            nums.pop_back();
        }
    }


```

Problem 4 217-Contains Duplicates
--------------------------------
```cpp
//Simple hash table inplementation, because it needs to check the hash table every time, it is not fast, 100ms.

class Solution {
public:
    bool containsDuplicate(vector<int>& nums) {
        map<int,int> mymap;
        for(int i = 0; i < nums.size(); i++){
            if(mymap.find(nums[i]) == mymap.end()){
                mymap[nums[i]] = 1;
            }else{
                return true;
            }
        }
        return false;
    }
};

//Using "set" container, faster, 50ms.

class Solution {
public:
    bool containsDuplicate(vector<int>& nums) {
        return set<int>(nums.begin(), nums.end()).size() < nums.size();
    }
};
```

Problem 5 55-Jump Game 
--------------------------------
```cpp
//Rewrite other's idea.Simple greedy algorithm

class Solution {
public:
    bool canJump(vector<int>& nums) {
        int destination = nums.size() - 1;
        for(int i = nums.size()-2; i >= 0; i--){
            destination = i+nums[i] >= destination ? i : destination;
        }
        return destination == 0;
    }
};
```

Problem 6 1-Two Sum 
--------------------------------
```cpp
/*I rewrote other’s idea from discuss section, it didn’t occur to me that I should
  use hash table, just remember this kind of answer*/
class Solution {
public:
    vector<int> twoSum(vector<int>& nums, int target) {
        vector <int> result;
        map <int,int> need;
        for(int i = 0; i < nums.size(); i++){
            if(need.find(target-nums[i]) == need.end()){
                need[nums[i]] = i + 1; 
            }
            else{
                result.push_back(need[target-nums[i]]);
                result.push_back(i + 1);
                return result;
            }
        }
        return result;
    }
};
```

Problem 7 35-Search Insert Position 
--------------------------------
```cpp
//Quite easy

class Solution {
public:
    int searchInsert(vector<int>& nums, int target) {
        for(int i = 0; i < nums.size(); i++){
            if(nums[i] >= target) return i;
        }
        return nums.size();
    }
};
```

Problem 8 62-Unique Paths
--------------------------------
// My basic version 1.0
```cpp
//Simple dp

class Solution {
public:
    int uniquePaths(int m, int n) {
        if(m == 0 || n == 0) return 0;
        if(m == 1 || n == 1) return 1;
        vector<vector<int>> grid(m, vector<int>(n,1));
        grid[0][0] = 0;
        for(int i = 1; i < n; i++){
            for(int j = 1; j < m ; j++){
                grid[j][i] = grid[j][i-1] + grid[j-1][i];
            }
        }
        return grid[m-1][n-1];
    }
};

```


Problem 9 81-Search in Rotated Sorted Array II
--------------------------------
```cpp
//Duplicates wouldn't affect the result, because once I find out the element that is equal to target, I return true,
//it doesn't matter whether I find another equation in other half of the array.

class Solution {
public:
    bool binarysearch(int start, int end, vector<int> &nums,int target){
        if(nums[start] == target) return true;
        if(start < end){
            return binarysearch(start, (start+end)/2, nums,target) || binarysearch((start				+end)/2 + 1, end, nums,target);
        }
        return false;
    }
    bool search(vector<int>& nums, int target) {
        return binarysearch(0, nums.size()/2-1, nums,target) || binarysearch(nums.size()/2, nums.size()-1, nums, target); 
    }
};```

Problem 10 31-Next Permutation
--------------------------------
Well, in fact the problem of next permutation has been studied long ago. From the Wikipedia page, in the 14th century, 
a man named Narayana Pandita gives the following classic and yet quite simple algorithm (with minor modifications in notations 
to fit the problem statement):

Find the largest index k such that nums[k] < nums[k + 1]. If no such index exists, the permutation is sorted in descending order, just reverse it to ascending order and we are done. For example, the next permutation of [3, 2, 1] is [1, 2, 3].
Find the largest index l greater than k such that nums[k] < nums[l].
Swap the value of nums[k] with that of nums[l].
Reverse the sequence from nums[k + 1] up to and including the final element nums[nums.size() - 1].
Quite simple, yeah? Now comes the following code, which is barely a translation.

Well, a final note here, the above algorithm is indeed powerful --- 
it can handle the cases of duplicates! If you have tried the problems Permutations and Permutations II, 
then the following function is also useful. Both of Permutations and Permutations II can be solved easily using this function.
Hints: sort nums in ascending order, add it to the result of all permutations and then repeatedly generate the next permutation
and add it ... until we get back to the original sorted condition. If you want to learn more, please visit this solution and
that solution.


```cpp

class Solution {
public:
    void nextPermutation(vector<int>& nums) {
        int k = -1, l = 0;
        for(int i = 0; i < nums.size()-1; i++){
            if(nums[i] < nums[i + 1]){
                k = i;
            }
        }
        if(k == -1) {
            reverse(nums.begin(),nums.end());
            return;
        }
        for(int i = k+1; i < nums.size(); i++){
            if(nums[i] > nums[k]) l = i;
        }
        swap(nums[k],nums[l]);
        reverse(nums.begin() + k + 1, nums.end());
    }
};

```