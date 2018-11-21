// 528. Random Pick with Weight
class Solution {
    int[] weightSums;
    int totalWeight;

    public Solution(int[] w) {
        totalWeight = 0;
        random = new Random();
        weightSums = new int[w.length + 1];
        for(int i = 0; i < w.length; ++i) {
            totalWeight += w[i];
            weightSums[i + 1] =  totalWeight;
        }
    }
    
    public int pickIndex() {
        Random rand = new Random();
        // [0, totalWeight)
        int r = random.nextInt(totalWeight);
        
        int left = 0;
        int right = weightSums.length - 1;
        while(left + 1 < right) {
            int mid = left + (right - left) / 2;
            if(weightSums[mid] == r) {
                return mid;
            } else if(weightSums[mid] < r) {
                left = mid;
            } else {
                right = mid;
            }  
        }
        
        if(weightSums[left] <= r) {
            return left;
        } else {
            return right;
        }
    }
}

class Solution {
    int[] weightSums;
    int totalWeight;

    public Solution(int[] w) {
        totalWeight = 0;
        weightSums = new int[w.length + 1];
        for (int i = 0; i < w.length; ++i) {
            totalWeight += w[i];
            weightSums[i + 1] = totalWeight;
        }
    }

    public int pickIndex() {
        Random rand = new Random();
        // [0, totalWeight)
        int r = rand.nextInt(totalWeight);

        // 如用Java内置的二分查找，注意返回的负数的含义
        int index = Arrays.binarySearch(weightSums, 0, weightSums.length, r);
        if (index < 0) {
            return -(index + 2);
        } else {
            return index;
        }
    }
}