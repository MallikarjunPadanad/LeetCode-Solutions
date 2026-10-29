class Solution {
    private int[] tin;
    private int[] tout;
    private int[] depth;
    private int[][] up;
    private int[] head;
    private int[] to;
    private int[] next;
    private int timer;
    private int log;
    private Fenwick bit;

    public List<Boolean> palindromePath(int n, int[][] edges,
                                        String s, String[] queries) {
        head = new int[n];
        Arrays.fill(head, -1);

        to = new int[2 * edges.length];
        next = new int[2 * edges.length];

        int idx = 0;

        for (int[] e : edges) {
            int u = e[0];
            int v = e[1];

            to[idx] = v;
            next[idx] = head[u];
            head[u] = idx++;

            to[idx] = u;
            next[idx] = head[v];
            head[v] = idx++;
        }

        log = 1;
        while ((1 << log) <= n) {
            log++;
        }

        tin = new int[n];
        tout = new int[n];
        depth = new int[n];
        up = new int[log][n];

        buildEulerTour(n);

        bit = new Fenwick(n + 1);
        char[] chars = s.toCharArray();

        for (int i = 0; i < n; i++) {
            int mask = 1 << (chars[i] - 'a');
            bit.update(tin[i], mask);
            bit.update(tout[i] + 1, mask);
        }

        List<Boolean> answer = new ArrayList<>();

        for (String query : queries) {
            String[] parts = query.split(" ");

            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);

                if (chars[u] == c) {
                    continue;
                }

                int mask = (1 << (chars[u] - 'a'))
                         ^ (1 << (c - 'a'));

                bit.update(tin[u], mask);
                bit.update(tout[u] + 1, mask);

                chars[u] = c;
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);

                int ancestor = lca(u, v);

                int mask = bit.query(tin[u])
                         ^ bit.query(tin[v])
                         ^ (1 << (chars[ancestor] - 'a'));

                answer.add((mask & (mask - 1)) == 0);
            }
        }

        return answer;
    }

    private void buildEulerTour(int n) {
        int[] parent = new int[n];
        int[] iterator = new int[n];
        int[] stack = new int[n];
        boolean[] entered = new boolean[n];

        Arrays.fill(parent, -1);

        int top = 0;
        stack[top++] = 0;
        parent[0] = 0;

        while (top > 0) {
            int u = stack[top - 1];

            if (!entered[u]) {
                entered[u] = true;
                tin[u] = ++timer;
                up[0][u] = parent[u];

                for (int j = 1; j < log; j++) {
                    up[j][u] = up[j - 1][up[j - 1][u]];
                }

                iterator[u] = head[u];
            }

            int edge = iterator[u];

            while (edge != -1 && to[edge] == parent[u]) {
                edge = next[edge];
            }

            iterator[u] = edge == -1 ? -1 : next[edge];

            if (edge == -1) {
                tout[u] = timer;
                top--;
            } else {
                int v = to[edge];

                parent[v] = u;
                depth[v] = depth[u] + 1;
                stack[top++] = v;
            }
        }
    }

    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int temp = u;
            u = v;
            v = temp;
        }

        int diff = depth[u] - depth[v];

        for (int j = 0; j < log; j++) {
            if ((diff & (1 << j)) != 0) {
                u = up[j][u];
            }
        }

        if (u == v) {
            return u;
        }

        for (int j = log - 1; j >= 0; j--) {
            if (up[j][u] != up[j][v]) {
                u = up[j][u];
                v = up[j][v];
            }
        }

        return up[0][u];
    }

    private static class Fenwick {
        private final int[] tree;
        private final int n;

        Fenwick(int n) {
            this.n = n;
            tree = new int[n + 1];
        }

        void update(int index, int mask) {
            for (int i = index; i <= n; i += i & -i) {
                tree[i] ^= mask;
            }
        }

        int query(int index) {
            int mask = 0;

            for (int i = index; i > 0; i -= i & -i) {
                mask ^= tree[i];
            }

            return mask;
        }
    }
}