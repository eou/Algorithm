// 77. Combinations
// DFS
class Solution {
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> res = new ArrayList<>();
        if (n == 0 || k == 0 || n < k) {
            return res;
        }

        dfs(res, n, k, 1, new ArrayList<>());
        return res;
    }

    private void dfs(List<List<Integer>> res, int n, int k, int start, List<Integer> list) {
        // NO EXIT since start > n won't jump into the loop
        for (int i = start; i <= n; i++) {
            list.add(i);
            if (list.size() == k) {
                res.add(new ArrayList<>(list));
            }
            dfs(res, n, k, i + 1, list);
            list.remove(list.size() - 1);
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

        //!!! C(n, k) = C(n-1, k-1) + C(n-1, k)
        result = combine(n - 1, k - 1);
        for (List<Integer> list : result) {
            list.add(n);
        }
        result.addAll(combine(n - 1, k));

        return result;
    }
}

class Solution {
    // 非递归版本，每次改变相邻的两个数字
    // n = 3, k = 2: 初始状态：[0, 0] 
    // => [1, 0] => [1, 1] √ => [1, 2] √ => [1, 3] √ => 溢出：[1, 4]
    // => [2, 4] => [2, 2] √ => [2, 3] √ => [2, 4]
    // => [3, 4] => [3, 3] √ => [3, 4]
    // => [4, 4] => i = -1
    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> results = new ArrayList<>();
        List<Integer> cur = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            cur.add(0);
        }

        int i = 0;
        while (i >= 0) {
            cur.set(i, cur.get(i) + 1);
            if (cur.get(i) > n) {
                i--;
            } else if (i == k - 1) {
                results.add(new ArrayList<>(cur));
            } else {
                i++;
                cur.set(i, cur.get(i - 1));
            }
        }

        return results;
    }
}