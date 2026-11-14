class Solution {
    public int minimumSumSubarray(List<Integer> nums, int l, int r) {
        int n = nums.size();
        int[] P = new int[n + 1];
        for (int i = 0; i < n; i++) P[i + 1] = P[i] + nums.get(i);

        TreeMap<Integer, Integer> window = new TreeMap<>();
        int ans = Integer.MAX_VALUE;

        for (int j = l; j <= n; j++) {
            // add newest valid left index
            window.merge(P[j - l], 1, Integer::sum);

            // remove the index that just fell out of range
            int old = j - r - 1;
            if (old >= 0) {
                window.merge(P[old], -1, Integer::sum);
                if (window.get(P[old]) == 0) window.remove(P[old]);
            }

            // largest prefix value strictly less than P[j]
            Integer lo = window.lowerKey(P[j]);
            if (lo != null) ans = Math.min(ans, P[j] - lo);
        }
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}