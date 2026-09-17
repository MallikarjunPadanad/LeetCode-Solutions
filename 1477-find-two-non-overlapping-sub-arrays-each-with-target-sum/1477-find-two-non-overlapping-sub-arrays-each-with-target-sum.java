
class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length, sum = 0, ans = Integer.MAX_VALUE;
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);

        for (int i = 1; i <= n; i++) {
            sum += arr[i - 1];
            dp[i] = dp[i - 1];

            if (map.containsKey(sum - target)) {
                int j = map.get(sum - target);
                int len = i - j;
                if (dp[j] != Integer.MAX_VALUE)
                    ans = Math.min(ans, dp[j] + len);
                dp[i] = Math.min(dp[i], len);
            }
            map.put(sum, i);
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}
