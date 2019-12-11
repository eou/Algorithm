// 353. Design Snake Game
class SnakeGame {
    private boolean gameOver;
    private int[][] food;
    private int width;
    private int height;
    private int score;
    // 2d place mapping to 1d place
    private Set<Integer> body;
    private Deque<Integer> queue;
    public SnakeGame(int width, int height, int[][] food) {
        this.width = width;
        this.height = height;
        this.food = food;
        
        this.gameOver = false;
        this.score = 0;

        this.queue = new ArrayDeque<>();
        this.queue.offer(0 * width + 0);
        this.body = new HashSet<>();
        this.body.add(0 * width + 0);
        
        // first place contain a food
        if(food.length > 0 && food[score][0] == 0 && food[score][1] == 0) {
            score++;
        }
    }
    
    public int move(String direction) {
        if(gameOver) {
            return -1;
        }
        
        int dx = 0;
        int dy = 0;
        switch (direction) {
            case "U": dx = -1; dy = 0; break;
            case "L": dx = 0; dy = -1; break;
            case "R": dx = 0; dy = 1; break;
            case "D": dx = 1; dy = 0; break;
            default: break;
        }
        
        // snake's head next position
        int x = queue.peekLast() / width + dx;
        int y = queue.peekLast() % width + dy;
        if(x >= 0 && x < height && y >= 0 && y < width) {
            // head is the last element of queue
            queue.offer(x * width + y);
            if(score < food.length && x == food[score][0] && y == food[score][1]) {
                score++;
            } else {
                // not increase body, remove tail of body
                body.remove(queue.peek());
                queue.poll();
            }
            
            // bite itself
            if(body.contains(x * width + y)) {
                gameOver = true;
                return -1;
            } else {
                body.add(x * width + y);
            }
            
            return score;
        } else {
            // collides with border
            gameOver = true;
            return -1;
        }
    }
}

/**
 * Your SnakeGame object will be instantiated and called as such:
 * SnakeGame obj = new SnakeGame(width, height, food);
 * int param_1 = obj.move(direction);
 */