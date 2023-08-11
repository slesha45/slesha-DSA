// Question 2.a)
public class LongestSubsequence {
    public static int longestSubsequence(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n]; // dp[i] stores the length of the longest subsequence ending at index i
        dp[0] = 1; // Initialize the length of the subsequence ending at index 0 to 1

        for (int i = 1; i < n; i++) {
            dp[i] = 1; // Initialize the length of the subsequence ending at index i to 1
            for (int j = 0; j < i; j++) {
                // Check if the condition for forming a valid subsequence is satisfied
                if (nums[i] < nums[j] && Math.abs(nums[i] - nums[j]) <= k) {
                    dp[i] = Math.max(dp[i], dp[j] + 1); // Update the length of the subsequence ending at index i
                }
            }
        }

        int maxLength = 0;
        for (int length : dp) {
            maxLength = Math.max(maxLength, length); // Find the maximum length among all subsequences
        }

        return maxLength; // Return the length of the longest subsequence
    }

    public static void main(String[] args) {
        int[] nums = {8, 5, 4, 2, 1, 4, 3, 4, 3, 1, 15};
        int k = 3;
        int result = longestSubsequence(nums, k);
        System.out.println("Length of the longest subsequence: " + result); 
    }
}