class Solution {
    public int countIslands(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int count = 0;

        boolean[][] visited = new boolean[m][n];
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 || visited[i][j]) {
                    continue;
                }

                long sum = 0;
                visited[i][j] = true;
                stack.push(i * n + j);

                while (!stack.isEmpty()) {
                    int cell = stack.pop();
                    int r = cell / n;
                    int c = cell % n;

                    sum += grid[r][c];

                    for (int d = 0; d < 4; d++) {
                        int nr = r + dr[d];
                        int nc = c + dc[d];

                        if (nr >= 0 && nr < m &&
                            nc >= 0 && nc < n &&
                            grid[nr][nc] > 0 &&
                            !visited[nr][nc]) {

                            visited[nr][nc] = true;
                            stack.push(nr * n + nc);
                        }
                    }
                }

                if (sum % k == 0) {
                    count++;
                }
            }
        }

        return count;
    }
}
