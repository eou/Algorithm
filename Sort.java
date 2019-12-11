import java.util.*;
import java.lang.*;

class Sort {
    // O(n^2)
    public static void bubbleSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if (arr[i] > arr[j]) {
                    int tmp = arr[j];
                    arr[j] = arr[i];
                    arr[i] = tmp;
                }
            }
        }
    }

    // O(n^2), less swap operations than bubbleSort
    public static void selectSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 0; i < arr.length; i++) {
            int mini = i;
            for (int j = i; j < arr.length; j++) {
                if (arr[j] < arr[mini]) {
                    mini = j;
                }
            }
            int tmp = arr[i];
            arr[i] = arr[mini];
            arr[mini] = tmp;
        }
    }

    public static void insertionSort(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        for (int i = 1; i < arr.length; i++) {
            // insert arr[i] into arr[0...i-1]
            int tmp = arr[i];
            int j = i - 1;
            for (; j >= 0 && arr[j] > tmp; j--) {
                arr[j + 1] = arr[j];
            }
            arr[j + 1] = tmp;
        }
    }

    

    public static void main(String[] args) {
        int[] nums = new int[10];
        for (int i = 0; i < 10; i++) {
            int num = (int)(Math.random() * 10);
            nums[i] = num;
        }

        for (int num : nums) {
            System.out.print(num + " ");
        }
        System.out.println();
        // bubbleSort(nums);
        // selectSort(nums);
        insertionSort(nums);
        for (int num : nums) {
            System.out.print(num + " ");
        }
    }
}