// 155. Min Stack
class MinStack {
    class minStackEle {
        int value;
        int min;
        minStackEle(int v, int m) {
            value = v;
            min = m;
        }
    }
    Stack<minStackEle> stack;
    
    public MinStack() {
        stack = new Stack<>();
    }
    
    public void push(int x) {
        int min = stack.isEmpty() ? x : Math.min(stack.peek().min, x);
        stack.push(new minStackEle(x, min));
    }
    
    public void pop() {
        stack.pop();
    }
    
    public int top() {
        return stack.peek().value;
    }
    
    public int getMin() {
        return stack.peek().min;
    }
}

// store the difference of current value and current min value
// pay attetion to the difference between Integer.MIN_VALUE and Integer.MAX_VALUE
class MinStack {
    long min;
    Stack<Long> stack;

    public MinStack() {
        stack = new Stack<>();
    }

    public void push(int x) {
        if (stack.isEmpty()) {
            stack.push(0L);
            min = x;
        } else {
            stack.push(x - min);
            min = x < min ? x : min;
        }
    }

    public void pop() {
        if (stack.isEmpty()) {
            return;
        }

        long pop = stack.pop();
        if (pop < 0) {
            min = min - pop;
        }
    }

    public int top() {
        long top = stack.peek();
        if (top > 0) {
            return (int) (top + min);
        } else {
            return (int) min;
        }
    }

    public int getMin() {
        return (int) min;
    }
}
