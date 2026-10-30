class Solution {
    public int shortestPath(int n, int[][] edges, String s, int k) {
        List<int[]>[] graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
        }

        long INF = Long.MAX_VALUE / 2;
        // dist[node][run], with run in 1..k
        long[][] dist = new long[n][k + 1];
        for (long[] row : dist) Arrays.fill(row, INF);
        dist[0][1] = 0;

        // {distance, node, run}
        PriorityQueue<long[]> pq = new PriorityQueue<>((a, b) -> Long.compare(a[0], b[0]));
        pq.offer(new long[]{0, 0, 1});

        while (!pq.isEmpty()) {
            long[] cur = pq.poll();
            long d = cur[0];
            int u = (int) cur[1];
            int run = (int) cur[2];

            if (d > dist[u][run]) continue;
            if (u == n - 1) return (int) d; // first pop of the target is optimal

            for (int[] edge : graph[u]) {
                int v = edge[0];
                int w = edge[1];
                int nrun = (s.charAt(v) == s.charAt(u)) ? run + 1 : 1;
                if (nrun > k) continue;

                long nd = d + w;
                if (nd < dist[v][nrun]) {
                    dist[v][nrun] = nd;
                    pq.offer(new long[]{nd, v, nrun});
                }
            }
        }
        return -1;
    }
}