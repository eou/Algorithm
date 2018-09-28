// 868. Binary Gap 
class Solution {
    // 注意复杂度是O(logN)
    public int binaryGap(int N) {
        List<Integer> list = new ArrayList<>();
        int pos = 0;
        while (N != 0) {
            if (N % 2 == 1) {
                list.add(pos);
            }
            N /= 2;
            pos++;
        }
        int maxDis = 0;
        for (int i = 0, j = 1; j < list.size(); ++i, ++j) {
            maxDis = maxDis < (list.get(j) - list.get(i)) ? (list.get(j) - list.get(i)) : maxDis;
        }
        return maxDis;
    }
}

class Solution {
    // 只需保存相邻两个1的位置即可，简化了空间复杂度O(1)
    public int binaryGap(int N) {
        int cur = -1, pre = -1, pos = 0, maxDis = 0;
        while (N != 0) {
            if (N % 2 == 1) {
                if (pre == -1) {
                    pre = pos;
                } else {
                    cur = pos;
                }
                if (pre != -1 && cur != -1) {
                    maxDis = maxDis < (cur - pre) ? (cur - pre) : maxDis;
                    pre = cur;
                }

            }
            N /= 2;
            pos++;
        }
        return maxDis;
    }
}