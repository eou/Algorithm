// 568. Maximum Vacation Days
class Solution {
    // 直接 DFS 会 TLE，时间复杂度为 O(N^K), N 个城市，K 个星期
    int max = 0;
    public int maxVacationDays(int[][] flights, int[][] days) {
        helper(0, 0, 0, flights, days);
        return max;
    }
    
    private void helper(int vacationDays, int week, int city, int[][] flights, int[][] days) {
        if(week == days[0].length) {
            if(max < vacationDays) {
                max = vacationDays;
            }
            return;
        }
        
        for(int i = 0; i < flights[0].length; i++) {
            if(flights[city][i] == 1) {
                vacationDays += days[i][week];
                helper(vacationDays, week + 1, i, flights, days);
                vacationDays -= days[i][week];
            } else {
                vacationDays += days[city][week];
                helper(vacationDays, week + 1, city, flights, days);
                vacationDays -= days[city][week];
            }
        }
    }
}

class Solution {
    // DFS 另一个版本
    public int maxVacationDays(int[][] flights, int[][] days) {
        return helper(0, 0, flights, days);
    }

    private int helper(int week, int city, int[][] flights, int[][] days) {
        if (week == days[0].length) {
            return 0;
        }

        int max = 0;
        for (int i = 0; i < flights[0].length; i++) {
            int vacationDays = 0;
            if (flights[city][i] == 1) {
                vacationDays = days[i][week] + helper(week + 1, i, flights, days);
            } else {
                vacationDays = days[city][week] + helper(week + 1, city, flights, days);
            }
            max = Math.max(vacationDays, max);
        }

        return max;
    }
}

class Solution {
    // DFS with memoization，时间复杂度为 O(n^2 * k)
    public int maxVacationDays(int[][] flights, int[][] days) {
        // memo[i][j] used to store the max number of vacactions that you will take when
        // you are in city i starting at week j
        int[][] memo = new int[flights.length][days[0].length];
        for (int[] i : memo) {
            Arrays.fill(i, -1);
        }
        return helper(0, 0, flights, days, memo);
    }

    private int helper(int week, int city, int[][] flights, int[][] days, int[][] memo) {
        if (week == days[0].length) {
            return 0;
        }

        if (memo[city][week] != -1) {
            return memo[city][week];
        }

        int max = 0;
        for (int i = 0; i < flights[0].length; i++) {
            int vacationDays = 0;
            if (flights[city][i] == 1) {
                vacationDays = days[i][week] + helper(week + 1, i, flights, days, memo);
            } else {
                vacationDays = days[city][week] + helper(week + 1, city, flights, days, memo);
            }
            max = Math.max(vacationDays, max);
        }

        memo[city][week] = max;
        return max;
    }
}

class Solution {
    // DP 版本，时间复杂度为 O(n^2 * k)
    public int maxVacationDays(int[][] flights, int[][] days) {
        if(flights.length == 0 || days.length == 0) {
            return 0;
        }

        int[][] dp = new int[flights.length][days[0].length + 1];
        for(int week = days[0].length - 1; week >= 0; week--) {
            for(int city = 0; city < days.length; city++) {
                dp[city][week] = days[city][week] + dp[city][week + 1];
                for(int otherCity = 0; otherCity < days.length; otherCity++) {
                    if(flights[city][otherCity] == 1) {
                        dp[city][week] = Math.max(days[otherCity][week] + dp[otherCity][week + 1], dp[city][week]);
                    }
                }
            }
        }

        return dp[0][0];
    }
}

class Solution {
    public int maxVacationDays(int[][] flights, int[][] days) {
        int N = flights.length;
        int K = days[0].length;
        // dp[i][j] means the maximum vacation days I can get until ith week in city j
        Integer[][] dp = new Integer[K][N];
        // week 0
        for (int i = 0; i < N; i++) {
            if (flights[0][i] == 1 || i == 0) {
                dp[0][i] = days[i][0];
            }
        }
        // in week i
        for (int i = 1; i < K; i++) {
            // start from city j
            for (int j = 0; j < N; j++) {
                // city j is reachable in last week
                if (dp[i - 1][j] != null) {
                    // come to city k or still stay city j to spend this week
                    for (int k = 0; k < N; k++) {
                        if (flights[j][k] == 1 || j == k) {
                            if (dp[i][k] != null) {
                                dp[i][k] = Math.max(dp[i][k], dp[i - 1][j] + days[k][i]);
                            } else {
                                dp[i][k] = dp[i - 1][j] + days[k][i];
                            }
                        }
                    }
                }
            }
        }

        // get the max vacation days;
        int max = 0;
        for (int i = 0; i < N; i++) {
            if (dp[K - 1][i] != null) {
                max = Math.max(max, dp[K - 1][i]);
            }
        }
        return max;
    }
}