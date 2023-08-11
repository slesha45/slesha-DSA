// Question 4.a)
import java.util.*;

public class MinimumStepsToCompleteTasks {
    public int minNumberOfSemesters(int N, int[][] relations, int k) {

        // Create an adjacency list to represent the directed graph
        List<Integer>[] graph = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            graph[i] = new ArrayList<>();
        }

        // Create an array to store the indegree of each course
        int[] indegree = new int[N + 1];

        // Build the graph and calculate indegrees
        for (int[] rel : relations) {
            int from = rel[0];
            int to = rel[1];
            graph[from].add(to);
            indegree[to]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }

        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            List<Integer> nextSemester = new ArrayList<>();

            // Enroll courses in the current semester (up to k courses)
            for (int i = 0; i < Math.min(k, size); i++) {
                int course = queue.poll();
                nextSemester.add(course);

                // Decrease the indegree of neighboring courses
                for (int neighbor : graph[course]) {
                    indegree[neighbor]--;
                    if (indegree[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
            steps++;

            // Decrease the indegree of neighboring courses for the next semester
            for (int course : nextSemester) {
                for (int neighbor : graph[course]) {
                    indegree[neighbor]--;
                    if (indegree[neighbor] == 0) {
                        queue.add(neighbor);
                    }
                }
            }
        }

        return steps;
    }

    public static void main(String[] args) {
        int N = 3;
        int[][] relations = {{1, 3}, {2, 3}};
        int k = 2;

        MinimumStepsToCompleteTasks solver = new MinimumStepsToCompleteTasks();
        int result = solver.minNumberOfSemesters(N, relations, k);
        System.out.println("Minimum number of steps: " + result);
    }
}