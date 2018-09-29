// 40. Combination Sum II
// 比 39 I 多了一个input数组可能含有重复数字的条件，而且每个数字只能使用一次
// 处理方式是排序，然后取相同数字的时候，观察是否前一个相同数字已经取过了，如果没有取过，就不能取当前的数字
// 与 39 的区别就是两个地方： i > startIndex 的时候防重复，每次递归 startIndex + 1
// 与 90 Subsets II 基本一个模板
class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(candidates);
        if (candidates == null || candidates.length == 0) {
            return results;
        }

        List<Integer> subset = new ArrayList<>();
        int sum = 0;
        helper(candidates, target, results, subset, 0, sum);
        return results;
    }

    private void helper(int[] candidates, int target, List<List<Integer>> results, List<Integer> subset, int startIndex,
            int sum) {
        if (sum == target) {
            results.add(new ArrayList<>(subset));
            return;
        }

        for (int i = startIndex; i < candidates.length; ++i) {
            if (sum + candidates[i] <= target) {
                // 当前的数字 candidates[startIndex] 与 candidates[startIndex - 1] 相同没关系，但是 candidates[startIndex + 1] 及其以后的数字重复就不能取
                if (i > startIndex && candidates[i] == candidates[i - 1]) {
                    continue;
                }
                subset.add(candidates[i]);
                // 每个数字只能取一次，改成 i + 1就行
                helper(candidates, target, results, subset, i + 1, sum + candidates[i]);
                subset.remove(subset.size() - 1);
            } 
        }
    }
}
