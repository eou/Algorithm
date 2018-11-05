// 90. Subsets II
// 与 78 I 不同之处在于有重复数字
// 与 40. Combination Sum II 一个模板
class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (nums == null) {
            return results;
        }
        
        if (nums.length == 0) {
            results.add(new ArrayList<Integer>());
            return results;
        }

        Arrays.sort(nums);

        List<Integer> subset = new ArrayList<Integer>();
        helper(nums, 0, subset, results);
        return results;
    }

    public void helper(int[] nums, int startIndex, List<Integer> subset, List<List<Integer>> results){
        results.add(new ArrayList<Integer>(subset));

        for(int i = startIndex; i < nums.length; i++) {
            // 从 startIndex 开始往后数，startIndex 与 startIndex - 1 相同没有关系，但是之后 startIndex + 1 ,..., 与 startIndex 相同就在此次递归中不能选
            if (i != 0 && i > startIndex && nums[i] == nums[i - 1]) {
                continue;
            }

            subset.add(nums[i]);
            helper(nums, i + 1, subset, results);
            subset.remove(subset.size() - 1);
        }
    }
}
