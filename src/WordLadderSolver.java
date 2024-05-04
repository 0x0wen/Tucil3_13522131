import java.util.*;

public class WordLadderSolver {
    public List<String> greedyBestFirstSearch(String start, String end, Set<String> wordSet) {
        Map<String, String> parentMap = new HashMap<>(); 
        PriorityQueue<String> openSet = new PriorityQueue<>(Comparator.comparingInt(node -> heuristic(node, end)));

        openSet.offer(start);
        parentMap.put(start, null);

        while (!openSet.isEmpty()) {
            String current = openSet.poll();
            if (current.equals(end)) {
                return reconstructPath(parentMap, current);
            }
            for (String neighbor : generateNeighbors(current, wordSet)) {
                if (!parentMap.containsKey(neighbor)) {
                    parentMap.put(neighbor, current);
                    openSet.offer(neighbor);
                }
            }
        }
        return Collections.emptyList();
    }

    private int heuristic(String word1, String word2) {
        int diffCount = 0;
        for (int i = 0; i < word1.length(); i++) {
            if (word1.charAt(i) != word2.charAt(i)) {
                diffCount++;
            }
        }
        return diffCount;
    }
    public List<String> uniformCostSearch(String start, String end, Set<String> wordSet) {
        Map<String, String> parentMap = new HashMap<>(); 
        Map<String, Integer> costMap = new HashMap<>(); 

        PriorityQueue<Pair<String, Integer>> openSet = new PriorityQueue<>(Comparator.comparingInt(Pair::getSecond));
        Set<String> closedSet = new HashSet<>();

        openSet.offer(new Pair<>(start, 0));
        parentMap.put(start, null);
        costMap.put(start, 0);

        while (!openSet.isEmpty()) {
            Pair<String, Integer> currentPair = openSet.poll();
            String currentWord = currentPair.getFirst();
            int currentCost = currentPair.getSecond();

            if (currentWord.equals(end)) {
                // Reconstruct and return the path
                return reconstructPath(parentMap, end);
            }

            closedSet.add(currentWord);

            for (String neighbor : generateNeighbors(currentWord, wordSet)) {
                int newCost = currentCost + 1; 
                if (!closedSet.contains(neighbor) && (!costMap.containsKey(neighbor) || newCost < costMap.get(neighbor))) {
                    parentMap.put(neighbor, currentWord);
                    costMap.put(neighbor, newCost);
                    openSet.offer(new Pair<>(neighbor, newCost));
                }
            }
        }
        return Collections.emptyList();
    }
    public class Pair<K, V> {
        private final K first;
        private final V second;
    
        public Pair(K first, V second) {
            this.first = first;
            this.second = second;
        }
    
        public K getFirst() {
            return first;
        }
    
        public V getSecond() {
            return second;
        }
    }
    private List<String> reconstructPath(Map<String, String> parentMap, String end) {
        List<String> path = new ArrayList<>();
        String current = end;
        while (current != null) {
            path.add(current);
            current = parentMap.get(current);
        }
        Collections.reverse(path);
        return path;
    }
    public List<String> aStar(String start, String end, Set<String> wordSet) {
        Map<String, String> parentMap = new HashMap<>(); 
        Map<String, Integer> gScore = new HashMap<>();
        Map<String, Integer> fScore = new HashMap<>(); 
    
        PriorityQueue<String> openSet = new PriorityQueue<>(Comparator.comparingInt(fScore::get));
        Set<String> closedSet = new HashSet<>();
        
        openSet.offer(start);
        gScore.put(start, 0);
        fScore.put(start, heuristic(start, end));
    
        while (!openSet.isEmpty()) {
            String current = openSet.poll();
            if (current.equals(end)) {
                return reconstructPath(parentMap, current);
            }
            closedSet.add(current);
            for (String neighbor : generateNeighbors(current, wordSet)) {
                if (!closedSet.contains(neighbor)) {
                    int tentativeGScore = gScore.getOrDefault(current, Integer.MAX_VALUE) + 1;
                    if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                        parentMap.put(neighbor, current);
                        gScore.put(neighbor, tentativeGScore);
                        fScore.put(neighbor, tentativeGScore + heuristic(neighbor, end));
                        openSet.offer(neighbor);
                    }
                }
            }
        }
        return Collections.emptyList();
    }
    
    private Set<String> generateNeighbors(String word, Set<String> wordSet) {
        Set<String> neighbors = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {
            char[] wordArray = word.toCharArray();
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != word.charAt(i)) {
                    wordArray[i] = c;
                    String neighbor = new String(wordArray);
                    if (wordSet.contains(neighbor)) {
                        neighbors.add(neighbor);
                    }
                }
            }
        }
        return neighbors;
    }
    
}


