class Solution {
    public String lexGreaterPermutation(String s, String target) {
        int[] freq = new int[26];
        for (char c : s.toCharArray()) freq[c - 'a']++;

        String answer = build(freq, target, 0, new StringBuilder());
        return answer == null ? "" : answer;
    }

    private String build(int[] freq, String target, int index, StringBuilder prefix) {
        if (index == target.length()) return null;

        char targetChar = target.charAt(index);

        if (freq[targetChar - 'a'] > 0) {
            freq[targetChar - 'a']--;
            prefix.append(targetChar);

            String result = build(freq, target, index + 1, prefix);
            if (result != null) return result;

            prefix.deleteCharAt(prefix.length() - 1);
            freq[targetChar - 'a']++;
        }

        for (char c = (char) (targetChar + 1); c <= 'z'; c++) {
            if (freq[c - 'a'] > 0) {
                freq[c - 'a']--;
                prefix.append(c);

                for (char rem = 'a'; rem <= 'z'; rem++) {
                    for (int k = 0; k < freq[rem - 'a']; k++) {
                        prefix.append(rem);
                    }
                }

                return prefix.toString();
            }
        }

        return null;
    }
}