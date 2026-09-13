class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        long[] A = new long[n];
        long[] B = new long[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) A[i] |= 1L << j;
                if (img2[i][j] == 1) B[i] |= 1L << j;
            }
        }

        int best = 0;

        for (int dy = -n + 1; dy < n; dy++) {
            long[] shiftedB = shiftCols(B, dy, n);
            for (int dx = -n + 1; dx < n; dx++) {
                int overlap = 0;
                for (int i = 0; i < n; i++) {
                    int j = i + dx;
                    if (j >= 0 && j < n) {
                        overlap += Long.bitCount(A[i] & shiftedB[j]);
                    }
                }
                best = Math.max(best, overlap);
            }
        }

        return best;
    }

    private long[] shiftCols(long[] rows, int shift, int n) {
        long[] result = new long[n];
        for (int i = 0; i < n; i++) {
            result[i] = shift >= 0 ? rows[i] << shift : rows[i] >>> -shift;
        }
        return result;
    }
}