class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        int[] A = new int[n];
        int[] B = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) A[i] |= 1 << j;
                if (img2[i][j] == 1) B[i] |= 1 << j;
            }
        }

        int best = 0;

        for (int dy = -n + 1; dy < n; dy++) {
            for (int dx = -n + 1; dx < n; dx++) {
                int overlap = 0;
                for (int i = Math.max(0, -dx); i < Math.min(n, n - dx); i++) {
                    int shifted = dy >= 0 ? (B[i + dx] << dy) : (B[i + dx] >>> -dy);
                    overlap += Integer.bitCount(A[i] & shifted);
                }
                best = Math.max(best, overlap);
            }
        }

        return best;
    }
}