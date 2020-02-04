// 287. Find the Duplicate Number
// 题目条件有暗示：Given an array nums containing n + 1 integers where each integer is between 1 and n (inclusive)
class Solution {
    // 不符合题目要求：You must not modify the array (assume the array is read only)
    public int findDuplicate(int[] nums) {
        Arrays.sort(nums);
        for(int i = 1; i < nums.length; i++) {
            if(nums[i] == nums[i - 1]) {
                return nums[i];
            }
        }

        return -1;
    }
}

class Solution {
    public int findDuplicate(int[] nums) {
        Set<Integer> visited = new HashSet<>();
        for(int n : nums) {
            if(visited.contains(n)) {
                return n;
            }
            visited.add(n);
        }

        return -1;
    }
}

// O(nlogn)
class Solution {
    public int findDuplicate(int[] nums) {
        int left = 1, right = nums.length;
        // O(logn)
        while (left < right) {
            int mid = left + (right - left) / 2, cnt = 0;
            // O(n)
            for (int num : nums) {
                if (num <= mid) {
                    ++cnt;
                }
            }
            if (cnt <= mid) {
                // duplicate in the right part
                left = mid + 1;
            } else {
                // duplicate in the left part
                right = mid;
            }
        }

        return right;
    }
}


// if num represents start, index represents end
// [4,3,5,1,2] is 1=>4, 2=>3, 3=>5, 4=>1, 5=>2 will be one or more cycles without intersection point, 1 <=> 4, 2 => 3 => 5 => 2
// if duplicate number exists, there will be a intersection point
// 快慢指针，类似 142. Linked List Cycle II
class Solution {
    public int findDuplicate(int[] nums) {
        if(nums.length <= 1) {
            return -1;
        }
        
        // chasing and meet
        int slow = next(nums, 0); // nums[0]
        int fast = next(nums, next(nums, 0)); // nums[nums[0]]
        while(slow != fast) {
            slow = next(nums, slow);    // nums[slow], 1 step
            fast = next(nums, next(nums, fast));    // 2 steps
        }

        // then same speed
        fast = 0;
        while(slow != fast) {
            fast = next(nums, fast);
            slow = next(nums, slow);
        }
        return slow;
    }

    public int next(int[] nums, int num) {
        return nums[num];
    }
}