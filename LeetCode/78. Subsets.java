// 78. Subsets
// input无重复，无序
// DFS, BFS都能解
class Solution {
    // DFS，组合类问题模板，一层层决策
    // [] -> 选:[1],[2],[3];不选:[];全递归
    // [1] -> 选[1,2],[1,3];不选[1];全递归
    // ...
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        Arrays.sort(nums);
        dfs(nums, 0, new ArrayList<Integer>(), results);
        return results;
    }

    // 1. 递归的定义
    private void dfs(int[] nums, int index, List<Integer> subset, List<List<Integer>> results) {
        // 3. 递归的出口
        // 每次DFS到头了再添加进results
        if (index == nums.length) {
            results.add(new ArrayList<Integer>(subset));
            return;
        }

        // 2. 递归的拆解
        // (如何进入下一层)

        // 选了 nums[index]
        subset.add(nums[index]);
        dfs(nums, index + 1, subset, results);

        // 不选 nums[index]
        subset.remove(subset.size() - 1);
        dfs(nums, index + 1, subset, results);
    }
}

class Solution {
    // DFS，回溯
    // [] -> 添加 [];递归[1],[2],[3]
    // [1] -> 添加 [1];递归[1,2],[1,3]
    // ...
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        if (nums == null) {
            return results;
        }

        if (nums.length == 0) {
            results.add(new ArrayList<Integer>());
            return results;
        }

        Arrays.sort(nums);
        helper(new ArrayList<Integer>(), nums, 0, results);
        return results;
    }

    // 1. 递归的定义
    private void helper(ArrayList<Integer> subset, int[] nums, int startIndex, List<List<Integer>> results) {
        // 2. 递归的拆解
        // deep copy
        // 每次进入递归就添加入results
        results.add(new ArrayList<Integer>(subset));

        for (int i = startIndex; i < nums.length; i++) {
            // [1] -> [1,2]
            subset.add(nums[i]);
            // 寻找所有以 [1,2] 开头的集合，并扔到 results
            helper(subset, nums, i + 1, results);
            // [1,2] -> [1] 回溯
            subset.remove(subset.size() - 1);
        }

        // 3. 递归的出口
        // return;
    }
}

class Solution {
    // BFS,类似第二种DFS
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> results = new LinkedList<>();

        if (nums == null) {
            return results;
        }

        Arrays.sort(nums);

        Queue<List<Integer>> queue = new LinkedList<>();
        queue.offer(new LinkedList<Integer>());

        while (!queue.isEmpty()) {
            List<Integer> subset = queue.poll();
            results.add(subset);

            for (int i = 0; i < nums.length; i++) {
                if (subset.size() == 0 || subset.get(subset.size() - 1) < nums[i]) {
                    List<Integer> nextSubset = new LinkedList<Integer>(subset);
                    nextSubset.add(nums[i]);
                    queue.offer(nextSubset);
                }
            }
        }

        return results;
    }
}

class Solution {
    // input数组长度转换为二进制，依照二进制特点一个个组合数组
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        // nums.length transformed to binary integer
        int n = nums.length;
        Arrays.sort(nums);

        // 1 << n is 2^n
        // each subset equals to an binary integer between 0 .. 2^n - 1
        // 0 -> 000 -> []
        // 1 -> 001 -> [a]
        // 2 -> 010 -> [b]
        // ..
        // 7 -> 111 -> [a,b,c]
        for (int i = 0; i < (1 << n); i++) {
            List<Integer> subset = new ArrayList<Integer>();
            for (int j = 0; j < n; j++) {
                // check whether the jth digit in i's binary representation is 1
                if ((i & (1 << j)) != 0) {
                    subset.add(nums[j]);
                }
            }
            result.add(subset);
        }
        return result;
    }
}