// 1007. Minimum Domino Rotations For Equal Row
// observation: A[0] or B[0] must in the final rorations
// and if A[0] works, no need to check B[0]. 
// Because if both A[0] and B[0] exist in all dominoes, the result will be the same since all the dominoes will the same
class Solution {
    public int minDominoRotations(int[] A, int[] B) {
        int n = A.length;
        for (int i = 0, a = 0, b = 0; i < n && (A[i] == A[0] || B[i] == A[0]); ++i) {
            if (A[i] != A[0]) a++;
            if (B[i] != A[0]) b++;
            if (i == n - 1) return Math.min(a, b);
        }
        for (int i = 0, a = 0, b = 0; i < n && (A[i] == B[0] || B[i] == B[0]); ++i) {
            if (A[i] != B[0]) a++;
            if (B[i] != B[0]) b++;
            if (i == n - 1) return Math.min(a, b);
        }
        return -1;
    }
}


class Solution {
    public int minDominoRotations(int[] A, int[] B) {
        // number => exists in how many tiles
        Map<Integer, Set<Integer>> map = new HashMap<>();
        for (int i = 0; i < A.length; i++) {
            Set<Integer> set = map.containsKey(A[i]) ? map.get(A[i]) : new HashSet<>();
            set.add(i); // might duplicate, use set to deduplicate
            map.put(A[i], set);
        }
        for (int i = 0; i < B.length; i++) {
            Set<Integer> set = map.containsKey(B[i]) ? map.get(B[i]) : new HashSet<>();
            set.add(i); // might duplicate, use set to deduplicate
            map.put(B[i], set);
        }
        boolean possible = false;
        int rotations = A.length;
        for (Integer num : map.keySet()) {
            if (map.get(num).size() == A.length) {
                int curRotation = 0;
                int total = A.length;
                for (int i = 0; i < A.length; i++) {
                    if (A[i] == num) {
                        if (B[i] != num) {
                            curRotation++;
                        } else {
                            // A and B are the same, do not need to ratate
                            total--;
                        }
                    }
                }
                rotations = Math.min(rotations, Math.min(curRotation, total - curRotation));
                possible = true;
            }
        }
        
        if (!possible) {
            return -1;
        }
        
        return rotations;
    }
}