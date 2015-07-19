Week 2 Report
================================
Problem 1 26-Remove Duplicates from Sorted Array
--------------------------------
```cpp
/*This problem is quite easy, just pay attention that once an element is erased, the indice of every
element in the array changes.*/

class Solution {
public:
    int removeDuplicates(vector<int>& nums) {
        if(nums.size() < 2) return nums.size();
        int i = 1, j = 0;
        //Two pointers
        while(i < nums.size()){
            if(nums[i] == nums[j]){
                nums.erase(nums.begin()+i);
                i = j+1;
            }
            else{
                j++;
                i++;
            }
        }
        return nums.size();
    }
};
```

Problem 2 27-Remove Element
--------------------------------
```cpp
//The essence of the problem is that when an entity is deleted in an array,the index of 
//the array changes. For example: 1-2-3-4， i = 2, 2 is deleted，and it becomes 1-3-4，next time i = 3，the index array[3] = 4.
//so we need two pointers, one is one step previous than the current node, thus, when the current node is deleted,
//i = j, so next time i increments one, and it will still point at the right node needs to be examined.
class Solution {
public:
    int removeElement(vector<int>& nums, int val) {
        int i = 0;
        int j = -1;
        while(i < nums.size()){
            if(nums[i] == val){
                nums.erase(nums.begin() + i);
                i = j;
                i++;
            }else{
                i++;
                j++;
            }
        }
        return nums.size();
    }
};
```

Problem 3 118-Pascal's Triangle
--------------------------------
```cpp
/*This problem is not diffcult, following is my code.
  I think my next step is to combine the boundary conditions and write more consice code.*/


class Solution {
public:
    vector<vector<int>> generate(int numRows) {
        vector<vector<int>> tower;
        if(numRows == 0) return tower;
        tower.push_back({1});
        if(numRows == 1) return tower;
        tower.push_back({1,1});
        if(numRows == 2) return tower;
        for(int i = 2; i < numRows; i++){
            vector<int> newline(i+1,1);
            for(int j = 1; j < i; j++){
                newline[j] = tower[i-1][j-1] + tower[i-1][j];
            }
            tower.push_back(newline);
        }
        return tower;
    }
};



//However, this more consice solution from discuss section


vector<vector<int> > generate(int numRows) {
        vector<vector<int>> r(numRows);
        for (int i = 0; i < numRows; i++) {
            r[i].resize(i + 1);
            r[i][0] = r[i][i] = 1;
            for (int j = 1; j < i; j++)
                r[i][j] = r[i - 1][j - 1] + r[i - 1][j];
        }
        return r;
    }
```

Problem 4 88-Merge Sorted Array
--------------------------------
```cpp
class Solution {
public:
    void merge(vector<int>& nums1, int m, vector<int>& nums2, int n) {
        int a = m -1;
        int b = n -1;
        int i = m + n - 1;
        while(a >= 0 && b >= 0){
            if(nums1[a] >= nums2[b]) nums1[i--] = nums1[a--];
            else nums1[i--] = nums2[b--];
        }
        while(b >= 0){
            nums1[i--] = nums2[b--];
        }
    }
};
```

Problem 5 169-Majority Element
--------------------------------
```cpp
/*This is my version 1.0, I didn't use divide and conquer nor bit manipulation...
  The idea is simple, sort the array first, then while traversing the array, using a variant to track 
  if the current pointed element has occured nums.size()/2 time, if it is, just return the element, 
  if it isn't increment the variant.
  When the element changes, reset the variant to 1 and start again until finding the majority.*/

class Solution {
public:
    int majorityElement(vector<int>& nums) {
        if(nums.size() == 1) return nums[0];
        sort(nums.begin(),nums.end());
        int major = 1;
        for(int i = 1; i < nums.size(); i++){
            if(nums[i] == nums[i-1]){
                major++;
                if(major > nums.size()/2) return nums[i];
            }else{
                major = 1;
            }
        }
        return 0;
    }
};
```

//Here is people's idea from discuss section:


This can be solved by Moore's voting algorithm. Basic idea of the algorithm is if we cancel out each occurrence of 
an element e with all the other elements that are different from e then e will exist till end if it is a majority element. 
Below code loops through each element and maintains a count of the element that has the potential of being the majority element. 
If next element is same then increments the count, otherwise decrements the count. 
If the count reaches 0 then update the potential index to the current element and sets count to 1.
```cpp
int majorityElement(vector<int> &num) {
    int majorityIndex = 0;
    for (int count = 1, i = 1; i < num.size(); i++) {
        num[majorityIndex] == num[i] ? count++ : count--;
        if (count == 0) {
            majorityIndex = i;
            count = 1;
        }
    }

    return num[majorityIndex];
}


```

//----------------------------------This one is great!!!------------------------------//


Well, if you have got this problem accepted, you may have noticed that there are 7 suggested solutions for this problem. The following passage will implement 6 of them except the O(n^2) brute force algorithm.

Hash Table

The hash-table solution is very straightforward. We maintain a mapping from each element to its number of appearances. 
While constructing the mapping, we update the majority element based on the max number of appearances we have seen. 
Notice that we do not need to construct the full mapping when we see that an element has appeared more than n / 2 times.

The code is as follows, which should be self-explanatory.
```cpp
class Solution {
public:
    int majorityElement(vector<int>& nums) {
        unordered_map<int, int> counts; 
        int n = nums.size();
        for (int i = 0; i < n; i++)
            if (++counts[nums[i]] > n / 2)
                return nums[i];
    }
};
```
Sorting

Since the majority element appears more than n / 2 times, the n / 2-th element in the sorted nums must be the majority element.
This can be proved intuitively. Note that the majority element will take more than n / 2 positions in the sorted nums (cover more than half of nums). 
If the first of it appears in the 0-th position, it will also appear in the n / 2-th position to cover more than half of nums.
It is similar if the last of it appears in the n - 1-th position.
These two cases are that the contiguous chunk of the majority element is to the leftmost and the rightmost in nums. 
For other cases (imagine the chunk moves between the left and the right end), it must also appear in the n / 2-th position.

The code is as follows, being very short if we use the system sort.
```cpp
class Solution {
public:
    int majorityElement(vector<int>& nums) {
        sort(nums.begin(), nums.end());
        return nums[nums.size() / 2];
    }
};
```
Randomization

This is a really nice idea and works pretty well (16ms running time on the OJ, almost fastest among the C++ solutions). 
The proof is already given in the suggested solutions.

The code is as follows, randomly pick an element and see if it is the majority one.
```cpp
class Solution {
public:
    int majorityElement(vector<int>& nums) {
        int n = nums.size();
        srand(unsigned(time(NULL)));
        while (true) {
            int idx = rand() % n;
            int candidate = nums[idx];
            int counts = 0; 
            for (int i = 0; i < n; i++)
                if (nums[i] == candidate)
                    counts++; 
            if (counts > n / 2) return candidate;
        }
    }
};
```
Divide and Conquer

This idea is very algorithmic. However, the implementation of it requires some careful thought about the base cases of the recursion.
The base case is that when the array has only one element, then it is the majority one. Moreover, this solution is relatively slow in practice.
```cpp
class Solution {
public:
    int majorityElement(vector<int>& nums) {
        return majority(nums, 0, nums.size() - 1);
    }
private:
    int majority(vector<int>& nums, int left, int right) {
        if (left == right) return nums[left];
        int mid = left + ((right - left) >> 1);
        int lm = majority(nums, left, mid);
        int rm = majority(nums, mid + 1, right);
        if (lm == rm) return lm;
        return counts(nums, lm) > counts(nums, rm) ? lm : rm;
    }
    int counts(vector<int>& nums, int elem) {
        int cnt = 0;
        for (int i = 0; i < int(nums.size()); i++)
            if (nums[i] == elem) cnt++;
        return cnt;
    }
};
```
Moore Voting Algorithm

A brilliant and easy-to-implement algorithm! It also runs very fast, about 20ms.
```cpp
class Solution {
public:
    int majorityElement(vector<int>& nums) {
        int major, counts = 0, n = nums.size();
        for (int i = 0; i < n; i++) {
            if (!counts) {
                major = nums[i];
                counts = 1;
            }
            else counts += (nums[i] == major) ? 1 : -1;
        }
        return major;
    }
};
```
Bit Manipulation

Another nice idea! The key lies in how to count the number of 1's on a specific bit. 
Specifically, you need a mask with a 1 on the i-the bit and 0 otherwise to get the i-th bit of each element in nums. 
The code is as follows.
```cpp
class Solution {
public:
    int majorityElement(vector<int>& nums) {
        int major = 0, n = nums.size();
        for (int i = 0, mask = 1; i < 32; i++, mask <<= 1) {
            int bitCounts = 0;
            for (int j = 0; j < n; j++) {
                if (nums[j] & mask) bitCounts++;
                if (bitCounts > n / 2) {
                    major |= mask;
                    break;
                }
            }
        } 
        return major;
    } 
};
```