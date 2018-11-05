// 358. Rearrange String k Distance Apart
class Solution {
    // 优先队列版本与767基本一致，767是 k = 2 的特殊情形，时间复杂度是O(nlogK)，其中k = 26，所以基本是O(n)
    private class Tuple {
        char c;
        int freq;
        Tuple(char c, int freq) {
            this.c = c;
            this.freq = freq;
        }
    }
    public String rearrangeString(String s, int k) {
        if(s.length() == 0) {
            return "";
        }
        // 注意在这里发现一个LeetCode上的test case bug：当 s.length() < k 的时候也不能返回s，比如s = "aaaaaa", k = 10 应该返回空字符串
        if(k <= 1) {
            return s;
        }
        
        PriorityQueue<Tuple> pq = new PriorityQueue<>((a, b) -> (a.freq == b.freq ? a.c - b.c : b.freq - a.freq));
        int[] cnt = new int[26];
        for(Character c : s.toCharArray()) {
            cnt[c - 'a']++;
        }
        
        for(int i = 0; i < 26; i++) {
            if(cnt[i] != 0) {
                // 这里应该有上下限，但是暂时没想出
                // if(cnt[i] * k > s.length() + k - 1) {
                //     return "";
                // } else {
                //     pq.offer(new Tuple((char)('a' + i), cnt[i]));
                // }
                pq.offer(new Tuple((char) ('a' + i), cnt[i]));
            }
        }
        
        StringBuilder strBuilder = new StringBuilder();
        while(pq.size() >= k) {
            List<Tuple> tmp = new ArrayList<>();
            for(int i = 0; i < k; i++) {
                Tuple t = pq.poll();
                strBuilder.append(t.c);
                t.freq--;
                if(t.freq > 0) {
                    tmp.add(t);
                }
            }
            // 每k个元素更新一次
            for(Tuple t : tmp) {
                pq.offer(t);
            }
            tmp.clear();
        }
        
        
        while(pq.size() > 0) {
            Tuple t = pq.poll();
            strBuilder.append(t.c);
        }
        
        // 最后要判断长度
        return strBuilder.length() == s.length() ? strBuilder.toString() : "";
    }
}

class Solution {
    public String rearrangeString(String s, int k) {
        if (s == null || s.length() == 0) {
            return "";
        }
        if (k <= 1) {
            return s;
        }

        int[] count = new int[26];
        int[] next = new int[26];
        for (Character c : s.toCharArray()) {
            count[c - 'a']++;
        }

        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            int index = findNextChar(count, next, i);
            if (index == -1) {
                return "";
            }
            strBuilder.append((char) ('a' + index));
            count[index]--;
            next[index] = i + k;
        }

        return strBuilder.toString();
    }

    private int findNextChar(int[] count, int[] next, int index) {
        int result = -1;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0 && count[i] > max && index >= next[i]) {
                max = count[i];
                result = i;
            }
        }
        return result;
    }
}