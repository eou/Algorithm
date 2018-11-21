
/**
 * Reservoir Sampling
 * https://www.geeksforgeeks.org/reservoir-sampling/
 * 遍历一次大数组或者读取一遍大数据流，等概率选出 k 个数
 * 398. Random Pick Index 要求遍历 n 个数一次，选一个数字，每个数字被选中的概率相等，即 1 / n
 * 此题拓展为遍历 n 个数一次，选 k 个数字，每个被选中的概率相等，即 k / n
 * 如果 n > k, 随机生成 0 - i 之间的数字 j，如果小于 k， 就替换水库中第 j 个数字（即 k / i 的概率保留当前数字）
 * 
 * 可以用数学归纳法证明：对于 i = k + 1, 如果出现替换，第 k + 1 个数字被选中的概率是 k / (k + 1)，其肯定会出现在水库中
 * 而水库中任意数字被替换的概率是 k / (k + 1) * 1 / k = 1 / (k + 1), 则被替换的数字出现在水库中的概率也是 1 - 1 / (k + 1) = k / (k + 1)
 * 对于整个算法，任意数字出现在水库里的概率就是 k / n
 * 详情见 https://zh.wikipedia.org/wiki/%E6%B0%B4%E5%A1%98%E6%8A%BD%E6%A8%A3
 */
import java.io.*;
import java.util.*;

class Solution {
    public int[] ReservoirSampling(int[] streams, int k) {
        int[] reservoir = new int[k];
        for(int i = 0; i < k; i++) {
            reservoir[i] = streams[i];
        }

        Random rand = new Random();
        // if n > k, for k <= i < n, the probability of keeping element in the reservoir is k / i
        for(int i = k; i < streams.length; i++) {
            int r = rand.nextInt(i);
            if(r < k) {
                reservoir[r] = streams[i];
            }
        }

        return reservoir;
    }

    public static void main(String[] args) {
        int[] input = {1, 3, 5, 7, 8, 4, 4, 7, 6, 6, 9, 6, 6, 6, 6, 1, 1, 9, 10};
        for(int i : new Solution().ReservoirSampling(input, 3)) {
            System.out.println(i);
        }
    }
}