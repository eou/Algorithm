// 679. 24 Game
class Solution {
    // 理论上要遍历所有组合来检查是否合法，必然是DFS
    public boolean judgePoint24(int[] nums) {
        List<Double> list = new ArrayList<>();
        for (int n : nums) {
            list.add((double) n);
        }
        return helper(list);
    }
    
    private boolean helper(List<Double> nums) {
        if (nums.size() == 1) {
            return Math.abs(nums.get(0) - 24) < 1e-7;
        }
        
        for (int i = 0; i < nums.size(); ++i) {
            for (int j = i + 1; j < nums.size(); ++j) {
                // save remaining numbers in order to calculate in next recursion
                List<Double> tmp = new ArrayList<>();
                for (int k = 0; k < nums.size(); ++k) {
                    if (k != i && k != j) {
                        tmp.add(nums.get(k));
                    }
                }
                
                // calculate
                for (int k = 0; k < 6; ++k) {
                    double n = 0; // 注意必须初始化，double n; 会报错
                    if (k == 0) {
                        n = nums.get(i) + nums.get(j);
                    } else if (k == 1) {
                        n = nums.get(i) * nums.get(j);
                    } else if (k == 2) {
                        n = nums.get(i) - nums.get(j);
                    } else if (k == 3) {
                        n = nums.get(j) - nums.get(i);
                    } else if (k == 4 && nums.get(j) != 0) {
                        n = nums.get(i) / nums.get(j);
                    } else if (nums.get(i) != 0) {
                        n = nums.get(j) / nums.get(i);
                    }
                    tmp.add(n);
                    // 如果这种组合可以，就返回true
                    if (helper(tmp)) {
                        return true;
                    }
                    tmp.remove(n);
                }
            }
        }
        
        return false;
    }
}

class Solution {
    public boolean judgePoint24(int[] nums) {
        // test all permutations
        // 4! * 4^3 * 5 = 7680 ? A(4, 2) * A(3, 2) * A(2, 2) * 4^3 = 9216 ?
        // use ArrayList to replace array since we need to do more add and remove operations
        List<Double> list = new ArrayList<>();
        for (int num : nums) {
            list.add((double)num);
        }
        return dfs(list);
    }
    
    public boolean dfs(List<Double> list) {
        // only one number, check result
        if (list.size() == 1) {
            return Math.abs(list.get(0) - 24.0) <= 1e-7;
        }
        
        // calculate pairs of numbers
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < i; j++) {
                double num1 = list.get(i);
                double num2 = list.get(j);
                List<Double> results = new ArrayList<>();   // cannot use Set since add and remove operation cannot keep the same order
                // calculate all possibilities
                results.addAll(Arrays.asList(num1 + num2, num1 - num2, num2 - num1, num1 * num2));
                if (num1 != 0) {
                    results.add(num2 / num1);
                }
                if (num2 != 0) {
                    results.add(num1 / num2);
                }
                
                // backtracking
                list.remove(num1);  // double will not treated as index
                list.remove(num2);
                for (double result : results) {
                    list.add(result);
                    if (dfs(list)) {
                        return true;
                    }
                    list.remove(result);
                }
                
                // restore the numbers and the order !
                list.add(j, num2);
                list.add(i, num1);
            }
        }
        
        return false;
    }
}

// 编程之美 1.16
class Solution {
    private static Map<Integer, Set<Double>> S;
    private static int A[];
    private static double EPS = 1e-7;

    public boolean judgePoint24(int[] nums) {
        S = new HashMap<Integer, Set<Double>>();
        A = nums;

        for (int i = 0; i < (1 << 4); i++) {
            Set<Double> set = new HashSet<>();
            S.put(i, set);
        }

        for (int i = 0; i < 4; i++) {
            Set<Double> set = new HashSet<>();
            set.add(Double.valueOf(A[i]));
            S.put(1 << i, set);
        }

        // for (int i = 1; i < (1<<4); i++) {
        // fork(i);
        // }
        fork((1 << 4) - 1);

        Set<Double> mSet = S.get((1 << 4) - 1);
        for (Double n : mSet) {
            if (Math.abs(n - 24) < EPS) {
                return true;
            }
        }

        return false;
    }

    private Set<Double> fork(int m) {
        Set<Double> mSet = S.get(m);
        if (mSet.size() > 0) {
            return mSet;
        } else {
            for (int x = 1; x < m; x++) {
                if ((x & m) == x) {
                    Set<Double> s1 = fork(x);
                    Set<Double> s2 = fork(m - x);
                    for (Double n1 : s1) {
                        for (Double n2 : s2) {
                            mSet.add(n1 + n2);

                            mSet.add(n1 - n2);

                            mSet.add(n2 - n1);

                            mSet.add(n1 * n2);

                            if (Math.abs(n1) > EPS) {
                                mSet.add(n2 / n1);
                            }
                            if (Math.abs(n2) > EPS) {
                                mSet.add(n1 / n2);
                            }
                        }
                    }  
                }
            }
            return mSet;
        }

    }
}