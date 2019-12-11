// 346. Moving Average from Data Stream
class MovingAverage {
    private Deque<Integer> queue;
    private int size;
    private double sum;
    
    public MovingAverage(int size) {
        sum = 0;
        this.size = size;
        queue = new ArrayDeque<>();
    }
    
    public double next(int val) {
        queue.offer(val);
        this.sum += val;
        if(queue.size() > this.size) {
            sum -= queue.poll();
        }
        
        return this.sum / queue.size();
    }
}

class MovingAverage {
    // 也可以用数组做
    private int[] window;
    private int size, insert;
    private double sum;
    
    public MovingAverage(int size) {
        window = new int[size];
        insert = 0;
        sum = 0;
    }
    
    public double next(int val) {
        if (size < window.length)  {
            size++;
        }
        
        sum += val;
        // 如更新的值覆盖之前的值，需要减去之前的值再更新
        sum -= window[insert];
        window[insert] = val;
        
        insert = (insert + 1) % window.length;
        
        return sum / size;
    }
}