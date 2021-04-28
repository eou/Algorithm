// 690. Employee Importance
// Start from root id, go to the end of the path, add all importances from employees we have visited
// BFS / DFS
class Solution {
    public int getImportance(List<Employee> employees, int id) {
        int res = 0;

        // id => employee
        Map<Integer, Employee> map = new HashMap<>();
        for (Employee employee : employees) {
            map.put(employee.id, employee);
        }

        Deque<Employee> queue = new ArrayDeque<>();
        queue.offer(map.get(id));
        while (!queue.isEmpty()) {
            Employee cur = queue.poll();
            res += cur.importance;
            for (int sub : cur.subordinates) {
                queue.offer(map.get(sub));
            }
        }
        return res;
    }
}




class Solution {
    public int getImportance(List<Employee> employees, int id) {
        Map<Integer, Employee> map = new HashMap<>();
        for (Employee employee : employees) {
            map.put(employee.id, employee);
        }
        return dfs(map, id);
    }
    
    private int dfs(Map<Integer, Employee> map, int id) {
        Employee cur = map.get(id);
        int res = cur.importance;
        for (int sub : cur.subordinates) {
            res += dfs(map, sub);
        }
        return res;
    }
}