class Solution {
    public int minMoves(int[] nums) {
        int max = 0;
        long sum = 0;
        for (int x : nums) {
            max = Math.max(max, x);
            sum += x;
        }
        return (int) ((long) max * nums.length - sum);
    }
}