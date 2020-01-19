// 134. Gas Station
// brute-force
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int res = -1;
        for (int i = 0; i < gas.length; i++) {
            // try start from index i
            int curGas = 0;
            for (int j = 0; j < gas.length; j++) {
                curGas += gas[(i + j) % gas.length];
                curGas -= cost[(i + j) % gas.length];
                // if it can get to next station
                if (curGas < 0) {
                    break;
                }
            }
            
            if (curGas >= 0) {
                res = i;
            }
        }
        
        return res;
    }
}

// 设sum-gas是所有加油站的油的总和，sum-cost是汽车走一圈消耗的油的总和，容易知道，当sum-gas<sum-cost时，汽车是不可能遍历所有加油站的，因为油不够
// 证明：当sum-gas>=sum-cost时，一定有一个起点，可以让汽车顺序跑一圈加油站。
// greedy
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int diff = 0;
        int start = 0;
        int total = 0;
        // the reason we can travel from i to j is that total gas should be more than total cost
        // if we cannot start from i to j, we cannot start from i + 1 to j => any stations from i to j cannot be start point (approve)
        for (int i = 0; i < gas.length; i++) {
            total += (gas[i] - cost[i]);
            diff += (gas[i] - cost[i]);
            if (diff < 0) {
                start = i + 1;
                diff = 0;
            }
        }
        
        return total < 0 ? -1 : start;
    }
}

// https://www.qiujiawei.com/leetcode-problem-134/
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int start = gas.length - 1;
        int end = 0;
        // start from last station to first station (clockwise), adjacent station in the circle !
        //   .... <= [gas.length - 1], 0, => 1, ....
        int sum = gas[start] - cost[start]; // start from first station
        while (start > end) {
            if (sum >= 0) {
                // can reach end, continue move
                sum += gas[end] - cost[end]; 
                ++end;
            } else {
                // cannot reach, want more gas from start
                --start;
                sum += gas[start] - cost[start];
            }
        }

        return sum < 0 ? -1 : start;
    }
}