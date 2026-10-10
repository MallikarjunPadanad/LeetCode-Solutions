class Solution {
    public List<Integer> findAllPeople(int n, int[][] meetings, int firstPerson) {
        Arrays.sort(meetings, (a, b) -> Integer.compare(a[2], b[2]));

        int[] parent = new int[n];
        int[] rank = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }

        union(parent, rank, 0, firstPerson);

        int i = 0;

        while (i < meetings.length) {
            int time = meetings[i][2];
            int j = i;

            while (j < meetings.length && meetings[j][2] == time) {
                union(parent, rank, meetings[j][0], meetings[j][1]);
                j++;
            }

            for (int k = i; k < j; k++) {
                int person = meetings[k][0];

                if (find(parent, person) != find(parent, 0)) {
                    parent[person] = person;
                    rank[person] = 0;
                }

                person = meetings[k][1];

                if (find(parent, person) != find(parent, 0)) {
                    parent[person] = person;
                    rank[person] = 0;
                }
            }

            i = j;
        }

        List<Integer> result = new ArrayList<>();

        for (int person = 0; person < n; person++) {
            if (find(parent, person) == find(parent, 0)) {
                result.add(person);
            }
        }

        return result;
    }

    private int find(int[] parent, int x) {
        if (parent[x] != x) {
            parent[x] = find(parent, parent[x]);
        }
        return parent[x];
    }

    private void union(int[] parent, int[] rank, int a, int b) {
        int rootA = find(parent, a);
        int rootB = find(parent, b);

        if (rootA == rootB) {
            return;
        }

        if (rank[rootA] < rank[rootB]) {
            parent[rootA] = rootB;
        } else if (rank[rootA] > rank[rootB]) {
            parent[rootB] = rootA;
        } else {
            parent[rootB] = rootA;
            rank[rootA]++;
        }
    }
}
