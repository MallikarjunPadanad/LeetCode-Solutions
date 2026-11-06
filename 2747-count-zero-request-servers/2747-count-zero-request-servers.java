class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));

        int m = queries.length;
        int[] result = new int[m];
        int[] freq = new int[n + 1];

        Integer[] indices = new Integer[m];

        for (int i = 0; i < m; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, (a, b) ->
            Integer.compare(queries[a], queries[b])
        );

        int left = 0;
        int right = 0;
        int activeServers = 0;

        for (int idx : indices) {
            int q = queries[idx];
            int start = q - x;

            while (right < logs.length && logs[right][1] <= q) {
                int server = logs[right][0];

                if (freq[server] == 0) {
                    activeServers++;
                }

                freq[server]++;
                right++;
            }

            while (left < right && logs[left][1] < start) {
                int server = logs[left][0];

                freq[server]--;

                if (freq[server] == 0) {
                    activeServers--;
                }

                left++;
            }

            result[idx] = n - activeServers;
        }

        return result;
    }
}
