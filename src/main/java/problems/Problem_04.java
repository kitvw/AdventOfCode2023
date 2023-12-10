package problems;

import common.PuzzleInput;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;

@Log4j2
public class Problem_04 {
    public static void main(String[] args) {
        List<String> input = PuzzleInput.getInputLines("input_04.txt");
        List<Card> cards = input.stream().map(Card::new).toList();
        int totalPoints = cards.stream().map(Card::pointsWon).reduce(0,Integer::sum);
        log.info("totalPoints: {}", totalPoints);

        Queue<Card> copies = new ArrayDeque<>(cards);
        int copyCount = 0;
        while(!copies.isEmpty()) {
            Card copy = copies.poll();
            copyCount++;
            int matches = copy.matches();
            for(int i = copy.getId(); i < Math.min(copy.getId() + matches,cards.size()); i++) {
                copies.add(cards.get(i));
            }
        }
        log.info("totalCopies: {}", copyCount);
    }

    @Data
    private static class Card {
        private int id;
        private List<Integer> winningNumbers;
        private List<Integer> heldNumbers;
        public Card( String line) {
            String[] parts = line.split(":");
            id = Integer.parseInt(parts[0].replaceAll("[\s]+", " ").split(" ")[1]);
            String[] numLists = parts[1].replaceAll("[\s]+", " ").split("\\|");
            winningNumbers = Arrays.stream(numLists[0].trim().split(" ")).map(Integer::parseInt).toList();
            heldNumbers = Arrays.stream(numLists[1].trim().split(" ")).map(Integer::parseInt).toList();
        }

        public int pointsWon() {
            int matches = matches();
            if (matches == 0)
                return 0;
            return (int) Math.pow(2,matches-1);
        }

        public int matches() {
            int count = 0;
            for(int heldNum : heldNumbers ) {
                if( winningNumbers.contains(heldNum))
                    count++;
            }
            return count;
        }
    }
}
