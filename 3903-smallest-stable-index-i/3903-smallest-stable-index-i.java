class Solution {
    public int firstStableIndex(int[] nums, int k) {
        int n = nums.length; 
        int p[] = new int[n]; // max 
        int s[] = new int[n]; // min 
        p[0] = nums[0]; 
        s[n - 1] = nums[n - 1]; 
        int mx = 0; 
        for(int i = 1; i < n; i++) {
            p[i] = Math.max(p[i-1], nums[i]); 
            s[n - i - 1] = Math.min(s[n - i], nums[n - i - 1]); 
        }

        // smallest stable index
        for(int i = 0; i < n; i++) {
            if(p[i] - s[i] <= k) {
                return i; 
            }
        }
        return -1 ;
    }
}