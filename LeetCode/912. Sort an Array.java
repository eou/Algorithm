// 912. Sort an Array
class Solution {
    public int[] sortArray(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return nums;
        }
        
        // bubbleSort(nums);
        // selectSort(nums);
        // insertionSort(nums);
        // quickSort(nums, 0, nums.length - 1);
        mergeSort(nums, nums.length);
        return nums;
    }
    
    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
    
    // O(n^2)
    private void bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i; j < nums.length; j++) {
                if (nums[i] > nums[j]) {
                    swap(nums, i, j);
                }
            }
        }
    }
    
    // O(n^2)
    private void selectSort(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int mini = i;
            for (int j = i; j < nums.length; j++) {
                if (nums[j] < nums[mini]) {
                    mini = j;
                }
            }
            
            swap(nums, i, mini);
        }
    }
    
    // O(n^2)
    private void insertionSort(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            int tmp = nums[i];
            int j = i - 1;
            for(; j >= 0 && nums[j] > tmp; j--) {
                nums[j + 1] = nums[j];
            }
            nums[j + 1] = tmp;
        }
    }
    
    // O(nlogn), space O(logn)
    private void quickSort(int[] nums, int low, int high) {
        if (low < high) {
            int pivot = partition(nums, low, high);
            quickSort(nums, low, pivot - 1);
            quickSort(nums, pivot + 1, high);
        }
    }
    
    private int partition(int[] nums, int low, int high) {
        int pivot = nums[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (nums[j] < pivot) {
                i++;
                swap(nums, j, i);
            }
        }
        
        swap(nums, high, i + 1);
        return i + 1;
    }
    
    private void merge(int[] left, int[] right, int[] arr) {
        int i = 0, l = 0, r = 0;

        while (l < left.length && r < right.length) {
            if(left[l] < right[r]) {
                arr[i] = left[l];
                l++;
            } else{
                arr[i] = right[r];
                r++;
            }
            i++;
        }

        while (l < left.length) {
            arr[i++] = left[l++];
        }

        while (r < right.length) {
            arr[i++] = right[r++];
        }
    }
    
    // O(nlogn), space O(n)
    private void mergeSort(int[] nums, int len) {
        if (len < 2) {
            return;
        }

        int mid = len / 2;
        int[] left = new int[mid];
        int[] right = new int[len - mid];

        // Dividing array into two and copying into two separate arrays
        int k = 0;
        for (int i = 0; i < len; ++i) {
            if (i < mid) {
                left[i] = nums[i];
            } else {
                right[k] = nums[i];
                k = k + 1;
            }
        }

        // Recursively calling the function to divide the subarrays further
        mergeSort(left, mid);
        mergeSort(right, len - mid);

        // Calling the merge method on each subdivision
        merge(left, right, nums);
    }
}