// Question 1.b)
import java.util.Arrays;

public class PathaoCoins {
    public static void main(String[] args) {
        int[] ratings = {1, 0, 2};
        int minimumCoins = minimumCoins(ratings);
        System.out.println("Minimum number of coins required: " + minimumCoins);
    }

    // Function to calculate the minimum number of coins required
    public static int minimumCoins(int[] ratings) {
        int n = ratings.length;
        int[] coins = new int[n];
        Arrays.fill(coins, 1);// Initialize coins array with 1 (each person starts with at least 1 coin)

        // Traverse the ratings array from left to right
        for (int i = 1; i < n; i++) {
            // If the current person has a higher rating than the previous person
            if (ratings[i] > ratings[i - 1]) {
                coins[i] = coins[i - 1] + 1; // Give this person one more coin than the previous person
            }
        }

        // Traverse the ratings array from right to left
        for (int i = n - 2; i >= 0; i--) {
            // If the current person has a higher rating than the next person,
            // and the current person has fewer or equal coins than the next person
            if (ratings[i] > ratings[i + 1] && coins[i] <= coins[i + 1]) {
                coins[i] = coins[i + 1] + 1; // Give this person one more coin than the next person
            }
        }

        // Calculate the total number of coins needed
        int totalCoins = 0;
        for (int coinsCount : coins) {
            totalCoins += coinsCount;
        }

        return totalCoins; // Return the minimum total number of coins required
    }
}