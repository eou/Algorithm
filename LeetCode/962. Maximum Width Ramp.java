// 962. Maximum Width Ramp
class Solution {
    // DFS with memoization 会 TLE
    public int maxWidthRamp(int[] A) {
        Integer[] memo = new Integer[A.length];
        return helper(A, 0, A.length - 1, memo);
    }

    public int helper(int[] A, int start, int end, Integer[] memo) {
        if (end - start == 0) {
            return 0;
        }
        if (A[start] <= A[end]) {
            return end - start;
        }

        int ramp1 = 0;
        int ramp2 = 0;
        if (memo[start + 1] != null) {
            ramp1 = memo[start + 1];
        } else {
            ramp1 = helper(A, start + 1, end, memo);
        }

        ramp2 = helper(A, start, end - 1, memo);

        int result = Math.max(ramp1, ramp2);
        memo[start] = result;
        return result;
    }
}

class Solution {
    // 直接排序即可，时间复杂度为 O(nlogn)
    public int maxWidthRamp(int[] A) {
        Integer[] index = new Integer[A.length];
        for(int i = 0; i < A.length; i++) {
            index[i] = i;
        }

        Arrays.sort(index, new Comparator<Integer>(){
            @Override
            public int compare(Integer i, Integer j) {
                return A[i] - A[j];
            }
        });
        // 也能用一行代替
        // Arrays.sort(index, (i, j) -> ((Integer) A[i]).compareTo(A[j]));

        int result = 0;
        int min = A.length;
        for(int i : index) {
            result = Math.max(result, i - min);
            min = Math.min(min, i);
        }

        return result;
    }
}

class Solution {
    // 维护一个递减的栈，每次使用二分查找，找出其中比当前元素小的最小的元素，时间复杂度为 O(nlogn)
    // 如 A = [6,0,8,2,1,5]:
    // result = 0, [0] => result = 0, [0, 1] => result = 2, [0, 1] => result = 2, [0, 1] => result = 2, [0, 1] => result = 4, [0, 1]
    public int maxWidthRamp(int[] A) {
        List<Integer> s = new ArrayList<>();
        int result = 0, n = A.length;
        for (int i = 0; i < n; ++i) {
            if (s.size() == 0 || A[i] < A[s.get(s.size() - 1)]) {
                s.add(i);
            } else {
                int left = 0, right = s.size() - 1, mid = 0;
                while (left < right) {
                    mid = left + (right - left) / 2;
                    mid = (left + right) / 2;
                    if (A[s.get(mid)] > A[i]) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                result = Math.max(result, i - s.get(left));
            }
        }
        return result;
    }
}

class Solution {
    // 上面方法的改进版本，时间复杂度为 O(n)
    public int maxWidthRamp(int[] A) {
        Deque<Integer> stack = new ArrayDeque<>();
        int result = 0, n = A.length;
        for (int i = 0; i < n; i++) {
            if (stack.isEmpty() || A[stack.peek()] > A[i]) {
                stack.addFirst(i);
            }
        }

        for (int i = n - 1; i > result; i--) {
            while (!stack.isEmpty() && A[stack.peek()] <= A[i]) {
                result = Math.max(result, i - stack.poll());
            }
        }
        return result;
    }
}

class Solution {
    // 利用 0 <= A[i] <= 50000 这个条件的一个巧妙解法，但是空间消耗略多
    // 如 A = [0,8,2,7,5]:
    // index: [0, 0, 0, 0, 0, 0, 0, 0, 0, ...]
    // index: [0, 0, 2, 0, 0, 4, 0, 3, 1, ...]
    // index: [4, 4, 4, 4, 4, 4, 3, 3, 1, ...]
    public int maxWidthRamp(int[] A) {
        int[] index = new int[50001];
        for(int i = 0; i < A.length; i++) {
            index[A[i]] = i;
        }

        for(int i = 49999; i >= 0; i--) {
            if(index[i] < index[i + 1]) {
                index[i] = index[i + 1];
            }
        }

        int result = 0;
        for(int i = 0; i < A.length; i++) {
            result = Math.max(result, index[A[i]] - i);
        }
        return result;
    }
}