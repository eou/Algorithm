// 359. Logger Rate Limiter
// 与 362. Design Hit Counter 同类型
class Logger {
    Map<String, Integer> map;
    public Logger() {
        map = new HashMap<>();
    }
    
    public boolean shouldPrintMessage(int timestamp, String message) {
        if(map.containsKey(message)) {
            if(timestamp - map.get(message) < 10) {
                return false;
            }
        }
        map.put(message, timestamp);
        return true;
    }
}

class Logger {
    private int[] times;
    private Set[] sets;

    public Logger() {
        times = new int[10];
        sets = new Set[10];
        for(int i = 0; i < 10; i++) {
            sets[i] = new HashSet<String>();
        }
    }

    public boolean shouldPrintMessage(int timestamp, String message) {
        int index = timestamp % 10;
        if(times[index] != timestamp) {
            sets[index].clear();
            times[index] = timestamp;
        }
        
        for(int i = 0; i < 10; i++) {
            if(timestamp - times[i] < 10) {
                if(sets[i].contains(message)) {
                    return false;
                }
            }
        }

        sets[index].add(message);
        return true;
    }
}