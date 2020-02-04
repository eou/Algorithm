// 554. Brick Wall
class Solution {
    public int leastBricks(List<List<Integer>> wall) {
        // length before slot => number of slot
        Map<Integer, Integer> slot = new HashMap<>();
        int totalWidth = 0;
        for (int width : wall.get(0)) {
            totalWidth += width;
        }
        for (List<Integer> w : wall) {
            int sumOfWidth = 0;
            for (int width : w) {
                sumOfWidth += width;
                slot.put(sumOfWidth, slot.getOrDefault(sumOfWidth, 0) + 1);
            }
        }
        
        int res = Integer.MAX_VALUE;
        for (int s : slot.keySet()) {
            if (s != totalWidth) {
                res = Math.min(wall.size() - slot.get(s), res);
            }
        }
        return res == Integer.MAX_VALUE ? wall.size() : res;
    }
}