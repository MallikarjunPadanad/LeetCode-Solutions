class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > 9 * num) return "";

        char[] res = new char[num];
        java.util.Arrays.fill(res, '0');

        int i = 0;
        while (sum >= 9) {
            res[i++] = '9';
            sum -= 9;
        }
        if (sum > 0) res[i] = (char) ('0' + sum);

        return new String(res);
    }
}