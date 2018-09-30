// 77. Combinations
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> results = new ArrayList<>();
        if (n < k) {
            return results;
        }
        if (n == k) {
            List<Integer> tmp = new ArrayList<>();
            for (int i = 1; i <= n; ++i) {
                tmp.add(i);
            }
            results.add(tmp);
            return results;
        }

        List<Integer> combination = new ArrayList<>();
        dfs(combination, k, 1, n, results);
        return results;
    }
    
    // 定义递归，参数有：每个数字组合，组合的大小，起点，结束，结果数组
    private void dfs(List<Integer> combination, int size, int start, int end, List<List<Integer>> results) {
        if (combination.size() == size) {
            // 注意不能直接results.add(combination);
            results.add(new ArrayList<>(combination));
            return;
        }

        for (int i = start; i <= end; ++i) {
            combination.add(i);
            dfs(combination, size, i + 1, end, results);
            combination.remove(combination.size() - 1);
        }
    }
}

class Solution {
    // 更简洁的递归
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        
        // 递归出口
        if (k > n || k < 0) {
            return result;
        }
        if (k == 0) {
            result.add(new ArrayList<Integer>());
            return result;
        }

        // C(n, k) = C(n-1, k-1) + C(n-1, k)
        result = combine(n - 1, k - 1);
        for (List<Integer> list : result) {
            list.add(n);
        }
        result.addAll(combine(n - 1, k));

        return result;
    }
}