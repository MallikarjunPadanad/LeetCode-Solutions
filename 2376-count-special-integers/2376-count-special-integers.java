class Solution {

    int[][][][] dp = new int[10][2][2][1024];

    int solve(int index, int tight, int started, int mask, String num) {

        // Base case
        if (index == num.length()) {
            return started == 1 ? 1 : 0;
        }

        // Already calculated
        if (dp[index][tight][started][mask] != -1) {
            return dp[index][tight][started][mask];
        }

        int limit = (tight == 1)
                ? num.charAt(index) - '0'
                : 9;

        int answer = 0;

        for (int digit = 0; digit <= limit; digit++) {

            // IMPORTANT:
            // Use original tight value, not limit
            int newTight = (tight == 1 && digit == limit) ? 1 : 0;

            // Leading zero
            if (started == 0 && digit == 0) {

                answer += solve(
                        index + 1,
                        newTight,
                        0,
                        mask,
                        num
                );

            } else {

                // Digit already used
                if ((mask & (1 << digit)) != 0) {
                    continue;
                }

                answer += solve(
                        index + 1,
                        newTight,
                        1,
                        mask | (1 << digit),
                        num
                );
            }
        }

        return dp[index][tight][started][mask] = answer;
    }

    public int countSpecialNumbers(int n) {

        String num = String.valueOf(n);

        // Fill DP with -1
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 2; j++) {
                for (int k = 0; k < 2; k++) {
                    java.util.Arrays.fill(dp[i][j][k], -1);
                }
            }
        }

        return solve(0, 1, 0, 0, num);
    }
}