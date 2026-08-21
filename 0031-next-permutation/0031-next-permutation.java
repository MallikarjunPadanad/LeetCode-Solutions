class Solution {
    public void nextPermutation(int[] nums) {
        int length = nums.length;

        int pivotIndex = length - 2;

        while (pivotIndex >= 0 && nums[pivotIndex] >= nums[pivotIndex + 1]) {
            pivotIndex--;
        }

        if (pivotIndex >= 0) {
            int swapIndex = length - 1;

            while (nums[swapIndex] <= nums[pivotIndex]) {
                swapIndex--;
            }

            swap(nums, pivotIndex, swapIndex);
        }

        int left = pivotIndex + 1;
        int right = length - 1;

        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}