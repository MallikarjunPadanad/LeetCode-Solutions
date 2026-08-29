class Solution {
  public int[] lexicographicallySmallestArray(int[] nums, int limit) {
    int n = nums.length;
    int[] ans = new int[n];
    Integer[] order = new Integer[n];
    for (int i = 0; i < n; ++i)
      order[i] = i;
    Arrays.sort(order, (a, b) -> nums[a] - nums[b]);

    int i = 0;
    while (i < n) {
      int j = i;
      while (j + 1 < n && nums[order[j + 1]] - nums[order[j]] <= limit)
        ++j;

      int size = j - i + 1;
      int[] sortedIndices = new int[size];
      for (int k = 0; k < size; ++k)
        sortedIndices[k] = order[i + k];
      Arrays.sort(sortedIndices);

      for (int k = 0; k < size; ++k)
        ans[sortedIndices[k]] = nums[order[i + k]];

      i = j + 1;
    }

    return ans;
  }
}