// 658. Find K Closest Elements
class Solution {
    // 时间复杂度 O(n)
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        List<Integer> results = new ArrayList<>();
        int start = binarySearch(arr, x);
        int left = start, right = start + 1;
        int count = 0;
        
        while((left >= 0 || right < arr.length) && count < k) {
            if(left >= 0 && right < arr.length) {
                if(Math.abs(arr[left] - x) > Math.abs(arr[right] - x)) {
                    results.add(arr[right]);
                    right++;
                } else {
                    results.add(0, arr[left]);
                    left--;
                }
            } else if(left < 0) {
                results.add(arr[right]);
                right++;
            } else {
                results.add(0, arr[left]);
                left--;
            }
            count++;
        }
        
        return results;
    }
    
    private int binarySearch(int[] arr, int x) {
        int left = 0, right = arr.length - 1;
        while(left < right - 1) {
            int mid = left + (right - left) / 2;
            if(arr[mid] == x) {
                return mid;
            } else if(arr[mid] < x) {
                left = mid;
            } else {
                right = mid;
            }
        }
        
        if(Math.abs(arr[left] - x) < Math.abs(arr[right] - x)) {
            return left;
        } else {
            return right;
        }
    }
}

class Solution {
    // 以上版本可以精简为用二分法直接找子数组左端点
    public List<Integer> findClosestElements(int[] arr, int k, int x) {
        int left = 0, right = arr.length - k;
        while (left < right) {
            int mid = (left + right) / 2;
            if (x - arr[mid] > arr[mid + k] - x)
                left = mid + 1;
            else
                right = mid;
        }

        List<Integer> results = new ArrayList<>();
        for (int i = left; i < left + k; i++) {
            results.add(arr[i]);
        }
        return results;
    }
}

class Solution {
    // 可以直接根据与x的差值来排序，取前k个即可，时间复杂度 O(nlogn)
    public List<Integer> findClosestElements(List<Integer> arr, int k, int x) {
        Collections.sort(arr, (a,b) -> a == b ? a - b : Math.abs(a-x) - Math.abs(b-x));
        arr = arr.subList(0, k);
        Collections.sort(arr);
        return arr;
    }
}