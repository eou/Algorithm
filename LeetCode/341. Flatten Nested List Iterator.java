// 341. Flatten Nested List Iterator
public class NestedIterator implements Iterator<Integer> {
    // 考察用双端队列 Double-ended queue
    Deque<NestedInteger> stack;

    public NestedIterator(List<NestedInteger> nestedList) {
        stack = new ArrayDeque<>();
        for (NestedInteger n : nestedList) {
            // 这里用到了双端队列特性，其实栈当做队列不太好，应该从尾部开始遍历List
            stack.offer(n);
        }
    }

    @Override
    public Integer next() {
        return stack.pop().getInteger(); // 注意要getInteger()
    }

    @Override
    public boolean hasNext() {
        while (!stack.isEmpty()) {
            if (stack.peek().isInteger()) {
                return true;
            } else {
                List<NestedInteger> list = stack.pop().getList();
                // stack
                for (int i = list.size() - 1; i >= 0; --i) {
                    stack.push(list.get(i));
                }
            }
        }
        return false;
    }
}

public class NestedIterator implements Iterator<Integer> {
    // 辅助List用递归预处理，这个预处理可能不是面试考察点
    List<Integer> list;
    public NestedIterator(List<NestedInteger> nestedList) {
        list = new ArrayList<>();
        flatten(nestedList);
    }
    
    private void flatten(List<NestedInteger> nestedList) {
        for (NestedInteger n : nestedList) {
            if (n.isInteger()) {
                list.add(n.getInteger());
            } else {
                flatten(n.getList());
            }
        }
    }

    @Override
    public Integer next() {
        Integer i = list.get(0);
        list.remove(0);
        return i;
    }

    @Override
    public boolean hasNext() {
        return !list.isEmpty();
    }
}