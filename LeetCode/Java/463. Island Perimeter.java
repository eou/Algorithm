// 463. Island Perimeter
class Solution {
    public int islandPerimeter(int[][] grid) {
        if(grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int perimeter = 0;
        for(int i = 0; i < grid.length; i++) {
            for(int j = 0; j < grid[0].length; j++) {
                if(grid[i][j] == 1) {
                    if(i == 0 || grid[i - 1][j] == 0) {
                        perimeter++;
                    }
                    if(i == grid.length - 1 || grid[i + 1][j] == 0) {
                        perimeter++;
                    }
                    if(j == 0 || grid[i][j - 1] == 0) {
                        perimeter++;
                    }
                    if(j == grid[0].length - 1 || grid[i][j + 1] == 0) {
                        perimeter++;
                    }
                }
            }
        }
        
        return perimeter;
    }
}

class Solution {
    public int islandPerimeter(int[][] grid) {
        int islands = 0, neighbours = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    islands++;
                    if(i > 0 && grid[i - 1][j] == 1) {
                        neighbours++;
                    }
                    if(i < grid.length - 1 && grid[i + 1][j] == 1) {
                        neighbours++;
                    }
                    if(j > 0 && grid[i][j - 1] == 1) {
                        neighbours++;
                    }
                    if(j < grid[0].length - 1 && grid[i][j + 1] == 1) {
                        neighbours++;
                    }
                }
            }
        }

        return islands * 4 - neighbours;
    }
}

class Solution {
    // 其实只需算一半的邻居，两个方向即可
    public int islandPerimeter(int[][] grid) {
        int islands = 0, neighbours = 0;

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    islands++;
                    if (i < grid.length - 1 && grid[i + 1][j] == 1) {
                        neighbours++;
                    }
                    if (j < grid[i].length - 1 && grid[i][j + 1] == 1) {
                        neighbours++;
                    }
                }
            }
        }

        return islands * 4 - neighbours * 2;
    }
}