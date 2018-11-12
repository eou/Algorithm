// Wood Cut
// https://www.lintcode.com/problem/wood-cut/description
class Solution {
    // 二分查找
    public int woodCut(int[] L, int k) {
        if(L.length == 0) {
            return 0;
        }
        
        int max = L[0];
        for(int i : L) {
            max = Math.max(i, max);
        }
        
        int left = 1;
        int right = max;
        while(left + 1 < right) {
            int mid = left + (right - left) / 2;
            if(cut(L, mid) >= k) {
                left = mid;
            } else {
                right = mid;
            }
        }
        
        if(cut(L, right) >= k) {
            return right;
        } 
        if(cut(L, left) >= k) {
            return left;
        }
        return 0;

        // 二分查找也能换一个终止条件，维护一个变量防止错过最大值，
        // int result = 0;
        // while (left <= right) {
        //     int mid = left + (right - left) / 2;
        //     if (cut(L, mid) >= k) {
        //         result = Math.max(result, mid);
        //         left = mid + 1;
        //     } else {
        //         right = mid - 1;
        //     }
        // }

        // return result;
    }
    
    private int cut(int[] L, int len) {
        int pieces = 0;
        for(int i : L) {
            pieces += i / len;
        }
        return pieces;
    }
}