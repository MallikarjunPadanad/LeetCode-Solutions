class Solution {
    public long minOperations(int[] nums, int x, int k) {
        int n = nums.length;
        long[] cost = windowCosts(nums, x);

        final long INF = Long.MAX_VALUE / 2;
        long[] prev = new long[n + 1]; // 0 windows chosen: cost 0 everywhere
        for (int j = 0; j < k; j++) {
            long[] cur = new long[n + 1];
            Arrays.fill(cur, 0, x, INF); // can't fit j+1 windows in fewer than x elements
            for (int i = x; i <= n; i++)
                cur[i] = Math.min(cur[i - 1], prev[i - x] + cost[i - x]);
            prev = cur;
        }
        return prev[n];
    }

    // cost[i] = sum |a - median| over nums[i .. i+x-1]
    private static long[] windowCosts(int[] nums, int x) {
        int n = nums.length;
        Comparator<Integer> cmp = Comparator.<Integer>comparingInt(i -> nums[i]).thenComparingInt(i -> i);
        TreeSet<Integer> low = new TreeSet<>(cmp), high = new TreeSet<>(cmp); // |low| = ceil(size/2)
        long lowSum = 0, highSum = 0;
        long[] cost = new long[n - x + 1];

        for (int i = 0; i < n; i++) {
            if (!low.isEmpty() && cmp.compare(i, low.last()) > 0) {
                high.add(i);
                highSum += nums[i];
            } else {
                low.add(i);
                lowSum += nums[i];
            }

            int out = i - x;
            if (out >= 0) {
                if (low.remove(out))
                    lowSum -= nums[out];
                else {
                    high.remove(out);
                    highSum -= nums[out];
                }
            }

            int want = (low.size() + high.size() + 1) / 2;
            while (low.size() > want) {
                int m = low.pollLast();
                high.add(m);
                lowSum -= nums[m];
                highSum += nums[m];
            }
            while (low.size() < want) {
                int m = high.pollFirst();
                low.add(m);
                highSum -= nums[m];
                lowSum += nums[m];
            }

            if (i >= x - 1) {
                long c = highSum - lowSum;
                if ((x & 1) == 1)
                    c += nums[low.last()]; // median counted in lowSum; remove its double-count
                cost[i - x + 1] = c;
            }
        }
        return cost;
    }
}