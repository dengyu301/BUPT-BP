Week 2 Report
================================
Problem 1 26-Remove Duplicates from Sorted Array
--------------------------------
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