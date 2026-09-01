class Solution {
    public int minMoves(String[] classroom, int energy) {
        int rows = classroom.length;
        int cols = classroom[0].length();

        // Locate the start position and assign each litter ('L') a bit index.
        int startRow = 0, startCol = 0;
        int litterCount = 0;
        int[][] litterIndex = new int[rows][cols];
        for (int r = 0; r < rows; r++) {
            String row = classroom[r];
            for (int c = 0; c < cols; c++) {
                char cell = row.charAt(c);
                if (cell == 'S') {
                    startRow = r;
                    startCol = c;
                } else if (cell == 'L') {
                    litterIndex[r][c] = litterCount++;
                }
            }
        }

        // No litter to collect.
        if (litterCount == 0) {
            return 0;
        }

        int fullMask = (1 << litterCount) - 1;
        boolean[][][][] visited = new boolean[rows][cols][energy + 1][1 << litterCount];

        List<int[]> currentLevel = new ArrayList<>();
        currentLevel.add(new int[] {startRow, startCol, energy, fullMask});
        visited[startRow][startCol][energy][fullMask] = true;

        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, 1, 0, -1};

        int moves = 0;
        while (!currentLevel.isEmpty()) {
            List<int[]> nextLevel = new ArrayList<>();

            for (int[] state : currentLevel) {
                int r = state[0], c = state[1], curEnergy = state[2], mask = state[3];

                // All litter collected on entering this state.
                if (mask == 0) {
                    return moves;
                }
                // No energy left to move further.
                if (curEnergy <= 0) {
                    continue;
                }

                for (int dir = 0; dir < 4; dir++) {
                    int nr = r + dRow[dir];
                    int nc = c + dCol[dir];
                    if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                        continue;
                    }

                    char nextCell = classroom[nr].charAt(nc);
                    if (nextCell == 'X') {
                        continue;
                    }

                    int nextEnergy = (nextCell == 'R') ? energy : curEnergy - 1;
                    int nextMask = mask;
                    if (nextCell == 'L') {
                        nextMask &= ~(1 << litterIndex[nr][nc]);
                    }

                    if (!visited[nr][nc][nextEnergy][nextMask]) {
                        visited[nr][nc][nextEnergy][nextMask] = true;
                        nextLevel.add(new int[] {nr, nc, nextEnergy, nextMask});
                    }
                }
            }

            currentLevel = nextLevel;
            moves++;
        }

        return -1;
    }
}