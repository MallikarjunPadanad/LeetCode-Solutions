class Solution {
    public int countSpecialNumbers(int n) {
        String s = String.valueOf(n);
        int len = s.length();
        int ans = 0;

        // Count numbers with fewer digits
        for (int digits = 1; digits < len; digits++) {
            ans += 9 * permutation(9, digits - 1);
        }

        // Count numbers with same number of digits
        boolean[] used = new boolean[10];

        for (int i = 0; i < len; i++) {
            int digit = s.charAt(i) - '0';

            // Try digits smaller than current digit
            for (int d = (i == 0 ? 1 : 0); d < digit; d++) {
                if (!used[d]) {
                    ans += permutation(10 - i - 1, len - i - 1);
                }
            }

            // If current digit is already used, stop
            if (used[digit]) {
                return ans;
            }

            used[digit] = true;
        }

        // n itself has unique digits
        return ans + 1;
    }

    private int permutation(int available, int positions) {
        int result = 1;

        for (int i = 0; i < positions; i++) {
            result *= available - i;
        }

        return result;
    }
}