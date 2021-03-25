// 444. Sequence Reconstruction
// topological sort
class Solution {
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        if (org == null || org.length == 0) {
            return true;
        }

        if (seqs == null || seqs.size() == 0 || seqs.get(0).size() == 0) {
            return false;
        }

        // 1. build graph
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (List<Integer> seq : seqs) {
            for (int i = 0; i < seq.size(); i++) {
                List<Integer> neighbor = graph.getOrDefault(seq.get(i), new ArrayList<>());
                if (i + 1 < seq.size()) {
                    neighbor.add(seq.get(i + 1));
                }
                graph.put(seq.get(i), neighbor);
            }
        }

        // 2. calculate indegree
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer i : graph.keySet()) {
            for (Integer j : graph.get(i)) {
                map.put(j, map.getOrDefault(j, 0) + 1);
            }
        }

        // BFS
        Deque<Integer> queue = new ArrayDeque<>();
        for (Integer i : graph.keySet()) {
            if (map.getOrDefault(i, 0) == 0) {
                queue.offer(i);
            }
        }

        List<Integer> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            if (queue.size() != 1) {
                return false;
            }

            Integer i = queue.poll();
            res.add(i);
            for (Integer j : graph.get(i)) {
                map.put(j, map.get(j) - 1);
                if (map.get(j) == 0) {
                    queue.offer(j);
                }
            }
        }

        if (res.size() != org.length || res.size() != graph.size()) {
            return false;
        }

        for (int i = 0; i < res.size(); i++) {
            if (res.get(i) != org[i]) {
                return false;
            }
        }

        return true;
    }
}

// Every 2 consecutive elements in org should be consecutive elements in some sequence from seqs
class Solution {
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        if (org == null || org.length == 0) {
            return true;
        }

        if (seqs == null || seqs.size() == 0 || seqs.get(0).size() == 0) {
            return false;
        }

        int[] idx = new int[org.length + 1];
        boolean[] pairs = new boolean[org.length];

        for (int i = 0; i < org.length; i++) {
            idx[org[i]] = i;
        }

        for (List<Integer> seq : seqs) {
            for (int i = 0; i < seq.size(); i++) {
                // org sequence is a permutation of the integers from 1 to n
                if (seq.get(i) > org.length || seq.get(i) < 0) {
                    return false;
                }

                if (i > 0 && idx[seq.get(i - 1)] >= idx[seq.get(i)]) {
                    return false;
                }

                if (i > 0 && idx[seq.get(i - 1)] + 1 == idx[seq.get(i)]) {
                    pairs[idx[seq.get(i - 1)]] = true;
                }
            }
        }

        for (int i = 0; i < org.length - 1; i++) {
            if (!pairs[i]) {
                return false;
            }
        }

        return true;
    }
}