class Solution {
    public int closedIsland(int[][] grid) {
        int m = grid.length, n = grid[0].length, count = 0;
        int[] stack = new int[m * n];
        int[] dirs = {1, 0, -1, 0, 1};

        for (int i = 1; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (grid[i][j] != 0) continue;

                boolean closed = true;
                int top = 0;
                stack[top++] = i * n + j;
                grid[i][j] = 1;

                while (top > 0) {
                    int cur = stack[--top];
                    int r = cur / n, c = cur % n;

                    if (r == 0 || c == 0 || r == m - 1 || c == n - 1) closed = false;

                    for (int d = 0; d < 4; d++) {
                        int nr = r + dirs[d], nc = c + dirs[d + 1];
                        if (nr >= 0 && nr < m && nc >= 0 && nc < n && grid[nr][nc] == 0) {
                            grid[nr][nc] = 1;
                            stack[top++] = nr * n + nc;
                        }
                    }
                }
                if (closed) count++;
            }
        }
        return count;
    }
}