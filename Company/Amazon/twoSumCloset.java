import java.io.*;
import java.util.*;

class twoSumCloset {
    public static List<Integer> solution1(List<Integer> nums, int target) {
        Collections.sort(nums);
        List<Integer> results = new ArrayList<>();
        int left = 0, right = nums.size() - 1;
        int diff = Integer.MAX_VALUE;
        while (left < right) {
            if (Math.abs(nums.get(left) + nums.get(right) - target) < diff) {
                results.clear();
                results.add(nums.get(left));
                results.add(nums.get(right));
                diff = Math.abs(nums.get(left) + nums.get(right) - target);
            }
            if (nums.get(left) + nums.get(right) > target) {
                right--;
            } else {
                left++;
            }
        }

        return results;
    }

    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 3, 5, 6, 8, 9, 4, -1);
        List<Integer> results = solution1(nums, 10);
        System.out.println(results.get(0));
        System.out.println(results.get(1));
    }
}