// 508. Most Frequent Subtree Sum
class Solution {
    // 如果单纯用分治，需要递归函数返回不止一个参数，可能需要包装一个类，因为子问题返回频率最高的数字之后，还要加上根节点计算和再判断一次。返回到上一层函数的是sum和一个统计频率的数组
    private Map<Integer, Integer> sumsFreq = new HashMap<Integer, Integer>();
    private int maxFreq = 0;

    public int[] findFrequentTreeSum(TreeNode root) {
        helper(root);

        ArrayList<Integer> arr = new ArrayList<Integer>();
        for (Integer key : sumsFreq.keySet()) {
            if (sumsFreq.get(key) == maxFreq) {
                arr.add(key);
            }
        }
        // slower transform
        // return arr.stream().mapToInt(i->i).toArray();
        return toIntArray(arr);
    }

    private int helper(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int sum = root.val + helper(root.left) + helper(root.right);

            if (!sumsFreq.containsKey(sum)) {
                sumsFreq.put(sum, 1);
            } else {
                sumsFreq.put(sum, sumsFreq.get(sum) + 1);
            }
            maxFreq = maxFreq < sumsFreq.get(sum) ? sumsFreq.get(sum) : maxFreq;
            return sum;
        }
    }

    private int[] toIntArray(List<Integer> list) {
        int[] ret = new int[list.size()];
        for (int i = 0; i < ret.length; i++)
            ret[i] = list.get(i);
        return ret;
    }
}