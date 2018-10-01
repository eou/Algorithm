// 46. Permutations
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        if (nums == null || nums.length == 0) {
         return results; 
        }

        List<Integer> permutation = new ArrayList<Integer>();
        // 用 set 判断当前数字是否已经在 permutation 中
        Set<Integer> set = new HashSet<>();
        helper(nums, permutation, set, results);

        return results;
    }
    
    // 1. 递归的定义，找到所有以 permutation 开头的排列
    public void helper(int[] nums, List<Integer> permutation, Set<Integer> set, List<List<Integer>> results) {
        // 3. 递归的出口
        if (permutation.size() == nums.length) {
            results.add(new ArrayList<Integer>(permutation));
            return;
        }

        // [3] => [3,1], [3,2], [3,4] ...
        for (int i = 0; i < nums.length; i++) {
            // 如果已经取过此数字就跳过
            if (set.contains(nums[i])) {
                continue;
            }

            permutation.add(nums[i]);
            set.add(nums[i]);
            
            helper(nums, permutation, set, results);
            
            set.remove(nums[i]);
            permutation.remove(permutation.size() - 1);
        }
    }
}

class Solution {
    // 换一个 visited 数组记录是否访问过
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

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

        // [3] => [3,1], [3,2], [3,4] ...
        for (int i = 0; i < nums.length; i++) {
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
