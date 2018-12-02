/**
 * Design Rate Limiter.java
 * 将来自一个 ip 的访问次数限制在 1 秒 100 次
 */
import java.util.*;
import java.io.*;

class RateLimiter {
    // 多个 ip
    private class Node {
        long time;
        String ip;

        public Node(long t, String ip) {
            this.time = t;
            this.ip = ip;
        }
    }

    // ip, timestamp
    Queue<Node> queue = new ArrayDeque<>();
    // ip => callTimes
    Map<String, Integer> map = new HashMap<>();

    public boolean ipLimiter(long timestamp, String ip, int callTimes) {
        while (!queue.isEmpty() && timestamp - queue.peek().time >= 1000) {
            Node node = queue.poll();
            map.remove(node.ip);
        }

        if (!map.containsKey(ip)) {
            // first call iplimiter
            if (callTimes > 100) {
                return false;
            }
            queue.offer(new Node(timestamp, ip));
            map.put(ip, callTimes);
            return true;
        } else {
            if (map.get(ip) + callTimes <= 100) {
                map.put(ip, map.get(ip) + callTimes);
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateLimiter();
        System.out.println(rateLimiter.ipLimiter(2000, "122.33.3.1", 1001));
        System.out.println(rateLimiter.ipLimiter(3000, "122.33.3.1", 100));
        System.out.println(rateLimiter.ipLimiter(3000, "122.33.3.1", 1));
        System.out.println(rateLimiter.ipLimiter(3001, "122.33.3.1", 100));
    }
}

class RateLimiter {
    // Circular queue using array, 仅仅对输入一个 ip
    long[] timestamp;
    int pointer;
    public RateLimiter() {
        timestamp = new long[100];
        pointer = 0;
    }

    public boolean ipLimiter(long currentTime) {
        if(timestamp[pointer] == 0 || currentTime - timestamp[pointer] >= 1000) {
            timestamp[pointer] = currentTime;
            pointer = (pointer + 1) % 100;
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        RateLimiter rateLimiter = new RateLimiter();
        for(int i = 0; i < 101; i++) {
            System.out.println(rateLimiter.ipLimiter(99));
        }
        System.out.println(rateLimiter.ipLimiter(1000));
        System.out.println(rateLimiter.ipLimiter(1098));
        System.out.println(rateLimiter.ipLimiter(1099));
    }
}