// 15. 3Sum
class Solution {
    // 思路直接，数组排序后按顺序在一个元素之后的所有元素中找2Sum
    // 时间复杂度O(n^2)
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> results = new ArrayList<>();
        for(int i = 0; i < nums.length - 2; i++) {
            if(i == 0 || nums[i] != nums[i - 1]) {
                int left = i + 1, right = nums.length - 1, sum = 0 - nums[i];
                while(left < right) {
                    if(nums[left] + nums[right] == sum) {
                        // List<Integer> tmp = new ArrayList<>();
                        // tmp.add(nums[i]);
                        // tmp.add(nums[left]);
                        // tmp.add(nums[right]);
                        // results.add(tmp);
                        // 注意用Arrays.asList获得的数组没有add和remove方法
                        results.add(Arrays.asList(nums[i], nums[left], nums[right]));
                        while(left < right && nums[left] == nums[left + 1]) {
                            left++;
                        }
                        while(left < right && nums[right] == nums[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    } else if(nums[left] + nums[right] < sum) {
                        // 小优化无关紧要
                        // while (left < right && nums[left] == nums[left + 1]) {
                        //     left++;
                        // }
                        left++;
                    } else {
                        // 小优化无关紧要
                        // while (left < right && nums[right] == nums[right - 1]) {
                        //     right--;
                        // }
                        right--;
                    }
                }
            }
        }
        
        return results;
    }
}

class Solution {
    // 不用排序的版本，超时
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        Set<List<Integer>> set = new HashSet<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        for (int i = 0; i < nums.length; i++) {
            // 注意 j 从 0 开始，因为内部还是需要判断升降序来去重，如果从 i + 1 开始可能会漏解
            for (int j = 0; j < nums.length; j++) {
                int a = nums[i];
                int b = nums[j];
                int c = 0 - a - b;
                // 去重
                if (!(a >= b && b >= c)) {
                    continue;
                }

                if (map.containsKey(c)) {
                    map.put(a, map.get(a) - 1);
                    map.put(b, map.get(b) - 1);
                    map.put(c, map.get(c) - 1);
                } else {
                    continue;
                }

                if (map.get(a) >= 0 && map.get(b) >= 0 && map.get(c) >= 0) {
                    // 去重
                    set.add(Arrays.asList(a, b, c));
                }

                map.put(a, map.get(a) + 1);
                map.put(b, map.get(b) + 1);
                map.put(c, map.get(c) + 1);
            }
        }

        results.addAll(set);
        return results;
    }
}

class Solution {
    // 不用排序的版本，超时，时间复杂度 O(n^3)
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();
        Set<List<Integer>> set = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                int a = nums[i];
                int b = nums[j];
                int c = 0 - a - b;
                if (!(a >= b && b >= c)) {
                    continue;
                }
                for (int k = 0; k < nums.length; k++) {
                    if (nums[k] == c && i != j && j != k && i != k) {
                        set.add(Arrays.asList(a, b, c));
                    }
                }
            }
        }

        results.addAll(set);
        return results;
    }
}

// 一个变形，输入改成三个有序数组
class Solution {
    public List<List<Integer>> threeArraythreeSum(int[] a, int[] b, int[] c) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < a.length; i++) { // a pointer
            if (i == 0 || a[i] != a[i - 1]) {
                int left = 0; // b pointer
                int right = c.length - 1; // c pointer
                int sum = 0 - a[i];
                while (left < b.length && right >= 0) {
                    if (b[left] + c[right] == sum) {
                        result.add(Arrays.asList(a[i], b[left], c[right]));
                        while (left < b.length - 1 && b[left] == b[left + 1]) {
                            left++;
                        }
                        while (right > 0 && c[right] == c[right - 1]) {
                            right--;
                        }
                        left++;
                        right--;
                    } else if (b[left] + c[right] < sum) {
                        left++;
                    } else {
                        right--;
                    }
                }
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int[] a = { -1, -1, -3, 4, 7, 9, 11, 55 };
        int[] b = { -43, -2, 0, 0, 0, 1, 2 };
        int[] c = { 0, 0, 5, 5, 10, 11 };

        for (List<Integer> list : new Solution().threeArraythreeSum(a, b, c)) {
            for (Integer i : list) {
                System.out.print(i + ", ");
            }
            System.out.println();
        }
    }
}