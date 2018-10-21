// 767. Reorganize String
class Solution {
    // 用优先队列维持一个最大堆，不断从队首取出两个字符，时间复杂度O(S*logK)
    // Java没有二元组，只能自己定义
    private class Tuple {
        char c;
        int count;
        Tuple(char c, int count) {
            this.c = c;
            this.count = count;
        }
    }
    
    public String reorganizeString(String s) {
        if(s == null || s.length() == 0) {
            return "";
        }
        
        PriorityQueue<Tuple> pq = new PriorityQueue<Tuple>((a, b) -> a.count == b.count ? a.c - b.c : b.count - a.count);
        int[] cnt = new int[26];
        for(Character c : s.toCharArray()) {
            cnt[c - 'a']++;
        }
        
        for(int i = 0; i < 26; i++) {
            if(cnt[i] * 2 > s.length() + 1) {
                return "";
            }
            if(cnt[i] != 0) {
                pq.add(new Tuple((char)('a' + i), cnt[i]));
            }
        }
        
        StringBuilder strBuilder = new StringBuilder();
        while(pq.size() >= 2) {
            Tuple t1 = pq.poll();
            Tuple t2 = pq.poll();
            strBuilder.append(t1.c);
            strBuilder.append(t2.c);
            t1.count--;
            t2.count--;
            if(t1.count > 0) {
                pq.add(t1);
            }
            if(t2.count > 0) {
                pq.add(t2);
            }
        }
        
        if(pq.size() > 0) {
            strBuilder.append(pq.poll().c);
        }
        
        return strBuilder.toString();
    }
}

class Solution {
    // 循环隔位放字符，时间复杂度O(S + KlogK)
    // 注意此版本生成的字符串与上面不同
    public String reorganizeString(String s) {
        int[] cnt = new int[26];
        // 将字符和字符出现次数编码在一起，501则代表 b 出现了5次
        for (Character c : s.toCharArray()) {
            cnt[c - 'a'] += 100;
        }
        for (int i = 0; i < 26; ++i) {
            cnt[i] += i;
        }
        Arrays.sort(cnt);

        char[] result = new char[s.length()];
        int pos = 1;
        for (int code : cnt) {
            int n = code / 100;
            char c = (char) ('a' + (code % 100));
            if (n * 2 > s.length() + 1) {
                return "";
            }
            
            // 从第2位开始，隔1位放一个字符，越界则从第1位开始
            for (int i = 0; i < n; ++i) {
                if (pos >= s.length()) {
                    pos = 0;
                }
                result[pos] = c;
                pos += 2;
            }
        }

        return String.valueOf(result);
    }
}