// https://leetcode.com/discuss/interview-question/394347/google-oa-2019-watering-flowers-20
// focus on correctness, not efficiency
class Solution {
    // capacity1 and capacity2 are smaller than 1B
    // plants'size is larger than 1
    public int solution(int[] plants, int capacity1, int capacity2) {
        // unnecessary under this condition
        if (plants == null || plants.length == 1 || capacity1 == 0 || capacity2 == 0) {
            return 0;
        }

        int c1 = capacity1, c2 = capacity2;
        int times = 2;  // first refill
        // 4:5 0, 1 / 3, 2
        // 5:6 0, 1, 2 / 4, 3, 2
        for (int i = 0; i < (plants.length + 1) / 2; i++) {
            int myplant = plants[i];
            int friendsplant = plants[plants.length - i - 1];
            if (i == plants.length - i - 1) {
                // middle
                if (c1 + c2 < myplant) {
                    times++;
                }
            } else {
                if (c1 < myplant) {
                    // refill
                    times++;
                    c1 = capacity1 - myplant;
                } else {
                    c1 -= myplant;
                }
                if (c2 < friendsplant) {
                    // refill
                    times++;
                    c2 = capacity2 - friendsplant;
                } else {
                    c2 -= friendsplant;
                }
            }
        }

        return times;
        
        // while loop version
        // int left = 0, right = plants.length - 1;
        // while (left < right) {
        //     if (c1 < plants[left]) {
        //         c1 = capacity1;
        //         ++times;
        //     }
        //     if (c2 < plants[right]) {
        //         c2 = capacity2;
        //         ++times;
        //     }
        
        //     c1 -= plants[left++];
        //     c2 -= plants[right--];
        // }
        
        // if (left == right && (plants[left] > c1 + c2)) {
        //     return ++times;
        // } else {
        //     return times;
        // }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int[] plants = new int[]{2, 4, 5, 1, 2};
        int capacity1 = 5, capacity2 = 7;
        int times = s.solution(plants, capacity1, capacity2);
        System.out.println(times);
    }
}