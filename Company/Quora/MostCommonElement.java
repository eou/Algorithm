import java.util.*;

// Find the most common elements in a list
class Solution {
    public List<Integer> findMostCommonElement(int[] a) {
        Map<Integer, Integer> map = new HashMap<>();
        int maxVal = 0;
        for (int i : a) {
            map.put(i, map.getOrDefault(i, 0) + 1);
            maxVal = Math.max(maxVal, map.get(i));
        }

        List<Integer> res = new ArrayList<>();
        for (int num : map.keySet()) {
            if (map.get(num) == maxVal) {
                res.add(num);
            }
        }
        return res;
    }

    public static void main(String[] args) { 
        Solution s = new Solution();
        int[] A = new int[]{2, 2, 3, 3, 5};
        for (Integer i : s.findMostCommonElement(A)) {
            System.out.println(i);
        }
    }
}
