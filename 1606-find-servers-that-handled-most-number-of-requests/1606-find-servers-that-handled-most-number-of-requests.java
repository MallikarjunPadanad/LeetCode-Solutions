class Solution {
    public List<Integer> busiestServers(int k, int[] arrival, int[] load) {
        TreeSet<Integer> free = new TreeSet<>();
        for (int i = 0; i < k; i++) free.add(i);

        PriorityQueue<int[]> busy = new PriorityQueue<>((a, b) -> Integer.compare(a[0], b[0]));
        int[] count = new int[k];
        int max = 0;

        for (int i = 0; i < arrival.length; i++) {
            // Release servers that have finished by now
            while (!busy.isEmpty() && busy.peek()[0] <= arrival[i]) {
                free.add(busy.poll()[1]);
            }
            if (free.isEmpty()) continue;

            Integer id = free.ceiling(i % k);
            if (id == null) id = free.first();

            free.remove(id);
            busy.offer(new int[]{arrival[i] + load[i], id});
            max = Math.max(max, ++count[id]);
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            if (count[i] == max) res.add(i);
        }
        return res;
    }
}