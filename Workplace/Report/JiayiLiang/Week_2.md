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