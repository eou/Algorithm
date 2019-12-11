// 136. Single Number
class Solution {
    public int singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for(int i : nums) {
            if(set.contains(i)) {
                set.remove(i);
            } else {
                set.add(i);
            }
        }
        
        // 访问 set 第一个元素
        return set.iterator().next();
    }
}

class Solution {
    public int singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int sum = 0, uniqSum = 0;
        for(int i : nums) {
            if(!set.contains(i)) {
                set.add(i);
                uniqSum += i;
            }
            sum += i;
        }
        
        return 2 * uniqSum - sum;
    }
}

class Solution {
    // a ^ 0 = a; a ^ a = 0; a ^ a c ^ c ^ b = 0 ^ b = b;
    public int singleNumber(int[] nums) {
        int result = 0;
        for (int i : nums) {
            result ^= i;
        }

        return result;
    }
}