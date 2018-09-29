// 216. Combination Sum III
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> results = new ArrayList<>();
        // 不需要处理边界情况
        // if (k > n || n < 0) {
        //     return results;
        // }
        
        // if (k == 0 || n == 0) {
        //     results.add(new ArrayList<>());
        //     return results;
        // }
        
        List<Integer> subset = new ArrayList<>();
        
        helper(k, n, subset, results, 1, 0);
        return results;
    }
    
    private void helper(int size, int target, List<Integer> subset, List<List<Integer>> results, int startIndex, int sum) {
        if (subset.size() == size) {
            if (sum == target) {
                results.add(new ArrayList<>(subset));
            }
            return;
        }
        
        for (int i = startIndex; i <= 9; ++i) {
            if (i + sum <= target) {
                subset.add(i);
                helper(size, target, subset, results, i + 1, sum + i);
                subset.remove(subset.size() - 1);
            }
        }
    }
    
}