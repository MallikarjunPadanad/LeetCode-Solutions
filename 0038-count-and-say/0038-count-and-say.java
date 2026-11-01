class Solution {
    public String countAndSay(int n) {
        String s = "1";
        for (int step = 1; step < n; step++) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < s.length()) {
                char c = s.charAt(i);
                int j = i;
                while (j < s.length() && s.charAt(j) == c) {
                    j++;
                }
                sb.append(j - i).append(c);
                i = j;
            }
            s = sb.toString();
        }
        return s;
    }
}