class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        List<int[]> A = new ArrayList<>();
        List<int[]> B = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) A.add(new int[]{i, j});
                if (img2[i][j] == 1) B.add(new int[]{i, j});
            }
        }

        Map<Integer, Integer> shiftCount = new HashMap<>();
        int best = 0;

        for (int[] a : A) {
            for (int[] b : B) {
                int key = (a[0] - b[0]) * 200 + (a[1] - b[1]);
                int c = shiftCount.merge(key, 1, Integer::sum);
                best = Math.max(best, c);
            }
        }

        return best;
    }
}