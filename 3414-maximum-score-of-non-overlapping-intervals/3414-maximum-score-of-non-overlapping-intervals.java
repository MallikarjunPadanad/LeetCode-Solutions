class Solution {

    private static final int[] EMPTY = new int[0];

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        long[][] arr = new long[n][4];
        for (int i = 0; i < n; i++) {
            List<Integer> iv = intervals.get(i);
            arr[i][0] = iv.get(0);
            arr[i][1] = iv.get(1);
            arr[i][2] = iv.get(2);
            arr[i][3] = i;
        }
        Arrays.sort(arr, (a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));

        int[] next = new int[n];
        for (int i = 0; i < n; i++) {
            int lo = i + 1, hi = n;
            long endI = arr[i][1];
            while (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (arr[mid][0] > endI)
                    hi = mid;
                else
                    lo = mid + 1;
            }
            next[i] = lo;
        }

        long[][] score = new long[5][n + 1];
        int[][][] ids = new int[5][n + 1][];
        for (int c = 0; c <= 4; c++)
            ids[c][n] = EMPTY;

        for (int i = n - 1; i >= 0; i--) {
            ids[0][i] = EMPTY;
            for (int c = 1; c <= 4; c++) {
                long skipScore = score[c][i + 1];
                int[] skipIds = ids[c][i + 1];

                int nxt = next[i];
                long takeScore = arr[i][2] + score[c - 1][nxt];

                int cmp = Long.compare(takeScore, skipScore);
                if (cmp > 0 || (cmp == 0 && lexSmaller(insertSorted(ids[c - 1][nxt], (int) arr[i][3]), skipIds))) {
                    score[c][i] = takeScore;
                    ids[c][i] = insertSorted(ids[c - 1][nxt], (int) arr[i][3]);
                } else {
                    score[c][i] = skipScore;
                    ids[c][i] = skipIds;
                }
            }
        }
        return ids[4][0];
    }

    private boolean lexSmaller(int[] a, int[] b) {
        int len = Math.min(a.length, b.length);
        for (int i = 0; i < len; i++)
            if (a[i] != b[i])
                return a[i] < b[i];
        return a.length < b.length;
    }

    private int[] insertSorted(int[] arr, int value) {
        int[] r = new int[arr.length + 1];
        int i = 0;
        while (i < arr.length && arr[i] < value)
            r[i] = arr[i++];
        r[i] = value;
        while (i < arr.length)
            r[i + 1] = arr[i++];
        return r;
    }
}