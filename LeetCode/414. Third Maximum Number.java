// 414. Third Maximum Number
class Solution {
    public int thirdMax(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }

        long[] max = new long[3];
        Arrays.fill(max, Long.MIN_VALUE);
        for (int i = 0; i < nums.length; i++) {
            long num = (long)nums[i];
            // avoid duplicate
            if (num == max[0] || num == max[1] || num == max[2]) {
                continue;
            }
            for (int j = 2; j >= 0; j--) {
                if (num > max[j]) {
                    long tmp = max[j];
                    max[j] = num;
                    num = tmp;
                }
            }
        }
        
        return max[0] == Long.MIN_VALUE ? (int)max[2] : (int)max[0];
    }
}

class Solution {
    public int thirdMax(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        TreeSet<Integer> set = new TreeSet<>();
        for (Integer elem : nums) {
            set.add(elem);
            if (set.size() > 3) {
                set.remove(set.first());
            }
        }

        return set.size() < 3 ? set.last() : set.first();
    }
}