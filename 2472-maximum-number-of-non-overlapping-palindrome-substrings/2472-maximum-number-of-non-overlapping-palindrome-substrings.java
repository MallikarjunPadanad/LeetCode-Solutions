class Solution {
    private int[] p;

    public int maxPalindromes(String s, int k) {
        int n = s.length();
        buildManacher(s);

        int ans = 0, start = 0;
        for (int r = k - 1; r < n; ++r) {
            int l = r - k + 1;
            if (l >= start && isPalin(l, r)) {
                ++ans;
                start = r + 1;
                continue;
            }
            l = r - k;
            if (l >= start && isPalin(l, r)) {
                ++ans;
                start = r + 1;
            }
        }
        return ans;
    }

    private void buildManacher(String s) {
        int n = s.length();
        int m = 2 * n + 1;
        char[] t = new char[m];
        t[0] = '#';
        for (int i = 0; i < n; i++) {
            t[2 * i + 1] = s.charAt(i);
            t[2 * i + 2] = '#';
        }

        p = new int[m];
        int center = 0, right = 0;
        for (int i = 0; i < m; i++) {
            if (i < right) {
                p[i] = Math.min(right - i, p[2 * center - i]);
            }
            while (i - p[i] - 1 >= 0 && i + p[i] + 1 < m && t[i - p[i] - 1] == t[i + p[i] + 1]) {
                p[i]++;
            }
            if (i + p[i] > right) {
                center = i;
                right = i + p[i];
            }
        }
    }

    private boolean isPalin(int l, int r) {
        return p[l + r + 1] >= r - l + 1;
    }
}