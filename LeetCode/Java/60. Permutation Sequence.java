// 60. Permutation Sequence
// 此题跟 Permutation 前几题思路略有不同，并不是DFS，差不多也是找数学规律
class Solution {
    public String getPermutation(int n, int k) {
        String s = "";
        boolean[] visited = new boolean[n];
        for (int i = 1; i <= n; ++i) {
            int index = k / factorial(n - i);
            // 这里处理 index 是为了整除时候的临界值
            // 其实在开头 k--; 就可以不这样判断
            if (k != 0 && k % factorial(n - i) == 0) {
                index--;
            }
            k = k - index * factorial(n - i);

            for (int j = 0; j < visited.length; ++j) {
                if (index == 0 && !visited[j]) {
                    s += (j + 1) + "";
                    visited[j] = true;
                    break;
                }
                if (!visited[j]) {
                    index--;
                }
            }
        }
        return s;
    }

    private int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; ++i) {
            result *= i;
        }
        return result;
    }
}

class Solution {
    // 简洁一点的版本
    public String getPermutation(int n, int k) {
        StringBuilder strBuilder = new StringBuilder();

        // 用数组简化之前的取数字操作
        List<Integer> nums = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            nums.add(i);
        }

        // k-- 的意味很明显，因为第 k 位 要靠 k - 1 与 n 的计算来确定
        k--;
        for (int i = 1; i <= n; ++i) {
            int index = k / factorial(n - i);
            k = k - index * factorial(n - i);
            strBuilder.append(String.valueOf(nums.get(index)));
            nums.remove(index);
        }
        return strBuilder.toString();
    }

    private int factorial(int n) {
        int result = 1;
        for (int i = 1; i <= n; ++i) {
            result *= i;
        }
        return result;
    }
}