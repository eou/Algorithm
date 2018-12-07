// 225. Implement Stack using Queues
class MyStack {
    // 时间复杂度为 push O(n), pop O(1)
    Deque<Integer> queue;
    Deque<Integer> helper;
    public MyStack() {
        queue = new ArrayDeque<>();
        helper = new ArrayDeque<>();
    }
    
    public void push(int x) {
        while(!queue.isEmpty()) {
            helper.offer(queue.poll());
        }
        queue.offer(x);
        while(!helper.isEmpty()) {
            queue.offer(helper.poll());
        }
    }
    
    public int pop() {
        return queue.poll();
    }
    
    public int top() {
        return queue.peek();
    }
    
    public boolean empty() {
        return queue.isEmpty();
    }
}

class MyStack {
    // 时间复杂度为 push O(1), pop O(n)
    Deque<Integer> input;
    Deque<Integer> output;
    public MyStack() {
        input = new ArrayDeque<>();
        output = new ArrayDeque<>();
    }
    
    public void push(int x) {
        input.offer(x);
    }
    
    public int pop() {
        while(input.size() > 1) {
            output.offer(input.poll());
        }
        Deque<Integer> tmp = input;
        input = output;
        output = tmp;
        return output.poll();
    }
    
    public int top() {
        while(input.size() > 1) {
            output.offer(input.poll());
        }
        Deque<Integer> tmp = input;
        input = output;
        output = tmp;
        
        int top = output.poll();
        input.offer(top);
        return top;
    }
    
    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }
}