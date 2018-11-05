// 47. Permutations II
// 比 46 I 多了一个input数组有重复元素，基本结构一致，加了一个排序和判断，与subsets, combination sum等题套路一致
class Solution {
    // 换一个 visited 数组记录是否访问过
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(nums);
        if (nums == null || nums.length == 0) {
            return results;
        }

        List<Integer> permutation = new ArrayList<Integer>();
        // 用 visited 判断当前数字是否已经在 permutation 中
        helper(nums, permutation, new boolean[nums.length], results);

        return results;
    }

    // 1. 递归的定义
    public void helper(int[] nums, List<Integer> permutation, boolean[] visited, List<List<Integer>> results) {
        // 3. 递归的出口
        if (permutation.size() == nums.length) {
            results.add(new ArrayList<Integer>(permutation));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // 去重：如果当前要取的元素与之前的元素相同，且前面的元素还没有被取，则当前元素不能取
            if (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1]) {
                continue;
            }
            // 如果已经取过此数字就跳过
            if (!visited[i]) {
                permutation.add(nums[i]);
                visited[i] = true;

                helper(nums, permutation, visited, results);

                visited[i] = false;
                permutation.remove(permutation.size() - 1);
            }

        }
    }
}