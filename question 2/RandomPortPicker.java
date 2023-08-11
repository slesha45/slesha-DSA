//Question 2.b)
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomPortPicker {
    private Set<Integer> whitelist;
    private Random random;

    // Constructor to initialize the whitelist and remove blacklisted ports
    public RandomPortPicker(int k, int[] blacklisted_ports) {
        whitelist = new HashSet<>();
        
        // Initialize the whitelist with ports from 0 to k - 1
        for (int i = 0; i < k; i++) {
            whitelist.add(i);
        }

        // Remove blacklisted ports from the whitelist
        for (int port : blacklisted_ports) {
            whitelist.remove(port);
        }

        random = new Random(); // Initialize the random number generator
    }

    // Method to get a random port from the whitelist
    public int get() {
        int randomIndex = random.nextInt(whitelist.size()); // Generate a random index
        int randomPort = 0;
        for (int port : whitelist) {
            if (randomIndex == 0) {
                randomPort = port;
                break;
            }
            randomIndex--;
        }
        return randomPort; // Return the randomly selected port
    }

    public static void main(String[] args) {
        int[] blacklisted_ports = {2, 3, 5};
        RandomPortPicker picker = new RandomPortPicker(7, blacklisted_ports);
        System.out.println(picker.get()); // Output: 0 (a random port from the whitelist)
        System.out.println(picker.get()); // Output: 4 (a different random port from the whitelist)
    }
}