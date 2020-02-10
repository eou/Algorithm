// 625. Minimum Factorization
class Solution {
    public int smallestFactorization(int a) {
        if (a < 10) {
            return a;
        }
        
        List<Integer> list = new ArrayList<>();
        while (a > 1) {
            int pre = a;
            for (int i = 9; i > 1; i--) {
                if (a % i == 0) {
                    list.add(i);
                    a /= i;
                    break;
                }
            }
            if (pre == a) {
                list = new ArrayList<>();
                break;
            }
        }
        
        long res = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            res = res * 10 + (list.get(i));
            if (res > Integer.MAX_VALUE) {
                return 0;   
            }
        }
        return (int)res;
    }
}