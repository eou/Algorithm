/**
 * Combinations From N List.java
 * n 个数组中各选一个数字组合起来
 */
import java.util.*;
import java.io.*;

public class Solution {
    // 非递归版本
    public List<List<Integer>> combination1(List<List<Integer>> nums) {
        List<List<Integer>> results = new ArrayList<>();
        Deque<Integer> stack = new ArrayDeque<>();
        int[] ptr = new int[nums.size()];

        int i = 0;
        while (i >= 0) {
            if (ptr[i] < nums.get(i).size()) {
                stack.push(nums.get(i).get(ptr[i]++));
                i++;
                if (i == nums.size()) {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.addAll(stack);
                    results.add(tmp);

                    stack.pop();
                    i--;
                }
            } else {
                if (stack.isEmpty()) {
                    break;
                }
                ptr[i] = 0;
                stack.pop();
                i--;
            }
        }

        return results;
    }

    // 递归版本
    public List<List<Integer>> combination2(List<List<Integer>> nums) {
        List<Integer> cur = new ArrayList<>();
        List<List<Integer>> results = new ArrayList<>();
        helper(nums, 0, cur, results);

        return results;
    }

    public void helper(List<List<Integer>> nums, int start, List<Integer> cur, List<List<Integer>> results) {
        for (int i = 0; i < nums.get(start).size(); i++) {
            cur.add(nums.get(start).get(i));

            if (start < nums.size() - 1) {
                helper(nums, start + 1, cur, results);
            } else {
                results.add(new ArrayList<>(cur));
            }

            cur.remove(cur.size() - 1);
        }
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        List<Integer> list1 = Arrays.asList(1, 4, 5);
        List<Integer> list2 = Arrays.asList(2);
        List<Integer> list3 = Arrays.asList(9);
        List<Integer> list4 = Arrays.asList(10, 11);
        List<List<Integer>> nums = new ArrayList<>();
        nums.add(list1);
        nums.add(list2);
        nums.add(list3);
        nums.add(list4);

        List<List<Integer>> results1 = s.combination1(nums);
        List<List<Integer>> results2 = s.combination2(nums);
        for (List<Integer> list : results1) {
            for (Integer i : list) {
                System.out.print(i + ", ");
            }
            System.out.println();
        }

        for (List<Integer> list : results2) {
            for (Integer i : list) {
                System.out.print(i + ", ");
            }
            System.out.println();
        }

        for (int i = 0; i < results1.size(); i++) {
            Collections.sort(results1.get(i));
            Collections.sort(results2.get(i));
            for (int j = 0; j < results1.get(i).size(); j++) {
                if (results1.get(i).get(j) != results2.get(i).get(j)) {
                    System.out.println("Two results are different.");
                    return;
                }
            }
        }
        System.out.println("Two results are same.");
        return;
    }
}