import java.util.Map;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class Main {
    public static Scanner input;
    
    public static void main(String[] args) {
        String start,end, algorithm;
        List<String> path;
        long startTime,endTime,executionTime;
        WordLadderSolver solver = new WordLadderSolver();
        Set<String> wordSet = readWordsFromFile("dictionary.txt");
        input = new Scanner(System.in);
        while(true){
            System.out.println("\nStart word: ");
            start = input.next().toLowerCase();
            System.out.println("End word: ");
            end = input.next().toLowerCase();
            if(start.length() != end.length()){
                System.out.println("Words must be of equal length");
                continue;
            }else if(!wordSet.contains(start) || !wordSet.contains(end)){
                System.out.println("Words not in dictionary");
                continue;
            }
            Set<String> filteredWordSet = filterWordsByLength(wordSet, start.length());
            System.out.println("1. UCS");
            System.out.println("2. GBFS");
            System.out.println("3. A*");
            System.out.println("Algorithm: ");
            algorithm = input.next().toLowerCase();
            if(algorithm.equals("1") || algorithm.equals("ucs")){
                System.out.println("Uniform Cost Search");
                 startTime = System.currentTimeMillis();
                path = solver.uniformCostSearch(start, end, filteredWordSet);
                 endTime = System.currentTimeMillis();
            }
            else if(algorithm.equals("2") || algorithm.equals("gbfs")){
                System.out.println("Greedy Best First Search");
                 startTime = System.currentTimeMillis();
                 path = solver.greedyBestFirstSearch(start, end, filteredWordSet);
                 endTime = System.currentTimeMillis();
            }
            else if(algorithm.equals("3") || algorithm.equals("a*")){
                System.out.println("A* Search");
                 startTime = System.currentTimeMillis();
                 path = solver.aStar(start, end, filteredWordSet);
                 endTime = System.currentTimeMillis();
            }
            else{
                System.out.println("Invalid input");
                 continue;
            }
            executionTime = endTime - startTime;
            System.out.println("\n--- Results ---");
            System.out.println("Execution time: " + executionTime + " milliseconds");
            System.out.println("Path length: " + path.size());
            if (path.isEmpty()) {
                System.out.println("No path found.");
            } else {
                System.out.println("Path found: " + path + "");
            }
            System.out.println("--- ****** ---\n");
            
        }
        
    }

    
    // Method to filter words in a set by length
    public static Set<String> filterWordsByLength(Set<String> wordSet, int targetLength) {
        Set<String> filteredSet = new HashSet<>();
        for (String word : wordSet) {
            if (word.length() == targetLength) {
                filteredSet.add(word);
            }
        }
        return filteredSet;
    }

    // Method to read words from a .txt file and store them in a set
    public static Set<String> readWordsFromFile(String filePath) {
        Set<String> wordSet = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Process each line to extract words
                // Example: split by space
                String[] words = line.split("\\s+");
                Collections.addAll(wordSet, words);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordSet;
    }
}