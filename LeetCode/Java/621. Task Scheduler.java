// 621. Task Scheduler
// 与 358 大同小异，双数组，时间复杂度O(n^2)，也可以优先队列
// 此题有一些 follow up
class Solution {
    public int leastInterval(char[] tasks, int n) {
        if(tasks.length == 0) {
            return 0;
        }
        
        int step = 0;
        // 若有别的字符，用 HashMap
        int[] count = new int[26];
        int[] next = new int[26];
        for(Character c : tasks) {
            count[c - 'A']++;
        }
        
        int i = 0;
        while(i < tasks.length) {
            int index = findNextTask(count, next, step);
            // 注意这里与358不同，细节调整一下
            if(index != -1) {
                count[index]--;
                next[index] = step + n + 1;
                i++;
            }
            step++;
        }
        
        return step;
    }
    
    private int findNextTask(int[] count, int[] next, int index) {
        int result = -1;
        int max = Integer.MIN_VALUE;
        for(int i = 0; i < 26; i++) {
            if(count[i] > 0 && count[i] > max && index >= next[i]) {
                max = count[i];
                result = i;
            }
        }
        return result;
    }
}

class Solution {
    // 优先队列版本，可以用 map 也可以用数组计数，但是最后队列中只存频率即可
    public int leastInterval(char[] tasks, int n) {
        // Map<Character, Integer> counts = new HashMap<Character, Integer>();
        // for (char task : tasks) {
        // counts.put(task, counts.getOrDefault(task, 0) + 1);
        // }
        int[] count = new int[26];
        for (char task : tasks) {
            count[task - 'A']++;
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            public int compare(Integer a, Integer b) {
                return b - a;
            }
        });
        // pq.addAll(counts.values());
        for (int c : count) {
            if (c != 0) {
                pq.add(c);
            }
        }

        int steps = 0;
        int cycle = n + 1;
        while (!pq.isEmpty()) {
            int worktime = 0;
            List<Integer> tmp = new ArrayList<Integer>();
            for (int i = 0; i < cycle; i++) {
                if (!pq.isEmpty()) {
                    tmp.add(pq.poll());
                    worktime++;
                }
            }
            for (int cnt : tmp) {
                if (--cnt > 0) {
                    pq.offer(cnt);
                }
            }
            // 如果到最后一轮，队列里的任务数不足 n，就不需要加 n + 1
            steps += !pq.isEmpty() ? cycle : worktime;
        }

        return steps;
    }
}

class Solution {
    // 巧妙解法，时间复杂度O(n)
    public int leastInterval(char[] tasks, int n) {
        int[] c = new int[26];
        for (char t : tasks) {
            c[t - 'A']++;
        }
        Arrays.sort(c);

        int i = 25;
        while (i >= 0 && c[i] == c[25]) {
            i--;
        }

        return Math.max(tasks.length, (c[25] - 1) * (n + 1) + 25 - i);
    }
}

// follow up: 维持 task 原顺序
class Solution {
    // 空间复杂度为 O(n)
    public int leastIntervalWithOrder(String s, int k) {
        // char, next avalible time
        Map<Character, Integer> map = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        int steps = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (!map.containsKey(c) || steps >= map.get(c)) {
                map.put(c, steps + k + 1);
                stringBuilder.append(c);
                i++;
            } else {
                stringBuilder.append('-');
            }
            steps++;
        }
        
        System.out.print(stringBuilder.toString());
        return steps;
    }
}
    
class Solution {
    // 空间复杂度只需 O(k)，维持一个 cool Down 的队列和集合
    public int leastIntervalWithOrder(String s, int k) {
        Queue<Character> queue = new ArrayDeque<>();
        Set<Character> set = new HashSet<>();

        StringBuilder stringBuilder = new StringBuilder();

        int i = 0;
        int steps = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (set.contains(c)) {
                stringBuilder.append("-");
                queue.offer(null);
            } else {
                stringBuilder.append(c);
                queue.offer(c);
                set.add(c);
                i++;
            }

            if (queue.size() > k) {
                Character next = queue.poll();
                if (next != null)
                    set.remove(next);
            }
            steps++;
        }

        System.out.println(sb.toString());
        return steps;
    }
}