// 215. Kth Largest Element in an Array
// 不同思路的时间复杂度大约是：
// 直接排序：O(nlogn) → 优先队列：O(nlogk) → Blum-Floyd-Pratt-Rivest-Tarjan algorithm (quick select)：O(n) ~ O(n^2) → shuffle + quick select：O(n)
class Solution {
    // 优先队列最小堆，每次弹出容量范围之外的最小元素，最后最小堆中只剩下 k 个最大的元素，其中最小的就是第 k 个最大的元素
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>(k);
        for (int i : nums) {
            pq.offer(i);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        return pq.peek();
    }
}

// 采用 quick select 思路，如排到刚好第 nums.length - k 个元素就返回，预期时间复杂度是O(n)
// T(n) = O(n) + T(n/2) = O(n) + (O(n/2) + T(n/4)) = O(n) + O(n/2) + ... + O(1)
// + T(1) = O(n)
// 如果是 quick sort, 则是T(n) = O(n) + 2 * T(n/2)
class Solution {
    // quick select 版本
    public int findKthLargest(int[] nums, int k) {
        shuffle(nums);
        int kth = quickSelect(nums, 0, nums.length - 1, nums.length - k);
        return nums[kth];
    }

    // return the index of the kth smallest number
    private int quickSelect(int[] nums, int start, int end, int k) {
        if (start >= end) {
            return k;
        }

        int left = start;
        int right = end;
        int pivot = start + (end - start) / 2;

        while (left <= right) {
            while (left <= right && nums[left] < nums[pivot]) {
                left++;
            }
            while (left <= right && nums[right] > nums[pivot]) {
                right--;
            }
            if (left <= right) {
                swap(nums, left, right);
                left++;
                right--;
            }
        }

        if (k <= right) {
            return quickSelect(nums, start, right, k);
        }
        if (k >= left) {
            return quickSelect(nums, left, end, k);
        }
        return k;
    }

    void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    private void shuffle(int nums[]) {
        Random random = new Random();
        for (int i = 1; i < nums.length; i++) {
            int r = random.nextInt(i + 1);
            swap(nums, i, r);
        }
    }
}

class Solution {
    // quick select 非递归版本
    public int findKthLargest(int[] nums, int k) {
        shuffle(nums);
        int start = 0;
        int end = nums.length - 1;
        while (start <= end) {
            int pivot = nums[end];
            int index = start;
            for (int i = start; i <= end; i++) {
                // 降序排列
                if (nums[i] > pivot) {
                    swap(nums, i, index);
                    index++;
                }
            }
            swap(nums, end, index);

            if (index == k - 1) {
                return nums[index];
            } else if (index < k - 1) {
                start = index + 1;
            } else {
                end = index - 1;
            }
        }

        return -1;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    private void shuffle(int nums[]) {
        Random random = new Random();
        for (int i = 1; i < nums.length; i++) {
            int r = random.nextInt(i + 1);
            swap(nums, i, r);
        }
    }
}

class Solution {
    // quick select 版本
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

    private int partition(int[] nums, int start, int end) {
        int left = start;
        int right = end;
        int pivot = start + (end - start) / 2;

        while (left <= right) {
            while (left <= right && nums[left] < nums[pivot]) {
                left++;
            }
            while (left <= right && nums[right] > nums[pivot]) {
                right--;
            }
            if (left <= right) {
                swap(nums, left, right);
                left++;
                right--;
            }
        }
        
        return right;
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