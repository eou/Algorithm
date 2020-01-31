class Solution {
    public int maxSatisfied(int[] customers, int[] grumpy, int X) {
        // sliding window
        int satisfied = 0;
        for (int i = 0; i < customers.length; i++) {
            if (grumpy[i] == 0) {
                satisfied += customers[i];
            }
        }
        
        int res = satisfied;
        for (int i = 0; i < X; i++) {
            if (grumpy[i] == 1) {
                satisfied += customers[i];
                res = Math.max(res, satisfied);
            }
        }
        
        for (int i = X; i < customers.length; i++) {
            if (grumpy[i - X] == 1) {
                satisfied -= customers[i - X];
            }
            if (grumpy[i] == 1) {
                satisfied += customers[i];
            }
            res = Math.max(res, satisfied);
        }
        
        return res;
    }
}