class Solution {

    public int distinctSubseqII(String s) {

        final long MOD = 1_000_000_007L;

        long total = 1;

        long[] last = new long[26];

        for (char c : s.toCharArray()) {

            int index = c - 'a';

            long newTotal = (2 * total - last[index]) % MOD;

            if (newTotal < 0) {
                newTotal += MOD;
            }

            last[index] = total;

            total = newTotal;
        }

        // Remove empty subsequence
        return (int) ((total - 1 + MOD) % MOD);
    }
}