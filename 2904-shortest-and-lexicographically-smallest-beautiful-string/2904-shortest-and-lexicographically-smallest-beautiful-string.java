class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        String best = "";
        int minLength = Integer.MAX_VALUE;
        int left = 0;
        int onesCount = 0;

        for (int right = 0; right < n; right++) {
            if (s.charAt(right) == '1') {
                onesCount++;
            }

            // Shrink from the left while we have more ones than needed
            while (onesCount > k) {
                if (s.charAt(left) == '1') {
                    onesCount--;
                }
                left++;
            }

            // Trim leading zeros; they don't affect the ones count
            int trimmedLeft = left;
            while (trimmedLeft < right && s.charAt(trimmedLeft) == '0') {
                trimmedLeft++;
            }

            if (onesCount == k) {
                int currentLength = right - trimmedLeft + 1;
                if (currentLength < minLength) {
                    minLength = currentLength;
                    best = s.substring(trimmedLeft, right + 1);
                } else if (currentLength == minLength) {
                    String candidate = s.substring(trimmedLeft, right + 1);
                    if (candidate.compareTo(best) < 0) {
                        best = candidate;
                    }
                }
            }
        }

        return best;
    }
}