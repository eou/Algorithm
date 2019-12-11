// 218. The Skyline Problem
class Solution {
    // heap / Priority Queue 版本，时间复杂度为 O(n^2)，如用 lazy remove 可以达到 O(nlogn)，空间复杂度为 O(n)
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> skyline = new ArrayList<>();
        List<int[]> heights = new ArrayList<>();
        // Three edge cases: two buildings have the same start; two buildings have the same end; one building's start is the end of another building 
        // Because start height of a building is negative and end height of a building is positive, the taller buildings' height will cover the shorter buildings
        // And for third case, at the same x index, start heights are always in front of the end heights, thus start height will always be accessed earlier than the end height 
        for(int[] building : buildings) {
            heights.add(new int[]{building[0], -building[2]}); // start x index of building, height
            heights.add(new int[]{building[1], building[2]}); // end x index of building, height
        }
        // sort building heights by x index; if various heights at a same x index, the start index is in front of the end index
        Collections.sort(heights, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[0] == b[0] ? a[1] - b[1] : a[0] - b[0];
            }
        });
        // Collections.sort(heights, (a, b) -> {
        //     return a[0] == b[0] ? a[1] - b[1] : a[0] - b[0];
        // });

        // PriorityQueue 自定义比较器必须指定队列大小，否则只能用 lamda 匿名函数
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>((a, b) -> (b - a));

        pq.offer(0);
        int prev = 0;
        for(int [] height : heights) {
            if(height[1] < 0) { // which means this is a building's start
                pq.offer(-height[1]);
            } else { // which means this is a building's end
                // PriorityQueue.remove is O(n). 但是可以用 lazy remove 为 O(logn) 与 poll() 一样的复杂度
                pq.remove(height[1]);
            }

            int curMaxHeight = pq.peek();
            // if max height change, add a new sky line
            if(prev != curMaxHeight) {
                skyline.add(new int[]{height[0], curMaxHeight});
                prev = curMaxHeight;
            }
        }

        return skyline;
    }
}

class Solution {
    // TreeMap.remove 时间复杂度为 O(logn)，基于红黑树 R-B tree
    public List<int[]> getSkyline(int[][] buildings) {
        List<int[]> skyline = new ArrayList<>();
        List<int[]> heights = new ArrayList<>();
        for (int[] building : buildings) {
            heights.add(new int[] { building[0], -building[2] }); // start x index of building, height
            heights.add(new int[] { building[1], building[2] }); // end x index of building, height
        }
        Collections.sort(heights, new Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return a[0] == b[0] ? a[1] - b[1] : a[0] - b[0];
            }
        });

        // height, frequency. 由于 TreeMap 不能插入重复的 key, 如有重复只能增加 value
        TreeMap<Integer, Integer> map = new TreeMap<>(Collections.reverseOrder());
        map.put(0, 1);
        int prev = 0;
        for (int[] height : heights) {
            if (height[1] < 0) {
                map.put(-height[1], map.computeIfAbsent(-height[1], key -> 0) + 1);
            } else {
                if (map.get(height[1]) == 1) {
                    // O(logn)
                    map.remove(height[1]);
                } else {
                    map.put(height[1], map.get(height[1]) - 1);
                }
            }

            int curMaxHeight = map.firstKey();
            if (prev != curMaxHeight) {
                skyline.add(new int[] { height[0], curMaxHeight });
                prev = curMaxHeight;
            }
        }

        return skyline;
    }
}