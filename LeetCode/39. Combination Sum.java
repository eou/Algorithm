// 39. Combination Sum
// input数组元素不重复
// 可以重复使用input数组元素
class Solution {
    // 由于input元素不重复，可以不排序
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        
        if (candidates == null || candidates.length == 0) {
            return results;
        }
        
        List<Integer> subset = new ArrayList<>();
        int sum = 0;
        helper(candidates, target, results, subset, 0, sum);
        return results;
    }
    
    // 注意startIndex这个参数，如果没有，就会导致结果的数组重复，即每次不能从后往前取数
    private void helper(int[] candidates, int target, List<List<Integer>> results, List<Integer> subset, int startIndex, int sum) {
        if (sum == target) {
            results.add(new ArrayList<>(subset));
            return;
        }
        
        for (int i = startIndex; i < candidates.length; ++i) {
            if (sum + candidates[i] <= target) {
                subset.add(candidates[i]);
                helper(candidates, target, results, subset, i, sum + candidates[i]);
                subset.remove(subset.size() - 1);
            }
        }
    }
}

class Solution {
    // 如果加一个input数组含有重复数字的条件
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();
        
        if (candidates == null || candidates.length == 0) {
            return results;
        }

        Arrays.sort(candidates);
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
            // 一个剪枝
            if (candidates[i] > target) {
                return;
            }

            // 如果有重复数字，排序后每次只允许取相同数字的第一个；i > 0 仅仅是防止越界
            if (i > 0 && candidates[i] == candidates[i - 1]) {
                continue;
            }
            if (sum + candidates[i] <= target) {
                subset.add(candidates[i]);
                helper(candidates, target, results, subset, i, sum + candidates[i]);
                subset.remove(subset.size() - 1);
            }
        }
    }
}