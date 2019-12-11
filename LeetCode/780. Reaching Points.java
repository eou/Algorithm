// 780. Reaching Points
// Math problem
// Stack overflow
class Solution {
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        if (sx > tx || sy > ty) {
            return false;
        }

        return dfs(sx, sy, tx, ty);
    }
    
    public boolean dfs(int curx, int cury, int tx, int ty) {
        if (curx == tx && cury == ty) {
            return true;
        }
        
        boolean res = false;
        if (curx < tx) {
            if (curx + cury <= tx) {
                res = res || dfs(curx + cury, cury, tx, ty);
            }
        }
        
        if (cury < ty) {
            if (curx + cury <= ty) {
                res = res || dfs(curx, curx + cury, tx, ty);
            }
        }
        
        return res;
    }
}

class Solution {
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
         /*
                    (sx, sy)
                     /     \
          (sx + sy, sy)  (sx, sx + sy)  
            / \               / \
            ....
            
            we can start from tx, ty, try to find a path back to sx, sy
            since there are many ways start from sx, sy
            but there are only one way go back from tx,ty to its parent node
         */
        while (tx > sx && ty > sy) {
            if (tx > ty) {
                tx = tx - ty;
            } else {
                ty = ty - tx;
            }
        }
        
        if (tx < sx || ty < sy) {
            return false;
        }
        
        while (tx > sx) {
            tx = tx - ty;
        }
        while (ty > sy) {
            ty = ty - tx;
        }
    
        return tx == sx && ty == sy;
    }
}

// optimize
class Solution {
    public boolean reachingPoints(int sx, int sy, int tx, int ty) {
        while (tx > sx && ty > sy) {
            if (tx > ty) {
                tx = tx % ty;
            } else {
                ty = ty % tx;
            }
        }
        
        if (tx < sx || ty < sy) {
            return false;
        }
        
        if (tx > sx) {
            return (tx - sx) % ty == 0;
        }
        if (ty > sy) {
            return (ty - sy) % tx == 0;
        }
    
        return tx == sx && ty == sy;
    }
}