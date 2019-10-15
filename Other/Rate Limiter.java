/**
 * Rate Limiter.java
 * Implement a RateLimiter Class with an isAllow method. 
 * Every request comes in with a unique clientID, deny a request if that client has made more than 100 requests in the past second.
 * 将来自一个 ip ? 或多个 ip  的访问次数限制在 1 秒 100 次
 * 
 * https://www.geeksforgeeks.org/leaky-bucket-algorithm/
 * Some advantage of token Bucket over leaky bucket:
 *      If bucket is full in token Bucket , token are discard not packets. While in leaky bucket, packets are discarded.
 *      Token Bucket can send Large bursts can faster rate while leaky bucket always sends packets at constant rate.
 * https://leetcode.com/discuss/interview-question/124558/Uber-or-Rate-Limiter
 * https://www.comm.utoronto.ca/~jorg/teaching/ece466/labs/lab2/TokenBucket/
 * https://github.com/vladimir-bukhtoyarov/bucket4j/blob/master/doc-pages/token-bucket-brief-overview.md
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
    // Circular queue using rotated array, 仅仅对输入一个 ip
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

class RateLimiter {
    // use bucket to control rate: Leacky bucket or Token bucket
    public class Bucket {
        public long TIME_LIMIT = 1000L;
        // actually in this implementation, we have not used FIFO feature of queue, we can replace it using array
        public Deque<Long> queue;       
        public long REQUEST_LIMIT;
        public Bucket(long rate) {
            queue = new ArrayDeque<>();
            REQUEST_LIMIT = rate;
        }
        public boolean limit(long curTime) {
            // only store requests in recent 1 second
            while(!queue.isEmpty() && curTime - queue.peek() >= TIME_LIMIT) {
                queue.poll();
            }
                
            if(queue.size() < rate) {
                queue.offer(curTime); 
                return true;
            }
            
            // too many requests in recent 1 second
            return false;
        }
    }

    public long rate;       // how many requests should be processed per second, like 100
    public Map<String, Bucket> requests;        // id => its own bucket
    public RateLimiter (long rate) {
        requests = new HashMap<>();
        this.rate = rate;
    }
    public void isAllow(String id) {
        long curTime = System.currentTimeMillis();
        requests.putIfAbsent(id, new Bucket(rate));
        Bucket bucket = requests.get(id);
        if (bucket.limit(curTime)) {
            // print just for testing
            System.out.println("allow");
        } else {
            System.out.println("disallow");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter(4);
        for (int i = 0; i < 10; i++) {
            rateLimiter.isAllow("128.0.0.1");
            Thread.sleep(200);  // 1 second 5 requests, should be 4 allow 1 disallow
        }
    }
}

class TokenBucket {
    private final long capacity;    // bucket capacity
    private final double refillTokensPerOneMillis;  // refill rate
    private double availableTokens;
    private long lastRefillTimestamp;

    /**
     * Creates token-bucket with specified capacity and refill rate equals to refillTokens/refillPeriodMillis
     */
    public TokenBucket(long capacity, long refillTokens, long refillPeriodMillis) {
        this.capacity = capacity;
        this.refillTokensPerOneMillis = (double)refillTokens / (double)refillPeriodMillis;
        this.availableTokens = capacity;
        this.lastRefillTimestamp = System.currentTimeMillis();
    }

    synchronized public boolean tryConsume(int numberTokens) {
        refill();
        if (availableTokens < numberTokens) {
            return false;
        } else {
            availableTokens -= numberTokens;
            return true;
        }
    }

    private void refill() {
        long currentTimeMillis = System.currentTimeMillis();
        if (currentTimeMillis > lastRefillTimestamp) {
            long millisSinceLastRefill = currentTimeMillis - lastRefillTimestamp;
            double refill = millisSinceLastRefill * refillTokensPerOneMillis;
            this.availableTokens = Math.min(capacity, availableTokens + refill);
            this.lastRefillTimestamp = currentTimeMillis;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucket limiter = new TokenBucket(10, 5, 1000);  // 5 tokens per 1 second

        long startMillis = System.currentTimeMillis();
        long consumed = 0;
        // test 10 s
        while (System.currentTimeMillis() - startMillis < 1000) {
            if (limiter.tryConsume(1)) {
                System.out.println("allowed");
                consumed++;
            } else {
                System.out.println("disallowed");
            }
            Thread.sleep(50);
        }
        System.out.println(consumed);
    }

}

class TokenBucket {
    private final int capacity;
    private final int tokensPerSeconds;
    private int tokens = 0;
    private long lastTimestamp = System.currentTimeMillis();
   
    public TokenBucket(int tokensPerUnit, TimeUnit unit) {
        capacity = tokensPerSeconds = (int) (tokensPerUnit / unit.toSeconds(1L));
    }
   
    public boolean take() {
        long now = System.currentTimeMillis();
        if (now > lastTimestamp) {
            tokens += (int) ((now - lastTimestamp) * tokensPerSeconds / 1000);
        }
        if (tokens > capacity) tokens = capacity;
        lastTimestamp = now;

        if (tokens < 1) {
            return false;
        }
        tokens--;
        return true;
    }
    
    public static void main(String[] args) throws InterruptedException {
        TokenBucket bucket = new TokenBucket(250, TimeUnit.MINUTES);
        Thread.sleep(1000L);
        for (int i = 0; i < 5; i++) {
            System.out.println(bucket.take());
        }
        Thread.sleep(1000L);
        for (int i = 0; i < 5; i++) {
            System.out.println(bucket.take());
        }
    }
}
     