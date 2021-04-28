// 465. Optimal Account Balancing
// NP Complete
// e.g. [[0, 1, 10], [2, 0, 5]]
// 0,   1,   2
// 10, -10
// -5,       5
// 5,  -10,  5
// 5-5, -10+10, 5-5
// 0,    0,   0
// clean all numbers into 0
// DFS
class Solution {
    public int minTransfers(int[][] transactions) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] t : transactions) {
            map.put(t[0], map.getOrDefault(t[0], 0) - t[2]);
            map.put(t[1], map.getOrDefault(t[1], 0) + t[2]);
        }

        List<Integer> debts = new ArrayList<>();
        for (Integer debt : map.values()) {
            if (debt != 0) {
                debts.add(debt);
            }
        }

        return dfs(0, debts);
    }

    public int dfs(int start, List<Integer> debts) {
        while (start < debts.size() && debts.get(start) == 0) {
            start++;
        }

        if (start >= debts.size() - 1) {
            return 0;
        }

        int res = Integer.MAX_VALUE;
        for (int i = start + 1; i < debts.size(); i++) {
            if (debts.get(start) * debts.get(i) < 0) {
                debts.set(i, debts.get(i) + debts.get(start));
                res = Math.min(res, 1 + dfs(start + 1, debts));
                debts.set(i, debts.get(i) - debts.get(start));
            }
        }

        return res;
    }
}

// DFS with optimization
class Solution {
    public int minTransfers(int[][] transactions) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int[] t : transactions) {
            map.put(t[0], map.getOrDefault(t[0], 0) - t[2]);
            map.put(t[1], map.getOrDefault(t[1], 0) + t[2]);
        }

        List<Integer> debts = new ArrayList<>();
        for (Integer debt : map.values()) {
            if (debt != 0) {
                debts.add(debt);
            }
        }

        return removeMatchedDebt(debts) + dfs(0, debts);
    }

    public int dfs(int start, List<Integer> debts) {
        while (start < debts.size() && debts.get(start) == 0) {
            start++;
        }

        if (start >= debts.size() - 1) {
            return 0;
        }

        int res = Integer.MAX_VALUE;
        for (int i = start + 1; i < debts.size(); i++) {
            if (debts.get(start) * debts.get(i) < 0) {
                debts.set(i, debts.get(i) + debts.get(start));
                res = Math.min(res, 1 + dfs(start + 1, debts));
                debts.set(i, debts.get(i) - debts.get(start));
            }
        }

        return res;
    }

    private int removeMatchedDebt(List<Integer> debts) {
        Collections.sort(debts);

        int left = 0, right = debts.size() - 1;
        int match = 0;
        while (left < right) {
            if (debts.get(left) + debts.get(right) == 0) {
                debts.remove(left);
                debts.remove(right - 1);
                right -= 2;
                match++;
            } else if (-1 * debts.get(left) > debts.get(right)) {
                left++;
            } else {
                right--;
            }
        }
        return match;
    }
}

// brute-force, DFS, TLE
class Solution {
    public int minTrans = Integer.MAX_VALUE;
    public int minTransfers(int[][] transactions) {
        Map<Integer, Integer> map = new HashMap<>();    // person => money
        for (int i = 0; i < transactions.length; i++) {
            int id_a = transactions[i][0];
            int id_b = transactions[i][1];
            int money = transactions[i][2];
            map.put(id_a, map.getOrDefault(id_a, 0) - money);
            map.put(id_b, map.getOrDefault(id_b, 0) + money);
        }
                    
        List<Integer> debt = new ArrayList<>();
        for (Integer i : map.values()) {
            if (i != 0) {
                debt.add(i);
            }
        }
        
        dfs(0, 0, debt);
        return minTrans;
    }
                    
    public void dfs(int trans, int finish, List<Integer> debt) {
        if (finish == debt.size()) {
            minTrans = Math.min(minTrans, trans);
            return;
        }
        
        // try all possibilities in one dfs level, need to optimize, do pruning
        for (int i = 0; i < debt.size(); i++) {
            if (debt.get(i) > 0) {
                for (int j = 0; j < debt.size(); j++) {
                    if (debt.get(j) < 0) {
                        // try 1 transaction
                        int pos_money = debt.get(i);
                        int neg_money = debt.get(j);
                        // 3 cases
                        if (pos_money + neg_money > 0) {
                            // enough money
                            debt.set(i, debt.get(i) + neg_money);
                            debt.set(j, 0);
                            dfs(trans + 1, finish + 1, debt);
                            // restore for next loop
                            debt.set(i, debt.get(i) - neg_money);
                            debt.set(j, neg_money);
                        } else if (pos_money + neg_money < 0) {
                            // not enough money
                            debt.set(i, 0);
                            debt.set(j, debt.get(j) + pos_money);
                            dfs(trans + 1, finish + 1, debt);
                            // restore for next loop
                            debt.set(i, pos_money);
                            debt.set(j, debt.get(j) - pos_money);
                        } else {
                            // just enough money
                            debt.set(i, 0);
                            debt.set(j, 0);
                            dfs(trans + 1, finish + 2, debt);
                            // restore for next loop
                            debt.set(i, pos_money);
                            debt.set(j, neg_money);
                        }
                    }
                }
            }
        }
    }
}

// !!! BFS is wrong
// [[1,0,18],[2,1,9],[4,3,11],[5,4,10],[5,6,7],[7,6,5],[8,7,3]]