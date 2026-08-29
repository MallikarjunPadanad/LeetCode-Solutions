class Solution {
  public int[] lexicographicallySmallestArray(int[] nums, int limit) {
    int n = nums.length;
    int[] ans = new int[n];
    Pair<Integer, Integer>[] numAndIndexes = getNumAndIndexes(nums);

    int i = 0;
    while (i < n) {
      int j = i;
      while (j + 1 < n && numAndIndexes[j + 1].getKey() - numAndIndexes[j].getKey() <= limit)
        j++;

      List<Integer> sortedIndices = new ArrayList<>();
      for (int k = i; k <= j; k++)
        sortedIndices.add(numAndIndexes[k].getValue());
      Collections.sort(sortedIndices);

      for (int k = 0; k <= j - i; k++)
        ans[sortedIndices.get(k)] = numAndIndexes[i + k].getKey();

      i = j + 1;
    }

    return ans;
  }

  private Pair<Integer, Integer>[] getNumAndIndexes(int[] nums) {
    Pair<Integer, Integer>[] numAndIndexes = new Pair[nums.length];
    for (int i = 0; i < nums.length; ++i)
      numAndIndexes[i] = new Pair<>(nums[i], i);
    Arrays.sort(numAndIndexes, Comparator.comparingInt(Pair::getKey));
    return numAndIndexes;
  }
}