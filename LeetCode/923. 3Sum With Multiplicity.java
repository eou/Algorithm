// 923. 3Sum With Multiplicity
class Solution {
    // 思路与3Sum一样，但是需要提前去重，否则TLE
    // [0,2,0] => [0,0,2] 需要将两个 0 作为两个数字判断
    public int threeSumMulti(int[] A, int target) {
        int M = 1_000_000_007;
        
        // 去重
        long[] count = new long[101];
        int uniq = 0;
        for (int x: A) {
            count[x]++;
            if (count[x] == 1) {
                uniq++;
            }
        }
        
        int[] keys = new int[uniq];
        int t = 0;
        for (int i = 0; i <= 100; ++i) {
            if (count[i] > 0){
                keys[t] = i;
                t++;
            }
        }
        
        long result = 0;
        for (int i = 0; i < keys.length; ++i) {
            int x = keys[i];
            int j = i, k = keys.length - 1;
            while (j <= k) {
                int y = keys[j], z = keys[k];
                if (y + z < target - x) {
                    j++;
                } else if (y + z > target - x) {
                    k--;
                } else {
                    if (i < j && j < k) {
                        result += count[x] * count[y] * count[z];
                    } else if (i == j && j < k) {
                        result += count[x] * (count[x] - 1) / 2 * count[z];
                    } else if (i < j && j == k) {
                        result += count[x] * count[y] * (count[y] - 1) / 2;
                    } else {
                        result += count[x] * (count[x] - 1) * (count[x] - 2) / 6;
                    }

                    result %= M;
                    j++;
                    k--;
                }
            }
        }

        return (int) (result);
    }
}