// 47. Permutations II
// 比 46 I 多了一个input数组有重复元素，基本结构一致，加了一个排序和判断，与subsets, combination sum等题套路一致
class Solution {
    // 换一个 visited 数组记录是否访问过
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);
        if (nums == null || nums.length == 0) {
            return res;
        }

        List<Integer> permutation = new ArrayList<Integer>();
        // 用 visited 判断当前数字是否已经在 permutation 中
        dfs(nums, permutation, new boolean[nums.length], res);

        return res;
    }

    // 1. 递归的定义
    public void dfs(int[] nums, List<Integer> permutation, boolean[] visited, List<List<Integer>> res) {
        // 3. 递归的出口
        if (permutation.size() == nums.length) {
            res.add(new ArrayList<>(permutation));
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

                dfs(nums, permutation, visited, res);

                visited[i] = false;
                permutation.remove(permutation.size() - 1);
            }

        }
    }
}

class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        if (nums == null || nums.length == 0) {
            return res;
        }
        
        Arrays.sort(nums);
        dfs(res, nums, new ArrayList<>(), 0, new boolean[nums.length]);
        return res;
    }

    private void dfs(List<List<Integer>> res, int[] nums, List<Integer> list, int start, boolean[] visited) {
        if (list.size() == nums.length) {
            res.add(new ArrayList<>(list));
            return;
        }
        
        // Only used in this level
        Set<Integer> visitedSet = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            int j = (i + start) % nums.length;
            // cant add same number
            if (!visited[j]) {
                // cant add duplicate number
                if (!visitedSet.contains(nums[j])) {
                    list.add(nums[j]);
                    visited[j] = true;
                    visitedSet.add(nums[j]);
                    dfs(res, nums, list, j + 1, visited);
                    list.remove(list.size() - 1);
                    visited[j] = false;
                }
            }
        }
    }
}

class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        Set<List<Integer>> res = new HashSet<>();
        dfs(new ArrayList<>(), nums, new boolean[nums.length], res);
        return new ArrayList<>(res);
    }

    public void dfs(List<Integer> cur, int[] nums, boolean[] used, Set<List<Integer>> res) {
        if (cur.size() == nums.length) {
            res.add(new ArrayList<>(cur));
        }

        for (int i = 0; i < nums.length; i++) {
            if (!used[i]) {
                used[i] = true;
                cur.add(nums[i]);
                dfs(cur, nums, used, res);
                cur.remove(cur.size() - 1);
                used[i] = false;
            }
        }
    }
}