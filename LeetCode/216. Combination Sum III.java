// 216. Combination Sum III
class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        if (k == 0 || n == 0 || n < k) {
            return res;
        }

        dfs(k, n, 0, 1, res, new ArrayList<>());
        return res;
    }

    private void dfs(int k, int n, int sum, int start, List<List<Integer>> res, List<Integer> list) {
        if (sum > n || list.size() > k) {
            return;
        }

        if (sum == n && list.size() == k) {
            res.add(new ArrayList<>(list));
        }

        for (int i = start; i <= 9; i++) {
            list.add(i);
            dfs(k, n, sum + i, i + 1, res, list);
            list.remove(list.size() - 1);
        }
    }
}