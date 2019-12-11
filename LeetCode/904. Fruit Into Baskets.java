// 904. Fruit Into Baskets
// 与 159. Longest Substring with At Most Two Distinct Characters 完全一样
class Solution {
    // sliding window, longest substring contains at most 2 distinct numbers
    public int totalFruit(int[] tree) {
        if (tree.length == 0) {
            return 0;
        }

        int left = 0;
        int right = 0;
        int len = 1;
        Map<Integer, Integer> map = new HashMap<>();
        while (right < tree.length) {
            map.put(tree[right], map.getOrDefault(tree[right], 0) + 1);
            while (map.size() > 2) {
                map.put(tree[left], map.get(tree[left]) - 1);
                if (map.get(tree[left]) == 0) {
                    map.remove(tree[left]);
                }
                left++;
            }
            len = Math.max(len, right - left + 1);
            right++;
        }

        return len;
    }
}

class Solution {
    // 另一个版本，只关心当前最近的两个种类
    public int totalFruit(int[] tree) {
        // track last two fruits seen
        int lastFruit = -1;
        int secondLastFruit = -1;
        int lastFruitCount = 0;
        int currMax = 0;
        int max = 0;

        for (int fruit : tree) {
            if (fruit == lastFruit || fruit == secondLastFruit) {
                currMax++; // 正常情况
            } else {
                currMax = lastFruitCount + 1; // 出现了新的种类，只能在前一种的基础上加 1 个
            }

            // 更新此时最新的种类的个数
            if (fruit == lastFruit) {
                lastFruitCount++;
            } else {
                lastFruitCount = 1;
                secondLastFruit = lastFruit;
                lastFruit = fruit;
            }

            max = Math.max(max, currMax);
        }

        return max;
    }
}