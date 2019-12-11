// 399. Evaluate Division
class Solution {
    public double[] calcEquation(String[][] equations, double[] values, String[][] queries) {
        // a / b = v: a => b => v
        Map<String, Map<String, Double>> map = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            map.putIfAbsent(equations[i][0], new HashMap<>());
            map.putIfAbsent(equations[i][1], new HashMap<>());
            map.get(equations[i][0]).put(equations[i][1], values[i]);
            map.get(equations[i][1]).put(equations[i][0], 1 / values[i]);
        }

        double[] results = new double[queries.length];
        for (int i = 0; i < queries.length; i++) {
            results[i] = helper(queries[i][0], queries[i][1], 1, map, new HashSet<>());
        }

        return results;
    }

    double helper(String s, String t, double result, Map<String, Map<String, Double>> map, Set<String> visited) {
        // if s does not exist in the map or we have visited s, we should return, otherwise  a => b => a => b => a ...
        // if (!map.containsKey(s) || !visited.add(s)) {
        //      return -1;
        // }

        if (!map.containsKey(s) || visited.contains(s)) {
            return -1;
        }

        if (s.equals(t)) {
            return result;
        }

        visited.add(s);
        for (String c : map.get(s).keySet()) {
            double tmp = helper(c, t, result * map.get(s).get(c), map, visited);
            if (tmp != -1) {
                return tmp;
            }
        }

        return -1;
    }
}