// 90. Subsets II
// 与 78 I 不同之处在于有重复数字
// 与 40. Combination Sum II 一个模板
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        // NullPointerException in Arrays.sort or nums.length in helper
        if (nums == null) {
            return results;
        }

        // required
        Arrays.sort(nums);

        helper(nums, 0, new ArrayList<>(), results);
        return results;
    }

    public void helper(int[] nums, int startIndex, List<Integer> subset, List<List<Integer>> results) {
        results.add(new ArrayList<Integer>(subset));

        for(int i = startIndex; i < nums.length; i++) {
            // Use a filter to avoid duplication
            // (1,2,2',2'')
            // (1), (2), (2')? (2'')? start from []
            // (1,2), (1,2')? start from [1]
            // (2,2'), (2,2'')? start from [2]
            // (1,2,2'), (1,2,2'') start from [1,2]
            // (2,2',2'') start from [2,2']
            // ...
            // 此策略保证 (2,2') 和 (2,2'') 不会同时出现，因为已经排序，且在同一层递归中只能与起始数字相邻的重复数字可以添加进去，继续递归
            // 换言之，对于一列同样的数字，只能是第一个数字加入集合中
            // i != 0 已经在 i > startIndex 中保证了
            if (i > startIndex && nums[i] == nums[i - 1]) {
                continue;
            }

            subset.add(nums[i]);
            helper(nums, i + 1, subset, results);
            subset.remove(subset.size() - 1);
        }
    }
}
