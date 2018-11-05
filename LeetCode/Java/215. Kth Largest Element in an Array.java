// 215. Kth Largest Element in an Array
// 不同思路的时间复杂度大约是：
// 排序：O(nlogn) → 优先队列：O(nlogk) → Blum-Floyd-Pratt-Rivest-Tarjan algorithm：O(n)
class Solution {
    // 采用quick sort思路，如排到刚好第nums.length-k个元素就返回，预期时间复杂度是O(n)
    public int findKthLargest(int[] nums, int k) {
        // 打乱数组防止最差情况发生（每次都排序到最大或者最小数字）
        shuffle(nums);
        k = nums.length - k;
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int j = partition(nums, left, right);
            if(j < k) {
                left = j + 1;
            } else if (j > k) {
                right = j - 1;
            } else {
                break;
            }
        }
        return nums[k];
    }

    private int partition(int[] nums, int left, int right) {
        int i = left;
        int j = right + 1;
        while(true) {
            while(i < right && nums[++i] < nums[left]);
            while(j > left && nums[left] < nums[--j]);
            if(i >= j) {
                break;
            }
            swap(nums, i, j);
        }
        swap(nums, left, j);
        return j;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    private void shuffle(int nums[]) {
        Random random = new Random();
        for(int i = 1; i < nums.length; i++) {
            int r = random.nextInt(i + 1);
            swap(nums, i, r);
        }
    }
}