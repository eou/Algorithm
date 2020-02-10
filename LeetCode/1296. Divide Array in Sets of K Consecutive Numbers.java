// 1296. Divide Array in Sets of K Consecutive Numbers
// len / k is the length of subarray
class Solution {
    public boolean isPossibleDivide(int[] nums, int k) {
        if (nums.length % k != 0) {
            return false;
        }
        if (k == 1) {
            return true;
        }

        int[] rec = new int[k];
        for (int num : nums) {
            rec[num % k]++;
        }

        for (int r : rec) {
            if (r != nums.length / k) {
                return false;
            }
        }
        return true;
    }
}

class Solution {
    public boolean isPossibleDivide(int[] nums, int k) {
        if (nums.length % k != 0) {
            return false;
        }
        if (k == 1) {
            return true;
        }
        
        Arrays.sort(nums);
        // int[]: index of current consecutive subarray, number of current consecutive subarray
        PriorityQueue<int[]> pq = new PriorityQueue<int[]>((a, b) -> {return a[1] - b[1];});
        
        for (int num : nums) { 
            if (pq.isEmpty()) {
                pq.offer(new int[]{1, num});
                continue;
            } else {
                if (pq.peek()[1] == num) {
                    // another subarray
                    pq.offer(new int[]{1, num});
                } else {
                    int[] cur  = pq.poll();
                    // next number
                    if (cur[1] == num - 1) {
                        cur[0]++;
                        cur[1]++;
                        if (cur[0] < k) {
                            // not finished
                            pq.offer(cur);
                        }
                    } else {
                        return false;
                    }
                }   
            }
        }
        
        return pq.isEmpty();
    }
}

class Solution {
    public boolean isPossibleDivide(int[] nums, int k) {
        if (nums.length % k != 0) {
            return false;
        }
        
        // use strategy in 128.Longest Consecutive Array ?
        TreeMap<Integer, Integer> map = new TreeMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }
        
        int len = nums.length;
        while(len > 0) {
            int start = 0;
            // keep order
            for (int num : map.keySet()) {
                if (map.get(num) > 0) {
                    start = num;
                }
            }
            for (int i = start; i > start - k; i--) {
                if (!map.containsKey(i) || map.get(i) == 0) {
                    return false;
                } else {
                    map.put(i, map.get(i) - 1);
                    len--;
                }
            }
        }
        
        return true;
    }
}

class Solution {
    public boolean isPossibleDivide(int[] nums, int k) {
        if (nums.length % k != 0) {
            return false;
        }

        Arrays.sort(nums);

        int len = nums.length;
        while (len > 0) {
            int start = -1, startNum = 0;
            // keep order
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != Integer.MAX_VALUE) {
                    start = i;
                    startNum = nums[start]; // nums[start] will be modified, thus needs to be saved
                    break;
                }
            }

            int index = 0;
            for (int i = start; index < k && i < nums.length; i++) {
                if (nums[i] != Integer.MAX_VALUE) {
                    if (nums[i] == startNum + index) {
                        index++;
                        nums[i] = Integer.MAX_VALUE;
                        len--;
                    }
                }
            }
            if (index != k) {
                return false;
            }
        }

        return true;
    }
}