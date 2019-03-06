// 461. Hamming Distance
// 重点在于 x ^ y, Xor 的使用
class Solution {
    public int hammingDistance(int x, int y) {
        return Integer.bitCount(x ^ y);
    }
}

class Solution {
    public int hammingDistance(int x, int y) {
        int dis = 0;
        while (x != 0 || y != 0) {
            int a = x % 2, b = y % 2;
            if (a != b) {
                dis++;
            }
            x /= 2;
            y /= 2;
        }
        return dis;
    }
}

class Solution {
    public int hammingDistance(int x, int y) {
        int distance = 0;
        
        while (x > 0 && y > 0) {
            if (x % 2 != y % 2) {
                ++distance;
            }
            x /= 2;
            y /= 2;
        }
        
        while (x > 0) {
            distance += (x % 2 == 0) ? 0 : 1;
            x /= 2;
        }
        
        while (y > 0) {
            distance += (y % 2 == 0) ? 0 : 1;
            y /= 2;
        }
        
        return distance;
    }
}