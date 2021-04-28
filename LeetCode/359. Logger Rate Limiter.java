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

// Only store message in last 20 seconds
class Logger {
    private Map<String, Integer> last20;    // Only stores messages in past 20~10 seconds
    private Map<String, Integer> last10;    // Only stores messages in past 10 seconds
    private int latest;

    public Logger() {
        this.last20 = new HashMap<>();
        this.last10 = new HashMap<>();
        this.latest = 0;
    }
    
    public boolean shouldPrintMessage(int timestamp, String message) {
        if (timestamp >= latest + 20) {
            last20.clear();
            last10.clear();
            latest = timestamp;
        } else if (timestamp >= latest + 10){
            last20 = new HashMap<>(last10);
            last10.clear();
            latest = timestamp;
        }
        
        // must not
        if(last10.containsKey(message)) {
            return false;
        }
        
        // it depends
        if(last20.containsKey(message)){
            int last = last20.get(message);
            if(last + 10 > timestamp) {
                return false;
            }
        }
        
        last10.put(message, timestamp);
        return true;
    }
}

// Circular buffer
class Logger {
    private int[] times;
    private Set[] messages;

    public Logger() {
        times = new int[10];
        messages = new Set[10]; // store messages in i second
        for(int i = 0; i < 10; i++) {
            messages[i] = new HashSet<String>();
        }
    }

    public boolean shouldPrintMessage(int timestamp, String message) {
        int index = timestamp % 10;
        if(times[index] != timestamp) {
            messages[index].clear();
            times[index] = timestamp;
        }
        
        for(int i = 0; i < 10; i++) {
            if(timestamp - times[i] < 10) {
                if(messages[i].contains(message)) {
                    return false;
                }
            }
        }

        messages[index].add(message);
        return true;
    }
}