/**
 * Memcache
 * https://www.lintcode.com/problem/memcache/description
 * 类似 146. LRU Cache，但是数据结构比较简单
 */
public class Memcache {
    private Map<Integer, int[]> map;
    
    public Memcache() {
         map = new HashMap<Integer, int[]>();
    }
    
    public int get(int curtTime, int key) {
        int[] temp;
        if (map.containsKey(key)) {
            temp = map.get(key);
        } else {
            return Integer.MAX_VALUE;
        }
        
        // temp[1] is expire time
        if (curtTime > temp[1] && temp[1] != 0) {
            return Integer.MAX_VALUE;
        } else {
            return temp[0];
        }
    }
    
    public void set(int curtTime, int key, int value, int ttl) {
        int expire = curtTime + ttl - 1;
        if (ttl == 0) {
            expire = 0;
        }
        int[] val = {value, expire}; 
        map.put(key, val);
    }
    
    public void delete(int curtTime, int key) {
        if (!map.containsKey(key)) {
            return;
        } else {
            map.remove(key);
        }
    }
    
    public int incr(int curtTime, int key, int delta) {
        if (!map.containsKey(key)) {
            return Integer.MAX_VALUE;
        }

        int[] temp = map.get(key);
        if (curtTime > temp[1] && temp[1] != 0) {
            return Integer.MAX_VALUE;
        } else {
            int value = temp[0];
            value = value + delta;
            int[] val = {value, temp[1]};
            map.put(key, val);
            return value;
        }
    }
    
    public int decr(int curtTime, int key, int delta) {
        if (!map.containsKey(key)) {
            return Integer.MAX_VALUE;
        }

        int[] temp = map.get(key);
        if (curtTime > temp[1] && temp[1] != 0) {
            return Integer.MAX_VALUE;
        } else {
            int value = temp[0];
            value = value - delta;
            int[] val = {value, temp[1]};
            map.put(key, val);
            return value;
        }
    }
}