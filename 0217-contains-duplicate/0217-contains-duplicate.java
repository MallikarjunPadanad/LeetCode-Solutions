class Solution {
    public boolean containsDuplicate(int[] nums) {
       Set<Integer> set = new HashSet<>((int)(nums.length / 0.75f) + 1);

        for (int num : nums) {
            if (!set.add(num)) return true;
        }

        return false;
    }
}