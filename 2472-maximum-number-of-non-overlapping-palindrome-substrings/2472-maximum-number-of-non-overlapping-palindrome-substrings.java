class Solution {
    private boolean[][] isPalin;
    private int[] memo;
    private int n, k;

    public int maxPalindromes(String s, int k) {
        n = s.length();
        this.k = k;
        isPalin = new boolean[n][n];
        memo = new int[n];
        Arrays.fill(memo, -1);

        for (int i = 0; i < n; i++) isPalin[i][i] = true;
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                isPalin[i][j] = s.charAt(i) == s.charAt(j) && (j - i < 2 || isPalin[i + 1][j - 1]);
            }
        }
        return dfs(0, s);
    }

    private int dfs(int i, String s) {
        if (i >= n) return 0;
        if (memo[i] != -1) return memo[i];
        int res = dfs(i + 1, s);
        for (int j = i + k - 1; j < n; j++) {
            if (isPalin[i][j]) {
                res = Math.max(res, 1 + dfs(j + 1, s));
                break;
            }
        }
        return memo[i] = res;
    }
}