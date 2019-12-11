// 128. Longest Consecutive Sequence
class Solution {
    // 暴力版本，时间复杂度为 O(n^3)
    public int longestConsecutive(int[] nums) {
        int longest = 0;
        for(int n : nums) {
            int start = n;
            int len = 1;
            while(arrayContains(nums, start + 1)) {
                start++;
                len++;
            }

            longest = Math.max(longest, len);
        }
        
        return longest;
    }

    private boolean arrayContains(int[] nums, int num) {
        for(int n : nums) {
            if(n == num) {
                return true;
            }
        }

        return false;
    }
}

class Solution {
    // 用 set 改进的暴力版本，时间复杂度为 O(n)
    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int n : nums) {
            set.add(n);
        }

        int longest = 0;
        for (int n : nums) {
            if(!set.contains(n - 1)) {
                int start = n;
                int len = 1;
                while (set.contains(start + 1)) {
                    start++;
                    len++;
                }

                longest = Math.max(longest, len);
            }
            
        }

        return longest;
    }
}

class Solution {
    // 时间复杂度为 O(nlogn)
    public int longestConsecutive(int[] nums) {
        if(nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);
        int longest = 1;
        int len = 1;
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] != nums[i - 1]) {
                if(nums[i] == nums[i - 1] + 1) {
                    len++;
                } else {
                    len = 1;
                }
                longest = Math.max(longest, len);
            }
        }

        return longest;
    }
}

class Solution {
    public int longestConsecutive(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }

        int longest = 1;
        // n => the length of sequence start or end with the n
        Map<Integer, Integer> map = new HashMap<>();
        for (int n : nums) {
            if (!map.containsKey(n)) {
                int leftSequenceLen = map.getOrDefault(n - 1, 0);
                int rightSequenceLen = map.getOrDefault(n + 1, 0);
                int len = leftSequenceLen + 1 + rightSequenceLen;
                map.put(n, len);

                longest = Math.max(longest, len);

                map.put(n - leftSequenceLen, len);
                map.put(n + rightSequenceLen, len);
            }
        }

        return longest;
    }
}