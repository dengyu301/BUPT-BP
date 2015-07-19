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

Problem 6 16-3Sum Closest 
--------------------------------
```cpp
class Solution {
public:
    int threeSumClosest(vector<int>& nums, int target) {
        int closet = 0;
        int sum = 0;
        if(nums.size() <= 3) return accumulate(nums.begin(),nums.end(),0);
        sort(nums.begin(),nums.end());
        int answer = nums[0] + nums[1] + nums[2];
        /*Computes the sum of the given value init and the elements in the range [first, 	last). 
          first, last	-	the range of elements to sum
          init	-	initial value of the sum.*/
        for(int first = 0; first < nums.size() - 2; first++){
            int second = first + 1;
            int third = nums.size() - 1;
            while(second < third){
                sum = nums[first] + nums[second] + nums[third];
                if(sum == target) return sum;
                if(sum > target){
                    third--;
                }else if(sum < target){
                    second++;
                }
                if(abs(target-sum) < abs(target-answer)) answer = sum;
            }
        }
        return answer;
    }
};
```

Problem 7 11-Container With Most Water 
--------------------------------
```cpp
/*This is a rewritten of other's idea, I can only work out O(n^2) solution*/


class Solution {
public:
    int maxArea(vector<int>& height) {
        int start = 0, end = height.size() - 1;
        int answer = 0, contain = 0;
        while(start < end){
            answer = max(answer, (end-start) * min(height[start], height[end]) );
            height[start] > height[end] ? end-- : start++;
        }
        return answer;
    }
};
```

//--------this is the original idea----------------//

The O(n) solution with proof by contradiction doesn't look intuitive enough to me. Before moving on, read the algorithm first if you don't know it yet.

Here's another way to see what happens in a matrix representation:

Draw a matrix where the row is the first line, and the column is the second line. For example, say n=6.

In the figures below, x means we don't need to compute the volume for that case: (1) On the diagonal, the two lines are overlapped; (2) The lower left triangle area of the matrix is symmetric to the upper right area.

We start by computing the volume at (1,6), denoted by o. Now if the left line is shorter than the right line, then all the elements left to (1,6) on the first row have smaller volume, so we don't need to compute those cases (crossed by ---).

        1 2 3 4 5 6
        1 x ------- o
        2 x x
        3 x x x 
        4 x x x x
        5 x x x x x
        6 x x x x x x
Next we move the left line and compute (2,6). Now if the right line is shorter, all cases below (2,6) are eliminated.

        1 2 3 4 5 6
        1 x ------- o
        2 x x       o
        3 x x x     |
        4 x x x x   |
        5 x x x x x |
        6 x x x x x x
And no matter how this o path goes, we end up only need to find the max value on this path, which contains n-1 cases.

        1 2 3 4 5 6
        1 x ------- o
        2 x x - o o o
        3 x x x o | |
        4 x x x x | |
        5 x x x x x |
        6 x x x x x x

Problem 8 64-Minimum Path Sum
--------------------------------
// My basic version 1.0
```cpp
int minPathSum(vector<vector<int>>& grid) {
    vector<vector<int>> cgrid(grid.size(),vector<int>(grid[0].size(),0));
    if(grid.size() == 0) return 0;
    cgrid[0][0] = grid[0][0];
    for(int i = 0; i < cgrid.size(); i++){
        for(int j = 0; j < cgrid[0].size(); j++){
            if(i==0 && j==0) continue;
            if(j-1 < 0 ){
                cgrid[i][j] = cgrid[i-1][j] + grid[i][j];
            }
            if(i-1 < 0 ){
                cgrid[i][j] = cgrid[i][j-1] + grid[i][j];
            }
            else if(j-1 >= 0 && i-1 >= 0){
                cgrid[i][j] = min(cgrid[i-1][j] + grid[i][j], cgrid[i][j-1] + grid[i][j]);
            }
        }
    }
    return cgrid[grid.size()-1][grid[0].size()-1];
}
```


//Follwoing is someone's complete solution.





This is a typical DP problem. Suppose the minimum path sum of arriving at point (i, j) is S[i][j], then the state equation is S[i][j] = min(S[i - 1][j], S[i][j - 1]) + grid[i][j].

Well, some boundary conditions need to be handled. The boundary conditions happen on the topmost row (S[i - 1][j] does not exist) and the leftmost column (S[i][j - 1] does not exist). Suppose grid is like [1, 1, 1, 1], then the minimum sum to arrive at each point is simply an accumulation of previous points and the result is [1, 2, 3, 4].

Now we can write down the following (unoptimized) code.
```cpp
class Solution {
public:
    int minPathSum(vector<vector<int>>& grid) {
        int m = grid.size();
        int n = grid[0].size(); 
        vector<vector<int> > sum(m, vector<int>(n, grid[0][0]));
        for (int i = 1; i < m; i++)
            sum[i][0] = sum[i - 1][0] + grid[i][0];
        for (int j = 1; j < n; j++)
            sum[0][j] = sum[0][j - 1] + grid[0][j];
        for (int i = 1; i < m; i++)
            for (int j = 1; j < n; j++)
                sum[i][j]  = min(sum[i - 1][j], sum[i][j - 1]) + grid[i][j];
        return sum[m - 1][n - 1];
    }
};
```
As can be seen, each time when we update sum[i][j], we only need sum[i - 1][j] (at the current column) and sum[i][j - 1] (at the left column). So we need not maintain the full m*n matrix. Maintaining two columns is enough and now we have the following code.
```cpp
class Solution {
public:
    int minPathSum(vector<vector<int>>& grid) {
        int m = grid.size();
        int n = grid[0].size();
        vector<int> pre(m, grid[0][0]);
        vector<int> cur(m, 0);
        for (int i = 1; i < m; i++)
            pre[i] = pre[i - 1] + grid[i][0];
        for (int j = 1; j < n; j++) { 
            cur[0] = pre[0] + grid[0][j]; 
            for (int i = 1; i < m; i++)
                cur[i] = min(cur[i - 1], pre[i]) + grid[i][j];
            swap(pre, cur); 
        }
        return pre[m - 1];
    }
};
```
Further inspecting the above code, it can be seen that maintaining pre is for recovering pre[i], which is simply cur[i] before its update. So it is enough to use only one vector. Now the space is further optimized and the code also gets shorter.
```cpp
class Solution {
public:
    int minPathSum(vector<vector<int>>& grid) {
        int m = grid.size();
        int n = grid[0].size();
        vector<int> cur(m, grid[0][0]);
        for (int i = 1; i < m; i++)
            cur[i] = cur[i - 1] + grid[i][0]; 
        for (int j = 1; j < n; j++) {
            cur[0] += grid[0][j]; 
            for (int i = 1; i < m; i++)
                cur[i] = min(cur[i - 1], cur[i]) + grid[i][j];
        }
        return cur[m - 1];
    }
};
```
Problem 9 78-Subsets
--------------------------------
```cpp
/*This problem requires the understanding of the subset
basically used dynamic idea, subset of ith is subset of (i-1)th append each element of subset of (i-1)th with S[i] in its tail.

for example {1,2,3,4}

subset of {1,2,3} is {1,2,3,12,13,23,123}

subset of {1,2,3,4} is {1,2,3,12,13,23,123} + {1 4,2 4,3 4,12 4,13 4,23 4,123 4} + {4}

*/

class Solution {
public:
    vector<vector<int>> subsets(vector<int>& nums) {
        vector<vector<int>> result(1,vector<int>());
        sort(nums.begin(),nums.end());
        /*Make sure it is acceding order*/
        for(int i = 0; i < nums.size(); i++){
            int n = result.size();
            /*dynamic programming, for every element in the result, copy and paste back to the 
              result, and add new element from nums the the back to form the new element in the result*/
            for(int j = 0; j < n; j++){
                result.push_back(result[j]);
                result.back().push_back(nums[i]);
            }
        }
        return result;
    }
};
```

Problem 10 121-Best Time to Buy and Sell Stock
--------------------------------
// This is inspired by other's idea,
// At first I was confused by how to implement DP in the answer
// Then I realized others didn't try to use dp on purpose.
// So I just need to find the longest ascending slope, then I am done.


```cpp



class Solution {
public:
    int maxProfit(vector<int>& prices) {
        if(prices.size() <= 1) return 0;
        int maxmoney = 0, current = 0;
        for(int i = 1; i < prices.size(); i++){
            current = current + prices[i] - prices[i-1];
            if(current < 0) current = 0;
            maxmoney = max(current,maxmoney);
        }
        return maxmoney;
    }
};
```