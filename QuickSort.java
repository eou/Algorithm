// Time complexity: O(nlogn) ~ O(n^2)
// Worst: T(n) = T(n - 1) + T(0) + O(n) = T(n - 1) + O(n) => O(n^2)
// Best: T(n) = 2T(n / 2) + O(n) => O(nlogn)
// Space complexity: O(logn), recursion function call
class QuickSort {
    public int partition(int arr[], int low, int high) { 
        int pivot = arr[high];  
        int i = (low - 1); // index of smaller element because i++ later
        for (int j = low; j < high; j++) { 
            // If current element is smaller than the pivot 
            if (arr[j] < pivot) { 
                i++; 
  
                // swap arr[i] and arr[j] 
                int temp = arr[i]; 
                arr[i] = arr[j]; 
                arr[j] = temp; 
            } 
        } 
  
        // swap arr[i+1] and arr[high] (or pivot) 
        int temp = arr[i + 1]; 
        arr[i + 1] = arr[high]; 
        arr[high] = temp; 
  
        return i + 1; 
    }
  
    public void sort(int arr[], int low, int high) { 
        if (low < high) {
            int pi = partition(arr, low, high); 
  
            sort(arr, low, pi-1); 
            sort(arr, pi+1, high); 
        } 
    } 
  
    public static void printArray(int arr[]) { 
        int n = arr.length; 
        for (int i = 0; i < n; ++i) 
            System.out.print(arr[i]+" "); 
        System.out.println(); 
    } 
  
    public static void main(String args[]) { 
        int arr[] = {10, 7, 8, 9, 1, 5};
  
        QuickSort qs = new QuickSort(); 
        qs.sort(arr, 0, arr.length - 1); 
  
        printArray(arr); 
    } 
} 