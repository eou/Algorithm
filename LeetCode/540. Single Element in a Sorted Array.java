// 540. Single Element in a Sorted Array
// Binary Search
class Solution {
    public int singleNonDuplicate(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;

            boolean rightHalfIsEven = (right - mid) % 2 == 0;
            if (nums[mid + 1] == nums[mid]) {
                if (rightHalfIsEven) {
                    left = mid + 2;
                } else {
                    right = mid - 1;
                }
            } else if (nums[mid - 1] == nums[mid]) {
                if (rightHalfIsEven) {
                    right = mid - 2;
                } else {
                    left = mid + 1;
                }
            } else {
                return nums[mid];
            }
        }

        return nums[left];
    }
}

class Solution {
    public int singleNonDuplicate(int[] nums) {
        int left = 0, right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (mid % 2 == 1) {
                mid--;
            }
            
            // mid is even number which means there are k elements in the left side of mid, k is even
            if (nums[mid] == nums[mid + 1]) {
                left = mid + 2;
            } else {
                right = mid;
            }
        }

        return nums[left];
    }
}

// Follow up: Given a sorted array of n elements containing elements in range from 1 to n-1 i.e. one element occurs twice, the task is to find the repeating element in an array.
class Main {
    private static int findRepeatingElement(int arr[]) {
        int left = 0, right = arr.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;

            if (arr[mid] != mid) {
                // wrong position, duplicate number exists in left side
                if (mid > 0 && arr[mid] == arr[mid - 1]) {
                    return arr[mid];
                }
                right = mid - 1;
            } else {
                left = mid + 1;
            }

        }

        return arr[left];
    }

    public static void main(String[] args) {
        int[] arr = new int[] { 0, 0, 1, 2, 3, 4, 5 };
        System.out.println(findRepeatingElement(arr));
    }
}
