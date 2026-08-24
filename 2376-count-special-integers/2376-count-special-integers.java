import java.util.*;

class Solution {

    int[][][] dp;
    String num;

    int solve(int index, int tight, int mask) {

        if (index == num.length()) {
            return mask == 0 ? 0 : 1;
        }

        if (dp[index][tight][mask] != -1) {
            return dp[index][tight][mask];
        }

        int limit = tight == 1
                ? num.charAt(index) - '0'
                : 9;

        int ans = 0;

        for (int digit = 0; digit <= limit; digit++) {

            int newTight =
                    (tight == 1 && digit == limit) ? 1 : 0;

            // Leading zero
            if (mask == 0 && digit == 0) {
                ans += solve(index + 1, newTight, 0);
            }
            else {

                // Digit already used
                if ((mask & (1 << digit)) != 0) {
                    continue;
                }

                ans += solve(
                        index + 1,
                        newTight,
                        mask | (1 << digit)
                );
            }
        }

        return dp[index][tight][mask] = ans;
    }

    public int countSpecialNumbers(int n) {

        num = String.valueOf(n);

        dp = new int[num.length()][2][1024];

        for (int i = 0; i < num.length(); i++) {
            for (int j = 0; j < 2; j++) {
                Arrays.fill(dp[i][j], -1);
            }
        }

        return solve(0, 1, 0);
    }
}