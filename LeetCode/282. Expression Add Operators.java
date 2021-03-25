// 282. Expression Add Operators
class Solution {
    // 时间复杂度为 O(4^n)
    public List<String> addOperators(String num, int target) {
        List<String> results = new ArrayList<>();
        if (num.length() < 2) {
            return results;
        }

        StringBuilder strBuilder = new StringBuilder();
        helper(num.toCharArray(), 0, strBuilder, results, target, 0, 0);
        return results;
    }

    // 难点在于把上一层递归中改变的值在 DFS 中传进去，以便后面乘法使用
    // 用 char[] num 也是因为用 num[i] 比 num.charAt(i) 效率高
    private void helper(char[] num, int start, StringBuilder current, List<String> results, int target, long result, long lastNum) {
        if (start == num.length) {
            if (result == target) {
                results.add(current.toString());
            }
            return;
        }

        long n = 0;
        for (int i = start; i < num.length; i++) {
            if (num[start] == '0' && i > start) {
                break;
            }

            n = n * 10 + (num[i] - '0');
            int len = current.length();
            // 用 String 就不用像 StringBuilder 每次都要 setLength() 恢复原长，但是效率低
            if (start == 0) {
                helper(num, i + 1, current.append(n), results, target, n, n);
                current.setLength(len);
            } else {
                helper(num, i + 1, current.append('+').append(n), results, target, result + n, n);
                current.setLength(len);
                helper(num, i + 1, current.append('-').append(n), results, target, result - n, -n);
                current.setLength(len);
                helper(num, i + 1, current.append('*').append(n), results, target, result - lastNum + lastNum * n, lastNum * n);
                current.setLength(len);
            }
        }
    }
}