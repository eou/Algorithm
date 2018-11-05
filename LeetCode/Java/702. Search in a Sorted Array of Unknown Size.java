// 702. Search in a Sorted Array of Unknown Size
class Solution {
    public int search(ArrayReader reader, int target) {
        int start = 0, end = 1;

        // 关键，类似exponential backoff
        while (reader.get(end) <= target) {
            end = end * 2;
        }

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int num = reader.get(mid);
            if (num == target) {
                return mid;
            } else if (num < target) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return -1;
    }
}