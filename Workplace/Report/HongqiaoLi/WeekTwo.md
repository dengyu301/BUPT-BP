Question 1:Remove Duplicates from Sorted Array#26
要点：
    1.第一次做leetcode上array的题目，发现，虽然是使用了STL出的题目，但是：
        a.因为是考察算法，不能使用泛函数。
        b.因为返回值大多是int,最好也别使用iterator而是直接使用下标操作。
    2.sorted array的去重，因为说明：
        Your function should return length = 2, with the first two elements of nums being 1 and 2 respectively. It doesn't matter what you leave beyond the new length.
        所以，不需要进行earse操作。
    3.如题目：
        with the first two elements of nums being 1 and 2 respectively，
        因此，很有可能return的length没有问题，但是nums中元素排序乱序而出错。
    4.如果要是数组不为空。那么，起始的slow值要从1开始，也就是slow = !nums.empty()，因为测试集中有[0,0,0,0,0]的输入。如果要是从0开始计数，会由于for中的if不会通过，造成输出的slow为0的情况发生。
思路：
    通过快慢指针法，使用两个指针（下标）。（具体程序中，fast是nums中元素值，而slow则是下标）
    1.先保持slow不动，fast++
    2.将fast值与slow值比较，当第一次出现不同时，交换fast与slow+1位置的值
    3.将slow++，继续1
边界条件：
    1.数组为空，输出长度为0
代码：
class Solution 
{
public:
    int removeDuplicates(vector<int>& nums) 
    {
        int slow = !nums.empty();
        for (int fast : nums)
            if (fast != nums[slow - 1])
                nums[slow ++] = fast;
        return slow;
    }
};

Question 2:Remove Element#27
要点：
    1.这道题目只要注意边界就好。
思路：
    就地算法，将不等于val的值，按照顺序重新存入count中即可。
边界条件：
    1.数组为空，输出长度为0
代码：
class Solution {
public:
    int removeElement(vector<int>& nums, int val) 
    {
        if (nums.empty())
            return 0;
        int count = 0;
        for (int each : nums)
            if (each != val)
                nums[count++] = each; 
        return count;
    }
};

Question 3:Pascal's Triangle#118
要点：
    1.这是杨辉三角，只要知道其生成方式，按照生成方式构造就可以了。
思路：
    通过杨辉三角的数学定义生成。
边界条件：
    1.numRows为0，输出为空。
    2.numRows为1，输出[1]。
代码：
class Solution {
public:
    vector<vector<int>> generate(int numRows) 
    {
        vector<vector<int> > result;
        if(!numRows)
            return result;
        for (int i = 0; i < numRows; i++)
        {
            vector<int> temp;
            if (i == 0)
                temp.push_back(1);
            else
            {
                temp.push_back(1);
                for(int j = 1; j < result[i - 1].size(); j++)
                    temp.push_back(result[i - 1][j] + result[i - 1][j - 1]);
                temp.push_back(1);
            }
            result.push_back(temp);
        }
        return result;
    }
};

Question 4:Merge Sorted Array#88
要点：
    1.这道题目主要是要倒序法来进行排序，因为如果按照正序进行插入，会出现nums1中的尚未使用的元素被覆盖的情况。
思路：
    通过倒序法来排序
    1.先定义nums1的倒序为k,nums2的倒序为j,最终结果nums1的倒序为i
    2.首先判断边界，之后，比较nums1与nums2最后一个元素的大小，将大的插入到nums2中i的位置。
边界条件：
    1.nums1为空，将nums2的所有元素倒序插入nums1中
代码：
class Solution {
public:
    void merge(vector<int>& nums1, int m, vector<int>& nums2, int n) 
    {
        int i = m + n - 1;
        int k = m - 1;
        int j = n - 1;
        while(j >= 0)
        {
            if (k < 0)
                nums1[i--] = nums2[j--];
            else
                nums1[i--] = nums1[k] > nums2[j] ? nums1[k--] : nums2[j--];
        }
    }
};

Question 5:Majority Element#169
要点：
    1.刚开始时，我想到的是遍历一次，计算每一个元素出现次数的o(n)空间与时间复杂度的算法（HASH TABLE）
    2.但是题目中有说明：
        The majority element is the element that appears more than ⌊ n/2 ⌋ times.
        因此，应该使用Moore voting algorithm
    3.Moore voting algorithm:
        主要思路是，由于Majority Element比其他元素出现次数加起来还多。因此依次对每个元素的出现次数做差，即使是最坏的情况下，也能求出Majority Element。
思路：
    Moore voting algorithm:
    1.先假设第一个元素为majority。
    2.遍历，如果下一个元素是majority的值，那么，count++；
    3.如果不是majority，相当于用此时的majority与另一个元素出现次数做差一次，count--
    4.当count等于0，则对其重新赋值为当前元素i
    5.由Majority Element比其他元素出现次数加起来还多，所以，即使是最坏的情况，也可以计算出Majority Element。
边界条件：
    无
代码：
class Solution {
public:
    int majorityElement(vector<int>& nums) 
    {
        int count = 0;
        int major = nums[0];
        for (int i = 0; i < nums.size(); i++)
        {
            if (count == 0)
            {
                major = nums[i];
                count ++;
            }
            else if(major == nums[i])
                count ++;
            else if(major != nums[i])
                count --;
        }
        return major;
    }
};
有问题：
事实上这道题有另外一个o(n)复杂度的解法：
We would need 32 iterations, each calculating the number of 1's for the ith bit of all n numbers. Since a majority must exist, therefore, either count of 1's > count of 0's or vice versa (but can never be equal). The majority number’s ith bit must be the one bit that has the greater count.
它是将每一个数的大小视为不超过2^32 - 1，对位进行操作。
但是：
1.每一位按照位操作，最后返回一个32位二进制数，可以得到0，1数不想等的那些位，也可以得到这些位的叠加次数，但是，通过这两个数怎么返回出majority element呢？
2.如果要是计算所有数的ith bit的count数，空间复杂度和hash table是一样的?
所以，我想不到该如何用这种方法求解，貌似也没查到。。。
请大家看看应该怎么解决。。。

Question 6:3 Sum Closest#16
要点：
    1.第一次想到的方法是o(n^3)复杂度的方法，思考很久以后，从discuss中得到启发，使用了它的算法。
    2.这种题目必须得排序，通过有序化后，才能降低复杂度。
思路：
    1.先通过泛函数，排序，还有计算边界条件（作者有点懒得写非重要的排序部分了）
    2.函数主体部分，如作者注释，通过两层循环：
        a.第一层循环定位中间j元素
        b.第二层循环确定两边的i,k，初始时i最小，k最大
            i.如果新的abs(sum - target) < abs(ans - target),则更新ans
            ii.如果sum > target，则将k--，减小3个元素中最大的，
            iii.如果sum < target,则将i++，增大3个元素中最小的，
    3.注意，中间不变
边界条件：
    1.数组小于3个，则返回所有元素相加的值。
代码：
class Solution {
public:
    int threeSumClosest(vector<int> &nums, int target) {
    vector<int> v(nums.begin(), nums.end()); // 拷贝构造函数.
    int n = 0;
    int ans = 0;
    int sum;
    sort(v.begin(), v.end());
    // If less then 3 elements then return their sum
    while (v.size() <= 3) {
        return accumulate(v.begin(), v.end(), 0);
    }
    n = v.size();
    /* v[0] v[1] v[2] ... v[i] .... v[j] ... v[k] ... v[n-2] v[n-1]
     *                    v[i]  <=  v[j]  <= v[k] always, because we sorted our array. 
     * Now, for each numsber, v[i] : we look for pairs v[j] & v[k] such that 
     * absolute value of (target - (v[i] + v[j] + v[k]) is minimised.
     * if the sum of the triplet is greater then the target it implies
     * we need to reduce our sum, so we do K = K - 1, that is we reduce
     * our sum by taking a smaller numsber.
     * Simillarly if sum of the triplet is less then the target then we
     * increase out sum by taking a larger numsber, i.e. J = J + 1.
     */
    ans = v[0] + v[1] + v[2];
    for (int i = 0; i < n-2; i++) {
        int j = i + 1;
        int k = n - 1;
        while (j < k) {
            sum = v[i] + v[j] + v[k];
            if (abs(target - ans) > abs(target - sum)) {
                ans = sum;
                if (ans == target) return ans;
            }
            (sum > target) ? k-- : j++;
        }
    }
    return ans;
}
};

Question 7:Container With Most Water#11
要点：
    1.这道题挺有趣的，通过2条线，与X轴组成容器，目标是容积尽可能大，也就是目标函数是f(i,j) = abs（i - j） * min(height[i], height[j])
    2.开始时想到的最简单方法就是o(n^2)，然后计算出max（f(i,j)）
    3.但是，因为f（i,j）=f(j,i)，所以，可以有o（n）的复杂度。
    4.方法是通过 Question 6的i最小，j最大来赋予初值计算。
思路：
    1.先将i，j分别设为首尾节点
    2.中间部分计算目标函数
    3.最后的条件o(n^2)转化为o(n)的条件，则是根据目标函数的形式，进行的优化，假设：
        f的第一项，因为j--或者i++减少1，值必然减少1。
        f的第二项则通过留下max(height[i], height[j])，保证了后一项尽可能增大。
        因而，可以认为目标函数在有限的条件下，可能收敛到最大值。
边界条件：
    无
代码：
class Solution {
public:
    int maxArea(vector<int>& height) 
    {
        int i = 0;
        int j = height.size() - 1;
        int result = 0;
        while(j > i)
        {
            result = max(result, abs(i - j) * min(height[i], height[j]));
            if(height[i] > height[j]) 
                j--;
            else
                i++;
        }
        return result;
    }
};

Question 8:Minimum Path Sum#64
要点：
    1.一道典型的DP问题，事实上，所有的m * n的格子问题，都可以视作为DP问题。
    2.DP问题，也就是将已经计算过的问题，通过迭代上一次的结果得到，而不是重新计算。
    3.本题，则是，将其分解为(m - 1) * n 或 m * （n -1）的矩阵，从而得到最终结果。
    4.目标函数则是： S[i][j] = min(S[i - 1][j], S[i][j - 1]) + grid[i][j]，
    5.当然，通过上述目标函数，可以写成递归的形式（事实上所有的DP都可以写成递归），但是，由于有递归深度，递归块大小等限制，我估计我电脑跑不了，我也就没有做。
思路：
    1.先计算第0行，与第0列的情况。
    2.循环目标函数
边界条件：
    无
代码：
class Solution {
public:
    int minPathSum(vector<vector<int> >& grid) 
    {
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
优化：（后面的优化，基本都是看leetcode别人代码的。。。）
要点：
    1.由于初始的边界条件时，只用了一行与一列，所以，完全没必要计算整个sum
    2.同时，不论是上面的向下，还是向右的运动，事实上都是前一步记录的值，所以，没必要将二者分开。
思路：
    1.先计算出边界向下，也就是沿第[1][0],[2][0]...[m][0],一步步的路径长度,使用sum[i]表示。
    2.通过sum[0] += grid[0][j]; 记录每一步向右的路径长度
    3.将目标函数改为：sum[i] = min(sum[i - 1], sum[i]) + grid[i][j];
    4.初始计算顺序则是：
        a.第一步计算[0][1] 到 [1][1]与 [1][0] 到[1][1]路径最短的一个
        b.后面计算i增加的条件下，也就是一直往下走局部最短的路径（即是往下走几步，往右转最短），保证目标函数最小
代码
class Solution {
public:
    int minPathSum(vector<vector<int>>& grid) 
    {
        int m = grid.size();
        int n = grid[0].size();
        vector<int> sum(m, grid[0][0]);
        for (int i = 1; i < m; i++)
            sum[i] = sum[i - 1] + grid[i][0]; 
        for (int j = 1; j < n; j++) 
        {
            sum[0] += grid[0][j]; 
            for (int i = 1; i < m; i++)
                sum[i] = min(sum[i - 1], sum[i]) + grid[i][j];
        }
        return sum[m - 1];
    }
};

Question 9:Subsets#78
要点：
    1.一共有n^2个子集，这道题，说实话，我是参考答案做出来的，还是有点难想到的。
思路：
    1.先排序，保证是非降序。
    2.使用DP的思想，认为有K个元素集合的子集，是K-1个集合的子集本身，加上这些集合与第K个元素构成的集合，外加K元素本身的集合所构成的
边界条件：
    无
代码：
class Solution {
public:
vector<vector<int> > subsets(vector<int> &S) 
{
    vector<vector<int> > res(1, vector<int>());
    sort(S.begin(), S.end());
    for (int i = 0; i < S.size(); i++) 
    {
        int n = res.size();
        for (int j = 0; j < n; j++) 
        {
            res.push_back(res[j]);
            res.back().push_back(S[i]);
        }
    }
    return res;
}
};

Question 10:Best Time to Buy and Sell Stock#121
要点：
    1.给出一段时间内，每天股票价格，然后总共只容许买卖一次。
思路：
    一次循环比较大小，如果价格比min低，则按照最低买入，如果价格比min高，则判断此时卖出profit是否最大。
边界条件：
    1.数组为空或为1个元素，输出profit为0
代码：
class Solution {
public:
    int maxProfit(vector<int>& prices) 
    {
        if(prices.empty() || prices.size() == 1)
            return 0;
        int min = prices[0];
        int profit = 0;
        for (int each : prices)
        {
            if (min > each)
                min = each;
            else
                profit = max(profit, each - min);
        }
        return profit;
    }
};

总结：
    1.由于数组题目，比上周做的题目来说，有泛函数可以使用，所以，在非关键的地方，比如sort之类，最好泛函数直接解决，但是题目本身还是不要用泛函数了，否则题目就显得没有意义了。
    2.由于数组的优势在于有下标操作，所以，在进行数组题目时，如果给的是乱序数组，第一步可以sort(array.begin(),array.end())，节省大量的算法复杂度。

第8题测试集：
#include<iostream>
#include<random>
using namespace std;
static default_random_engine e;
static uniform_int_distribution<int> u(0, 100);
static uniform_int_distribution<int> f(1, 100);
int main()
{
    int m = f(e);
    int n = f(e);
    cout << m << ' ' << n << endl;
    vector<vector<int> > grid;
    for (int i = 0; i < m; i++)
    {
        vector<int> temp;
        for (int j = 0; j < n; j++)
            temp.push_back(u(e));
        grid.push_back(temp);
    }
    for (int p = 0; p < m; p++)
    {
        for (int q = 0; q < n; q++)
            cout << grid[p][q] << ' ';
        cout << endl;
    }
    return 0;
}
注：
    1.随机数引擎设置如下：行列数目取值为[1：100]，每一个元素取值为[0:100]
    2.为了调试方便，每一次调试程序，都统一生成同一串随机数列。（在一次调试中，分别使用数列中不同的元素，从而保证随机。）

