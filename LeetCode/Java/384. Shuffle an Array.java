// 384. Shuffle an Array
// 暴力做法是随机从原数组拿出一个数字放入新数组，时间复杂度为 O(n^2)，因为删除原数组的数字也需要 O(n)
class Solution {
    /**
     * Fisher-Yates Algorithm，时空复杂度都为 O(n) 
     * For any element e, the probability that it will be shuffled into the first position
     * = probability that it is selected for swapping when i = 1
     * = 1/n
     * 
     * For any element e, the probability that it will be shuffled into the second position
     * = probability that it is NOT selected for the first position * probability that it is selected for swapping when i = 2
     * = (n-1)/n * 1/(n-1)
     * = 1/n
     */
    private int[] original;
    Random rand = new Random();

    public Solution(int[] nums) {
        original = nums.clone();
    }

    public int[] reset() {
        return original;
    }

    public int[] shuffle() {
        int[] array = original.clone();

        // 从尾到头交换比较方便，因为 Random 生成的是 [0, i) 之间的数字
        for (int i = 0; i < array.length; i++) {
            int j = randRange(i, array.length);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
    }

    private int randRange(int min, int max) {
        return rand.nextInt(max - min) + min;
    }
}