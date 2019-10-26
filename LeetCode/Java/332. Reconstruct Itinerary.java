// 332. Reconstruct Itinerary
// This is not topological sort!!! Topo-sort is for DAG, no cycle, but this could contain cycle.
// Eulerian Path is a path in graph that visits every edge exactly once.
// Hierholzer's algorithm / Fleury's Algorithm
// https://www.geeksforgeeks.org/eulerian-path-and-circuit/
// https://www.geeksforgeeks.org/hierholzers-algorithm-directed-graph/

// DFS all possibilities, TLE
class Solution {
    public String results = "";     // combine the list of string into one string using whitespaces
    public List<String> findItinerary(List<List<String>> tickets) {
        List<String> res = new ArrayList<>();
        if (tickets == null || tickets.size() == 0) {
            return res;
        }
        int[] select = new int[tickets.size()];
        dfs(tickets, "", 0, select);
        String[] strs = results.split(" ");
        for (String str : strs) {
            res.add(str);
        }
        return res;
    }
    
    public void dfs(List<List<String>> tickets, String cur, int level, int[] select) {
        // exit
        if (level == tickets.size()) {
            // we can check lexical order on each dfs level but not at the final step
            if (results == "" || cur.compareTo(results) < 0) {
                results = new String(cur);  // a new string contains the same content of cur
            }
        }
        
        for (int i = 0; i < select.length; i++) {
            if (select[i] == 0) {
                // unselect
                if (cur.length() < 3) {
                    // must start from JFK
                    if (tickets.get(i).get(0).equals("JFK")) {
                        cur += tickets.get(i).get(0);
                        cur += (" " + tickets.get(i).get(1));
                        select[i] = 1;
                        dfs(tickets, cur, level + 1, select);
                        cur = "";
                        select[i] = 0;
                    }
                } else {
                    if (tickets.get(i).get(0).equals(cur.substring(cur.length() - 3))) {
                        cur += (" " + tickets.get(i).get(1));
                        select[i] = 1;
                        dfs(tickets, cur, level + 1, select);
                        select[i] = 0;
                        cur = cur.substring(0, cur.length() - 4);
                    }   
                }
            }
        }
    }
}

// DFS
class Solution {
    public List<String> res = new ArrayList<>();
    public List<String> findItinerary(List<List<String>> tickets) {
        if (tickets == null || tickets.size() == 0) {
            return res;
        }
        
        // build graph
        Map<String, List<String>> graph = new HashMap<>();
        // Set<List<String>> ticketSet = new HashSet<>();   // cannot use set, may exist duplicate tickets
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            List<String> list = graph.containsKey(from) ? graph.get(from) : new ArrayList<>();
            list.add(to);
            graph.put(from, list);
        }
        
        // must start from JFK
        res.add("JFK");
        // topological sort
        dfs(graph, tickets);
        return res;
    }
    
    public boolean dfs(Map<String, List<String>> graph, List<List<String>> tickets) {
        // find an itinerary, exit
        if (tickets.size() == 0) {
            return true;
        }
        
        String from = res.get(res.size() - 1);
        List<String> tolist = graph.get(from);
        if (tolist != null) {   // may not exist next airports
            Collections.sort(tolist);   // sort lexically
            for (int i = 0; i < tolist.size(); i++) {
                String to = tolist.get(i);
                List<String> curTicket = new ArrayList<String>(Arrays.asList(from, to));
                // can fly
                if (tickets.contains(curTicket)) {
                    tickets.remove(curTicket);
                    res.add(to);
                    if (dfs(graph, tickets)) {
                        return true;
                    } else {
                        // backtracking
                        tickets.add(curTicket);
                        res.remove(res.size() - 1);
                    }
                }
            }
        }
        
        return false;
    }
}

// Hierholzer's algorithm. DFS
class Solution {
    public List<String> res = new ArrayList<>();
    public List<String> findItinerary(List<List<String>> tickets) {
        if (tickets == null || tickets.size() == 0) {
            return res;
        }
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            graph.putIfAbsent(from, new PriorityQueue<>());
            graph.get(from).add(to);
        }
        
        dfs("JFK", graph);
        return res;
    }
    
    public void dfs(String from, Map<String, PriorityQueue<String>> graph) {
        while (graph.containsKey(from) && graph.get(from).size() > 0) {
            dfs(graph.get(from).poll(), graph);
        }
        res.add(0, from);
    }
}

// dfs => iterative, use stack
class Solution {
    public List<String> findItinerary(List<List<String>> tickets) {
        List<String> res = new ArrayList<>();
        if (tickets == null || tickets.size() == 0) {
            return res;
        }
        Map<String, PriorityQueue<String>> graph = new HashMap<>();
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            graph.putIfAbsent(from, new PriorityQueue<>());
            graph.get(from).add(to);
        }
        
        Deque<String> stack = new ArrayDeque<>();
        stack.push("JFK");
        while (!stack.isEmpty()) {
            while (graph.containsKey(stack.peek()) && graph.get(stack.peek()).size() > 0) {
                stack.push(graph.get(stack.peek()).poll());
            }
            res.add(0, stack.pop());
        }
        return res;
    }
}