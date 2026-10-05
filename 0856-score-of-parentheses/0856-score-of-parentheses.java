class Solution {
    public int scoreOfParentheses(String s) {
        int score = 0;
        int depth = 0;
        boolean open = false;

        for (char c : s.toCharArray()) {
            if (c == '(') {
                depth++;
                open = true;
            } else {
                depth--;
                if (open) {
                    score += 1 << depth;
                }
                open = false;
            }
        }

        return score;
    }
}