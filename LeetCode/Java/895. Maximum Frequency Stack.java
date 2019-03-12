// 895. Maximum Frequency Stack
class FreqStack {
    Map<Integer, Integer> map1;          // number => frequency
    Map<Integer, Deque<Integer>> map2;   // frequency => stack
    int maxFreq;
    public FreqStack() {
        maxFreq = 0;
        map1 = new HashMap<>();
        map2 = new HashMap<>();
    }
    
    public void push(int x) {
        // update map1
        map1.put(x, map1.getOrDefault(x, 0) + 1);
        
        // update map2
        Deque<Integer> stack;
        if (map2.containsKey(map1.get(x))) {
            stack = map2.get(map1.get(x));
        } else {
            stack = new ArrayDeque<>();
        }
        stack.push(x);
        map2.put(map1.get(x), stack);
        // update max frequency
        maxFreq = Math.max(maxFreq, map1.get(x));
    }
    
    public int pop() {
        int result = map2.get(maxFreq).peek();
        if (map1.get(result) == 1) {
            map1.remove(result);
        } else {
            map1.put(result, map1.get(result) - 1);
        }
        map2.get(maxFreq).pop();
        if (map2.get(maxFreq).isEmpty()) {
            map2.remove(maxFreq);
            --maxFreq;
        }
        return result;
    }
}

