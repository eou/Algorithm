// 292. Nim Game
// https://leetcode.com/problems/nim-game/discuss/73749/Theorem%3A-all-4s-shall-be-false

// proof:
// For 1, 2, 3 stones, first player win
// For 4, first player lose
// For 5, 6, 7 stones, first player can reduce stone to 4, then second player lose, first player win
// For 8, first player will reduce stones to 5, 6, 7, then second player win, first player lose
// For 9, 10, 11, ... first player win
// For 12, first player lose
// ... 
class Solution {
    public boolean canWinNim(int n) {
        return n % 4 != 0;
    }
}