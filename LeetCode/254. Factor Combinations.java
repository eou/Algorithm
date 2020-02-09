// 254. Factor Combinations
// LintCode 652. Factorization testcases 更加严格
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

// LeetCode, LintCode 均 AC
class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> results = new ArrayList<>();
        helper(2, n, new ArrayList<>(), results);
        return results;
    }
    
    private void helper(int start, int n, List<Integer> factors, List<List<Integer>> results) {
        // 这里也可以写 i * i <= n; 简洁一些
        for(int i = start; i <= Math.sqrt(n); i++) {  
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

// 652. Factorization
// dfs, Memory Limit Exceeded, 50% passed
public class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                // Arrays.asList(i, n / i) cannot be added or removed, will throw exception
                res.add(new ArrayList<>(Arrays.asList(i, n / i)));  
                List<List<Integer>> factorRes = getFactors(n / i);
                for (List<Integer> list : factorRes) {
                    if (i <= list.get(0)) {
                        list.add(0, i);
                        res.add(list);
                    }
                }
            }
        }
        
        return res;
    }
}

// dfs with memo, Memory Limit Exceeded, 75% passed
public class Solution {
    public Map<Integer, List<List<Integer>>> memo = new HashMap<>();
    public List<List<Integer>> getFactors(int n) {
        if (memo.containsKey(n)) {
            return memo.get(n);
        }

        List<List<Integer>> res = new ArrayList<>();
        for (int i = 2; i * i <= n; i++) {
            if (n % i == 0) {
                res.add(new ArrayList<>(Arrays.asList(i, n / i)));
                List<List<Integer>> factorRes = null;
                if (memo.containsKey(n / i)) {
                    factorRes = memo.get(n / i);
                } else {
                    factorRes = getFactors(n / i);
                }

                for (List<Integer> list : factorRes) {
                    if (i <= list.get(0)) {
                        List<Integer> newList = new ArrayList<>(list);
                        newList.add(0, i);
                        res.add(newList);
                    }
                }
            }
        }

        memo.put(n, res);
        return res;
    }
}

// dfs, TLE, 2147483647
public class Solution {
    List<List<Integer>> res = new ArrayList<>();
    public List<List<Integer>> getFactors(int n) {
        dfs(2, n, new ArrayList<>());
        return res;
    }

    public void dfs(int start, int remain, List<Integer> cur) {
        if (remain == 1) {
            if (cur.size() != 1) {
                res.add(new ArrayList<>(cur)); // deep copy
            }
            return;
        }

        for (int i = start; i * i <= remain; i++) {
            if (remain % i == 0) {
                cur.add(i); // 进栈
                dfs(i, remain / i, cur);
                cur.remove(cur.size() - 1); // 出栈
            }
        }

        cur.add(remain);
        dfs(remain, 1, cur);
        cur.remove(cur.size() - 1);
    }
}

// AC
public class Solution {
    public List<List<Integer>> getFactors(int n) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(n, 2, res, new ArrayList<>());
        return res;
    }

    public void dfs(int n, int start, List<List<Integer>> res, List<Integer> tmp) {
        if (n == 1 && tmp.size() > 1) {
            res.add(new ArrayList<>(tmp));
            return;
        }

        for (int i = start; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                tmp.add(i);
                dfs(n / i, i, res, tmp);
                tmp.add(n / i);
                res.add(new ArrayList<>(tmp));
                tmp.remove(tmp.size() - 1);
                tmp.remove(tmp.size() - 1);
            }
        }
    }
}