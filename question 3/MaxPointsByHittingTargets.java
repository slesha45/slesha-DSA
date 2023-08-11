// Question 3.a)
public class MaxPointsByHittingTargets {
    public static int maxPoints(int[] a) {
        int n = a.length;
        int[] paddedTargets = new int[n + 2];
        paddedTargets[0] = paddedTargets[n + 1] = 1;
        System.arraycopy(a, 0, paddedTargets, 1, n);

        int[][] dp = new int[n + 2][n + 2];

        // Dynamic programming approach to find maximum points
        for (int len = 1; len <= n; len++) {
            for (int left = 1; left <= n - len + 1; left++) {
                int right = left + len - 1;
                for (int i = left; i <= right; i++) {
                    // Calculate the maximum points for the current segment
                    dp[left][right] = Math.max(dp[left][right],
                            dp[left][i - 1] + paddedTargets[left - 1] * paddedTargets[i] * paddedTargets[right + 1] + dp[i + 1][right]);
                }
            }
        }

        return dp[1][n]; // Return the maximum points for the entire array
    }

    public static void main(String[] args) {
        int[] a = {3, 1, 5, 8};
        int result = maxPoints(a);
        System.out.println("Maximum points: " + result);
    }
}