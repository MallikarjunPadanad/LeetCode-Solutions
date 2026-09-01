class Solution {
    public int maximumCount(int[] nums) {
        int n = nums.length;
        int lo = 0, hi = n;
        int lo2 = 0, hi2 = n;

        while (lo < hi || lo2 < hi2) {
            if (lo < hi) {
                int mid = (lo + hi) >>> 1;
                if (nums[mid] < 0) {
                    lo = mid + 1;
                } else {
                    hi = mid;
                }
            }

            if (lo2 < hi2) {
                int mid = (lo2 + hi2) >>> 1;
                if (nums[mid] <= 0) {
                    lo2 = mid + 1;
                } else {
                    hi2 = mid;
                }
            }
        }

        return Math.max(lo, n - lo2);
    }
}

