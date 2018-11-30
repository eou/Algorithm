// 362. Design Hit Counter
// 与 359. Logger Rate Limiter 同类型
class HitCounter {
    // mapping times to hits
    private int[] times;
    private int[] hits;

    public HitCounter() {
        times = new int[300];
        hits = new int[300];
    }

    public void hit(int timestamp) {
        int index = timestamp % 300;
        if (times[index] != timestamp) {
            times[index] = timestamp;
            hits[index] = 1;
        } else {
            hits[index]++;
        }
    }

    public int getHits(int timestamp) {
        int total = 0;
        for (int i = 0; i < 300; i++) {
            if (timestamp - times[i] < 300) {
                total += hits[i];
            }
        }

        return total;
    }
}

// large scale hits is inefficient
class HitCounter {
    Deque<Integer> queue;
    public HitCounter() {
        queue = new ArrayDeque<Integer>();
    }
    
    public void hit(int timestamp) {
        queue.offer(timestamp);
    }
    
    public int getHits(int timestamp) {
        while(!queue.isEmpty() && timestamp - queue.peek() >= 300) {
            queue.poll();
        }
        return queue.size();
    }
}

// multi-thread
class HitCounter {
	AtomicIntegerArray time;
	AtomicIntegerArray hit;
    
    public HitCounter() {
        time  = new AtomicIntegerArray(300);
        hit = new AtomicIntegerArray(300);
    }
    
    public void hit(int timestamp) {
    	int index = timestamp % 300;
    	if (time.get(index) != timestamp) {
    		time.set(index, timestamp);
    		hit.set(index, 1);
    	} else {
    		hit.incrementAndGet(index);//add one
    	}
        
    }
    
    public int getHits(int timestamp) {
    	int total = 0;
    	for (int i = 0; i < 300; i++) {
    		if (timestamp - time.get(i) < 300) {
    			total += hit.get(i);
    		}
    	}
    	return total;
    }
}
