class Solution {
    private final Random rand = new Random();

    public int minMoves2(int[] nums) {
        int n = nums.length;
        int median = select(nums, 0, n - 1, n / 2);
        int moves = 0;
        for (int x : nums) {
            moves += Math.abs(x - median);
        }
        return moves;
    }

    private int select(int[] nums, int lo, int hi, int k) {
        while (lo < hi) {
            int pivot = nums[lo + rand.nextInt(hi - lo + 1)];

            // 3-way partition: [lo..lt-1] < pivot, [lt..gt] == pivot, [gt+1..hi] > pivot
            int lt = lo, i = lo, gt = hi;
            while (i <= gt) {
                if (nums[i] < pivot) {
                    swap(nums, lt++, i++);
                } else if (nums[i] > pivot) {
                    swap(nums, i, gt--);
                } else {
                    i++;
                }
            }

            if (k < lt) {
                hi = lt - 1;
            } else if (k > gt) {
                lo = gt + 1;
            } else {
                return nums[k];
            }
        }
        return nums[lo];
    }

    private void swap(int[] nums, int a, int b) {
        int tmp = nums[a];
        nums[a] = nums[b];
        nums[b] = tmp;
    }
}