import java.util.Arrays;

public class Solution {

    private int[] freq = new int[26];
    private int n, half;
    private int oddIdx = -1;
    private byte[] dp;
    private char[] targetArr;

    public String lexPalindromicPermutation(String s, String target) {
        n = s.length();
        half = n >> 1;
        targetArr = target.toCharArray();

        dp = new byte[half];
        Arrays.fill(dp, (byte) -1);
        Arrays.fill(freq, 0);

        int par = 0, hasC = 0;
        for (int i = 0; i < n; i++) {
            int c = s.charAt(i) - 'a';
            freq[c]++;
            hasC |= 1 << c;
            par ^= 1 << c;
        }
        if (Integer.bitCount(par) > 1) return "";

        if ((n & 1) != 0) oddIdx = Integer.numberOfTrailingZeros(par);

        for (int mask = hasC; mask != 0; mask &= mask - 1) {
            int i = Integer.numberOfTrailingZeros(mask);
            freq[i] >>= 1;
            if (freq[i] == 0) hasC &= ~(1 << i);
        }

        StringBuilder ans = new StringBuilder(half);
        for (int i = 0; i < half; i++) {
            int ti = targetArr[i] - 'a';

            if (freq[ti] > 0 && canPlace(i, hasC)) {
                ans.append(targetArr[i]);
                if (--freq[ti] == 0) hasC &= ~(1 << ti);
            } else {
                int higher = hasC >>> (ti + 1);
                if (higher == 0) return "";

                int choice = Integer.numberOfTrailingZeros(higher) + ti + 1;
                if (--freq[choice] == 0) hasC &= ~(1 << choice);
                ans.append((char) ('a' + choice));

                for (int j = i + 1; j < half; j++) {
                    int idx = Integer.numberOfTrailingZeros(hasC);
                    ans.append((char) ('a' + idx));
                    if (--freq[idx] == 0) hasC &= ~(1 << idx);
                }
                return buildPalindrome(ans);
            }
        }

        String pal = buildPalindrome(ans);
        return pal.compareTo(target) > 0 ? pal : "";
    }

    private boolean canPlace(int i, int hasC) {
        if (dp[i] != -1) return dp[i] == 1;

        int c = targetArr[i] - 'a';
        if (freq[c] == 0) return false;

        int hasC1 = hasC;
        if (--freq[c] == 0) hasC1 &= ~(1 << c);

        boolean ans;
        if (i == half - 1) {
            if ((n & 1) != 0) {
                char mid = (char) ('a' + oddIdx);
                ans = (mid != targetArr[half]) ? (mid > targetArr[half]) : revLeftIsGrR();
            } else {
                ans = revLeftIsGrR();
            }
        } else {
            int nxt = targetArr[i + 1] - 'a';
            if ((hasC1 >>> (nxt + 1)) != 0) {
                ans = true;
            } else if (((hasC1 >>> nxt) & 1) == 0) {
                ans = false;
            } else {
                ans = canPlace(i + 1, hasC1);
            }
        }

        freq[c]++;
        dp[i] = (byte) (ans ? 1 : 0);
        return ans;
    }

    private boolean revLeftIsGrR() {
        for (int i = 0; i < half; i++) {
            char left = targetArr[half - 1 - i];
            char right = targetArr[half + (n & 1) + i];
            if (left > right) return true;
            if (left < right) return false;
        }
        return false;
    }

    private String buildPalindrome(StringBuilder ans) {
        int len = ans.length();
        StringBuilder pal = new StringBuilder(n);
        pal.append(ans);
        if ((n & 1) != 0) pal.append((char) ('a' + oddIdx));
        for (int i = len - 1; i >= 0; i--) pal.append(ans.charAt(i));
        return pal.toString();
    }
}