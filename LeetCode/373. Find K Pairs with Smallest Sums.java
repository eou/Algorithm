// 373. Find K Pairs with Smallest Sums
// 跟 378. Kth Smallest Element in a Sorted Matrix 的一种解法很相似
class Solution {
    // 时间复杂度为 O(klogk)
    public List<int[]> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        List<int[]> res = new ArrayList<>();
        if(nums1.length == 0 || nums2.length == 0 || k == 0) {
            return res;
        }

        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> (a[0] + a[1] - b[0] - b[1]));
        // add (nums1[0], nums2[0]), (nums1[0], nums2[1]), (nums1[0], nums2[2]), ..., (nums1[0], nums2[n - 1])
        for(int i = 0; i < nums2.length; i++) {
            pq.offer(new int[]{nums1[0], nums2[i], 0});
        }
        while(k > 0 && !pq.isEmpty()) {
            int[] cur = pq.poll();
            res.add(new int[]{cur[0], cur[1]});
            k--;
            if(cur[2] == nums1.length - 1) {
                continue;
            }
            // for current element(nums1[k1], nums2[k2]), we add (nums1[k1 + 1], nums2[k2]) since (nums1[?], nums2[k2 + 1]) already exists
            pq.offer(new int[]{nums1[cur[2] + 1], cur[1], cur[2] + 1});
        }
        
        return res;
    }
}
