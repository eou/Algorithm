// 465. Optimal Account Balancing
// DFS, TLE
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

// DFS, do one transaction one dfs level, avoid duplicate calculation
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
        
        for (int i = 0; i < debt.size(); i++) {
            if (debt.get(i) > 0) {
                for (int j = 0; j < debt.size(); j++) {
                    if (debt.get(j) < 0) {
                        // try 1 transaction
                        int pos_money = debt.get(i);
                        int neg_money = debt.get(j);
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
                return;
            }
            // just try transaction for debt.get(i) with other debt
        }
    }
}

// DFS with optimization
class Solution {
    public int minTransfers(int[][] transactions) {
        if (transactions == null || transactions.length == 0 || transactions[0].length == 0)
            return 0;
        //calculate delta for each account
        Map<Integer, Integer> accountToDelta = new HashMap<>();
        for (int[] transaction : transactions) {
            int from = transaction[0];
            int to = transaction[1];
            int val = transaction[2];
            if (!accountToDelta.containsKey(from)) {
                accountToDelta.put(from, 0);
            }
            if (!accountToDelta.containsKey(to)) {
                accountToDelta.put(to, 0);
            }
            accountToDelta.put(from, accountToDelta.get(from) - val);
            accountToDelta.put(to, accountToDelta.get(to) + val);
        }
        List<Integer> deltas = new ArrayList<Integer>();
        for (int delta : accountToDelta.values()) {
            if (delta != 0) {
                deltas.add(delta);
            }
        }
        
        // since minTransStartsFrom is slow, we can remove matched deltas to optimize it
        // for example, if account A has balance 5 and account B has balance -5, we know
        // that one transaction from B to A is optimal.
        int matchCount = removeMatchDeltas(deltas);
        // try out all possibilities to get minimum number of transactions
        return matchCount + minTransStartsFrom(deltas, 0);
    }

    // find same debts
    private int removeMatchDeltas(List<Integer> deltas) {
        Collections.sort(deltas);
        int left = 0;
        int right = deltas.size() - 1;
        int matchCount = 0;
        while (left < right) {
            if (-1 * deltas.get(left) == deltas.get(right)) {
                deltas.remove(left);
                deltas.remove(right - 1);
                right -= 2;
                matchCount++;
            } else if (-1 * deltas.get(left) > deltas.get(right)) {
                left++;
            } else {
                right--;
            }
        }
        return matchCount;
    }

    private int minTransStartsFrom(List<Integer> deltas, int start) {
        int result = Integer.MAX_VALUE;
        int n = deltas.size();
        while (start < n && deltas.get(start) == 0) {
            start++;
        }
            
        if (start == n) {
            return 0;
        }
            
        for (int i = start + 1; i < n; i++) {
            if ((long) deltas.get(i) * deltas.get(start) < 0) {
                deltas.set(i, deltas.get(i) + deltas.get(start));
                result = Math.min(result, 1 + minTransStartsFrom(deltas, start + 1));
                deltas.set(i, deltas.get(i) - deltas.get(start));
            }
        }
        return result;
    }
}

// 注意 BFS 是错误的
// [[1,0,18],[2,1,9],[4,3,11],[5,4,10],[5,6,7],[7,6,5],[8,7,3]]