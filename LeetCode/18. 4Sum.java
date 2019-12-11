// 18. 4Sum
// 当k > 2的时候，可以利用2sum的思路来优化解法，k - 2个数字的遍历和最后2Sum的线性扫描，使这个问题的时间复杂度为O(n^(k-1))
// 特殊地，当k = 2时，时间复杂度为O(nlogn)，因为需要对数组进行排序，否则是O(n)
class Solution {
    // kSum版本
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, 0, 4, target);
    }

    private List<List<Integer>> kSum(int[] nums, int start, int k, int target) {
        List<List<Integer>> results = new ArrayList<>();
        // 2Sum就可以线性扫描
        if (k == 2) {
            int left = start, right = nums.length - 1;
            while (left < right) {
                int sum = nums[left] + nums[right];
                if (sum == target) {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(nums[left]);
                    tmp.add(nums[right]);
                    results.add(tmp);
                    // 不能用Arrays.asList，此方法获得的数组不能add和remove
                    // results.add(Arrays.asList(nums[left], nums[right]));

                    // 防止重复
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    left++;
                    right--;
                } else if (sum < target) {
                    // 小优化
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    left++;
                } else {
                    // 小优化
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    right--;
                }
            }
        // 把 k 化为 k - 1 解决
        } else {
            for (int i = start; i < nums.length - (k - 1); i++) {
                // 这种判断可以写成 if(i > start && nums[i] == nums[i - 1]) continue;
                if (i == start || nums[i] != nums[i - 1]) {
                    List<List<Integer>> tmp = kSum(nums, i + 1, k - 1, target - nums[i]);
                    for (List<Integer> t : tmp) {
                        t.add(0, nums[i]);
                    }
                    results.addAll(tmp);
                }
            }
        }

        return results;
    }
}

class Solution {
    // 4Sum一般版本，可以看出三个循环嵌套，时间复杂度是O(n^3)
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> results = new ArrayList<>();

        if (nums.length < 4) {
            return results;
        }

        Arrays.sort(nums);
        // 每层结构都一样，最里层就是2Sum
        for (int i = 0; i < nums.length - 3; i++) {
            if (i == 0 || nums[i] != nums[i - 1]) {
                // 两个最大最小值优化
                if (nums[i] * 4 > target) {
                    break;
                }
                // 注意这是 continue; 不是 break;
                if (nums[i] + 3 * nums[nums.length - 1] < target) {
                    continue;
                }

                for (int j = i + 1; j < nums.length - 2; j++) {
                    if (j == i + 1 || nums[j] != nums[j - 1]) {
                        if (nums[j] * 3 > target - nums[i]) {
                            break;
                        }
                        // 注意这是 continue; 不是 break;
                        if (nums[j] + 2 * nums[nums.length - 1] < target - nums[i]) {
                            continue;
                        }

                        int left = j + 1, right = nums.length - 1;
                        while (left < right) {
                            int sum = nums[i] + nums[j] + nums[left] + nums[right];
                            if (sum == target) {
                                results.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                                while (left < right && nums[left] == nums[left + 1]) {
                                    left++;
                                }
                                while (left < right && nums[right] == nums[right - 1]) {
                                    right--;
                                }
                                left++;
                                right--;
                            } else if (sum < target) {
                                left++;
                            } else {
                                right--;
                            }
                        }
                    }
                }
            }
        }

        return results;
    }
}

class Solution {
    // HashMap版本，把4Sum转化为2个2Sum问题
    // 时间复杂度略好，O(n^2) ~ O(n^3)
    // 当数字都不一样的时候，O(n^2)，当数字全部相同的时候，O(n^3)，因为会重复计算
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> results = new ArrayList<>();

        if (nums == null || nums.length < 4) {
            return results;
        }

        Arrays.sort(nums);

        Map<Integer, List<List<Integer>>> map = createTwoSumMap(nums);
        for (int i = 0; i <= nums.length - 2; ++i) {
            if (i != 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            for (int j = i + 1; j <= nums.length - 1; ++j) {
                if (j != i + 1 && nums[j] == nums[j - 1]) {
                    continue;
                }
                
                int sum = target - (nums[i] + nums[j]);
                // 每次 containsKey 需要 log(n^2) (最多有 n^2 个元素)
                if (!map.containsKey(sum)) {
                    continue;
                }
                List<List<Integer>> pairs = map.get(sum);
                int previousNum1 = Integer.MAX_VALUE;
                int previousNum2 = Integer.MAX_VALUE;
                // 当数字全部相同的时候，如果走到这一步，为 O(n^2)，而之前二层循环因为重复计算为 O(n)
                for (List<Integer> pair : pairs) {
                    int thirdIndex = pair.get(0), fourthIndex = pair.get(1);
                    if (thirdIndex > j && fourthIndex > j && nums[thirdIndex] != previousNum1 && nums[fourthIndex] != previousNum2) {
                        results.add(Arrays.asList(nums[i], nums[j], nums[thirdIndex], nums[fourthIndex]));
                        previousNum1 = nums[thirdIndex];
                        previousNum2 = nums[fourthIndex];
                    }
                }
            }
        }
        return results;
    }

    private Map<Integer, List<List<Integer>>> createTwoSumMap(int[] nums) {
        Map<Integer, List<List<Integer>>> map = new HashMap<>();    // sum => ((index_i, index_j), ...)
        for (int i = 0; i <= nums.length - 2; ++i) {
            for (int j = i + 1; j <= nums.length - 1; ++j) {
                int sum = nums[i] + nums[j];
                List<Integer> pair = new ArrayList<>();
                pair.add(i);
                pair.add(j);
                if (map.containsKey(sum)) {
                    map.get(sum).add(pair);
                } else {
                    List<List<Integer>> pairs = new ArrayList<>();
                    pairs.add(pair);
                    map.put(sum, pairs);
                }
            }
        }
        return map;
    }
}