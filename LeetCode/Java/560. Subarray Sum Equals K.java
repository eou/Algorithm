// 560. Subarray Sum Equals K
class Solution {
    // prefix sum array, 时间复杂度为 O(n^2)
    public int subarraySum(int[] nums, int k) {
        int[] prefixSum = new int[nums.length + 1];
        for (int i = 1; i <= nums.length; ++i) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }

        int result = 0;
        for (int i = 1; i < prefixSum.length; ++i) {
            for (int j = 0; j < i; ++j) {
                if (prefixSum[i] - prefixSum[j] == k) {
                    ++result;
                }
            }
        }

        return result;
    }
}

class Solution {
    public int subarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        Map<Integer, List<Integer>> map = new HashMap<>();    // prefix sum => index
        map.put(0, new ArrayList<Integer>(){{add(-1);}}); // [1, 1, 1]
        int sum = 0;
        int res = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                res += (map.get(sum - k).size());
            }
            List<Integer> list = map.getOrDefault(sum, new ArrayList<>());
            list.add(i);
            map.put(sum, list);
        }
        
        return res;
    }
}

class Solution {
    // 用 HashMap 达到 O(n) 的时间和空间复杂度
    // 根据 prefix sum array 原理，只要找到满足 sum[j] - sum[i] = k 的i和j即可
    // 所以用map保存所有prefix sum，然后一对一对寻找即可
    // follow up: 如果要返回一个数组包含所有这些子数组的首尾下标，可以用 map<sum, list<index>> 保存 => 325.Maximum Size Subarray Sum Equals k
    public int subarraySum(int[] nums, int k) {
        // map<sum, cnt>
        Map<Integer, Integer> map = new HashMap<>();     // prefix sum => quantity
        map.put(0, 1);
        int sum = 0, cnt = 0;
        for(int i = 0; i < nums.length; i++) {
            sum += nums[i];
            // sum[j] - sum[i] == k => sum - k = map.get(sum - k)
            // if(map.containsKey(sum - k)) {
            //     cnt += map.get(sum - k);
            // }
            // map.put(sum, map.containsKey(sum) ? map.get(sum) + 1 : 1);
            // Java 8标准新增方法getOrDefault，第一个参数是key, 第二个参数是找不到这个key后返回的默认值
            cnt += map.getOrDefault(sum - k, 0);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return cnt;
    }
}