// 1570. Dot Product of Two Sparse Vectors
// O(min(n, m))
class SparseVector {
    Map<Integer, Integer> map;
    SparseVector(int[] nums) {
        map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                map.put(i, nums[i]);
            }
        }
    }
    
	// Return the dotProduct of two sparse vectors
    public int dotProduct(SparseVector vec) {
        Map<Integer, Integer> tmpMap = new HashMap<>();
        if (vec.map.size() < map.size()) {
            tmpMap = vec.map;
        } else {
            tmpMap = map;
        }

        int res = 0;
        for (Integer i : tmpMap.keySet()) {
            res += (map.getOrDefault(i, 0) * vec.map.getOrDefault(i, 0));
        }
        
        return res;
    }
}