// 630. Course Schedule III
// Similar with 435.Non-overlapping Intervals
// Gready!!! https://leetcode-cn.com/problems/course-schedule-iii/solution/ke-cheng-biao-iii-by-leetcode/
// 1. Choose the earliest deadline course first! Sort.
// 2. Choose the shortest duration course. (Mathematcial induction, proof by contradict)
public class Solution {
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (i, j) -> i[1] - j[1]);

        PriorityQueue<Integer> heap = new PriorityQueue<>((a, b) -> b - a);
        int time = 0;
        for (int[] course : courses) {
            int duration = course[0];
            int deadline = course[1];
            if (time + duration <= deadline) {
                heap.offer(duration);
                time += duration;
            } else if (!heap.isEmpty() && heap.peek() > duration) {
                time += duration - heap.poll();
                heap.offer(duration);
            }
        }

        return heap.size();
    }
}

// DFS, TLE
class Solution {
    public int scheduleCourse(int[][] courses) {
        int res = 0;
        if (courses == null || courses.length == 0 || courses[0].length == 0) {
            return res;
        }

        return dfs(courses, new HashSet<>(), 0);
    }
    
    private int dfs(int[][] courses, Set<Integer> taken, int days) {
        if (taken.size() == courses.length) {
            return courses.length;
        }
        
        int res = taken.size();
        for (int i = 0; i < courses.length; i++) {
            if (!taken.contains(i) && days + courses[i][0] <= courses[i][1]) {
                taken.add(i);
                res = Math.max(res, dfs(courses, taken, days + courses[i][0]));
                taken.remove(i);
            }
        }
        
        return res;
    }
}

// DFS with memo, still TLE
class Solution {
    public int scheduleCourse(int[][] courses) {
        int res = 0;
        if (courses == null || courses.length == 0 || courses[0].length == 0) {
            return res;
        }

        Map<Set<Integer>, Integer> memo = new HashMap<>();
        for (int i = 0; i < courses.length; i++) {
            Set<Integer> set = new HashSet<>();
            set.add(i);
            res = Math.max(res, dfs(courses, set, courses[i][0], memo));
        }

        return res;
    }

    private int dfs(int[][] courses, Set<Integer> taken, int days, Map<Set<Integer>, Integer> memo) {
        if (taken.size() == courses.length) {
            return courses.length;
        }

        if (memo.containsKey(taken)) {
            return memo.get(taken);
        }

        int res = taken.size();
        for (int i = 0; i < courses.length; i++) {
            if (!taken.contains(i) && days + courses[i][0] <= courses[i][1]) {
                taken.add(i);
                res = Math.max(res, dfs(courses, taken, days + courses[i][0], memo));
                taken.remove(i);
            }
        }

        memo.put(new HashSet<>(taken), res);
        return res;
    }
}

// faster DFS, TLE
class Solution {
    public int scheduleCourse(int[][] courses) {
        // Has to sort first for this DFS solution
        // make sure the courses with earliest deadline would be picked first
        Arrays.sort(courses, (i, j) -> i[1] - j[1]);
        return helper(courses, 0, 0);

    }

    private static int helper(int[][] course, int day, int index) {
        // exit
        if (index == course.length) {
            return 0;
        }

        int[] cur = course[index];
        int taken = 0;

        if (day + cur[0] <= cur[1]) {
            taken = 1 + helper(course, day + cur[0], index + 1);
        }
        int not_taken = helper(course, day, index + 1);
        return Math.max(taken, not_taken);
    }
}

// faster DFS with memo, MLE!!! Cannot use DP either
// top-down DP
class Solution {
    public int scheduleCourse(int[][] courses) {
        // Has to sort first for this DFS solution
        // make sure the courses with earliest deadline would be picked first
        Arrays.sort(courses, (i, j) -> i[1] - j[1]);

        // [courses][days]
        int[][] memo = new int[courses.length][courses[courses.length - 1][1] + 1];
        return dfs(courses, 0, 0, memo);

    }

    private int dfs(int[][] course, int day, int index, int[][] memo) {
        // exit
        if (index == course.length) {
            return 0;
        }

        if (memo[index][day] != 0) {
            return memo[index][day];
        }

        int[] cur = course[index];
        int taken = 0;

        if (day + cur[0] <= cur[1]) {
            taken = 1 + dfs(course, day + cur[0], index + 1, memo);
        }
        int not_taken = dfs(course, day, index + 1, memo);

        memo[index][day] = Math.max(taken, not_taken);
        return memo[index][day];
    }
}

// bottom-up DP, MLE
class Solution {
    public int scheduleCourse(int[][] courses) {
        Arrays.sort(courses, (i, j) -> i[1] - j[1]);

        // [courses][days]
        int[][] dp = new int[courses.length + 1][courses[courses.length - 1][1] + 1];
        // days, start from 1 to avoid index -1
        for (int i = 1; i < dp[0].length; i++) {
            // courses
            for (int j = 1; j < dp.length; j++) {
                dp[j][i] = dp[j - 1][i];
                // i - courses[j - 1][0] >= 0 and ith day is before the deadline
                if (i >= courses[j - 1][0] && i <= courses[j - 1][1]) {
                    dp[j][i] = Math.max(dp[j][i], 1 + dp[j - 1][i - courses[j - 1][0]]);
                } else {
                    dp[j][i] = Math.max(dp[j][i], dp[j][i - 1]);
                }
            }
        }

        return dp[courses.length][courses[courses.length - 1][1]];
    }
}
