// 254. Factor Combinations
// 一个数大约有 logn 个因子，所有答案组合大约是 2^(logn) = n 个，每个解的复杂度为 logn，时间复杂度为 O(nlogn)
// 或者 写成 T(n) = T(n / 2) + T(n / 3) + T(n / 4) + ... + T(n / n) => T(n) = O(nlogn)
// 或者 所有因子全组合的时间复杂度为 O((logn)!)，每一层都 n 次循环和 logn 的递归深度，所以如果是从 2 ~ n 循环的话就为 O((logn)! * n ^ (logn))，如果是 2 ~ √n，就为 O((logn)! * √n ^ (logn))
class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> results = new ArrayList<>();
        helper(2, n, new ArrayList<>(), results);
        return results;
    }

    private void helper(int start, int n, List<Integer> factors, List<List<Integer>> results) {
        if (n == 1) {
            if (factors.size() > 1) {
                results.add(new ArrayList<>(factors));
            }
            return;
        }

        for (int i = start; i <= n; i++) {
            if (n % i == 0) {
                factors.add(i);
                helper(i, n / i, factors, results);
                factors.remove(factors.size() - 1);
            }
        }
    }
}

class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> results = new ArrayList<>();
        helper(2, n, new ArrayList<>(), results);
        return results;
    }
    
    private void helper(int start, int n, List<Integer> factors, List<List<Integer>> results) {
        // 这里也可以写 i * i <= n; 简洁一些
        for(int i = start; i <= (int)Math.sqrt((double)n); i++) {  
            if(n % i == 0) {
                List<Integer> list = new ArrayList<>(factors);
                list.add(i);
                helper(i, n / i, list, results);
                list.add(n / i);
                results.add(list);
            }
        }
    }
}

class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> results = new ArrayList<>();
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                List<List<Integer>> tmp = getFactors(n / i);
                List<Integer> factors = new ArrayList<>();
                factors.add(i);
                factors.add(n / i);
                results.add(factors);
                for (List<Integer> list : tmp) {
                    if (i <= list.get(0)) {
                        list.add(0, i);
                        results.add(list);
                    }
                }
            }
        }

        return results;
    }
}