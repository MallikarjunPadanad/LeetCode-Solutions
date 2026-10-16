class Solution {
    static final int MOD = 1_000_000_007;

    long[] sum, squares, lazy;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        sum = new long[4 * n];
        squares = new long[4 * n];
        lazy = new long[4 * n];

        int[] last = new int[100001];
        long ans = 0;

        for (int i = 0; i < n; i++) {
            int left = last[nums[i]];
            update(1, 0, n - 1, left, i);
            last[nums[i]] = i + 1;
            ans = (ans + squares[1]) % MOD;
        }

        return (int) ans;
    }

    private void update(int node, int start, int end, int l, int r) {
        if (l > end || r < start) return;

        if (l <= start && end <= r) {
            long len = end - start + 1L;
            squares[node] = (squares[node] + 2 * sum[node] + len) % MOD;
            sum[node] = (sum[node] + len) % MOD;
            lazy[node]++;
            return;
        }

        push(node, start, end);

        int mid = (start + end) / 2;
        update(node * 2, start, mid, l, r);
        update(node * 2 + 1, mid + 1, end, l, r);

        sum[node] = (sum[node * 2] + sum[node * 2 + 1]) % MOD;
        squares[node] = (squares[node * 2] + squares[node * 2 + 1]) % MOD;
    }

    private void push(int node, int start, int end) {
        if (lazy[node] == 0 || start == end) return;

        int mid = (start + end) / 2;
        apply(node * 2, mid - start + 1L, lazy[node]);
        apply(node * 2 + 1, end - mid, lazy[node]);

        lazy[node] = 0;
    }

    private void apply(int node, long len, long x) {
        squares[node] = (squares[node] + 2 * x % MOD * sum[node] % MOD
                         + len * x % MOD * x % MOD) % MOD;
        sum[node] = (sum[node] + len * x) % MOD;
        lazy[node] = (lazy[node] + x) % MOD;
    }
}