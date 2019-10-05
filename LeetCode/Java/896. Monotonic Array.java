// 896. Monotonic Array
class Solution {
    // 检测所有不等于 0 的差值即可
    public boolean isMonotonic(int[] A) {
        if (A.length == 1) {
            return true;
        }

        boolean increasing = false;
        boolean firstCmp = false;
        for (int i = 0; i < A.length - 1; i++) {
            if (A[i + 1] - A[i] != 0) {
                if (firstCmp != true) {
                    increasing = A[i + 1] - A[i] > 0;
                    firstCmp = true;
                } else {
                    if ((A[i + 1] - A[i] > 0) != increasing) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
}

class Solution {
    // 更简洁的 one pass 版本
    public boolean isMonotonic(int[] A) {
        boolean increasing = true;
        boolean decreasing = true;
        for (int i = 0; i < A.length - 1; ++i) {
            if (A[i] > A[i + 1]) {
                increasing = false;
            }
            if (A[i] < A[i + 1]) {
                decreasing = false;
            }
        }

        return increasing || decreasing;
    }
}

class Solution {
    // two pass 版本
    public boolean isMonotonic(int[] A) {
        return increasing(A) || decreasing(A);
    }

    public boolean increasing(int[] A) {
        for (int i = 0; i < A.length - 1; ++i)
            if (A[i] > A[i + 1])
                return false;
        return true;
    }

    public boolean decreasing(int[] A) {
        for (int i = 0; i < A.length - 1; ++i)
            if (A[i] < A[i + 1])
                return false;
        return true;
    }
}

class Solution {
    public boolean isMonotonic(int[] A) {
        if (A == null || A.length <= 1) {
            return true;
        }
        
        // A.length >= 2
        // use diff array
        int[] diff = new int[A.length];
        for (int i = 0; i < A.length - 1; i++) {
            diff[i] = A[i + 1] - A[i];
        }
        
        boolean positive = false;
        boolean negative = false;
        for (int i = 0; i < diff.length; i++) {
            if (diff[i] > 0) {
                positive = true;
            }
            if (diff[i] < 0) {
                negative = true;
            }
        }
        
        if (positive == true && negative == true) {
            return false;
        }
        
        return true;
    }
}
