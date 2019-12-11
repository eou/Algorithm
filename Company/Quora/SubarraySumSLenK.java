import java.util.*;

// find number of subarray whose length is less than k and sum is s
class Solution {
    public static int numberOfSubarray(int[] nums, int s, int k) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<Integer> list = new ArrayList<>();
        list.add(-1);
        map.put(0, list);

        int res = 0, sum = 0;
        List<Integer> tmp;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - s)) {
                tmp = map.get(sum - s);
                if (tmp.size() > 0) {
                    for (int j = 0; j < tmp.size(); j++) {
                        if (i - tmp.get(j) <= k) {
                            System.out.print(i + ", ");
                            System.out.println(tmp.get(j));
                            res++;
                        }
                    }
                }
            }
            if (map.containsKey(sum)) {
                tmp = map.get(sum);
            } else {
                tmp = new ArrayList<>();
            }
            tmp.add(i);
            map.put(sum, tmp);
        }
        return res;
    }

    public static void main(String[] args) {
        int[] a = new int[]{-1, 1, 2, -1, 4, 0, -4, 1, -1, 0};
        int[] b = new int[] { -1, 1, 0 };
        System.out.println(numberOfSubarray(a, 0, 3));
        System.out.println(numberOfSubarray(b, 0, 3));
        System.out.println(numberOfSubarray(b, 0, 1));
    }
}