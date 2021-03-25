// 46. Permutations
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        if (nums == null || nums.length == 0) {
         return res; 
        }

        List<Integer> permutation = new ArrayList<Integer>();
        // 用 set 判断当前数字是否已经在 permutation 中
        Set<Integer> set = new HashSet<>();
        dfs(nums, permutation, set, res);

        return res;
    }
    
    // 1. 递归的定义，找到所有以 permutation 开头的排列
    public void dfs(int[] nums, List<Integer> permutation, Set<Integer> set, List<List<Integer>> res) {
        // 3. 递归的出口
        if (permutation.size() == nums.length) {
            res.add(new ArrayList<>(permutation));
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
            
            dfs(nums, permutation, set, res);
            
            set.remove(nums[i]);
            permutation.remove(permutation.size() - 1);
        }
    }
}

class Solution {
    // 换一个 visited 数组记录是否访问过
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        if (nums == null || nums.length == 0) {
            return res;
        }

        // 用 boolean[] 判断当前数字是否已经在 permutation 中
        dfs(nums, new ArrayList<>(), new boolean[nums.length], res);

        return res;
    }

    // 1. 递归的定义
    public void dfs(int[] nums, List<Integer> permutation, boolean[] visited, List<List<Integer>> res) {
        // 3. 递归的出口
        if (permutation.size() == nums.length) {
            res.add(new ArrayList<Integer>(permutation));
            return;
        }

        // [3] => [3,1], [3,2], [3,4] ...
        for (int i = 0; i < nums.length; i++) {
            // 如果已经取过此数字就跳过
            if (!visited[i]) {
                permutation.add(nums[i]);
                visited[i] = true;

                dfs(nums, permutation, visited, res);

                visited[i] = false;
                permutation.remove(permutation.size() - 1);
            }

        }
    }
}

// dfs
// given distinct integers
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        return dfs(set);
    }

    public List<List<Integer>> dfs(Set<Integer> nums) {
        if (nums.size() == 0) {
            List<List<Integer>> res = new ArrayList<>();
            res.add(new ArrayList<>());
            return res;
        }

        List<List<Integer>> res = new ArrayList<>();
        for (Integer num : nums) {
            Set<Integer> nextNums = new HashSet<>(nums);
            nextNums.remove(num);
            for (List<Integer> list : dfs(nextNums)) {
                list.add(num);
                res.add(list);
            }
        }

        return res;
    }
}