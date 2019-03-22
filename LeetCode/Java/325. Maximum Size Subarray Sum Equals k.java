// 325. Maximum Size Subarray Sum Equals k
class Solution {
    public int maxSubArrayLen(int[] nums, int k) {
        int[] prefix = new int[nums.length + 1];
        prefix[0] = 0;
        for (int i = 0; i < nums.length; ++i) {
            prefix[i + 1] = prefix[i] + nums[i]; // nums[i] + nums[i + 1] + ... + nums[j] = prefix[j + 1] - prefix[i]
        }

        int result = 0;
        for (int i = 0; i < prefix.length; ++i) {
            for (int j = 0; j < i; ++j) {
                if (prefix[i] - prefix[j] == k) {
                    result = Math.max(result, i - j);
                }
            }
        }

        return result;
    }
}

class Solution {
    // 这种做法偏慢，因为不需要用list存储那么多下标，当添加了 sum - k 的key之后，就不需要用后面的下标更新了，所以也只需要用Integer保存即可
    public int maxSubArrayLen(int[] nums, int k) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(-1);
        map.put(0, list);

        int len = 0, sum = 0;
        List<Integer> tmp;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k)) {
                tmp = map.get(sum - k);
                len = Math.max(i - tmp.get(0), len);
            }
            if (map.containsKey(sum)) {
                tmp = map.get(sum);
                tmp.add(i);
                map.put(sum, tmp);
            } else {
                tmp = new ArrayList<>();
                tmp.add(i);
                map.put(sum, tmp);
            }
        }
        return len;
    }
}

class Solution {
    public int maxSubArrayLen(int[] nums, int k) {
        int sum = 0, len = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            // 这个判断避免了map的初始化: map.put(0,-1); 
            // 但是循环中每次都判断还不如初始化就添加一次值
            if (sum == k) {
                len = i + 1;
            }
            if (map.containsKey(sum - k)) {
                len = Math.max(len, i - map.get(sum - k));
            }
            if (!map.containsKey(sum)) {
                map.put(sum, i);
            }
        }
        return len;
    }
}