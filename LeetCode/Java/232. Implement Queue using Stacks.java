// 232. Implement Queue using Stacks
class MyQueue {
    // 时间复杂度为 push() O(n), pop() O(1)
    Stack<Integer> stack;
    Stack<Integer> helper;
    public MyQueue() {
        stack = new Stack();
        helper = new Stack();
    }
    
    public void push(int x) {
        while(!stack.isEmpty()) {
            helper.push(stack.pop());
        }
        stack.push(x);
        while(!helper.isEmpty()) {
            stack.push(helper.pop());
        }
    }
    
    public int pop() {
        return stack.pop();
    }
    
    public int peek() {
        return stack.peek();
    }
    
    public boolean empty() {
        return stack.isEmpty();
    }
}

class MyQueue {
    // 时间复杂度为 push() O(1), pop() amortized O(1)
    Stack<Integer> input;
    Stack<Integer> output;
    public MyQueue() {
        input = new Stack<>();
        output = new Stack<>();
    }
    
    public void push(int x) {
        input.push(x);
    }
    
    public int pop() {
        if(output.isEmpty()) {
            while(!input.isEmpty()) {
                output.push(input.pop());
            }
        }
        return output.pop();
    }
    
    public int peek() {
        if(output.isEmpty()) {
            while(!input.isEmpty()) {
                output.push(input.pop());
            }
        }
        return output.peek();
    }
    
    public boolean empty() {
        return input.isEmpty() && output.isEmpty();
    }
}
