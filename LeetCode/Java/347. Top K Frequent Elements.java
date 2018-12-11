// 347. Top K Frequent Elements
class Solution {
    // 时间复杂度为 O(nlogk)
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        for(int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>(){
            public int compare(Integer a, Integer b) {
                return count.get(a) - count.get(b);
            }
        });
        // PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> count.get(a) - count.get(b));
        for(int n : count.keySet()) {
            pq.add(n);
            if(pq.size() > k) {
                pq.poll();
            }
        }

        List<Integer> results = new ArrayList<>();
        while(!pq.isEmpty()) {
            results.add(0, pq.poll());
        }
        return results;
    }
}

class Solution {
    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int n : nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }

        List<Integer>[] bucket = new List[nums.length + 1];
        for (int key : count.keySet()) {
            if (bucket[count.get(key)] == null) {
                bucket[count.get(key)] = new ArrayList<>();
            }
            bucket[count.get(key)].add(key);
        }

        List<Integer> results = new ArrayList<>();
        for (int i = bucket.length - 1; i >= 0 && results.size() < k; i--) {
            if (bucket[i] != null) {
                results.addAll(bucket[i]);
            }
        }
        return results.subList(0, k);
    }
}