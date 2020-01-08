// 1248. Count Number of Nice Subarrays
// https://www.acwing.com/solution/LeetCode/content/5821/
// brute-force, TLE
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            int count = 0;
            for (int j = i; j < nums.length; j++) {
                if (nums[j] % 2 != 0) {
                    count++;
                }
                if (count == k) {
                    result++;
                }
            }
        }
        return result;
    }
}

// prefix array, binary search, O(nlogn)
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int[] prefix = new int[nums.length + 1];
        for (int i = 1; i < nums.length + 1; i++) {
            // 0 0 0 1 1 1 2 3 3 3 ...
            prefix[i] = prefix[i - 1] + (nums[i - 1] % 2 == 1 ? 1 : 0);
        }

        int result = 0;
        for (int i = 0; i < prefix.length; i++) {
            result += (binarySearch(prefix, i, k) - binarySearch(prefix, i, k + 1));
        }
        return result;
    }

    // return last position of target number
    public int binarySearch(int[] nums, int i, int k) {
        int l = 0, r = i;
        while (l < r) {
            int mid = (l + r) / 2;
            if (nums[i] - nums[mid] >= k)
                l = mid + 1;
            else
                r = mid;
        }

        return l;
    }
}

// double pointer, O(n)
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        int left = 0, right = 0, result = 0, subarr = 0, count = 0;

        while (right < nums.length) {
            if (nums[right] % 2 == 1) {
                count++;
            }

            // recount current subarray
            if (count == k) {
                subarr = 0;
            }

            while (count == k) {
                subarr++;
                if (nums[left] % 2 == 1) {
                    count--;
                }
                left++;
            }

            // add subarray for each number
            result += subarr;
            right++;
        }

        return result;
    }
}

// multiplication, O(n)
class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        List<Integer> odd = new ArrayList<>();
        odd.add(-1);
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 == 1) {
                odd.add(i);
            }
        }
        odd.add(nums.length);

        int result = 0;
        for (int i = 1; i + k < odd.size(); i++) {
            // even number before odd.get(i) * even number before odd.get(i + k + 1)
            // i + (k - 1) is k odd number
            result += (odd.get(i) - odd.get(i - 1)) * (odd.get(i + k + 1 - 1) - odd.get(i + k - 1));
        }
        return result;
    }
}

class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 == 1) {
                nums[i] = 1;
            } else {
                nums[i] = 0;
            }
        }

        return subarraySum(nums, k);
    }

    // leetcode 560
    public int subarraySum(int[] nums, int k) {
        int result = 0;
        Map<Integer, Integer> map = new HashMap<>(); // prefix sum => size
        map.put(0, 1);
        int prefix = 0;
        for (int i = 0; i < nums.length; i++) {
            prefix += nums[i];
            if (map.containsKey(prefix - k)) {
                result += map.get(prefix - k);
            }
            map.put(prefix, map.getOrDefault(prefix, 0) + 1);
        }
        return result;
    }
}