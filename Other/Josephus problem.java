import java.util.*;
// first selected people must be k mod n
// then for 2nd round, start from people (k + 1) mod n, index will update to 1, k + 2 => 2, ...
// if we know the selected people in 2nd round is x, then his index in 2nd round is (x + k) mod n in 1st round
// thus f[n] = (f[n - 1] + k) mod n
class Solution {
    public static int josephus(int n, int k) {
        if (n == 1) {
            return 1;
        } else {
            /*
             * The position returned by josephus(n - 1, k) is adjusted because the recursive
             * call josephus(n - 1, k) considers the original position k % n + 1 as position
             * 1
             */
            return (josephus(n - 1, k) + k - 1) % n + 1;
        }

    }
    
    // f[1] = 0;
    // f = (f + k) mod i; (i>1）
    public static int josephus2(int n, int k) {
        int i = 2;
        int result = 0;
        while (i <= n) {
            result = (result + k) % i;
            ++i;
        }
        return result + 1;
    }
}