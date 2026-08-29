class Solution {
    public double myPow(double x, int n) {
        long exponent = n;
        if (exponent < 0) {
            x = 1 / x;
            exponent = -exponent;
        }
        return power(x, exponent, 1);
    }

    double power(double x, long n, double ans) {
        if (n == 0) {
            return ans;
        }
        if (n % 2 != 0) {
            ans *= x;
        }
        return power(x * x, n / 2, ans);
    }
}