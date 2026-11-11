class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long c1 = cost1, c2 = cost2, cb = costBoth;
        long n1 = need1, n2 = need2;

        long m = Math.min(n1, n2);
        long M = Math.max(n1, n2);

        long onlySingles = n1 * c1 + n2 * c2;                      // k = 0
        long mixed       = m * cb + (n1 - m) * c1 + (n2 - m) * c2; // k = min
        long onlyBoth    = M * cb;                                 // k = max

        return Math.min(onlySingles, Math.min(mixed, onlyBoth));
    }
}