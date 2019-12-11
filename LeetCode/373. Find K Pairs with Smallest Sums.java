// 373. Find K Pairs with Smallest Sums
// 跟 378. Kth Smallest Element in a Sorted Matrix 的一种解法很相似
class Solution {
    // 时间复杂度为 O(klogk)
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> results = new ArrayList<>();
        if(nums1.length == 0 || nums2.length == 0 || k == 0) {
            return results;
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] + a[1] - b[0] - b[1]));
        // 把与 nums1[0] 组成对的所有可能情况放入最小堆中
        for(int i = 0; i < nums2.length; i++) {
            pq.offer(new int[]{nums1[0], nums2[i], 0});
        }
        while(k > 0 && !pq.isEmpty()) {
            int[] cur = pq.poll();
            results.add(new int[]{cur[0], cur[1]});
            k--;
            if(cur[2] == nums1.length - 1) {
                continue;
            }
            // 如果要放入下一对，只能保持 nums2 的元素不变，挑选下一个 nums1 的元素组成对
            pq.offer(new int[]{nums1[cur[2] + 1], cur[1], cur[2] + 1});
        }
        
        return results;
    }
}