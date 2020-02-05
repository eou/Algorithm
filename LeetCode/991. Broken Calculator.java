// 991. Broken Calculator
// https://www.acwing.com/solution/LeetCode/content/993/
// if X > Y, decrease
// if X < Y, Y or (Y + 1) / 2

// 反过来考虑这个问题，也就是如何从Y得到X，而操作变为：
// 如果Y是偶数，Y = Y / 2
// 如果Y是奇数，Y = Y + 1
// 当Y <= X时，无法进行Y = Y / 2，只需要进行Y = Y + 1；
// 当Y > X时，需要持续的减少Y，直到Y <= X。

// 如果Y是偶数，Y = Y / 2
// 如果Y是奇数，Y = Y + 1
// 因为(Y + 1 + 1) / 2 = (Y / 2) + 1，而等号左边需要3步而右边仅需要2步，所以当Y为偶数时仅需要考虑Y = Y / 2
class Solution {
    public int brokenCalc(int X, int Y) {
        int res = 0;
        while (Y > X) {
            if (Y % 2 == 0) {
                Y /= 2;
            } else {
                Y++;
            }
            res++;
        }
        return res + X - Y;
    }
}