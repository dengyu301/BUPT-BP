Week 4 Report
================================
Problem 1 18-4 Sum
--------------------------------
```cpp

class Solution {
public:
    vector<vector<int>> fourSum(vector<int>& nums, int target) {
        vector<vector<int>> result;
        if(nums.size() < 4) return result;
        sort(nums.begin(),nums.end());
        for(int first = 0; first < nums.size() - 3; ){
            int target_3 = target - nums[first];
            /*When the elements in the vector is big and the sum of all of them four may exceed 
              INT_MAX, substract one element to reduce the chance of extension*/
            for(int fourth = nums.size() - 1; fourth > first + 2; ){
                int second = first + 1;
                int third = fourth - 1;
                int target_2 = target_3 - nums[fourth];
                while(second < third){
                    if(nums[second]+nums[third] == target_2){
                        result.push_back({nums[first],nums[second],nums[third],nums[fourth]});
                        second++;
                        while(nums[second] == nums[second-1]){
                            second++;
                        }
                        continue;
                    }
                    if(nums[second]+nums[third] > target_2){
                        third--;
                        while(nums[third] == nums[third+1]) third--;
                    }
                    if(nums[second]+nums[third] < target_2){
                        second++;
                        while(nums[second] == nums[second-1]) second++;
                    }
                }
                fourth--;
                while(nums[fourth] == nums[fourth+1]) fourth--;
            }
            first++;
            while(nums[first] == nums[first-1]) first++;
        }
        return result;
    }
};
```

Problem 2 3-Longest Substring Without Repeating Characters
--------------------------------

```cpp
//my version 1.0

int lengthOfLongestSubstring(string s) {
    if(s.length() < 2) return s.length();
    vector<int> hashmap(128,-1);
    int maxn = 0;
    int length = 0;
    int start = 0;
    for(int i = 0; i < s.length(); i++){
        if(hashmap[s[i]] == -1){
            hashmap[s[i]] = i;
            length++;
        }else{
            if(hashmap[s[i]] < start) {
                hashmap[s[i]] = i;
                length++;
            }
            else{
                maxn = max(maxn,length);
                start = hashmap[s[i]] + 1;
                hashmap[s[i]] = i;
                length = i - start + 1;
            }
        }
    }
    maxn = max(maxn,length);
    return maxn;
}
};





/**
 *Other's idea
 */

/**
 * Solution (DP, O(n)):
 * 
 * Assume L[i] = s[m...i], denotes the longest substring without repeating
 * characters that ends up at s[i], and we keep a hashmap for every
 * characters between m ... i, while storing <character, index> in the
 * hashmap.
 * We know that each character will appear only once.
 * Then to find s[i+1]:
 * 1) if s[i+1] does not appear in hashmap
 *    we can just add s[i+1] to hash map. and L[i+1] = s[m...i+1]
 * 2) if s[i+1] exists in hashmap, and the hashmap value (the index) is k
 *    let m = max(m, k), then L[i+1] = s[m...i+1], we also need to update
 *    entry in hashmap to mark the latest occurency of s[i+1].
 * 
 * Since we scan the string for only once, and the 'm' will also move from
 * beginning to end for at most once. Overall complexity is O(n).
 *
 * If characters are all in ASCII, we could use array to mimic hashmap.
 */

int lengthOfLongestSubstring(string s) {
    // for ASCII char sequence, use this as a hashmap
    vector<int> charIndex(256, -1);
    int longest = 0, m = 0;

    for (int i = 0; i < s.length(); i++) {
        m = max(charIndex[s[i]] + 1, m);    // automatically takes care of -1 case
        charIndex[s[i]] = i;
        longest = max(longest, i - m + 1);
    }

    return longest;
}
```

Problem 3 36-Valid Sudoku
--------------------------------
```cpp

//My idea, save space, but when checking subbox, there is a fourth time loop...

class Solution {
public:
    bool isValidSudoku(vector<vector<char>>& board) {
        vector<int> hashmap(9,0);
        for(int i = 0; i < board.size(); i++){
            for(int j = 0; j < board[0].size(); j++){
                if(board[i][j] != '.'){
                    if(hashmap[board[i][j] - '0' - 1] == 1) return false;
                    hashmap[board[i][j] - '0' - 1] = 1;
                }
            }
            hashmap = {0,0,0,0,0,0,0,0,0};
        }
        for(int i = 0; i < board[0].size(); i++){
            for(int j = 0; j < board.size(); j++){
                if(board[j][i] != '.'){
                    if(hashmap[board[j][i] - '0' - 1] == 1) return false;
                    hashmap[board[j][i] - '0' - 1] = 1;
                }
            }
            hashmap = {0,0,0,0,0,0,0,0,0};
        }
        for(int row = 0; row < board[0].size(); row += 3){
            for(int col = 0; col < board.size(); col += 3){
                for(int i = 0; i < 3; i++){
                    for(int j = 0; j < 3; j++){
                        if(board[row+i][col+j] != '.'){
                            if(hashmap[board[row+i][col+j] - '0' - 1] == 1) return false;
                            hashmap[board[row+i][col+j] - '0' - 1] = 1;
                        }
                    }
                }
                hashmap = {0,0,0,0,0,0,0,0,0};
            }
        }
        return true;
    }
};





//another idea;

class Solution
{
public:
    bool isValidSudoku(vector<vector<char> > &board)
    {
        int used1[9][9] = {0}, used2[9][9] = {0}, used3[9][9] = {0};

        for(int i = 0; i < board.size(); ++ i)
            for(int j = 0; j < board[i].size(); ++ j)
                if(board[i][j] != '.')
                {
                    int num = board[i][j] - '0' - 1, k = i / 3 * 3 + j / 3;
                    if(used1[i][num] || used2[j][num] || used3[k][num])
                        return false;
                    used1[i][num] = used2[j][num] = used3[k][num] = 1;
                }

        return true;
    }
};

```

Problem 4 49-Group Anagrams
--------------------------------
```cpp
class Solution {
public:
    vector<string> anagrams(vector<string>& strs) {
        vector<string> sortedstr = strs;
        vector<string> result;
        /*Get a copy of the original strs, and sort this copy, 
        so the anagrams will be the same*/
        unordered_map<string, vector<int>> map;
        for(int i = 0; i < strs.size(); i++){
            sort(sortedstr[i].begin(), sortedstr[i].end());
            map[sortedstr[i]].push_back(i);
            /*While sorting every string, record their existence
            and position in the map*/
        }
        for(auto it = map.begin(); it != map.end(); it++){
            /*Traverse the map and find the string which records at
            least two positions*/
            if(it->second.size() > 1){
                for(int i = 0; i < it->second.size(); i++){
                    /*Using the recorded ith location to locate the real string in strs*/
                    result.push_back(strs[it->second[i]]);
                }
            }
        }
        return result;
    }
};```

Problem 5 136-Single Number 
--------------------------------
```cpp
//my version 1.0, barely accecptable

class Solution {
public:
    int singleNumber(vector<int>& nums) {
        unordered_map <int,vector<int>> hashmap;
        for(int i = 0; i < nums.size(); i++){
            hashmap[nums[i]].push_back(i);
        }
        for(auto it = hashmap.begin(); it != hashmap.end(); it++){
            if(it->second.size() == 1){
                return it->first;
            }
        }
        return 0;
    }
};

//my version 2.0, no extra space.
class Solution {
public:
    int singleNumber(vector<int>& nums) {
        sort(nums.begin(),nums.end());
        for(int i = 0; i < nums.size()-1;){
            if(nums[i]==nums[i+1]) i+=2;
            else return nums[i];
        }
        return nums[nums.size()-1];
    }
};


//other's idea:
//known that A XOR A = 0 and the XOR operator is commutative, the solution will be very straightforward. `
//Logic: XOR will return 1 only on two different bits. So if two numbers are the same,
//XOR will return 0. Finally only one number left. A ^ A = 0 and A ^ B ^ A = B.
int singleNumber(int A[], int n) {
    int result = 0;
    for (int i = 0; i<n; i++)
    {
        result ^=A[i];
    }
    return result;
}
```

Problem 6 202-happy number 
--------------------------------
```cpp
//other's idea
/*I see the majority of those posts use hashset to record values. Actually, we can simply adapt the Floyd Cycle detection 
algorithm. I believe that many people have seen this in the Linked List Cycle detection problem. The following is my code:*/

int digitSquareSum(int n) {
    int sum = 0, tmp;
    while (n) {
        tmp = n % 10;
        sum += tmp * tmp;
        n /= 10;
    }
    return sum;
}

bool isHappy(int n) {
    int slow, fast;
    slow = fast = n;
    do {
        slow = digitSquareSum(slow);
        fast = digitSquareSum(fast);
        fast = digitSquareSum(fast);
    } while(slow != fast);
    if (slow == 1) return 1;
    else return 0;
}


/*From Wiki, in case you have not seen it: If n is not happy, then its sequence does not go to 1. Instead, it ends in the cycle:

4, 16, 37, 58, 89, 145, 42, 20, 4, ... To see this fact, first note that if n has m digits, then the sum of the squares of its digits is at most 9^2 m, or 81m.

For m=4 and above,

n\geq10^{m-1}>81m so any number over 1000 gets smaller under this process and in particular becomes a number with strictly fewer digits. Once we are under
1000, the number for which the sum of squares of digits is largest is 999, and the result is 3 times 81, that is, 243.

In the range 100 to 243, the number 199 produces the largest next value, of 163. In the range 100 to 163, the number 159 produces the largest next value, of 107. 
In the range 100 to 107, the number 107
produces the largest next value, of 50. Considering more precisely the intervals [244,999], [164,243], [108,163] and [100,107],
we see that every number above 99 gets strictly smaller under this process. Thus, no matter what number we start with, we eventually 
drop below 100. An exhaustive search then shows that every number in the interval [1,99] either is happy or goes to the above cycle.

The above work produces the interesting result that no positive integer other than 1 is the sum of the squares of its own digits, since any such
number would be a fixed point of the described process.

There are infinitely many happy numbers and infinitely many unhappy numbers. Consider the following proof:

1 is a happy number, and for every n, 10n is happy since its sum is 1 and for every n, 2 × 10n is unhappy since its sum is 4 and 4 is an unhappy number.*/

//other's code


//my rewritten
class Solution {
public:
    bool isHappy(int n) {
        int temp = 0;
        while(1){
            if(n == 4) return false;
            if(n == 1) return true;
            while(n){
                temp += (n%10) * (n%10);
                n = n/10;
            }
            n = temp;
            temp = 0;
        }
        return 1;
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
};
```

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