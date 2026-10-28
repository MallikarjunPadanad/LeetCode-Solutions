class Solution {
    public int minCost(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        final int S = 1024;

        boolean[][] prev = new boolean[n][];
        for (int i = 0; i < m; i++) {
            boolean[][] cur = new boolean[n][];
            for (int j = 0; j < n; j++) {
                int v = grid[i][j];
                boolean[] reach = new boolean[S];

                if (i == 0 && j == 0) {
                    reach[v] = true;
                } else {
                    if (i > 0) {
                        boolean[] up = prev[j];
                        for (int x = 0; x < S; x++) {
                            if (up[x]) reach[x ^ v] = true;
                        }
                    }
                    if (j > 0) {
                        boolean[] left = cur[j - 1];
                        for (int x = 0; x < S; x++) {
                            if (left[x]) reach[x ^ v] = true;
                        }
                    }
                }
                cur[j] = reach;
            }
            prev = cur;
        }

        boolean[] last = prev[n - 1];
        for (int x = 0; x < S; x++) {
            if (last[x]) return x;
        }
        return -1;
    }
}