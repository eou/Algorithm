import java.util.*;

// 输⼊a，b两个array， ⼀个query array
// query ⼀种是[target]查从a中取⼀个数, b中取⼀个数，求加起来等于target的情况有多少种
// 第⼆种 query 是 [index, num], 把 b 中在 index 位置的数字改成 num，这种 query 不需要输出
// 最后输出所有第⼀种query的result
class Solution {
    public int[] TwoSumQuery(int[] a, int[] b, int[][] querys) {
        List<Integer> res = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i : a) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }

        for (int[] query : querys) {
            if (query.length == 2) {
                int temp = findSum(map, b, query[1]);
                res.add(temp);
            } else if (query.length == 3) {
                update(a, b, query[1], query[2]);
            }

        }
        int[] ansArray = new int[res.size()];
        for (int i = 0; i < res.size(); i++) {
            ansArray[i] = res.get(i);
        }
        return ansArray;
    }

    public static int findSum(Map<Integer, Integer> map, int[] b, int target) {
        int res = 0;
        for (int i = 0; i < b.length; i++) {
            if (map.containsKey(target - b[i])) {
                res += map.get(target - b[i]);
            }
        }
        return res;
    }

    public static void update(int[] a, int[] b, int loc, int num) {
        b[loc] = num;
    }

    public static void main(String[] args) { 
        Solution s = new Solution();
    }
}
