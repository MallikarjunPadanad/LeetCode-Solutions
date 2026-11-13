class Solution {
    public long countNonDecreasingSubarrays(int[] nums, int k) {
        int n = nums.length;
        long[] pre = new long[n + 1];
        for (int i = 0; i < n; i++)
            pre[i + 1] = pre[i] + nums[i];

        Deque<Integer> dq = new ArrayDeque<>(); // prefix-max record indices, front = l
        long cur = 0; // sum of prefixMax over window [l..r]
        int r = n - 1;
        long ans = 0;

        for (int l = n - 1; l >= 0; l--) {
            // nums[l] absorbs smaller-or-equal records on the left
            while (!dq.isEmpty() && nums[dq.peekFirst()] <= nums[l]) {
                int p = dq.pollFirst();
                int nxt = dq.isEmpty() ? r + 1 : dq.peekFirst();
                cur -= (long) nums[p] * (nxt - p);
            }
            int nxt = dq.isEmpty() ? r + 1 : dq.peekFirst();
            cur += (long) nums[l] * (nxt - l);
            dq.offerFirst(l);

            // shrink from the right while cost exceeds k
            while (cur - (pre[r + 1] - pre[l]) > k) {
                cur -= nums[dq.peekLast()]; // record covering position r
                if (dq.peekLast() == r)
                    dq.pollLast();
                r--;
            }

            ans += r - l + 1;
        }
        return ans;
    }
}